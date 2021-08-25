//Andrea Peluso N.M.: 732530 Gabriele De Fino N.M.: 733427

package soluzione;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/** Classe principale */
public class Skyline {
	/**stringa contenente nome del file eventset.txt*/
	String f1;
	/**stringa contenente nome del file cmd.txt*/
	String f2;
	/**
	 * metodo main 
	 * @param args parametri da linea di comando
	 */
public static void main(String[] args) {
	if(args.length==2){//se sono stati inseriti i nomi dei file da linea di comando
	/**lista degli eventi*/
	List<Evento> Eventi = new ArrayList<Evento>();//crea lista di eventi
	/**lista degli comandi*/
	List<Comando> Comandi = new ArrayList<Comando>();//crea lista di comandi
	/**stringa del primo file passato da linea di comando*/
	String f1=args[0];//inizializzo variabili con argomento passato da terminale(eventset.txt)
	/**stringa del secondo file passato da linea di comando*/
	String f2=args[1];//inizializzo variabile con argomento passato da terminale(cmd.txt)
	Eventi Ev=new Eventi(Eventi);//istanzio oggetto di tipo Eventi passandogli la lista Eventi
   	Ev.importa(f1);//riempie la lista di eventi passando il nome del file
	Comandi Cmd=new Comandi(Comandi);//istanzio oggetto di tipo Comandi passandogli la lista Comandi
	Cmd.importa(f2);//riempie la lista di comandi passando il nome del file
	Eventi.sort(Comparator.comparing(Evento::getYear));//Ordina liste di eventi in base all'anno se disordinati
	/**creazione lista demolizione*/
	List<Demolish> demolizione = new ArrayList<Demolish>();//crea lista di demolish
	/**creazione lista palazzi*/
	List<HeightList> Listaparziale = new ArrayList<HeightList>();//crea lista di palazzi
	Operazioni Op=new Operazioni(Eventi,demolizione,Listaparziale);//istanzio oggetto di tipo operazioni e passo le liste: Eventi,demolizione e Listaparziale
	for (Evento itemE: Eventi) {//itero gli eventi
		if(itemE instanceof Demolish){//se è di tipo demolish
			demolizione.add(new Demolish(itemE.EType,itemE.EYear,itemE.Ep));//creo lista eventi demolizione
		}}
	     for (Comando itemC: Comandi) {//calcola size
	    	 if(itemC.CmdType.contains("s")){//se è di tipo size
	    		 	Op.riempiHashMap();
	    			int size=Op.size(itemC.CmdYear);//passo anno alla funzione size della classe operazioni
	    		 	Op.svuotaHashMap();
	    		    System.out.println(itemC.CmdType+"  "+itemC.CmdYear+" : "+size);//stampo ampiezza
	}else if(itemC.CmdType.contains("h")){//se è di tipo height
		float res=0;
	 	Op.riempiHashMap();
		float Height=Op.height(itemC.CmdYear);//calcolo altezza 
		if(Height==0)//se l'altezza è zero 
			res=0;//il risultato sarà zero e non effettuo la divisione che causerebbe un errore in esecuzione
		else
	    res=(Height)/Op.size(itemC.CmdYear);//divido per ampiezza skyline
	 	Op.svuotaHashMap();
		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.FLOOR);//considero solo i primi 3 decimali
		System.out.println(itemC.CmdType+"  "+itemC.CmdYear+" : "+ df.format(res));//stampo altezza
	}}}}}

