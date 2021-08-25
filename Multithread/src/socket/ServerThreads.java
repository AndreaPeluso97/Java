package socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThreads extends Thread{
	
	Socket s;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	private Scacchiera laScacchiera;
	
	public ServerThreads(Socket socket,Scacchiera scacchiera) throws IOException{
		
		s = socket;
		
		oos=new ObjectOutputStream(s.getOutputStream());
		ois=new ObjectInputStream(s.getInputStream());
		laScacchiera=scacchiera;
		
		this.start();
	}
	
	
	
	public void run() {
		boolean finito=false;
		Mossa m;
		while (!finito) {
			try{
			System.out.println("giocatore : ho mosso e attendo contromossa");
				Mossa m1=(Mossa) ois.readObject();
				laScacchiera.mossa(m1);
			
			if(laScacchiera.finita()){
				System.out.println("giocatore : ho perso!");
				finito=true;
				
				
			}
			
			 else {
				
				m=pensaMossa();
				System.out.println("giocatore : ha pensato mossa: "+m.pezzo+" "+m.riga+" "+m.colonna);
				
					oos.writeObject(m);
				
				
				if(laScacchiera.finita()){
					System.out.println("giocatore : ho vinto!");
					finito=true;
					
					
					
				}
			}
			
		}catch (IOException | ClassNotFoundException e){
			break;
		}
		}
		
	}
	
	
	
	private Mossa pensaMossa(){
		// qui bisognerebbe pensare una mossa intelligente ...
		return new Mossa("cavallo", 1, 3);
	}
	

}
