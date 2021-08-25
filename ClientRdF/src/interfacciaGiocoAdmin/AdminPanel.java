package interfacciaGiocoAdmin;


import javax.swing.JPanel;
import javax.swing.border.Border;

import gioco.AdminRdF;

import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class AdminPanel extends JPanel{
	private static final long serialVersionUID=1;
	
	private static final Font FONT = new Font("Open Sans Light", Font.BOLD, 20);
	
	private ButtonListener4 buttonListener;
	
	private AdminFrame playerFrame;
	
	private JLabel imgL;
	
	private Image img;
	
	private JButton logGame, editProfile, statsList, editPassw, phraseManagement;
	
	public AdminPanel(AdminFrame frame) {
		super(new GridBagLayout());
		
		playerFrame = frame;
		
		buttonListener = new ButtonListener4();
		
		Border style = BorderFactory.createRaisedBevelBorder();
		Border border = BorderFactory.createEtchedBorder(Color.CYAN.darker(), Color.CYAN.darker());
		Border finalStyle = BorderFactory.createCompoundBorder(border, style);
		
		try {
			img = ImageIO.read(new File("immagini/RDF.png"));
		}catch (IOException e) {}
		
		imgL = new JLabel(new ImageIcon(img));
		imgL.setOpaque(true);
		
		editPassw = new JButton("MODIFICA PASSWORD");
		editPassw.setForeground(new Color(96, 24, 149));
		editPassw.addActionListener(buttonListener);
		editPassw.setFont(FONT);
		editPassw.setContentAreaFilled(false);
		editPassw.setOpaque(true);
		editPassw.setBackground(Color.YELLOW);
		editPassw.setBorder(finalStyle);
		
		logGame = new JButton("ELENCO PARTITE");
		logGame.setForeground(new Color(96, 24, 149));
		logGame.addActionListener(buttonListener);
		logGame.setFont(FONT);
		logGame.setContentAreaFilled(false);
		logGame.setOpaque(true);
		logGame.setBackground(Color.YELLOW);
		logGame.setBorder(finalStyle);
		
		editProfile = new JButton("MODIFICA PROFILO");
		editProfile.setForeground(new Color(96, 24, 149));
		editProfile.addActionListener(buttonListener);
		editProfile.setFont(FONT);
		editProfile.setContentAreaFilled(false);
		editProfile.setOpaque(true);
		editProfile.setBackground(Color.YELLOW);
		editProfile.setBorder(finalStyle);
		
		statsList = new JButton("STATISTICHE DI GIOCO");
		statsList.setForeground(new Color(96, 24, 149));
		statsList.addActionListener(buttonListener);
		statsList.setFont(FONT);
		statsList.setContentAreaFilled(false);
		statsList.setOpaque(true);
		statsList.setBackground(Color.YELLOW);
		statsList.setBorder(finalStyle);
		
		phraseManagement = new JButton("GESTIONE FRASI");
		phraseManagement.setForeground(new Color(96, 24, 149));
		phraseManagement.addActionListener(buttonListener);
		phraseManagement.setFont(FONT);
		phraseManagement.setContentAreaFilled(false);
		phraseManagement.setOpaque(true);
		phraseManagement.setBackground(Color.YELLOW);
		phraseManagement.setBorder(finalStyle);
		
		GridBagConstraints g = new GridBagConstraints();
		
		g.insets = new Insets(-30, 20, 20, 20);
		
		g.gridy = 1;
		add(logGame, g);
		
		g.insets = new Insets(10, 20, 20, 20);
		
		g.gridy = 2;
		add(editProfile, g);
		
		g.gridy = 3;
		add(editPassw, g);
		
		g.gridy = 4;
		add(statsList, g);
		
		g.gridy = 5;
		add(phraseManagement, g);
		
		setPreferredSize(new Dimension(600, 550));
	}
	
	private class ButtonListener4 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton)e.getSource();
			if(source == logGame) {
				playerFrame.dispose();
				
				try {
					new GameListFrameAdmin(AdminRdF.gameList());
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			} else if(source == editProfile) {
				playerFrame.dispose();
				try {
					new EditFrameAdmin(AdminRdF.getData());
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			} else if (source == editPassw) {
				playerFrame.dispose();
				new EditPasswFrameAdmin();
			} else if(source == phraseManagement){
				playerFrame.dispose();
				new PhraseMFrame();
			}

		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, -150, 0, null);
	}
}
