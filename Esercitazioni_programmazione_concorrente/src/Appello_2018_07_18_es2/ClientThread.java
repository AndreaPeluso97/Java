package Appello_2018_07_18_es2;

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

class ClientThread extends Thread {
	private Socket socket;
	private ObjectInputStream obj_in_s;
	private ObjectOutputStream obj_out_s;
	private int myID;
	public ClientThread(InetAddress addr, int id) {
		System.out.println("Making client " + id);
		myID=id;
		try {
			socket = new Socket(addr, 2222);
		} catch (IOException e){System.err.println("Socket failed");}
		try {
			obj_in_s = new ObjectInputStream(socket.getInputStream());
			obj_out_s = new ObjectOutputStream(socket.getOutputStream());
			start();
		} catch (IOException e) {
			try {
				socket.close();
			} catch (IOException e2) {
				System.err.println("Socket not closed");
			}
		}
	}
	public void run() {
		
		try {
			int x, y, r;
			x=18; y=3;
			obj_out_s.writeObject("MCD");
			obj_out_s.writeObject(x);
			obj_out_s.writeObject(y);
			r=(int) obj_in_s.readObject();	
			System.out.println("Client"+myID+" : MCD("+x+","+y+")="+r);
			Thread.sleep(ThreadLocalRandom.current().nextInt(1, 4));
			x=18; y=6;
			obj_out_s.writeObject("MCD");
			obj_out_s.writeObject(x);
			obj_out_s.writeObject(y);
			r=(int) obj_in_s.readObject();	
			System.out.println("Client"+myID+" : MCD("+x+","+y+")="+r);
			Thread.sleep(ThreadLocalRandom.current().nextInt(1, 4));
			x=18; y=7;
			obj_out_s.writeObject("MCD");
			obj_out_s.writeObject(x);
			obj_out_s.writeObject(y);
			r=(int) obj_in_s.readObject();	
			System.out.println("Client"+myID+" : MCD("+x+","+y+")="+r);
			Thread.sleep(ThreadLocalRandom.current().nextInt(1, 4));
			x=18765; y=345435;
			obj_out_s.writeObject("MCD");
			obj_out_s.writeObject(x);
			obj_out_s.writeObject(y);
			r=(int) obj_in_s.readObject();	
			System.out.println("Client"+myID+" : MCD("+x+","+y+")="+r);
			socket.close();
		} catch (IOException | InterruptedException | ClassNotFoundException e) {
			System.err.println("IO Exception");
		} finally { // Always close it:
			try { 
				socket.close();
			} catch (IOException e) {
				System.err.println("Socket not closed");
			}
		}
	}
}

