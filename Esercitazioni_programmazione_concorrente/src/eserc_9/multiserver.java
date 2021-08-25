package eserc_9;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class multiserver {

	public static void main(String[] args) throws IOException{

		ServerSocket ss=null;

		try{
			ss=new ServerSocket(8080);
			while(true){
				Socket s=ss.accept();
				try{
					new server(s);
				}catch(Exception e){
				}
			}
		}finally{
			ss.close();
		}

	}
}
