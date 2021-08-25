package Copiafile;

import java.io.*;

class CopiaFileArgs {

    public static void main(String[] args) throws IOException {

        if (args.length == 2) {
            //stream lettura da file
            FileReader frd = new FileReader(args[0]);

            //stream scrittura su file
            FileWriter fwr = new FileWriter(args[1]);

            int i;
            //lettura e scrittura
            while ((i = frd.read()) != -1) {
                fwr.write(i);
            }

            //chiusura degli stream
            frd.close();
            fwr.flush();
            fwr.close();

        } else {
            System.out.println("Uso: java CopiaFileArgs <file_da_copiare> <file_copia>");
        }
    }
}

