// Package set-up
package Methods;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

// import from packages
import ADTMatrix.Matrix;
import ADTMatrix.Operation;

public class Inverse {

    // Selektor
    Operation op = new Operation();
    Determinant det = new Determinant();
    Scanner input = new Scanner(System.in);

    public boolean isZeroRow(double[] row) {
        // Mengecek baris dengan elemen bernilai semua 0

        for (int i = 0; i < row.length; i++) {
            if (row[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isZeroColumn(Matrix M) {
        // Mengecek kolom dengan elemen bernilai semua 0

        for (int col = 0; col < M.colEff; col++) {
            boolean isZero = true;
            for (int row = 0; row < M.rowEff; row++) {
                if (M.matrix[row][col] != 0) {
                    isZero = false;
                    break;
                }
            }
            if (isZero) {
                return true;
            }
        }
        return false;
    }

    public void multiplyRowInv(Matrix M, int row, double factor) {
        // Mengalikan baris dengan suatu faktor

        for (int j = 0; j < M.colEff; j++) {
            M.matrix[row][j] *= factor;
        }
    }

    public void subtractRowInv(Matrix M, int rowToSubtract, int targetRow, double factor) {
        // Mengurangi satu baris dengan baris lainnya

        for (int j = 0; j < M.colEff; j++) {
            M.matrix[targetRow][j] -= factor * M.matrix[rowToSubtract][j];
        }
    }

    public void swapRow(Matrix M, int row1, int row2) {
        // Menukar dua baris pada matriks

        double[] temp = M.matrix[row1];
        M.matrix[row1] = M.matrix[row2];
        M.matrix[row2] = temp;
    }

    public Matrix inverseGJ(Matrix M) {
        // Menghitung invers matriks dengan metode Gauss-Jordan

        // Buat matriks identitas
        int n = M.rowEff;
        Matrix identityMatrix = op.createIdentityMatrix(n);

        // Buat matriks augmented
        Matrix augmented = new Matrix(n, 2 * n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmented.matrix[i][j] = M.matrix[i][j];
                augmented.matrix[i][j + n] = identityMatrix.matrix[i][j];
            }
        }

        // Gauss-Jordan
        for (int i = 0; i < n; i++) {
            // Jika diagonal utama bernilai 0, cari baris di bawahnya untuk ditukar
            if (augmented.matrix[i][i] == 0) {
                for (int j = i + 1; j < n; j++) {
                    if (augmented.matrix[j][i] != 0) {
                        swapRow(augmented, i, j);
                        break;
                    }
                }
            }

            // Membuat elemen diagonal bernilai 1
            double diagonal = augmented.matrix[i][i];
            multiplyRowInv(augmented, i, 1 / diagonal);

            // Eliminasi kolom lainnya
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    double factor = augmented.matrix[j][i];
                    subtractRowInv(augmented, i, j, factor);
                }
            }
        }

        // Invers Matrix
        Matrix inverseMatrix = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverseMatrix.matrix[i][j] = augmented.matrix[i][j + n];
            }
        }
        op.errorRounding(inverseMatrix);

        return inverseMatrix;
    }

    public Matrix getAdj(Matrix M){
        // Menghitung matriks adjoin dari matrix M

        Matrix res = op.transposeMatrix(det.getCofactor(M));

        return res;
    }

    public Matrix inverseDet(Matrix M){
        // Menghitung invers matriks dengan metode determinan

        Matrix res = op.multiplyMatrixByScalar(getAdj(M), 1 / det.determinantCofactor(M));
        op.errorRounding(res);
        return res;
    }

    public void exportInverse(Matrix M, Matrix inverse)
    {
        // Menulis matriks ke file
        String filename;
        System.out.print("Masukkan nama file: ");
        filename = input.nextLine() + ".txt";

        // Menuliskan matriks ke layar
        int[] columnWidths = new int[M.colEff];
        int width = 0;

        for (int col = 0; col < M.colEff; col++) 
        {
            for (int row = 0; row < M.rowEff; row++) 
            {
                BigDecimal bd = new BigDecimal(M.matrix[row][col]).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros(); // Round to 5 decimal places
                width = (bd.toString()).length();
                
                if (width > columnWidths[col]) 
                {
                    columnWidths[col] = width;
                }
            }
        }

        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("../test/result/" + filename));
            
            writer.write("Matriks Awal: ");
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
                writer.write("Matriks tidak memiliki invers karena bukan matriks persegi.");
            }
            else if (det.determinantOBE(M) == 0)
            {
                writer.write("Matriks tidak memiliki invers karena determinan bernilai 0.");
            }
            else
            {
                int[] columnInvWidths = new int[inverse.colEff];
                width = 0;

                for (int col = 0; col < inverse.colEff; col++) 
                {
                    for (int row = 0; row < inverse.rowEff; row++) 
                    {
                        BigDecimal bdi = new BigDecimal(inverse.matrix[row][col]).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros(); // Round to 5 decimal places
                        width = (bdi.toString()).length();
                        
                        if (width > columnInvWidths[col]) 
                        {
                            columnInvWidths[col] = width;
                        }
                    }
                }

                writer.write("Matriks Invers-nya: ");
                writer.newLine();
    
                // Pretty Print :)
                for (int row = 0; row < inverse.rowEff; row++)
                {
                    if (inverse.rowEff == 1)
                    {
                        writer.write("[");
                    }
                    else if (row == 0)
                    {
                        writer.write("┌");
                    }
                    else if (row == inverse.rowEff - 1)
                    {
                        writer.write("└");
                    }
                    else
                    {
                        writer.write("|");
                    }
    
                    for (int col = 0; col < inverse.colEff; col++) {
                        BigDecimal bdi = new BigDecimal(inverse.matrix[row][col]).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros(); // Round to 5 decimal places
                        writer.write(String.format("%" + (columnInvWidths[col] + 2) + "s", bdi.toPlainString()));
                    }
    
                    if (inverse.rowEff == 1)
                    {
                        writer.write("  ]");
                    }
                    else if (row == 0)
                    {
                        writer.write("  ┐");
                    }
                    else if (row == inverse.rowEff - 1)
                    {
                        writer.write("  ┘");
                    }
                    else
                    {
                        writer.write("  |");
                    }
                    writer.newLine();
                }
            }
            writer.close();
        }

        catch (IOException e)
        {
            System.out.println("Terjadi kesalahan saat menulis file.");
        }
    }
}