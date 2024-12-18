//package setup;
package Methods;

// import from packages
import ADTMatrix.Matrix;
import ADTMatrix.Operation;

// import java packages
import java.lang.Math;
import java.util.Scanner;
import java.util.Locale;
import java.io.*;

public class PolynomialInterpolation {

    // Inisialisasi scanner
    public Scanner input = new Scanner(System.in);

    Operation op = new Operation();
    SPL spl = new SPL();

    public Matrix localInput() {

        // input derajat polinom
        System.out.print("Masukkan derajat polinom untuk interpolasi (n): ");
        int n = Integer.parseInt(input.nextLine());

        Matrix inputMatrix = new Matrix(n+2, 2);
        double a;
        
        System.out.print("Masukkan nilai x dan fx: \n");
        inputMatrix.readMatrix(n+1, 2);

        double minima = minimumX(inputMatrix);
        double maxima = maximumX(inputMatrix);

        while (true) {
            System.out.println(String.format(Locale.US, "Masukkan nilai a (dalam rentang %f dan %f, inklusif): ", minima, maxima));
            try {
                a = Double.parseDouble(input.nextLine());
                // Bersihkan buffer setelah membaca double

                if (a >= minima && a <= maxima) {
                    break; // Keluar dari loop jika input valid
                } else {
                    System.out.println(String.format(Locale.US, "Nilai a harus dalam rentang %f dan %f inklusif.\n", minima, maxima));
                }

            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Silakan masukkan angka desimal.");
            }
        }

        inputMatrix.matrix[n+1][0] = a;

        return inputMatrix;
    }

    public Matrix takeX(Matrix M) {

        // Fungsi untuk mengambil nilai x dari matriks input

        Matrix x = new Matrix(M.rowEff - 1, 1);
        for (int i = 0; i < x.rowEff; i++) {
            x.matrix[i][0] = M.matrix[i][0];
        }
        return x;
    }

    public double minimumX(Matrix M){
        // Fungsi untuk mendapatkan nilai minimum dari x
        double minima = M.matrix[0][0];

        for (int i = 1; i < M.rowEff; i++) {
            if (M.matrix[i][0] < minima){
                minima = M.matrix[i][0];
            }
        }
        return minima;
    }

    public double maximumX(Matrix M){
        // Fungsi untuk mendapatkan nilai maksimum dari x
        double maxima = M.matrix[0][0];

        for (int i = 1; i < M.rowEff; i++) {
            if (M.matrix[i][0] > maxima){
                maxima = M.matrix[i][0];
            }
        }
        return maxima;
    }

    public Matrix takeFx(Matrix M) {

        // Fungsi untuk mengambil nilai y dari matriks input
        Matrix fx = new Matrix(M.rowEff - 1, 1);
        for (int i = 0; i < fx.rowEff; i++) {
            fx.matrix[i][0] = M.matrix[i][1];
        }
        return fx;
    }

    public double takeXValue(Matrix M) {

        // Fungsi untuk mengambil nilai x yang akan dicari interpolasinya

        return M.matrix[M.rowEff][0];
    }

    public double takeXValueFile(Matrix M) {

        // Fungsi untuk mengambil nilai x yang akan dicari interpolasinya

        return M.matrix[M.rowEff - 1][0];
    }

    public Matrix xi(Matrix x) {
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

    public Matrix calculateCoeff(Matrix xi, Matrix fx) {

        // Menghitung koefisien polinom
        Matrix augmented = op.expandCol(xi, fx);
        Matrix gaussed = spl.gauss(augmented);
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

    public double calculateFx(Matrix f, double x) {

        // Menghitung nilai interpolasi f(x)

        double fx = 0;

        for (int i = 0; i < f.rowEff; i++) {
            fx += f.matrix[i][0] * Math.pow(x, i);
        }
        return fx;
    }

    public String showPolynom(Matrix f) {

        // Menampilkan polinom dalam bentuk string
        String fx = new String("f(x) = ");
        boolean first = true;

        for (int i = 0; i < f.rowEff; i++) {
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

    public void interpolationFile(Matrix M) {
        // Menyimpan hasil interpolasi ke dalam file

        String filename;
        System.out.print("\nMasukkan nama file: ");
        filename = input.nextLine() + ".txt";

        try {            
            BufferedWriter writer = new BufferedWriter(new FileWriter("../test/result/" + filename));

            Matrix x = takeX(M);
            Matrix fx = takeFx(M);
            double xint = takeXValue(M);

            // Membangun matriks xi berdasarkan nilai x
            Matrix xi = xi(x);

            // Menghitung koefisien polinom cf
            Matrix cf = calculateCoeff(xi, fx);

            // Menghitung f(x) pada titik yang diinterpolasi
            double hasilInterpolasi = calculateFx(cf, xint);

            // Menampilkan polinom dalam bentuk string
            writer.write("f(x) = ");
            boolean first = true;

            for (int i = 0; i < cf.rowEff; i++) {
                double coeff = cf.matrix[i][0];

                if (coeff != 0) {
                    if (!first) {
                        if (coeff > 0) {
                            writer.write(" + ");
                        } else {
                            writer.write(" - ");
                        }
                    } else if (coeff < 0) {
                        writer.write(" - ");
                    }
                    first = false;

                    if (Math.abs(coeff) != 1 || i == 0) {
                        writer.write(String.format("%.10f",Math.abs(coeff)));
                    }

                    if (i > 0) {
                        if (i == 1) {
                            writer.write("x");
                        } else {
                            writer.write("x^" + (i));
                        }
                    }
                }
            }

            if (first) {
                writer.write(" 0");
            }

            // Menampilkan hasil interpolasi


            writer.newLine();
            writer.write("Nilai f(" + xint + ") = " + hasilInterpolasi);

            writer.flush();
            writer.close();
        }

        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}