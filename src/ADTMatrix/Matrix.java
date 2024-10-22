package ADTMatrix;

import java.util.Scanner;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/* Definisi Kelas Matrix */
public class Matrix 
{
    /* Atribut */
    public static Scanner input = new Scanner(System.in);
    public int rowEff;
    public int colEff;
    public double [][] matrix;

    /* Konstruktor */
    public Matrix(int mRow, int nCol) 
    {
        // Membuat matriks berukuran mRow x nCol
        this.rowEff = mRow;
        this.colEff = nCol;
        this.matrix = new double[mRow][nCol];
    }

    public Matrix()
    {
        // Membuat matriks berukuran 100 x 100 (default)
        this.matrix = new double[100][100]; 
        this.rowEff = 0;
        this.colEff = 0;
    }

    /* Validator */
    private boolean isValidRowInput(String[] rowElmts, int nCol) 
    {
        if (rowElmts.length != nCol) 
        {
            System.out.println("Jumlah bilangan tidak sesuai banyak kolom, masukkan " + nCol + " buah bilangan.");
            return false;
        }

        try 
        {
            for (String element : rowElmts) 
            {
                Double.parseDouble(element);
            }
        } 
        
        catch (NumberFormatException e) 
        {
            System.out.println("Terdapat input yang tidak valid, mohon ulangi input.");
            return false;
        }

        return true;
    }

    /* IO */
    public void readMatrix(int mRow, int nCol) 
    {
        // Membaca matriks dari input keyboard user
        this.rowEff = mRow;
        this.colEff = nCol;

        Scanner input = new Scanner(System.in);
        for (int row = 0; row < mRow; row++) 
        {
            boolean valid = false;
            do 
            {
                String line = input.nextLine();
                String[] rowElmts = line.split(" ");
                
                if (isValidRowInput(rowElmts, this.colEff)) 
                {
                    for (int col = 0; col < this.colEff; col++) 
                    {
                        this.matrix[row][col] = Double.parseDouble(rowElmts[col]);
                    }
                    valid = true;
                }
            } 
            while (!valid);
        }
    }

    public void writeMatrix()
    {
        // Menuliskan matriks ke layar
        int[] columnWidths = new int[this.colEff];

        for (int col = 0; col < this.colEff; col++) 
        {
            for (int row = 0; row < this.rowEff; row++) 
            {
                int width = String.format("%.5f", this.matrix[row][col]).length();
                
                if (width > columnWidths[col]) 
                {
                    columnWidths[col] = width;
                }
            }
        }

        // Pretty Print :)
        for (int row = 0; row < this.rowEff; row++)
        {
            if (this.rowEff == 1)
            {
                System.out.print("[");
            }
            else if (row == 0)
            {
                System.out.print("┌");
            }
            else if (row == this.rowEff - 1)
            {
                System.out.print("└");
            }
            else
            {
                System.out.print("|");
            }

            for (int col = 0; col < this.colEff; col++) 
            {
                System.out.printf("%" + (columnWidths[col] + 2) + ".5f", this.matrix[row][col]); 
            }

            if (this.rowEff == 1)
            {
                System.out.print("  ]");
            }
            else if (row == 0)
            {
                System.out.print("  ┐");
            }
            else if (row == this.rowEff - 1)
            {
                System.out.print("  ┘");
            }
            else
            {
                System.out.print("  |");
            }

            System.out.println();
        }
    }

    public void importMatrix(String filename)
    {
        // Membaca matriks dari file
        File file = new File(filename);

        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null)
            {
                String[] rowElmts = line.split(" ");
                
                if (rowElmts.length > 0)
                {
                    if (this.colEff == 0)
                    {
                        this.colEff = rowElmts.length;
                    }

                    if (isValidRowInput(rowElmts, this.colEff))
                    {
                        for (int col = 0; col < this.colEff; col++) 
                        {
                            this.matrix[this.rowEff][col] = Double.parseDouble(rowElmts[col]);
                        }
                        this.rowEff++;
                    } 
                }
            }
            reader.close();
        }

        catch (FileNotFoundException e)
        {
            System.out.println("File tidak ditemukan.");
        }

        catch (IOException e)
        {
            System.out.println("Terjadi kesalahan saat membaca file.");
        }
    }

    public void importMatrixWithEmpty(String filename, int nEmpty)
    {
        // Membaca matriks dari file dengan elemen kosong sebanyak nEmpty

        /* nEmpty */
        /* Regresi Linier : nEmpty = 1
        * Interpolasi    : nEmpty = 1
        * Bikubik        : nEmpty = 2
        */

        File file = new File(filename);

        try
        {
            Scanner fileReader = new Scanner(file);
            int nElmt = 0;

            while(fileReader.hasNextLine())
            {
                this.rowEff++;
                Scanner rowReader = new Scanner(fileReader.nextLine());

                while(rowReader.hasNextDouble())
                {
                    nElmt++;
                    rowReader.nextDouble();
                }
                rowReader.close();
            }
            fileReader.close();

            this.colEff = (nElmt + nEmpty) / this.rowEff;

            fileReader = new Scanner(file);
            for (int row = 0; row < this.rowEff; row++)
            {
                for (int col = 0; col < this.colEff; col++)
                {
                    if (fileReader.hasNextDouble())
                    {
                        this.matrix[row][col] = fileReader.nextDouble();
                    }
                }
            }

            for (int k = this.colEff - 1; k >= this.colEff - nEmpty; k--) 
            {
                this.matrix[this.rowEff - 1][k] = -999.0;
            }
            fileReader.close();
        }

        catch (FileNotFoundException e)
        {
            System.out.println("File tidak ditemukan.");
        }
    }

    public void exportMatrix()
    {
        // Menulis matriks ke file
        String filename;
        System.out.print("Masukkan nama file: ");
        filename = input.nextLine() + ".txt";

        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./test/" + filename));

            for (int row = 0; row < this.rowEff; row++)
            {
                for (int col = 0; col < this.colEff; col++)
                {
                    writer.write(this.matrix[row][col] + " ");
                }
                writer.newLine();
            }
            writer.close();
        }

        catch (IOException e)
        {
            System.out.println("Terjadi kesalahan saat menulis file.");
        }
    }
} 