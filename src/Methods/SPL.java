package Methods;

import java.util.*;

import ADTMatrix.Matrix;
import ADTMatrix.Operation;

import java.io.*;

public class SPL
{
    Operation op = new Operation();

    public int nEff;
    public double[] x;
    public String[] answer;
    
    Scanner input = new Scanner(System.in);

    /* Konstruktor SPL */
    public SPL()
    {
        this.nEff = 0; // Jumlah elemen valid
        this.x = new double[100000]; // Inisialisasi x
        this.answer = new String[100000];
    }

    // Prosedur untuk implementasi solusi inverse
    public void InverseSPL(Matrix M)
    {
        Matrix M1 = new Matrix();
        Matrix M2 = new Matrix();

        M1 = op.dropLastCol(M);
        M2 = op.takeLastCol(M);
    
        System.out.print("\n");

        if (M1.rowEff != M1.colEff)
        {
            System.out.println("Matriks memerlukan " + M1.colEff + " persamaan untuk dapat diselesaikan");
        }
        else if (det.determinantCofactor(M1) == 0)
        {
            System.out.println("Matriks tidak memiliki inverse.");
        }
        else
        {
            M1 = inv.inverseAdjoint(M1);
            M2 = op.multiplyMatrix(M1, M2);

            System.out.println("Solusi: ");
            for (int i = 0; i < M2.rowEff; i++)
            {
                System.out.print("x");
                System.out.print(i+1);
                System.out.print(" = ");
                System.out.print(M2.matrix[i][0]);
                System.out.print(", \n");
            }
        }   
    }

    public void InverseSPLFile(Matrix M)
    {
        Matrix M1 = new Matrix();
        Matrix M2 = new Matrix();

        String filename;

        M1 = op.takeLastCol(M);
        M2 = op.dropLastCol(M);

        System.out.print("\nMasukkan nama file: ");
        filename = input.nextLine() + ".txt";

        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("../test/" + filename));

            writer.write("Matriks: ");
            writer.newLine();
            for (int i = 0; i < M.rowEff; i++)
            {
                for (int j = 0; j < M.colEff; j++)
                {
                    writer.write(M.matrix[i][j] + ((j == M.colEff - 1) ? "" : " "));
                }
            writer.newLine();
            }

            writer.newLine();
            if (M2.rowEff != M2.colEff)
            {
                writer.write("Matriks memerlukan " + M2.colEff + " persamaan untuk dapat diselesaikan");
            }
            else if (det.determinantCofactor(M2) == 0)
            {
                writer.write("Tidak dapat menggunakan metode matriks balikan!");
                writer.newLine();
            }
            else {
                M2 = inv.inverseAdjoint(M);
                M1 = op.multiplyMatrix(M2, M1);
                writer.write("Solusi: ");
                for (int i = 0; i < M1.rowEff; i++){
                    writer.write("x" + (i+1) + " = " + M1.matrix[i][0]);
                    if (i == M1.rowEff - 1){
                        writer.newLine();
                    }
                    else{
                        writer.write(", ");
                        writer.newLine();
                    }
                }
            }

            writer.flush();
            writer.close();
        } 
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public Matrix cramerSwap(Matrix M1, Matrix M2, int col)
    {
        Matrix temp = new Matrix();
        temp = op.copyMatrix(M2);

        for (int i = 0; i < M2.rowEff; i++)
        {
            temp.matrix[i][col] = M1.matrix[i][0];
        }
        return temp;
    }

    public void kaidahCramer(Matrix m)
    {
        Matrix A = new Matrix();
        Matrix B = new Matrix();
        double detX, determinant;

        // memotong elemen terakhir dari matriks augmented dan masukin ke matriks A
        for(int i = 0; i < m.rowEff; i++){
            for(int j = 0; j < m.colEff-1; j++)
            {
                A.matrix[i][j] = m.matrix[i][j];
            }
        }

        determinant = det.determinantCofactor(A);

        if (determinant == 0)
        {
            System.out.println("Tidak bisa menggunakan kaidah cramer karena determinan matriks = 0");
        }
        else
        {
            // bikin matriks yang isinya b
            for (int i=0; i<m.rowEff; i++)
            {
                B.matrix[i][0] = m.matrix[i][m.colEff-1];
            }

            for (int j=0; j<m.colEff-1; j++)
            {
                for(int i=0; i<m.rowEff; i++)
                {
                    A.matrix[i][j] = B.matrix[i][0];
                }
                detX = det.determinantCofactor(A);
                System.out.println("Nilai x" + (j+1) + " = " + detX/determinant);
            }
        }
    }

        // Prosedur untuk implementasi solusi kaidah cramer
    public void CramerSPL(Matrix M)
    {
        Matrix M1 = new Matrix();
        Matrix M2 = new Matrix();
        Matrix temp = new Matrix();

        M1 = op.takeLastCol(M);
        M2 = op.dropLastCol(M);
        temp = op.copyMatrix(M2);

        System.out.print("\n");
        if (M2.rowEff != M2.colEff)
        {
            System.out.println("Matriks memerlukan " + M2.colEff + " persamaan untuk dapat diselesaikan.");
        }
        else if (det.determinantCofactor(M2) == 0)
        {
            System.out.println("Tidak dapat menggunakan kaidah Cramer!");
        }
        else
        {
            System.out.println("Solusi: ");
            for (int i = 0; i < M1.rowEff; i++)
            {
                temp = cramerSwap(M2, M1, i);

                System.out.print("x");
                System.out.print(i+1);
                System.out.print(" = ");
                System.out.print(det.determinantCofactor(temp)/det.determinantCofactor(M2));
                System.out.print(", \n");
            }
        }
    }

    public void CramerSPLFile(Matrix M)
    {
        Matrix M1 = new Matrix();
        Matrix M2 = new Matrix();
        Matrix temp = new Matrix();

        M1 = op.takeLastCol(M);
        M2 = op.dropLastCol(M);
        temp = op.copyMatrix(M2);

        String filename;

        System.out.print("\nMasukkan nama file: ");
        filename = input.nextLine() + ".txt";

        try 
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("../test/" + filename));

            writer.write("Matriks:");
            writer.newLine();
            for (int i = 0; i < M.rowEff; i++)
            {
                for (int j = 0; j < M.colEff; j++)
                {
                    writer.write(M.matrix[i][j] + ((j == M.colEff-1) ? "" : " "));
                }
            writer.newLine();
            }

            writer.newLine();
            if (M2.rowEff != M2.colEff)
            {
                writer.write("Matriks memerlukan " + M2.colEff + " persamaan untuk dapat diselesaikan.");
                writer.newLine();
            }
            else if (det.determinantCofactor(M2) == 0)
            {
                writer.write("Matriks tidak memiliki inverse sehingga tidak bisa menggunakan kaidah Cramer.");
                writer.newLine();
            }
            else
            {
                System.out.println("Solusi: ");
                for (int i = 0; i < M1.rowEff; i++)
                {
                    temp = cramerSwap(M2, M1, i);
                    writer.write("x" + (i+1) + " = " + (det.determinantCofactor(temp) / det.determinantCofactor(m2)));
                    if (i == M1.rowEff - 1) 
                    {
                        writer.newLine();
                    }
                    else
                    {
                        writer.write(", ");
                        writer.newLine();
                    }
                }
            }

            writer.flush();
            writer.close();
        } 
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public Matrix shiftZero(Matrix M)
    {
        Matrix result = new Matrix();
        result = op.copyMatrix(M);
        boolean swap;
        int col = 0, rowSearch, lenNonZero = 0;

        while ((lenNonZero < result.rowEff) && (col < result.colEff)) 
        {
            swap = false;
            if (result.matrix[lenNonZero][col] == 0) 
            {
                
                rowSearch = lenNonZero + 1;
                while ((rowSearch < result.rowEff) && (!swap)) 
                {
                    if (result.matrix[rowSearch][col] != 0) 
                    {
                        swap = true;
                        result = swapRow(result, rowSearch, lenNonZero);
                        lenNonZero += 1;
                    }
                    else
                    {
                        rowSearch += 1;
                    }
                }

                if (!swap) 
                {
                    col += 1;
                }
            }
            else
            {
                lenNonZero += 1;
            }
        }
        return result;
    }

    // Fungsi untuk mengurangkan dan mengalikan satu baris tertentu dari matriks
    public Matrix subMultiplyRow(Matrix M, int rowDes, int rowMin, double k)
    {
        Matrix result = new Matrix();

        result = op.copyMatrix(M);

        for (int i = 0; i < result.colEff; i++)
        {
            result.matrix[rowDes][i] -= k * result.matrix[rowMin][i];
        }

        return result;
    }     

    // Fungsi untuk mengalikan semua elemen dalam satu baris tertentu dari matriks dengan sebuah konstanta
    public Matrix multiplyRowByConst(Matrix M, int row, double k)
    {
        Matrix result = new Matrix();

        result = op.copyMatrix(M);

        for (int i = 0; i < result.colEff; i++)
        {
            result.matrix[row][i] *= k;
        }

        return result;
    }

    public Matrix swapRow(Matrix M, int row1, int row2)
    {
        Matrix result = new Matrix();

        result = op.copyMatrix(M);

        result.matrix[row1] = M.matrix[row2];
        result.matrix[row2] = M.matrix[row1];

        return result;
    }

    // Fungsi untuk implementasi metode eliminasi Gauss
    public Matrix gauss(Matrix M)
    {
        Matrix result = new Matrix();
        int col = 0;
        int row = 0;
        int i;
        
        op.errorRounding(M);
        result = shiftZero(M);

        while (col < result.colEff-1 && row < result.rowEff) {
            if (result.matrix[row][col] == 0) 
            {
                col += 1;
            }
            else{
                for(i = row + 1; i < result.rowEff; i++)
                {
                    result = subMultiplyRow(result, i, row, result.matrix[i][col] / result.matrix[row][col]);
                }
                result = multiplyRowByConst(result, row, 1/result.matrix[row][col]);
                result = shiftZero(result);

                col += 1;
                row += 1;
            }
        }
        return result;
    }

    public Matrix gaussJordan(Matrix M)
    {
        Matrix result = new Matrix();
        int col = 0;
        int row = 0;

        result = gauss(M);
        op.errorRounding(result);

        while (col < result.colEff-1 && row < result.rowEff){
            if (result.matrix[row][col] == 0)
            {
                col += 1;
            }
            else
            {
                for (int i = 0; i < row; i++)
                {
                    if (i != row)
                    {
                        result = subMultiplyRow(result, i, row, result.matrix[i][col]/result.matrix[row][col]);
                    }
                }
                col += 1;
                row += 1;
            }
        }
        return result;
    }   

    public void gaussSPL(Matrix M)
    {
        // Menerima matriks gauss/gauss jordan SPL
        int solusi;

        op.errorRounding(M);
        solusi = checkSolusi(M);

        System.out.print("\n");
        switch (solusi)
        {
            case 0:
            solusiKosong(M);
            break;

            case 1:
            solusiUnik(M);
            break;

            case 2:
            solusiBanyak(M);
            break;

            case 3:
            solusiNone(M);
            break;
        }
    }

    // Prosedur untuk membuat file dari solusi SPL
    public void gaussSPLFile(Matrix M)
    {
        int solusi;
        String filename;

        op.errorRounding(M);
        solusi = checkSolusi(M);
        System.out.print("\nMasukkan nama file: (tambahkan .txt di akhir)");
        filename = input.nextLine() + ".txt";

        switch (solusi)
        {
            case 0:
            solusiKosongFile(M, filename);
            break;

            case 1:
            solusiUnikFile(M, filename);
            break;

            case 2:
            solusiBanyakFile(M, filename);
            break;

            case 3:
            solusiNoneFile(M, filename);
            break;
        }
    }

    public boolean isAllZero(Matrix M) 
    {
        boolean foundNonZero = false;

        for (int i = 0; i < M.rowEff && !foundNonZero; i++) 
        {
            for (int j = 0; j < M.colEff && !foundNonZero; j++) 
            {
                foundNonZero = (M.matrix[i][j] != 0);
            }
        }
        return !foundNonZero;
    }

    public Matrix removeRowAll0 (Matrix M)
    {
        // Menghapus baris yang seluruh elemennya 0
        Matrix temp = new Matrix();
        temp = op.copyMatrix(M);

        boolean allZero = true;
        int i = M.rowEff - 1, j;

        while (allZero && i > -1){
            while (allZero && i > -1) {

                j = 0;
                while (allZero && j < M.colEff){
                    if(M.matrix[i][j] != 0){
                        allZero = false;
                    }
                    j += 1;
                }

                if (allZero){
                    temp = op.dropLastRow(temp);
                    i = temp.rowEff - 1;
                }
            }
        }

        return temp;
    }

    public boolean isSolvable(Matrix M) 
    {
        boolean solvable = false;
        int j;

        M = removeRowAll0(M);

        j = 0;
        while (!solvable && j < M.colEff-1)
        {
            if (M.matrix[M.rowEff - 1][j] != 0)
            {
                solvable = true;
            }
            else
            {
                j += 1;
            }
        }
        return solvable;
    }

        // Fungsi untuk melakukan perhitungan rekursif yang terkait dengan manipulasi matriks dan sistem persamaan linear
    public double recursion(int toplimit, int bottomlimit, int row, int varCol, double arrayHasil[], String arrayString[], Matrix M)
    {
        double cacheConst = 0;
        for (int k = toplimit; k > bottomlimit; k--)
        {
            if ((arrayHasil[k] != 0 || arrayString[k] != "") && M.matrix[row][k] != 0)
            {
                int row1 = M.rowEff-1;
                while (M.matrix[row1][k] != 1) 
                {
                    row1 -=1;
                }
                cacheConst = cacheConst + M.matrix[row][k]*(M.matrix[row1][varCol]) - M.matrix[row][k] * recursion(toplimit, cariSatu(M, row1), row1, varCol, arrayHasil, arrayString, M);
            }
        }
        return cacheConst;
    }

    public int checkSolusi(Matrix M) 
    {
        // PREKONDISI: matriks M adalah matriks gauss/gauss-jordan SPL
        // 0 = Solusi Trivial (Matriks kosong)
        // 1 = Solusi unik
        // 2 = Solusi banyak
        // 3 = Solusi tidak ada

        int x = -999;
        boolean unique;

        if (isAllZero(M)) 
        {
            x = 0;
        }
        else if (!isSolvable(M))
        {
            x = 3;
        }
        else if (M.rowEff == M.colEff-1)
        {
            unique = true;
            for (int i = 0; i < M.rowEff; i++)
            {
                if (M.matrix[i][i] != 1)
                {
                    unique = false;
                }
            }

            if (unique)
            {
                x = 1;
            }
            else x = 2;
        }
        else
        {
            x = 2;
        }

        return x;
    }

    public void solusiKosong(Matrix M)
    {
        char var = 'S';
        char freeVariables[] = new char[M.colEff-1];

        for (int i = M.colEff - 2; i > -1; i--)
        {
            freeVariables[i] = var;
            if (var == 'Z')
            {
                var = 'A';
            }
            else if (var == 'R')
            {
                var = 'a';
            }
            else var += 1;
        }

        System.out.println("Matriks Hasil: ");
        M.writeMatrix();
        
        System.out.println();
        System.out.println("Persamaan linear kosong, semua variabel nilai memenuhi.");
        

        for (int i = 0; i < M.colEff-1; i++)
        {
            System.out.print("x");
            System.out.print(i+1);
            System.out.print(" = ");
            System.out.print(freeVariables[i]);
            System.out.print(", \n");
        }
    }

    
    // Prosedur untuk menulis solusi kosong di file
    public void solusiKosongFile(Matrix M, String filename)
    {
        char var = 'S';
        char freeVariables[] = new char[M.colEff-1];

        for (int i = M.colEff-2; i > -1; i--)
        {
            freeVariables[i] = var;
            if (var == 'Z')
            {
                var = 'A';
            }
            else if (var == 'R')
            {
                var = 'a';
            }
            else var += 1;
        }

        try
        {   
            String userDirectory = System.getProperty("user.dir");
            BufferedWriter writer = new BufferedWriter(new FileWriter(userDirectory + "/test/result/" + filename));

            writer.write("Matriks Hasil: ");
            writer.newLine();
            for (int i = 0; i < M.rowEff; i++)
            {
                for (int j = 0; j < M.colEff; j++)
                {
                    writer.write(M.matrix[i][j] + ((j == M.colEff - 1) ? "" : " "));
                }
            writer.newLine();
            }
            writer.newLine();

            // Write perline
            writer.write("Persamaan linear kosong, semua variabel nilai memenuhi.");
            writer.newLine();
            for (int i = 0; i < M.colEff-1; i++)
            {
                writer.write("x" + (i + 1) + " = " + freeVariables[i]);
                if (i == M.colEff - 2)
                {
                    writer.newLine();
                }
                else
                {
                    writer.write(", ");
                    writer.newLine();
                }
            }
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public int cariSatu(Matrix M, int row)
    {
        // Dipakai di solusi banyak
        int col;
        boolean adasatu = false;

        col = 0;
        while ((!adasatu) && (col < M.colEff))
        {
            if (M.matrix[row][col] == 1)
            {
                adasatu = true;
            }
            else
            {
                col += 1;
            }
        }

        return col;
    }
    
    // Prosedur untuk mencari solusi unik dari persamaan gauss
    public void solusiUnik(Matrix M)
    {
        double cache;
        double arrayRes[] = new double[M.colEff-1];
        
        for (int i = 0; i < M.colEff-1; i++)
        {
            arrayRes[i] = 0;
        }

        for (int i = M.rowEff-1; i > -1; i--)
        {
            cache = M.matrix[i][M.colEff-1];
            for(int j = i; j < M.colEff-1; j++)
            {
                cache -= arrayRes[j] * M.matrix[i][j];
            }

            arrayRes[i] = cache;
        }

        System.out.println("Matriks Hasil: ");
        M.writeMatrix();

        System.out.println();
        for (int i = 0; i < M.colEff-1; i++)
        {
            System.out.print("x");
            System.out.print(i+1);
            System.out.print(" = ");
            System.out.print(arrayRes[i]);
            System.out.print(", \n");
        }
    }
    
    // Prosedur untuk menulis file solusi unik dari persamaan gauss
    public void solusiUnikFile(Matrix M, String filename)
    {
        double cache;
        double arrayRes[] = new double[M.colEff-1];

        try 
        {
            for (int i = 0; i < M.colEff-1; i++)
            {
                arrayRes[i] = 0;
        }

        for (int i = M.rowEff-1; i > -1; i--)
        {
            cache = M.matrix[i][M.colEff-1];
            for(int j = i; j < M.colEff-1; j++)
            {
                cache -= arrayRes[j] * M.matrix[i][j];
            }
            arrayRes[i] = cache;
        }    
            String userDirectory = System.getProperty("user.dir");
            BufferedWriter writer = new BufferedWriter(new FileWriter(userDirectory + "/test/result/" + filename));

            writer.write("Matriks Hasil:");
            writer.newLine();
            for (int i= 0; i<M.rowEff; i++)
            {
                for (int j=0; j<M.colEff; j++)
                {
                    writer.write(M.matrix[i][j] + ((j == M.colEff-1) ? "" : " "));
                }
            writer.newLine();
            }
            writer.newLine();

            writer.write("Solusi: ");
            for (int i = 0; i < M.colEff-1; i++)
            {
                writer.write("x" + (i + 1) + " = " + arrayRes[i]);
                if (i == M.colEff - 2) 
                {
                    writer.newLine();
                }
                else
                {
                    writer.write(", ");
                    writer.newLine();
                }
            }
            writer.flush();
            writer.close();

        // Handling Error
        } 
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    
    // Prosedur untuk mencari solusi banyak dari persamaan gauss
    public void solusiBanyak(Matrix M)
    {
        boolean trivial, realzerp;
        double cache, arrayRes[] = new double[M.colEff-1];
        char var, arrayChar[] = new char[M.colEff-1];
        String arrayString[] = new String[M.colEff-1];

        // Algoritma
        for (int i = 0; i < M.colEff-1; i++)
        {
            arrayRes[i] = 0;
        }

        for (int i = 0; i < M.colEff-1; i++)
        {
            arrayChar[i] = '/';
        } 

        for (int i = 0; i < M.colEff-1; i++)
        {
            arrayString[i] = "";
        }

        var = 'S';
        M = removeRowAll0(M);

        for (int i = M.rowEff-1; i > -1; i--)
        {
            cache = M.matrix[i][M.colEff-1];

            for (int j = cariSatu(M, i) + 1; j < M.colEff-1; j++)
            {
                if (arrayRes[j] == 0) {
                    realzerp = true;
                    for(int k = j; k < M.colEff-1; k++)
                    {
                        if (arrayChar[k] != '/')
                        {
                            realzerp = false;
                        }
                    }

                    if (j > 0)
                    {
                        for(int k = j-1; k > -1; k--)
                        {
                            if (M.matrix[i][k] != 0)
                            {
                                realzerp = false;
                            }
                        }
                    }

                    if (arrayString[j] != "")
                    {
                        realzerp = true;
                    }

                    if (j == M.colEff-2)
                    {
                        realzerp = false;
                    }

                    if (!realzerp)
                    {
                        if  (arrayChar[j] == '/')
                        {
                            arrayChar[j] = var;
                            if (var == 'Z')
                            {
                                var = 'A';
                            }
                            else if (var == 'R')
                            {
                                var = 'a';
                            }
                            else var += 1;
                        }

                        double cacheConst = (-1)*M.matrix[i][j];
                        cacheConst += recursion(M.colEff-2, cariSatu(M, i), i, j, arrayRes, arrayString, M);
                        
                        if (cacheConst > 0)
                        {
                            arrayString[cariSatu(M, i)] += String.format("+%.2f%c", cacheConst, arrayChar[j]);
                        }
                        else if (cacheConst < 0) 
                        {
                            arrayString[cariSatu(M, i)] += String.format("%.2f%c", cacheConst, arrayChar[j]);
                        }
                    }
                }
                else
                {
                    cache -= arrayRes[j] * M.matrix[i][j];
                }
            }
            try 
            {
                arrayRes[cariSatu(M, i)] = cache;
            } 
            catch (Exception e) 
            {

            }
        }

        trivial = true;
        for (int i = 0; i < M.colEff-1; i++)
        {
            if (arrayRes[i] != 0)
            {
                trivial = false;
            }
        }

        System.out.println("Matriks Hasil: ");
        M.writeMatrix();

        System.out.println();
        if (trivial) 
        {
            for(int i = 0; i < M.colEff-1; i++)
            {
                System.out.print("x");
                System.out.print(i+1);
                System.out.print(" = ");
            }
            System.out.print(0);
            System.out.println("atau");
        }

        for (int i = 0; i < M.colEff-1; i++)
        {
            System.out.print("x");
            System.out.print(i+1);
            System.out.print(" = ");
            if (arrayRes[i] == 0)
            {
                realzerp = true;
                for (int j = i; j < M.colEff-1; j++)
                {
                    if (arrayChar[j] != '/')
                    {
                        realzerp = false;
                    }
                }

                if (arrayString[i] != "")
                {
                    realzerp = true;
                }

                if (!realzerp){
                    if (arrayChar[i] == '/')
                    {
                        arrayChar[i] = var;
                        if (var == 'Z')
                        {
                            var = 'A';
                        }
                        else if (var == 'R')
                        {
                            var = 'a';
                        }
                        else var += 1;
                    }
                    System.out.print(arrayChar[i] + "");
                }
                if (realzerp && (arrayString[i] == ""))
                {
                    System.out.print(arrayRes[i]);
                }
            }
            else 
            {
                System.out.print(arrayRes[i]);
            }
            System.out.print(arrayString[i]);
            System.out.print(", \n");
        }
    }

    
    // Prosedur untuk menulis file solusi banyak dari persamaan gauss
    public void solusiBanyakFile(Matrix M, String filename)
    {
        boolean trivial, realzerp;
        double cache, arrayRes[] = new double[M.colEff-1];
        char var, arrayChar[] = new char[M.colEff-1];
        String arrayString[] = new String[M.colEff-1];

        for (int i = 0; i < M.colEff-1; i++)
        {
            arrayRes[i] = 0;
        }
        
        for (int i = 0; i < M.colEff-1; i++)
        {
            arrayChar[i] = '/';
        } 
        
        for (int i = 0; i < M.colEff-1; i++)
        {
            arrayString[i] = "";
        }

        var = 'S';
        M = removeRowAll0(M);

        for (int i = M.rowEff-1; i > -1; i--)
        {

            cache = M.matrix[i][M.colEff-1];

            for (int j = cariSatu(M, i) + 1; j < M.colEff-1; j++)
            {
                if (arrayRes[j] == 0) 
                {
                    realzerp = true;
                    for (int k = j; k < M.colEff-1; k++)
                    {
                        if (arrayChar[k] != '/'){
                            realzerp = false;
                        }
                    }

                    if (j > 0)
                    {
                        for(int k = j-1; k < -1; k--)
                        {
                            if (M.matrix[i][k] != 0){
                                realzerp = false;
                            }
                        }
                    }

                    if (arrayString[j] != "")
                    {
                        realzerp = true;
                    }

                    if (j == M.colEff-2)
                    {
                        realzerp = false;
                    }

                    if (!realzerp)
                    {
                        if  (arrayChar[j] == '/')
                        {
                            arrayChar[j] = var;
                            if (var == 'Z')
                            {
                                var = 'A';
                            }
                            else if (var == 'R')
                            {
                                var = 'a';
                            }
                            else var += 1;
                        }

                        double cacheConst = (-1)*M.matrix[i][j];
                        cacheConst += recursion(M.colEff-2, cariSatu(M, i), i, j, arrayRes, arrayString, M);
                        
                        if (cacheConst > 0)
                        {
                            arrayString[cariSatu(M, i)] += String.format("+%.2f%c", cacheConst, arrayChar[j]);
                        }
                        else if (cacheConst < 0) 
                        {
                            arrayString[cariSatu(M, i)] += String.format("%.2f%c", cacheConst, arrayChar[j]);
                        }
                    }
                }
                else
                {
                    cache -= arrayRes[j] * M.matrix[i][j];
                }
            }

            try 
            {
                arrayRes[cariSatu(M, i)] = cache;
            } 
            catch (Exception e) 
            {

            }
        }
        
        trivial = true;
        for (int i = 0; i < M.colEff-1; i++)
        {
            if (arrayRes[i] != 0)
            {
                trivial = false;
            }
        }

        try 
        {   
            String userDirectory = System.getProperty("user.dir");
            BufferedWriter writer = new BufferedWriter(new FileWriter(userDirectory + "/test/result/" + filename));

            writer.write("Matriks Hasil:");
            writer.newLine();
            for (int i= 0; i<M.rowEff; i++)
            {
                for (int j=0; j<M.colEff; j++)
                {
                    writer.write(M.matrix[i][j] + ((j == M.colEff-1) ? "" : " "));
                }
            writer.newLine();
            }
            writer.newLine();

            if (trivial) 
            {
                for(int i = 0; i < M.colEff-1; i++)
                {
                    writer.write("x" + (i+1) + " = " + 0);
                }
                writer.newLine();
                writer.write("atau");
                writer.newLine();
            }

            for (int i = 0; i < M.colEff-1; i++)
            {
                writer.write("x" + (i+1) + " = ");
                if (arrayRes[i] == 0)
                {
                    realzerp = true;
                    for (int j = i; j < M.colEff-1; j++)
                    {
                        if (arrayChar[j] != '/'){
                            realzerp = false;
                        }
                    }
                    
                    if (arrayString[i] != "")
                    {
                        realzerp = true;
                    }

                    if (i > 0)
                    {
                        for(int j = i-1; j > -1; j--)
                        {
                            if (M.matrix[i][j] != 0)
                            {
                                realzerp = false;
                            }
                        }
                    }
    
                    if (!realzerp)
                    {
                        if (arrayChar[i] == '/')
                        {
                            arrayChar[i] = var;
                            if (var == 'Z')
                            {
                                var = 'A';
                            }
                            else if (var == 'R')
                            {
                                var = 'a';
                            }
                            else var += 1;
                        }
                        writer.write(arrayChar[i]);
                    }

                    if (realzerp && arrayString[i] == "")
                    {
                        writer.write(arrayRes[i] + "");
                    }
                }

                
                else 
                {
                    writer.write(arrayRes[i] + "");
                }
                writer.write(arrayString[i]);
                writer.write((i == M.colEff-2) ? "\n" : ", \n");
            }
            writer.flush();
            writer.close();

        // Handling Error
        } 
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    // Prosedur untuk menampilkan tidak ada solusi
    public void solusiNone(Matrix M)
    {   
        System.out.println("Matriks Hasil: ");
        M.writeMatrix();

        System.out.println();
        System.out.println("Solusi tidak ada.");
    }
    
    // Prosedur untuk menulis file tidak ada solusi
    public void solusiNoneFile(Matrix M, String filename)
    {
        try 
        {   
            String userDirectory = System.getProperty("user.dir");
            BufferedWriter writer = new BufferedWriter(new FileWriter(userDirectory + "/test/result/" + filename));

            writer.write("Matriks Hasil:");
            writer.newLine();
            for (int i= 0; i<M.rowEff; i++)
            {
                for (int j=0; j<M.colEff; j++)
                {
                    writer.write(M.matrix[i][j] + ((j == M.colEff-1) ? "" : " "));
                }
            writer.newLine();
            }
            writer.newLine();
            writer.write("Solusi tidak ada.");
            writer.newLine();
            writer.flush();
            writer.close();

        } 
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}