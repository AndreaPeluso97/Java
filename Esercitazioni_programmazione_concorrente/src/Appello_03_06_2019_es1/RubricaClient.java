package Appello_03_06_2019_es1;


public class RubricaClient {

	public static void main(String[] args) {
		Rubrica miaRubrica = new Rubrica();
		
		for(int i=0;i<10;i++){
		new RubricaThread(miaRubrica);
		
		}
		
		
	}

}
