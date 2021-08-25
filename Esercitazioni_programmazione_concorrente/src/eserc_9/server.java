package eserc_9;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class server extends Thread{

	Socket s;
	ObjectOutputStream oos;
	ObjectInputStream ois;


	public server(Socket s) throws IOException {
		this.s=s;
		oos=new ObjectOutputStream(s.getOutputStream());
		ois=new ObjectInputStream(s.getInputStream());
		start();
	}

	public void run(){
		try {
			if(ois.readObject().equals("ciao")){
				oos.writeObject("ciao a te");
			}
		} catch (Exception e) {
		}finally{
			try{
				s.close();
			}
			catch(Exception e){}
		}

	}}