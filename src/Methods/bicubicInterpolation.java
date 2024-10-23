package Methods;

import java.io.BufferedWriter;
import java.io.FileWriter;
// import java.util.*;
import java.lang.Math;
import java.util.Scanner;

import ADTMatrix.Matrix;

public class bicubicInterpolation {

    public Scanner input = new Scanner(System.in);

    public Matrix getMatrixX() 
    {
        Matrix matriksX =  new Matrix(16,16);

        int baris = 0 , kolom = 0;
        for(int k = 0 ; k < 4;k++){
            for(int y = 0 ; y < 2;y++){
                for(int x = 0 ; x < 2;x++){
                    kolom = 0;
                    for(int j = 0 ; j < 4;j++){
                        for(int i = 0 ; i < 4;i++){
                            if(baris >= 0 && baris < 4){
                                matriksX.matrix[baris][kolom] = Math.pow(x, i) * Math.pow(y, j);
                            }else if(baris >= 4 && baris < 8){
                                matriksX.matrix[baris][kolom] = i * Math.pow(x, i-1) * Math.pow(y, j);
                            }else if(baris >= 8 && baris < 12){
                                matriksX.matrix[baris][kolom] = j * Math.pow(x, i) * Math.pow(y, j-1);
                            }else if(baris >= 12 && baris < 16){
                                matriksX.matrix[baris][kolom] = i * j * Math.pow(x, i-1) * Math.pow(y, j-1);
                            }

                            if(Double.isNaN(matriksX.matrix[baris][kolom])){
                                matriksX.matrix[baris][kolom] = 0;
                            }

                            kolom++;
                        }
                    }
                    baris++;
                }
            }
        }
        return matriksX;
    }

    public Matrix getMatrixF(Matrix m4x4) 
    {
        Matrix matrixF = new Matrix(16,1);

        int row = 0;
        for(int i = 0 ; i < 4;i++){
            for(int j = 0 ; j < 4;j++){
                matrixF.matrix[row][0] = m4x4.matrix[i][j];
                row++;
            }
        }

        return matrixF;
    }


    public Matrix multiplyMatrixBik(Matrix m1, Matrix m2){
        Matrix mOut = new Matrix(m1.rowEff, m2.colEff);
    
        int i,j,k;
        for(i=0; i<m1.rowEff; i++){
            for(j=0; j<m2.colEff; j++){
                for(k=0; k<m1.colEff; k++){
                    mOut.matrix[i][j] += m1.matrix[i][k] * m2.matrix[k][j];
                }
            }
        }
        return mOut;
    }

    public Matrix getMatrixAij(Matrix m16x1) 
    {
        Inverse inverse = new Inverse();
        Matrix temp = getMatrixX();
        // temp.writeMatrix();
        Matrix inverted = inverse.inverseGJ(temp);
        // inverted.writeMatrix(); 

        return multiplyMatrixBik(inverted, m16x1);
    }

    public double getFabResult(Matrix Maij, double a, double b)
    {
        double result = 0;
        int row = 0;
        for(int i = 0 ; i < 4;i++){
            for(int j = 0 ; j < 4;j++){
                result += Maij.matrix[row][0] * Math.pow(a, j) * Math.pow(b, i);
                row++;
            }
        }
        return result;
    }

    public void exportBicubic(Matrix Maij, double a, double b)
    {
        double result = getFabResult(Maij, a, b);
        String filename;
        System.out.println("Masukkan nama file: ");
        filename = input.nextLine() + ".txt";

        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./test/" + filename));
            writer.write("Hasil dari f(" +String.valueOf(a)+","+ String.valueOf(b)+ "): " +String.valueOf(result));
            writer.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
}
