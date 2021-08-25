package RMI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;

public class Server extends UnicastRemoteObject implements Interface{
	
	static LinkedList<String> list;
	
	static ArrayList<InterfacciaClient> Clients;

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Server() throws RemoteException {

		super();
		
		Clients=new ArrayList<InterfacciaClient>();
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException{
		
			list=new LinkedList<String>();

			LocateRegistry.createRegistry(1099);
		
			Server s=new Server();

			Naming.rebind("Server", s);
		
		
	}

	public boolean add(String parola,InterfacciaClient rf) throws RemoteException {
		
		
		if(!Clients.contains(rf)){

		Clients.add(rf);
		}
	
		if(!list.contains(parola)){
		list.add(parola);
		
		for(InterfacciaClient c: Clients){c.Notifica(parola);}
		return true;
		}else{
		return false;
		}
	}

	public boolean cerca(String parola) throws RemoteException {
		
		if(!list.contains(parola)){

		return false;
		}else{
			
			return true;

		}
	}
	
	

}
