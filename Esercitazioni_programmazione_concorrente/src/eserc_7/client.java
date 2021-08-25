package eserc_7;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class client extends Thread{
	Socket s;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	public client(InetAddress address) throws IOException {
		s=new Socket(address,8080);
		oos=new ObjectOutputStream(s.getOutputStream());
		ois=new ObjectInputStream(s.getInputStream());
		start();
	}

	public void run(){
		try{
			oos.writeObject("ciao");
			String risposta=(String) ois.readObject();
			System.out.println(risposta);
		}catch(Exception e){
			
		}finally{try{s.close();}catch(Exception e){}}
	}
}
