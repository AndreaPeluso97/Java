package eserc_2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread{

	private Socket s;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private int i;
	
	public Client(InetAddress address,int i){
		
		this.i=i;
		
		try {
			s=new Socket(address,8080);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try{
		oos=new ObjectOutputStream(s.getOutputStream());
		ois=new ObjectInputStream(s.getInputStream());
		start();
		}catch(Exception e){
			try{s.close();}catch(Exception e1){System.out.println("Error");}
		}
	}
	
	public void run(){
		
	try{
		
		oos.writeObject("ciao");
		String risposta=(String)ois.readObject();
		s.close();
		System.out.println("Client "+i+" Ha ricevuto: "+ risposta);
	}catch(Exception e){
		System.out.println("Error");
	}	finally{
		try{s.close();}catch(Exception e1){System.out.println("Error");}
		
	}
		
		
	}
	
}
