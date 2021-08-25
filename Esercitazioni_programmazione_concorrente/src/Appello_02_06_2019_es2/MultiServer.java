package Appello_02_06_2019_es2;

import java.net.*;
import java.io.*;

public class MultiServer {


	public static void main(String[] args) throws IOException {

		ServerSocket ss=new ServerSocket(2222);

		try{
			while(true){	
				Socket s=ss.accept();
				try{
					new Server(s);
				}catch(Exception e){s.close();}
			}
		}finally{ss.close();}
	}
}
