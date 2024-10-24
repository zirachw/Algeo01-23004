import Methods.*; 
import ADTMatrix.*;

import java.util.Scanner;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.File;

public class Main {

    Scanner in = new Scanner (System.in);
    SPL spl = new SPL();
    Inverse inv = new Inverse();
    Operation op = new Operation();
    Determinant det = new Determinant();
    Regression reg = new Regression();
    ImageResizing ir = new ImageResizing();
    BicubicInterpolation bi = new BicubicInterpolation();
    PolynomialInterpolation inter = new PolynomialInterpolation();

    /* MAIN PROGRAM */
    public void main(String[] args) 
    {
        boolean run = true;
        int input = 0;
        String line;
        String[] row = new String[100];

        // Tampilkan pilihan menu
        while (run)
        {
            System.out.print("Rudal Sekeloa Bantai Matrixxx\n");
            System.out.println("\nMENU");
            System.out.println("1. Sistem Persamaan Linear");
            System.out.println("2. Determinan");
            System.out.println("3. Matriks Balikan");
            System.out.println("4. Interpolasi Polinom");
            System.out.println("5. Interpolasi Bicubic Spline");
            System.out.println("6. Regresi Linear Berganda");
            System.out.println("7. Image Resizing");
            System.out.println("8. Keluar");
            
            // Lakukan looping selama input belum valid
            do 
            {
                // Terima input pilihan menu dari pengguna
                System.out.print(">> ");
                line = in.nextLine();
                row = line.split(" ");
                // Lakukan penanganan error
                try{
                    input = Integer.parseInt(row[0]);

                }catch (NumberFormatException e){
                    input = 0;
                }
                if (input <= 0 || input > 8){
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                }
            }
            while(input <= 0 || input > 8);

            switch(input)
            {
                // Bila pengguna input 1, maka masuk ke SPL
                case 1:
                System.out.print("\033[H\033[2J");
                System.out.println("Sistem Persamaan Linear");
                System.out.println("\nPILIH METODE");
                System.out.println("1. Metode Eliminasi Gauss");
                System.out.println("2. Metode Eliminasi Gauss-Jordan");
                System.out.println("3. Metode Matriks Balikan");
                System.out.println("4. Kaidah Cramer");
                System.out.println("5. Kembali Ke Menu");
                // Lakukan looping selama input belum valid
                do{

                    // Terima input pilihan metode SPL dari pengguna
                    System.out.print(">> ");
                    line = in.nextLine();
                    row = line.split(" ");
                    try
                    {
                        input = Integer.parseInt(row[0]);
    
                    }
                    catch(NumberFormatException e)
                    {
                        input = 0;
                    }

                    if (input <= 0 || input > 5)
                    {
                        System.out.println("Input tidak valid! Silahkan input dengan benar.");
                    }

                }
                while(input <= 0 || input > 5);

                switch(input)
                {
                    // Jika pengguna input 1, maka lakukan penyelesaian SPL dengan metode eleminasi Gauss
                    case 1:
                    System.out.print("\033[H\033[2J");
                    SPLGauss();
                    break;
                    
                    // Jika pengguna input 2, maka lakukan penyelesaian SPL dengan metode eleminasi Gauss-Jordan
                    case 2:
                    System.out.print("\033[H\033[2J");
                    SPLGaussJordan();
                    break;
                    
                    // Jika pengguna input 3, maka lakukan penyelesaian SPL dengan metode matriks balikan
                    case 3:
                    System.out.print("\033[H\033[2J");
                    SPLInverse();
                    break;
                    
                    // Jika pengguna input 4, maka lakukan penyelesaian SPL dengan kaidah cramer
                    case 4:
                    System.out.print("\033[H\033[2J");
                    SPLCramer();
                    break;

                    // Jika pengguna input 5, maka kembali ke main menu
                    case 5:
                    System.out.println("\nKembali ke menu utama...");
                    break;
                }
                break;

                case 2:
                System.out.print("\033[H\033[2J");
                System.out.println("Determinan");
                System.out.println("\nPILIH METODE");
                System.out.println("1. Metode OBE");
                System.out.println("2. Metode Kofaktor");
                System.out.println("3. Kembali Ke Menu");
                do
                {
                    System.out.print(">> ");
                    line = in.nextLine();
                    row = line.split(" ");
                    try
                    {
                        input = Integer.parseInt(row[0]);
                    }
                    catch(NumberFormatException e)
                    {
                        input = 0;
                    }

                    if (input <= 0 || input > 3)
                    {
                        System.out.println("Input tidak valid! Silahkan input dengan benar.");
                    }

                }
                while(input <= 0 || input > 533);

                switch(input)
                {
                    // Metode OBE
                    case 1:
                    System.out.print("\033[H\033[2J");
                    DeterminanOBE();
                    break;
                    
                    // Kofaktor
                    case 2:
                    System.out.print("\033[H\033[2J");
                    DeterminanKofaktor();
                    break;

                    case 3:
                    System.out.println("\nKembali ke menu utama...");
                    break;
                }
                break;

                case 3:
                System.out.print("\033[H\033[2J");
                System.out.println("Matriks Balikan");
                System.out.println("\nPILIH METODE");
                System.out.println("1. Metode Identitas");
                System.out.println("2. Metode Adjoint");
                System.out.println("3. Kembali Ke Menu");
                do{
                    System.out.print(">> ");
                    line = in.nextLine();
                    row = line.split(" ");
                    try
                    {
                        input = Integer.parseInt(row[0]);
    
                    }
                    catch(NumberFormatException e)
                    {
                        input = 0;
                    }

                    if (input <= 0 || input > 3)
                    {
                        System.out.println("Input tidak valid! Silahkan input dengan benar.");
                    }

                }
                while(input <= 0 || input > 3);

                switch(input)
                {
                    // Metode eliminasi gauss
                    case 1:
                    System.out.print("\033[H\033[2J");
                    InverseIdentitas();
                    break;
                    
                    // Metode inverse adjoint
                    case 2:
                    System.out.print("\033[H\033[2J");
                    InverseAdjoint();
                    break;

                    case 3:
                    System.out.println("\nKembali ke menu utama...");
                    break;
                }
                break;

                case 4:
                System.out.print("\033[H\033[2J");
                InterpolasiPolinom();
                break;

                case 5:
                System.out.print("\033[H\033[2J");
                BicubicInterpolation();
                break;

                case 6:
                System.out.print("\033[H\033[2J");
                System.out.print("\033[H\033[2J");
                System.out.println("Regresi");
                System.out.println("\nPILIH METODE");
                System.out.println("1. Regresi Linear Berganda");
                System.out.println("2. Regresi Kuadratik Berganda");
                System.out.println("3. Kembali Ke Menu");
                do{
                    System.out.print(">> ");
                    line = in.nextLine();
                    row = line.split(" ");
                    try{
                        input = Integer.parseInt(row[0]);
    
                    } catch(NumberFormatException e){
                        input = 0;
                    }
                    if (input <= 0 || input > 3){
                        System.out.println("Input tidak valid! Silahkan input dengan benar.");
                    }
                }while(input <= 0 || input > 3);

                switch(input){
                    // Metode eliminasi gauss
                    case 1:
                    System.out.print("\033[H\033[2J");
                    RegresiLinear();
                    break;
                    
                    // Metode inverse adjoint
                    case 2:
                    System.out.print("\033[H\033[2J");
                    RegresiKuadratik();
                    break;

                    case 3:
                    System.out.println("\nKembali ke menu utama...");
                    break;
                }
                break;

                case 7:
                System.out.print("\033[H\033[2J");
                Image();

                case 8:
                System.out.print("Sampai Jumpa!");
                run = false;
                break;

            }
        }

    }


    /* FUNGSI ANTARA */
    // Fungsi antara untuk memanggil SPL Gauss
    public void SPLGauss()
    {
        String line;
        String[] row;
        Matrix M = new Matrix();
        int baris, kolom, input = 0;
        Boolean notFirst = false;

        System.out.println("\nPilih metode masukan:");
        System.out.println("1. File");
        System.out.println("2. Keyboard");
        System.out.println("\n3. Kembali Ke Menu");

        do
        {
            System.out.print(">> ");
            line = in.nextLine();
            row = line.split(" ");
            try
            {
                input = Integer.parseInt(row[0]);
            }
            catch (NumberFormatException e)
            {
                input = 0;
            }

            if (input <= 0 || input > 3)
            {
                System.out.println("Input tidak valid! Silahkan input dengan benar.");
            }
        }
        while (input <= 0 || input > 3);

        switch (input)
        {
            case 1:
            System.out.print("\nMengambil file dari folder test.");
            System.out.print("\nNama file: ");

            String filepath, filename = in.nextLine(), userDirectory = System.getProperty("user.dir");
            filepath = userDirectory + "/test/case/" + filename + ".txt";

            M.importMatrix(filepath);
            break;

            case 2:
            do
            {
                System.out.print("\nMasukkan jumlah persamaan: ");
                line = in.nextLine();
                row = line.split(" ");
                try
                {
                    baris = Integer.parseInt(row[0]);
                }
                catch (NumberFormatException e)
                {
                    baris = 0;
                }

                if (baris <= 0)
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                }
            } 
            while (baris <= 0);

            do
            {
                System.out.print("Masukkan jumlah variabel: ");
                line = in.nextLine();
                row = line.split(" ");
                try
                {
                    kolom = Integer.parseInt(row[0]);
                } 
                catch (NumberFormatException e)
                {
                    kolom = 0;
                }

                if (kolom <= 0)
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                }

            } 
            while (kolom <= 0);
            kolom = kolom + 1;

            System.out.print("Masukkan nilai dan hasil dari tiap variabel di tiap persamaan: \n");
            M.readMatrix(baris, kolom);
            break;

            case 3:
            break;
        }
        Matrix temp = op.copyMatrix(M);
        if (M.rowEff > 0 && M.colEff > 0)
        {
            M = spl.gauss(M);
            spl.gaussSPL(M, temp);

            System.out.println();
            System.out.println("Simpan dalam bentuk file?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak");

            do
            {
                System.out.print(">> ");
                line = in.nextLine();
                row = line.split(" ");
                try
                {
                    input = Integer.parseInt(row[0]);
                } 
                catch (NumberFormatException e)
                {
                    input = 0;
                }

                if ((input <= 0 || input > 2) && notFirst)
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                }
                notFirst = true;
            } 
            while(input <= 0 || input > 2);

            switch (input)
            {
                case 1:
                spl.gaussSPLFile(M, temp);
                break;
                
                case 2:
                System.out.println("\nKembali ke menu utama");
                break;
            }
        }
        else
        {
            System.out.println("Operasi gagal, kembali ke menu utama...");
        }
    }

    // Fungsi antara untuk memanggil SPL Gauss Jordan
    public void SPLGaussJordan()
    {
        String line;
        String[] row;
        Matrix M = new Matrix();
        int baris, kolom, input;
        Boolean notFirst = false;

        System.out.println("\nPilih metode masukan:");
        System.out.println("1. File");
        System.out.println("2. Keyboard");
        System.out.println("\n3. Kembali Ke Menu");

        do
        {
            System.out.print(">> ");
            line = in.nextLine();
            row = line.split(" ");
            try
            {
                input = Integer.parseInt(row[0]);
            }
            catch (NumberFormatException e)
            {
                input = 0;
            }
            if (input <= 0 || input > 3)
            {
                System.out.println("Input tidak valid! Silahkan input dengan benar.");
            }
        }
        while (input <= 0 || input > 3);

        switch (input)
        {
            case 1:
            System.out.print("\nMengambil file dari folder test.");
            System.out.print("\nNama file: ");

            String filepath, filename = in.nextLine(), userDirectory = System.getProperty("user.dir");
            filepath = userDirectory + "/test/case/" + filename + ".txt";

            M.importMatrix(filepath);
            break;

            case 2:
            do
            {
                System.out.print("\nMasukkan jumlah persamaan: ");
                line = in.nextLine();
                row = line.split(" ");
                try
                {
                    baris = Integer.parseInt(row[0]);
                }
                catch (NumberFormatException e)
                {
                    baris = 0;
                }

                if (baris <= 0)
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                }
            } 
            while (baris <= 0);

            do
            {
                System.out.print("Masukkan jumlah variabel: ");
                line = in.nextLine();
                row = line.split(" ");
                try
                {
                    kolom = Integer.parseInt(row[0]);
                } 
                catch (NumberFormatException e)
                {
                    kolom = 0;
                }

                if (kolom <= 0)
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                }
            } 
            while (kolom <= 0);
            kolom = kolom + 1;

            System.out.print("Masukkan nilai dan hasil dari tiap variabel di tiap persamaan: \n");
            M.readMatrix(baris, kolom);
            break;

            case 3:
            break;
        }
        Matrix temp = op.copyMatrix(M);
        if (M.rowEff > 0 && M.colEff > 0)
        {
            M = spl.gaussJordan(M);
            spl.gaussSPL(M, temp);

            System.out.println();
            System.out.println("Simpan dalam bentuk file?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak");
            do
            {
                System.out.print(">> ");
                line = in.nextLine();
                row = line.split(" ");
                try
                {
                    input = Integer.parseInt(row[0]);
                } 
                catch (NumberFormatException e)
                {
                    input = 0;
                }
                if ((input <= 0 || input > 2) && notFirst)
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                }
                notFirst = true;
            } 
            while(input <= 0 || input > 2);

            switch (input)
            {
                case 1:
                spl.gaussSPLFile(M, temp);
                break;
                
                case 2:
                System.out.println("\nKembali ke menu utama");
                break;
            }
        }
        else
        {
            System.out.println("Operasi gagal, kembali ke menu utama...");
        }
    }

    // Fungsi antara untuk memanggil SPL Inverse
    public void SPLInverse()
    {
        String line;
        String[] row;
        Matrix M = new Matrix();
        int baris, kolom, input;
        Boolean notFirst = false;

        System.out.println("\nPilih metode masukan:");
        System.out.println("1. File");
        System.out.println("2. Keyboard");
        System.out.println("\n3. Kembali Ke Menu");

        do
        {
            System.out.print(">>");
            line = in.nextLine();
            row = line.split(" ");
            try
            {
                input = Integer.parseInt(row[0]);
            }
            catch (NumberFormatException e)
            {
                input = 0;
            }

            if (input <= 0 || input > 3)
            {
                System.out.println("Input tidak valid! Silahkan input dengan benar.");
            }
        }
        while (input <= 0 || input > 3);

        switch (input)
        {
            case 1:
            System.out.print("\nMengambil file dari folder test.");
            System.out.print("\nNama file: ");

            String filepath, filename = in.nextLine(), userDirectory = System.getProperty("user.dir");
            filepath = userDirectory + "/test/case/" + filename + ".txt";

            M.importMatrix(filepath);
            break;

            case 2:
            do
            {
                System.out.print("\nMasukkan jumlah persamaan: ");
                line = in.nextLine();
                row = line.split(" ");
                try
                {
                    baris = Integer.parseInt(row[0]);
                }
                catch (NumberFormatException e)
                {
                    baris = 0;
                }
                if (baris <= 0)
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                }
            } 
            while (baris <= 0);

            do
            {
                System.out.print("Masukkan jumlah variabel: ");
                line = in.nextLine();
                row = line.split(" ");
                try
                {
                    kolom = Integer.parseInt(row[0]);
                } 
                catch (NumberFormatException e)
                {
                    kolom = 0;
                }

                if (kolom <= 0)
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                }
            }
            while (kolom <= 0);
            kolom = kolom + 1;

            System.out.print("Masukkan nilai dan hasil dari tiap variabel di tiap persamaan: \n");
            M.readMatrix(baris, kolom);
            break;

            case 3:
            break;
        }

        if (M.rowEff > 0 && M.colEff > 0)
        {
            spl.InverseSPL(M);

            System.out.println("Simpan dalam bentuk file?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak");
            do
            {
                System.out.print(">> ");
                line = in.nextLine();
                row = line.split(" ");
                try
                {
                    input = Integer.parseInt(row[0]);
                } 
                catch (NumberFormatException e)
                {
                    input = 0;
                }

                if ((input <= 0 || input > 2) && notFirst)
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                }
                notFirst = true;
            } 
            while(input <= 0 || input > 2);

            switch (input)
            {
                case 1:
                spl.InverseSPLFile(M);
                break;
                
                case 2:
                System.out.println("\nKembali ke menu utama");
                break;
            }
        }
        else
        {
            System.out.println("Operasi gagal, kembali ke menu utama...");
        }
    }

    // Fungsi antara untuk memanggil SPL Cramer
    public void SPLCramer()
    {
        String line;
        String[] row;
        Matrix M = new Matrix();
        int input, baris, kolom;
        Boolean notFirst = false;

        System.out.println("\nPilih metode masukan:");
        System.out.println("1. File");
        System.out.println("2. Keyboard");
        System.out.println("\n3. Kembali Ke Menu");
        
        do
        {
            System.out.print(">>");
            line = in.nextLine();
            row = line.split(" ");
            try 
            {
                input = Integer.parseInt(row[0]);
            } 
            catch (NumberFormatException e) 
            {
                input = 0;
            }
            if (input <= 0 || input > 3) 
            {
                System.out.println("Input tidak valid! Silahkan input dengan benar.");
            } 
        }
        while (input <= 0 || input > 3);

        switch (input)
        {
            case 1:
            System.out.print("\nMengambil file dari folder test.");
            System.out.print("\nNama file: ");

            String filepath, filename = in.nextLine(), userDirectory = System.getProperty("user.dir");
            filepath = userDirectory + "/test/case/" + filename + ".txt";

            M.importMatrix(filepath);
            break;

            case 2:
            do
            {
                System.out.print("\nMasukkan jumlah persamaan: ");
                line = in.nextLine();
                row = line.split(" ");
                try 
                {
                    baris = Integer.parseInt(row[0]);
                } catch (NumberFormatException e) 
                {
                    baris = 0;
                }

                if (baris <= 0) 
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                } 
            } 
            while (baris <= 0);

            do
            {
                System.out.print("Masukkan jumlah variabel: ");
                line = in.nextLine();
                row = line.split(" ");
                try 
                {
                    kolom = Integer.parseInt(row[0]);
                } 
                catch (NumberFormatException e) 
                {
                    kolom = 0;
                }

                if (kolom <= 0) 
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                } 
            } 
            while (kolom <= 0);
            kolom = kolom + 1;
    
            System.out.print("Masukkan nilai koefisien dan hasil dari tiap variabel di tiap persamaan: \n");
            M.readMatrix(baris, kolom);
            break;

            case 3:
            break;
        }


        if (M.rowEff > 0 && M.colEff > 0)
        {
            spl.CramerSPL(M);

            System.out.println("Simpan dalam bentuk file?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak");
            do
            {
                System.out.print(">> ");
                line = in.nextLine();
                row = line.split(" ");
                try 
                {
                    input = Integer.parseInt(row[0]);
                } 
                catch (NumberFormatException e) 
                {
                    input = 0;
                }

                if ((input <= 0 || input > 2) && notFirst) 
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                }
                notFirst = true;
            } 
            while (input <= 0 || input > 2);

            switch (input)
            {
                case 1:
                spl.CramerSPLFile(M);

                case 2:
                System.out.println("\nKembali ke menu utama...");
                break;
            }
        }
        else
        {
            System.out.println("Operasi gagal, kembali ke menu utama...");
        }
    }
    
    // Fungsi antara untuk memanggil Determinan metode OBE
    public void DeterminanOBE()
    {
        String line;
        String[] row;
        Matrix M = new Matrix();
        int input, baris, kolom;
        double detResult;
        BigDecimal roundDet = new BigDecimal(0);

        // double det;
        System.out.println("\nPilih metode masukan:");
        System.out.println("1. File");
        System.out.println("2. Keyboard");
        System.out.println("\n3. Kembali Ke Menu");
        
        do
        {
            System.out.print(">>");
            line = in.nextLine();
            row = line.split(" ");
            try 
            {
                input = Integer.parseInt(row[0]);
            } 
            catch (NumberFormatException e) 
            {
                input = 0;
            }

            if (input <= 0 || input > 3) 
            {
                System.out.println("Input tidak valid! Silahkan input dengan benar.");
            } 
        } 
        while (input <= 0 || input > 3);

        switch (input)
        {
            case 1:
            System.out.print("\nMengambil file dari folder test.");
            System.out.print("\nNama file: ");

            String filepath, filename = in.nextLine(), userDirectory = System.getProperty("user.dir");
            filepath = userDirectory + "/test/case/" + filename + ".txt";

            M.importMatrix(filepath);
            break;

            case 2:
            do
            {
                System.out.print("\nMasukkan jumlah baris: ");
                line = in.nextLine();
                row = line.split(" ");
                try 
                {
                    baris = Integer.parseInt(row[0]);
                } catch (NumberFormatException e) 
                {
                    baris = 0;
                }

                if (baris <= 0) 
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                } 
            } 
            while (baris <= 0);

            do
            {
                System.out.print("Masukkan jumlah kolom: ");
                line = in.nextLine();
                row = line.split(" ");
                try 
                {
                    kolom = Integer.parseInt(row[0]);
                } 
                catch (NumberFormatException e) 
                {
                    kolom = 0;
                }

                if (kolom <= 0) 
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                } 
            } 
            while (kolom <= 0);
    
            System.out.print("Masukkan nilai-nilai dari matriks per baris. Contoh jika 3x3: 1 0 1 \n");
            M.readMatrix(baris, kolom);
            break;

            case 3:
            break;
        }

        if (M.rowEff > 0 && M.colEff > 0)
        {
            if (M.rowEff != M.colEff)
            {
                System.out.println("Matriks harus persegi untuk dihitung determinannya!");
            }
            else
            {
                detResult = det.determinantOBE(M);
                roundDet = new BigDecimal(detResult).setScale(10, RoundingMode.HALF_UP);
                roundDet = roundDet.stripTrailingZeros();
                System.out.print("\nDeterminannya adalah: ");
                System.out.println(roundDet.toPlainString());
            }

            System.out.println("Simpan dalam bentuk file?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak");
            do
            {
                System.out.print(">>");
                line = in.nextLine();
                row = line.split(" ");
                try 
                {
                    input = Integer.parseInt(row[0]);
                } 
                catch (NumberFormatException e) 
                {
                    input = 0;
                }

                if (input <= 0 || input > 2) 
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                } 
            } 
            while (input <= 0 || input > 2);

            switch (input)
            {
                case 1:
                det.exportDet(M, roundDet);

                case 2:
                System.out.println("\nKembali ke menu utama...");
                break;
            }
        }
        else
        {
            System.out.println("Operasi gagal, kembali ke menu utama...");
        }
    }

    // Fungsi antara untuk memanggil Determinan metode ekspansi kofaktor
    public void DeterminanKofaktor()
    {
        String line;
        String[] row;
        Matrix M = new Matrix();
        int input, baris, kolom;
        double detResult;
        BigDecimal roundDet = new BigDecimal(0);

        // double det;
        System.out.println("\nPilih metode masukan:");
        System.out.println("1. File");
        System.out.println("2. Keyboard");
        System.out.println("\n3. Kembali Ke Menu");
        
        do
        {
            System.out.print(">>");
            line = in.nextLine();
            row = line.split(" ");
            try 
            {
                input = Integer.parseInt(row[0]);
            } 
            catch (NumberFormatException e) 
            {
                input = 0;
            }

            if (input <= 0 || input > 3) 
            {
                System.out.println("Input tidak valid! Silahkan input dengan benar.");
            } 
        } 
        while (input <= 0 || input > 3);

        switch (input)
        {
            case 1:
            System.out.print("\nMengambil file dari folder test.");
            System.out.print("\nNama file: ");

            String filepath, filename = in.nextLine(), userDirectory = System.getProperty("user.dir");
            filepath = userDirectory + "/test/case/" + filename + ".txt";

            M.importMatrix(filepath);
            break;

            case 2:
            do
            {
                System.out.print("\nMasukkan jumlah baris: ");
                line = in.nextLine();
                row = line.split(" ");
                try 
                {
                    baris = Integer.parseInt(row[0]);
                } catch (NumberFormatException e) 
                {
                    baris = 0;
                }

                if (baris <= 0) 
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                } 
            } 
            while (baris <= 0);

            do
            {
                System.out.print("Masukkan jumlah kolom: ");
                line = in.nextLine();
                row = line.split(" ");
                try 
                {
                    kolom = Integer.parseInt(row[0]);
                } 
                catch (NumberFormatException e) 
                {
                    kolom = 0;
                }

                if (kolom <= 0) 
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                } 
            } 
            while (kolom <= 0);
    
            System.out.print("Masukkan nilai-nilai dari matriks per baris. Contoh jika 3x3: 1 0 1 \n");
            M.readMatrix(baris, kolom);
            break;

            case 3:
            break;
        }

        if (M.rowEff > 0 && M.colEff > 0)
        {
            if (M.rowEff != M.colEff)
            {
                System.out.println("Matriks harus persegi untuk dihitung determinannya!");
            }
            else
            {
                detResult = det.determinantCofactor(M);
                roundDet = new BigDecimal(detResult).setScale(10, RoundingMode.HALF_UP);
                roundDet = roundDet.stripTrailingZeros();
                System.out.print("\nDeterminannya adalah: ");
                System.out.println(roundDet.toPlainString());
            }

            System.out.println("Simpan dalam bentuk file?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak");
            do
            {
                System.out.print(">>");
                line = in.nextLine();
                row = line.split(" ");
                try 
                {
                    input = Integer.parseInt(row[0]);
                } 
                catch (NumberFormatException e) 
                {
                    input = 0;
                }

                if (input <= 0 || input > 2) 
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                } 
            } 
            while (input <= 0 || input > 2);

            switch (input)
            {
                case 1:
                det.exportDet(M, roundDet);

                case 2:
                System.out.println("\nKembali ke menu utama...");
                break;
            }
        }
        else
        {
            System.out.println("Operasi gagal, kembali ke menu utama...");
        }
    }
    
    // Fungsi antara untuk memanggil Matriks Balikan metode inverse identitas
    public void InverseIdentitas()
    {
        String line;
        String[] row;
        Matrix M = new Matrix();
        int dimensi, input = 0;
        System.out.println("\nPilih metode masukan:");
        System.out.println("1. File");
        System.out.println("2. Keyboard");
        System.out.println("\n3. Kembali Ke Menu");
        
        do
        {
            System.out.print(">>");
            line = in.nextLine();
            row = line.split(" ");
            try 
            {
                input = Integer.parseInt(row[0]);
            } 
            catch (NumberFormatException e) 
            {
                input = 0;
            }

            if (input <= 0 || input > 3) 
            {
                System.out.println("Input tidak valid! Silahkan input dengan benar.");
            } 
        } 
        while (input <= 0 || input > 3);

        switch (input)
        {
            case 1:
            System.out.print("\nMengambil file dari folder test.");
            System.out.print("\nNama file: ");

            String filepath, filename = in.nextLine(), userDirectory = System.getProperty("user.dir");
            filepath = userDirectory + "/test/case/" + filename + ".txt";

            M.importMatrix(filepath);
            break;

            case 2:
            do
            {
                System.out.print("\nMasukkan dimensi matriks: ");
                line = in.nextLine();
                row = line.split(" ");
                try 
                {
                    dimensi = Integer.parseInt(row[0]);
                } 
                catch (NumberFormatException e) 
                {
                    dimensi = 0;
                }

                if (dimensi <= 0) 
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                } 
            } 
            while (dimensi <= 0);
    
            System.out.print("Masukkan nilai elemen pada matriks: \n");
            M.readMatrix(dimensi, dimensi);
            break;

            case 3:
            break;
        }
        
        Matrix inverse = op.copyMatrix(M);

        if (M.rowEff > 0 && M.colEff > 0)
        {
            if (M.rowEff != M.colEff)
            {
                System.out.println("Matriks tidak memiliki invers karena bukan matriks persegi.");
            }
            else if (det.determinantOBE(M) == 0)
            {
                System.out.println("Matriks tidak memiliki invers karena determinan bernilai 0.");
            }
            else
            {
                inverse = inv.inverseGJ(M);
                System.out.print("\nBalikannya adalah: \n");
                inverse.writeMatrix();
            }
        
            System.out.println("Simpan dalam bentuk file?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak");
            do
            {
                System.out.print(">>");
                line = in.nextLine();
                row = line.split(" ");
                try 
                {
                    input = Integer.parseInt(row[0]);
                } 
                catch (NumberFormatException e) 
                {
                    input = 0;
                }
                if (input <= 0 || input > 2) 
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                } 
            } 
            while (input <= 0 || input > 2);
    
            switch (input){
                case 1:
                inv.exportInverse(M, inverse);
    
                case 2:
                System.out.println("\nKembali ke menu utama...");
                break;
            }
        }
        else
        {
            System.out.println("Operasi gagal, kembali ke menu utama...");
        }
    }

    // Fungsi antara untuk memanggil Matriks Balikan metode inverse adjoint
    public void InverseAdjoint()
    {
        String line;
        String[] row;
        Matrix M = new Matrix();
        int dimensi, input = 0;
        System.out.println("\nPilih metode masukan:");
        System.out.println("1. File");
        System.out.println("2. Keyboard");
        System.out.println("\n3. Kembali Ke Menu");
        
        do
        {
            System.out.print(">>");
            line = in.nextLine();
            row = line.split(" ");
            try 
            {
                input = Integer.parseInt(row[0]);
            } 
            catch (NumberFormatException e) 
            {
                input = 0;
            }

            if (input <= 0 || input > 3) 
            {
                System.out.println("Input tidak valid! Silahkan input dengan benar.");
            } 
        } 
        while (input <= 0 || input > 3);

        switch (input)
        {
            case 1:
            System.out.print("\nMengambil file dari folder test.");
            System.out.print("\nNama file: ");

            String filepath, filename = in.nextLine(), userDirectory = System.getProperty("user.dir");
            filepath = userDirectory + "/test/case/" + filename + ".txt";

            M.importMatrix(filepath);
            break;

            case 2:
            do
            {
                System.out.print("\nMasukkan dimensi matriks: ");
                line = in.nextLine();
                row = line.split(" ");
                try 
                {
                    dimensi = Integer.parseInt(row[0]);
                } 
                catch (NumberFormatException e) 
                {
                    dimensi = 0;
                }

                if (dimensi <= 0) 
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                } 
            } 
            while (dimensi <= 0);
    
            System.out.print("Masukkan nilai elemen pada matriks: \n");
            M.readMatrix(dimensi, dimensi);
            break;

            case 3:
            break;
        }
        
        Matrix inverse = op.copyMatrix(M);

        if (M.rowEff > 0 && M.colEff > 0)
        {
            if (M.rowEff != M.colEff)
            {
                System.out.println("Matriks tidak memiliki invers karena bukan matriks persegi.");
            }
            else if (det.determinantOBE(M) == 0)
            {
                System.out.println("Matriks tidak memiliki invers karena determinan bernilai 0.");
            }
            else
            {
                inverse = inv.inverseDet(M);
                System.out.print("\nBalikannya adalah: \n");
                inverse.writeMatrix();
            }
        
            System.out.println("Simpan dalam bentuk file?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak");
            do
            {
                System.out.print(">>");
                line = in.nextLine();
                row = line.split(" ");
                try 
                {
                    input = Integer.parseInt(row[0]);
                } 
                catch (NumberFormatException e) 
                {
                    input = 0;
                }
                if (input <= 0 || input > 2) 
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                } 
            } 
            while (input <= 0 || input > 2);
    
            switch (input){
                case 1:
                inv.exportInverse(M, inverse);
    
                case 2:
                System.out.println("\nKembali ke menu utama...");
                break;
            }
        }
        else
        {
            System.out.println("Operasi gagal, kembali ke menu utama...");
        }
    }

    // Fungsi antara untuk memanggil Interpolasi Polinom
    public void InterpolasiPolinom()
    {
        String line;
        String[] row;

        /* Memilih metode input */
        System.out.println("\nPilih metode masukan:");
        System.out.println("1. File");
        System.out.println("2. Keyboard");
        System.out.println("\n3. Kembali Ke Menu");

        int input;
        Matrix M = new Matrix();

        do
        {
            System.out.print(">> ");
            line = in.nextLine();
            row = line.split(" ");
            try 
            {
                input = Integer.parseInt(row[0]);
            } 
            catch (NumberFormatException e) 
            {
                input = 0;
            }
            if (input <= 0 || input > 3) 
            {
                System.out.println("Input tidak valid! Silahkan input dengan benar.");
            } 
        } 
        while (input <= 0 || input > 3);

        /* Memberi masukan */
        switch (input) 
        {
            case 1:
            System.out.print("\nMengambil file dari folder test.");
            System.out.print("\nNama file: ");

            String filepath, filename = in.nextLine(), userDirectory = System.getProperty("user.dir");
            filepath = userDirectory + "/test/case/" + filename + ".txt";

            M.importMatrixWithEmpty(filepath, 1);
            M.writeMatrix();
            break;

            case 2:
            M = inter.localInput();
            break;
        }

        if (!(M.rowEff == 0 || M.colEff == 0)) {
            M.writeMatrix();
            Matrix x = inter.takeX(M);
            Matrix fx = inter.takeFx(M);
            x.writeMatrix();
            fx.writeMatrix();
            // Mendapatkan nilai x yang akan diinterpolasi
            double xInterpolasi = inter.takeXValue(M);

            // Membangun matriks xi berdasarkan nilai x
            Matrix xi = inter.xi(x);

            // Menghitung koefisien polinom cf
            Matrix cf = inter.calculateCoeff(xi, fx);

            // Menghitung f(x) pada titik yang diinterpolasi
            double hasilInterpolasi = inter.calculateFx(cf, xInterpolasi);
            // Menampilkan polinom yang terbentuk
            String polinom = inter.showPolynom(cf);
            System.out.println(polinom);

            // Menampilkan hasil interpolasi
            System.out.println("Nilai f(" + xInterpolasi + ") = " + hasilInterpolasi);
        
            /* Output file */
            System.out.println("\nApakah ingin dalam bentuk file?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak");
            do
            {
                line = in.nextLine();
                row = line.split(" ");
                try 
                {
                    input = Integer.parseInt(row[0]);
                } 
                catch (NumberFormatException e) 
                {
                    input = 0;
                }

                if (input <= 0 || input > 2) 
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                } 
            } 
            while (input <= 0 || input > 2);
        
            switch (input)
            {
                case 1:
                inter.interpolationFile(M);
                break;
        
                case 2:
                System.out.println("\nKembali ke menu utama...");
                break;
            }

        } 
        else 
        {
            System.out.println("\nOperasi gagal, kembali ke menu utama...");
        }
    }

    // Fungsi antara untuk memanggil Regresi Linear Berganda
    public void RegresiLinear()
    {
        Matrix M = new Matrix();
        int input;
        String mark;
        String[] baris;

        System.out.println("\nPilih metode masukan:");
        System.out.println("1. File");
        System.out.println("2. Keyboard");
        System.out.println("\n3. Kembali Ke Menu");

        do
        {
            System.out.print(">>");
            mark = in.nextLine();
            baris = mark.split(" ");
            try 
            {
                input = Integer.parseInt(baris[0]);
            } 
            catch (NumberFormatException e) 
            {
                input = 0;
            }

            if (input <= 0 || input > 3) 
            {
                System.out.println("Pilihan Anda tidak valid, Silahkan Ulangi!");
            } 
        } 
        while (input <= 0 || input > 3);

        switch (input) 
        {
            case 1:
            System.out.print("\nMengambil file dari folder test.");
            System.out.print("\nNama file: ");

            String filepath, filename = in.nextLine(), userDirectory = System.getProperty("user.dir");
            filepath = userDirectory + "/test/case/" + filename + ".txt";

            M.importMatrixWithEmpty(filepath, 1);
            break;

            case 2:
            M = reg.inputLinearRegression();
            break;

            case 3:
            break;
        }

        /* Proses */
        if (!(M.rowEff == 0 || M.rowEff == 0)) 
        {
            //output terminal biasa
            System.out.println("\nHasil Perhitungan Regresi Linear Berganda");
            System.out.println("Persamaan regresi linear berganda f(x):");
            reg.linearRegression(M);
            
           //Output File
            System.out.println("\nSimpan dalam bentuk file?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak");
            do
            {
                System.out.print(">>");
                mark = in.nextLine();
                baris = mark.split(" ");
                try 
                {
                    input = Integer.parseInt(baris[0]);
                } 
                catch (NumberFormatException e) 
                {
                    input = 0;                    
                }

                if (input <= 0 || input > 2) 
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                } 
            } 
            while (input <= 0 || input > 2);
    
            switch (input)
            {
                case 1:
                reg.linearRegressionFile(M);
                break;
    
                case 2:
                System.out.println("\nKembali ke menu utama...");
                break;
            }
        } 
        else 
        {
            System.out.println("\nOperasi gagal, kembali ke menu utama...");
        }
    }


    // Fungsi antara untuk memanggil Regresi Linear Berganda
    public void RegresiKuadratik()
    {
        Matrix M = new Matrix();
        int input;
        String mark;
        String[] baris;

        System.out.println("\nPilih metode masukan:");
        System.out.println("1. File");
        System.out.println("2. Keyboard");
        System.out.println("\n3. Kembali Ke Menu");

        do
        {
            System.out.print(">>");
            mark = in.nextLine();
            baris = mark.split(" ");
            try {
                input = Integer.parseInt(baris[0]);
            } catch (NumberFormatException e) {
                input = 0;
            }
            if (input <= 0 || input > 3) {
                System.out.println("Pilihan Anda tidak valid, Silahkan Ulangi!");
            } 
        } 
        while (input <= 0 || input > 3);


        switch (input) 
        {
            case 1:
            System.out.print("\nMengambil file dari folder test.");
            System.out.print("\nNama file: ");

            String filepath, filename = in.nextLine(), userDirectory = System.getProperty("user.dir");
            filepath = userDirectory + "/test/case/" + filename + ".txt";

            M.importMatrixWithEmpty(filepath, 1);
            break;

            case 2:
            M = reg.inputQuadraticRegression();
            break;

            case 3:
            break;
        }

        /* Proses */
        if (!(M.rowEff == 0 || M.rowEff == 0)) 
        {
            System.out.println("Persamaan regresi linear berganda f(x):");
            Matrix beta = reg.QuadraticRegression(M);
            Matrix y = new Matrix(M.colEff - 1, 1);

            for (int i = 0; i < M.colEff - 1; i++) 
            {
                y.matrix[i][0] = M.matrix[M.rowEff - 1][i];
            }
            reg.calcFxQuadratic(beta, y);
            
           //Output File
            System.out.println("\nSimpan dalam bentuk file?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak");
            do
            {
                System.out.print(">>");
                mark = in.nextLine();
                baris = mark.split(" ");
                try 
                {
                    input = Integer.parseInt(baris[0]);
                } 
                catch (NumberFormatException e) 
                {
                    input = 0;                    
                }

                if (input <= 0 || input > 2) 
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                } 
            } 
            while (input <= 0 || input > 2);
    
            switch (input)
            {
                case 1:
                reg.quadraticRegressionFile(M, y);
                break;
    
                case 2:
                System.out.println("\nKembali ke menu utama...");
                break;
            }
        } 
        else 
        {
            System.out.println("\nOperasi gagal, kembali ke menu utama...");
        }
    }

    // Fungsi antara untuk memanggil Bicubic Interpolation
    public void BicubicInterpolation()
    {
        Matrix M = new Matrix();
        int input;
        String mark;
        String[] baris;

        System.out.println("\nPilih metode masukan:");
        System.out.println("1. File");
        System.out.println("2. Keyboard");
        System.out.println("\n3. Kembali Ke Menu");

        do
        {
            System.out.print(">>");
            mark = in.nextLine();
            baris = mark.split(" ");
            try 
            {
                input = Integer.parseInt(baris[0]);
            } 
            catch (NumberFormatException e) 
            {
                input = 0;
            }

            if (input <= 0 || input > 3) 
            {
                System.out.println("Pilihan Anda tidak valid, Silahkan Ulangi!");
            } 
        } 
        while (input <= 0 || input > 3);
    
        switch (input) 
        {
            case 1:
            System.out.print("\nMengambil file dari folder test.");
            System.out.print("\nNama file: ");

            String filepath, filename = in.nextLine(), userDirectory = System.getProperty("user.dir");
            filepath = userDirectory + "/test/case/" + filename + ".txt";

            M.importMatrixWithEmpty(filepath, 2);
            break;

            case 2:
            M = bi.inputBicubic();
            break;

            case 3:
            break;
        }

        /* Proses */
        if (M.rowEff == 5 || M.colEff == 4) {
            Matrix matrixF = bi.getMatrixF(M);
            double a = bi.getA(M); 
            double b = bi.getB(M); 

            Matrix Ma_ij = bi.getMatrixAij(matrixF);
            double result = bi.getFabResult(Ma_ij, a, b);

            /* Output terminal */
            System.out.println("Hasil dari f(" +String.valueOf(a)+","+ String.valueOf(b)+ "): " +String.valueOf(result));
            
            /* Output file */
            System.out.println("\nSimpan dalam bentuk file?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak");
            
            do
            {
                System.out.print(">>");
                mark = in.nextLine();
                baris = mark.split(" ");
                try 
                {
                    input = Integer.parseInt(baris[0]);
                } 
                catch (NumberFormatException e) 
                {
                    input = 0;
                }

                if (input <= 0 || input > 2) 
                {
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                } 
            } 
            while (input <= 0 || input > 2);
        
            switch (input)
            {
                case 1:
                bi.exportBicubic(Ma_ij, a, b);
        
                case 2:
                System.out.println("\nKembali ke menu utama...");
                break;
            }

        } 
        else 
        {
            System.out.println("\nOperasi gagal, kembali ke menu utama...");
        }
    }

    public void Image()
    {
        /* Menerima masukan dari file */
        System.out.print("\nMengambil file dari folder test.");
        System.out.print("\nNama file (dengan extensionnya. Misal: .jpg) >> ");

        String sourceImg = in.nextLine();
        String imgDirectory = System.getProperty("user.dir") + "/test/img/";
        File imgFile = new File(imgDirectory + sourceImg);
        ir.imageProccesing(imgFile);
    }
}