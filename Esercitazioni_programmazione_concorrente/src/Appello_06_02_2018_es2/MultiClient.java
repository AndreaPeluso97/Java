package Appello_06_02_2018_es2;

import java.net.*;
import java.io.*;

public class MultiClient {
	static final int MAX_THREADS = 4;
	public static void main(String[] args) throws IOException, InterruptedException {
		InetAddress addr = InetAddress.getByName(null);
		int i=0;
		while (i<1) {
			if (ClientThread.threadCount() < MAX_THREADS)
				new ClientThread(addr, i);
			Thread.sleep(1000);
			i++;
		}
		Thread.sleep(5000);
		System.out.println("Finito");
	}
}
