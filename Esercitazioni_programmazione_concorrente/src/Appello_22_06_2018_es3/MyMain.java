package Appello_22_06_2018_es3;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class MyMain {

	public static void main(String[] args) throws AccessException, RemoteException, NotBoundException {
		Registry reg=LocateRegistry.getRegistry(null);
		MCD stub=(MCD)reg.lookup("server");		
		int x, y;
		x=18; y=3;
		System.out.println("MCD("+x+","+y+")="+stub.mcd(x,y));
		x=18; y=6;
		System.out.println("MCD("+x+","+y+")="+stub.mcd(x,y));
		x=18; y=7;
		System.out.println("MCD("+x+","+y+")="+stub.mcd(x,y));
		x=18765; y=345435;
		System.out.println("MCD("+x+","+y+")="+stub.mcd(x,y));
	}
}
