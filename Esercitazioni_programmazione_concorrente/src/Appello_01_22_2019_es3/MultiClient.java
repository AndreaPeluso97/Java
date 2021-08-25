package Appello_01_22_2019_es3;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MultiClient {

	private final static int MAXt=3;
	
	public static void main(String[] args) throws AccessException, RemoteException, NotBoundException{
		int i=0;
		while(i<MAXt)
		{
			new Client();
			i++;
		}
		
		
	}
	
}
