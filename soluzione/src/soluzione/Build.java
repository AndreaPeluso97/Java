package soluzione;
/**Classe che gestisce gli eventi di costruzione*/
public class Build extends Evento{
	/**
    *costruttore per la lista dei palazzi costruiti
	*@param eType2 tipo di evento
	*@param eYear2 anno del palazzo
	*@param ep2 identificativo del palazzo
	*@param el2 lato del palazzo
	*@param ed2 distanza del palazzo
	*@param eb2 ampiezza palazzo
	*@param eh2 altezza palazzo
	*/
	public Build(String eType2, int eYear2, String ep2, String el2, int ed2,int eb2, int eh2) {
		super(eType2, eYear2, ep2, el2, ed2, eb2, eh2);//chiamo il costruttore della superclasse evento
	}
	

}




