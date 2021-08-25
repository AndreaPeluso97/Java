package Appello_01_22_2019_es1;


public class ContoCorrente {
	private int Ammontare=100;
	private String myName;
	public ContoCorrente(String s){
		myName=s;
	}
	public int getAmmontare(){
		return this.Ammontare;
	}
	public synchronized void incAmmontare(int valore){
		int temp=Ammontare;
		temp+=valore;
		Ammontare=temp;

	}
	public String leggiNome(){
		return this.myName;
	}
	
}
