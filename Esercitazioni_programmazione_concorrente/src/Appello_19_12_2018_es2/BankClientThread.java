package Appello_19_12_2018_es2;

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

class BankClientThread extends Thread {
	private Socket socket;
	private static int threadcount = 0;
	private ObjectInputStream obj_in_s;
	private ObjectOutputStream obj_out_s;
	private int myID;
	public static int threadCount() {
		return threadcount;
	}
	public BankClientThread(InetAddress addr, int id) {
		System.out.println("Making client " + id);
		myID=id;
		threadcount++;
		try {
			socket = new Socket(addr, 2222);
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
		String str;
		try {
			for (int i = 0; i < 5; i++) {
				System.out.println("Client_"+myID+": new cycle");
				obj_out_s.writeObject("bankOp");
				Operation op1=new Operation("prelievo", 100*Math.random());
				System.out.println("Client_"+myID+" op1: (" + op1.getType() + " " + op1.getAmount() + ")");
				obj_out_s.writeObject(op1);
				str = (String) obj_in_s.readObject();
				System.out.println("Client_"+myID+": response received: "+str);
				Thread.sleep(ThreadLocalRandom.current().nextInt(1, 4));

				obj_out_s.writeObject("bankOp");
				Operation op2=new Operation("deposito", 100*Math.random());
				System.out.println("Client_"+myID+" op2: (" + op2.getType() + " " + op2.getAmount() + ")");
				obj_out_s.writeObject(op2);
				str = (String) obj_in_s.readObject();
				System.out.println("Client_"+myID+": response received: "+str);
				Thread.sleep(ThreadLocalRandom.current().nextInt(1, 4));
			}
			obj_out_s.writeObject("END");
		} catch (IOException e) {
			System.err.println("IO Exception");
		} catch (InterruptedException e) { e.printStackTrace();
		} catch (ClassNotFoundException e) {
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

