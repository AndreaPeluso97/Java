package Appello_21_06_2019_es2;

import java.io.IOException;



public class MainGioco {

	Gioco ilGioco;	
	public MainGioco(){
		ilGioco=new Gioco(0);	
	}
	void myRun() throws IOException{
		new Giocatore(0);
		new Giocatore(1);		
	}
	public static void main(String[] args) throws IOException {
		new MainGioco().myRun();
	}
}
