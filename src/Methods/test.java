
//package setup;
package Methods;

// import from packages
import ADTMatrix.Matrix;

// import java packages
import java.lang.Math;
import java.util.Scanner;

public class test {

    // Inisialisasi scanner
    static Scanner input = new Scanner(System.in);

    public Matrix localInput() {

        // input derajat polinom
        System.out.print("Masukkan derajat polinom untuk interpolasi (n): ");
        int n = Integer.parseInt(input.nextLine());

        // Inisialisasi matriks untuk titik-titik dan nilai x yang akan diinterpolasi
        Matrix inputMatrix = new Matrix(n + 2, 2);

        // Input nilai titik (x dan y)
        for (int i = 0; i < n + 1; i++) {
            System.out.print("Masukkan nilai x titik ke-" + i + ": ");
            inputMatrix.matrix[i][0] = Double.parseDouble(input.nextLine());
            System.out.print("Masukkan nilai y titik ke-" + i + ": ");
            inputMatrix.matrix[i][1] = Double.parseDouble(input.nextLine());
        }

        // Input nilai x yang akan diinterpolasi
        System.out.print("Masukkan nilai x yang akan diinterpolasi: ");
        inputMatrix.matrix[n + 1][0] = Double.parseDouble(input.nextLine());
        inputMatrix.matrix[n + 1][1] = -999.0;  // MARK

        return inputMatrix;
    }

    public static Matrix takeX(Matrix M) {

        // Fungsi untuk mengambil nilai x dari matriks input

        Matrix x = new Matrix(M.rowEff - 1, 1);
        for (int i = 0; i < x.rowEff; i++) {
            x.matrix[i][0] = M.matrix[i][0];
        }
        return x;
    }

    public static Matrix takeFx(Matrix M) {

        // Fungsi untuk mengambil nilai y dari matriks input
        Matrix fx = new Matrix(M.rowEff - 1, 1);
        for (int i = 0; i < fx.rowEff; i++) {
            fx.matrix[i][0] = M.matrix[i][1];
        }
        return fx;
    }

    public static double takeXValue(Matrix M) {

        // Fungsi untuk mengambil nilai x yang akan dicari interpolasinya

        return M.matrix[M.rowEff - 1][0];
    }

    public static Matrix xi(Matrix x) {
        // Membuat matriks xi untuk polinom interpolasi

        // Matriks koefisien
        Matrix xi = new Matrix(x.rowEff, x.rowEff);
        for (int i = 0; i < xi.rowEff; i++) {
            for (int j = 0; j < xi.colEff; j++) {
                xi.matrix[i][j] = Math.pow(x.matrix[i][0], j);
            }
        }
        return xi;
    }

    public static Matrix gauss(Matrix augmented) {

        // Implementasi eliminasi Gauss untuk sistem persamaan linear
        int n = augmented.rowEff;

        for (int i = 0; i < n; i++) {

            // Pivotisasi: mencari elemen diagonal utama terbesar
            int maxRow = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(augmented.matrix[k][i]) > Math.abs(augmented.matrix[maxRow][i])) {
                    maxRow = k;
                }
            }

            // Tukar baris
            double[] temp = augmented.matrix[i];
            augmented.matrix[i] = augmented.matrix[maxRow];
            augmented.matrix[maxRow] = temp;

            // Eliminasi
            for (int k = i + 1; k < n; k++) {
                double factor = augmented.matrix[k][i] / augmented.matrix[i][i];
                for (int j = i; j < augmented.colEff; j++) {
                    augmented.matrix[k][j] -= factor * augmented.matrix[i][j];
                }
            }
        }

        return augmented;
    }

    public static Matrix concatCol(Matrix M1, Matrix M2) {

        // Menggabungkan dua matriks menjadi matriks augmented
        if (M1.rowEff != M2.rowEff) {
            throw new IllegalArgumentException("Jumlah baris kedua matriks harus sama.");
        }

        Matrix result = new Matrix(M1.rowEff, M1.colEff + M2.colEff);
        for (int i = 0; i < M1.rowEff; i++) {

            // Salin matriks pertama
            for (int j = 0; j < M1.colEff; j++) {
                result.matrix[i][j] = M1.matrix[i][j];
            }

            // Salin matriks kedua
            for (int j = 0; j < M2.colEff; j++) {
                result.matrix[i][j + M1.colEff] = M2.matrix[i][j];
            }
        }
        return result;
    }

    public static Matrix calculateCoeff(Matrix xi, Matrix fx) {

        // Menghitung koefisien polinom
        Matrix augmented = concatCol(xi, fx);
        Matrix gaussed = gauss(augmented);
        Matrix cf = new Matrix(fx.rowEff, 1);

        for (int i = gaussed.rowEff - 1; i >= 0; i--) {
            double sum = gaussed.matrix[i][gaussed.colEff - 1];
            for (int j = i + 1; j < gaussed.rowEff; j++) {
                sum -= gaussed.matrix[i][j] * cf.matrix[j][0];
            }
            cf.matrix[i][0] = sum / gaussed.matrix[i][i];
        }

        return cf;
    }

    public static double calculateFx(Matrix f, double x) {

        // Menghitung nilai interpolasi f(x)

        double fx = 0;

        for (int i = 0; i < f.rowEff; i++) {
            fx += f.matrix[i][0] * Math.pow(x, i);
        }
        return fx;
    }


    public static String showPolynom(Matrix f) {

        // Menampilkan polinom dalam bentuk string
        String fx = new String("f(x) = ");
        boolean first = true;

        for (int i = f.rowEff - 1; i >= 0; i--) {
            double coeff = f.matrix[i][0];

            if (coeff != 0) {
                if (!first) {
                    if (coeff > 0) {
                        fx += " + ";
                    } else {
                        fx += " - ";
                    }
                } else if (coeff < 0) {
                    fx += " - ";
                }
                first = false;

                if (Math.abs(coeff) != 1 || i == 0) {
                    fx += Math.abs(coeff);
                }

                if (i > 0) {
                    if (i == 1) {
                        fx += "x";
                    } else {
                        fx += "x^";
                        fx += i;
                    }
                }
            }
        }

        if (first) {
            fx += " 0";
        }

        return fx.toString();
    }


    // Ngetes hasil interpolasi
    public static void main(String[] args) {
        Matrix M = new Matrix();
        /*
        M.matrix[0][0] = 6.567;
        M.matrix[0][1] = 12.624;
        M.matrix[1][0] = 7;
        M.matrix[1][1] = 21.807;
        M.matrix[2][0] = 7.258;
        M.matrix[2][1] = 38.391;
        M.matrix[3][0] = 7.451;
        M.matrix[3][1] = 54.517;
        M.matrix[4][0] = 7.548;
        M.matrix[4][1] = 51.952;
        M.matrix[5][0] = 7.839;
        M.matrix[5][1] = 28.228;
        M.matrix[6][0] = 8.161;
        M.matrix[6][1] = 35.764;
        M.matrix[7][0] = 8.484;
        M.matrix[7][1] = 20.813;
        M.matrix[8][0] = 8.709;
        M.matrix[8][1] = 12.408;
        M.matrix[9][0] = 9;
        M.matrix[9][1] = 10.534;
        M.matrix[10][0] = 9.166;
        M.matrix[10][1] = -999.0;
        */


        // M.matrix[0][0] = 0.1;
        // M.matrix[0][1] = 0.003;
        // M.matrix[1][0] = 0.3;
        // M.matrix[1][1] = 0.0067;
        // M.matrix[2][0] = 0.5;
        // M.matrix[2][1] = 0.148;
        // M.matrix[3][0] = 0.7;
        // M.matrix[3][1] = 0.248;
        // M.matrix[4][0] = 0.9;
        // M.matrix[4][1] = 0.370;
        // M.matrix[5][0] = 1.1;
        // M.matrix[5][1] = 0.518;
        // M.matrix[6][0] = 1.3;
        // M.matrix[6][1] = 0.697;
        // M.matrix[7][0] = 0.2;
        // M.matrix[7][1] = -999.0;

        /*
        M.matrix[0][0] = -3;
        M.matrix[0][1] = 2;
        M.matrix[1][0] = -2;
        M.matrix[1][1] = 3;
        M.matrix[2][0] = -1;
        M.matrix[2][1] = 5;
        M.matrix[3][0] = -4;
        M.matrix[3][1] = 0;
        M.matrix[4][0] = -2.5;
        M.matrix[4][1] = -999.0;
        */
        // Mengambil nilai x dan f(x) dari inputMatrix
        // Matrix M = localInput();
        System.out.print("\nMengambil file dari folder test.");
        System.out.print("\nNama file: ");

        String filepath, filename = input.nextLine(), userDirectory = System.getProperty("user.dir");
        filepath = userDirectory + "/test/case/" + filename + ".txt";

        M.importMatrixWithEmpty(filepath, 1);
        M.writeMatrix();

        Matrix x = takeX(M);
        Matrix fx = takeFx(M);

        // Mendapatkan nilai x yang akan diinterpolasi
        double xInterpolasi = takeXValue(M);

        // Membangun matriks xi berdasarkan nilai x
        Matrix xi = xi(x);

        // Menghitung koefisien polinom cf
        Matrix cf = calculateCoeff(xi, fx);

        // Menghitung f(x) pada titik yang diinterpolasi
        double hasilInterpolasi = calculateFx(cf, xInterpolasi);

        // Menampilkan polinom yang terbentuk
        String polinom = showPolynom(cf);
        System.out.println(polinom);

        // Menampilkan hasil interpolasi
        System.out.println("Nilai f(" + xInterpolasi + ") = " + hasilInterpolasi);

    }
}