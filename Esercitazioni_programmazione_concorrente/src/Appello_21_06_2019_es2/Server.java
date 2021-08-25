package Appello_21_06_2019_es2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


	public Server() throws IOException{

	}

	public static void main(String[] args) throws IOException{


		ServerSocket ss=null;
		Gioco gioco=new Gioco(0);

		try{
			ss=new ServerSocket(2345);
			System.out.println("Server avviato");
			while(true){
				Socket s = ss.accept();
				new ServerThread(s,gioco);
			}}catch(Exception e){}finally{

				try{
					ss.close();

				}catch(Exception e){}

			}




	}


}

