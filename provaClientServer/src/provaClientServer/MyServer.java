package provaClientServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;

public interface MyServer extends Remote {
  public void warnAt(int X, WarnClient cli) throws RemoteException;
}

