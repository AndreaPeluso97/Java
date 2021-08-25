package Appello_2018_07_18_es2;

import java.net.*;
import java.io.*;

public class MultiServer {
	static final int PORT = 2222;
	public static void main(String[] args) throws IOException {
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Game Server Started");
		try {
			while (true) {
				Socket socket = s.accept();
				System.out.println("Server accepts connection");
				try {  
					new Server(socket);
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
