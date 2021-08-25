package Appello_06_02_2018_es2;

import java.util.concurrent.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class InStreamMonitor extends Thread{
	private MyBuffer2 myBuf;
	private ObjectInputStream myIns;
	private ObjectOutputStream myOuts;
	InStreamMonitor(MyBuffer2 buf, ObjectInputStream ins, ObjectOutputStream outs){
		myBuf= buf;
		myIns = ins;
		myOuts=outs;
		start();
	}
	public void run() {
		String str;
		while(true){
			try {
				str=(String) myIns.readObject();
				System.out.println("proxy monitor received "+str);
				if(str.equals("newsAvailable")){
					// System.out.println("proxy monitor sets fresh ");
					// myBuf.setFresh();
					myOuts.writeObject("GetNews");
					str = (String) myIns.readObject();
					System.out.println("proxy: obtained "+str);
					myBuf.setNews(str);	
				}
			} catch (ClassNotFoundException | IOException | InterruptedException e) { } 
		}
	}
}

