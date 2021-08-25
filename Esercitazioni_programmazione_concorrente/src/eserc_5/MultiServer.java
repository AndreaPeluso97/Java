package eserc_5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
	
	public static void main(String[] args) throws IOException{
		
		ServerSocket ss = null;
		try{
			ss=new ServerSocket(8080);
			
			while(true)
			{
				
				Socket s=ss.accept();
				try{
				new Server(s);
				}catch(Exception e){s.close();}
			}
			
		}finally{
			ss.close();
		}
	}

}
