// Package set-up
package Methods;

// import from packages
import ADTMatrix.Matrix;
import ADTMatrix.Operation;
import Methods.SPL;
import Methods.Determinant;

public class Inverse {

    // Selektor
    Operation op = new Operation();
    Determinant det = new Determinant();
    SPL spl = new SPL();

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
                        op.swapRows(augmented, i, j);
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

        Matrix res = op.MultiplyMatrixByScalar(getAdj(M), 1/ det.determinantCofactor(M));
        op.errorRounding(res);
        return res;
    }
}