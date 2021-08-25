package Appello_07_09_2018_es2;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ReadServer {
	static final int PORT = 8099;
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String str;
		Socket socket;
		ServerSocket s;
		ObjectOutputStream obj_out_s = null; 
		try {
			s = new ServerSocket(PORT);
			socket = s.accept();
		        System.out.println("Server: accepted connection");
			obj_out_s = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("Server: streams ready");
		} catch (IOException e) { e.printStackTrace(); }
		System.out.println("Server: entering cycle");
		while(true){
			System.out.print("> ");
			str = scanner.nextLine();
			try {
				System.out.println("read from stdin:"+str);
				obj_out_s.writeObject(str);
			} catch (IOException e) { e.printStackTrace(); }
		}	
	}
}

