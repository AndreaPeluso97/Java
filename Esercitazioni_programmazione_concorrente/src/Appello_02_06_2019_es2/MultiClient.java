package Appello_02_06_2019_es2;

import java.net.*;
import java.io.*;

public class MultiClient {

	private final static int NTHREADS=3;

	public static void main(String[] args) throws IOException, InterruptedException {
		int i=0;
		InetAddress Addr=InetAddress.getByName(null);
		while(i<=NTHREADS){
			new MCDclient(Addr,i);
			Thread.sleep(1000);
			i++;
		}
	}
}
