package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
	static ServerSocket ss;

	
	Socket s;
	public GameServer(Scacchiera sc) throws IOException {
		
	}
	
	
	public static void main(String[] args) throws IOException{
		try {
			ss=new ServerSocket(2222);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scacchiera s = new Scacchiera();
		while(true){
			
			Socket sac = ss.accept();
			
			new ServerThreads(sac,s);
		}
		
	}

}

