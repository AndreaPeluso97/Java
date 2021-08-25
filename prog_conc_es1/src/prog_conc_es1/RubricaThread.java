package prog_conc_es1;

public class RubricaThread extends Thread{
	int myId;
	Rubrica rub;

	public RubricaThread(int n, Rubrica r) {
		rub = r;
		myId=n;
		start();
	}

	public void run() {
		rub.aggiungiNumero("Zia Pina", "+390212345678");
		rub.aggiungiNumero("Giorgio", "+390213579");
		rub.eliminaNumero("Giorgio");
		rub.aggiungiNumero("Adalberto", "+390224680");
		String num = rub.trova("Zia Pina");
		System.out.println("Client "+myId+": il numero della zia Pina e` "+ num);
		System.out.println("Client "+myId+" termina");
	}

}
