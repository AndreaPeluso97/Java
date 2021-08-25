package provaClientServer;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
public class MyServerImpl extends UnicastRemoteObject
                         implements MyServer {
  public MyServerImpl() throws RemoteException { super(); }
  public void warnAt(int X, WarnClient cli) {
    try {
      Thread.sleep(X);
    } catch(InterruptedException e) {}
    cli.notify();
  }
  static public void main(String args[]) {
    try {
      MyServerImpl obj = new MyServerImpl();
      Registry registro = LocateRegistry.getRegistry();
      registro.rebind("WARN_SEVER", obj);
      System.out.println("MyServer bound in registry");
    } catch (Exception e) {
      System.out.println("MyServer err: " + e.getMessage());
    }
  }
}

