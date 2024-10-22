//package setup;
package Methods;

// import packages
import ADTMatrix.Matrix;
import ADTMatrix.Operation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
// import java packages
import java.lang.Math;
import java.util.Scanner;

public class Regresi {
    Operation op = new Operation();
    static Scanner input = new Scanner(System.in);

    public Matrix inputLinearRegression() {
        System.out.print("Masukkan jumlah parameter (y tidak dihitung): ");
        int col = Integer.parseInt(input.nextLine());

        System.out.print("Masukkan jumlah data regresi: ");
        int row = Integer.parseInt(input.nextLine());

        Matrix inputMatrix = new Matrix(row + 1, col + 1);

        for (int i = 0; i < inputMatrix.rowEff; i++) {
            for (int j = 0; j < inputMatrix.colEff; j++) {
                if (i != inputMatrix.rowEff - 1) {
                    if (j != inputMatrix.colEff - 1) {
                        System.out.print("Masukkan nilai x" + (j + 1) + " data ke-" + (i + 1) + ": ");
                    } else {
                        System.out.print("Masukkan nilai y data ke-" + (i + 1) + ": ");
                    }
                    inputMatrix.matrix[i][j] = Double.parseDouble(input.nextLine());
                } else {
                    if (j != inputMatrix.colEff - 1) {
                        System.out.print("Masukkan nilai x" + (j + 1) + " yang akan diregresi: ");
                        inputMatrix.matrix[i][j] = Double.parseDouble(input.nextLine());
                    }
                }
            }
        }
        inputMatrix.matrix[inputMatrix.rowEff - 1][inputMatrix.colEff - 1] = -999.0; // MARK
        return inputMatrix;
    }

    public Matrix inputQuadraticRegression() {
        System.out.print("Masukkan jumlah parameter (y tidak dihitung): ");
        int col = Integer.parseInt(input.nextLine());

        System.out.print("Masukkan jumlah data regresi: ");
        int row = Integer.parseInt(input.nextLine());

        // Matrix size for quadratic terms
        Matrix inputMatrix = new Matrix(row + 1, 2 * col + 1);

        for (int i = 0; i < inputMatrix.rowEff - 1; i++) {
            // Input original x values and calculate quadratic terms
            for (int j = 0; j < col; j++) {
                System.out.print("Masukkan nilai x" + (j + 1) + " data ke-" + (i + 1) + ": ");
                double value = Double.parseDouble(input.nextLine());
                inputMatrix.matrix[i][j] = value;
                inputMatrix.matrix[i][j + col] = value * value;
            }
            // Input y value
            System.out.print("Masukkan nilai y data ke-" + (i + 1) + ": ");
            inputMatrix.matrix[i][2 * col] = Double.parseDouble(input.nextLine());
        }

        // Input values for prediction
        for (int j = 0; j < col; j++) {
            System.out.print("Masukkan nilai x" + (j + 1) + " yang akan diregresi: ");
            double value = Double.parseDouble(input.nextLine());
            inputMatrix.matrix[row][j] = value;
            inputMatrix.matrix[row][j + col] = value * value;
        }
        inputMatrix.matrix[row][2 * col] = -999.0; // MARK
        return inputMatrix;
    }

    public Matrix regressionGJ(Matrix augmented) {
        int n = augmented.rowEff;

        for (int i = 0; i < n; i++) {
            // Normalize diagonal element to 1
            double pivot = augmented.matrix[i][i];
            if (pivot != 0) {
                for (int j = 0; j < augmented.colEff; j++) {
                    augmented.matrix[i][j] /= pivot;
                }
            }

            // Eliminate elements above and below pivot
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = augmented.matrix[k][i];
                    for (int j = 0; j < augmented.colEff; j++) {
                        augmented.matrix[k][j] -= factor * augmented.matrix[i][j];
                    }
                }
            }
        }
        return augmented;
    }

    public double calcRow(Matrix M, int s, int rowReg, int colReg) {
        double countRow = 0;
        for (int i = 0; i < s - 1; i++) {
            if (rowReg == 0) {
                countRow += M.matrix[colReg - 1][i];
            } else {
                countRow += M.matrix[rowReg - 1][i] * M.matrix[colReg - 1][i];
            }
        }
        return countRow;
    }

    public double calcFxLinear(Matrix a, Matrix m) {
        double fx = a.matrix[0][a.colEff - 1];
        for (int i = 1; i < a.rowEff; i++) {
            fx += a.matrix[i][a.colEff - 1] * m.matrix[m.rowEff - 1][i - 1];
        }
        return fx;
    }

    public double calcFxQuadratic(Matrix a, Matrix m) {
        double fx = a.matrix[0][a.colEff - 1]; // constant term
        int numOriginalVars = (m.colEff - 1) / 2;

        // Linear terms
        for (int i = 1; i <= numOriginalVars; i++) {
            fx += a.matrix[i][a.colEff - 1] * m.matrix[m.rowEff - 1][i - 1];
        }

        // Quadratic terms
        for (int i = numOriginalVars + 1; i < a.rowEff; i++) {
            fx += a.matrix[i][a.colEff - 1] * m.matrix[m.rowEff - 1][i - 1];
        }

        return fx;
    }

    public Matrix normalize(Matrix m) {
        Matrix m1 = op.transposeMatrix(m);
        Matrix norm = new Matrix(m.colEff, m.colEff + 1);

        // First element
        norm.matrix[0][0] = m.rowEff - 1;

        // Process first row separately
        for (int j = 1; j < norm.colEff; j++) {
            norm.matrix[0][j] = calcRow(m1, m.rowEff, 0, j);
        }

        // Process remaining rows and columns
        for (int i = 1; i < norm.rowEff; i++) {
            norm.matrix[i][0] = norm.matrix[0][i];
            for (int j = 1; j < norm.colEff; j++) {
                norm.matrix[i][j] = calcRow(m1, m.rowEff, i, j);
            }
        }

        return norm;
    }
    public void linearRegression(Matrix M) 
    {
        Matrix norm = normalize(M);
        Matrix a = regressionGJ(norm);
        String equation = generateLinearEquation(a);
        double fx = calcFxLinear(a, M);

        // Output to console
        M.writeMatrix();
        System.out.println();
        System.out.println("Persamaan regresi linear: " + equation);
        System.out.println("Hasil taksiran f(x): " + fx);
    }

    public void linearRegressionFile(Matrix M) {

        System.out.print("\nMasukkan nama file: ");
        String filename = input.nextLine() + ".txt";
        try {
            Matrix norm = normalize(M);
            Matrix a = regressionGJ(norm);
            String equation = generateLinearEquation(a);
            double fx = calcFxLinear(a, M);

            String userDirectory = System.getProperty("user.dir");
            BufferedWriter writer = new BufferedWriter(new FileWriter(userDirectory + "/test/result/" + filename));
            
            // Write input data
            writer.write("Data Input:\n");

            for (int row = 0; row < M.rowEff; row++)
            {
                for (int col = 0; col < M.colEff; col++)
                {
                    writer.write(M.matrix[row][col] + " ");
                }
                writer.newLine();
            }
            writer.newLine();

            writer.write("Persamaan regresi linear: " + equation + "\n");
            writer.write("Hasil taksiran f(x): " + fx + "\n\n");
            
            writer.close();
            System.out.println("File berhasil dibuat!");

        } catch (IOException e) {
            System.out.println("Terjadi error dalam pembuatan file!");
            e.printStackTrace();
        }
    }

    private String generateLinearEquation(Matrix a) {
        StringBuilder output = new StringBuilder("f(x) = ");
        for (int i = 0; i < a.rowEff; i++) {
            double coefficient = a.matrix[i][a.colEff - 1];
            if (i == 0) {
                output.append(String.format("%.10f", coefficient));
            } else {
                if (coefficient >= 0) {
                    output.append(" + ");
                } else {
                    output.append(" - ");
                }
                output.append(String.format("%.10f", Math.abs(coefficient))).append("x").append(i);
            }
        }
        return output.toString();
    }

    public void quadraticRegression(Matrix M) {
        Matrix norm = normalize(M);
        Matrix a = regressionGJ(norm);
        String equation = generateQuadraticEquation(a, M);
        double fx = calcFxQuadratic(a, M);

        // Output to console
        M.writeMatrix();
        System.out.println();
        System.out.println("Persamaan regresi linear: " + equation);
        System.out.println("Hasil taksiran f(x): " + fx);
    }

    public void quadraticRegressionFile(Matrix M) {
        System.out.print("\nMasukkan nama file: ");
        String filename = input.nextLine() + ".txt";
        try {
            Matrix norm = normalize(M);
            Matrix a = regressionGJ(norm);
            String equation = generateQuadraticEquation(a, M);
            double fx = calcFxQuadratic(a, M);

            String userDirectory = System.getProperty("user.dir");
            BufferedWriter writer = new BufferedWriter(new FileWriter(userDirectory + "/test/result/" + filename));

            // Write input data
            writer.write("Data Input:\n");

            for (int row = 0; row < M.rowEff; row++)
            {
                for (int col = 0; col < M.colEff; col++)
                {
                    writer.write(M.matrix[row][col] + " ");
                }
                writer.newLine();
            }

            // Write equation and result
            writer.write("Persamaan regresi kuadratik: " + equation + "\n");
            writer.write("Hasil taksiran f(x): " + fx + "\n\n");
            
            writer.close();

            System.out.println("File berhasil dibuat!");
        } 
        catch (IOException e) 
        {
            System.out.println("Terjadi error dalam pembuatan file!");
        }
    }

    private String generateQuadraticEquation(Matrix a, Matrix M) {
        StringBuilder output = new StringBuilder("f(x) = ");
        int numOriginalVars = (M.colEff - 1) / 2;

        // Constant term
        output.append(String.format("%.10f", a.matrix[0][a.colEff - 1]));

        // Linear terms for all variables (x1, x2, x3, etc.)
        for (int i = 1; i <= numOriginalVars; i++) {
            double coef = a.matrix[i][a.colEff - 1];
            if (coef >= 0) {
                output.append(" + ");
            } else {
                output.append(" - ");
            }
            output.append(String.format("%.10f", Math.abs(coef))).append("x").append(i);
        }

        // Quadratic terms for all variables (x1², x2², x3², etc.)
        for (int i = 1; i <= numOriginalVars; i++) {
            double coef = a.matrix[i + numOriginalVars][a.colEff - 1];
            if (coef >= 0) {
                output.append(" + ");
            } else {
                output.append(" - ");
            }
            output.append(String.format("%.10f", Math.abs(coef))).append("x").append(i).append("²");
        }

        // Cross-product terms (x1x2, x1x3, x2x3, etc.)
        for (int i = 1; i <= numOriginalVars; i++) {
            for (int j = i + 1; j <= numOriginalVars; j++) {
                double coef = a.matrix[i + j + numOriginalVars - 1][a.colEff - 1];
                if (coef >= 0) {
                    output.append(" + ");
                } else {
                    output.append(" - ");
                }
                output.append(String.format("%.10f", Math.abs(coef)))
                    .append("x").append(i)
                    .append("x").append(j);
            }
        }

        return output.toString();
    }

    public void main(String[] args) {
        // Matrix M = new Matrix(21, 4);
        // M.matrix[0][0] = 72.4;
        // M.matrix[0][1] = 76.3;
        // M.matrix[0][2] = 29.18;
        // M.matrix[0][3] = 0.9;
        // M.matrix[1][0] = 41.6;
        // M.matrix[1][1] = 70.3;
        // M.matrix[1][2] = 29.35;
        // M.matrix[1][3] = 0.91;
        // M.matrix[2][0] = 34.3;
        // M.matrix[2][1] = 77.1;
        // M.matrix[2][2] = 29.24;
        // M.matrix[2][3] = 0.96;
        // M.matrix[3][0] = 35.1;
        // M.matrix[3][1] = 68.0;
        // M.matrix[3][2] = 29.27;
        // M.matrix[3][3] = 0.89;
        // M.matrix[4][0] = 10.7;
        // M.matrix[4][1] = 79.0;
        // M.matrix[4][2] = 29.78;
        // M.matrix[4][3] = 1.00;
        // M.matrix[5][0] = 12.9;
        // M.matrix[5][1] = 67.4;
        // M.matrix[5][2] = 29.39;
        // M.matrix[5][3] = 1.10;
        // M.matrix[6][0] = 8.3;
        // M.matrix[6][1] = 66.8;
        // M.matrix[6][2] = 29.69;
        // M.matrix[6][3] = 1.15;
        // M.matrix[7][0] = 20.1;
        // M.matrix[7][1] = 76.9;
        // M.matrix[7][2] = 29.48;
        // M.matrix[7][3] = 1.03;
        // M.matrix[8][0] = 72.2;
        // M.matrix[8][1] = 77.7;
        // M.matrix[8][2] = 29.09;
        // M.matrix[8][3] = 0.77;
        // M.matrix[9][0] = 24.0;
        // M.matrix[9][1] = 67.7;
        // M.matrix[9][2] = 29.60;
        // M.matrix[9][3] = 1.07;
        // M.matrix[10][0] = 23.2;
        // M.matrix[10][1] = 76.8;
        // M.matrix[10][2] = 29.38;
        // M.matrix[10][3] = 1.07;
        // M.matrix[11][0] = 47.4;
        // M.matrix[11][1] = 86.6;
        // M.matrix[11][2] = 29.35;
        // M.matrix[11][3] = 0.94;
        // M.matrix[12][0] = 31.5;
        // M.matrix[12][1] = 76.9;
        // M.matrix[12][2] = 29.63;
        // M.matrix[12][3] = 1.10;
        // M.matrix[13][0] = 10.6;
        // M.matrix[13][1] = 86.3;
        // M.matrix[13][2] = 29.56;
        // M.matrix[13][3] = 1.10;
        // M.matrix[14][0] = 11.2;
        // M.matrix[14][1] = 86.0;
        // M.matrix[14][2] = 29.48;
        // M.matrix[14][3] = 1.10;
        // M.matrix[15][0] = 73.3;
        // M.matrix[15][1] = 76.3;
        // M.matrix[15][2] = 29.40;
        // M.matrix[15][3] = 0.91;
        // M.matrix[16][0] = 75.4;
        // M.matrix[16][1] = 77.9;
        // M.matrix[16][2] = 29.28;
        // M.matrix[16][3] = 0.87;
        // M.matrix[17][0] = 96.6;
        // M.matrix[17][1] = 78.7;
        // M.matrix[17][2] = 29.29;
        // M.matrix[17][3] = 0.78;
        // M.matrix[18][0] = 107.4;
        // M.matrix[18][1] = 86.8;
        // M.matrix[18][2] = 29.03;
        // M.matrix[18][3] = 0.82;
        // M.matrix[19][0] = 54.9;
        // M.matrix[19][1] = 70.9;
        // M.matrix[19][2] = 29.37;
        // M.matrix[19][3] = 0.95;
        // M.matrix[20][0] = 50;
        // M.matrix[20][1] = 76;
        // M.matrix[20][2] = 29.30;
        // M.matrix[20][3] = -999.0;

        // Matrix inputMatrix = mtxfromkeyboard(); // Menerima input dari pengguna
        // quadraticRegression(M); // Menyelesaikan dan mencetak hasil regresi
        Matrix M = new Matrix();
        Scanner in = new Scanner(System.in);
        String filepath, filename = in.nextLine(), userDirectory = System.getProperty("user.dir");
        filepath = userDirectory + "/test/case/" + filename + ".txt";
        in.close();
        M.importMatrixWithEmpty(filepath, 1);
        M.writeMatrix();
        linearRegression(M);
        quadraticRegression(M);
    }
    
}