package Appello_21_06_2019_es2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread{

	Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	Gioco gioco;
	String mess;
	int id;
	String mossa;

	public ServerThread(Socket s,Gioco g) throws IOException{
		socket=s;
		oos=new ObjectOutputStream(s.getOutputStream());
		ois=new ObjectInputStream(s.getInputStream());	
		gioco=g;
		start();		
	}

	public void run(){
		System.out.println("gioco");
		while(true){
			try{
				mess=(String) ois.readObject();
				if(mess.equals("fineServizio")) {break;}
				if(mess.equals("partitaFinita")){
					oos.writeObject(gioco.finito);}
				else if(mess.equals("vincitore")){
					oos.writeObject(gioco.vincitore());}
				else if(mess.equals("gioca")){
					id=(int)ois.readObject();
					mossa=(String)ois.readObject();
					gioco.gioca(id, mossa);
				}
			}catch(Exception e){}
		} 
		try{
		socket.close();
		System.out.println("connessione chiusa");
		}catch(Exception e){}
	}
}
