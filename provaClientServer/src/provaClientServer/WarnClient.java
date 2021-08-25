package provaClientServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;

public interface WarnClient extends Remote {
  public void myNotify() throws RemoteException;
}

