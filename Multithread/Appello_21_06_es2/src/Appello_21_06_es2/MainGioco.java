package Appello_21_06_es2;


public class MainGioco {

	Gioco ilGioco;	
	public MainGioco(){
		ilGioco=new Gioco(0);	
	}
	void myRun(){
		new Giocatore(0);
		new Giocatore(1);		
	}
	public static void main(String[] args) {
		new MainGioco().myRun();
	}
}