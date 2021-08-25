package gioco;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.xml.bind.DatatypeConverter;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;


public class DBimplementation extends DBadapter {

	private int update=0,update2=0;



	public boolean AdminExist(){

		boolean returned=false;
		PreparedStatement admin_exist_stmt = null;
		ResultSet admin_exist_exc = null;
		try {
			admin_exist_stmt = conn.prepareStatement("select * from utente where admin=true");//cerco se esiste un amministratore nel database
			admin_exist_exc = admin_exist_stmt.executeQuery();//eseguo la query
			if(admin_exist_exc.isBeforeFirst()){//se il risultato non è vuoto ovvero il cursore è prima di qualcosa
				returned=true;//allora esiste il profilo amministratore
			}else{
				returned=false;//altrimenti no
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(admin_exist_exc!=null){
				try {
					admin_exist_exc.close();//chiudo il ResultSet
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			if(admin_exist_stmt!=null){
				try {
					admin_exist_stmt.close();//chiudo il PreparedStatement
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();//mi disconnetto
		}
		return returned;
	}

	public boolean insert_admin(String email,String name,String surname,String nickname,String passw){

		boolean returned = false;

		PreparedStatement insert_admin_stmt=null;
		try {
			//inserisco i dati dell'amministratore nella tabella utente del database con il campo admin settato a true
			insert_admin_stmt = conn.prepareStatement("INSERT INTO public.utente(email, nome, cognome, nickname, password, admin,oracodice)VALUES (?, ? , ?, ?, ?, ?, ?)");
			insert_admin_stmt.setString(1, email);
			insert_admin_stmt.setString(2, name);
			insert_admin_stmt.setString(3, surname);
			insert_admin_stmt.setString(4, nickname);
			insert_admin_stmt.setString(5, Md5Hash(passw));
			insert_admin_stmt.setBoolean(6, true);
			insert_admin_stmt.setLong(7, 0);
			insert_admin_stmt.executeUpdate();
			returned=true;

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(insert_admin_stmt!=null){
				try {
					insert_admin_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return returned;

	}

	public boolean insert_newuser(String email, String name ,String surname, String nickname,boolean isAdmin) throws UnsupportedEncodingException, NoSuchAlgorithmException, SendFailedException, MessagingException{

		boolean returned = false;
		LocalDateTime now = LocalDateTime.now();//ottengo la data di adesso
		byte[] array = new byte[7]; 
		new Random().nextBytes(array);
		String generatedString = new String(array, Charset.forName("UTF-8"));
		String passw=email+now+generatedString;//concateno la data a una stringa casuale
		long time=0;
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(passw.getBytes());//cripto la stringa in md5
		byte[] digest = md.digest();
		passw = DatatypeConverter.printHexBinary(digest).toUpperCase();
		passw = passw.substring(0,10);//prendo i primi 10 caratteri

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		time= timestamp.getTime();

		PreparedStatement find_user_stmt =null;
		PreparedStatement insert_user_stmt=null;
		PreparedStatement get_codetime_stmt=null;
		PreparedStatement update_code_stmt=null;
		ResultSet find_user_exc=null;
		ResultSet get_codetime_exc=null;


		try {
			//controllo se esiste un utente già registrato con questa email o nickname
			find_user_stmt = conn.prepareStatement("select * from utente where email=? or nickname=?");
			find_user_stmt.setString(1, email);
			find_user_stmt.setString(2, nickname);
			find_user_exc = find_user_stmt.executeQuery();
			//se non ci sono utenti registrati con questa email
			if(!find_user_exc.isBeforeFirst()){
				//inserisco i dati dell'utente nel database
				insert_user_stmt = conn.prepareStatement("INSERT INTO public.utente(email, nome, cognome, nickname, password, admin,oracodice)VALUES (?, ? , ?, ?, ?, ?, ?)");
				insert_user_stmt.setString(1, email);
				insert_user_stmt.setString(2, name);
				insert_user_stmt.setString(3, surname);
				insert_user_stmt.setString(4, nickname);
				insert_user_stmt.setString(5, passw);
				insert_user_stmt.setBoolean(6, isAdmin);
				insert_user_stmt.setLong(7, time);
				update=insert_user_stmt.executeUpdate();
				if(update==1){//se aggiorno i dati nel database invio email contente il codice di attivazione
					new MailSender("ruotadellafortunamanagement@gmail.com", "Fortuna98", email, "Codice attivazione", "Benvenuto nel gioco La Ruota Della Fortuna! Ecco il suo codice di attivazione: "+ passw +" , le ricordiamo che il codice ha una validità di 10 minuti.");
					returned=true;			
				}			
			}  else { 
				//controllo che l'utente abbia effettivamente completato la registrazione altrimenti lo faccio registrare di nuovo

				get_codetime_stmt = conn.prepareStatement("select oracodice from utente where email=?");
				get_codetime_stmt.setString(1, email);
				get_codetime_exc = get_codetime_stmt.executeQuery();
				get_codetime_exc.next();
				long codetime=get_codetime_exc.getLong(1);
				if(codetime!=0){//se non ha confermato il codice di attivazione permetto di resgistrare di nuovo con la stessa email
					update_code_stmt = conn.prepareStatement("UPDATE utente SET nome=?, cognome=?,nickname=?,password=?,admin=?,oracodice=? where email=?");
					update_code_stmt.setString(1, name);
					update_code_stmt.setString(2, surname);
					update_code_stmt.setString(3, nickname);
					update_code_stmt.setString(4, passw);
					update_code_stmt.setBoolean(5, isAdmin);
					update_code_stmt.setLong(6, time);
					update_code_stmt.setString(7, email);
					update = update_code_stmt.executeUpdate();	
					if(update==1){
						new MailSender("ruotadellafortunamanagement@gmail.com", "Fortuna98", email, "Codice attivazione", "Benvenuto nel gioco La Ruota Della Fortuna! Ecco il suo codice di attivazione: "+ passw +" , le ricordiamo che il codice ha una validità di 10 minuti.");
						returned=true;
					}else{
						returned=false;
					}

				}else{

					returned=false;//utente già registrato correttamente
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(insert_user_stmt!=null){
				try {
					insert_user_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(find_user_exc!=null){
				try {
					find_user_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(find_user_stmt!=null){
				try {
					find_user_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(get_codetime_exc!=null){
				try {
					get_codetime_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(get_codetime_stmt!=null){
				try {
					get_codetime_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(update_code_stmt!=null){
				try {
					update_code_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return returned;
	}


	public boolean user_login(String name,String passw){
		boolean returned=false;
		PreparedStatement user_login_stmt=null;
		ResultSet user_login_exc=null;

		try {
			//verifico se esiste utente(non amministratore) con quella email e password nel database 
			user_login_stmt = conn.prepareStatement("select * from utente where email=? AND password=? AND admin=false AND oracodice=0");
			user_login_stmt.setString(1, name);
			user_login_stmt.setString(2, Md5Hash(passw));
			user_login_exc = user_login_stmt.executeQuery();
			if(user_login_exc.isBeforeFirst()){//se esiste e i dati sono corretti allora ritorno true
				returned=true;
			}else{
				returned=false;//altrimenti i dati sono errati e ritorno false
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(user_login_exc!=null){
				try {
					user_login_exc.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}}
			if(user_login_stmt!=null){
				try {
					user_login_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();//disconnetto 

		}
		return returned;
	}

	public ArrayList<String> otherPlayer_list(long idmatch,String nick){

		ArrayList<String> returned=new ArrayList<String>();
		PreparedStatement player_list_stmt=null;
		ResultSet player_list_exc=null;
		try {
			//ottengo la lista dei giocatori alla partita tranne chi ha chiamato il metodo
			player_list_stmt= conn.prepareStatement("select nickname from partecipazione natural join utente where idpartita=? and tipo=0");
			player_list_stmt.setLong(1, idmatch);
			player_list_exc = player_list_stmt.executeQuery();
			if(player_list_exc.isBeforeFirst()){
				while(player_list_exc.next()){
					if(!(player_list_exc.getString(1).equals(nick))){
						returned.add(player_list_exc.getString(1));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(player_list_exc!=null){
				try {
					player_list_exc.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}}
			if(player_list_stmt!=null){
				try {
					player_list_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return returned;
	}

	public ArrayList<String> allPlayer_list(long idmatch){

		ArrayList<String> returned=new ArrayList<String>();
		PreparedStatement player_list_stmt=null;
		ResultSet player_list_exc=null;
		try {
			//ottengo la lista di tutti i giocatori alla partita
			player_list_stmt= conn.prepareStatement("select nickname from partecipazione natural join utente where idpartita=? and tipo=0");
			player_list_stmt.setLong(1, idmatch);
			player_list_exc = player_list_stmt.executeQuery();
			if(player_list_exc.isBeforeFirst()){
				while(player_list_exc.next()){
					returned.add(player_list_exc.getString(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(player_list_exc!=null){
				try {
					player_list_exc.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}}
			if(player_list_stmt!=null){
				try {
					player_list_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return returned;
	}

	public boolean admin_login(String name,String passw){
		boolean returned=false;
		ResultSet user_login_exc=null;
		PreparedStatement user_login_stmt=null;

		try {
			//verifico se esiste amministratore(non utente) con quella email e password nel database 
			user_login_stmt = conn.prepareStatement("select * from utente where email=? AND password=? AND admin=true AND oracodice=0");
			user_login_stmt.setString(1, name);
			user_login_stmt.setString(2, Md5Hash(passw));
			user_login_exc = user_login_stmt.executeQuery();
			if(user_login_exc.isBeforeFirst()){
				returned=true;
			}else{
				returned=false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(user_login_exc!=null){
				try {
					user_login_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(user_login_stmt!=null){
				try {
					user_login_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();

		}
		return returned;
	}

	public boolean code_update(String name,String passw,String code){
		boolean returned=true;
		PreparedStatement update_password_stmt=null;

		try {
			//se il codice corrisponde e non è scaduto allora aggiorno la password
			update_password_stmt = conn.prepareStatement("UPDATE utente SET password = ? , oracodice=0 where email=? AND password=?");
			update_password_stmt.setString(1, Md5Hash(passw));
			update_password_stmt.setString(2, name);
			update_password_stmt.setString(3, code);
			update = update_password_stmt.executeUpdate();	
			if(update==1){
				//invio email comunicando che la password è stata aggiornata
				new MailSender("ruotadellafortunamanagement@gmail.com", "Fortuna98", name, "Password aggiornata", "Salve, Le comunichiamo che la sua password è stata aggiornata! ");
				returned=true;
			}else{
				returned=false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(update_password_stmt!=null){
				try {
					update_password_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}

		return returned;
	}


	public int profile_update(String email,String name,String surname,String nickname){

		PreparedStatement update_profile_stmt=null;

		try {
			//aggiorno i dati del profilo dell'utente
			update_profile_stmt = conn.prepareStatement("UPDATE utente SET nome=? , cognome=? , nickname=? where email=?");
			update_profile_stmt.setString(1, name);
			update_profile_stmt.setString(2, surname);
			update_profile_stmt.setString(3, nickname);
			update_profile_stmt.setString(4, email);
			update = update_profile_stmt.executeUpdate();
			if(update==1){
				//invio email comunicando che i dati sono stati aggiornati
				new MailSender("ruotadellafortunamanagement@gmail.com", "Fortuna98", email, "Profilo aggiornato", "Salve " +name+", Le comunichiamo che il suo profilo è stato aggiornato! ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(update_profile_stmt!=null){
				try {
					update_profile_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}
		return update;
	}   


	public boolean verify_code(String name,String passw){
		boolean returned=false;
		long time1=0;
		long time2=0;
		PreparedStatement verify_code_stmt=null;
		ResultSet verify_code_exc=null;
		PreparedStatement delete_user_stmt=null;
		try {
			//verifico se il codice inserito dall'utente corrisponde a quello nel database
			verify_code_stmt = conn.prepareStatement("select oracodice from utente where email=? AND password=?");
			verify_code_stmt.setString(1, name);
			verify_code_stmt.setString(2, passw);
			verify_code_exc = verify_code_stmt.executeQuery();

			if(verify_code_exc.isBeforeFirst()){//se corrisponde

				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				time1= timestamp.getTime();//ora attuale
				verify_code_exc.next();
				time2=verify_code_exc.getLong(1);//ora codice attivazione
				long milliseconds = time1 - time2; //calcolo tempo passato dall'invio (ora attuale meno ora codice di attivazione)
				int seconds = (int) milliseconds / 1000;
				int minutes = (seconds % 3600) / 60;//converto in minuti
				seconds = (seconds % 3600) % 60;
				if(minutes>=10)//verifico se non sono passati più di 10 minuti dall'invio del codice di attivazione
				{
					delete_user_stmt = conn.prepareStatement("DELETE FROM utente WHERE email=?");//se si elimino utente
					delete_user_stmt.setString(1, name);
					delete_user_stmt.executeUpdate();
					returned=false;//codice scaduto
				}else{
					returned=true;//codice corrisponde e non scaduto
				}
			}else{
				returned=false;//codice non corrisponde
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{

			if(delete_user_stmt!=null){
				try {
					delete_user_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			if(verify_code_exc!=null){
				try {
					verify_code_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			if(verify_code_stmt!=null){
				try {
					verify_code_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}
		return returned;
	}


	public String[] get_data(String email){

		String[] data=new String[3];
		PreparedStatement get_data_stmt=null;
		ResultSet get_data_exc=null;
		try {
			//seleziono i dati realativi all'utente
			get_data_stmt = conn.prepareStatement("select nome,cognome,nickname from utente where email=?");
			get_data_stmt.setString(1, email);
			get_data_exc = get_data_stmt.executeQuery();
			if(get_data_exc.next()){
				data[0]=get_data_exc.getString(1);
				data[1]=get_data_exc.getString(2);
				data[2]=get_data_exc.getString(3);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(get_data_exc!=null){
				try {
					get_data_exc.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}}
			if(get_data_stmt!=null){
				try {
					get_data_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}
		return data;
	}

	public boolean insert_phrases() throws FileNotFoundException, IOException{

		boolean returned = false;

		try (Connection conn = DriverManager.getConnection(jdbUrl, username, password)) {
			long rowsInserted = new CopyManager((BaseConnection) conn)
			.copyIn(
					"COPY frase(tema,corpo) FROM STDIN (FORMAT csv, HEADER,ENCODING 'UTF-8')", 
					new BufferedReader(new InputStreamReader(new FileInputStream("src/gioco/phrases/frasi.csv"),"UTF-8"))
					);
			update2=(int) rowsInserted;
		}catch (SQLException e) {
			e.printStackTrace();
		}

		if(update2>=0){
			returned = true;
		}

		return returned;

	}



	public long create_match(String email){

		long returned=0;
		long id=0;
		PreparedStatement partecipation_stmt=null;
		ResultSet create_match_stmt_exc=null;
		PreparedStatement create_match_stmt=null;

		try {
			String generatedColumns[] = {"idpartita"};

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			long time1= timestamp.getTime();//ora attuale

			//inserimento nuova partita
			create_match_stmt = conn.prepareStatement("INSERT into partita (datainizio, datafine,email,nmanche,winner,turno) VALUES (?,?,?,0,null,null);",generatedColumns );
			create_match_stmt.setLong(1, time1);
			create_match_stmt.setLong(2, 0);
			create_match_stmt.setString(3, email);
			update = create_match_stmt.executeUpdate();
			create_match_stmt_exc = create_match_stmt.getGeneratedKeys();
			if (create_match_stmt_exc.next()) {
				id = create_match_stmt_exc.getLong(1);
			}

			if(update==1){
				//inserisco partecipazione alla partita 
				partecipation_stmt = conn.prepareStatement("INSERT into partecipazione (email, idpartita,tipo) VALUES (?,?,0)");
				partecipation_stmt.setString(1, email);
				partecipation_stmt.setLong(2, id);
				update2 = partecipation_stmt.executeUpdate();
				if(update2==1){
					returned=id;
				}else{
					returned=0;
				}
			}else{
				returned=0;
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}finally{

			if(partecipation_stmt!=null){
				try {
					partecipation_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(create_match_stmt_exc!=null){
				try {
					create_match_stmt_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(create_match_stmt!=null){
				try {
					create_match_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}
		return returned;

	}

	public boolean checkPhrases(long idMatch){

		boolean returned=false;

		int nphrases=0;
		ArrayList<String> players =new ArrayList<String>();
		ArrayList<String> admin =new ArrayList<String>();
		PreparedStatement player_list_stmt=null;
		ResultSet player_list_exc=null;
		PreparedStatement randomphrases_stmt=null;
		ResultSet randomphrases_exc=null;
		PreparedStatement admin_list_stmt=null;
		ResultSet admin_list_exc=null;
		PreparedStatement delete_partecipation_stmt=null;
		PreparedStatement delete_match_stmt=null;

		try{
			//seleziono i partecipanti(giocatori) alla partita
			player_list_stmt = conn.prepareStatement("select email from partecipazione where idpartita=? and tipo=0");
			player_list_stmt.setLong(1, idMatch);
			player_list_exc = player_list_stmt.executeQuery();
			if(player_list_exc.isBeforeFirst()){
				while(player_list_exc.next()){
					players.add(player_list_exc.getString(1));
				}
			}
			//controllo che ci siano almeno 5 frasi che i giocatori non hanno mai visto
			randomphrases_stmt = conn.prepareStatement("SELECT count(*) as total FROM frase WHERE idfrase not in(select distinct idfrase from manche natural join partita inner join partecipazione on partita.idpartita=partecipazione.idpartita where (partecipazione.email=? OR partecipazione.email=? OR partecipazione.email=?) AND idfrase is not null)");
			randomphrases_stmt.setString(1, players.get(0));
			randomphrases_stmt.setString(2, players.get(1));
			randomphrases_stmt.setString(3, players.get(2));
			randomphrases_exc = randomphrases_stmt.executeQuery();
			if(randomphrases_exc.isBeforeFirst()){
				while(randomphrases_exc.next()){
					nphrases=randomphrases_exc.getInt("total");			}
			}

			if(nphrases<5){//se non ci sono almeno 5 frasi disponibili
				//seleziona tutti gli amministratori
				admin_list_stmt = conn.prepareStatement("select email from utente where admin=true");
				admin_list_exc = admin_list_stmt.executeQuery();
				if(admin_list_exc.isBeforeFirst()){
					while(admin_list_exc.next()){
						admin.add(admin_list_exc.getString(1));
					}
				}

				for(String a:admin){
					//manda e-mail agli amministratori
					new MailSender("ruotadellafortunamanagement@gmail.com", "Fortuna98", a, "Aggiungere nuove Frasi", "Attenzione! Aggiungere nuove frasi al gioco la Ruota della Fortuna!.");		
				}

				//elimino la partecipazione e la partita
				delete_partecipation_stmt = conn.prepareStatement("DELETE FROM partecipazione WHERE idpartita=?");
				delete_partecipation_stmt.setLong(1, idMatch);
				delete_partecipation_stmt.executeUpdate();
				//elimina la partita
				delete_match_stmt = conn.prepareStatement("DELETE FROM partita WHERE idpartita=?");
				delete_match_stmt.setLong(1, idMatch);
				delete_match_stmt.executeUpdate();

				returned=false;

			}else{
				returned=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{

			if(player_list_exc!=null){
				try {
					player_list_exc.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}}
			if(player_list_stmt!=null){
				try {
					player_list_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(randomphrases_exc!=null){
				try {
					randomphrases_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(randomphrases_stmt!=null){
				try {
					randomphrases_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(admin_list_exc!=null){
				try {
					admin_list_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(admin_list_stmt!=null){
				try {
					admin_list_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(delete_partecipation_stmt!=null){
				try {
					delete_partecipation_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(delete_match_stmt!=null){
				try {
					delete_match_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}

		return returned;

	}

	public Hashtable<Long,String> matches_list(){
		Hashtable<Long,String> data = new Hashtable<Long,String>();
		PreparedStatement match_list_stmt=null;
		ResultSet match_list_exc=null;
		try {
			//seleziono la lista delle partite (le ultime 5)
			match_list_stmt = conn.prepareStatement("select idpartita,nickname from partita natural join utente where datafine=0 ORDER BY idpartita DESC limit 5");
			match_list_exc = match_list_stmt.executeQuery();
			while(match_list_exc.next()){
				data.put(match_list_exc.getLong(1),match_list_exc.getString(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(match_list_exc!=null){
				try {
					match_list_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(match_list_stmt!=null){
				try{
					match_list_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}
		return data;
	}


	public boolean reset_Password(String email) throws UnsupportedEncodingException, NoSuchAlgorithmException, SendFailedException, MessagingException{

		LocalDateTime now = LocalDateTime.now();
		byte[] array = new byte[7]; // length is bounded by 7
		new Random().nextBytes(array);
		String generatedString = new String(array, Charset.forName("UTF-8"));
		String passw=email+now+generatedString;
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(passw.getBytes());
		byte[] digest = md.digest();
		passw = DatatypeConverter.printHexBinary(digest).toUpperCase();
		passw = passw.substring(0,10);
		boolean returned=false;

		PreparedStatement reset_password=null;

		try {
			//aggiorno password inviando una nuova passowrd generata casualmente
			reset_password = conn.prepareStatement("UPDATE utente SET password = ? where email=?");
			reset_password.setString(1, passw);
			reset_password.setString(2, email);
			update = reset_password.executeUpdate();

			if(update==1){
				//invio nuova password per email
				new MailSender("ruotadellafortunamanagement@gmail.com", "Fortuna98", email, "Nuova Password", "Gentile utente, questa è la sua nuova passowrd: "+ passw );
				returned=true;
			}else{
				returned=false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(reset_password!=null){
				try {
					reset_password.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}
		return returned;

	}


	public int n_player(long idmatch){
		int returned=0;
		PreparedStatement n_player_stmt=null;
		ResultSet n_player_exc=null;
		try {
			//seleziono il numero di giocatori alla partita(escludendo gli osservatori)
			n_player_stmt = conn.prepareStatement("select count(*) from partecipazione where idpartita=? and tipo=0");
			n_player_stmt.setLong(1, idmatch);
			n_player_exc = n_player_stmt.executeQuery();
			if(n_player_exc.isBeforeFirst()){
				n_player_exc.next();
				returned=n_player_exc.getInt(1);

			}else{
				returned=0;//se non ci sono giocatori ritorna 0
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(n_player_exc!=null){
				try {
					n_player_exc.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}}
			if(n_player_stmt!=null){
				try {
					n_player_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}
		return returned;
	}


	public ArrayList<String> player_list(long idmatch,String email){

		ArrayList<String> returned=new ArrayList<String>();
		PreparedStatement player_list_stmt =null;
		ResultSet player_list_exc=null;
		try {
			//seleziono la lista dei giocatori e degli osservatori alla partita
			player_list_stmt = conn.prepareStatement("select email from partecipazione where idpartita=? and tipo <> 2");
			player_list_stmt.setLong(1, idmatch);
			player_list_exc = player_list_stmt.executeQuery();
			if(player_list_exc.isBeforeFirst()){
				while(player_list_exc.next()){
					if(!(player_list_exc.getString(1).equals(email))){
						returned.add(player_list_exc.getString(1));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(player_list_exc!=null){
				try {
					player_list_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(player_list_stmt!=null){
				try {
					player_list_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}
		return returned;
	}



	public void start_match(long idMatch){

		long idphrase=0;
		ArrayList<String> players =new ArrayList<String>();
		PreparedStatement update_manche_stmt=null;
		PreparedStatement player_list_stmt=null;
		ResultSet player_list_exc=null;
		PreparedStatement randomphrases_stmt =null;
		ResultSet randomphrases_exc=null;
		PreparedStatement new_manche_stmt=null;
		try{
			//aggiorno la partita settando la manche a 1
			update_manche_stmt = conn.prepareStatement("update partita set nmanche=nmanche+1 where idpartita=?");
			update_manche_stmt.setLong(1, idMatch);
			update_manche_stmt.executeUpdate();

			//seleziono la lista dei giocatori alla partita
			player_list_stmt = conn.prepareStatement("select email from partecipazione where idpartita=? and tipo=0");
			player_list_stmt.setLong(1, idMatch);
			player_list_exc = player_list_stmt.executeQuery();
			if(player_list_exc.isBeforeFirst()){
				while(player_list_exc.next()){
					players.add(player_list_exc.getString(1));
				}

			}
			//scelgo una frase che non è mai stata usata dai tre giocatori
			randomphrases_stmt = conn.prepareStatement("SELECT idfrase FROM frase WHERE idfrase not in(select distinct idfrase from manche natural join partita inner join partecipazione on partita.idpartita=partecipazione.idpartita where (partecipazione.email=? OR partecipazione.email=? OR partecipazione.email=?) AND idfrase is not null) ORDER BY RANDOM() LIMIT 1");
			randomphrases_stmt.setString(1, players.get(0));
			randomphrases_stmt.setString(2, players.get(1));
			randomphrases_stmt.setString(3, players.get(2));
			randomphrases_exc = randomphrases_stmt.executeQuery();
			if(randomphrases_exc.isBeforeFirst()){
				randomphrases_exc.next();
				idphrase=randomphrases_exc.getLong(1);
			}
			//inserisco una nuova manche nella tabella manche associando la frase
			new_manche_stmt = conn.prepareStatement("INSERT into manche (idmanche,idpartita,idfrase,punteggio,vincitore) VALUES (1,?,?,0,null)");
			new_manche_stmt.setLong(1, idMatch);
			new_manche_stmt.setLong(2, idphrase);
			update = new_manche_stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(update_manche_stmt!=null){
				try {
					update_manche_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(player_list_exc!=null){
				try {
					player_list_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(player_list_stmt!=null){
				try {
					player_list_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(randomphrases_exc!=null){
				try {
					randomphrases_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(randomphrases_stmt!=null){
				try {
					randomphrases_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(new_manche_stmt!=null){
				try {
					new_manche_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}

	}


	public void update_last_manche(long idMatch,String email,int score){
		PreparedStatement update_manche_winner_stmt=null;
		try {
			//aggiorno l'ultima manche 
			update_manche_winner_stmt = conn.prepareStatement("update manche set vincitore=? , punteggio=? where idpartita=? and idmanche=(SELECT nmanche from partita WHERE idpartita=?)");
			update_manche_winner_stmt.setString(1, email);
			update_manche_winner_stmt.setInt(2, score);
			update_manche_winner_stmt.setLong(3, idMatch);
			update_manche_winner_stmt.setLong(4, idMatch);
			update_manche_winner_stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(update_manche_winner_stmt!=null){
				try {
					update_manche_winner_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}

	}



	public void update_manche(long idMatch,String email,int score){

		int nmanche=0;
		long idphrase=0;
		ArrayList<String> players =new ArrayList<String>();
		PreparedStatement update_manche_winner_stmt=null;
		PreparedStatement update_manche_stmt =null;
		PreparedStatement get_nmanche_stmt=null;
		ResultSet get_nmanche_exc=null;
		PreparedStatement player_list_stmt=null;
		ResultSet player_list_exc=null;
		PreparedStatement randomphrases_stmt=null;
		ResultSet randomphrases_exc=null;
		PreparedStatement new_manche_stmt=null;
		try {
			//aggiorno manche inserendo il vincitore e il suo punteggio
			update_manche_winner_stmt = conn.prepareStatement("update manche set vincitore=? , punteggio=? where idpartita=? and idmanche=(SELECT nmanche from partita WHERE idpartita=?)");
			update_manche_winner_stmt.setString(1, email);
			update_manche_winner_stmt.setInt(2, score);
			update_manche_winner_stmt.setLong(3, idMatch);
			update_manche_winner_stmt.setLong(4, idMatch);
			update_manche_winner_stmt.executeUpdate();
			//aggiorno la tabella partita cambiando il numero della manche
			update_manche_stmt = conn.prepareStatement("update partita set nmanche=nmanche+1 where idpartita=?");
			update_manche_stmt.setLong(1, idMatch);
			update_manche_stmt.executeUpdate();
			//seleziono il numero della manche
			get_nmanche_stmt = conn.prepareStatement("SELECT nmanche from partita where idpartita=?");
			get_nmanche_stmt.setLong(1, idMatch);
			get_nmanche_exc = get_nmanche_stmt.executeQuery();
			if(get_nmanche_exc.isBeforeFirst()){
				get_nmanche_exc.next();
				nmanche=get_nmanche_exc.getInt(1);
			}
			//seleziono i giocatori della partita
			player_list_stmt = conn.prepareStatement("select email from partecipazione where idpartita=? and tipo=0");
			player_list_stmt.setLong(1, idMatch);
			player_list_exc = player_list_stmt.executeQuery();
			if(player_list_exc.isBeforeFirst()){
				while(player_list_exc.next()){
					players.add(player_list_exc.getString(1));
				}
			}
			//seleziono una frase che non è mai stata usata dai giocatori
			randomphrases_stmt = conn.prepareStatement("SELECT idfrase FROM frase WHERE idfrase not in(select distinct idfrase from manche natural join partita inner join partecipazione on partita.idpartita=partecipazione.idpartita where (partecipazione.email=? OR partecipazione.email=? OR partecipazione.email=?) AND idfrase is not null) ORDER BY RANDOM() LIMIT 1");
			randomphrases_stmt.setString(1, players.get(0));
			randomphrases_stmt.setString(2, players.get(1));
			randomphrases_stmt.setString(3, players.get(2));
			randomphrases_exc = randomphrases_stmt.executeQuery();
			if(randomphrases_exc.isBeforeFirst()){
				randomphrases_exc.next();
				idphrase=randomphrases_exc.getLong(1);
			}
			//inserisco una nuova manche associandole la frase
			new_manche_stmt = conn.prepareStatement("INSERT into manche (idmanche,idpartita,idfrase,punteggio,vincitore) VALUES (?,?,?,0,null)");
			new_manche_stmt.setLong(1, nmanche);
			new_manche_stmt.setLong(2, idMatch);
			new_manche_stmt.setLong(3, idphrase);
			update = new_manche_stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(update_manche_winner_stmt!=null){
				try {
					update_manche_winner_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(update_manche_stmt!=null){
				try {
					update_manche_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(get_nmanche_exc!=null){
				try {
					get_nmanche_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(get_nmanche_stmt!=null){
				try {
					get_nmanche_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(player_list_exc!=null){
				try {
					player_list_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(player_list_stmt!=null){
				try {
					player_list_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(randomphrases_exc!=null){
				try {
					randomphrases_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(randomphrases_stmt!=null){
				try {
					randomphrases_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(new_manche_stmt!=null){
				try {
					new_manche_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}

	}


	public ArrayList<String> get_phrase(long id){

		int nwords;
		ArrayList<String> result=new ArrayList<String>();
		int manche=0;
		String string="";
		PreparedStatement get_manche_stmt=null;
		ResultSet get_manche_exc=null;
		PreparedStatement get_phrase_stmt=null;
		ResultSet get_phrase_exc=null;
		try {
			//seleziono il numero della manche corrispondete alla partita
			get_manche_stmt = conn.prepareStatement("select nmanche from partita where idpartita=?");
			get_manche_stmt.setLong(1, id);
			get_manche_exc = get_manche_stmt.executeQuery();
			if(get_manche_exc.isBeforeFirst()){
				get_manche_exc.next();
				manche=get_manche_exc.getInt(1);
			}
			//seleziono il corpo della frase
			get_phrase_stmt = conn.prepareStatement("select corpo from frase natural join manche natural join partita where idmanche=? and partita.idpartita=? and partita.datafine = 0 ");
			get_phrase_stmt.setLong(1, manche);
			get_phrase_stmt.setLong(2, id);
			get_phrase_exc = get_phrase_stmt.executeQuery();
			if(get_phrase_exc.isBeforeFirst()){
				get_phrase_exc.next();
				string=get_phrase_exc.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{		
			if(get_manche_exc!=null){
				try {
					get_manche_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(get_manche_stmt!=null){
				try {
					get_manche_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}		
			if(get_phrase_exc!=null){
				try {
					get_phrase_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(get_phrase_stmt!=null){
				try {
					get_phrase_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}

		if(string.length()<=60){
			String[] strings_to_be_joined= string.split(" ");
			nwords = strings_to_be_joined.length;
			ArrayList<String> words=new ArrayList<String>();
			ArrayList<String> joinedwords=new ArrayList<String>();

			for(String c: strings_to_be_joined){
				words.add(c);	}
			String wordssum = null;
			for (int i = 0; i < words.size(); i++) {
				wordssum=words.get(i);	
				if(wordssum.length()<=15){			
					for(int c=0;c<=(nwords/4);c++){			
						if((i+1<words.size()) && wordssum.length()+1+words.get(i+1).length()<=15){	
							wordssum+=" "+words.get(i+1);i++;}}		
				}else{wordssum=words.get(i); i++;
				}joinedwords.add(wordssum);}
			result=joinedwords;
		}

		for(String x:result){System.out.println(x);}
		return result;
	}


	public String get_theme(long id){

		int manche=0;
		String stringa="";
		PreparedStatement get_manche_theme_stmt =null;
		ResultSet get_manche_theme_exc=null;
		PreparedStatement get_theme_stmt=null;
		ResultSet get_theme_exc=null;
		try {
			//seleziono il numero della manche corrispondete alla partita
			get_manche_theme_stmt = conn.prepareStatement("select nmanche from partita where idpartita=?");
			get_manche_theme_stmt.setLong(1, id);
			get_manche_theme_exc = get_manche_theme_stmt.executeQuery();
			if(get_manche_theme_exc.isBeforeFirst()){
				get_manche_theme_exc.next();
				manche=get_manche_theme_exc.getInt(1);
			}
			//seleziono il tema della frase
			get_theme_stmt = conn.prepareStatement("select tema from frase natural join manche natural join partita where idmanche=? and partita.idpartita=? and partita.datafine = 0  ");
			get_theme_stmt.setLong(1, manche);
			get_theme_stmt.setLong(2, id);
			get_theme_exc = get_theme_stmt.executeQuery();
			if(get_theme_exc.isBeforeFirst()){
				get_theme_exc.next();
				stringa=get_theme_exc.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(get_manche_theme_exc!=null){
				try {
					get_manche_theme_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(get_manche_theme_stmt!=null){
				try {
					get_manche_theme_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(get_theme_exc!=null){
				try {
					get_theme_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(get_theme_stmt!=null){
				try {
					get_theme_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}
		return stringa;
	}


	public void update_move(int nmanche,String email, int point, String move,long idMatch){

		PreparedStatement update_move_stmt=null;
		try {
			//inserisco una nuova mossa nella tabella mossa
			update_move_stmt = conn.prepareStatement("INSERT into mossa (idmanche,idutente,idpartita,punti,nome) VALUES (?,?,?,?,?);");
			update_move_stmt.setInt(1, nmanche);
			update_move_stmt.setString(2, email);
			update_move_stmt.setLong(3, idMatch);
			update_move_stmt.setInt(4, point);
			update_move_stmt.setString(5, move);
			update = update_move_stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(update_move_stmt!=null){
				try {
					update_move_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			disconnect();
		}


	}


	public void end_match(long idMatch){

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		long time1= timestamp.getTime();//ora attuale
		PreparedStatement end_match_stmt=null;

		try {
			//aggiorno la partita inserendo la data di fine della stessa
			end_match_stmt = conn.prepareStatement("update partita set datafine=? where idpartita=?");
			end_match_stmt.setLong(1, time1);
			end_match_stmt.setLong(2, idMatch);
			end_match_stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(end_match_stmt!=null){
				try {
					end_match_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}

	}

	public int join_match(String email,long idmatch){

		PreparedStatement join_match_stmt =null;
		try {
			//inserisco la partecipazione nella tabella partecipazione inserendo come tipo 0 (giocatore)
			join_match_stmt = conn.prepareStatement("INSERT into partecipazione (email, idpartita,tipo) VALUES (?,?,0)");
			join_match_stmt.setString(1, email);
			join_match_stmt.setLong(2, idmatch);
			update = join_match_stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(join_match_stmt!=null){
				try {
					join_match_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}
		return update;

	}


	public int observe_match(String email,long idmatch){

		PreparedStatement observe_match_stmt=null;

		try {
			//inserisco la partecipazione nella tabella partecipazione inserendo come tipo 1(osservatore)
			observe_match_stmt = conn.prepareStatement("INSERT into partecipazione (email, idpartita,tipo) VALUES (?,?,1)");
			observe_match_stmt.setString(1, email);
			observe_match_stmt.setLong(2, idmatch);
			update = observe_match_stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(observe_match_stmt!=null){
				try {
					observe_match_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}
		return update;

	}


	public String get_creator(long idmatch){
		String returned="";
		PreparedStatement get_creator_stmt=null;
		ResultSet get_creator_exc=null;
		try {
			//seleziono il creatore della partita
			get_creator_stmt = conn.prepareStatement("SELECT email from partita where idpartita=?");
			get_creator_stmt.setLong(1, idmatch);
			get_creator_exc = get_creator_stmt.executeQuery();
			if(get_creator_exc.isBeforeFirst()){
				get_creator_exc.next();
				returned=get_creator_exc.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(get_creator_exc!=null){
				try {
					get_creator_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(get_creator_stmt!=null){
				try {
					get_creator_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}
		return returned;
	}



	public String get_winner(long idmatch){

		String returned="";
		Hashtable<String,Integer> players=new Hashtable<String,Integer>();
		PreparedStatement player_list_stmt=null;
		ResultSet player_list_exc=null;
		PreparedStatement update_winner_stmt=null;
		try {
			//seleziono i punteggi ottenuti dai 3 giocatori nella partita
			player_list_stmt = conn.prepareStatement("select sum(punteggio),vincitore from manche where idpartita=? and vincitore in (select email from partecipazione where idpartita=? and tipo=0) group by vincitore ");
			player_list_stmt.setLong(1, idmatch);
			player_list_stmt.setLong(2, idmatch);
			player_list_exc = player_list_stmt.executeQuery();
			if(player_list_exc.isBeforeFirst()){
				while(player_list_exc.next()){
					players.put(player_list_exc.getString(2), player_list_exc.getInt(1));
				}	
			}

			//ottengo il giocatore con il punteggio più alto
			String maxKey=null;
			int maxValue = Integer.MIN_VALUE; 
			for(Map.Entry<String,Integer> entry : players.entrySet()) {
				if(entry.getValue() > maxValue) {
					maxValue = entry.getValue();
					maxKey = entry.getKey();
				}
			}
			//aggiorno la partita impostando il vincitore
			update_winner_stmt = conn.prepareStatement("update partita set winner=? where idpartita=?");
			update_winner_stmt.setString(1, maxKey);
			update_winner_stmt.setLong(2, idmatch);
			update_winner_stmt.executeUpdate();
			returned=maxKey;//ritorno l'email del vincitore

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(player_list_exc!=null){
				try {
					player_list_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			if(player_list_stmt!=null){
				try {
					player_list_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(update_winner_stmt!=null){
				try {
					update_winner_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}

		return returned;
	}


	public Hashtable<Integer,String> s_Turn(long idmatch){

		Hashtable<Integer,String> data = new Hashtable<Integer,String>();
		Random r = new Random();
		int n_random=r.nextInt(2);//numero casuale da 0 a 2
		String t=null;
		PreparedStatement s_turn_game=null;
		PreparedStatement update_match_stmt=null;
		ResultSet s_turn_game_exc=null;
		try {
			//seleziono un giocatore della partita in modo casuale e assegno il turno
			s_turn_game = conn.prepareStatement("select email from partecipazione where idpartita=? and tipo=0 LIMIT 1 OFFSET ?;");
			s_turn_game.setLong(1, idmatch);
			s_turn_game.setInt(2, n_random);
			s_turn_game_exc = s_turn_game.executeQuery();
			while(s_turn_game_exc.next()){
				data.put(n_random,s_turn_game_exc.getString(1));
				t=s_turn_game_exc.getString(1);
			}

			update_match_stmt = conn.prepareStatement("update partita set turno=? where idpartita=?");
			update_match_stmt.setString(1, t);
			update_match_stmt.setLong(2, idmatch);
			update_match_stmt.executeUpdate();


		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(s_turn_game_exc!=null){
				try {
					s_turn_game_exc.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}}
			if(s_turn_game!=null){
				try {
					s_turn_game.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}

		return data;
	}

	public Hashtable<Integer,String> c_Turn(int nTurn,long idmatch,String email,int nmanche){
		Hashtable<Integer,String> data = new Hashtable<Integer,String>();
		int turn=0;
		String move="CT";
		int point=0;
		//cambio il turno passando al prossimo giocatore
		if(nTurn==2){turn=0;}
		else{turn=nTurn+1;}

		String t=null;
		PreparedStatement c_turn_game=null;
		ResultSet c_turn_game_exc=null;
		PreparedStatement update_move_ct_stmt=null;
		PreparedStatement update_match_stmt=null;
		try {
			c_turn_game = conn.prepareStatement("select email from partecipazione where idpartita=? and tipo=0 LIMIT 1 OFFSET ?;");
			c_turn_game.setLong(1, idmatch);
			c_turn_game.setInt(2, turn);
			c_turn_game_exc = c_turn_game.executeQuery();
			while(c_turn_game_exc.next()){
				data.put(turn,c_turn_game_exc.getString(1));
				t=c_turn_game_exc.getString(1);
			}

			update_match_stmt = conn.prepareStatement("update partita set turno=? where idpartita=?");
			update_match_stmt.setString(1, t);
			update_match_stmt.setLong(2, idmatch);
			update_match_stmt.executeUpdate();

			//aggiorna mossa 
			update_move_ct_stmt = conn.prepareStatement("INSERT into mossa (idmanche,idutente,idpartita,punti,nome) VALUES (?,?,?,?,?);");
			update_move_ct_stmt.setInt(1, nmanche);
			update_move_ct_stmt.setString(2, email);
			update_move_ct_stmt.setLong(3, idmatch);
			update_move_ct_stmt.setInt(4, point);
			update_move_ct_stmt.setString(5, move);
			update = update_move_ct_stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(c_turn_game_exc!=null){
				try {
					c_turn_game_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(c_turn_game!=null){
				try {
					c_turn_game.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(update_move_ct_stmt!=null){
				try {
					update_move_ct_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}
		return data;
	}

	public int exit_lobby(long idmatch,String email){
		PreparedStatement exit_match_stmt =null;
		try {
			//elimino la partecipazione dalla partita quando un osservatore o giocatore esce dalla lobby
			exit_match_stmt = conn.prepareStatement("DELETE FROM partecipazione where idpartita=? and email=?");
			exit_match_stmt.setLong(1, idmatch);
			exit_match_stmt.setString(2, email);
			update = exit_match_stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(exit_match_stmt!=null){
				try {
					exit_match_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return update;
	}

	public int exit_lobbyObserver(long idmatch,String email){
		PreparedStatement exit_match_stmt =null;
		try {
			//elimino la partecipazione dalla partita quando un osservatore o giocatore esce dalla lobby
			exit_match_stmt = conn.prepareStatement("update partecipazione set tipo=2 where idpartita=? and email=?");
			exit_match_stmt.setLong(1, idmatch);
			exit_match_stmt.setString(2, email);
			update = exit_match_stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(exit_match_stmt!=null){
				try {
					exit_match_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return update;
	}

	public int remove_match(long idmatch){
		PreparedStatement join_match_stmt =null;
		try {
			//elimino la partita
			join_match_stmt = conn.prepareStatement("DELETE FROM partita where idpartita=?");
			join_match_stmt.setLong(1, idmatch);
			update = join_match_stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(join_match_stmt!=null){
				try {
					join_match_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return update;
	}


	public int join_observer(String email,long idmatch){
		PreparedStatement join_match_stmt =null;
		PreparedStatement rejoin_match_stmt =null;
		PreparedStatement check_stmt =null;
		ResultSet check_stmt_exc=null;
		try {
			//controllo se esiste già un osservatore con quella email in quella partita
			check_stmt = conn.prepareStatement("select * from partecipazione where idpartita=? and email=?");
			check_stmt.setLong(1, idmatch);
			check_stmt.setString(2, email);
			check_stmt_exc = check_stmt.executeQuery();
			if(check_stmt_exc.isBeforeFirst()){//se esiste aggirno lo stato a 1
				rejoin_match_stmt = conn.prepareStatement("update partecipazione set tipo=1 where idpartita=? and email=?");
				rejoin_match_stmt.setLong(1, idmatch);
				rejoin_match_stmt.setString(2, email);
				update = rejoin_match_stmt.executeUpdate();
			}else{
				//inserisco la partecipazione di un osservatore nel database
				join_match_stmt = conn.prepareStatement("INSERT into partecipazione (email, idpartita,tipo) VALUES (?,?,1)");
				join_match_stmt.setString(1, email);
				join_match_stmt.setLong(2, idmatch);
				update = join_match_stmt.executeUpdate();			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(join_match_stmt!=null){
				try {
					join_match_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			if(rejoin_match_stmt!=null){
				try {
					rejoin_match_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(check_stmt!=null){
				try {
					check_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			if(check_stmt_exc!=null){
				try {
					check_stmt_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}


			disconnect();
		}
		return update;
	}

	public QueryReturn statistics1(){
		QueryReturn returned=null;
		PreparedStatement statistics1_stmt=null;
		ResultSet statistics1_exc=null;
		try {
			statistics1_stmt = conn.prepareStatement("SELECT manche.punteggio,manche.vincitore,utente.nome FROM manche inner join utente on manche.vincitore=utente.email WHERE manche.punteggio >= all( SELECT manche.punteggio FROM manche ) ORDER BY idmanche ASC LIMIT 1");
			statistics1_exc = statistics1_stmt.executeQuery();
			if(statistics1_exc.isBeforeFirst()){
				statistics1_exc.next();
				returned=new QueryReturn(statistics1_exc.getFloat(1),statistics1_exc.getString(2),statistics1_exc.getString(3));
			}else{
				returned=new QueryReturn(0,"","");;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(statistics1_exc!=null){
				try {
					statistics1_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(statistics1_stmt!=null){
				try {
					statistics1_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}
		return returned;
	}

	public QueryReturn statistics2(){
		QueryReturn returned=null;
		PreparedStatement statistics2_stmt=null;
		ResultSet statistics2_exc=null;
		try {
			statistics2_stmt = conn.prepareStatement("select sum(manche.punteggio) as score,manche.vincitore,utente.nome from manche inner join utente on manche.vincitore=utente.email group by(manche.idpartita,manche.vincitore,utente.nome) HAVING sum(manche.punteggio) >= all( select sum(manche.punteggio) from manche group by(manche.idpartita,manche.vincitore,utente.nome)) ORDER BY manche.idpartita ASC LIMIT 1");
			statistics2_exc = statistics2_stmt.executeQuery();
			if(statistics2_exc.isBeforeFirst()){
				statistics2_exc.next();
				returned=new QueryReturn(statistics2_exc.getFloat(1),statistics2_exc.getString(2),statistics2_exc.getString(3));
			}else{
				returned=new QueryReturn(0,"","");;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(statistics2_exc!=null){
				try {
					statistics2_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(statistics2_stmt!=null){
				try {
					statistics2_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}
		return returned;
	}


	public QueryReturn statistics3(){
		QueryReturn returned=null;
		PreparedStatement statistics3_stmt=null;
		ResultSet statistics3_exc=null;
		try {
			statistics3_stmt = conn.prepareStatement("select count(*) as ntime,utente.email,utente.nome from manche inner join partita on manche.idpartita=partita.idpartita inner join partecipazione on partita.idpartita=partecipazione.idpartita inner join utente on partecipazione.email=utente.email group by (utente.email,utente.nome) having count(*) >= all( select count(*) as ntime from manche inner join partita on manche.idpartita=partita.idpartita inner join partecipazione on partita.idpartita=partecipazione.idpartita inner join utente on partecipazione.email=utente.email group by (utente.email,utente.nome)) LIMIT 1");
			statistics3_exc = statistics3_stmt.executeQuery();
			if(statistics3_exc.isBeforeFirst()){
				statistics3_exc.next();
				returned=new QueryReturn(statistics3_exc.getFloat(1),statistics3_exc.getString(2),statistics3_exc.getString(3));
			}else{
				returned=new QueryReturn(0,"","");;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(statistics3_exc!=null){
				try {
					statistics3_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(statistics3_stmt!=null){
				try {
					statistics3_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}
		return returned;
	}

	public QueryReturn statistics4(){
		QueryReturn returned=null;
		PreparedStatement statistics4_stmt=null;
		ResultSet statistics4_exc=null;
		try {
			statistics4_stmt = conn.prepareStatement("SELECT ROUND(AVG(punteggio),2) AS score,manche.vincitore,utente.nome FROM manche inner join utente on manche.vincitore=utente.email	group by (manche.vincitore,utente.nome) having ROUND(AVG(punteggio),2)>= all( SELECT ROUND(AVG(punteggio),2) FROM manche inner join utente on manche.vincitore=utente.email	group by (manche.vincitore,utente.nome)) limit 1");
			statistics4_exc = statistics4_stmt.executeQuery();
			if(statistics4_exc.isBeforeFirst()){
				statistics4_exc.next();
				returned=new QueryReturn(statistics4_exc.getFloat(1),statistics4_exc.getString(2),statistics4_exc.getString(3));
			}else{
				returned=new QueryReturn(0,"","");;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(statistics4_exc!=null){
				try {
					statistics4_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(statistics4_stmt!=null){
				try {
					statistics4_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}
		return returned;
	}

	public QueryReturn statistics5(){
		QueryReturn returned=null;
		PreparedStatement statistics5_stmt=null;
		ResultSet statistics5_exc=null;
		try {
			statistics5_stmt = conn.prepareStatement("select count(*) as ntime,mossa.idutente,utente.nome from mossa inner join utente on mossa.idutente=utente.email where mossa.nome='CT' group by (mossa.idutente,utente.nome) having count(*) >= all( select count(*) as ntime from mossa inner join utente on mossa.idutente=utente.email where mossa.nome='CT' group by (mossa.idutente,utente.nome)) limit 1");
			statistics5_exc = statistics5_stmt.executeQuery();
			if(statistics5_exc.isBeforeFirst()){
				statistics5_exc.next();
				returned=new QueryReturn(statistics5_exc.getFloat(1),statistics5_exc.getString(2),statistics5_exc.getString(3));
			}else{
				returned=new QueryReturn(0,"","");;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(statistics5_exc!=null){
				try {
					statistics5_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(statistics5_stmt!=null){
				try {
					statistics5_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return returned;
	}

	public QueryReturn statistics6(){
		QueryReturn returned=null;
		PreparedStatement statistics6_stmt=null;
		ResultSet statistics6_exc=null;
		try {
			statistics6_stmt = conn.prepareStatement("select count(*) as ntime,mossa.idutente,utente.nome from mossa inner join utente on mossa.idutente=utente.email where mossa.nome='Perde' group by (mossa.idutente,utente.nome) having count(*) >= all( select count(*) as ntime from mossa inner join utente on mossa.idutente=utente.email where mossa.nome='Perde' group by (mossa.idutente,utente.nome)) limit 1");
			statistics6_exc = statistics6_stmt.executeQuery();
			if(statistics6_exc.isBeforeFirst()){
				statistics6_exc.next();
				returned=new QueryReturn(statistics6_exc.getFloat(1),statistics6_exc.getString(2),statistics6_exc.getString(3));
			}else{
				returned=new QueryReturn(0,"","");;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(statistics6_exc!=null){
				try {
					statistics6_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(statistics6_stmt!=null){
				try {
					statistics6_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return returned;
	}

	public float statistics7(){
		float returned=0;
		PreparedStatement statistics7_stmt=null;
		ResultSet statistics7_exc=null;
		try {
			statistics7_stmt = conn.prepareStatement("select round(avg(movecount),2) from (select count(*) as movecount,manche.idmanche from mossa natural join manche where mossa.nome <> 'CT' group by(manche.idmanche)) as count_move");
			statistics7_exc = statistics7_stmt.executeQuery();
			if(statistics7_exc.isBeforeFirst()){
				statistics7_exc.next();
				returned=statistics7_exc.getFloat(1);
			}else{
				returned=0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(statistics7_exc!=null){
				try {
					statistics7_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(statistics7_stmt!=null){
				try {
					statistics7_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return returned;
	}

	public QueryReturn statistics8(){
		QueryReturn returned=null;
		PreparedStatement statistics8_stmt=null;
		ResultSet statistics8_exc=null;
		try {
			statistics8_stmt = conn.prepareStatement("select mossa.nome,mossa.idutente,frase.corpo from mossa inner join utente on mossa.idutente=utente.email inner join manche on mossa.idmanche=manche.idmanche and mossa.idpartita=manche.idpartita inner join frase on manche.idfrase=frase.idfrase where mossa.nome LIKE '%cns' and mossa.punti >= all( select mossa.punti from mossa where mossa.nome LIKE '%cns') limit 1");
			statistics8_exc = statistics8_stmt.executeQuery();
			if(statistics8_exc.isBeforeFirst()){
				statistics8_exc.next();
				returned=new QueryReturn(statistics8_exc.getString(1),statistics8_exc.getString(2),statistics8_exc.getString(3));
			}else{
				returned=new QueryReturn(0,"","");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(statistics8_exc!=null){
				try {
					statistics8_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(statistics8_stmt!=null){
				try {
					statistics8_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return returned;
	}

	public int statistics9(String email){
		int returned=0;
		PreparedStatement statistics9_stmt=null;
		ResultSet statistics9_exc=null;
		try {
			statistics9_stmt = conn.prepareStatement(" select count(*) from manche natural join partecipazione where partecipazione.email=? and tipo=0");
			statistics9_stmt.setString(1,email);
			statistics9_exc = statistics9_stmt.executeQuery();
			if(statistics9_exc.isBeforeFirst()){
				statistics9_exc.next();
				returned=statistics9_exc.getInt(1);
			}else{
				returned=0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(statistics9_exc!=null){
				try {
					statistics9_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(statistics9_stmt!=null){
				try {
					statistics9_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return returned;
	}

	public int statistics10(String email){
		int returned=0;
		PreparedStatement statistics10_stmt=null;
		ResultSet statistics10_exc=null;
		try {
			statistics10_stmt = conn.prepareStatement("select count(*) from partita inner join partecipazione on partita.idpartita=partecipazione.idpartita where partecipazione.email=? and tipo=0");
			statistics10_stmt.setString(1,email);
			statistics10_exc = statistics10_stmt.executeQuery();
			if(statistics10_exc.isBeforeFirst()){
				statistics10_exc.next();
				returned=statistics10_exc.getInt(1);
			}else{
				returned=0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(statistics10_exc!=null){
				try {
					statistics10_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(statistics10_stmt!=null){
				try {
					statistics10_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return returned;
	}

	public int statistics11(String email){
		int returned=0;
		PreparedStatement statistics11_stmt=null;
		ResultSet statistics11_exc=null;
		try {
			statistics11_stmt = conn.prepareStatement("select count(*) from manche natural join partecipazione where partecipazione.email=? and (tipo=1 or tipo=2)");
			statistics11_stmt.setString(1,email);
			statistics11_exc = statistics11_stmt.executeQuery();
			if(statistics11_exc.isBeforeFirst()){
				statistics11_exc.next();
				returned=statistics11_exc.getInt(1);
			}else{
				returned=0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(statistics11_exc!=null){
				try {
					statistics11_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(statistics11_stmt!=null){
				try {
					statistics11_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return returned;
	}

	public int statistics12(String email){
		int returned=0;
		PreparedStatement statistics12_stmt=null;
		ResultSet statistics12_exc=null;
		try {
			statistics12_stmt = conn.prepareStatement("select count(*) from partita inner join partecipazione on partita.idpartita=partecipazione.idpartita where partecipazione.email=? and (tipo=1 or tipo=2)");
			statistics12_stmt.setString(1,email);
			statistics12_exc = statistics12_stmt.executeQuery();
			if(statistics12_exc.isBeforeFirst()){
				statistics12_exc.next();
				returned=statistics12_exc.getInt(1);
			}else{
				returned=0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(statistics12_exc!=null){
				try {
					statistics12_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(statistics12_stmt!=null){
				try {
					statistics12_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return returned;
	}

	public int statistics13(String email){
		int returned=0;
		PreparedStatement statistics13_stmt=null;
		ResultSet statistics13_exc=null;
		try {
			statistics13_stmt = conn.prepareStatement("select count(*) from manche where manche.vincitore=?");
			statistics13_stmt.setString(1,email);
			statistics13_exc = statistics13_stmt.executeQuery();
			if(statistics13_exc.isBeforeFirst()){
				statistics13_exc.next();
				returned=statistics13_exc.getInt(1);
			}else{
				returned=0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(statistics13_exc!=null){
				try {
					statistics13_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(statistics13_stmt!=null){
				try {
					statistics13_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return returned;
	}

	public int statistics14(String email){
		int returned=0;
		PreparedStatement statistics14_stmt=null;
		ResultSet statistics14_exc=null;
		try {
			statistics14_stmt = conn.prepareStatement("select count(*) from partita where partita.winner=?");
			statistics14_stmt.setString(1,email);
			statistics14_exc = statistics14_stmt.executeQuery();
			if(statistics14_exc.isBeforeFirst()){
				statistics14_exc.next();
				returned=statistics14_exc.getInt(1);
			}else{
				returned=0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(statistics14_exc!=null){
				try {
					statistics14_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(statistics14_stmt!=null){
				try {
					statistics14_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return returned;
	}


	public int statistics15(String email){
		int returned=0;
		PreparedStatement statistics15_stmt=null;
		ResultSet statistics15_exc=null;
		try {
			statistics15_stmt = conn.prepareStatement("select round(avg(score),2) from (select sum(punteggio) as score,idpartita,vincitore,nome from manche inner join utente on manche.vincitore=utente.email	where vincitore=? group by(idpartita,vincitore,nome)) as sum_score");
			statistics15_stmt.setString(1,email);
			statistics15_exc = statistics15_stmt.executeQuery();
			if(statistics15_exc.isBeforeFirst()){
				statistics15_exc.next();
				returned=statistics15_exc.getInt(1);
			}else{
				returned=0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(statistics15_exc!=null){
				try {
					statistics15_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(statistics15_stmt!=null){
				try {
					statistics15_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return returned;
	}


	public int statistics16(String email){
		int returned=0;
		PreparedStatement statistics16_stmt=null;
		ResultSet statistics16_exc=null;
		try {
			statistics16_stmt = conn.prepareStatement("select round(avg(ntime),2) from (select count(*) as ntime,idpartita,idmanche,idutente from mossa where nome='CT' and idutente=? group by (idpartita,idmanche,idutente)) as count_ct");
			statistics16_stmt.setString(1,email);
			statistics16_exc = statistics16_stmt.executeQuery();
			if(statistics16_exc.isBeforeFirst()){
				statistics16_exc.next();
				returned=statistics16_exc.getInt(1);
			}else{
				returned=0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(statistics16_exc!=null){
				try {
					statistics16_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(statistics16_stmt!=null){
				try {
					statistics16_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return returned;
	}

	public int statistics17(String email){
		int returned=0;
		PreparedStatement statistics17_stmt=null;
		ResultSet statistics17_exc=null;
		try {
			statistics17_stmt = conn.prepareStatement("select round(avg(ntime),2) from (select count(*) as ntime,idpartita,idutente from mossa where nome='CT' and idutente=? group by (idpartita,idutente)) as count_ct");
			statistics17_stmt.setString(1,email);
			statistics17_exc = statistics17_stmt.executeQuery();
			if(statistics17_exc.isBeforeFirst()){
				statistics17_exc.next();
				returned=statistics17_exc.getInt(1);
			}else{
				returned=0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(statistics17_exc!=null){
				try {
					statistics17_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			if(statistics17_stmt!=null){
				try {
					statistics17_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return returned;
	}

	public int statistics18(String email){
		int returned=0;
		PreparedStatement statistics18_stmt=null;
		ResultSet statistics18_exc=null;
		try {
			statistics18_stmt = conn.prepareStatement("select round(avg(ntime),2) from (select count(*) as ntime,idpartita,idmanche,idutente from mossa where nome='Perde' and idutente=? group by (idpartita,idmanche,idutente)) as count_lose");
			statistics18_stmt.setString(1,email);
			statistics18_exc = statistics18_stmt.executeQuery();
			if(statistics18_exc.isBeforeFirst()){
				statistics18_exc.next();
				returned=statistics18_exc.getInt(1);
			}else{
				returned=0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(statistics18_exc!=null){
				try {
					statistics18_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(statistics18_stmt!=null){
				try {
					statistics18_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return returned;
	}

	public int statistics19(String email){
		int returned=0;
		PreparedStatement statistics19_stmt=null;
		ResultSet statistics19_exc=null;
		try {
			statistics19_stmt = conn.prepareStatement("select round(avg(ntime),2) from (select count(*) as ntime,idpartita,idutente from mossa where nome='Perde' and idutente=? group by (idpartita,idutente)) as count_lose");
			statistics19_stmt.setString(1,email);
			statistics19_exc = statistics19_stmt.executeQuery();
			if(statistics19_exc.isBeforeFirst()){
				statistics19_exc.next();
				returned=statistics19_exc.getInt(1);
			}else{
				returned=0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(statistics19_exc!=null){
				try {
					statistics19_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			if(statistics19_stmt!=null){
				try {
					statistics19_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}
		return returned;
	}

	public Hashtable<Integer,Phrases> get_phrases() {

		Hashtable<Integer,Phrases> phrases=new Hashtable<Integer,Phrases>();
		PreparedStatement phrases_stmt=null;
		ResultSet phrases_exc=null;
		try {
			phrases_stmt = conn.prepareStatement("select idfrase,tema,corpo from frase order by idfrase");
			phrases_exc = phrases_stmt.executeQuery();
			if(phrases_exc.isBeforeFirst()){
				while(phrases_exc.next()){
					phrases.put(phrases_exc.getInt(1),new Phrases(phrases_exc.getString(3),phrases_exc.getString(2)));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(phrases_exc!=null){
				try {
					phrases_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			if(phrases_stmt!=null){
				try {
					phrases_stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}

			disconnect();
		}
		return phrases;
	}

	public int update_Phrases(Object[][] phrases) {
		//controllo sui dati
		if(phrases[0].length!=3 || hasEmptyValues(phrases)){
			return 0;
		}

		int returned=0; 
		PreparedStatement clear_phrases_stmt=null;
		PreparedStatement restart_seq_stmt=null;
		PreparedStatement update_seq_stmt=null;
		PreparedStatement updatePhrase_stmt = null;
		int id=0;

		try {
			clear_phrases_stmt = conn.prepareStatement("DELETE FROM frase");
			clear_phrases_stmt.executeUpdate();
			restart_seq_stmt = conn.prepareStatement("ALTER SEQUENCE frase_idfrase_seq RESTART;");
			restart_seq_stmt.executeUpdate();
			update_seq_stmt = conn.prepareStatement("UPDATE frase SET idfrase = DEFAULT;");
			update_seq_stmt.executeUpdate();
			for(int i=0;i<phrases.length;i++){
				updatePhrase_stmt = conn.prepareStatement("INSERT INTO frase(idfrase,tema,corpo)VALUES (?, ? , ?)");
				if(phrases[i][0] instanceof String){
					id=Integer.parseInt((String) phrases[i][0]);
				}else{
					id=(int) phrases[i][0];
				}
				updatePhrase_stmt.setInt(1, id);
				updatePhrase_stmt.setString(2, (String)phrases[i][1]);
				updatePhrase_stmt.setString(3, (String)phrases[i][2]);
				returned = updatePhrase_stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(clear_phrases_stmt!=null){
				try {
					clear_phrases_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(restart_seq_stmt!=null){
				try {
					restart_seq_stmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}}
			if(update_seq_stmt!=null){
				try {
					update_seq_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			if(updatePhrase_stmt!=null){
				try {
					updatePhrase_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}
		return returned;
	}

	//controlla se ci sono campi vuoti nella matrice
	public boolean hasEmptyValues(Object[][] phrases){

		for(int i=0;i<phrases.length;i++)
			for(int j=0;j<phrases[0].length;j++)
				if(phrases[i][j].equals(""))
					return true;
		return false;

	}

	public String Md5Hash(String pswd){
		String passwordToHash = pswd;
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			//Add password bytes to digest
			md.update(passwordToHash.getBytes());
			//Get the hash's bytes
			byte[] bytes = md.digest();
			//This bytes[] has bytes in decimal format;
			//Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			//Get complete hashed password in hex format
			generatedPassword = sb.toString();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return generatedPassword;
	}

	public int get_Score(long idMatch, String nickname) {

		int score=0;
		PreparedStatement score_stmt=null;
		ResultSet score_exc=null;

		try {
			score_stmt = conn.prepareStatement("select sum(punti) from mossa where idutente=(select email from utente where nickname=?) and idpartita=? and idmanche=(select nmanche from partita where idpartita=?)");
			score_stmt.setString(1, nickname);
			score_stmt.setLong(2,idMatch);
			score_stmt.setLong(3,idMatch);
			score_exc = score_stmt.executeQuery();
			if(score_exc.isBeforeFirst()){
				while(score_exc.next()){
					score=score_exc.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(score_exc!=null){
				try {
					score_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			if(score_stmt!=null){
				try {
					score_stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}

			disconnect();
		}

		return score;
	}

	public int get_Bank(long idMatch, String nickname) {

		int scoretot=0;
		PreparedStatement scoretot_stmt=null;
		ResultSet scoretot_exc=null;

		try {

			scoretot_stmt = conn.prepareStatement("select sum(punteggio) from manche where vincitore=(select email from utente where nickname=?) and idpartita=?");
			scoretot_stmt.setString(1, nickname);
			scoretot_stmt.setLong(2,idMatch);
			scoretot_exc = scoretot_stmt.executeQuery();
			if(scoretot_exc.isBeforeFirst()){
				while(scoretot_exc.next()){
					scoretot=scoretot_exc.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{

			if(scoretot_exc!=null){
				try {
					scoretot_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			if(scoretot_stmt!=null){
				try {
					scoretot_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}


		return scoretot;
	}

	public int get_Nmanche(long idMatch) {
		int nmanche=0;
		PreparedStatement nmanche_stmt=null;
		ResultSet nmanche_exc=null;
		try {
			nmanche_stmt = conn.prepareStatement("select nmanche from partita where idpartita=?");
			nmanche_stmt.setLong(1,idMatch);
			nmanche_exc =nmanche_stmt.executeQuery();
			if(nmanche_exc.isBeforeFirst()){
				while(nmanche_exc.next()){
					nmanche=nmanche_exc.getInt(1);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(nmanche_exc!=null){
				try {
					nmanche_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			if(nmanche_stmt!=null){
				try {
					nmanche_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}

		return nmanche;
	}

	public String get_turn(long idMatch) {
		String turn=null;
		PreparedStatement nturn_stmt=null;
		ResultSet nturn_exc=null;
		try {
			nturn_stmt = conn.prepareStatement("select turno from partita where idpartita=?");
			nturn_stmt.setLong(1,idMatch);
			nturn_exc =nturn_stmt.executeQuery();
			if(nturn_exc.isBeforeFirst()){
				while(nturn_exc.next()){
					turn=nturn_exc.getString(1);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(nturn_exc!=null){
				try {
					nturn_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			if(nturn_stmt!=null){
				try {
					nturn_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}

		return turn;
	}

	public int get_jolly(long idMatch, String nickname) {
		int jolly=0;
		PreparedStatement jolly_stmt=null;
		ResultSet jolly_exc=null;

		try {

			jolly_stmt = conn.prepareStatement("select count(*) - (select count(*) from mossa where mossa.nome='UJ' and mossa.idpartita=? and idutente=(select email from utente where nickname=?)) as njolly from mossa where mossa.nome='TJ' and mossa.idpartita=? and idutente=(select email from utente where nickname=?) ");
			jolly_stmt.setLong(1,idMatch);
			jolly_stmt.setString(2,nickname);
			jolly_stmt.setLong(3,idMatch);
			jolly_stmt.setString(4,nickname);
			jolly_exc = jolly_stmt.executeQuery();
			if(jolly_exc.isBeforeFirst()){
				while(jolly_exc.next()){
					jolly=jolly_exc.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{

			if(jolly_exc!=null){
				try {
					jolly_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			if(jolly_stmt!=null){
				try {
					jolly_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
			disconnect();
		}


		return jolly;
	}


	public ArrayList<String> get_Letters(long idMatch) {
		ArrayList<String> get_Letters=new ArrayList<String>();
		PreparedStatement letters_stmt=null;
		ResultSet letters_exc=null;

		try {
			letters_stmt = conn.prepareStatement("select distinct nome from mossa where mossa.idpartita=? and (mossa.nome like '%cns' OR mossa.nome like '%voc') and idmanche=(select nmanche from partita where idpartita=?)");
			letters_stmt.setLong(1,idMatch);
			letters_stmt.setLong(2,idMatch);
			letters_exc =letters_stmt.executeQuery();
			if(letters_exc.isBeforeFirst()){
				while(letters_exc.next()){
					get_Letters.add(letters_exc.getString(1));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(letters_exc!=null){
				try {
					letters_exc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			if(letters_stmt!=null){
				try {
					letters_stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}

			disconnect();
		}

		return get_Letters;
	}

}


