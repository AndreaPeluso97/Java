package Appello_01_22_2019_es3;


public class ContoCorrente {
	private int Ammontare=100;
	private String numeroCC;
	public ContoCorrente(String s){
		numeroCC=s;
	}
	public void scriviAmmontare(int v){
		this.Ammontare=v;
	}
	public int getAmmontare(){
		return this.Ammontare;
	}
	public String leggiNome(){
		return this.numeroCC;
	}
}
