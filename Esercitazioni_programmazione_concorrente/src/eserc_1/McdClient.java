package eserc_1;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class McdClient extends Thread{

	private int Myid;	
	private Socket s;	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public McdClient(InetAddress Addr,int i){

		Myid=i;
		try{
			s=new Socket(Addr,2222);
		}catch(Exception e){System.out.println("Socket fallita");}
		try{
			oos=new ObjectOutputStream(s.getOutputStream());
			ois=new ObjectInputStream(s.getInputStream());
		}catch(Exception e){
			try{
				s.close();
				System.out.println("Socket chiusa");
			}catch(Exception e1){
				System.out.println("Socket non chiusa");
			}
		}
		start();
	}

	public void run(){

		try{
			int x,y,r;
			x=18;y=3;
			oos.writeObject("MCD");
			oos.writeObject(x);
			oos.writeObject(y);
			r=(int)ois.readObject();
			System.out.println("Thread "+Myid+": MCD ("+x+","+y+") = "+r);
			Thread.sleep(100);
			x=5;y=3;
			oos.writeObject("MCD");
			oos.writeObject(x);
			oos.writeObject(y);
			r=(int)ois.readObject();
			System.out.println("Thread "+Myid+": MCD ("+x+","+y+") = "+r);
			Thread.sleep(100);
			x=18765;y=345435;
			oos.writeObject("MCD");
			oos.writeObject(x);
			oos.writeObject(y);
			r=(int)ois.readObject();
			System.out.println("Thread "+Myid+": MCD ("+x+","+y+") = "+r);
			Thread.sleep(100);
		}catch(Exception e){
			System.out.println("Errore: "+ e.toString());
		}finally{
			try{
				s.close();
				System.out.println("Socket chiusa");
			}catch(Exception e){
				System.out.println("Socket non chiusa");
			}
		}
	}
}