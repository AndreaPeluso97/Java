package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;



public class ClientThread extends Thread{
	
	
	private String messaggio;
	
	private int numero;
	
	Container cont;
	
	ObjectOutputStream oos;

	ObjectInputStream ois;
	
	Socket s;


	public ClientThread(String mex,int n,Container c) throws IOException{
		
		s=new Socket(InetAddress.getByName(null),2222);
		
		oos=new ObjectOutputStream(s.getOutputStream());
		ois=new ObjectInputStream(s.getInputStream());
		
		messaggio=mex;
		
		numero=n;
		
		cont=c;
		
		start();
		
	}
	

	
	 public void run(){		
		 
		 
		 try {
			 System.out.println(messaggio+" "+numero+" esegue: ");

				oos.writeObject("add");
			 System.out.println(messaggio+" "+numero+" esegue: ");
			oos.writeObject("search");
			
			
			s.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		 
			

			/*cont.add(messaggio,numero);
			
			cont.search(messaggio,numero);

			*/
		}
	}
	

