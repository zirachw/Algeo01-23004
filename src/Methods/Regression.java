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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class Regression {
    Inverse inv = new Inverse();
    Determinant det = new Determinant();
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

    public Matrix QuadraticRegression(Matrix M) {
        
        int m = M.rowEff - 1;
        int n = M.colEff - 1;
        
        Matrix X = new Matrix(m, ((n * n) + (3 * n) + 2) / 2);
        Matrix Y = new Matrix(m, 1);
        Matrix y = new Matrix(n, 1);

        for (int i = 0; i < m; i++) {

            X.matrix[i][0] = 1;

            for (int j = 0; j < n; j++) 
            {
                X.matrix[i][j + 1] = M.matrix[i][j];
                X.matrix[i][j + n + 1] = M.matrix[i][j] * M.matrix[i][j];
            }

            int index = 2 * n + 1;
            for (int j = 1; j <= n; j++)
            {
                for (int k = j + 1; k <= n; k++)
                {
                    X.matrix[i][index] = X.matrix[i][j] * X.matrix[i][k];
                    index++;
                }
            
            Y.matrix[i][0] = M.matrix[i][n];
            }
        }

        for (int i = 0; i < n; i++) 
        {
            y.matrix[i][0] = M.matrix[M.rowEff - 1][i];
        }

        Matrix XT = op.transposeMatrix(X);
        Matrix XTX = op.multiplyMatrix(XT, X);

        if (det.determinantOBE(XTX) == 0)
        {
            throw new IllegalArgumentException("Matrix XTX is singular");
        }

        Matrix XTXI = inv.inverseGJ(XTX);
        Matrix XTXIXT = op.multiplyMatrix(XTXI, XT);
        Matrix beta = op.multiplyMatrix(XTXIXT, Y);


        return beta;
    }

    public void calcFxQuadratic(Matrix beta, Matrix y)
    {
        int n = y.rowEff;
        System.out.print("Persamaan regresi kuadratik: f(x):");
        System.out.println();

        for (int i = 0; i < beta.rowEff; i++)
        {

            if (i == 0)
                if (beta.matrix[i][0] >= 0) System.out.print(new BigDecimal(beta.matrix[i][0]).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros());
                else System.out.print(" - " + (new BigDecimal(beta.matrix[i][0]).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros().abs()));
            else if (i <= n) 
                if (beta.matrix[i][0] >= 0) System.out.print(" + " + new BigDecimal(beta.matrix[i][0]).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros() + "x" + i);
                else System.out.print(" - " + new BigDecimal(beta.matrix[i][0]).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros().abs() + "x" + i);
            else if (i <= 2 * n)
                if (beta.matrix[i][0] >= 0) System.out.print(" + " + new BigDecimal(beta.matrix[i][0]).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros() + "x" + (i - n) + "^2");
                else System.out.print(" - " + new BigDecimal(beta.matrix[i][0]).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros().abs() + "x" + (i - n) + "^2");
            else
            {
                for (int j = 1; j <= n; j++)
                {
                    for (int k = j + 1; k <= n; k++)
                    {
                        if (beta.matrix[i][0] >= 0) System.out.print(" + " + new BigDecimal(beta.matrix[i][0]).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros() + "x" + j + "x" + k);
                        else System.out.print(" - " + new BigDecimal(beta.matrix[i][0]).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros().abs() + "x" + j + "x" + k);
                        i++;
                    }
                }
            }
        }

        double fy = 0;

        // Iterate through each effective row in the beta matrix
        for (int i = 0; i < beta.rowEff; i++) {
        
            // First element case
            if (i == 0) {
                fy += beta.matrix[i][0];  // Add first beta value
            }
            // Linear terms
            else if (i <= n) {
                fy += beta.matrix[i][0] * y.matrix[i - 1][0];  // Linear contribution from y
            }
            // Quadratic terms (single variable)
            else if (i <= 2 * n) {
                fy += beta.matrix[i][0] * y.matrix[i - n - 1][0] * y.matrix[i - n - 1][0];  // Quadratic contribution from y
            }
            // Interaction terms
            else {
                int index = 2 * n + 1;  // Initialize index for interactions
                for (int j = 1; j <= n; j++) {
                    for (int k = j + 1; k <= n; k++) {
                        // Ensure you are accessing the correct matrix entries
                        fy += beta.matrix[index][0] * y.matrix[j - 1][0] * y.matrix[k - 1][0];  // Interaction contribution
                        index++;  // Increment index for the next beta matrix row
                    }
                }
                // Remove the break if you expect to continue looping
                break;
            }
        }
        
        // At this point, fy should contain the computed result
        
        System.out.println();

        String predict = "";
        for (int i = 0; i < y.rowEff; i++)
        {
            if (i == y.rowEff - 1) predict += (y.matrix[i][0]);
            else predict += y.matrix[i][0] + ", ";
        }
        BigDecimal bdfy = new BigDecimal(fy).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros();
        System.out.println("\nHasil taksiran f(" + predict + "): " + bdfy);
    }

    public void quadraticRegressionFile(Matrix M, Matrix y) 
    {
        System.out.print("\nMasukkan nama file: ");
        String filename = input.nextLine() + ".txt";

        int n = M.colEff - 1;
        Matrix beta = QuadraticRegression(M);

        try {
            String userDirectory = System.getProperty("user.dir");
            BufferedWriter writer = new BufferedWriter(new FileWriter(userDirectory + "/test/result/" + filename));
            
            writer.write("Persamaan regresi kuadratik: f(x):");
            writer.newLine();

            for (int i = 0; i < beta.rowEff; i++)
            {
                if (i == 0)
                    if (beta.matrix[i][0] >= 0) writer.write((new BigDecimal(beta.matrix[i][0]).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros()).toPlainString());
                    else writer.write("- " + (new BigDecimal(beta.matrix[i][0]).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros()).abs().toPlainString());
                else if (i <= n) 
                    if (beta.matrix[i][0] >= 0) writer.write(" + " + new BigDecimal(beta.matrix[i][0]).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString() + "x" + i);
                    else writer.write(" - " + (new BigDecimal(beta.matrix[i][0]).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros()).abs().toPlainString() + "x" + i);
                else if (i <= 2 * n)
                    if (beta.matrix[i][0] >= 0) writer.write(" + " + new BigDecimal(beta.matrix[i][0]).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString() + "x" + (i - n) + "^2");
                    else writer.write(" - " + (new BigDecimal(beta.matrix[i][0]).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros()).abs().toPlainString() + "x" + (i - n) + "^2");
                else
                {
                    for (int j = 1; j <= n; j++)
                    {
                        for (int k = j + 1; k <= n; k++)
                        {
                            if (beta.matrix[i][0] >= 0) writer.write(" + " + (new BigDecimal(beta.matrix[i][0]).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros()).abs().toPlainString() + "x" + j + "x" + k);
                            else writer.write(" - " + (new BigDecimal(beta.matrix[i][0]).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros()).abs().toPlainString() + "x" + j + "x" + k);
                            i++;
                        }
                    }
                }
            }

            double fy = 0;

            // Iterate through each effective row in the beta matrix
            for (int i = 0; i < beta.rowEff; i++) {
            
                // First element case
                if (i == 0) {
                    fy += beta.matrix[i][0];  // Add first beta value
                }
                // Linear terms
                else if (i <= n) {
                    fy += beta.matrix[i][0] * y.matrix[i - 1][0];  // Linear contribution from y
                }
                // Quadratic terms (single variable)
                else if (i <= 2 * n) {
                    fy += beta.matrix[i][0] * y.matrix[i - n - 1][0] * y.matrix[i - n - 1][0];  // Quadratic contribution from y
                }
                // Interaction terms
                else {
                    int index = 2 * n + 1;  // Initialize index for interactions
                    for (int j = 1; j <= n; j++) {
                        for (int k = j + 1; k <= n; k++) {
                            // Ensure you are accessing the correct matrix entries
                            fy += beta.matrix[index][0] * y.matrix[j - 1][0] * y.matrix[k - 1][0];  // Interaction contribution
                            index++;  // Increment index for the next beta matrix row
                        }
                    }
                    // Remove the break if you expect to continue looping
                    break;
                }
            }
            writer.newLine();
    
            String predict = "";
            for (int i = 0; i < y.rowEff; i++)
            {
                if (i == y.rowEff - 1) predict += (y.matrix[i][0]);
                else predict += y.matrix[i][0] + ", ";
            }
            BigDecimal bdfy = new BigDecimal(fy).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros();
            writer.write("\nHasil taksiran f(" + predict + "): " + bdfy.toString());
            
            writer.close();
            System.out.println("File berhasil dibuat!");

        } catch (IOException e) {
            System.out.println("Terjadi error dalam pembuatan file!");
            e.printStackTrace();
        }
    }
}