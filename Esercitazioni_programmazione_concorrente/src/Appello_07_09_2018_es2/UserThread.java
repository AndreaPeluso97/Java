package Appello_07_09_2018_es2;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class UserThread extends Thread {
	Buffer myBuffer;
	InetAddress addr = null;
	public UserThread(Buffer t){
		this.myBuffer=t;
		try {
			addr = InetAddress.getByName(null);
		} catch (UnknownHostException e) { e.printStackTrace(); }
		this.start();
	}
	public void run(){
		String str = null;
		ObjectInputStream obj_in_s = null;
		try {
			@SuppressWarnings("resource")
			Socket socket = new Socket(addr, 8099);
			obj_in_s = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) { e.printStackTrace(); }
		System.out.println("reader thread started");
		while(true){
			try {
				str=(String) obj_in_s.readObject();
				System.out.println("reader thread received "+str);
			} catch (ClassNotFoundException | IOException e) { e.printStackTrace(); }	
			myBuffer.put(str);
		}
	}
}



