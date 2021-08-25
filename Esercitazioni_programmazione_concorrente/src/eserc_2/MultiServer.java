package eserc_2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
	
	public static void main(String[] args) throws IOException{
		
		ServerSocket ss=new ServerSocket(8080);
		System.out.println("Server Avviato!");
		
		try {
			
			while(true){
				
				Socket s=ss.accept();
				System.out.println("Server Accetta connessioni...");
				
				try{
					
				new Server(s);
				}catch(Exception e){s.close();}
				
				
			}
		} finally{ ss.close();}
		
	}

}
