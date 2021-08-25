package Appello_21_06_2019_es2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Giocatore extends Thread {
	int mioId;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	Socket mySocket;
	public Giocatore(int id){
		mioId=id;
		try {
			mySocket = new Socket(InetAddress.getByName(null), 2345);
			oos = new ObjectOutputStream(mySocket.getOutputStream());
			ois = new ObjectInputStream(mySocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Giocatore "+mioId+": connesso");								
		start();
	}
	public void run() {
		boolean finito=false;
		int who;
		while(!finito) {
			try {
				oos.writeObject("partitaFinita");
				finito = (boolean) ois.readObject();
				if(finito){
					oos.writeObject("vincitore");
					who = (int) ois.readObject();
					if(who==mioId){
						System.out.println("Giocatore "+mioId+": Ho vinto!");						
					} else {
						System.out.println("Giocatore "+mioId+": Ho perso!");						
					}
					finito=true;
					oos.writeObject("fineServizio");
				} else {
					try {
						Thread.sleep(200); // pensa la mossa
					} catch (InterruptedException e) {	}
					//System.out.println("Giocatore "+mioId+": gioco");						
					oos.writeObject("gioca");
					oos.writeObject(mioId);
					oos.writeObject("la mia mossa");
				}
			} catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
}