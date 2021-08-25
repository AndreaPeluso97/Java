package Appello_2018_07_18_es2;

import java.net.*;
import java.io.*;

public class MultiClient {
	static final int MAX_THREADS = 3;
	public static void main(String[] args) throws IOException, InterruptedException {
		InetAddress addr = InetAddress.getByName(null);
		int i=0;
		while (i<MAX_THREADS) {
			new ClientThread(addr, i);
			Thread.sleep(100);
			i++;
		}
	}
}
