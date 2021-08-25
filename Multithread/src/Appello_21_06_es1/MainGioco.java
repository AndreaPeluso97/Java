package Appello_21_06_es1;


public class MainGioco {

	Gioco ilGioco;	
	public MainGioco(){
		ilGioco=new Gioco(0);	
	}
	void myRun(){
		new Giocatore(0, ilGioco);
		new Giocatore(1, ilGioco);		
	}
	public static void main(String[] args) {
		new MainGioco().myRun();
	}
}
