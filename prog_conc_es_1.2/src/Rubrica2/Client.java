package Rubrica2;


public class Client{
	
	private static final int Nthreads=3;
	
	public static void main(String[] args){
		
		
		for(int i=0;i<Nthreads;i++){
			new ClientThread(i).start();
		}
		
	}
}
