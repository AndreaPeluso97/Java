package provaservertoclient;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RemoteObserverImpl implements RemoteObserver {
protected RemoteObserverImpl() throws RemoteException {
}
public void remoteUpdate(Object observable,
Object updateMsg)
throws RemoteException {
System.out.println("got message:" + updateMsg);
}


public static void main(String[] args) {
try {
Registry registry = LocateRegistry.getRegistry(1099);
RMIObservableService remoteService =
(RMIObservableService) registry.lookup("RmiService");
RemoteObserver client = new RemoteObserverImpl();
RemoteObserver cliStub = (RemoteObserver)
UnicastRemoteObject.exportObject(client, 3949);
remoteService.addObserver(cliStub);
} catch (Exception ex) {
ex.printStackTrace();
}
}
}