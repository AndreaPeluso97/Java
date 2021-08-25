package soluzione;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
/** Classe che importa il file dei comandi */
public class Comandi {
	/** Lista per contenere i comandi */
	public List<Comando> Comandi;
	/** Costruttore della classe comandi 
	 * @param Comandi Lista comandi 
	 */
	public Comandi(List<Comando> Comandi){//costruttore classe con lista
		this.Comandi=Comandi ;
	}
/** Metodo per importare il file con i comandi nella lista Comandi
 *@param f Stringa contenente il percorso del file */	
public void importa(String f){
	try {
		/** Creazione dello stream file */
		FileInputStream fstream = new FileInputStream(f);
		/** Creazione dello stream data */
		DataInputStream in = new DataInputStream(fstream);
		/** Creazione del buffer */
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		/** Stringa che rappresenta le singole linee di comando */
		String strLine;
		while ((strLine = br.readLine()) != null) {
			/** Vettore string che contiene tutti i dati di un comando */
			String[] splited = strLine.split("\\s+");//splitto riga per ogni spazio
			   Comandi.add(new Comando(splited[0],Integer.parseInt(splited[1])));	
		}
		in.close();
	}
	catch(Exception e) {    
	}
}
}

