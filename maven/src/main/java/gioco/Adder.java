package gioco;

import gioco.DBimplementation;

import java.util.ArrayList;
import java.rmi.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.io.*;

import javax.mail.MessagingException;

public class Adder extends Observable implements ServerInterface,Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<WrappedObserver> WrappedObserver;


	Adder() throws RemoteException{
		WrappedObserver= new ArrayList<WrappedObserver>();
	}


	public void removeObs(RemoteObserver observer) {
		ArrayList<WrappedObserver> observertoremove = new ArrayList<WrappedObserver>();

		for(WrappedObserver w:WrappedObserver){
			if(w.getObs().equals(observer)){
				try{
					observertoremove.add(w);
					deleteObserver(w);	
				}catch(Exception e){
					e.printStackTrace();
				}
				System.out.println("eliminato");
			}
		}

		for(WrappedObserver wtoremove:observertoremove){
			WrappedObserver.remove(wtoremove);
		}
	}


	public void addObs(RemoteObserver o) {
		System.out.println(o);
		WrappedObserver mo=new WrappedObserver(o);
		WrappedObserver.add(mo);//aggiunge nuovo osservatore alla lista degli osservatori 
		System.out.println("Aggiunto");
		try{
			addObserver(mo);
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	public void notify(long idMatch,String email){
		ArrayList<String> result=new ArrayList<String>();
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){//connette al database
			try{
				result=RdFDB.player_list(idMatch,email);//ottiene la lista dei giocatori da notificare
			}catch(Exception e){}
		}else{
			System.out.println("problem of connection!");
		}
		setChanged();
		try{
			notifyObservers(result);//notifica tutti gli osservatori mandandogli la lista contenente le e-mail degli osservatori interessati alla notifica
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void notifyExitLobby(long idMatch,String email){
		ArrayList<String> result=new ArrayList<String>();
		ArrayList<String> players=new ArrayList<String>();
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){//connette al database
			try{
				players=RdFDB.otherPlayer_list(idMatch,email);//ottiene la lista dei giocatori nella lobby
			}catch(Exception e){}
		}

		if(RdFDB.connect()){//connette al database
			try{
				result=RdFDB.player_list(idMatch,email);//ottiene la lista dei giocatori da notificare
			}catch(Exception e){}
			if(!players.isEmpty()){
				result.add(0,"exitlobby");
				setChanged();
				try{
					notifyObservers(result);//notifica tutti gli osservatori mandandogli la lista contenente le e-mail degli osservatori interessati alla notifica
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				if(RdFDB.connect()){
					try{
						RdFDB.remove_match(idMatch);
					}catch(Exception e){
						e.printStackTrace();
					}
					if(!result.isEmpty()){
						result.add(0,"exitlobby");
						setChanged();
						try{
							notifyObservers(result);//notifica tutti gli osservatori mandandogli la lista contenente le e-mail degli osservatori interessati alla notifica
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			}
		}else{
			System.out.println("problem of connection!");
		}
	}

	public ArrayList<String> otherPlayerList(long idMatch,String nick){
		ArrayList<String> result=new ArrayList<String>();
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){//connette al database
			try{
				result=RdFDB.otherPlayer_list(idMatch,nick);//ottiene la lista degli altri 2 giocatori della partita 
			}catch(Exception e){}
		}else{
			System.out.println("problem of connection!");
		}
		return result;

	}

	public ArrayList<String> allPlayerList(long idMatch){
		ArrayList<String> result=new ArrayList<String>();
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){//connette al database
			try{
				result=RdFDB.allPlayer_list(idMatch);//ottiene la lista di tutti i giocatori della partita
			}catch(Exception e){}
		}else{
			System.out.println("problem of connection!");
		}
		return result;

	}


	public int getScore(long idMatch,String nickname){
		int score=0;
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){//connette al database
			try{
				score=RdFDB.get_Score(idMatch,nickname);//ottiene i punteggi dei giocatori(usato dagli osservatori)
				System.out.println(score);
			}catch(Exception e){}
		}else{
			System.out.println("problem of connection!");
		}
		return score;

	}

	public int getBank(long idMatch,String nickname){
		int bank=0;
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){//connette al database
			try{
				bank=RdFDB.get_Bank(idMatch,nickname);//ottiene i punteggi dei giocatori(usato dagli osservatori)
			}catch(Exception e){}
		}else{
			System.out.println("problem of connection!");
		}
		return bank;

	}

	public int getNmanche(long idMatch){
		int nmanche=0;
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){//connette al database
			try{
				nmanche=RdFDB.get_Nmanche(idMatch);//ottiene il numero della manche per gli osservatori
			}catch(Exception e){}
		}else{
			System.out.println("problem of connection!");
		}
		return nmanche;

	}

	public String getFirstTurn(long idMatch){
		String turn=null;
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){//connette al database
			try{
				turn=RdFDB.get_turn(idMatch);//ottiene il numero della manche per gli osservatori
			}catch(Exception e){}
		}else{
			System.out.println("problem of connection!");
		}
		return turn;

	}


	public int getJolly(long idMatch,String nickname){
		int jolly=0;
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){//connette al database
			try{
				jolly=RdFDB.get_jolly(idMatch,nickname);//ottiene il numero dei jolly dei giocatori(usato dagli osservatori)
			}catch(Exception e){}
		}else{
			System.out.println("problem of connection!");
		}
		return jolly;

	}


	public ArrayList<String> getLetters(long idMatch){
		ArrayList<String> letters=new ArrayList<String>();
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){//connette al database
			try{
				letters=RdFDB.get_Letters(idMatch);//ottiene il numero della manche per gli osservatori
			}catch(Exception e){}
		}else{
			System.out.println("problem of connection!");
		}
		return letters;

	}


	public void notify_nophrases(ArrayList<String> players){
		ArrayList<String> result=new ArrayList<String>();
		result=players;
		result.add(0,"frasi");
		setChanged();
		try{
			notifyObservers(result);//notifica tutti gli osservatori mandandogli la lista contenente le e-mail degli osservatori interessati alla notifica
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void notifyManche(long idMatch,String email,int score){
		ArrayList<String> result=new ArrayList<String>();
		DBimplementation RdFDB = new DBimplementation();
		result.add("manche");

		String winner = email;

		result.add(winner);
		result.add(String.valueOf(score));

		if(RdFDB.connect()){//connette al database
			try{
				for(String s:RdFDB.player_list(idMatch,email)){
					result.add(s);
				}
			}catch(Exception e){}
		}else{
			System.out.println("problem of connection!");
		}

		setChanged();
		try{
			notifyObservers(result);//notifica tutti gli osservatori mandandogli la lista contenente le e-mail degli osservatori interessati alla notifica
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	public String victory(String email,long idMatch,int score){
		ArrayList<String> result=new ArrayList<String>();
		DBimplementation RdFDB = new DBimplementation();
		String winner = null;

		if(RdFDB.connect()){//connette al database
			try{
				RdFDB.update_last_manche(idMatch,email,score);//aggiorna il numero della manche
			}catch(Exception e){}
		}else{
			System.out.println("problem of connection!");
		}

		if(RdFDB.connect()){//connette al database
			try{
				RdFDB.end_match(idMatch);//imposto data fine partita
			}catch(Exception e){}
		}else{
			System.out.println("problem of connection!");
		}

		result.add("victory");


		if(RdFDB.connect()){//connette al database
			try{
				winner=RdFDB.get_winner(idMatch);//ottiene il vincitore
			}catch(Exception e){}

		}else{
			System.out.println("problem of connection!");
		}

		result.add(winner);

		if(RdFDB.connect()){//connette al database
			try{
				for(String s:RdFDB.player_list(idMatch,email)){
					result.add(s);
				}
			}catch(Exception e){}
		}else{
			System.out.println("problem of connection!");
		}
		setChanged();
		try{
			notifyObservers(result);//notifica tutti gli osservatori mandandogli la lista contenente le e-mail degli osservatori interessati alla notifica
		}catch(Exception e){
			e.printStackTrace();
		}

		return winner;
	}

	public void notifyCTurn(Hashtable<Integer,String> data){
		setChanged();
		try{
			notifyObservers(data);//notifica gli osservatori assegnando il turno a chi deve girare la ruota
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void notifyMove(Move move){
		setChanged();
		try{
			notifyObservers(move);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public boolean addUser(String email,String name,String surname, String nickname,boolean isAdmin){
		boolean returned=false;
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){//prova a connettersi al database
			try {
				returned=RdFDB.insert_newuser(email,name,surname,nickname,isAdmin);//inserisce un nuovo utenten nel database
			} catch (UnsupportedEncodingException
					| NoSuchAlgorithmException | MessagingException e) {
				e.printStackTrace();
			}}else{
				System.out.println("problem of connection!");
			}

		return returned;

	}


	public boolean login(String email,String passw){
		boolean returned=false;
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				returned=RdFDB.user_login(email,passw);//prova ad effettuare il login
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			System.out.println("problem of connection!");
		}
		return returned;

	}


	public boolean loginAdmin(String email,String passw){
		boolean returned=false;
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				returned=RdFDB.admin_login(email,passw);//prova ad effettuare il login
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			System.out.println("problem of connection!");
		}
		return returned;

	}


	public boolean checkCode(String email,String code){
		boolean returned=false;
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				returned=RdFDB.verify_code(email,code);//verifica la correttezza del codice di verifica inserito
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			System.out.println("problem of connection!");
		}
		return returned;
	}


	public boolean updateCode(String email,String passw,String code){
		boolean returned=false;
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				returned=RdFDB.code_update(email,passw,code);//dopo aver verificato il codice aggiorna la password
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			System.out.println("problem of connection!");
		}
		return returned;

	}



	public void updateProfile(String email,String name,String surname,String nickname){
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				RdFDB.profile_update(email,name,surname,nickname);//aggiorna i dati del profilo utente
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			System.out.println("problem of connection!");
		}

	}

	public String[] getData(String email){
		String[] data=new String[3];
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				data= RdFDB.get_data(email);//ottiene i dati del profilo utente
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			System.out.println("problem of connection!");
		}
		return data;
	}


	public ArrayList<String> getPhrase(long id){
		ArrayList<String> data=new ArrayList<String>();
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				data= RdFDB.get_phrase(id);//ottiene la frase della partita in corso
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			System.out.println("problem of connection!");
		}
		return data;
	}
	
	public void notifyWheelStop(long idMatch,String email,String score){
		ArrayList<String> result=new ArrayList<String>();
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){//connette al database
			try{
				result=RdFDB.player_list(idMatch,email);//ottiene la lista dei giocatori da notificare
			}catch(Exception e){}
			result.add(0,email);
			result.add(0,score);
			result.add(0,"SR");
			setChanged();
			try{
				notifyObservers(result);//notifica tutti gli osservatori mandandogli la lista contenente le e-mail degli osservatori interessati alla notifica
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			System.out.println("problem of connection!");
		}
	}

	public String getTheme(long id){
		String data = null;
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				data= RdFDB.get_theme(id);//ottiene il tema della partita in corso
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			System.out.println("problem of connection!");
		}
		return data;
	}


	public long createMatch(String email){
		long returned=0;
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				returned=RdFDB.create_match(email);//inserisce nella tabella 'partita' del database i dati relativi alla partita creata
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			System.out.println("problem of connection!");
		}
		return returned;
	}

	public Hashtable<Long,String>  matchesList(){
		Hashtable<Long,String> data = new Hashtable<Long,String>();
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				data= RdFDB.matches_list();//ottiene la lista delle partite dal database
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			System.out.println("problem of connection!");
		}
		return data;
	}

	public boolean resetPassword(String email){
		boolean returned=false;
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try {
				returned=RdFDB.reset_Password(email);//in caso di password dimenticata invia e-mail con nuova password e la aggiorna nel database
			} catch (UnsupportedEncodingException | NoSuchAlgorithmException
					| MessagingException e) {
				e.printStackTrace();
			}}else
			{
				System.out.println("problem of connection!");
			}

		return returned;

	}


	public int nPlayers(long idMatch){
		int data=0;
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				data=RdFDB.n_player(idMatch);//ottiene il numero di giocatori di una specifica partita
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			System.out.println("problem of connection!");
		}
		return data;
	}

	public void updateMove(int nManche,String email, int score, String move,long idMatch){
		DBimplementation RdFDB = new DBimplementation();
		ArrayList<String> result=new ArrayList<String>();
		String m=null;
		if(RdFDB.connect()){
			try{
				if(move.contains("all")){
					m=move.substring(0,1);
				}else{
					m=move;
				}
				RdFDB.update_move(nManche,email,score,m,idMatch);//aggiunge la mossa effettutata nella tabella 'mossa' del database	
			}catch(Exception e){
				e.printStackTrace();
			}
			if(RdFDB.connect()){
				try{
					result=RdFDB.player_list(idMatch,email);
				}catch(Exception e){
					e.printStackTrace();
				}
			}

			Move newmove=new Move(move,email,score,result);
			notifyMove(newmove);

		}else{
			System.out.println("problem of connection!");
		}
	}

	public boolean joinMatch(String email,long idMatch){
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				RdFDB.join_match(email,idMatch);//aggiunge una partecipazione nella tabella 'partecipazione' alla specifica partita da parte di un player
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			System.out.println("problem of connection!");
		}

		if(nPlayers(idMatch)==3){
			ArrayList<String> players=new ArrayList<String>();
			if(RdFDB.connect()){
				players=RdFDB.player_list(idMatch, email);
			}

			if(RdFDB.connect()){
				if(RdFDB.checkPhrases(idMatch)){
					if(RdFDB.connect()){
						RdFDB.start_match(idMatch);
					}
					notify(idMatch,email);
					return true;
				}else{
					notify_nophrases(players);
					return false;
				}
			}
			//devo verificare se esistono frasi non ancora utilizzate dai 3 giocatori in tutte le partite
			//se non esistono mando e-mail ad amministratore e notifico dell'annullamento della partita
			//elimino la partita
			//altrimenti inserisco le frasi e inizio la partita
		}	
		else{
			notify(idMatch,email);//notifica gli osservatori interessati a questo aggiornamento,ovvero quelli nella lobby
			return true;
		}

		return false;
	}




	public void endMatch(long idMatch){
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				RdFDB.end_match(idMatch);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			System.out.println("problem of connection!");
		}
	}


	public String getCreator(long idMatch){
		String data = null;
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				data= RdFDB.get_creator(idMatch);//ottiene l'email del creatore della partita
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			System.out.println("problem of connection!");
		}
		return data;
	}

	public void updateManche(String email,long idMatch,int score){
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				RdFDB.update_manche(idMatch,email,score);//aggiorna il numero della manche
			}catch(Exception e){
				e.printStackTrace();
			}
			if(RdFDB.connect()){
				notifyManche(idMatch,email,score);		
			}
		}else{	
			System.out.println("problem of connection!");
		}
	}


	public void turn(long idMatch){
		Hashtable<Integer,String> data = new Hashtable<Integer,String>();
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				data= RdFDB.s_Turn(idMatch);//a inizio partita assegna il turno in modo casuale
			}catch(Exception e){
				e.printStackTrace();
			}
			System.out.println(data);
			notifyCTurn(data);//notifica chi deve iniziare a giocare per primo
		}else{
			System.out.println("problem of connection!");
		}

	}


	public void c_turn(int nTurn, long idGame,String email,int nmanche){
		Hashtable<Integer,String> data = new Hashtable<Integer,String>();
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				data= RdFDB.c_Turn(nTurn,idGame,email,nmanche);//cambia il turno decidendo chi è il prossimo a giocare
			}catch(Exception e){
				e.printStackTrace();
			}
			notifyCTurn(data);//notifica il cambiamento
		}else{
			System.out.println("problem of connection!");
		}

	}

	public int exitLobbyObs(String email, long idMatch) {
		int update=0;
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				update=RdFDB.exit_lobby(idMatch,email);//
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			System.out.println("problem of connection!");
		}
		return update;
	}

	public int exitLobby(String email,long idMatch){
		int update=0;
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				update=RdFDB.exit_lobby(idMatch,email);//
			}catch(Exception e){
				e.printStackTrace();
			}
			notifyExitLobby(idMatch,email);
		}else{
			System.out.println("problem of connection!");
		}

		return update;
	}

	public int exitObserver(String email,long idMatch){
		int update=0;
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				update=RdFDB.exit_lobbyObserver(idMatch,email);//
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			System.out.println("problem of connection!");
		}
		return update;

	}

	public boolean joinObserver(String email,long idMatch){
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				RdFDB.join_observer(email,idMatch);//aggiunge una partecipazione nella tabella 'partecipazione' alla specifica partita da parte di un osservatore
			}catch(Exception e){
				e.printStackTrace();
			}
			return true;
		}else{
			System.out.println("problem of connection!");
			return false;
		}
	}


	public QueryReturn statistics1(){

		QueryReturn result=null;	
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){
			try{
				//1ottieni il giocatore con il punteggio manche più alto
				result=RdFDB.statistics1();
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}else{
			System.out.println("problem of connection!");
			return null;
		}

	}

	public QueryReturn statistics2(){

		QueryReturn result=null;	
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){
			try{
				//2giocatore con punteggio partita più alto			
				result=RdFDB.statistics2();
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}else{
			System.out.println("problem of connection!");
			return null;
		}

	}


	public QueryReturn statistics3(){

		QueryReturn result=null;	
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){
			try{
				//3che ha giocato più manche in assoluto
				result=RdFDB.statistics3();
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}else{
			System.out.println("problem of connection!");
			return null;
		}

	}


	public QueryReturn statistics4(){

		QueryReturn result=null;	
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){
			try{
				//4media punteggio più alta per manche 
				result=RdFDB.statistics4();
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}else{
			System.out.println("problem of connection!");
			return null;
		}

	}


	public QueryReturn statistics5(){

		QueryReturn result=null;	
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){
			try{
				//5che ha dovuto cedere più volte il turno di gioco in seguito ad errori
				result=RdFDB.statistics5();
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}else{
			System.out.println("problem of connection!");
			return null;
		}

	}


	public QueryReturn statistics6(){

		QueryReturn result=null;	
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){
			try{
				//6che ha perso tutto in seguito ad un giro di ruota per il maggior numero di volte
				result=RdFDB.statistics6();
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}else{
			System.out.println("problem of connection!");
			return null;
		}

	}


	public float statistics7(){

		float result=0;	
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){
			try{
				//7numero medio di mosse con le quali viene indovinata una frase per manche
				result=RdFDB.statistics7();
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}else{
			System.out.println("problem of connection!");
			return 0;
		}

	}



	public QueryReturn statistics8(){

		QueryReturn result=null;	
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){
			try{
				//8Mostrare la chiamata di consonante, riferita ad una manche già giocata, che ha portato all’acquisizione della maggior 
				//quantità punti, la frase misteriosa associata, e l’utente che ha fatto la mossa.

				result=RdFDB.statistics8();
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}else{
			System.out.println("problem of connection!");
			return null;
		}

	}



	//RELATIVE A UTENTE


	public int statistics9(String email){

		int result=0;	
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){
			try{
				//1) numero di manche giocate
				result=RdFDB.statistics9(email);
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}else{
			System.out.println("problem of connection!");
			return 0;
		}

	}


	public int statistics10(String email){

		int result=0;	
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){
			try{
				//1) numero di partite giocate
				result=RdFDB.statistics9(email);
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}else{
			System.out.println("problem of connection!");
			return 0;
		}

	}



	public int statistics11(String email){

		int result=0;	
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){
			try{
				//1) numero di manche osservate
				result=RdFDB.statistics11(email);
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}else{
			System.out.println("problem of connection!");
			return 0;
		}

	}


	public int statistics12(String email){

		int result=0;	
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){
			try{
				//1) numero di partite osservate
				result=RdFDB.statistics12(email);
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}else{
			System.out.println("problem of connection!");
			return 0;
		}

	}


	public int statistics13(String email){

		int result=0;	
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){
			try{
				//1) numero di manche vinte
				result=RdFDB.statistics13(email);
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}else{
			System.out.println("problem of connection!");
			return 0;
		}

	}


	public int statistics14(String email){

		int result=0;	
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){
			try{
				//1) numero di partite vinte
				result=RdFDB.statistics14(email);
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}else{
			System.out.println("problem of connection!");
			return 0;
		}

	}


	public int statistics15(String email){

		int result=0;	
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){
			try{
				//7il punteggio medio vinto per partita
				result=RdFDB.statistics15(email);
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}else{
			System.out.println("problem of connection!");
			return 0;
		}

	}


	public int statistics16(String email){

		int result=0;	
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){
			try{
				//Il numero medio di volte, per manche che ha dovuto cedere il turno di gioco
				result=RdFDB.statistics16(email);
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}else{
			System.out.println("problem of connection!");
			return 0;
		}

	}


	public int statistics17(String email){

		int result=0;	
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){
			try{
				//Il numero medio di volte, per partita che ha dovuto cedere il turno di gioco
				result=RdFDB.statistics17(email);
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}else{
			System.out.println("problem of connection!");
			return 0;
		}

	}





	public int statistics18(String email){

		int result=0;	
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){
			try{
				//Il numero medio di volte, per manche che perso tutto
				result=RdFDB.statistics18(email);
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}else{
			System.out.println("problem of connection!");
			return 0;
		}

	}


	public int statistics19(String email){

		int result=0;	
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){
			try{
				//Il numero medio di volte, per partita che ha perso tutto
				result=RdFDB.statistics19(email);
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}else{
			System.out.println("problem of connection!");
			return 0;
		}

	}


	public Hashtable<Integer,Phrases> getPhrases(){
		Hashtable<Integer,Phrases> data=new Hashtable<Integer,Phrases>();
		DBimplementation RdFDB = new DBimplementation();
		if(RdFDB.connect()){
			try{
				data= RdFDB.get_phrases();//ottiene le frasi 
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			System.out.println("problem of connection!");
		}
		return data;
	}

	public int updatePhrases(Object[][] phrases){
		int result=0; 
		DBimplementation RdFDB = new DBimplementation();

		if(RdFDB.connect()){
			try{
				result=RdFDB.update_Phrases(phrases);
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}else{
			System.out.println("problem of connection!");
			return 0;
		}
	}


}

