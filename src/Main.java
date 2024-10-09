import ADTMatrix.Matrix;
import java.util.Scanner;

public class Main 
{
    static Scanner input = new Scanner (System.in);

    public static void main(String[] args) 
    {
        String line;
        String[] rowElmts, colElmts;
        int option, mRow, nCol;
        Matrix M = new Matrix();

        System.out.println("\nPilih metode masukan:");
        System.out.println("1. File");
        System.out.println("2. Keyboard");
        System.out.println("3. Exit");

        do
        {
            System.out.print(">> ");
            line = input.nextLine();
            rowElmts = line.split(" ");

            try
            {
                option = Integer.parseInt(rowElmts[0]);
            }
            catch (NumberFormatException e)
            {
                option = 0;
            }

            if (option <= 0 || option > 3){
                System.out.println("Masukan tidak valid! Silahkan masukan kembali dengan benar.");
            }
        }
        while (option <= 0 || option > 3);

        switch (option)
        {
            case 1:
            System.out.println("\nMengambil file dari folder test...");
            System.out.println("Nama file (tanpa ekstensi .txt): ");
            System.out.print(">> ");

            String filename = input.nextLine();
            filename = "../test/" + filename + ".txt";
            M.importMatrix(filename);

            System.out.println("\nMatrix yang dibaca:");
            M.writeMatrix();
            break;

            case 2:
            do
            {
                System.out.println("\nMasukkan jumlah baris: ");
                System.out.print(">> ");
                line = input.nextLine();
                rowElmts = line.split(" ");

                try
                {
                    mRow = Integer.parseInt(rowElmts[0]);
                }
                catch (NumberFormatException e)
                {
                    mRow = 0;
                }

                if (mRow <= 0)
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                }
            } 
            while (mRow <= 0);

            do
            {
                System.out.println("\nMasukkan jumlah kolom: ");
                System.out.print(">> ");
                line = input.nextLine();
                colElmts = line.split(" ");

                try
                {
                    nCol = Integer.parseInt(colElmts[0]);
                } 
                catch (NumberFormatException e)
                {
                    nCol = 0;
                }

                if (nCol <= 0)
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                }
            } 
            while (nCol <= 0);

            System.out.println("\nMasukkan nilai dan hasil dari tiap variabel di tiap persamaan:");
            M.readMatrix(mRow, nCol);

            System.out.println("\nMatrix yang dibaca:");
            M.writeMatrix();
            System.err.println();

            break;

            case 3:
            break;
        }
        input.close();
    }
}
