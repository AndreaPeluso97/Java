package interfacciaGioco;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.border.*;

import gioco.Client;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ObserverPhrasePanel  extends JPanel{
	private static final long serialVersionUID=1;
	
	private static final int LARGHEZZA = 35, ALTEZZA = 45,
            SPAZIO = 2, LARGHEZZA_FRASE = 15 * LARGHEZZA,
            ALTEZZA_FRASE = 4 * ALTEZZA;

    private static JLabel category;
    
    private static String phrase;

	private static String categ;
    
    private static Set<Character> GuessedLetters;
    
    public ObserverPhrasePanel() {
    	super(); 
    	
    	newPhrase();
    	
    	category = new JLabel("Categoria: "+ categ);
    	category.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
    	category.setForeground(Color.GREEN.darker());
    	category.setLocation(450, 0);
    	Border raisedbevel = BorderFactory.createRaisedBevelBorder();
    	Border loweredbevel = BorderFactory.createLoweredBevelBorder();
    	Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
    	category.setBorder(compound);
    	
        add(Box.createVerticalStrut(470));
        add(category);
        
        setPreferredSize(new Dimension(1000, 280));
        repaint();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Disegna lo spazio per ogni lettera
        for (int i = 0; i < phrase.length(); ++i) {
            int row = i / 15; //ci sono righe da 15 colonne 
            int col = i % 15; //ci sono 15 colonne 

            paintLetterBox(g, row, col, phrase.charAt(i) == ' ');
            
         // Disegna la lettera se è stata indovinata
            if (GuessedLetters.contains(phrase.charAt(i))) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Impatto", Font.PLAIN, 35));
                drawLetter(g, ("" + phrase.charAt(i)).toUpperCase(), row, col);
            }
        }
    }
    
    public void newPhrase(){    	
		ArrayList<String> phraseList = null;
		GuessedLetters = new HashSet<Character>();
		try {
			phraseList = Client.getPhrase();
			categ = Client.getCategory(Client.idGame);
			ArrayList<String> letters = Client.getSt().getLetters(Client.idGame);
			if(letters.size()!=0) {
				for(String s: letters) {
					char c = s.charAt(0);
					GuessedLetters.add(c);
				}
			}
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
		repaint();
	}
    
    public void newManche() {
    	newPhrase();
    	category.setText("Categoria: "+categ);
    }
    
    public static void revealsLetter(char c) {
    	GuessedLetters.add(c);
    }
    
    public static void revealsPhrase() {
		for (char c : phrase.toCharArray()) {
			GuessedLetters.add(c);
		}
	}

    private void paintLetterBox(Graphics g, int row, int col, boolean b) {
        g.setColor(b ? Color.GREEN.darker() : Color.WHITE);
        g.fillRect((getWidth() - LARGHEZZA_FRASE) / 2 + col
            * (LARGHEZZA + SPAZIO), (getHeight() - ALTEZZA_FRASE) / 3
            + row * (ALTEZZA + SPAZIO), LARGHEZZA, ALTEZZA);
    }

    private void drawLetter(Graphics g, String str, int row, int col) {
        g.drawString(str, (getWidth() - LARGHEZZA_FRASE) / 2 + col
            * (LARGHEZZA + SPAZIO) + LARGHEZZA / 8,
            (getHeight() - ALTEZZA_FRASE) / 3 + (row + 1)
                * (ALTEZZA + SPAZIO) - ALTEZZA / 6); 
    }
}
