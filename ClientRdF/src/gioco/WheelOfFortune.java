package gioco;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class WheelOfFortune {

	private String category;

	private static String phrase;

	private static Set<Character> GuessedLetters;

	private int points, bank;

	private static int manche;

	private int bonus;

	public WheelOfFortune() {
		points = 0;
		manche = 1;
		bonus = 0;
		bank = 0;
	}

	public void newPhrase(){    	
		ArrayList<String> phraseList = null;
		try {
			phraseList = Client.getPhrase();
			category = Client.getCategory(Client.idGame);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		
		String tmp = "";
		phrase = "";
		for (String s : phraseList){
			if(s.length() == 15) {
				phrase += s;
				tmp = " ";
			}else {
				for(int i = 0; i<(15/s.length()); i++) {
					tmp = " ";
				}
				tmp += s;
				while(tmp.length() < 15) {
						tmp += " ";
				}
				phrase += tmp;
				if(phrase.charAt(14) == ' ') {
					tmp = "";
				} else {
					tmp = " ";
				}
			}
		}
		phrase = phrase.toUpperCase();
		phrase = phrase.replaceAll("'", " ");
		
		System.out.println(phrase); //da togliere
		GuessedLetters = new HashSet<Character>();
	} 

	public int revealsLetter(char lettera) {
		if (GuessedLetters.contains(lettera)) {
			return 0;
		}

		GuessedLetters.add(lettera);

		int occorrenze = 0;
		for(int i=0; i<phrase.length(); i++) {
			if(lettera == phrase.charAt(i)) {
				occorrenze += 1;
			}
		}

		return occorrenze;
	}

	public boolean phraseResolved() {
		for (char c : phrase.toCharArray()) {
			if (!GuessedLetters.contains(c)) {
				return false;
			}
		}

		return true;
	}

	public void revealsPhrase() {
		for (char c : phrase.toCharArray()) {
			GuessedLetters.add(c);
		}
	}

	public void disableVowels() {
		GuessedLetters.add('A');
		GuessedLetters.add('E');
		GuessedLetters.add('I');
		GuessedLetters.add('O');
		GuessedLetters.add('U');
	}

	public String getCategory() {
		return category;
	}

	public String getPhrase() {
		return phrase;
	}

	public Set<Character> getGuessedLetters() {
		return GuessedLetters;
	}

	public int getScore() {
		return points;
	}
	
	public int getTotScore() {
		return bank;
	}

	public int getBonus() {
		return bonus;
	}

	public static int getManche() {
		return manche;
	}

	public void addManche() {
		manche = manche+1;
	}

	public void addBonus() {
		bonus = bonus+1;
	}

	public void removeBonus() {
		bonus = bonus-1;
	}

	public void addScore(int points) {
		this.points += points;
	}
	
	public void addTotScore(int points) {
		bank += points;
	}

	public void resetScore() {
		points = 0;
	}

	public void resetValues() {
		manche = 1;
		points = 0;
	}

	public static boolean isAllVowelsGuessed() {
		return !(phrase.contains("A") && !GuessedLetters.contains('A')
				|| phrase.contains("E") && !GuessedLetters.contains('E')
				|| phrase.contains("I") && !GuessedLetters.contains('I')
				|| phrase.contains("O") && !GuessedLetters.contains('O') 
				|| phrase.contains("U") && !GuessedLetters.contains('U'));
	}

	public static boolean isAllConsonantsGuessed() {
		return !(phrase.contains("Q") && !GuessedLetters.contains('Q')
				|| phrase.contains("R") && !GuessedLetters.contains('R')
				|| phrase.contains("T") && !GuessedLetters.contains('T')
				|| phrase.contains("Y") && !GuessedLetters.contains('Y') 
				|| phrase.contains("P") && !GuessedLetters.contains('P')
				|| phrase.contains("S") && !GuessedLetters.contains('S')
				|| phrase.contains("D") && !GuessedLetters.contains('D')
				|| phrase.contains("F") && !GuessedLetters.contains('F')
				|| phrase.contains("G") && !GuessedLetters.contains('G')
				|| phrase.contains("H") && !GuessedLetters.contains('H')
				|| phrase.contains("J") && !GuessedLetters.contains('J')
				|| phrase.contains("K") && !GuessedLetters.contains('K')
				|| phrase.contains("L") && !GuessedLetters.contains('L')
				|| phrase.contains("Z") && !GuessedLetters.contains('Z')
				|| phrase.contains("X") && !GuessedLetters.contains('X')
				|| phrase.contains("C") && !GuessedLetters.contains('C')
				|| phrase.contains("V") && !GuessedLetters.contains('V')
				|| phrase.contains("B") && !GuessedLetters.contains('B')
				|| phrase.contains("N") && !GuessedLetters.contains('N')
				|| phrase.contains("M") && !GuessedLetters.contains('M'));
	}

}
