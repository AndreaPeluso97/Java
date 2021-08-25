
package soluzione;
/** Classe che gestisce i comandi dati in input */
public class Comando {
	/** Stringa che determina il tipo di comando (height o size) */
	public String CmdType; 
	/**Intero che si riferisce all'anno su cui il comando dovrà agire */
    public int CmdYear;
    /**Costruttore della classe comando 
     * @param CmdType tipo di comando
     * @param CmdYear anno su cui agisce il comando
     */
    public Comando(String CmdType, int CmdYear)//costruttore classe
    {
       this.CmdType = CmdType;       
       this.CmdYear = CmdYear;         
       
    }
   
}
