package Client;

import java.io.IOException;


public class Client {
	
	public static void main(String[] args) throws InterruptedException, IOException{
		
		final int NThreads=10;
		
		final String messaggio="ciao io sono il thread ";
		
		Container c=new Container();
		for(int i=0;i<NThreads;i++){
			
			new ClientThread(messaggio,i,c);
		

			
		}
		
		
		
	}

}
