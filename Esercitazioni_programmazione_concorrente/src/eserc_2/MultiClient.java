package eserc_2;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MultiClient {
	
	static final int Nthreads=3;
	
	public static void main(String[] args) throws UnknownHostException {
		
		InetAddress Address=InetAddress.getByName(null);
		int i=0;
		while(i<=Nthreads){
			new Client(Address,i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i++;
		}
		
	}

}
