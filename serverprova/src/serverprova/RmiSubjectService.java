package serverprova;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiSubjectService extends Remote {

	   void addObserver(RemoteObserver o) throws RemoteException;
	}