package Appello_21_06_es2;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread implements Runnable{

	Socket cliSocket;
	Gioco mioGioco;
	ObjectOutputStream oos;
	ObjectInputStream ois;

	public ServerThread(Socket s, Gioco r) {
		cliSocket = s;
		mioGioco = r;
		try {
			oos = new ObjectOutputStream(cliSocket.getOutputStream());
			ois = new ObjectInputStream(cliSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String str;
		int idGiocatore;
		System.out.println("Server thread running");
		while(true) {
			try {
				str = (String)ois.readObject();
				if(str.equals("FineServizio")) {
					break;
				}
				if(str.equals("partitaFinita")) {
					oos.writeObject(mioGioco.partitaFinita());
				}
				else if(str.equals("vincitore")) {
					oos.writeObject(mioGioco.vincitore());
				}
				else if(str.equals("gioca")) {
					idGiocatore = (int) ois.readObject();
					str = (String)ois.readObject();  // legge la mossa
					mioGioco.gioca(idGiocatore, str);
				}
			} catch (ClassNotFoundException | IOException e) {
				break;
			} 
		}
		try {
			System.out.println("Server socket completes");
			cliSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
