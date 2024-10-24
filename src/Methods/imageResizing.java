package Methods;

import ADTMatrix.Matrix;
import ADTMatrix.Operation;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Scanner;


public class ImageResizing {
    BicubicInterpolation bi = new BicubicInterpolation();
    Determinant det = new Determinant();
    Inverse inverse = new Inverse();
    Operation op = new Operation();

    public Matrix getMatrixD() {
        Matrix matrixD = new Matrix(16, 16);
        
        double[][] variationPoints = {
            {0, 0},  
            {1, 0}, 
            {0, 1},
            {1, 1}
        };
        
        int[] blockRows = {0, 4, 8, 12};  // f: 0-3, fx: 4-7, fy: 8-11, fxy: 12-15
        
        for (int blockStart : blockRows) {
            for (double[] point : variationPoints) {
                int row = blockStart + (int)(point[0] + point[1] * 2);
                int idx = 0;
                
                for (int j = -1; j < 3; j++) {
                    for (int i = -1; i < 3; i++) {
                        double value = 0.0;
                        
                        switch (blockStart) {
                            case 0:  // Function values (f)
                                if (point[0] == i && point[1] == j) {
                                    value = 1.0;
                                }
                                break;
                                
                            case 4:  // X-derivatives (fx)
                                if (point[1] == j) {  // Same y-coordinate
                                    if (point[0] + 1 == i) value = 0.5;
                                    else if (point[0] - 1 == i) value = -0.5;
                                }
                                break;
                                
                            case 8:  // Y-derivatives (fy)
                                if (point[0] == i) {  // Same x-coordinate
                                    if (point[1] + 1 == j) value = 0.5;
                                    else if (point[1] - 1 == j) value = -0.5;
                                }
                                break;
                                
                            case 12: // Mixed derivatives (fxy)
                                if (point[0] + 1 == i && point[1] + 1 == j) value = 0.25;
                                else if (point[0] == i && point[1] - 1 == j) value = -0.25;
                                else if (point[0] - 1 == i && point[1] == j) value = -0.25;
                                else if (point[0] == i && point[1] == j) value = 0.25;
                                break;
                        }
                        
                        matrixD.matrix[row][idx++] = value;
                    }
                }
            }
        }
        
        return matrixD;
    }

    public Matrix getMatrixXinvD()
    {
        /*Matrik hasil perkalian X^-1.D untuk mendapatkan nilai a */
        Matrix result = new Matrix(16,16);

        Matrix MatrixXInverse = inverse.inverseGJ(bi.getMatrixX());
        result = op.multiplyMatrix(MatrixXInverse, getMatrixD());
        return result;
    }

    public static Matrix[] getSurroundingPoints(int i, int j, BufferedImage input) {
        // Constants for clarity
        final int REGION_SIZE = 4;  // 4x4 region
        final int MATRIX_HEIGHT = 16;  // 16 points total
        final int CHANNEL_COUNT = 4;   // ARGB channels
        
        // Initialize matrices for each color channel
        Matrix[] channelMatrices = new Matrix[CHANNEL_COUNT];
        for (int channel = 0; channel < CHANNEL_COUNT; channel++) {
            channelMatrices[channel] = new Matrix(MATRIX_HEIGHT, 1);
        }
        
        // Process the 4x4 region around the point
        int matrixRow = 0;
        for (int y = j - 1; y < j + REGION_SIZE - 1; y++) {
            for (int x = i - 1; x < i + REGION_SIZE - 1; x++) {
                // Get ARGB values for current pixel
                double[] colorChannels = getColorRGB(input.getRGB(x, y));
                
                // Store each channel value in its respective matrix
                for (int channel = 0; channel < CHANNEL_COUNT; channel++) {
                    channelMatrices[channel].matrix[matrixRow][0] = colorChannels[channel];
                }
                
                matrixRow++;
            }
        }
        
        return channelMatrices;
    }

    public static double[] getColorRGB (int in){
        double[] temp = new double[4];
        temp[0] = (double)((in >> 24) & 0xff);
        temp[1] = (double)((in & 0xff0000) >> 16);
        temp[2] = (double)((in & 0xff00) >> 8);
        temp[3] = (double)(in & 0xff);
        return temp;
    }

    public int getColorValue(double x, double y, Matrix[] colorMatrices) {
        final int GRID_SIZE = 4;
        final int ALPHA = 0, RED = 1, GREEN = 2, BLUE = 3;
        
        // Calculate interpolated values for each channel
        double[] channels = new double[4];
        int idx = 0;
        
        // Calculate polynomial interpolation for each channel
        for (int j = 0; j < GRID_SIZE; j++) {
            double yPow = Math.pow(y, j);
            for (int i = 0; i < GRID_SIZE; i++) {
                double xPow = Math.pow(x, i);
                double factor = xPow * yPow;
                
                channels[ALPHA] += colorMatrices[ALPHA].matrix[idx][0] * factor;
                channels[RED] += colorMatrices[RED].matrix[idx][0] * factor;
                channels[GREEN] += colorMatrices[GREEN].matrix[idx][0] * factor;
                channels[BLUE] += colorMatrices[BLUE].matrix[idx][0] * factor;
                
                idx++;
            }
        }
        
        // Clamp all channels to valid range [0, 255]
        int[] clampedChannels = new int[4];
        for (int i = 0; i < 4; i++) {
            clampedChannels[i] = clampTo8Bit((int)channels[i]);
        }
        
        // Combine channels into final ARGB color
        return (clampedChannels[ALPHA] << 24) | 
               (clampedChannels[RED] << 16) | 
               (clampedChannels[GREEN] << 8) | 
               clampedChannels[BLUE];
    }
    
    private int clampTo8Bit(int value) {
        return Math.min(255, Math.max(0, value));
    }

    public void imageProccesing(File imgFile)
    {
        Scanner in = new Scanner(System.in);
        BufferedImage inputImg = null;
        BufferedImage curImg = null;
        BufferedImage outputImg = null;

        try
        {
            inputImg = ImageIO.read(imgFile);
            int height = inputImg.getHeight();
            int width = inputImg.getWidth();

            System.out.print("Masukkan faktor pengali x: \n");
            double x, y;
    
            while (true) {
                System.out.println("Masukkan nilai x (dalam rentang 0.5 dan 2, inklusif): ");
                try {
                    x = Double.parseDouble(in.nextLine());
                    // Bersihkan buffer setelah membaca double
                    if (x >= 0.5 && x <= 2) {
                        break; // Keluar dari loop jika input valid
                    } else {
                        System.out.println("Faktor pengali x harus dalam rentang 0.5 dan 2, inklusif.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input tidak valid. Silakan masukkan angka desimal.");
                }
            }
    
            // Input nilai b
            while (true) {
                System.out.println("Masukkan nilai y (dalam rentang 0.5 dan 2, inklusif): ");
                try {
                    y = Double.parseDouble(in.nextLine());
                    // Bersihkan buffer setelah membaca double
                    if (y >= 0.5 && y <= 2) {
                        break; // Keluar dari loop jika input valid
                    } else {
                        System.out.println("Nilai y harus dalam rentang 0.5 dan 2, inklusif.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input tidak valid. Silakan masukkan angka desimal.");
                }
            }

            curImg = new BufferedImage(width+4, height+4, BufferedImage.TYPE_INT_RGB);

            for (int i = 0; i < width; i++){
                for (int j = 0; j < height; j++){
                    curImg.setRGB(i+2, j+2, inputImg.getRGB(i, j));
                }
            }

            for (int i = 0; i < width+4; i++){
                if (i <= 2){
                    /* Set the top 2 rows (0 and 1) */
                    curImg.setRGB(i, 0, curImg.getRGB(2, 2));
                    curImg.setRGB(i, 1, curImg.getRGB(2, 2));
                    /* Set the bottom 2 rows (height + 2 and height + 3)*/
                    curImg.setRGB(i, height+2, curImg.getRGB(2, height+1));
                    curImg.setRGB(i, height+3, curImg.getRGB(2, height+1));
                } else if (i >= width + 1){
                    /* Set the leftmost two pixels in the last two rows*/
                    curImg.setRGB(i, 0, curImg.getRGB(width+1, 2));
                    curImg.setRGB(i, 1, curImg.getRGB(width+1, 2));
                    /*  Set the rightmost two pixels in the bottom two rows */
                    curImg.setRGB(i, height+2, curImg.getRGB(width+1, height+1));
                    curImg.setRGB(i, height+3, curImg.getRGB(width+1, height+1));
                } else {
                    /* Set the left and right edge pixels (y = 0 and y = 1) for the inner image*/
                    curImg.setRGB(i, 0, curImg.getRGB(i, 2));
                    curImg.setRGB(i, 1, curImg.getRGB(i, 2));
                    /* Set the left and right edge pixels (y = height + 2 and y = height + 3) for the inner image*/
                    curImg.setRGB(i, height+2, curImg.getRGB(i, height+1));
                    curImg.setRGB(i, height+3, curImg.getRGB(i, height+1));
                }
            }

            for (int j = 2; j < height+2; j++){
                curImg.setRGB(0, j, curImg.getRGB(2, j));
                curImg.setRGB(1, j, curImg.getRGB(2,j));
                curImg.setRGB(width+2, j, curImg.getRGB(width+1, j));
                curImg.setRGB(width+3, j, curImg.getRGB(width+1, j));
            }

            /*Hitung ukuran baru image*/
            int newWidth = (int)(width*x);
            int newHeight = (int)(height*y);

            Matrix[][] Aijused = new Matrix[height+1][4];

            int curX = -1;
            int curY = -1;

            outputImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

            for(int i = 0 ; i < newWidth;i++){
                for(int j = 0 ; j < newHeight;j++){
                    /*Hitung Skala Posisi dari setiap pixel */
                    double xCon = (double)((1 + (2*i+1)*(double)width/(double)newWidth)/2);
                    double yCon = (double)((1 + (2*j+1)*(double)height/(double)newHeight)/2);

                    double idxAi = Math.floor(xCon);
                    double idxAj = Math.floor(yCon);
                    System.out.println("nilai x: "+idxAi+", nilai y: "+idxAj);
                    
                    if(idxAi > (double)curX){
                        curX = (int)idxAi;
                        curY = -1;
                    }

                    if(idxAj > (double)curY)
                    {
                        curY = (int)idxAj;
                        Matrix[] surroundingPoint = getSurroundingPoints((int)(idxAi + 1), (int)(idxAj + 1), curImg);
                        Aijused[curY][0] = op.multiplyMatrix(getMatrixXinvD(),surroundingPoint[0]);
                        Aijused[curY][1] = op.multiplyMatrix(getMatrixXinvD(),surroundingPoint[1]);
                        Aijused[curY][2] = op.multiplyMatrix(getMatrixXinvD(),surroundingPoint[2]);
                        Aijused[curY][3] = op.multiplyMatrix(getMatrixXinvD(),surroundingPoint[3]);
                    }

                    // calculate the xtrace, ytrace used for f(xtrace,ytrace), 0 < xtrace, ytrace < 1
                    double xtrace = xCon - idxAi;
                    double ytrace = yCon - idxAj;
                    int rgb = getColorValue(xtrace, ytrace, Aijused[(int)idxAj]);

                    outputImg.setRGB(i, j, rgb);
                }
            }

        }catch(IOException e){
            System.out.println("Error: "+e);
        }

        try 
        {
            System.out.println("Masukkan nama file output (tanpa ekstensi): ");
            String proccesedImg = in.nextLine();
            File output = new File("../test/img/"+proccesedImg + ".jpg");
            ImageIO.write(outputImg, "jpg",output);
        } 
        catch (IOException e) 
        {
            System.out.println(e);
        }
    }
    
}
