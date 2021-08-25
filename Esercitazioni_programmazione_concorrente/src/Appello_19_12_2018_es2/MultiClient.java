package Appello_19_12_2018_es2;

import java.net.*;
import java.io.*;

public class MultiClient {
	static final int MAX_THREADS = 5;
	public static void main(String[] args) throws IOException, InterruptedException {
		InetAddress addr = InetAddress.getByName(null);
		int i=0;
		while (i<10) {
			if (BankClientThread.threadCount() < MAX_THREADS)
				new BankClientThread(addr, i);
			//Thread.sleep(10);
			i++;
		}
		//Thread.sleep(5000);
		//System.out.println("Finito");
	}
}
