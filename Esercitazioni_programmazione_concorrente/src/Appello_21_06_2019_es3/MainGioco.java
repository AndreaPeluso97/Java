package Appello_21_06_2019_es3;


public class MainGioco {
	
	public MainGioco(){
	}
	void myRun(){
		new Giocatore(0);
		new Giocatore(1);		
	}
	public static void main(String[] args) {
		new MainGioco().myRun();
	}
}

