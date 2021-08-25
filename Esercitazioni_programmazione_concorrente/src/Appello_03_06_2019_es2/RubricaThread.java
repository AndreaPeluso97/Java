package Appello_03_06_2019_es2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class RubricaThread extends Thread{
	
	Socket s;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	
	
	public RubricaThread() throws UnknownHostException, IOException{
		s=new Socket(InetAddress.getByName(null),2222);
		oos=new ObjectOutputStream(s.getOutputStream());
		ois=new ObjectInputStream(s.getInputStream());
		start();
	}
	
	
	public void run(){
		
		try{
		oos.writeObject("aggiungi");
		oos.writeObject("Zia Pina");
		oos.writeObject("+390212345678");
		oos.writeObject("aggiungi");
		oos.writeObject("Giorgio");
		oos.writeObject("+390213579");
		oos.writeObject("aggiungi");
		oos.writeObject("Adalberto");
		oos.writeObject("+390224680");
		oos.writeObject("In Rubrica");
		oos.writeObject("Zia Pina");
		oos.writeObject("Elimina");
		oos.writeObject("Zia Pina");
		oos.writeObject("In Rubrica");
		oos.writeObject("Giorgio");
		oos.writeObject("Elimina");
		oos.writeObject("Giorgio");
		oos.writeObject("Trova");
		oos.writeObject("Zia Pina");

		String num = (String) ois.readObject();
		System.out.println("Il numero della zia Pina e` "+ num);
		
		}catch(Exception e){}finally{
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
