package clientprova;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiSubjectService extends Remote {

	   void addObserver(RmiObserverClient o) throws RemoteException;


	}