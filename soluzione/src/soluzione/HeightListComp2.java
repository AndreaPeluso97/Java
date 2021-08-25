package soluzione;

import java.util.Comparator;
/** Classe per comparare due palazzi in base al lato esterno */
public class HeightListComp2 implements Comparator<HeightList>{
	/** Metodo per comparare la fine di due palazzi 
	 * @param     e1 Palazzo 1
	 * @param     e2 Palazzo 2
     * @return    ritorna il risultato della comparazione*/
    public int compare(HeightList e1, HeightList e2) {
        return e1.getfine().compareTo(e2.getfine());
    }
}
