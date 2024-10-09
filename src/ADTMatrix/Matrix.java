package ADTMatrix;

import java.util.Scanner;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Matrix 
{
  public static Scanner input = new Scanner(System.in);
  public int rowEff;
  public int colEff;
  public double [][] matrix;

  public Matrix(int mRow, int nCol) 
  {
      this.rowEff = mRow;
      this.colEff = nCol;
      this.matrix = new double[mRow][nCol];
  }

  public Matrix()
  {
      this.matrix = new double[100][100]; 
      this.rowEff = 0;
      this.colEff = 0;
  }

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

  public void readMatrix(int mRow, int nCol) 
  {
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
      input.close();
  }

  public void writeMatrix()
  {
    int[] columnWidths = new int[this.colEff];
    
    // Find max width for each col
    for (int col = 0; col < this.colEff; col++) {
        for (int row = 0; row < this.rowEff; row++) {
            
            int width = String.format("%.5f", this.matrix[row][col]).length();
            
            if (width > columnWidths[col]) {
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

      for (int col = 0; col < this.colEff; col++) {
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
          if (col < this.colEff - nEmpty)
          {
            this.matrix[row][col] = fileReader.nextDouble();
          }
          else
          {
            this.matrix[row][col] = -999;
          }
        }
      }
      fileReader.close();
    }

    catch (FileNotFoundException e)
    {
      System.out.println("File tidak ditemukan.");
    }
  }

  public void exportMatrix(Matrix matrix)
  {
    String filename;
    System.out.print("Masukkan nama file: ");
    filename = input.nextLine() + ".txt";

    try
    {
      BufferedWriter writer = new BufferedWriter(new FileWriter("./test/" + filename));

      for (int row = 0; row < matrix.rowEff; row++)
      {
        for (int col = 0; col < matrix.colEff; col++)
        {
          writer.write(matrix.matrix[row][col] + " ");
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
