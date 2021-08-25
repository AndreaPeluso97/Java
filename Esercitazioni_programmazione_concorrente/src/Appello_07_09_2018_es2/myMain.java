 package Appello_07_09_2018_es2;



public class myMain {
	public static void main(String[] args) throws InterruptedException {
		Buffer theBuffer=new Buffer("default");
		new UserThread(theBuffer);
		while(true){
			Thread.sleep(5000);
			String s=theBuffer.get();
			System.out.println("Main uses "+s);
		}
	}
}

