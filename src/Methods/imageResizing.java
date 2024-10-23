package Methods;

import ADTMatrix.Matrix;
import ADTMatrix.Operation;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;


public class imageResizing {
    bicubicInterpolation bi = new bicubicInterpolation();
    Determinant det = new Determinant();
    Inverse inverse = new Inverse();
    Operation op = new Operation();

    public Matrix getMatrixD()
    {
        /*Set Up Matrix D */
        Matrix MatrixD = new Matrix(16,16);
        double[][] var = new double[][]{{0,0},{1,0},{0,1},{1,1}};//range f,fx,fy,fxy
        int[] rows = new int[]{0,4,8,12};//f 0-3, fx 4-7, fy 8-11, fxy 12-15    
        int row, idx;
        for (int x : rows){
            for (double[] arr : var){
                idx = 0;
                for (int j = -1; j < 3; j++){
                    for (int i = -1; i < 3; i++){
                        row = x + (int)(arr[0] + arr[1]*2);
                        if (x == 0){
                            if (arr[0] == i && arr[1] == j){
                                MatrixD.matrix[row][idx] = 1;
                            }
                        } else if (x == 4){
                            if (arr[0]+1 == i && arr[1] == j){
                                MatrixD.matrix[row][idx] = 0.5;
                            } else if (arr[0]-1 == i && arr[1] == j){
                                MatrixD.matrix[row][idx] = -0.5;
                            }
                        } else if (x == 8){
                            if (arr[0] == i && arr[1] + 1 == j){
                                MatrixD.matrix[row][idx] = 0.5;
                            } else if (arr[0] == i && arr[1] - 1 == j){
                                MatrixD.matrix[row][idx] = -0.5;
                            }
                        } else {
                            if (arr[0]+1 == i && arr[1]+1 == j){
                                MatrixD.matrix[row][idx] = 0.25;
                            } else if (arr[0] == i && arr[1] - 1 == j){
                                MatrixD.matrix[row][idx] = -0.25;
                            } else if (arr[0]-1 == i && arr[1] == j){
                                MatrixD.matrix[row][idx] = -0.25;
                            } else if (arr[0] == i && arr[1] == j){
                                MatrixD.matrix[row][idx] = 0.25;
                            }
                        }
                        idx++;
                    }
                }
            }
        }
        return MatrixD;
    }

    public Matrix getMatrixXinvD()
    {
        /*Matrik hasil perkalian X^-1.D untuk mendapatkan nilai a */
        Matrix result = new Matrix(16,16);

        Matrix MatrixXInverse = inverse.inverseGJ(bi.getMatrixX());
        result = Operation.multiplyMatrix(MatrixXInverse, getMatrixD());
        return result;
    }

    public static Matrix[] getSurroundingPoint(int i, int j, BufferedImage input){
        Matrix[] temp = new Matrix[4];
        for (int q = 0; q < 4; q++){
            temp[q] = new Matrix(16, 1);
        }
        int idx = 0;
        for (int y = j-1; y < j+3; y++){
            for (int x = i-1; x < i+3; x++){
                double[] rgb = getColorRGB(input.getRGB(x, y));
                //alpha
                temp[0].matrix[idx][0] = rgb[0];
                //red
                temp[1].matrix[idx][0] = rgb[1];
                //green
                temp[2].matrix[idx][0] = rgb[2];
                //blue
                temp[3].matrix[idx][0] = rgb[3];
                idx++;
            }
        }
        return temp;
    }

    public static double[] getColorRGB (int in){
        double[] temp = new double[4];
        temp[0] = (double)((in >> 24) & 0xff);
        temp[1] = (double)((in & 0xff0000) >> 16);
        temp[2] = (double)((in & 0xff00) >> 8);
        temp[3] = (double)(in & 0xff);
        return temp;
    }

    public int getColorValue(double x, double y, Matrix[] a){
        int idx = 0;
        double alpha = 0,red = 0, green = 0, blue = 0;
        int al, r, g ,b;
        for (int j = 0; j < 4; j++){
            for (int i = 0; i < 4; i++){
                alpha += a[0].matrix[idx][0]*Math.pow(x,i)*Math.pow(y,j);
                red += a[1].matrix[idx][0]*Math.pow(x,i)*Math.pow(y,j);
                green += a[2].matrix[idx][0]*Math.pow(x,i)*Math.pow(y,j);
                blue += a[3].matrix[idx][0]*Math.pow(x,i)*Math.pow(y,j);
                idx++;
            }
        }
        al = (int)alpha;
        if (al > 255){
            al = 255;
        } else if (al < 0){
            al = 0;
        }
        r = (int)red;
        if (r > 255){
            r = 255;
        } else if (r < 0){
            r = 0;
        }
        g = (int)green;
        if (g > 255){
            g = 255;
        } else if (g < 0){
            g = 0;
        }
        b = (int)blue;
        if (b > 255){
            b = 255;
        } else if (b < 0){
            b = 0;
        }
        int rgb = (al << 24) | (r << 16) | (g << 8) | b ;
        return rgb;
    }

    public void imageProccesing(double scaleX, double scaleY, String sourceImg, String proccesedImg)
    {
        BufferedImage inputImg = null;
        BufferedImage curImg = null;
        BufferedImage outputImg = null;
        String imgDirectory = System.getProperty("user.dir") + "/test/img/";
        File imgFile = new File(imgDirectory + sourceImg);
        
        try
        {
            inputImg = ImageIO.read(imgFile);
            int height = inputImg.getHeight();
            int width = inputImg.getWidth();
            // System.out.println("Original Image Dimension: "+width+"x"+height);
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
            int newWidth = (int)(width*scaleX);
            int newHeight = (int)(height*scaleY);

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
                        Matrix[] surroundingPoint = getSurroundingPoint((int)(idxAi + 1), (int)(idxAj + 1), curImg);
                        Aijused[curY][0] = Operation.multiplyMatrix(getMatrixXinvD(),surroundingPoint[0]);
                        Aijused[curY][1] = Operation.multiplyMatrix(getMatrixXinvD(),surroundingPoint[1]);
                        Aijused[curY][2] = Operation.multiplyMatrix(getMatrixXinvD(),surroundingPoint[2]);
                        Aijused[curY][3] = Operation.multiplyMatrix(getMatrixXinvD(),surroundingPoint[3]);
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

        try {
            File output = new File(imgDirectory+proccesedImg);
            ImageIO.write(outputImg, "png",output);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
}
