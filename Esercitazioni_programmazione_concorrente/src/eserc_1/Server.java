package eserc_1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Server extends Thread{

	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public Server(Socket s) throws IOException{
		socket=s;
		oos=new ObjectOutputStream(socket.getOutputStream());
		ois=new ObjectInputStream(socket.getInputStream());
		start();
	}

	public void run(){
		try{
			while(true)
			{
				if(ois.readObject().equals("MCD")){
					int n=(int)ois.readObject();
					int m=(int)ois.readObject();
					int r=mcd(n,m);
					oos.writeObject(r);
				}
			}
		}catch(Exception e){
			System.out.println("Eccezione");
		}finally{
			try{
				socket.close();
				System.out.println("Socket chiusa");
			}catch(Exception e){
				System.out.println("Socket non chiusa");
			}
		}
	}

	private int mcd(int n, int m){
		int r;
		while(m != 0) {
			r = n % m;
			n = m; 
			m = r;
		}
		return n;
	}
}