package Appello_01_22_2019_es1;


public class Banca{
	
	static final int numCC=4;
	private ContoCorrente conti[];
	public Banca(){
		conti = new ContoCorrente[numCC];
		for(int i=0; i<numCC; i++){
			conti[i]= new ContoCorrente("conto_"+i);
		}
	}
	public void trasferimento(int valore, int da, int a){
		conti[da].incAmmontare(-valore);
		conti[a].incAmmontare(valore);
	}
	
	public void saldoTotale() {
		int tot=0;
		for(int i=0; i<numCC; i++){
			System.out.println("conto "+i+": saldo="+conti[i].getAmmontare());
			tot+=conti[i].getAmmontare();
		}
		System.out.println("Totalone="+tot);
	}
}
