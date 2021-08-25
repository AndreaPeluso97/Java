package Client;

import java.util.LinkedList;
import java.util.Random;

public class Container {

	
	LinkedList<Integer> list;
	public Container(){
	list=new LinkedList<Integer>();
	}
	
	
	public synchronized void add(String messaggio,int numero){
		
		
		Random r=new Random();
		
		int n=r.nextInt(10);
		if(!list.contains(n)){
		list.add(n);
		
		System.out.println(messaggio+numero+" ho aggiunto "+n);
		
		notifyAll();
}
		}
	
	
	public synchronized void search(String messaggio,int numero){

	
	Random r2=new Random();
	
	int n2=r2.nextInt(10);
	System.out.println(messaggio+numero+" cerco "+n2);

	while(!list.contains(n2)){
	
			try {
				
				System.out.println(messaggio+numero+" attendo"+n2);

				wait();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}	
	
	System.out.println(messaggio+numero+" ho trovato"+n2);

	}
	

}
