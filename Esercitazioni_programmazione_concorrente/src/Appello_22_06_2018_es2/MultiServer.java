package Appello_22_06_2018_es2;

import java.net.*;
import java.io.*;

public class MultiServer {
	static final int PORT = 8080;
	public static void main(String[] args) throws IOException {
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Game Server Started");
		try {
			while (true) {
				Socket socket = s.accept();
				System.out.println("Server accepts connection");
				try {  
					new GameServer(socket);
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
