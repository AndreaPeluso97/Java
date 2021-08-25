package soluzione;

import java.util.Comparator;
/** Classe per comparare due palazzi in base al lato interno */
public class HeightListComp implements Comparator<HeightList>{
	/** Metodo per comparare l'inzio di due palazzi 
	 * @param     e1 Palazzo 1
	 * @param     e2 Palazzo 2
     * @return    ritorna il risultato della comparazione*/
    public int compare(HeightList e1, HeightList e2) {
        return e1.getinizio().compareTo(e2.getinizio());
    }
}
