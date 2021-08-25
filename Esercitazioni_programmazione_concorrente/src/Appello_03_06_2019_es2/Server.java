package Appello_03_06_2019_es2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	
	public Server(){
			
	}
	
	public static void main(String[] args){
		
		Rubrica miaRubrica=new Rubrica();
		ServerSocket ss=null;
		try{	
			ss = new ServerSocket(2222);
		while(true){
			Socket s=ss.accept();
			new ServerThread(s,miaRubrica);	
		}
		}catch(Exception e){}finally{
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
