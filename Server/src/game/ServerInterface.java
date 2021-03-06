package game;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;

/**
 * Interfaccia che mette a disposizione i metodi del server ai client
 * @author Andrea Peluso
 * @author Luca Cremonesi
 * @version 1.0
 */
public interface ServerInterface extends Remote 
{
	/**
	 * metodo usato per aggiungere un client quando deve ricever le notifiche dal server
	 * @param o indica il client da aggiungere
	 * @throws RemoteException per la gestione delle eccezioni
	 */
	public void addObs(RemoteObserver o) throws RemoteException;

	/**
	 * metodo usato per rimuovere un client quando non deve pi? ricevere notifiche dal server
	 * @param o indica il client da rimuovere
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public void removeObs(RemoteObserver o) throws RemoteException;

	/**
	 * metodo usato per aggiungere un nuovo utente
	 * @param email indica l'email dell'utente
	 * @param name indica il nome dell'utente
	 * @param surname indica il cognome dell'utente
	 * @param nickname indica il nickname dell'utente
	 * @param isAdmin indica se l'utente ? o no un amministatore
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public boolean addUser(String email,String name, String surname, String nickname,boolean isAdmin) throws RemoteException;


	/**
	 * metodo usato per effettuare il login da parte di un utente non amministratore
	 * @param email indica l'email di chi effettua il login 
	 * @param passw indica la password di chi effettua il login 
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public boolean login(String email,String passw) throws RemoteException;

	/**
	 * metodo usato per verificare se il codice di registrazione corrisponde a quello inviato per email e non ? scaduto
	 * @param email indica l'email dell'utente
	 * @param code rappresenta il codice inviato per email
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public boolean checkCode(String email,String code) throws RemoteException;

	/**
	 * metodo usato per aggiornare la password 
	 * @param email indica l'email associata alla password
	 * @param passw indica la password nuova
	 * @param code indica la password vecchia
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public boolean updateCode(String email,String passw,String code) throws RemoteException;

	/**
	 * metodo usato per aggiornare lo stato di un utente (online/offline)
	 * @param email indica l'email dell'utente
	 * @param online indica lo stato del giocatore
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public boolean setOnline(String email,boolean online) throws RemoteException;

	/**
	 * metodo usato per aggiornare il profilo di un utente
	 * @param email indica l'email dell'utente
	 * @param name indica il nome dell'utente
	 * @param surname indica il cognome dell'utente
	 * @param nickname indica il nickname dell'utente
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public void updateProfile(String email,String name,String surname,String nickname) throws RemoteException;

	/**
	 * metodo usato per ottenere i dati di un utente
	 * @param email indica l'email dell'utente
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public String[] getData(String email) throws RemoteException;  

	/**
	 * metodo usato per creare una nuova partita
	 * @param email indica l'email di chi ha creato la partita
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public long createMatch(String email) throws RemoteException;  


	/**
	 * metodo usato per ottenere la lista delle partite disponibili
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public LinkedHashMap<Long, String> matchesList() throws RemoteException;

	/**
	 * metodo usato per resettare la password in caso di password dimenticata
	 * @param email indica l'utente che ha chiesto il reset
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public boolean resetPassword(String email) throws RemoteException;  

	/**
	 * metodo usato per ottenere il numero di player della partita
	 * @param idMath indica l'identificativo della partita
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int nPlayers(long idMatch) throws RemoteException;

	/**
	 * metodo usato per ottenere tutte le frasi
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public ArrayList<String> getPhrase(long id) throws RemoteException;

	/**
	 * metodo usato per ottenere il tema della frase
	 * @param id indica l'identificativo della partita
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public String getTheme(long id) throws RemoteException;

	/**
	 * metodo usato per indicare la fine della partita
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public void endMatch(long idMatch) throws RemoteException;

	/**
	 * metodo usato per notificare che un utente ha effettuato una mossa
	 * @param nManche indica il numero della manche
	 * @param email indica l'email di chi ha effettuato la mossa
	 * @param score indica il punteggio della mossa
	 * @param move indica il tipo di mossa
	 * @param idMatch indica l'identificativo della partita
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public void updateMove(int nmanche,String email, int score, String move,long idMatch) throws RemoteException;

	/**
	 * metodo usato per aggiungere una partecipazione ad una partita
	 * @param email indica l'email di chi ha partecipato
	 * @param idMatch indica l'identificativo della partita
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public boolean joinMatch(String email,long idMatch) throws RemoteException;

	/**
	 * metodo usato per ottenere il creatore di una partita
	 * @param idMatch indica l'identificativo della partita
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public String getCreator(long idMatch) throws RemoteException;

	/**
	 * metodo usato per notificare il cambio di manche
	 * @param email indica l'email di chi ha vinto la manche
	 * @param idMatch indica l'identificativo della partita
	 * @param score indica il punteggio del vincitore della manche
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public void updateManche(String email,long idManche,int score) throws RemoteException;

	/**
	 * metodo usato per notifica il cambio di turno
	 * @param nTurn indica il numero del turno 
	 * @param idGame indica l'identificativo della partita
	 * @param email indica l'email di chi ha il turno
	 * @param nmanche indica il numero della manche 
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public void c_turn(int nTurn,long idMatch,String email,int nmanche) throws RemoteException;

	/**
	 * metodo usato per ottenere il giocatore che ha il diritto di fare la prima mossa nella partita
	 * @param idMatch indica l'identificativo della partita
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public String getFirstTurn(long idMatch) throws RemoteException;

	/**
	 * metodo usato per ottenere il giocatore che ha il turno nella partita
	 * @param idMatch indica l'identificativo della partita
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int getNTurn(long idMatch) throws RemoteException;

	/**
	 * metodo usato per notificare la vittoria dellla partita
	 * @param email indica l'email del giocatore
	 * @param idMatch indica l'identificativo della partita
	 * @param score il punteggio del vincitore dell'ultima manche
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public String victory(String email,long idMatch,int score) throws RemoteException;

	/**
	 * metodo usato per notificare che un giocatore ? uscito dalla lobby
	 * @param email indica l'email di chi ? uscito dalla lobby
	 * @param idMatch indica l'identificativo della partita
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int exitLobby(String email,long idMatch) throws RemoteException;

	/**
	 * metodo usato per notificare che un osservatore ? uscito dalla partita
	 * @param email indica l'email di chi ? uscito dalla lobby
	 * @param idMatch indica l'identificativo della partita
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int exitObserver(String email,long idMatch) throws RemoteException;

	/**
	 * metodo usato per notificare che un osservatore ? entrato nella lobby
	 * @param email indica l'email di chi ? uscito dalla lobby
	 * @param idMatch indica l'identificativo della partita
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public boolean joinObserver(String email,long idMatch) throws RemoteException;

	/**
	 * metodo usato per effettuare il login da parte di un utente amministratore
	 * @param email indica l'email di chi effettua il login 
	 * @param passw indica la password di chi effettua il login 
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public boolean loginAdmin(String email,String passw) throws RemoteException;

	/**
	 * metodo usato per ottenere tutte le frasi
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public Hashtable<Integer,Phrases> getPhrases() throws RemoteException;

	/**
	 * metodo usato per ottenere il giocatore che detiene il primato di punteggio raggiunto per manche
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public QueryReturn statistics1() throws RemoteException;

	/**
	 * metodo usato per ottenere il giocatore che detiene il primato di punteggio raggiunto per partita
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public QueryReturn statistics2() throws RemoteException;

	/**
	 * metodo usato per ottenere il giocatore che ha giocato pi? manche in assoluto
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public QueryReturn statistics3() throws RemoteException;

	/**
	 * metodo usato per ottenere il giocatore con la media pi? alta di punti acquisiti per manche
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public QueryReturn statistics4() throws RemoteException;

	/**
	 * metodo usato per ottenere il giocatore che ha dovuto cedere pi? volte il turno di gioco in seguito ad errori
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public QueryReturn statistics5() throws RemoteException;

	/**
	 * metodo usato per ottenere il giocatore che ha perso tutto in seguito ad un giro di ruota per il maggior numero di volte
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public QueryReturn statistics6() throws RemoteException;

	/**
	 * metodo usato per ottenere il numero medio mosse per manche con le quali viene indovinata una frase
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public float statistics7() throws RemoteException;

	/**
	 * metodo usato per mostrare la chiamata di consonante, riferita ad una manche gi? giocata, 
	 * che ha portato all?acquisizione della maggior quantit? punti, la frase misteriosa associata, 
	 * e l?utente che ha fatto la mossa.
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public QueryReturn statistics8() throws RemoteException;


	/**
	 * metodo usato per mostrare il numero di manche giocate
	 * @param email indica l'email dell'utente
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int statistics9(String email) throws RemoteException;


	/**
	 * metodo usato per mostrare il numero di partite giocate
	 * @param email indica l'email dell'utente
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int statistics10(String email) throws RemoteException;

	/**
	 * metodo usato per mostrare il numero di manche osservate
	 * @param email indica l'email dell'utente
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int statistics11(String email) throws RemoteException;

	/**
	 * metodo usato per mostrare il numero di partite osservate
	 * @param email indica l'email dell'utente
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int statistics12(String email) throws RemoteException;

	/**
	 * metodo usato per mostrare il numero di manche vinte
	 * @param email indica l'email dell'utente
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int statistics13(String email) throws RemoteException;


	/**
	 * metodo usato per mostrare il numero di partite vinte
	 * @param email indica l'email dell'utente
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int statistics14(String email) throws RemoteException;

	/**
	 * metodo usato per mostrare il numero di manche giocate
	 * @param email indica l'email dell'utente
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int statistics15(String email) throws RemoteException;

	/**
	 * metodo usato per mostrare il numero medio di passaggio turno per manche
	 * @param email indica l'email dell'utente
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int statistics16(String email) throws RemoteException;

	/**
	 * metodo usato per mostrare il numero medio di volte, per manche e partita, che ha dovuto cedere il turno di gioco
	 * @param email indica l'email dell'utente
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int statistics17(String email) throws RemoteException;

	/**
	 * metodo usato per mostrare il numero medio di volte, per manche che perso tutto
	 * @param email indica l'email dell'utente
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int statistics18(String email) throws RemoteException;

	/**
	 * metodo usato per mostrare il numero medio di volte, per partita che perso tutto
	 * @param email indica l'email dell'utente
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int statistics19(String email) throws RemoteException;

	/**
	 * metodo usato per ottenere l'id degli altri giocatori della partita
	 * @param idMatch indica l'identificativo della partita
	 * @param nick indica l'email del giocatore
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public ArrayList<String> otherPlayerList(long idmatch,String nickname) throws RemoteException;

	/**
	 * metodo usato dagli osservatori per ottenre la lista dei giocatori della partita
	 * @param idMatch indica l'identificativo della partita
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public ArrayList<String> allPlayerList(long idMatch) throws RemoteException;

	/**
	 * metodo usato per ottenere il punteggio dei giocatori della partita
	 * @param idMatch indica l'identificativo della partita
	 * @param nickname indica l'email del giocatore
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int getScore(long idMatch,String nickname) throws RemoteException;

	/**
	 * metodo usato per ottenere il deposito della partita
	 * @param idMatch indica l'identificativo della partita
	 * @param nickname indica l'email del giocatore
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int getBank(long idMatch,String nickname) throws RemoteException;

	/**
	 * metodo usato per ottenere il numero della manche della partita
	 * @param idMatch indica l'identificativo della partita
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int getNmanche(long idMatch) throws RemoteException;

	/**
	 * metodo usato per ottenere il numero dei jolly del giocatore nella partita
	 * @param idMatch indica l'identificativo della partita
	 * @param nickname indica l'email del giocatore
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int getJolly(long idMatch,String nickname) throws RemoteException;

	/**
	 * metodo usato per ottenere le lettere della frase nel tabellone svelate nella partita
	 * @param idMatch indica l'identificativo della partita
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public ArrayList<String> getLetters(long idMatch) throws RemoteException;

	/**
	 * metodo usato per notificare che un osservatore ? uscito dalla lobby
	 * @param email indica l'email di chi ? uscito dalla lobby
	 * @param idMatch indica l'identificativo della partita
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int exitLobbyObs(String email, long idMatch) throws RemoteException;

	/**
	 * metodo usato per notificare che la ruota ha finito di girare
	 * @param idMatch indica l'identificativo della partita
	 * @param email indica la email del giocatore a cui si ? fermata la ruota
	 * @param score indica il punteggio ottenuto dal giro di ruota
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public void notifyWheelStop(long idMatch,String email,String score) throws RemoteException;

	/**
	 * metodo usato per ottenere la classifica della partita 
	 * @param idMatch indica l'identificativo della partita
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public LinkedHashMap<String, Integer>  ranking(long idMatch) throws RemoteException;

	/**
	 * metodo usato per aggiungere delle frasi
	 * @param phrases indica le frasi da aggiungere
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int addPhrases(Object[][] phrases) throws RemoteException;

	/**
	 * metodo usato per rimuovere una frase
	 * @param id indica l'id della frase da rimuovere
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int removePhrase(int id) throws RemoteException;

	/**
	 * metodo usato per modificare una frase
	 * @param id indica l'id della frase da modificare
	 * @param tema indica il tema della frase da modificare
	 * @param corpo indica il corpo della frase da modificare
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int updatePhrase(int id,String tema,String corpo) throws RemoteException;


	/**
	 * metodo usato per inserire una frase
	 * @param id indica l'identificativo della frase
	 * @param tema indica il tema della frase da modificare
	 * @param corpo indica il corpo della frase da modificare
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public int insertPhrase(int id,String tema,String corpo) throws RemoteException;

	/**
	 * metodo usato per controllare se la password passata per l'aggiornamento della steasa corrisponde a quella nel database
	 * @param email indica 
	 * @param passw indica la password
	 * @throws RemoteException per la gestione delle eccezioni
	 */

	public boolean isPasswordCorrect(String email, String passw) throws RemoteException;


}
