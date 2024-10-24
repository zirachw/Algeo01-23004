package Methods;

import ADTMatrix.Matrix;
import ADTMatrix.Operation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;

public class Determinant {

    Operation op = new Operation();
    Scanner input = new Scanner(System.in);

    public  double determinantCofactor(Matrix M) 
    {
        /* Menghitung determinan matriks dengan metode cofactor */
        if (M.rowEff != M.colEff) 
        {
            System.out.println("Matriks bukan matriks persegi, determinan tidak dapat dihitung.");
            return 0;
        }

        if (M.rowEff == 1) 
        {
            return M.matrix[0][0];
        }

        if (M.rowEff == 2) 
        {
            return M.matrix[0][0] * M.matrix[1][1] - M.matrix[0][1] * M.matrix[1][0];
        }

        double det = 0;
        for (int i = 0; i < M.colEff; i++) 
        {
            Matrix minor = new Matrix(M.rowEff - 1, M.colEff - 1);
            for (int j = 1; j < M.rowEff; j++) 
            {
                for (int k = 0; k < M.colEff; k++) 
                {
                    if (k < i) 
                    {
                        minor.matrix[j - 1][k] = M.matrix[j][k];
                    } else if (k > i) {
                        minor.matrix[j - 1][k - 1] = M.matrix[j][k];
                    }
                }
            }
            det += Math.pow(-1, i) * M.matrix[0][i] * determinantCofactor(minor);
        }
        return det;
    }

    public  double determinantOBE(Matrix M) {
        /* Menghitung determinan matriks dengan metode eliminasi Gauss */
        if (M.rowEff != M.colEff) {
            System.out.println("Matriks bukan matriks persegi, determinan tidak dapat dihitung.");
            return 0;
        }
    
        if (M.rowEff == 1) {
            return M.matrix[0][0];
        }
    
        Matrix temp = new Matrix(M.rowEff, M.colEff);
        for (int i = 0; i < M.rowEff; i++) {
            System.arraycopy(M.matrix[i], 0, temp.matrix[i], 0, M.colEff);
        }
    
        double det = 1;
        for (int i = 0; i < M.rowEff; i++) {
            double pivot = temp.matrix[i][i];
            int pivotRow = i;
    
            for (int j = i + 1; j < M.rowEff; j++) {
                if (Math.abs(temp.matrix[j][i]) > Math.abs(pivot)) {
                    pivot = temp.matrix[j][i];
                    pivotRow = j;
                }
            }
    
            if (pivotRow != i) {
                double[] tempRow = temp.matrix[i];
                temp.matrix[i] = temp.matrix[pivotRow];
                temp.matrix[pivotRow] = tempRow;
                det *= -1; 
            }
    
            if (temp.matrix[i][i] == 0) {
                return 0; 
            }
    
            for (int j = i + 1; j < M.rowEff; j++) {
                double factor = temp.matrix[j][i] / temp.matrix[i][i];
                for (int k = i; k < M.colEff; k++) {
                    temp.matrix[j][k] -= factor * temp.matrix[i][k];
                }
            }
        }
    
        for (int i = 0; i < M.rowEff; i++) {
            det *= temp.matrix[i][i];
        }
    
        return det;
    }    

    public  Matrix getCofactor(Matrix M){
        /* Menghitung matriks kofactor dari matrix M */
        Matrix cofactor = new Matrix(M.rowEff, M.colEff);

        for(int i = 0 ; i < M.rowEff;i++)
        {
            for(int j = 0 ; j < M.colEff;j++)
            {
                cofactor.matrix[i][j] = Math.pow(-1, i+j) * determinantCofactor(op.getMinor(M, i, j));
            }
        }
        return cofactor;
    }

    public void exportDet(Matrix M, BigDecimal det)
    {
        // Menulis matriks ke file
        String filename;
        System.out.print("Masukkan nama file: ");
        filename = input.nextLine() + ".txt";

        // Menuliskan matriks ke layar
        int[] columnWidths = new int[M.colEff];

        for (int col = 0; col < M.colEff; col++) 
        {
            for (int row = 0; row < M.rowEff; row++) 
            {
                BigDecimal bd = new BigDecimal(M.matrix[row][col]).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros();
                int width = (bd.toString()).length();
                
                if (width > columnWidths[col]) 
                {
                    columnWidths[col] = width;
                }
            }
        }

        try
        {
            String userDirectory = System.getProperty("user.dir");
            BufferedWriter writer = new BufferedWriter(new FileWriter(userDirectory + "/test/result/" + filename));
            
            writer.write("Matriks: ");
            writer.newLine();

            // Pretty Print :)
            for (int row = 0; row < M.rowEff; row++)
            {
                if (M.rowEff == 1)
                {
                    writer.write("[");
                }
                else if (row == 0)
                {
                    writer.write("┌");
                }
                else if (row == M.rowEff - 1)
                {
                    writer.write("└");
                }
                else
                {
                    writer.write("|");
                }

                for (int col = 0; col < M.colEff; col++)
                {
                    BigDecimal bd = new BigDecimal(M.matrix[row][col]).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros(); // Round to 5 decimal places
                    writer.write(String.format("%" + (columnWidths[col] + 2) + "s", bd.toPlainString()));
                }

                if (M.rowEff == 1)
                {
                    writer.write("  ]");
                }
                else if (row == 0)
                {
                    writer.write("  ┐");
                }
                else if (row == M.rowEff - 1)
                {
                    writer.write("  ┘");
                }
                else
                {
                    writer.write("  |");
                }
                writer.newLine();
            }
            writer.newLine();

            if (M.rowEff != M.colEff)
            {
                writer.write("Determinan tidak dapat dihitung karena bukan matriks persegi.");
            }
            else
            {
                writer.write("Determinannya adalah: " + det);
            }
            writer.close();
        }

        catch (IOException e)
        {
            System.out.println("Terjadi kesalahan saat menulis file.");
        }
    }
}
