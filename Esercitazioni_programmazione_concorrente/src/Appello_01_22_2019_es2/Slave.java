package Appello_01_22_2019_es2;

import java.net.*;
import java.io.*;

public class Slave extends Thread {
	private Socket socket;
	private ObjectInputStream obj_in_s;
	private ObjectOutputStream obj_out_s;
	private Banca laBanca;
	public Slave(Socket s, Banca b) throws IOException {
		socket = s;
		obj_out_s = new ObjectOutputStream(socket.getOutputStream());
		obj_in_s = new ObjectInputStream(socket.getInputStream());
		laBanca = b;
		System.out.println("Slave started");
	}
	public void run() {
		try {
			while (true) {
				System.out.println("Slave starting cycle");
				String str = (String) obj_in_s.readObject();
				System.out.println("Received: " + str);
				if (str.equals("END")) {
					laBanca.saldoTotale();
					break;
				}
				if(str.equals("Transfer")){
					int v = (int)obj_in_s.readObject();
					int cc1 = (int)obj_in_s.readObject();
					int cc2 = (int)obj_in_s.readObject();
					System.out.println("Slave: transfer "+v+" from "+cc1+" to "+cc2);
					laBanca.trasferimento(v, cc1, cc2);
					obj_out_s.writeObject("Transfer completed");				
				} else if(str.equals("Leggi")) {
					int cc = (int)obj_in_s.readObject();
					System.out.println("Slave: reading "+cc);
					int v=laBanca.leggi(cc);
					obj_out_s.writeObject(v);
				} else if(str.equals("Aggiorna")) {
					int v = (int)obj_in_s.readObject();
					int cc = (int)obj_in_s.readObject();
					System.out.println("Slave: updating "+cc+" to "+v);
					laBanca.aggiorna(v, cc);
				} else if(str.equals("Lock")) {
					int cc = (int)obj_in_s.readObject();
					System.out.println("Slave: locking "+cc);
					laBanca.lock(cc);
				} else if(str.equals("Unlock")) {
					int cc = (int)obj_in_s.readObject();
					System.out.println("Slave: unlocking "+cc);
					laBanca.unlock(cc);
				} else {
					obj_out_s.writeObject("Command unknown");
					System.out.println("Slave: could not understand request");						
				}
			}
			System.out.println("closing...");
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("IO Exception");
		} finally {
			try {
				socket.close();
				System.out.println("Socket closed");
			} catch (IOException e) {
				System.err.println("Socket not closed");
			}
		}
	}
}
