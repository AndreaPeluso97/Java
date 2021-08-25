package soluzione;
/** Classe che gestisce gli eventi */
public abstract class Evento
{
	/** Stringa per il tipo di evento */
	public String EType;
	/** Intero per l'anno dell'evento */
	public int EYear;
	/** Stringa per l'indentificativo palazzo */
	public String Ep;
	/** Stringa per il lato del palazzo (nord/sud) */
	public String El;
	/** Intero per la distanza del palazzo dalla riva */
	public int Ed;
	/** Intero per l'ampiezza del palazzo */
	public int Eb;
	/** Intero per l'altezza del palazzo */
	public int Eh;
	
	/** Costruttore per la classe demolish
	 * @param EType tipo di comando
	 * @param EYear anno del comando
	 * @param Ep identidicativo palazzo
	 */    
	public Evento(String EType, int EYear, String Ep)
    {
       this.EType = EType;       
       this.EYear = EYear;         
       this.Ep = Ep;    
       
    }
	/** Costruttore per la classe build
	 * @param EType tipo di comando
	 * @param EYear anno della costruzione
	 * @param Ep identificativo del palazzo
	 * @param El lato del palazzo
	 * @param Ed distanza del palazzo dalla riva
	 * @param Eb ampiezza del palazzo
	 * @param Eh altezza del palazzo
	 */    
	public Evento(String EType, int EYear, String Ep,String El,int Ed,int Eb,int Eh) {
		 this.EType = EType;       
	       this.EYear = EYear;         
	       this.Ep = Ep;  
	       this.El = El;  
	       this.Ed = Ed;       
	       this.Eb = Eb; 
	       this.Eh = Eh; 
	}

	/** Getter per l'anno dell'evento
	 * @return ritorna l'anno del palazzo
	 */ 
	public int getYear(){
		return EYear;
	}
    
   
}
