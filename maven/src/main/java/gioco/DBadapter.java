package gioco;


import java.sql.*;

public class DBadapter {
	
	
	public static String jdbUrl = null;
	public static String username = null;
	public static String password = null;

	// variabili per il database
	Connection conn = null;
	

	//costruttore
	public DBadapter(){

	}
	//connessione al database
	public boolean connect (){
		try {
			conn = DriverManager.getConnection(jdbUrl , username, password);
			return true;
		} catch (SQLException e) {
			return false;
			
		}
	}
	//disconnessione dal database
	public void disconnect(){
		try {
			if (conn != null){
				conn.close();
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}

