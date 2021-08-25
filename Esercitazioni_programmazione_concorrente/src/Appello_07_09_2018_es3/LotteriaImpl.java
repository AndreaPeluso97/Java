package Appello_07_09_2018_es3;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class LotteriaImpl extends UnicastRemoteObject implements Lotteria{
	private static final long serialVersionUID = 1L;
	private String bigliettoVincente;
	private ArrayList<GiocatoreInterf> list=new ArrayList<GiocatoreInterf>();
	
	
	public LotteriaImpl() throws RemoteException {
		super();
	}
	private void goOn(){
		System.out.println("Lotteria: inizio ciclo estrazioni!");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {	}
		int x =(int) (1000000*Math.random());
		bigliettoVincente="biglietto"+x;
		System.out.println("Lotteria: estratto: "+bigliettoVincente);
		for(GiocatoreInterf g:list){
		    try {
				g.stampa(bigliettoVincente);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		LotteriaImpl obj = null;
		try {
			obj = new LotteriaImpl();
			Registry registro = LocateRegistry.createRegistry(1099);
			registro.rebind("Lotteria", obj);
		} catch (RemoteException e) {
			System.err.println("Server: problema con registry RMI");
		}
		
		while(true){
		obj.goOn();
		}
		

	} 

	public String compraBiglietto(GiocatoreInterf gInterf) throws RemoteException {
		
		if(!list.contains(gInterf)){
		list.add(gInterf);
		}
		int x =(int)(1000000*Math.random());
		return "biglietto"+x;
	}
	
	
}

