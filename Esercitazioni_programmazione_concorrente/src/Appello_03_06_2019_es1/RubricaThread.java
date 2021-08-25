package Appello_03_06_2019_es1;

public class RubricaThread extends Thread{
	
	private Rubrica miaRubrica;
	
	public RubricaThread(Rubrica r){
		
		miaRubrica = r;
		start();
	}
	
	
	public void run(){
		miaRubrica.aggiungiNumero("Zia Pina", "+390212345678");
		miaRubrica.aggiungiNumero("Giorgio", "+390213579");
		miaRubrica.aggiungiNumero("Adalberto", "+390224680");
		miaRubrica.inRubrica("Zia Pina");
		miaRubrica.eliminaNumero("Zia Pina");
		miaRubrica.inRubrica("Giorgio");
		miaRubrica.eliminaNumero("Giorgio");



		String num = miaRubrica.trova("Zia Pina");
		System.out.println("Il numero della zia Pina e` "+ num);
		
	}

}
