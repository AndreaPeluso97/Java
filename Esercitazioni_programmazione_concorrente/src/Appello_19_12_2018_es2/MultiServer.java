package Appello_19_12_2018_es2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
	
	
	
public static void main(String[] args){
	
	ServerSocket ss=null;
	
	try{
		
		ss=new ServerSocket(2222);
		
		while(true){
			Socket s=ss.accept();
			try{
			new MonoServer(s);
			}catch(Exception e){s.close();}
			
			
		}
		
		
		
	}catch(Exception e){try {
		ss.close();
	} catch (IOException e1) {
		e1.printStackTrace();
	}}
	
}

}
