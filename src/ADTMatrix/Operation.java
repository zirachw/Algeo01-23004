package ADTMatrix;

public class Operation 
{

    public boolean isSquareMatrix(Matrix matrix)
    {
        return matrix.rowEff == matrix.colEff;
    }

    public boolean isIdentityMatrix(Matrix matrix)
    {
        if (!isSquareMatrix(matrix))
        {
            return false;
        }

        for (int i = 0; i < matrix.rowEff; i++)
        {
            for (int j = 0; j < matrix.colEff; j++)
            {
                if (i == j && matrix.matrix[i][j] != 1)
                {
                    return false;
                }
                else if (i != j && matrix.matrix[i][j] != 0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isMatrixEqual(Matrix m1, Matrix m2)
    {
        return (m1.rowEff == m2.rowEff && m1.colEff == m2.colEff);
    }

    public Matrix createIdentityMatrix(int size)
    {
        Matrix result = new Matrix(size, size);

        for (int i = 0; i < size; i++)
        {
            result.matrix[i][i] = 1;
        }
        return result;
    }

    public Matrix multiplyMatrix(Matrix m1, Matrix m2)
    {
        Matrix result = new Matrix(m1.rowEff, m2.colEff);

        for (int i = 0; i < m1.rowEff; i++)
        {
            for (int j = 0; j < m2.colEff; j++)
            {
                for (int k = 0; k < m1.colEff; k++)
                {
                    result.matrix[i][j] += m1.matrix[i][k] * m2.matrix[k][j];
                }
            }
        }
        return result;
    }

    public Matrix transposeMatrix(Matrix matrix)
    {
        Matrix result = new Matrix(matrix.colEff, matrix.rowEff);

        for (int i = 0; i < matrix.rowEff; i++)
        {
            for (int j = 0; j < matrix.colEff; j++)
            {
                result.matrix[j][i] = matrix.matrix[i][j];
            }
        }
        return result;
    }

    public Matrix expandCol(Matrix m1, Matrix m2)
    {
        Matrix result = new Matrix(m1.rowEff, m1.colEff + m2.colEff);

        for (int i = 0; i < m1.rowEff; i++)
        {
            for (int j = 0; j < result.colEff; j++)
            {
                if (j < m1.colEff)
                {
                    result.matrix[i][j] = m1.matrix[i][j];
                }
                else
                {
                    result.matrix[i][m1.colEff + j] = m2.matrix[i][j];
                }
            }
        }
        return result;
    }

    public Matrix getMinor(Matrix matrix, int delRow, int delCol)
    {
        Matrix result = new Matrix(matrix.rowEff - 1, matrix.colEff - 1);
        int row = 0, col = 0;

        for (int i = 0; i < matrix.rowEff; i++)
        {
            if (i == delRow)
            {
                continue;
            }

            for (int j = 0; j < matrix.colEff; j++)
            {
                if (j == delCol)
                {
                    continue;
                }

                result.matrix[row][col] = matrix.matrix[i][j];
                col++;
            }
            row++;
            col = 0;
        }
        return result;
    }

    public Matrix takeLastRow(Matrix matrix)
    {
        Matrix result = new Matrix(1, matrix.colEff);

        for (int i = 0; i < matrix.colEff; i++)
        {
            result.matrix[0][i] = matrix.matrix[matrix.rowEff - 1][i];
        }
        return result;
    }

    public Matrix takeLastCol(Matrix matrix)
    {
        Matrix result = new Matrix(matrix.rowEff, 1);

        for (int i = 0; i < matrix.rowEff; i++)
        {
            result.matrix[i][0] = matrix.matrix[i][matrix.colEff - 1];
        }
        return result;
    }
    
    public Matrix dropLastRow(Matrix matrix)
    {
        Matrix result = new Matrix(matrix.rowEff - 1, matrix.colEff);

        for (int i = 0; i < matrix.rowEff - 1; i++)
        {
            for (int j = 0; j < matrix.colEff; j++)
            {
                result.matrix[i][j] = matrix.matrix[i][j];
            }
        }
        return result;
    }

    public Matrix dropLastCol(Matrix matrix)
    {
        Matrix result = new Matrix(matrix.rowEff, matrix.colEff - 1);

        for (int i = 0; i < matrix.rowEff; i++)
        {
            for (int j = 0; j < matrix.colEff - 1; j++)
            {
                result.matrix[i][j] = matrix.matrix[i][j];
            }
        }
        return result;
    }
    
}
