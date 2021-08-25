package com.company;

import com.company.dao.ImplementazioneDB;

public class Main {

    public static void main(String[] args) {
        System.out.println("Ruota della Fortuna");
	    // connettiamoci dai
        ImplementazioneDB RdFDB = new ImplementazioneDB();
        RdFDB.connect();

        // crea tabelle
        RdFDB.createTables();

        // Inserisci utenti
        RdFDB.inserisciUtente("Dezso", 0001);
        RdFDB.inserisciUtente("Luca", 0002);
        RdFDB.inserisciUtente("Davide", 0003);

        // metodo che updata le cose
        RdFDB.aggiornaNome("Dezso", "Dezso");
        RdFDB.aggiornaNome("Luca", "Luca");

        // cancella utente
        RdFDB.cancellaUtente("Dezso");
        RdFDB.cancellaUtente("Luca");
        RdFDB.cancellaUtente("Davide");

        RdFDB.disconnect();
    }
}
