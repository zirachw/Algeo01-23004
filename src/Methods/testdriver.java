package Methods;

import ADTMatrix.*;

public class testdriver {
    
    public static void main(String[] args) {
        BicubicInterpolation bi = new BicubicInterpolation();
        Matrix m4x4 = new Matrix(4,4);
        double[][] matrix = {
            {21.0, 98.0, 125.0, 153.0},
            {51.0, 101.0, 161.0, 59.0},
            {0.0, 42.0, 72.0, 210.0},
            {16.0, 12.0, 81.0, 96.0}
        };

        for(int i = 0 ; i < 4;i++){
            for(int j = 0 ; j < 4;j++){
                m4x4.matrix[i][j] = matrix[i][j];
            }
        }

        // Matrix matriksX = bi.getMatrixX();
        Matrix matrixF = bi.getMatrixF(m4x4);
        Matrix MAIJ = bi.getMatrixAij(matrixF);
        double result = bi.getFabResult(MAIJ, 0.25, 0.75);

        // matrixF.writeMatrix();
        // MAIJ.writeMatrix();
        System.out.println(result);

    }
}