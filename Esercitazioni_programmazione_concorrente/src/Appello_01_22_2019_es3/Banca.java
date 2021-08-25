package Appello_01_22_2019_es3;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Banca extends UnicastRemoteObject implements BancaInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int numCC=2;
	private ContoCorrente conti[];
	public Banca() throws RemoteException{
		conti = new ContoCorrente[numCC];
		for(int i=0; i<numCC; i++){
			conti[i]= new ContoCorrente("conto_"+i);
		}
	}
	public int leggi(int cc){
		return conti[cc].getAmmontare();
	}
	public void aggiorna(int valore, int cc){
		conti[cc].scriviAmmontare(valore);
	}
	public void saldoTotale() {
		int tot=0;
		for(int i=0; i<numCC; i++){
			System.out.println("conto "+i+": saldo="+conti[i].getAmmontare());
			tot+=conti[i].getAmmontare();
		}
		System.out.println("Totalone="+tot);
	}
	
	public static void main(String[] args) throws RemoteException{
		
		Registry reg=LocateRegistry.createRegistry(1099);
		Banca obj=new Banca();
		reg.rebind("banca", obj);
		
	}
}
