package provaClientServer;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.NotBoundException;
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;

public class MyClient extends UnicastRemoteObject 
                      implements WarnClient {
  protected MyClient() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
public static void main(String[] args)
                 throws RemoteException, NotBoundException {
    Registry registro = LocateRegistry.getRegistry(args[0],1099);
    MyServer ms = (MyServer) registro.lookup("WARN_SERVER");
    WarnClient thisClient= new MyClient();
    ms.warnAt(100, thisClient);
  }
  public void myNotify() {
     System.out.println("Client: notification received! ");
  }
}

