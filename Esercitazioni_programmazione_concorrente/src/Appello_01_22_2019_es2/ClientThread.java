package Appello_01_22_2019_es2;

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

class ClientThread extends Thread {
	private Socket socket;
	private static int threadcount = 0;
	private ObjectInputStream obj_in_s;
	private ObjectOutputStream obj_out_s;
	private int myID;

	public static int threadCount() {
		return threadcount;
	}
	public ClientThread(InetAddress addr, int id) {
		System.out.println("Making client " + id);
		myID=id;
		threadcount++;
		try {
			socket = new Socket(addr, 8080);
		} catch (IOException e){System.err.println("Socket failed");}
		try {
			obj_in_s = new ObjectInputStream(socket.getInputStream());
			obj_out_s = new ObjectOutputStream(socket.getOutputStream());
			start();
		} catch (IOException e) {
			try {
				threadcount--;
				socket.close();
			} catch (IOException e2) {
				System.err.println("Socket not closed");
			}
		}
	}
	public void run() {
		int valoreDaTrasferire;
		int sorgente;
		int destinazione;
		try {
			for (int i = 0; i < 5; i++) {
				valoreDaTrasferire = ThreadLocalRandom.current().nextInt(0, 10);
				sorgente = ThreadLocalRandom.current().nextInt(0, 2);
				destinazione=sorgente;
				while(destinazione==sorgente){
					destinazione = ThreadLocalRandom.current().nextInt(0, 2);	
				}
				System.out.println("Client_"+myID+": asking to Transfer "+valoreDaTrasferire+" da "+sorgente+" a "+destinazione);
				// facciamo il lock in ordine per prevenire deadlock!
				if(sorgente<destinazione) {
					obj_out_s.writeObject("Lock");
					obj_out_s.writeObject(sorgente);
					obj_out_s.writeObject("Lock");
					obj_out_s.writeObject(destinazione);
				} else {
					obj_out_s.writeObject("Lock");
					obj_out_s.writeObject(destinazione);
					obj_out_s.writeObject("Lock");
					obj_out_s.writeObject(sorgente);					
				}
				obj_out_s.writeObject("Leggi");
				obj_out_s.writeObject(sorgente);
				int vs = (int) obj_in_s.readObject();
				obj_out_s.writeObject("Leggi");
				obj_out_s.writeObject(destinazione);
				int vd = (int) obj_in_s.readObject();
				Thread.sleep(ThreadLocalRandom.current().nextInt(1, 4));
				obj_out_s.writeObject("Aggiorna");
				obj_out_s.writeObject(vs-valoreDaTrasferire);
				obj_out_s.writeObject(sorgente);
				obj_out_s.writeObject("Aggiorna");
				obj_out_s.writeObject(vd+valoreDaTrasferire);
				obj_out_s.writeObject(destinazione);
				obj_out_s.writeObject("Unlock");
				obj_out_s.writeObject(sorgente);
				obj_out_s.writeObject("Unlock");
				obj_out_s.writeObject(destinazione);
			}
			obj_out_s.writeObject("END");
		} catch (IOException | InterruptedException | ClassNotFoundException e) {  
			System.err.println("IO Exception");
		} finally { // Always close it:
			try { 
				socket.close();
			} catch (IOException e) {
				System.err.println("Socket not closed");
			}
			threadcount--; // Ending this thread
		}
	}
}

