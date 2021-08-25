package com.company.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ImplementazioneDB extends DBadapter {
    //metodo per creare tabelle
    public void createTables(){
        try {
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS utenti (" +
                    "utente_id SERIAL PRIMARY KEY NOT NULL, " +
                    "utente_nome VARCHAR(100) NOT NULL) ";
            stmt.executeUpdate(sql);
            stmt.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //metodo per inserire utenti
    public void inserisciUtente(String utente_nome, int utente_id){
        System.out.println("DBRdF: inserisciUtente " + utente_nome + " ID: " + utente_id);
        try {
            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO utenti " +
                            "(utente_nome, utente_id) " +
                            "VALUES(?,?)");

            st.setString(1, utente_nome);
            st.setDouble(2, utente_id);
            st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
}


    //metodo che serve per modificare il nome utente
    public void aggiornaNome(String Nome, String nuovoNome){
        System.out.println("RdFDB: aggiornaNome " + Nome +
                " - nuovo nome :" + nuovoNome);
        try {
            PreparedStatement st = conn.prepareStatement(
                    "UPDATE utenti SET utente_nome=?" +
                            "WHERE utente_nome=?");

            st.setString(1, Nome);
            st.setString(2, nuovoNome);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //metodo per cancellare dati
    public void cancellaUtente (String Nome){
        System.out.println("RdFDB: cancellaUtente " + Nome);
        try {
            PreparedStatement st = conn.prepareStatement(
                    "DELETE FROM utenti " +
                            "WHERE utente_nome=?");

            st.setString(1, Nome);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
