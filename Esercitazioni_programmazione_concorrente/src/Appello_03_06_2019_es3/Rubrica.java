package Appello_03_06_2019_es3;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

public class Rubrica extends UnicastRemoteObject implements RubricaInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Hashtable<String, String> laRubrica;
	public Rubrica() throws RemoteException{
		laRubrica = new Hashtable<String, String>();
	}
	
	public static void main(String[] args) throws RemoteException{
		Registry r=LocateRegistry.createRegistry(1099);
		Rubrica obj=new Rubrica();
		r.rebind("RMI", obj);
		
	}
	
	public synchronized void aggiungiNumero(String nome, String num) {
		if (!laRubrica.containsKey(nome)) {
			laRubrica.put(nome, num);
			notifyAll();
			System.out.println("Rubrica: aggiunto " + nome + "  " + num);
		} else {
			System.out.println("Rubrica: NON aggiungo " + nome + " gia` presente.");
		}
		
		
	}
	public synchronized void eliminaNumero(String nome) {
		if (laRubrica.containsKey(nome)) {
			laRubrica.remove(nome);
			System.out.println("Rubrica: rimosso " + nome);
		} else {
			System.out.println("Rubrica: NON rimosso " + nome + " gia` assente.");
		}
	}
	public synchronized boolean inRubrica(String nome) {
		return laRubrica.containsKey(nome);
	}
	public synchronized String trova(String nome){
		System.out.println("Rubrica: cerco numero di " + nome);
		while(!laRubrica.containsKey(nome)) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String numero=laRubrica.get(nome);
		System.out.println("Rubrica: trovato "+numero+" di " + nome);
		return numero;
	}	
	
	
	
}
