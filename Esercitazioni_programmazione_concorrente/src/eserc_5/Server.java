package eserc_5;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Server extends Thread{
	
	ObjectOutputStream oos;
	ObjectInputStream ois;
	Socket socket;

	
	public Server(Socket s) throws IOException {
		socket=s;
		oos= new ObjectOutputStream(socket.getOutputStream());
		ois=new ObjectInputStream(socket.getInputStream());
		start();
	}
	
	 public void run(){
		  
		  try{
			  if(ois.readObject().equals("ciao")){
				  oos.writeObject("Ciao a te");
			  }
			  
		  }catch(Exception e){}finally{try{socket.close();}catch(Exception e){
			  
		  }}
	  }

}
