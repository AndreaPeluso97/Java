package Appello_06_02_2018_es2;

import java.net.*;
import java.io.*;

public class MultiServer {
	static final int PORT = 8099;
	public static void main(String[] args) throws IOException {
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Server Started");
		try {
			while (true) {
				Socket socket = s.accept();
				System.out.println("Server accepts connection");
				try {  
					new NewsServer(socket);
				} catch (IOException e) {
					System.err.println("Server thread creation failed.");
					socket.close();
				}
			}
		} finally {
			s.close();
		}
	}
}
