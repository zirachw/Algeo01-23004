// Package set-up
package Methods;

// import from packages
import ADTMatrix.Matrix;
import ADTMatrix.Operation;


public class Inverse {

    // Selektor
    Operation op = new Operation();
    Determinan det = new Determinan();

    public void swapRows(Matrix M, int row1, int row2) {
        // Menukar dua baris pada matriks

        double[] temp = M.matrix[row1];
        M.matrix[row1] = M.matrix[row2];
        M.matrix[row2] = temp;
    }

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

    public void multiplyRow(Matrix M, int row, double factor) {
        // Mengalikan baris dengan suatu faktor

        for (int j = 0; j < M.colEff; j++) {
            M.matrix[row][j] *= factor;
        }
    }

    public void subtractRows(Matrix M, int rowToSubtract, int targetRow, double factor) {
        // Mengurangi satu baris dengan baris lainnya

        for (int j = 0; j < M.colEff; j++) {
            M.matrix[targetRow][j] -= factor * M.matrix[rowToSubtract][j];
        }
    }
    public boolean checkInverse(Matrix M){
        // Mengecek apakah matriks memiliki invers

        // Bukan matrix persegi
        if (!op.isSquareMatrix(M)) {
            return false;
        }

        // Determinan matriks 0
        if (det.determinanCofactor(M) == 0) {
            return false;
        }

        // Matriks memiliki baris yang semuanya nol
        for (int i = 0; i < M.rowEff; i++){
            if (isZeroRow(M.matrix[i])) {
                return false;
            }
        }

        // Matriks memiliki kolom yang semuanya nol
        for (int j = 0; j < M.colEff; j++){
            if (isZeroColumn(M)) {
                return false;
            }
        }
        return true;
    }
    public void errorRounding(Matrix M){
        // Melakukan pembulatan galat pada matriks

        for(int i =0; i < M.rowEff; i++){
            for(int j = 0; j < M.colEff; j++){
                if (M.matrix[i][j] > 0.99999999999 && M.matrix[i][j] < 1.00000000001){
                    M.matrix[i][j] = 1;
                } else if (M.matrix[i][j] > -0.00000000001 && M.matrix[i][j] < 0.00000000001){
                    M.matrix[i][j] = 0;
                }
            }
        }
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
                        swapRows(augmented, i, j);
                        break;
                    }
                }
            }

            // Membuat elemen diagonal bernilai 1
            double diagonal = augmented.matrix[i][i];
            multiplyRow(augmented, i, 1 / diagonal);

            // Eliminasi kolom lainnya
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    double factor = augmented.matrix[j][i];
                    subtractRows(augmented, i, j, factor);
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
        errorRounding(inverseMatrix);

        return inverseMatrix;
    }

    public Matrix getAdj(Matrix M){
        // Menghitung matriks adjoin dari matrix M

        Matrix res = op.transposeMatrix(det.getKofaktor(M));

        return res;
    }

    public Matrix scalarMultiple(Matrix M, double scalar){
        // Mengalikan matriks dengan nilai skalar

        for (int i = 0; i < M.rowEff; i++){
            for (int j = 0; j < M.colEff; j++){
                M.matrix[i][j] *= scalar;
            }
        }
        return M;
    }

    public Matrix inverseDet(Matrix M){
        // Menghitung invers matriks dengan metode determinan

        Matrix res = scalarMultiple(getAdj(M), 1/ det.determinanCofactor(M));
        errorRounding(res);
        return res;
    }
}