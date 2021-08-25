package com.company.dao;

import java.sql.*;
import java.sql.Statement;

public class DBadapter {
    //variabili necessarie
    String jdbUrl = "jdbc:postgresql://localhost:5432/Ruota_Della_Fortuna";
    String username = "postgres";
    String password = "postgre";

    // variabili per il database
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    //costruttore
    public DBadapter(){

    }
    //connessione al database
    public void connect (){
        try {
            // connettiti al database suppongo
            conn = DriverManager.getConnection(jdbUrl , username, password);
            // printa se va tutto bene
            System.out.println("Database Connesso");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //disconnessione dal database
    public void disconnect(){
        try {
            if(stmt != null){
                stmt.close();
            }
            if(rs != null){
                rs.close();
            }
            if (conn != null){
                conn.close();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
