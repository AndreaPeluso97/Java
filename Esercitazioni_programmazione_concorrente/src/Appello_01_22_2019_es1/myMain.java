package Appello_01_22_2019_es1;


public class myMain {
	static final int numOp=4;

	public static void main(String[] args) throws InterruptedException {
		Banca b=new Banca();
		Thread ops[];
		ops = new Operazione[numOp];
		for(int i=0; i<numOp; i++){
			ops[i]= new Operazione(i, b);
		}
		for(int i=0; i<numOp; i++){
			ops[i].join();
		}
		b.saldoTotale();
	}
}

