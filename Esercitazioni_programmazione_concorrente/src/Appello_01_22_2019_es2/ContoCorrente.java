package Appello_01_22_2019_es2;


public class ContoCorrente {
	private int Ammontare=100;
	private String myName;
	private Boolean locked = false;
	public ContoCorrente(String s){
		myName=s;
	}
	public void scriviAmmontare(int v){
		this.Ammontare=v;
	}
	public int getAmmontare(){
		return this.Ammontare;
	}
	public void incAmmontare(int valore){ // synchronized
		int temp=Ammontare;
		temp+=valore;
		Ammontare=temp;
	}
	public String leggiNome(){
		return this.myName;
	}
	public synchronized void lock(){
		while(this.locked) {
			try {
				wait();
			} catch (InterruptedException e) {	}
		}
		this.locked=true;
	}
	public synchronized void unlock(){
		this.locked=false;
		notifyAll();
	}
}

