package eserc_2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Server extends Thread{
	private Socket s;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public Server(Socket s) throws IOException{
		this.s=s;
		oos=new ObjectOutputStream(s.getOutputStream());
		ois=new ObjectInputStream(s.getInputStream());
		start();
	}
	
	public void run(){	
	try{
		if(ois.readObject().equals("ciao")){
			oos.writeObject("ciao a te!");
		}
	}catch(Exception e){
		System.out.println("Error");
	}	finally{
		System.out.println("closing...");
		try{s.close();}catch(Exception e1){System.out.println("Error");}
		
	}
		
		
	}
	


}
