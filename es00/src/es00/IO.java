package es00;

import java.io.*;

class IO {

    public static void main(String[] args) throws IOException {
        // costruiamo un buffered reader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //stream per la scrittura
        PrintStream out = System.out;
        out.print("Scrivi un numero > ");

        //lettura
        String s = br.readLine();
        int n = Integer.parseInt(s);

        //stampa
        out.println("Numero scritto > " + n);
    }
}
