package interfacciaGiocoAdmin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.border.*;
import gioco.WheelOfFortune;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;;

public class PhrasePanelAdmin  extends JPanel{
	private static final long serialVersionUID=1;
	
	private static final int LARGHEZZA = 35, ALTEZZA = 45,
            SPAZIO = 2, LARGHEZZA_FRASE = 15 * LARGHEZZA,
            ALTEZZA_FRASE = 4 * ALTEZZA;
	
	private WheelOfFortune gioco;

    private JLabel categoria;
    
    public PhrasePanelAdmin(WheelOfFortune gioco) {
    	super();
    	
    	this.gioco = gioco;
    	categoria = new JLabel();
    	categoria.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
    	categoria.setForeground(Color.GREEN.darker());
    	Border raisedbevel = BorderFactory.createRaisedBevelBorder();
    	Border loweredbevel = BorderFactory.createLoweredBevelBorder();
    	Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
    	categoria.setBorder(compound);
    	
    	Box labelBox = Box.createVerticalBox();
        labelBox.add(Box.createVerticalStrut(215));
        labelBox.add(categoria);

        add(labelBox);
        setPreferredSize(new Dimension(900, 260));
    }
    
    public void nuovaPartita() {
    	gioco.newPhrase();
        categoria.setText("CATEGORIA: " + gioco.getCategory());
        
        repaint();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        String frase = gioco.getPhrase();         

        // Disegna lo spazio per ogni lettera
        for (int i = 0; i < frase.length(); ++i) {
            int row = i / 15; //ci devono essere righe da 15 colonne 
            int col = i % 15; //ci sono 15 colonne 

            paintLetterBox(g, row, col, frase.charAt(i) == ' ');

            // Disegna la lettera se è stata indovinata
            if (gioco.getGuessedLetters().contains(frase.charAt(i))) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Impatto", Font.PLAIN, 35));
                drawLetter(g, ("" + frase.charAt(i)).toUpperCase(), row, col);
            }
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
