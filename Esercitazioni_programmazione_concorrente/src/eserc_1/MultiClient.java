package eserc_1;

import java.io.IOException;
import java.net.InetAddress;

public class MultiClient{

	private final static int Nthreads=3;

	public static void main(String[] args) throws IOException, InterruptedException{

		int i=0;
		InetAddress Addr=InetAddress.getByName(null);
		while(i<=Nthreads){
			new McdClient(Addr,i);
			Thread.sleep(1000);
			i++; }}}