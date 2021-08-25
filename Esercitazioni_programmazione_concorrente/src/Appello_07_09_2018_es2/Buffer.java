package Appello_07_09_2018_es2;


public class Buffer {
	private String value;
	Buffer(String s){
		value=s;
		System.out.println("Buffer inizializzato con "+s);
	}
	public synchronized void put(String s){
		value=s;
		System.out.println("Buffer: value set to  "+s);
	}
	public synchronized String get(){
		System.out.println("Buffer: "+value+" read ");
		return value;
	}
}
