package ADTMatrix;

public class Operation 
{

    public boolean isSquareMatrix(Matrix M)
    {
        return M.rowEff == M.colEff;
    }

    public boolean isIdentityMatrix(Matrix M)
    {
        if (!isSquareMatrix(M))
        {
            return false;
        }

        for (int i = 0; i < M.rowEff; i++)
        {
            for (int j = 0; j < M.colEff; j++)
            {
                if (i == j && M.matrix[i][j] != 1)
                {
                    return false;
                }
                else if (i != j && M.matrix[i][j] != 0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isMatrixEqual(Matrix M1, Matrix M2)
    {
        return (M1.rowEff == M2.rowEff && M1.colEff == M2.colEff);
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

    public Matrix multiplyMatrix(Matrix M1, Matrix M2)
    {
        Matrix result = new Matrix(M1.rowEff, M2.colEff);

        for (int i = 0; i < M1.rowEff; i++)
        {
            for (int j = 0; j < M2.colEff; j++)
            {
                for (int k = 0; k < M1.colEff; k++)
                {
                    result.matrix[i][j] += M1.matrix[i][k] * M2.matrix[k][j];
                }
            }
        }
        return result;
    }

    public Matrix transposeMatrix(Matrix M)
    {
        Matrix result = new Matrix(M.colEff, M.rowEff);

        for (int i = 0; i < M.rowEff; i++)
        {
            for (int j = 0; j < M.colEff; j++)
            {
                result.matrix[j][i] = M.matrix[i][j];
            }
        }
        return result;
    }

    public Matrix expandCol(Matrix M1, Matrix M2)
    {
        Matrix result = new Matrix(M1.rowEff, M1.colEff + M2.colEff);

        for (int i = 0; i < M1.rowEff; i++)
        {
            for (int j = 0; j < result.colEff; j++)
            {
                if (j < M1.colEff)
                {
                    result.matrix[i][j] = M1.matrix[i][j];
                }
                else
                {
                    result.matrix[i][M1.colEff + j] = M2.matrix[i][j];
                }
            }
        }
        return result;
    }

    public Matrix getMinor(Matrix M, int delRow, int delCol)
    {
        Matrix result = new Matrix(M.rowEff - 1, M.colEff - 1);
        int row = 0, col = 0;

        for (int i = 0; i < M.rowEff; i++)
        {
            if (i == delRow)
            {
                continue;
            }

            for (int j = 0; j < M.colEff; j++)
            {
                if (j == delCol)
                {
                    continue;
                }

                result.matrix[row][col] = M.matrix[i][j];
                col++;
            }
            row++;
            col = 0;
        }
        return result;
    }

    public Matrix takeLastRow(Matrix M)
    {
        Matrix result = new Matrix(1, M.colEff);

        for (int i = 0; i < M.colEff; i++)
        {
            result.matrix[0][i] = M.matrix[M.rowEff - 1][i];
        }
        return result;
    }

    public Matrix takeLastCol(Matrix M)
    {
        Matrix result = new Matrix(M.rowEff, 1);

        for (int i = 0; i < M.rowEff; i++)
        {
            result.matrix[i][0] = M.matrix[i][M.colEff - 1];
        }
        return result;
    }
    
    public Matrix dropLastRow(Matrix M)
    {
        Matrix result = new Matrix(M.rowEff - 1, M.colEff);

        for (int i = 0; i < M.rowEff - 1; i++)
        {
            for (int j = 0; j < M.colEff; j++)
            {
                result.matrix[i][j] = M.matrix[i][j];
            }
        }
        return result;
    }

    public Matrix dropLastCol(Matrix M)
    {
        Matrix result = new Matrix(M.rowEff, M.colEff - 1);

        for (int i = 0; i < M.rowEff; i++)
        {
            for (int j = 0; j < M.colEff - 1; j++)
            {
                result.matrix[i][j] = M.matrix[i][j];
            }
        }
        return result;
    }
    
}
