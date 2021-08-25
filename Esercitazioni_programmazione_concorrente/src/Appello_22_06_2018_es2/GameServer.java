package Appello_22_06_2018_es2;

import java.net.*;
import java.io.*;

public class GameServer extends Thread {
	private Socket socket;
	private ObjectInputStream obj_in_s;
	private ObjectOutputStream obj_out_s;
	private Scacchiera scacchiera;

	public GameServer(Socket s) throws IOException {
		socket = s;
		obj_out_s = new ObjectOutputStream(socket.getOutputStream());
		obj_in_s = new ObjectInputStream(socket.getInputStream());
		scacchiera = new Scacchiera();
		start(); 
	}
	private Mossa pensaMossa(){
		// qui bisognerebbe pensare una mossa intelligente ...
		return new Mossa("cavallo", 1, 3);
	}
	public void run() {
		try {
			boolean finito=false;
			while (!finito) {
				Mossa m = (Mossa)obj_in_s.readObject();
				System.out.println("Giocatore ha mosso "+m.pezzo+" in "+m.colonna+", "+m.riga);
				scacchiera.mossa(m);
				if(scacchiera.finita()){
					System.out.println("Giocatore ha vinto");
					finito=true;
				} else {
					m=pensaMossa();
					scacchiera.mossa(m);
					if(scacchiera.finita()){
						System.out.println("Server matta: "+m.pezzo+" in "+m.colonna+", "+m.riga);
						obj_out_s.writeObject(m);						
						finito=true;
					} else {
						System.out.println("Server ha mosso "+m.pezzo+" in "+m.colonna+", "+m.riga);
						obj_out_s.writeObject(m);						
					}
				}
			}
			System.out.println("closing...");
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("IO Exception");
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				System.err.println("Socket not closed");
			}
		}
	}
}
