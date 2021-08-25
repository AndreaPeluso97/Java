package Appello_19_12_2018_es3;

import java.io.*;
import java.rmi.NotBoundException;

public class MultiClient {
	static final int MAX_THREADS = 5;
	public static void main(String[] args) throws IOException, InterruptedException, NotBoundException {
		int i=0;
		while (i<10) {
				new BankClientThread();
				
				i++;
			//Thread.sleep(10);
		}
		//Thread.sleep(5000);
		//System.out.println("Finito");
	}
}
