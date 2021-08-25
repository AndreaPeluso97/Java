package eserc_5;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread{
	
	Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;

	public Client(InetAddress address) throws IOException {
		socket=new Socket(address,8080);
		oos= new ObjectOutputStream(socket.getOutputStream());
		ois=new ObjectInputStream(socket.getInputStream());
		start();
	}
	
	
  public void run(){
	  
	  try{
		  oos.writeObject("ciao");
		  String risposta=(String)ois.readObject();
		  System.out.println(risposta);
		  
	  }catch(Exception e){}finally{try{socket.close();}catch(Exception e){
		  
	  }}
  }
}
