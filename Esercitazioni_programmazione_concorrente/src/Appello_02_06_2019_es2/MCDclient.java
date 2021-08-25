package Appello_02_06_2019_es2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;


public class MCDclient extends Thread{

	private int myId;
	private Socket s;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;


	public MCDclient(InetAddress addr,int i) throws IOException{

		myId=i;

		try{
			s=new Socket(addr,2222);}
		catch(Exception e){
			System.err.println("Socket fallita"); }

		try{
			oos=new ObjectOutputStream(s.getOutputStream());
			ois=new ObjectInputStream(s.getInputStream());
		}catch(Exception e){
			try{
				s.close();
			}catch(Exception e1){
				System.err.println("Socket non chiusa"); }}
		
		start();	
	}

	public void run() {
		try {
			int x, y,r;
			x=18; y=3;
			oos.writeObject("MCD");
			oos.writeObject(x);
			oos.writeObject(y);
			r=(int) ois.readObject();
			System.out.println("Thread: "+myId+" MCD("+x+","+y+")="+r);
			Thread.sleep(100);
			x=18; y=6;
			oos.writeObject("MCD");
			oos.writeObject(x);
			oos.writeObject(y);
			r=(int) ois.readObject();
			System.out.println("Thread: "+myId+" MCD("+x+","+y+")="+r);
			Thread.sleep(100);
			x=18; y=7;
			oos.writeObject("MCD");
			oos.writeObject(x);
			oos.writeObject(y);
			r=(int) ois.readObject();
			System.out.println("Thread: "+myId+" MCD("+x+","+y+")="+r);
			Thread.sleep(100);
			x=18765; y=345435;
			oos.writeObject("MCD");
			oos.writeObject(x);
			oos.writeObject(y);
			r=(int) ois.readObject();
			System.out.println("Thread: "+myId+" MCD("+x+","+y+")="+r);
			Thread.sleep(100);
		} catch (Exception e) {
			System.err.println("Client exc.: " + e.toString());
		} finally{	
			try{	
				s.close();
			}
			catch(Exception e){	
				System.err.println("Socket non chiusa");
			}
		}
	}
}

