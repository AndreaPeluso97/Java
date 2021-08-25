package socket;

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

class Giocatore extends Thread{
	private int myID;
	private Scacchiera laScacchiera;
	
	Socket s;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	private Mossa pensaMossa(){
		// qui bisognerebbe pensare una mossa intelligente ...
		try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(100, 400));
		} catch (InterruptedException e) { e.printStackTrace(); }
		return new Mossa("cavallo", 1, 3);
	}
	public Giocatore(int id,Socket socket) throws UnknownHostException, IOException {
		myID=id;
		laScacchiera = new Scacchiera();
		
		s=socket;
		
		oos=new ObjectOutputStream(s.getOutputStream());
		ois=new ObjectInputStream(s.getInputStream());
		
		
		this.start();
		
		
	}
	public void run() {
		boolean finito=false;
		Mossa m;
		while (!finito) {
			
			try{
			m=pensaMossa();
			System.out.println("giocatore"+myID+": ha pensato mossa: "+m.pezzo+" "+m.riga+" "+m.colonna);
			
				oos.writeObject(m);
			

			if(laScacchiera.finita()){
				System.out.println("giocatore "+myID+": ho vinto!");
				finito=true;
				
			} else {
				System.out.println("giocatore "+myID+": ho mosso e attendo contromossa");
					Mossa m1=(Mossa) ois.readObject();
					laScacchiera.mossa(m1);
				
				if(laScacchiera.finita()){
					System.out.println("giocatore "+myID+": ho perso!");
					finito=true;	
					
				}
			}
			}catch (IOException | ClassNotFoundException e){
				break;
			}
		}
	}
}

