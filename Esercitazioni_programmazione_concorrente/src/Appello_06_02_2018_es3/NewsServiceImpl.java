package Appello_06_02_2018_es3;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;

public class NewsServiceImpl extends UnicastRemoteObject implements NewsService {
        /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private int ncount=0;
        private String s="abc"+ncount;
	public NewsServiceImpl() throws RemoteException {
		super();
	}
	public synchronized String readNews() throws RemoteException {
          String theNews=s;
          updateNews();
          return theNews;
	}
	public synchronized void updateNews() throws RemoteException {
          ncount++;
          s="abc"+ncount;
	}
	public static void main(String args[]) {
		try {
			NewsServiceImpl obj = new NewsServiceImpl();
			Registry registro = LocateRegistry.createRegistry(1099);
			registro.rebind("NEWS", obj);
			System.err.println("Server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}

