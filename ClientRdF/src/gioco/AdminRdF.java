package gioco;


import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import interfacciaAccessoAdmin.LogFrameAdmin;
import interfacciaGioco.LoadingFrame;
import interfacciaGioco.ObserverFrame;
import interfacciaGioco.PlayerFrame;

import java.awt.Color;
import java.io.*;

public class AdminRdF implements Serializable,RemoteObserver{
	private static final long serialVersionUID=1;

	private static ServerInterface st;

	private static RemoteObserver adminStub;
	 
    private static Registry registry=null;
	 
	private static AdminRdF admin=null;

	public static String email;

	public static long idGame;

	public static int nturn;

	protected AdminRdF() throws RemoteException {}
	
	public void remoteUpdate(Object updateMsg) throws RemoteException{
		 observerNotify(updateMsg);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public void observerNotify(Object updateMsg) throws RemoteException {
		if(updateMsg instanceof ArrayList<?>) {
			if(((ArrayList) updateMsg).get(0).equals("manche")) {
				String winner = (String) ((ArrayList) updateMsg).get(1);
				String[] data = st.getData(winner);
				winner = data[2];
				((ArrayList) updateMsg).remove(1);
				ObserverFrame.increaseNManche();
				ObserverFrame.resetScore();
				if(ObserverFrame.getNickF1().getText().equals(winner)) {
					ObserverFrame.getBankF1().setText("" + (String) ((ArrayList) updateMsg).get(3));
				} else if(ObserverFrame.getNickF2().getText().equals(winner)) {
					ObserverFrame.getBankF2().setText("" + (String) ((ArrayList) updateMsg).get(3));
				} else {
					ObserverFrame.getBankF3().setText("" + (String) ((ArrayList) updateMsg).get(3));
				}				
			} else if(((ArrayList) updateMsg).get(0).equals("victory")) {
				String winner = (String) ((ArrayList) updateMsg).get(1);
				ObserverFrame.setTextArea("<html>Il giocatore "+ winner + " ha vinto la partita!<br/>Partita terminata, non ti resta che uscire</html>");
			} else if(((ArrayList) updateMsg).get(0).equals("frasi")) {
				Iterator<String> it = ((ArrayList<String>) updateMsg).iterator();
				while(it.hasNext()) {
					if(it.next().equals(email)) {
						PopUp pu = new PopUp("Errore", "Ops, non ci sono nuove frasi disponibili! \nSarà inviata una mail agli amministratori", Color.RED, null);
						pu.add();
						new PlayerFrame();
						LoadingFrame.frame.dispose();
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
					new ObserverFrame();
				}
		   }

		} else if (updateMsg instanceof Hashtable<?,?>) {
			Hashtable<Integer,String> data = (Hashtable<Integer, String>) updateMsg;
			Set<Integer> keys = data.keySet();
			String mail = null;
			for(Integer c: keys) {
				mail = data.get(c); 
				String[] dt = Client.getSt().getData(mail);
				if(ObserverFrame.getNickF1().getText().equals(dt[2])) {
					ObserverFrame.getNickF1().setForeground(Color.GREEN);
					ObserverFrame.getNickF2().setForeground(Color.BLACK);
					ObserverFrame.getNickF3().setForeground(Color.BLACK);
				} else if(ObserverFrame.getNickF2().getText().equals(dt[2])) {
					ObserverFrame.getNickF1().setForeground(Color.BLACK);
					ObserverFrame.getNickF2().setForeground(Color.GREEN);
					ObserverFrame.getNickF3().setForeground(Color.BLACK);
				} else if(ObserverFrame.getNickF3().getText().equals(dt[2])) {
					ObserverFrame.getNickF1().setForeground(Color.BLACK);
					ObserverFrame.getNickF2().setForeground(Color.BLACK);
					ObserverFrame.getNickF3().setForeground(Color.GREEN);
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
					} else if(((Move) updateMsg).getType().contains("all")) {
						String[] data = st.getData(((Move) updateMsg).getOwner());
						String nick = data[2];
		
						if(nick.equals(ObserverFrame.getNickF1().getText())) {
							ObserverFrame.setScoreF1(((Move) updateMsg).getPoints());
						} else if(nick.equals(ObserverFrame.getNickF2().getText())) {
							ObserverFrame.setScoreF2(((Move) updateMsg).getPoints());
						} else {
							ObserverFrame.setScoreF3(((Move) updateMsg).getPoints());
						}
						String n = ((Move) updateMsg).getType().substring(0,1);
						ObserverFrame.updatePanel(n);
					} else if (((Move) updateMsg).getType().equals("UJ")){
						String[] data = st.getData(((Move) updateMsg).getOwner());
						String nick = data[2];
						if(ObserverFrame.getNickF1().getText().equals(nick)) {
							ObserverFrame.addBonusF1(-1);
						} else if(ObserverFrame.getNickF2().getText().equals(nick)){
							ObserverFrame.addBonusF2(-1);
						} else {
							ObserverFrame.addBonusF3(-1);
						}
						ObserverFrame.setTextArea("<html>Il giocatore "+ nick + " ha usato un jolly!</html>");
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
						ObserverFrame.setTextArea("<html>Il giocatore "+ nick + " ha pescato PERDE, <br/>il suo punteggio scende a zero!</html>");
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
						ObserverFrame.setTextArea("<html>Il giocatore "+ nick + " ha pescato un jolly!</html>");
					} else {
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
						ObserverFrame.setTextArea("<html>Il giocatore "+ nick + " ha selezionato la lettera " + n + " <br/> e ha guadagnato $" + score + "!</html>");
					}
				}
			}
		}
	}
	
	public static void removeMe() throws RemoteException {
		try{
			System.out.println("Esco");
			st.removeObs(adminStub);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]){
		try{
			registry = LocateRegistry.getRegistry(1099);
			   st = (ServerInterface) registry.lookup("AddService");
			   admin=new AdminRdF();
			   adminStub = (RemoteObserver)UnicastRemoteObject.exportObject(admin, 0);
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception ex) {
			}

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run(){
					try {
						new LogFrameAdmin();
					} catch(Exception e) {}
				}
			});
		}catch(Exception e){
			System.out.println(e);
		}
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

	public static boolean loginAdmin(String email, String password) throws RemoteException{
		return st.loginAdmin(email, password);
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

	public static Hashtable<Long,String> gameList() throws RemoteException{
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

	public static void updateMove(int nManche,String email, int points, String move) throws RemoteException{
		  st.updateMove(nManche,email, points, move, idGame);
	}

	public static boolean logGame() throws RemoteException{
		if(st.joinMatch(email, idGame)) {			
			return true;
		}else {
			return false;
		}
	}

	public static String getCreator(long idGame) throws RemoteException{
		return st.getCreator(idGame);
	}

	public static void updateManche(int points) throws RemoteException{
		st.updateManche(email, idGame, points); 
	}
	
	public static void addObs() throws RemoteException {
		st.addObs(adminStub);
	}
	
	public static ServerInterface getSt() {
		return st;
	}
	
	public static RemoteObserver getClientStub() {
		return adminStub;
	}
	
	public static void exitLobby() throws RemoteException {
		st.exitLobby(email, idGame);
	}
	
} 
