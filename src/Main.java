import Methods.*; 
import ADTMatrix.*;

import java.util.Scanner;

public class Main {

    Scanner in = new Scanner (System.in);
    SPL spl = new SPL();
    Operation op = new Operation();
    Regresi reg = new Regresi();

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
            System.out.println("7. Keluar");
            
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
                if (input <= 0 || input > 7){
                    System.out.println("Input tidak valid! Silahkan input dengan benar.");
                }
            }
            while(input <= 0 || input > 7);

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

        if (M.rowEff > 0 && M.colEff > 0)
        {
            M = spl.gauss(M);
            spl.gaussSPL(M);

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
                spl.gaussSPLFile(M);
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

        if (M.rowEff > 0 && M.colEff > 0)
        {
            M = spl.gaussJordan(M);
            spl.gaussSPL(M);

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
                spl.gaussSPLFile(M);
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
        int dimensi, input;
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

        if (M.rowEff != M.colEff)
        {
            System.out.println("Matriks harus persegi untuk dihitung determinannya!");
        }
        else
        {
            if (M.rowEff > 0 && M.colEff > 0)
            {
            
                // det = determinan.detOBE(M);
                System.out.print("\nDeterminannya adalah: ");
                // System.out.println(det);
    
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
                    // determinan.detFile(M, det);
    
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
    }

    // Fungsi antara untuk memanggil Determinan metode ekspansi kofaktor
    public void DeterminanKofaktor()
    {
        String line;
        String[] row;
        Matrix M = new Matrix();
        int dimensi, input;
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

        if (M.rowEff != M.colEff)
        {
            System.out.println("Matriks harus persegi untuk dihitung determinannya!");
        }
        else
        {
            if (M.rowEff > 0 && M.colEff > 0)
            {
            
                // det = determinan.determinanKofaktor(M);
                System.out.print("\nDeterminannya adalah: ");
                // System.out.println(det);
    
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
                    // determinan.detFile(M, det);
    
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

        if (M.rowEff > 0 && M.colEff > 0)
        {
            if (M.rowEff == 0)
            { // determinan.determinanKofaktor(M) == 0
                System.out.println("Matriks tidak memiliki balikan.");
            }
            else
            {
                // M = balikan.inverseIdentitas(M);
                System.out.print("\nBalikannya adalah: \n");
                M.writeMatrix();
        
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
                    M.exportMatrix();
        
                    case 2:
                    System.out.println("\nKembali ke menu utama...");
                    break;
                }
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

        if (M.rowEff > 0 && M.colEff > 0){
            // Jika determinan dari 
            if (M.rowEff == 0) // determinan.determinanKofaktor(M) == 0
            { 
                System.out.println("Matriks tidak memiliki balikan.");
            }
            else
            {
                // M = balikan.inverseAdjoint(M);
                System.out.print("\nBalikannya adalah: \n");
                M.writeMatrix();
        
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
                    M.exportMatrix();
        
                    case 2:
                    System.out.println("\nKembali ke menu utama...");
                    break;
                }
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

        /* Memberi masukan */
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
            // M = InterpolasiPolinom.inputKeyboard();
            break;
        }

        if (!(M.rowEff == 0 || M.colEff == 0)) 
        {
            // Matrix ai = InterpolasiPolinom.ai(InterpolasiPolinom.xi(InterpolasiPolinom.x(M)),InterpolasiPolinom.fx(M));
            // double a = InterpolasiPolinom.a(M);
            
            // System.out.println("\nHasil Perhitungan Interpolasi Polinom");
            // System.out.println("Penjabaran f(x):");
            // System.out.println(InterpolasiPolinom.fxString(ai));
            // System.out.println("Hasil substitusi dengan nilai x dari masukan:");
            // System.out.println("f("+ a +") = " + InterpolasiPolinom.fa(ai, a));
            
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
                // InterpolasiPolinom.IPFile(ai, a);
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
                reg.quadraticRegressionFile(M);
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
        String line;
        String[] row;

        System.out.println("\nBICUBIC INTERPOLATION");

        int input; 
        Matrix M = new Matrix();
    
        /* Menerima masukan dari file */
        System.out.print("\nMengambil file dari folder test.");
        System.out.print("\nNama file: ");

        String filepath, filename = in.nextLine(), userDirectory = System.getProperty("user.dir");
        filepath = userDirectory + "/test/case/" + filename + ".txt";

        M.importMatrixWithEmpty(filepath, 2);

        /* Proses */
        if (!(M.rowEff == 0 || M.colEff == 0)) {
            // Matrix mAij = bicubicInterpolation.mAij(bicubicInterpolation.m16x1(bicubicInterpolation.m4x4(M)));
            // double a = bicubicInterpolation.getA(M); 
            // double b = bicubicInterpolation.getB(M); 

            // /* Output terminal */
            // System.out.println("\nHasil Bicubic Interpolation");
            // System.out.println("Nilai x dan y dari masukan disubstitusikan, dan hasilnya adalah");
            // System.out.println("f(" + a + "," + b + ") = " + bicubicInterpolation.getFab(mAij, a, b));
            
            /* Output file */
            System.out.println("\nSimpan dalam bentuk file?");
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
                // bicubicInterpolation.bicubicInterFile(mAij, a, b);
        
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
}