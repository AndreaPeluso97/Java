package eserc_7;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class multiclient {
	static int mt=4;
	public static void main(String[] args) throws InterruptedException, IOException{
		int i=0;
		InetAddress address=InetAddress.getByName(null);
		while(i<mt){
		new client(address);
		i++;
		Thread.sleep(1000);
		}
	}
}
