package provaservertoclient;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface RMIObservableService extends Remote {
void addObserver(RemoteObserver o) throws RemoteException;

}