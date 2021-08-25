package ListaDir;


import java.io.*;

public class ListaDir {

  public static void main(String[] args) throws IOException {
    
    File f = null;
    
    //se non ci sono argomenti consideriamo la directory corrente
    if (args.length == 0)
      f = new File(".");
    else 
      f = new File(args[0]);

    //se il nome specificato individua ...
    if (f.isFile()){
      // un file allora ne stampa nome e dimensione 
    	System.out.println("File: " + f.getAbsolutePath() + ", " + f.length() + " byte");
    }
    else {
      // una dir allora preleva la lista dei file
    	System.out.println("Directory: " + f.getAbsolutePath());

      String[] lista = f.list();
      
      //stampa
      for(int i=0; i < lista.length; i++){
        File tmp = new File(f.getAbsolutePath(), lista[i]);
        if (tmp.isFile())
        	System.out.println("          file: " + tmp.getName() + " " + tmp.length()
                      + " byte");
        else
          System.out.println("dir.: " + tmp.getName());
      }
    }
  }
}
