package Appello_2018_07_18_es2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Server extends Thread{


	Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public Server(Socket s) throws IOException {

		socket=s;
		oos=new ObjectOutputStream(s.getOutputStream());
		ois=new ObjectInputStream(s.getInputStream());

		start();


	}

	public void run() {
		try {
			int i=0;
			while(i<4){
				if(ois.readObject().equals("MCD")){
					int n = (int) ois.readObject();
					int m = (int) ois.readObject();
					int r = mcd(m, n);
					oos.writeObject(r);
					i++;
				}

				
			}


		} catch (IOException | ClassNotFoundException e) {
			System.err.println(e);
		} finally {
			System.out.println("closing...");
			try {
				socket.close();
			} catch (IOException e) {
				System.err.println("Socket not closed");
			}
		}
	}



	public int mcd(int n, int m){
		int r;
		while(m != 0) {
			r = n % m;
			n = m; 
			m = r;
		}
		return n;
	}
}
