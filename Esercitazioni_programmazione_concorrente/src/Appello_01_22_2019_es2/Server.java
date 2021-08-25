package Appello_01_22_2019_es2;
import java.net.*;
import java.io.*;

public class Server {
	static final int PORT = 8080;
	static final int maxSlaves = 4;
	static int numSlaves=0;
	public static void main(String[] args) throws IOException {
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Server attivo");
		Banca laBanca = new Banca();
		try {
			while (true) {
				Socket socket = s.accept();
				System.out.println("Server ha accettato connessione");
				try {  
					Slave sl = new Slave(socket, laBanca);
					sl.start();
				} catch (IOException e) {
					// If it fails, close the socket, otherwise the thread will close it:
					socket.close();
				}
			}
		} finally {
			s.close(); 
		}
	}
}
