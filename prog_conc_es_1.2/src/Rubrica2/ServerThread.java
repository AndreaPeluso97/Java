package Rubrica2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread implements Runnable{
	

	Socket s;
	Rubrica rub;
	ObjectOutputStream oos;
	ObjectInputStream ois;

	

	public ServerThread(Socket sac, Rubrica r) {
		
		s=sac;
		rub=r;
		try {
			oos=new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			ois=new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void run() {
		
		
		String str = null;
		numero num = null;
		String chi = null;
		boolean inRub = false; 
		
	while(true){	
		try {
			str = (String)ois.readObject();
			if(str.equals("FineServizio")) {
				break;
			}
			if(str.equals("Aggiungi")) {
				num = (numero)ois.readObject();
				rub.aggiungiNumero(num.getChi(), num.getNum());
			}
			else if(str.equals("Trova")) {
				str = (String)ois.readObject();
				chi = rub.trova(str);
				oos.writeObject(chi);
			}
			else if(str.equals("Elimina")) {
				str = (String)ois.readObject();
				rub.eliminaNumero(str);
			}
			else if(str.equals("InRubrica")) {
				str = (String)ois.readObject();
				inRub = rub.inRubrica(str);
				oos.writeObject(inRub);
			}
		} catch (ClassNotFoundException | IOException e) {
			break;
		} 
	}try {
		s.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
}
