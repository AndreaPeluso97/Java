package soluzione;
/**Classe che gestisce gli eventi di demolizione*/
public class Demolish extends Evento{
	/**
	    *costruttore per la lista dei palazzi costruiti
		*@param EType tipo di evento
		*@param EYear anno del palazzo
		*@param Ep identificativo del palazzo
		*/
    public Demolish(String EType, int EYear, String Ep) {
		super(EType, EYear, Ep);//chiamo il costruttore della super classe evento
	}

};
