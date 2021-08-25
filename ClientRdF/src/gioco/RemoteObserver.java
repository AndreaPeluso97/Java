package gioco;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteObserver extends Remote{
	void remoteUpdate(Object msg) throws RemoteException;
}
