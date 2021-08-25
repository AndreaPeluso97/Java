package socket;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class MultiClient {
	static Thread t;

	static final int MAX_THREADS = 1;
	public static void main(String[] args) throws IOException, InterruptedException {
		int i=0;
		while (i<MAX_THREADS) {
			Socket socket = new Socket(InetAddress.getByName(null),2222);
			t=new Giocatore(i,socket);
			Thread.sleep(100);
			i++;
		}
		
		t.join();
		
		System.out.println("Finito");
	}
}
