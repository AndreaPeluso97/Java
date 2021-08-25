package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Client.Container;

public class ServerThread extends Thread{
	
	Container cont;
	
	Socket so;
	
	ObjectOutputStream oos;
	
	ObjectInputStream ois;
	
	public ServerThread(Container c,Socket s) throws IOException{
		
		
		cont=c;
		
		so=s;
		
		oos=new ObjectOutputStream(s.getOutputStream());
		
		ois=new ObjectInputStream(s.getInputStream());
		
		start();

		
	}
	
	public void run(){
		
		while(true){
			
			try {
				if(((String)ois.readObject()).equals("add")){
					
					cont.add("ho aggiunto ", 1);

				}
				
				if(((String)ois.readObject()).equals("search")){
					
					cont.search("ho cercato ",1);
				}
			} catch (ClassNotFoundException | IOException e) {
				
				break;
				// TODO Auto-generated catch block
			}
		}
		
	}

}
