package gioco;


import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

public class AddServer {
	
	
	
	private static ServerInterface stub = null;
	
	private static ServerInterface addService=null;

	public static boolean Start(){
		
		try{
			LocateRegistry.createRegistry(1099); //creazione del registro RMI sulla porta 1099
			addService=new Adder(); //istanziazione dell'oggetto da registrare
			stub = (ServerInterface)UnicastRemoteObject.exportObject(addService, 0); //Utilizzato per esportare un oggetto e ottenere una stub che comunica con l'oggetto remoto.
			Naming.rebind("AddService",stub); //Ricollega il nome specificato a un nuovo oggetto remoto. 
			return true;
		}catch(Exception e){
			return false;
		}
	}

}