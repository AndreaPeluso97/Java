package Appello_01_22_2019_es2;

import java.net.*;
import java.io.*;

public class MultiClient {
	static final int MAX_THREADS = 3;
	public static void main(String[] args) throws IOException, InterruptedException {
		InetAddress addr = InetAddress.getByName(null);
		int i=0;
		while (i<10) {
			if (ClientThread.threadCount() < MAX_THREADS)
				new ClientThread(addr, i);
			Thread.sleep(10);
			i++;
		}
	}
}
