package Methods;

import ADTMatrix.Matrix;
import ADTMatrix.Operation;

public class Determinant {
    
    public  double determinantCofactor(Matrix M) 
    {
        /* Menghitung determinan matriks dengan metode kofaktor */
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
        /* Menghitung matriks kofaktor dari matrix M */
        Matrix kofaktor = new Matrix(M.rowEff, M.colEff);

        for(int i = 0 ; i < M.rowEff;i++)
        {
            for(int j = 0 ; j < M.colEff;j++)
            {
                kofaktor.matrix[i][j] = Math.pow(-1, i+j) * determinantCofactor(Operation.getMinor(M, i, j));
            }
        }
        return kofaktor;
    }

}
