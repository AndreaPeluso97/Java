package Appello_22_06_2018_es2;

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

class Giocatore extends Thread{
	private int myID;
	private Scacchiera laScacchiera;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	Socket s;
	private Mossa pensaMossa(){
		// qui bisognerebbe pensare una mossa intelligente ...
		try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(100, 400));
		} catch (InterruptedException e) { e.printStackTrace(); }
		return new Mossa("cavallo", 1, 3);
	}
	public Giocatore(int id) throws IOException {
		myID=id;
		laScacchiera = new Scacchiera();
		s=new Socket(InetAddress.getByName(null),8080);

		oos=new ObjectOutputStream(s.getOutputStream());
		ois=new ObjectInputStream(s.getInputStream());
		this.start();
	}
	public void run() {
		boolean finito=false;
		while (!finito) {
			Mossa m=pensaMossa();
			System.out.println("giocatore"+myID+": ha pensato mossa: "+m.pezzo+" "+m.riga+" "+m.colonna);
			laScacchiera.mossa(m);
			if(laScacchiera.finita()){
				System.out.println("giocatore "+myID+": ho vinto!");
				finito=true;
			} else {
				System.out.println("giocatore "+myID+": ho mosso e attendo contromossa");
				try{

					oos.writeObject(m);
					Mossa mossa=(Mossa) ois.readObject();

					laScacchiera.mossa(mossa);
					}catch(Exception e){}
				if(laScacchiera.finita()){
					System.out.println("giocatore "+myID+": ho perso!");
					finito=true;						
				}
			}
		}
	}
}

