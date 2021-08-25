package soluzione;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
/**Classe che importa il file degli eventi*/
public class Eventi {
	/**	lista degli eventi */

	public List<Evento> Eventi;
	/**	
	 *costruttore della lista eventi
	 *@param Eventi lista eventi
	 */
	public Eventi(List<Evento> Eventi){//costruttore classe passando la lista
		this.Eventi=Eventi ;
	}
	
	/**
	 * metodo per importare il file degli eventi
	 * @param f nome del file
	 */
public void importa(String f){//Passo il file
	try {//leggo il file
		/** creazione stream */
		FileInputStream fstream = new FileInputStream(f);//importo gli streams
		DataInputStream in = new DataInputStream(fstream);
		/** creazione buffer */
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		while ((strLine = br.readLine()) != null) {//vado di linea in linea
			/** riga di comando */
			String[] splited = strLine.split("\\s+");//splitto riga per ogni spazio
			if(splited[0].toLowerCase().contains("build")){//se è di tipo build
				/** aggiunta evento alla lista */
			   Eventi.add(new Build(splited[0],Integer.parseInt(splited[1]),splited[2],splited[3],Integer.parseInt(splited[4]),Integer.parseInt(splited[5]),Integer.parseInt(splited[6])));
			}else{//se è demolish
				/** aggiunta evento alla lista */
				Eventi.add(new Demolish(splited[0],Integer.parseInt(splited[1]),splited[2]));
			}
		}
		in.close();//chiudo lo stream
	}
	catch(Exception e) {//gestione eccezzioni      
	}
}
}

