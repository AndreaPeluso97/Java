package interfacciaGioco;


import java.awt.Dimension;
import java.awt.Font;
import java.rmi.RemoteException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
//import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gioco.Client;
import gioco.WheelOfFortune;

public class FirstPanel  extends JPanel{
	private static final long serialVersionUID=1;
	
	private static final Font FONT = new Font("Open Sans Light", Font.BOLD, 20);
	
	private WheelOfFortune game;
	
	private JLabel pointsL, bankL, nickL, bonusL;

    private JTextField pointsF, bankF, nickF, bonusF;
    
    public FirstPanel(WheelOfFortune game){
    	super();
    	
    	this.game = game;
    	
    	bonusL = new JLabel(new ImageIcon("immagini/bonus_img.png"));
    	bankL = new JLabel(new ImageIcon("immagini/depos.png"));
    	pointsL = new JLabel(new ImageIcon("immagini/points.png"));
    	nickL = new JLabel("Nickname:", JLabel.RIGHT);
    	nickL.setFont(FONT);
    	
    	bonusF = new JTextField("" + this.game.getBonus());
        bonusF.setEditable(false); 
        bankF = new JTextField("$" + this.game.getTotScore());
        bankF.setEditable(false);
    	pointsF = new JTextField("$ " + this.game.getScore());
    	pointsF.setEditable(false);
    	try {
    		String[] data = Client.getSt().getData(Client.email);
			nickF = new JTextField("" + data[2]);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
        nickF.setEditable(false);
        
        bonusF.setFont(FONT);
    	bankF.setFont(FONT);
    	nickF.setFont(FONT);
    	pointsF.setFont(FONT);
        
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(Box.createHorizontalStrut(50));
        add(nickL);
        add(Box.createHorizontalStrut(10));
        add(nickF);
        add(Box.createHorizontalStrut(100));
        add(bonusL);
        add(Box.createHorizontalStrut(10));
        add(bonusF);
        add(Box.createHorizontalStrut(100));
        add(bankL);
        add(Box.createHorizontalStrut(10));
        add(bankF);
        add(Box.createHorizontalStrut(100));
        add(pointsL);
        add(Box.createHorizontalStrut(10));
        add(pointsF);
        add(Box.createHorizontalStrut(20));
        setPreferredSize(new Dimension(900, 30));
    }
    
    public int getScore() {
    	return game.getScore();
    }
    
    public void aggiungiPunti(int score) {
        game.addScore(score);
        pointsF.setText("$" + game.getScore());
    }
    
    public void addTotScore(int score) {
    	game.addTotScore(score);
    	bankF.setText("$" + game.getTotScore());
    }

    public void resetPunti() {
        game.resetScore();
        pointsF.setText("$" + game.getScore());
    }

    public void resetValori() {
        game.resetScore();
        pointsF.setText("$" + game.getScore());
    }
    
    public void setNickname(String nick) {
    	nickF.setText(nick);
    }
    
    public void aggiungiBonus() {
    	game.addBonus();
    	bonusF.setText("" + game.getBonus());
    }
    
    public void diminuisciBonus() {
    	game.removeBonus();
    	bonusF.setText("" + game.getBonus());
    }
}

