package soluzione;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**Classe che gestisce le operazioni di size e height*/
 public class Operazioni {
	/**lista degli eventi*/
	public List<Evento> Eventi;
	/**lista delle demolizioni*/
	public List<Demolish> demolizione;
	/**lista dei comandi*/
	public List<Comando> Comandi;
	/**lista deli palazzi*/
	public List<HeightList> ListaParziale;
	/**lista deli palazzi nord*/
	List<HeightList> ListaparzialeN = new ArrayList<HeightList>();//crea lista di ListaN
	/**lista deli palazzi sud*/
	List<HeightList> ListaparzialeS = new ArrayList<HeightList>();//crea lista di ListaS
	/**Mappa hash contenente gli eventi di demolizione*/
	Map<String, Integer> MappaHash = new HashMap<>();
    /**
    *costruttore operazioni
	*@param Eventi lista degli eventi
	*@param demolizione lista degli eventi demolizione
	*@param Parziale lista dei palazzi
	*/
	public Operazioni(List<Evento> Eventi,List<Demolish> demolizione,List<HeightList> Parziale){//costruttore classe passando le liste
		this.Eventi=Eventi ;
		this.demolizione=demolizione;
		this.ListaParziale=Parziale;	
		    
	}
	/**
    metodo che rimpie la mappa Hash con i valori dei palazzi demoliti
	*/
	public void riempiHashMap(){
	for (Demolish itemD: demolizione) {//riempio la mappa Hash delle demolizioni con i dati <identificativo palazzo, anno di demolizione>
        MappaHash.put(itemD.Ep,itemD.EYear);
        }
    }
	/**
    metodo che svuota la mappa Hash
	*/
	public void svuotaHashMap(){
	        MappaHash.clear();
	    }
	
	/**
    metodo che calcola l'ampiezza dello skyline
	@param y anno calcolato per l'ampiezza
	@return ampiezza dello skyline
	*/
	public int size(int y){
		/**verifica esistenza evento*/
		boolean esiste;//variabile booleana esiste
		/**valori nord e sud*/
	   	  int maxN=0,maxS=0;//inizializzo variabili contenitori dei valori massimi nord e sud
		 for (Evento itemE: Eventi) {//cicla gli eventi
			 if(itemE.EYear>y){//cicla fino all'anno dato
				 break;//ferma ciclo
			 }else{
				 esiste=true;//per ogni evento setto variabile a true
				 if(itemE instanceof Build){//ciclo gli eventi build
				 if(MappaHash.containsKey(itemE.Ep) && (MappaHash.get(itemE.Ep)<=y) && (MappaHash.get(itemE.Ep)>=itemE.EYear)){
							esiste=false; //se è stato demolito non esiste     	
					 } 	 
				if(esiste){//se esiste procedo 
				 if(itemE.El.contains("N")){//se è nord
					 if(maxN<((itemE.Ed)+(itemE.Eb))){//trovo massimo
						 maxN=itemE.Ed+itemE.Eb;//aggiorno massimo nord
					 }}else if(itemE.El.contains("S")){//se è sud
    					 if(maxS<(itemE.Ed+itemE.Eb)){//trovo massimo
    						 maxS=itemE.Ed+itemE.Eb;//aggiorno massimo sud
    					 }}}}}}
		 return ((maxN)+(maxS));//ritorno operazione size
	}
	
	/**
    metodo che calcola l'altezza dello skyline
	@param y anno calcolato per l'altezza
	@return altezza dello skyline
	*/
	public int height(int y){
		/**altezza skyline*/
		int altezza=0;
		/**verifica esistenza evento*/
		boolean esiste;//variabile booleana esiste
		 for (Evento itemE: Eventi) {//cicla gli eventi
			 if(itemE.EYear>y){//cicla fino all'anno dato
				 break;//ferma ciclo
			 }else{
				 esiste=true;//per ogni evento setto variabile a true
				 if(itemE instanceof Build){//ciclo gli eventi build
				 if(MappaHash.containsKey(itemE.Ep) && (MappaHash.get(itemE.Ep)<=y) && (MappaHash.get(itemE.Ep)>=itemE.EYear)){
							esiste=false; //se è stato demolito non esiste     	
					 }  		 
				if(esiste){//se esiste procedo 
				 if(itemE.El.contains("N")){//se contiene nord/sud 
				ListaparzialeN.add(new HeightList(itemE.Ep,itemE.Ed,(itemE.Eb+itemE.Ed),itemE.Eh));//aggiungo nuovo palazzo alla lista

				 
				 }else if(itemE.El.contains("S")){

				ListaparzialeS.add(new HeightList(itemE.Ep,itemE.Ed,(itemE.Eb+itemE.Ed),itemE.Eh));//aggiungo nuovo palazzo alla lista

					 }
				 }}}}
		 
		 
		
			
		 
			//lato nord
		 if(!ListaparzialeN.isEmpty()){
			/**inizio più piccolo tra i palazzi nord*/
		    HeightList mininizion = Collections.min(ListaparzialeN, new HeightListComp());
			/**fine più grande tra i palazzi nord*/
		    HeightList maxfinen = Collections.max(ListaparzialeN, new HeightListComp2());

			ListaparzialeN.sort(Comparator.comparing(HeightList::getaltezza));//Ordina liste di eventi in base all'altezza
		    Collections.reverse(ListaparzialeN);//oridno la lista dall'altezza maggiore a quella minore

		    for(int i=mininizion.inizio;i<=maxfinen.fine;i++){//analizzo tutto la linea dello skyline
	    	int tmp=0;
		    		for(HeightList itemZ:ListaparzialeN){//per ogni punto guardo qual'è il palazzo con altezza maggiore
		    			if(itemZ.inizio<=i && itemZ.fine.intValue()>i){
		    				if(itemZ.altezza>tmp){
		    					tmp=itemZ.altezza;//assegno alla variabile temporanea l'altezza più alta in quel punto
		    				}
		    		}
		    }
		    	altezza+=tmp;//sommo l'altezza di quel punto agli altri calcolati in precedenza */
		    	}
		    
		    ListaparzialeN.clear();//pulisco la lista
		 }
		    //lato sud
		 if(!ListaparzialeS.isEmpty()){

			/**inizio più piccolo tra i palazzi sud*/
		    HeightList mininizios = Collections.min(ListaparzialeS, new HeightListComp());
			/**fine più grande tra i palazzi sud*/
		    HeightList maxfines = Collections.max(ListaparzialeS, new HeightListComp2());

			ListaparzialeS.sort(Comparator.comparing(HeightList::getaltezza));//Ordina liste di eventi in base all'altezza
		    Collections.reverse(ListaparzialeS);//oridno la lista dallì'altezza maggiore a quella minore

		    for(int i=mininizios.inizio;i<=maxfines.fine;i++){//analizzo tutto la linea dello skyline
	    		int tmp=0;
		    		for(HeightList itemZ:ListaparzialeS){//per ogni punto guardo qual'è il palazzo con altezza maggiore
		    			if(itemZ.inizio<=i && itemZ.fine.intValue()>i){
		    				if(itemZ.altezza>tmp){
		    					tmp=itemZ.altezza;//assegno alla variabile temporanea l'altezza più alta in quel punto 
		    				}
		    		}
		    }
		    	altezza+=tmp;
		    	}
		    ListaparzialeS.clear();//pulisco la lista per utilizzarla per un altro comando

		 }
		 
			 return altezza;//ritorno l'altezza

}

	}
		
	



