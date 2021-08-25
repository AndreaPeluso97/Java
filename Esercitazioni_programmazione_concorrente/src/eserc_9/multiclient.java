package eserc_9;

import java.io.IOException;
import java.net.InetAddress;

public class multiclient {
	static int nt=3;

	public static void main(String[] args) throws InterruptedException, IOException{

		int i=0;
		InetAddress address=InetAddress.getByName(null);
		while(i<nt){
			new client(address);
			i++;
			Thread.sleep(100);
		}	
	}
}
