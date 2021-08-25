package Appello_06_02_2018_es1;



public class ProdCons {
	public static void main(String[] args) {
		MyBuffer buf=new MyBuffer();
	    new Produttore(buf).start();
	    Consumatore cons1 = new Consumatore(0, buf);
	    Consumatore cons2 = new Consumatore(1, buf);
	    Consumatore cons3 = new Consumatore(2, buf);
	    cons1.start();
	    cons2.start();
	    cons3.start();
	    try {
			cons1.join();
		    cons2.join();
		    cons3.join();
		} catch (InterruptedException e) {e.printStackTrace(); }
	}
}
