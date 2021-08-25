package soluzione;

/** Classe che gestisce la lista dei palazzi per calcolare l'altezza */
public class HeightList{
	/**stringa che identifica il nome del palazzo*/
	String np;
	/**intero che identifica l'inizio del palazzo */
	Integer inizio;
	/**intero che identifica la fine del palazzo */
	Integer fine;
	/**intero che identifica l'altezza del palazzo */
	int altezza;
	
	/**
	 * costruttore della classe HeightList
	 * @param np nome del palazzo
	 * @param inizio inizio del palazzo
	 * @param fine fine del palazzo
	 * @param altezza altezza del palazzo */
	    public HeightList(String np, Integer inizio, Integer fine, int altezza) {
	    	this.np = np;       
	        this.inizio = inizio;         
	        this.fine = fine; 
	        this.altezza = altezza;    

	        
		}

	   
	    /**
	     *metodo che ritorna l'inizio del palazzo
	     *@return ritorna l'inizio del palazzo
	     */
	    public Integer getinizio(){//ritorna altezza(funzoine utilizzata per l'ordinamento)
			return inizio;
		}
	    /**
	     * metodo che ritorna la fine del palazzo
	     *@return ritorna la fine del palazzo
	     */
	    public Integer getfine(){//ritorna altezza(funzoine utilizzata per l'ordinamento)
			return fine;
		}
	    /**
	     * metodo che ritorna l'altezza del palazzo
	     * @return ritorna l'altezza del palazzo
	     */
	    public Integer getaltezza(){//ritorna altezza(funzoine utilizzata per l'ordinamento)
			return altezza;
		}
	    
	    
	};


