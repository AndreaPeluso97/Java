package interfacciaGioco;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.border.*;

import gioco.Client;
import gioco.WheelOfFortune;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;;

public class PhrasePanel  extends JPanel{
	private static final long serialVersionUID=1;
	
	private static final Font FONT = new Font("Open Sans Light", Font.BOLD, 20);
	
	private static final int LARGHEZZA = 35, ALTEZZA = 45,
            SPAZIO = 2, LARGHEZZA_FRASE = 15 * LARGHEZZA,
            ALTEZZA_FRASE = 4 * ALTEZZA;
	
	private WheelOfFortune game;
	
	private JLabel points1L, points2L, bank1L, bank2L, bonus1L, bonus2L ,nick1L, nick2L; 
	
	private static JTextField points1F, points2F, bank1F, bank2F, nick1F, nick2F, bonus1F, bonus2F;
	
    private JLabel category;
    
    private ArrayList<String> otherPlayers;
    
    public PhrasePanel(WheelOfFortune g) {
    	super();
    	
    	this.game = g;
    	try {
    		String[] data = Client.getSt().getData(Client.email);
			String nick = data[2];
			otherPlayers = Client.getSt().otherPlayerList(Client.idGame, nick);
		} catch (RemoteException e) {
			e.printStackTrace();
		}    	
    	
    	bonus1L = new JLabel(new ImageIcon("immagini/bonus_img.png"));
    	bank1L = new JLabel(new ImageIcon("immagini/depos.png"));
    	points1L = new JLabel(new ImageIcon("immagini/points.png"));
    	nick1L = new JLabel("Player:", JLabel.RIGHT);
    	nick1L.setFont(FONT);
    	
    	bonus2L = new JLabel(new ImageIcon("immagini/bonus_img.png"));
    	bank2L = new JLabel(new ImageIcon("immagini/depos.png"));
    	points2L = new JLabel(new ImageIcon("immagini/points.png"));
    	nick2L = new JLabel("Player:", JLabel.RIGHT);
    	nick2L.setFont(FONT);    	
    	
    	category = new JLabel();
    	category.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
    	category.setForeground(Color.GREEN.darker());
    	category.setLocation(450, 0);
    	Border raisedbevel = BorderFactory.createRaisedBevelBorder();
    	Border loweredbevel = BorderFactory.createLoweredBevelBorder();
    	Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
    	category.setBorder(compound);
    	
    	bonus1F = new JTextField("0");
    	bonus1F.setPreferredSize(new Dimension(51, 30));
        bonus1F.setEditable(false); 
        bank1F = new JTextField("$0");
        bank1F.setEditable(false);
    	points1F = new JTextField("$0");
    	points1F.setEditable(false);
    	nick1F = new JTextField("" + otherPlayers.get(0)); //il server deve tornare i nomi degli altri 2 players --> qui metto il nick di uno dei due
    	nick1F.setMinimumSize(new Dimension(120,30));
    	nick1F.setMaximumSize(new Dimension(120, 30));
    	nick1F.setPreferredSize(new Dimension(120,30));
    	nick1F.setEditable(false);
        
        bonus1F.setFont(FONT);
    	bank1F.setFont(FONT);
    	nick1F.setFont(FONT);
    	points1F.setFont(FONT);
    	
    	bonus2F = new JTextField("0");
        bonus2F.setEditable(false); 
        bank2F = new JTextField("$0");
        bank2F.setEditable(false);
    	points2F = new JTextField("$0");
    	points2F.setEditable(false);
    	nick2F = new JTextField("" + otherPlayers.get(1)); //il server deve tornare i nomi degli altri 2 players --> qui metto il nick di uno dei due
    	nick2F.setMinimumSize(new Dimension(120,30));
    	nick2F.setMaximumSize(new Dimension(120, 30));
    	nick2F.setPreferredSize(new Dimension(120,30));
    	nick2F.setEditable(false);
    	
    	bonus2F.setFont(FONT);
    	bank2F.setFont(FONT);
    	nick2F.setFont(FONT);
    	points2F.setFont(FONT);
    	
    	Box player1LabelBox = Box.createVerticalBox(); //blocco di JLabel per il player1
    	player1LabelBox.add(nick1L);
    	player1LabelBox.add(Box.createVerticalStrut(10));
    	player1LabelBox.add(bonus1L);
    	player1LabelBox.add(Box.createVerticalStrut(10));
    	player1LabelBox.add(bank1L);
    	player1LabelBox.add(Box.createVerticalStrut(10));
    	player1LabelBox.add(points1L);
    	
    	Box player1TextBox = Box.createVerticalBox(); //blocco di JTextField per il player1
    	player1TextBox.add(nick1F);
    	player1TextBox.add(Box.createVerticalStrut(10));
    	player1TextBox.add(bonus1F);
    	player1TextBox.add(Box.createVerticalStrut(10));
    	player1TextBox.add(bank1F);
    	player1TextBox.add(Box.createVerticalStrut(10));
    	player1TextBox.add(points1F);
    	
    	
    	Box player2LabelBox = Box.createVerticalBox(); //blocco di JLabel per il player2
    	player2LabelBox.add(nick2L);
    	player2LabelBox.add(Box.createVerticalStrut(10));
    	player2LabelBox.add(bonus2L);
    	player2LabelBox.add(Box.createVerticalStrut(10));
    	player2LabelBox.add(bank2L);
    	player2LabelBox.add(Box.createVerticalStrut(10));
    	player2LabelBox.add(points2L);
    	
    	Box player2TextBox = Box.createVerticalBox(); //blocco di JTextField per il player2
    	player2TextBox.add(nick2F);
    	player2TextBox.add(Box.createVerticalStrut(10));
    	player2TextBox.add(bonus2F);
    	player2TextBox.add(Box.createVerticalStrut(10));
    	player2TextBox.add(bank2F);
    	player2TextBox.add(Box.createVerticalStrut(10));
    	player2TextBox.add(points2F);
    	
    	Box playersBox = Box.createHorizontalBox();
    	playersBox.add(Box.createHorizontalStrut(10));
    	playersBox.add(player1LabelBox);
    	playersBox.add(Box.createHorizontalStrut(10));
    	playersBox.add(player1TextBox);
    	playersBox.add(Box.createHorizontalStrut(590));
    	playersBox.add(player2LabelBox);
    	playersBox.add(Box.createHorizontalStrut(10));
    	playersBox.add(player2TextBox);
    	
    	Box categoryBox = Box.createHorizontalBox();
    	categoryBox.add(category);
    	
    	Box finalBox = Box.createVerticalBox();
        finalBox.add(Box.createVerticalStrut(20));
        finalBox.add(playersBox);
        finalBox.add(Box.createVerticalStrut(40));
        finalBox.add(categoryBox);
        
        add(finalBox);
        setPreferredSize(new Dimension(1000, 265));
    }
    
    public void newGame() {
    	game.newPhrase();
        category.setText("Categoria: " + game.getCategory());
        
        repaint();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        String frase = game.getPhrase();         

        // Disegna lo spazio per ogni lettera
        for (int i = 0; i < frase.length(); ++i) {
            int row = i / 15; //ci sono righe da 15 colonne 
            int col = i % 15; //ci sono 15 colonne 

            paintLetterBox(g, row, col, frase.charAt(i) == ' ');

            // Disegna la lettera se è stata indovinata
            if (game.getGuessedLetters().contains(frase.charAt(i))) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Impatto", Font.PLAIN, 35));
                drawLetter(g, ("" + frase.charAt(i)).toUpperCase(), row, col);
            }
        }
    }

    private void paintLetterBox(Graphics g, int row, int col, boolean b) {
        g.setColor(b ? Color.GREEN.darker() : Color.WHITE);
        g.fillRect((990 - LARGHEZZA_FRASE) / 2 + col
            * (LARGHEZZA + SPAZIO), (getHeight() - ALTEZZA_FRASE) / 3
            + row * (ALTEZZA + SPAZIO), LARGHEZZA, ALTEZZA);
    }

    private void drawLetter(Graphics g, String str, int row, int col) {
        g.drawString(str, (990 - LARGHEZZA_FRASE) / 2 + col
            * (LARGHEZZA + SPAZIO) + LARGHEZZA / 8,
            (getHeight() - ALTEZZA_FRASE) / 3 + (row + 1)
                * (ALTEZZA + SPAZIO) - ALTEZZA / 6); 
    }
    
    public static void resetPoints() {
    	resetPoints1F();
    	resetPoints2F();
    }
    
    public static String getNick1(){
    	return nick1F.getText();
    }
    
    public static String getNick2(){
    	return nick2F.getText();
    }
    
    public static void resetPoints1F() {
    	points1F.setText("$0");
    }
    
    public static void resetPoints2F() {
    	points2F.setText("$0");
    }

	public static void setPoints1F(int points1f) {
		int points = Integer.parseInt(points1F.getText().substring(1));
		points1F.setText("$"+ (points + points1f));
	}

	public static void setPoints2F(int points2f) {
		int points = Integer.parseInt(points2F.getText().substring(1));
		points2F.setText("$"+ (points + points2f));
	}

	public static void setBank1F(int bank1f) {
		int points = Integer.parseInt(bank1F.getText().substring(1));
		bank1F.setText("$" + (points + bank1f));
	}

	public static void setBank2F(int bank2f) {
		int points = Integer.parseInt(bank2F.getText().substring(1));
		bank2F.setText("$"+ (points + bank2f));
	}

	public static void addBonus1F(int i) {
		int b = Integer.parseInt(bonus1F.getText());
		bonus1F.setText("" + (b+i));;
	}

	public static void addBonus2F(int i) {
		int b = Integer.parseInt(bonus2F.getText());
		bonus2F.setText("" + (b+i));;
	}
}
