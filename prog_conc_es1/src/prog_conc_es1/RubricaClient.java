package prog_conc_es1;


public class RubricaClient {

	private static final int Nthreads = 3;

	public static void main(String[] args) {
		Rubrica miaRubrica = new Rubrica();
		for(int i=0; i<Nthreads; i++) {
			new RubricaThread(i, miaRubrica);
		}
	}


}
