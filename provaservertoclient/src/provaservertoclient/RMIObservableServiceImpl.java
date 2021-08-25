package provaservertoclient;

import java.util.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
public class RMIObservableServiceImpl extends Observable implements RMIObservableService {
public RMIObservableServiceImpl() {
	
	
}

public void run() {
while (true) {
try {
Thread.sleep(5 * 1000);
} catch (InterruptedException e) { }
setChanged();
notifyObservers(new Date());
}
}

public void addObserver(RemoteObserver o)
throws RemoteException {
WrappedObserver mo = new WrappedObserver(o);
addObserver(mo);
System.out.println("Added observer:" + mo);
}



public static void main(String[] args) {
try {
RMIObservableService obj =
new RMIObservableServiceImpl();
RMIObservableService stub = (RMIObservableService)
UnicastRemoteObject.exportObject(obj, 3939);
Registry registry =
LocateRegistry.createRegistry(1099);
registry.rebind("RmiService", stub);
System.err.println("Server ready");
((RMIObservableServiceImpl) obj).run();
} catch (Exception ex) {
ex.printStackTrace();
}
}
}