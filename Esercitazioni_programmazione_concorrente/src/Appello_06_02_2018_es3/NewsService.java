package Appello_06_02_2018_es3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NewsService extends Remote {
  public String readNews() throws RemoteException;
}


