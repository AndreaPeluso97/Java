package Appello_06_02_2018_es2;

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

class ClientThread extends Thread {
	private Socket socket;
	//	private BufferedReader in;
	//	private PrintWriter out;
	private static int counter = 0;
	private static int threadcount = 0;
	private ClientProxy myProxy;
	private int myID;

	public static int threadCount() {
		return threadcount;
	}
	public ClientThread(InetAddress addr, int id) {
		System.out.println("Making client " + id);
		myID=id;
		threadcount++;
		try {
			socket = new Socket(addr, 8099);
			System.out.println("Client connected");
		} catch (IOException e){System.err.println("Socket failed");}
		myProxy = new ClientProxy(socket);
		start();
	}

	public void run() {
			for (int i = 0; i < 50; i++) {
				System.out.println("Client_"+myID+": looking for news");
				if(myProxy.notiziaFresca()){
					String str = myProxy.lettura();
					System.out.println("Client_"+myID+": read: "+str);					
				} else {
					try {
						System.out.println("client: no fresh news, going to sleep");
						Thread.sleep(400);
					} catch (InterruptedException e) { e.printStackTrace(); }
				}
				try {
					Thread.sleep(ThreadLocalRandom.current().nextInt(300, 800));
				} catch (InterruptedException e) {e.printStackTrace(); }
			}
			myProxy.shutdown();
				try {
					socket.close();
				} catch (IOException e) { e.printStackTrace(); }
			threadcount--; // Ending this thread
		}
	}

