package gioco;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import interfacciaAccesso.*;
import interfacciaGioco.LoadingFrame;
import interfacciaGioco.ObserverFrame;
import interfacciaGioco.PhrasePanel;
import interfacciaGioco.PlayerFrame;
import interfacciaGioco.WheelFrame;
import interfacciaGioco.WheelPanel;

import java.awt.Color;
import java.io.*;

public class Client implements Serializable,RemoteObserver{
	private static final long serialVersionUID=1;

	private static ServerInterface st;

	private static RemoteObserver clientStub;
	 
    private static Registry registry=null;
	 
	private static Client client=null;

	public static String email;
	
	public static boolean player=false;
	
	public static boolean observer=false;

	public static long idGame;

	public static int nturn;

	protected Client() throws RemoteException {}
	
	public void remoteUpdate(Object updateMsg) throws RemoteException{
		if(isPlayer()) {
			playerNotify(updateMsg);
		} else if(isObserver()) {
			observerNotify(updateMsg);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public void observerNotify(Object updateMsg) throws RemoteException {
		if(updateMsg instanceof ArrayList<?>) {
			if(((ArrayList) updateMsg).get(0).equals("manche")) {
				String winner = (String) ((ArrayList) updateMsg).get(1);
				String[] data = st.getData(winner);
				winner = data[2];
				((ArrayList) updateMsg).remove(1);
				String score = (String) ((ArrayList) updateMsg).get(1);
				((ArrayList) updateMsg).remove(1);
				ObserverFrame.increaseNManche();
				ObserverFrame.resetScore();
				if(ObserverFrame.getNickF1().getText().equals(winner)) {
					ObserverFrame.getBankF1().setText("$" + score);
				} else if(ObserverFrame.getNickF2().getText().equals(winner)) {
					ObserverFrame.getBankF2().setText("$" + score);
				} else {
					ObserverFrame.getBankF3().setText("$" + score);
				}
				ObserverFrame.newPhrase();
				int nManche = ObserverFrame.getNManche();
				if(nManche == 5) {
					ObserverFrame.setTextArea("Il giocatore "+ winner + " ha indovinato la frase!\nInizia l'ultima manche e cambia la frase");
				} else {
					ObserverFrame.setTextArea("Il giocatore "+ winner + " ha indovinato la frase!\nInizia la manche numero " + nManche +" e cambia la frase");
				}
			} else if(((ArrayList) updateMsg).get(0).equals("victory")) {
				String winner = (String) ((ArrayList) updateMsg).get(1);
				String[] data = st.getData(winner);
				winner = data[2];
				ObserverFrame.revealPhrase();
				ObserverFrame.setTextArea("Il giocatore "+ winner + " ha vinto la partita!\nPartita terminata, non ti resta che uscire");
			} else if(((ArrayList) updateMsg).get(0).equals("frasi")) {
				Iterator<String> it = ((ArrayList<String>) updateMsg).iterator();
				while(it.hasNext()) {
					if(it.next().equals(email)) {
						PopUp pu = new PopUp("Errore", "Ops, non ci sono nuove frasi disponibili! \nSarà inviata una mail agli amministratori", Color.RED, null);
						pu.add();
						new PlayerFrame();
						LoadingFrame.frame.dispose();
						observer = false;
					}
				}
			} else if(((ArrayList) updateMsg).get(0).equals("exitlobby")) {
				Iterator<String> it4 = ((ArrayList<String>) updateMsg).iterator();
				while(it4.hasNext()) {
					if(it4.next().equals(email)) {
						LoadingFrame.addPlayer(-1);
						LoadingFrame.updatePlayers();
					}
				}
			} else if(((ArrayList) updateMsg).get(0).equals("SR")) {
				String owner = (String) ((ArrayList) updateMsg).get(2);
				((ArrayList) updateMsg).remove(2);
				Iterator<String> it5 = ((ArrayList<String>) updateMsg).iterator();
				while(it5.hasNext()) {
					if(it5.next().equals(email)) {
						String s = (String) ((ArrayList) updateMsg).get(1);
						String[] data = st.getData(owner);
						owner = data[2];
						if(s.contains("PASSA")) {
							ObserverFrame.setTextArea("La ruota si è fermata su "+ s + "\nAdesso "+ owner +" deve decidere se usare il jolly...");
						} else {
							ObserverFrame.setTextArea("La ruota si è fermata su "+ s + "\nAdesso "+ owner +" deve decidere quale cononante selezionare...");
						}
						ObserverFrame.setTimerSecs(5);
						ObserverFrame.startTimer();
					}
				}
			}else {
				Iterator<String> it2 = ((ArrayList<String>) updateMsg).iterator();
				while(it2.hasNext()) {
					if(it2.next().equals(email)) {
						LoadingFrame.addPlayer(1);
						LoadingFrame.updatePlayers();
						break;
					} 
				}
				if(nPlayers(idGame) == 3) {
					new ObserverFrame(0);
				}
		   }
		} else if (updateMsg instanceof Hashtable<?,?>) {
			Hashtable<Integer,String> data = (Hashtable<Integer, String>) updateMsg;
			Set<Integer> keys = data.keySet();
			String mail = null;
			for(Integer c: keys) {
				mail = data.get(c); 
				String[] dt = Client.getSt().getData(mail);
				ObserverFrame.stopTimer();
				if(ObserverFrame.getNickF1().getText().equals(dt[2])) {
					ObserverFrame.getNickF1().setForeground(Color.GREEN);
					ObserverFrame.getNickF2().setForeground(Color.BLACK);
					ObserverFrame.getNickF3().setForeground(Color.BLACK);
					break;
				} else if(ObserverFrame.getNickF2().getText().equals(dt[2])) {
					ObserverFrame.getNickF1().setForeground(Color.BLACK);
					ObserverFrame.getNickF2().setForeground(Color.GREEN);
					ObserverFrame.getNickF3().setForeground(Color.BLACK);
					break;
				} else if(ObserverFrame.getNickF3().getText().equals(dt[2])) {
					ObserverFrame.getNickF1().setForeground(Color.BLACK);
					ObserverFrame.getNickF2().setForeground(Color.BLACK);
					ObserverFrame.getNickF3().setForeground(Color.GREEN);
					break;
				}
			}
		} else if (updateMsg instanceof Move) {
			ArrayList<String> players = (ArrayList<String>)((Move) updateMsg).getTo();
			for(String p: players) {
				if(email.equals(p)) {
					if(((Move) updateMsg).getType().equals("esce")) {
						String[] data = st.getData(((Move) updateMsg).getOwner());
						ObserverFrame.quit(data[2]);
						idGame = 0;
						removeMe();
						Client.getSt().exitObserver(Client.email, Client.idGame);
						observer = false;
					} else if(((Move) updateMsg).getType().contains("all")) {
						ObserverFrame.stopTimer();
						String[] data = st.getData(((Move) updateMsg).getOwner());
						String nick = data[2];
						int score = ((Move) updateMsg).getPoints();
		
						if(nick.equals(ObserverFrame.getNickF1().getText())) {
							ObserverFrame.setScoreF1(score);
						} else if(nick.equals(ObserverFrame.getNickF2().getText())) {
							ObserverFrame.setScoreF2(score);
						} else {
							ObserverFrame.setScoreF3(score);
						}
						String n = ((Move) updateMsg).getType().substring(0,1);
						ObserverFrame.updatePanel(n);
						ObserverFrame.setTextArea("Il giocatore "+ nick + " ha selezionato la lettera " + n + "\ne ha guadagnato $" + score + "!");
					} else if (((Move) updateMsg).getType().equals("UJ")){
						ObserverFrame.stopTimer();
						String[] data = st.getData(((Move) updateMsg).getOwner());
						String nick = data[2];
						if(ObserverFrame.getNickF1().getText().equals(nick)) {
							ObserverFrame.addBonusF1(-1);
						} else if(ObserverFrame.getNickF2().getText().equals(nick)){
							ObserverFrame.addBonusF2(-1);
						} else {
							ObserverFrame.addBonusF3(-1);
						}
						ObserverFrame.setTextArea("Il giocatore "+ nick + " ha usato un jolly!");
					} else if(((Move) updateMsg).getType().equals("Perde")) {
						String[] data = st.getData(((Move) updateMsg).getOwner());
						String nick = data[2];
						if(ObserverFrame.getNickF1().getText().equals(nick)) {
							ObserverFrame.setScoreF1(0);
						} else if(ObserverFrame.getNickF2().getText().equals(nick)) {
							ObserverFrame.setScoreF2(0);
						} else {
							ObserverFrame.setScoreF3(0);
						}
						ObserverFrame.setTextArea("Il giocatore "+ nick + " ha pescato PERDE, \nil suo punteggio scende a zero!");
					} else if(((Move) updateMsg).getType().equals("TJ")) {
						String[] data = st.getData(((Move) updateMsg).getOwner());
						String nick = data[2];
						if(ObserverFrame.getNickF1().getText().equals(nick)) {
							ObserverFrame.addBonusF1(1);
						} else if(ObserverFrame.getNickF2().getText().equals(nick)){
							ObserverFrame.addBonusF2(1);
						} else {
							ObserverFrame.addBonusF3(1);
						}
						ObserverFrame.setTextArea("Il giocatore "+ nick + " ha pescato un jolly!");
					} else if(((Move) updateMsg).getType().equals("Passa")) {
						String[] data = st.getData(((Move) updateMsg).getOwner());
						String nick = data[2];
						ObserverFrame.setTextArea("Il giocatore "+ nick + " ha pescato PASSA, \nnon avendo bonus perde il turno!");
					} else if(((Move) updateMsg).getType().equals("GR")){
						String[] data = st.getData(((Move) updateMsg).getOwner());
						String nick = data[2];
						ObserverFrame.setTextArea("Il giocatore "+ nick + " ha girato la ruota, \nla ruota gira...");
					} else if(((Move) updateMsg).getType().equals("TS")) {
						String[] data = st.getData(((Move) updateMsg).getOwner());
						String nick = data[2];
						ObserverFrame.setTextArea("Il giocatore "+ nick + " ha perso tempo, \nil turno passa al prossimo giocatore");
					} else {
						int score = ((Move) updateMsg).getPoints();
						String[] data = st.getData(((Move) updateMsg).getOwner());
						String nick = data[2];
						String n = ((Move) updateMsg).getType().substring(0,1);
						if(score != 0) {
							ObserverFrame.updatePanel(n);
							if(nick.equals(ObserverFrame.getNickF1().getText())) {
								ObserverFrame.setScoreF1(score);
							} else if(nick.equals(ObserverFrame.getNickF2().getText())) {
								ObserverFrame.setScoreF2(score);
							} else {
								ObserverFrame.setScoreF3(score);
							}
							ObserverFrame.setTextArea("Il giocatore "+ nick + " ha selezionato la lettera " + n + "\ne ha guadagnato $" + score + "!");
						} else {
							ObserverFrame.setTextArea("Il giocatore "+ nick + " ha sbagliato selezionando la lettera " + n + "\ne ha perso il turno!");
						}
					}
				}
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void playerNotify(Object updateMsg) throws RemoteException {
		if(updateMsg instanceof ArrayList<?>) {
			if(((ArrayList) updateMsg).get(0).equals("manche")) {
				String winner = (String) ((ArrayList) updateMsg).get(1);
				String[] data = st.getData(winner);
				winner = data[2];
				((ArrayList) updateMsg).remove(1);
				String score = (String) ((ArrayList) updateMsg).get(1);
				((ArrayList) updateMsg).remove(1);
				Iterator<String> it1 = ((ArrayList<String>) updateMsg).iterator();
				while(it1.hasNext()) {
					if(it1.next().equals(email)) {
						WheelPanel.updateMancheObs();
						PhrasePanel.resetPoints();
						if(PhrasePanel.getNick1().equals(winner)) {
							PhrasePanel.setBank1F(Integer.parseInt(score));
						} else {
							PhrasePanel.setBank2F(Integer.parseInt(score));
						}
					}
				}
			} else if(((ArrayList) updateMsg).get(0).equals("victory")) {
				String winner = (String) ((ArrayList) updateMsg).get(1);
				((ArrayList) updateMsg).remove(1);
				Iterator<String> it3 = ((ArrayList<String>) updateMsg).iterator();
				while(it3.hasNext()) {
					if(it3.next().equals(email)) {
						if(email.equals(winner)) {
							PopUp pu = new PopUp("Vittoria Partita", "Hai vinto la partita!", Color.GREEN, null);
							pu.add();
							WheelPanel.remoteClose();
						} else {
							PopUp pu = new PopUp("Game Over", "Ci dispiace, hai perso la partita", Color.RED, null);
							pu.add();
							WheelPanel.remoteClose();
						}
					}
				}
			} else if(((ArrayList) updateMsg).get(0).equals("frasi")) {
				Iterator<String> it = ((ArrayList<String>) updateMsg).iterator();
				while(it.hasNext()) {
					if(it.next().equals(email)) {
						PopUp pu = new PopUp("Frasi Terminate", "Ops, non ci sono nuove frasi disponibili! \nSarà inviata una mail agli amministratori", Color.RED, null);
						pu.add();
						new PlayerFrame();
						LoadingFrame.frame.dispose();
						player = false;
					}
				}
			} else if(((ArrayList) updateMsg).get(0).equals("exitlobby")) {
				Iterator<String> it4 = ((ArrayList<String>) updateMsg).iterator();
				while(it4.hasNext()) {
					if(it4.next().equals(email)) {
						LoadingFrame.addPlayer(-1);
						LoadingFrame.updatePlayers();
					}
				}
			} else if(((ArrayList) updateMsg).get(0).equals("SR")) {
				String owner = (String) ((ArrayList) updateMsg).get(2);
				((ArrayList) updateMsg).remove(2);
				Iterator<String> it6 = ((ArrayList<String>) updateMsg).iterator();
				while(it6.hasNext()) {
					if(it6.next().equals(email)) {
						String s = (String) ((ArrayList) updateMsg).get(1);
						String[] data = st.getData(owner);
						owner = data[2];
						if(s.contains("PASSA")) {
							WheelPanel.setWheelState("Il giocatore "+ owner +" ha pescato " + s +"\ne deve decidere se utilizzare il jolly");
						} else {
							WheelPanel.setWheelState("Il giocatore "+ owner +" ha pescato " + s +"\ne deve selezionare una consonante");
						}

					}
				}
			} else {
				Iterator<String> it2 = ((ArrayList<String>) updateMsg).iterator();
				while(it2.hasNext()) {
					if(it2.next().equals(email)) {
						LoadingFrame.addPlayer(1);
						LoadingFrame.updatePlayers();
						break;
					} 
				}
				if(nPlayers(idGame) == 3) {
					try {
						new WheelFrame();
						
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
						e.printStackTrace();
					}
				}
		   }

		} else if (updateMsg instanceof Hashtable<?,?>) {
			Hashtable<Integer,String> data = (Hashtable<Integer, String>) updateMsg;
			Set<Integer> keys = data.keySet();
			String mail = null;
			for(Integer c: keys) {
				mail = data.get(c);  
				if(email.equals(mail)) {
					WheelPanel.wheelstart(true);
					if(WheelOfFortune.isAllConsonantsGuessed()) {
						WheelPanel.unlockSolvePhrase(true);
					}
					nturn = c;
				} else {
					WheelPanel.bloccaTasti(false);
				}
			}
		} else if (updateMsg instanceof Move) {
			ArrayList<String> players = (ArrayList<String>)((Move) updateMsg).getTo();
			for(String p: players) {
				if(email.equals(p)) {
					if(((Move) updateMsg).getType().equals("esce")) {
						String[] data = st.getData(((Move) updateMsg).getOwner());
						WheelPanel.quit(data[2]);
						idGame = 0;
						st.removeObs(clientStub);
						player = false;
					} else if(((Move) updateMsg).getType().contains("all")) {
						String[] data = st.getData(((Move) updateMsg).getOwner());
						String nick = data[2];
		
						if(nick.equals(PhrasePanel.getNick1())) {
							PhrasePanel.setPoints1F(((Move) updateMsg).getPoints());
						} else {
							PhrasePanel.setPoints2F(((Move) updateMsg).getPoints());
						}
						String n = ((Move) updateMsg).getType().substring(0,1);
						WheelPanel.updatePanel(n);
						PopUp pu = new PopUp("Consonanti Terminate", "Attenzione, le consonanti sono terminate \nevita di selezionarle", Color.RED, null);
						pu.add();
					} else if (((Move) updateMsg).getType().equals("UJ")){
						String[] data = st.getData(((Move) updateMsg).getOwner());
						String nick = data[2];
						if(PhrasePanel.getNick1().equals(nick)) {
							PhrasePanel.addBonus1F(-1);
						} else {
							PhrasePanel.addBonus2F(-1);
						}
					} else if(((Move) updateMsg).getType().equals("Perde")) {
						String[] data = st.getData(((Move) updateMsg).getOwner());
						String nick = data[2];
						if(PhrasePanel.getNick1().equals(nick)) {
							PhrasePanel.resetPoints1F();
						} else {
							PhrasePanel.resetPoints2F();
						}
					} else if(((Move) updateMsg).getType().equals("Passa")) {
						String[] data = st.getData(((Move) updateMsg).getOwner());
						String nick = data[2];
						WheelPanel.setWheelState("Il giocatore "+ nick + " ha pescato PASSA e ha perso il turno");
					} else if(((Move) updateMsg).getType().equals("TJ")) {
						String[] data = st.getData(((Move) updateMsg).getOwner());
						String nick = data[2];
						if(PhrasePanel.getNick1().equals(nick)) {
							PhrasePanel.addBonus1F(1);
						} else {
							PhrasePanel.addBonus2F(1);
						}
					} else if(((Move) updateMsg).getType().equals("GR")){
						String[] data = st.getData(((Move) updateMsg).getOwner());
						String nick = data[2];
						WheelPanel.setWheelState("Il giocatore "+ nick +" ha girato la ruota\nla ruota gira...");
					} else if(((Move) updateMsg).getType().equals("TS")) {
						String[] data = st.getData(((Move) updateMsg).getOwner());
						String nick = data[2];
						WheelPanel.setWheelState("Il giocatore "+ nick +" ha perso tempo\nil turno passa al prossimo giocatore.");
					} else {
						String[] data = st.getData(((Move) updateMsg).getOwner());
						String nick = data[2];
						if(nick.equals(PhrasePanel.getNick1())) {
							PhrasePanel.setPoints1F(((Move) updateMsg).getPoints());
						} else {
							PhrasePanel.setPoints2F(((Move) updateMsg).getPoints());
						}
						WheelPanel.updatePanel(((Move) updateMsg).getType());
					}
				}
			}
		}
	}
	
	public static void removeMe() throws RemoteException {
		try{
			System.out.println("Esco");
			st.removeObs(clientStub);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]){
		try{
			registry = LocateRegistry.getRegistry(1099);
		    st = (ServerInterface) registry.lookup("AddService");
	    	client = new Client();
			clientStub = (RemoteObserver)UnicastRemoteObject.exportObject(client, 0);
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception ex) {
			}

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run(){
					try {
						new LogFrame();
					} catch(Exception e) {}
				}
			});
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public static boolean isPlayer() {
		return player;
	}
	
	public static boolean isObserver() {
		return observer;
	}
	
	public static void changeTurn() throws RemoteException {
		  st.c_turn(nturn,idGame,email,WheelOfFortune.getManche());
    }

	public static boolean addUser(String email, String nome, String cognome, String nickname) throws RemoteException{
		return st.addUser(email, nome, cognome, nickname, false);		
	}

	public static String victory(int points) throws RemoteException {
		return st.victory(email, idGame, points);
	}

	public static boolean login(String email, String password) throws RemoteException{
		return st.login(email, password);
	}

	public static boolean verCode(String email, String codice) throws RemoteException{
		return st.checkCode(email, codice);
	}

	public static boolean updatePasswordCode(String nome, String passw, String codice) throws RemoteException{
		return st.updateCode(nome, passw, codice);
	}


	public static String[] getData() throws RemoteException{
		return st.getData(email);
	}

	public static void updateProfile(String nome, String cognome, String nickname) throws RemoteException{
		st.updateProfile(email, nome, cognome, nickname);
	}

	public static boolean resetPassword(String email) throws RemoteException{
		return st.resetPassword(email);
	}

	public static long newGame() throws RemoteException{
		return idGame = st.createMatch(email);
	}

	public static LinkedHashMap<Long,String> gameList() throws RemoteException{
		return st.matchesList();
	}

	public static int nPlayers(long idGame) throws RemoteException{
		return st.nPlayers(idGame);
	}

	public static ArrayList<String> getPhrase() throws RemoteException{
		return st.getPhrase(idGame);
	}

	public static String getCategory(long idGame) throws RemoteException{
		return st.getTheme(idGame);
	}  

	public static void endGame() throws RemoteException{
		st.endMatch(idGame);
		idGame = 0;
		removeMe();
	}

	public static void updateMove(int nManche, String email, int points, String move) throws RemoteException{
		  st.updateMove(nManche,email, points, move, idGame);
	}

	public static boolean logGame() throws RemoteException{
		if(st.joinMatch(email, idGame)) {			
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean logObGame() throws RemoteException{
		if(st.joinObserver(email, idGame)) {			
			return true;
		}else {
			return false;
		}
	}
	
	public static ArrayList<String> getPlayerList() throws RemoteException{
		return st.allPlayerList(idGame);
	}

	public static String getCreator(long idGame) throws RemoteException{
		return st.getCreator(idGame);
	}

	public static void updateManche(int points) throws RemoteException{
		st.updateManche(email, idGame, points); 
	}
	
	public static void addObs() throws RemoteException {
		st.addObs(clientStub);
	}
	
	public static ServerInterface getSt() {
		return st;
	}
	
	public static RemoteObserver getClientStub() {
		return clientStub;
	}
	
	public static void exitLobby() throws RemoteException {
		st.exitLobby(email, idGame);
	}
	
	public static void exitLobbyObs() throws RemoteException {
		st.exitLobbyObs(email, idGame);
	}
	
	public static void exitObserver() throws RemoteException {
		st.exitObserver(email, idGame);
	}
	
	public static void notifyWheelStop(String s) throws RemoteException {
		st.notifyWheelStop(idGame, email, s);
	}
} 
