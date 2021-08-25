package Appello_02_06_2019_es2;

import java.net.*;
import java.io.*;

public class Server extends Thread {

	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Socket socket;


	public Server(Socket s) throws IOException {
		socket=s; 
		oos=new ObjectOutputStream(socket.getOutputStream());
		ois=new ObjectInputStream(socket.getInputStream());
		start();
	}

	public void run() {
		try{
			while(true){
				if(ois.readObject().equals("MCD")){
					int n=(int) ois.readObject();
					int m=(int) ois.readObject();
					int r=mcd(n,m);
					oos.writeObject(r); }}
		}catch(Exception e){
			System.err.println("Eccezione"); }
		finally{
			try{
				socket.close();
				System.err.println("Socket chiusa");
			}catch(Exception e){
				System.err.println("Socket non chiusa"); }}}

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
