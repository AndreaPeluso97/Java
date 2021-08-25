package Appello_03_06_2019_es2;

import java.io.IOException;
import java.net.UnknownHostException;


public class RubricaClient {
	public static void main(String[] args) throws UnknownHostException, IOException  {
		for(int i=0;i<10;i++){
		new RubricaThread();
		}	
	}
}
