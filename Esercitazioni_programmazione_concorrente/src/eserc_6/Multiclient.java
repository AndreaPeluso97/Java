package eserc_6;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Multiclient {
	
	static int NThreads=4;
public static void main(String[] args) throws InterruptedException, IOException{
	int i=0;
	InetAddress address=InetAddress.getByName(null);
	while(i<NThreads){
		
		new client(address);
		i++;
		Thread.sleep(1000);
		
	}
	
}
}
