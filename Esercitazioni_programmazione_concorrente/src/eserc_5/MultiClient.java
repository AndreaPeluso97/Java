package eserc_5;

import java.io.IOException;
import java.net.InetAddress;

public class MultiClient {
	
	private static int NThreads=3;
	
	public static void main(String[] args) throws InterruptedException, IOException{
	int	i=0;
	InetAddress address=InetAddress.getByName(null);

	while(i<NThreads){

		new Client(address);
		Thread.sleep(100);
		i++;
	}
	}
}
