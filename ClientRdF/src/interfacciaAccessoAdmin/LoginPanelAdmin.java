package interfacciaAccessoAdmin;


import javax.swing.JPanel;

import interfacciaAccessoAdmin.LogFrameAdmin;
import interfacciaGiocoAdmin.AdminFrame;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import gioco.AdminRdF;

import java.rmi.RemoteException;

import javax.swing.JPasswordField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class LoginPanelAdmin extends JPanel{
	private static final long serialVersionUID=1;
	
	private static final Font FONT_1 = new Font("Open Sans Light", Font.BOLD, 20);
	
	private static final Font FONT_2 = new Font("Open Sans Light", Font.PLAIN, 20);
	
	private LoginFrameAdmin loginFrame;
	
	private JButton enter, back;
	
	private JLabel emailL, passwordL;
	
	private JTextField emailF;
	
	private JPasswordField passwordF;
	
	private ButtonListener3 buttonListener;
	
	public LoginPanelAdmin(LoginFrameAdmin frame) {
		super(new GridBagLayout());
		
		loginFrame = frame;
		
		buttonListener = new ButtonListener3();
		
		Border style = BorderFactory.createRaisedBevelBorder();
		Border border = BorderFactory.createEtchedBorder(Color.CYAN.darker(), Color.CYAN.darker());
		Border finalStyle = BorderFactory.createCompoundBorder(border, style);

		enter = new JButton("INVIA");
		enter.addActionListener(buttonListener);
		enter.setFont(FONT_1);
		enter.setContentAreaFilled(false);
		enter.setOpaque(true);
		enter.setBackground(Color.CYAN.brighter());
		enter.setBorder(finalStyle);
		 
		back = new JButton(new ImageIcon("immagini/arrowicon.png"));
		back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		back.addActionListener(buttonListener);
		back.setFont(FONT_2);
		back.setContentAreaFilled(false);
		back.setOpaque(true);
		back.setBackground(Color.CYAN.brighter());
		back.setBorder(finalStyle);
		
		
		emailL = new JLabel("Email:", JLabel.LEFT);
		emailL.setFont(FONT_1);
		emailL.setForeground(Color.GRAY);
		
		passwordL = new JLabel("Password:", JLabel.LEFT);
		passwordL.setFont(FONT_1);
		passwordL.setForeground(Color.GRAY);
		
		emailF = new JTextField("");
		emailF.setPreferredSize(new Dimension(200, 25));
		
		passwordF = new JPasswordField("");
		passwordF.setPreferredSize(new Dimension(200, 25));
		
		GridBagConstraints g = new GridBagConstraints();
		
		g.insets = new Insets(-30, 20, 20, 20);
		
		g.gridx = 0;
		g.gridy = 1;
		add(emailL, g);
		g.gridx = 1;
		g.gridy = 1;
		add(emailF, g);
		
		g.insets = new Insets(10, 20, 20, 20);
		
		g.gridx = 0;
		g.gridy = 2;
		add(passwordL, g);
		g.gridx = 1;
		g.gridy = 2;
		add(passwordF, g);
		
		g.insets = new Insets(20, 20, 20, 20);
		
		g.gridx = 0;
		g.gridy = 3;
		add(back, g);
		
		g.insets = new Insets(20, 20, 20, 20);
		
		g.gridx = 1;
		g.gridy = 3;
		add(enter, g);
		
		setBackground(Color.BLUE.darker());
		setPreferredSize(new Dimension(600, 400));
	}
	
	private class ButtonListener3 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton)e.getSource();
			if(source == enter) {
				try {
					boolean acc = false;
					if(emailF.getText() != "" && (String.valueOf(passwordF.getPassword())) != "") {
						
						acc = AdminRdF.loginAdmin(emailF.getText(), String.valueOf(passwordF.getPassword()));
						
					}
					AdminRdF.email = emailF.getText();
					emailF.setText("");
					passwordF.setText("");
					if(acc) {
						loginFrame.dispose();
						new AdminFrame();
					} else 
						AdminRdF.email = "";
									
				} catch(RemoteException ex) {}	
			} else if(source == back) {
				loginFrame.dispose();
				new LogFrameAdmin();
			}

		}
	}
}
