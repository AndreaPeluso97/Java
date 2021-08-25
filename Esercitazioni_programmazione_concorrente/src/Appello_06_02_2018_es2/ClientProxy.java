package Appello_06_02_2018_es2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientProxy {
	private ObjectInputStream obj_in_s;
	private ObjectOutputStream obj_out_s;
	private MyBuffer2 newsBuf;
	private InStreamMonitor inMon;

	ClientProxy(Socket s){
		try {
			obj_in_s = new ObjectInputStream(s.getInputStream());
			obj_out_s = new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {e.printStackTrace();}
		newsBuf=new MyBuffer2();
		inMon=new InStreamMonitor(newsBuf, obj_in_s, obj_out_s);
	}
	public String lettura(){
		String str="";
		System.out.println("proxy: lettura");
		try {
			str=newsBuf.getNews();
			System.out.println("proxy: obtained "+str);
		} catch (InterruptedException e) { e.printStackTrace(); }
		return str;
	}
	public void shutdown(){
		try {
			obj_out_s.writeObject("END");
		} catch (IOException e) { e.printStackTrace(); }
	}
	public boolean notiziaFresca(){
		return newsBuf.newsIsFresh();
	}
}