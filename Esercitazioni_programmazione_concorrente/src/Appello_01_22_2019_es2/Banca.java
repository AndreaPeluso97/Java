package Appello_01_22_2019_es2;



public class Banca{
	static final int numCC=2;
	private ContoCorrente conti[];
	public Banca(){
		conti = new ContoCorrente[numCC];
		for(int i=0; i<numCC; i++){
			conti[i]= new ContoCorrente("conto_"+i);
		}
	}
	public int leggi(int cc){
		return conti[cc].getAmmontare();
	}
	public void aggiorna(int valore, int cc){
		conti[cc].scriviAmmontare(valore);
	}
	public void lock(int cc){
		conti[cc].lock();
	}
	public void unlock(int cc){
		conti[cc].unlock();
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

