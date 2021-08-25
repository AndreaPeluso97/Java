package Appello_06_02_2018_es2;

import java.net.*;
import java.io.*;

public class NewsServer extends Thread {
	private Socket socket;
	private ObjectInputStream obj_in_s;
	private ObjectOutputStream obj_out_s;
	private MyBuffer newsBuf;
	private NewsUpdater myUpdater;
	public NewsServer(Socket s) throws IOException {
		this.socket = s;
		obj_out_s = new ObjectOutputStream(socket.getOutputStream());
		obj_in_s = new ObjectInputStream(socket.getInputStream());
		this.newsBuf = new MyBuffer();
		start(); 
	}
	public void run() {
		String str="bubu";
		int bytesToRead=0;
		myUpdater = new NewsUpdater(newsBuf, obj_out_s);
		System.out.println("Server thread started.");
		try {
			while (true) {
				System.out.println("Server thread nel ciclo.");
				str = (String) obj_in_s.readObject();
				System.out.println("Server received command: " + str);
				if (str.equals("END")){
					myUpdater.interrupt(); // terminate myUpdater
					break;					
				}
				if(str.equals("GetNews")){
					str=newsBuf.getNews();
					System.out.println("Server: sending news "+str);
					obj_out_s.writeObject(str);
				}										
			}
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			System.err.println(" Exception");
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				System.err.println("Socket not closed");
			}
		}
	}
}
