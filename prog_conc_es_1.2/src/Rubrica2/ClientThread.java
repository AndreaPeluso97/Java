package Rubrica2;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientThread  extends Thread{
	
	
	ObjectOutputStream oos;
	ObjectInputStream ois;
	Socket mySocket;
	int myid;
	
	
	public ClientThread(int i){
		
		myid=i;
		
		try{
			mySocket=new Socket(InetAddress.getByName(null),2345);
			oos=new ObjectOutputStream(mySocket.getOutputStream());
			ois=new ObjectInputStream(mySocket.getInputStream());
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public void run(){
		
		
		try{
			
			oos.writeObject("Aggiungi");
			oos.writeObject(new numero("Zia Pina", "+390212345678"));
			
			oos.writeObject("Elimina");
			oos.writeObject("Zia Pina");
			
			oos.writeObject("InRubrica");
			oos.writeObject("Zia Pina");
			boolean esito = (boolean)ois.readObject();
			if(esito) {
				System.out.println("Client "+myid+": Zia Pina e` in rubrica");
			}
			else System.out.println("Client "+myid+": Zia Pina non e` in rubrica");
			oos.writeObject("FineServizio");
			mySocket.close();
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
}
	
}