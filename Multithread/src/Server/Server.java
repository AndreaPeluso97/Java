package Server;

import java.net.ServerSocket;
import java.net.Socket;

import Client.Container;

public class Server {

	
	
	public Server() {
		
	}
	
	public static void main(String[] args){
		ServerSocket s=null;
		Container c=new Container();


		try{
		s=new ServerSocket(2222);

		
		while(true){
			
			Socket sac = s.accept();
	
				new ServerThread(c,sac);
	
		}
		}catch(Exception e){}
}
}