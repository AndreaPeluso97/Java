package Appello_03_06_2019_es3;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class RubricaClient {

	public static void main(String[] args) throws RemoteException, NotBoundException {
		
		for(int i=0;i<10;i++){
		new RubricaThread();
		
		}
		
		
	}

}
