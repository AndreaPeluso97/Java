package Rubrica2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	
	public static void main(String[] args){
	Rubrica r=new Rubrica();
	
	ServerSocket s=null;
	
	try{
		
		s=new ServerSocket(2345);
		while(true){
			System.out.println("porco dio");
			Socket Sac=s.accept();
			new Thread(new ServerThread(Sac,r)).start();
				
		}
		
		
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
}