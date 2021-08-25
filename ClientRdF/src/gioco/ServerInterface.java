package gioco;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;

public interface ServerInterface extends Remote 
{
	
	public void addObs(RemoteObserver o) throws RemoteException;
	
	public void removeObs(RemoteObserver o) throws RemoteException;

	public boolean addUser(String email,String name, String surname, String nickname,boolean isAdmin) throws RemoteException;

	public boolean login(String email,String passw) throws RemoteException;
	
	public boolean checkCode(String email,String code) throws RemoteException;

	public boolean updateCode(String email,String passw,String code) throws RemoteException;

	public void updateProfile(String email,String name,String surname,String nickname) throws RemoteException;

	public String[] getData(String email) throws RemoteException;  

	public long createMatch(String email) throws RemoteException;  

	public LinkedHashMap<Long, String> matchesList() throws RemoteException;

	public boolean resetPassword(String email) throws RemoteException;  

	public int nPlayers(long idMatch) throws RemoteException;

	public ArrayList<String> getPhrase(long id) throws RemoteException;

	public String getTheme(long id) throws RemoteException;

	public void endMatch(long idMatch) throws RemoteException;

	public void updateMove(int nmanche,String email, int score, String move,long idMatch) throws RemoteException;

	public boolean joinMatch(String email,long idMatch) throws RemoteException;

	public String getCreator(long idMatch) throws RemoteException;

	public void updateManche(String email,long idManche,int score) throws RemoteException;
		
    public void c_turn(int nTurn,long idMatch,String email,int nmanche) throws RemoteException;
    
    public String getFirstTurn(long idMatch) throws RemoteException;
    
    public int getNTurn(long idMatch) throws RemoteException;
    	
	public String victory(String email,long idMatch,int score) throws RemoteException;
	
	public int exitLobby(String email,long idMatch) throws RemoteException;
	
	public int exitObserver(String email,long idMatch) throws RemoteException;

	public boolean joinObserver(String email,long idMatch) throws RemoteException;

	public boolean loginAdmin(String email,String passw) throws RemoteException;
	
	public Hashtable<Integer,Phrases> getPhrases() throws RemoteException;

    public QueryReturn statistics1() throws RemoteException;

    public QueryReturn statistics2() throws RemoteException;

    public QueryReturn statistics3() throws RemoteException;
    
    public QueryReturn statistics4() throws RemoteException;

    public QueryReturn statistics5() throws RemoteException;
    
    public QueryReturn statistics6() throws RemoteException;
    
    public float statistics7() throws RemoteException;

    public QueryReturn statistics8() throws RemoteException;

    public int statistics9(String email) throws RemoteException;

    public int statistics10(String email) throws RemoteException;
    
    public int statistics11(String email) throws RemoteException;

    public int statistics12(String email) throws RemoteException;
    
    public int statistics13(String email) throws RemoteException;
    
    public int statistics14(String email) throws RemoteException;
    
    public int statistics15(String email) throws RemoteException;

    public int statistics16(String email) throws RemoteException;
   
    public int statistics17(String email) throws RemoteException;
    
    public int statistics18(String email) throws RemoteException;
    
    public int statistics19(String email) throws RemoteException;

    public ArrayList<String> otherPlayerList(long idmatch,String nickname) throws RemoteException;
    
    public ArrayList<String> allPlayerList(long idMatch) throws RemoteException;
    
    public int updatePhrases(Object[][] phrases) throws RemoteException;
    
    public int getScore(long idMatch,String nickname) throws RemoteException;
    
    public int getBank(long idMatch,String nickname) throws RemoteException;
    
    public int getNmanche(long idMatch) throws RemoteException;
    
    public int getJolly(long idMatch,String nickname) throws RemoteException;

    public ArrayList<String> getLetters(long idMatch) throws RemoteException;
        
    public int exitLobbyObs(String email, long idMatch) throws RemoteException;
    
    public void notifyWheelStop(long idMatch,String email,String score) throws RemoteException;
    
    public LinkedHashMap<String, Integer>  ranking(long idMatch) throws RemoteException;
    
    public int removePhrase(int id) throws RemoteException;
    
    public int updatePhrase(int id,String tema,String corpo) throws RemoteException;
    
    public int insertPhrase(String tema,String corpo) throws RemoteException;
    
  }
