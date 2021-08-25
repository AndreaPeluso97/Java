package Appello_03_06_2019_es3;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RubricaInterface extends Remote{
	public void aggiungiNumero(String nome, String num) throws RemoteException;
	public void eliminaNumero(String nome) throws RemoteException;
	public boolean inRubrica(String nome) throws RemoteException;
	public String trova(String nome) throws RemoteException;
}

