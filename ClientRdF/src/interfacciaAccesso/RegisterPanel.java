package interfacciaAccesso;

import javax.swing.JPanel;
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

import gioco.Client;

import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class RegisterPanel extends JPanel{
	private static final long serialVersionUID=1;

	private static final Font FONT_1 = new Font("Open Sans Light", Font.BOLD, 20);
	
	private static final Font FONT_2 = new Font("Open Sans Light", Font.PLAIN, 20); 

	private RegisterFrame registerFrame;
	
	private JButton enter, back;

	private JLabel emailL, nameL, surnameL, nicknameL;

	private JTextField emailF, nameF, surnameF, nicknameF;

	private ButtonListener2 buttonListener;

	public RegisterPanel(RegisterFrame frame) {
		super(new GridBagLayout());
		
		registerFrame = frame;
		
		buttonListener = new ButtonListener2();
		
		Border style = BorderFactory.createRaisedBevelBorder();
		Border border = BorderFactory.createEtchedBorder(Color.CYAN.darker(), Color.CYAN.darker());
		Border finalStyle = BorderFactory.createCompoundBorder(border, style);

		enter = new JButton("INVIA");
		enter.addActionListener(buttonListener);
		enter.setFont(FONT_1);
		enter.setContentAreaFilled(false);
		enter.setOpaque(true);
		enter.setBackground(Color.CYAN.brighter());
		enter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		enter.setBorder(finalStyle);

		back = new JButton(new ImageIcon("immagini/arrowicon.png"));
		back.addActionListener(buttonListener);
		back.setFont(FONT_2);
		back.setContentAreaFilled(false);
		back.setOpaque(true);
		back.setBackground(Color.CYAN.brighter());
		back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		back.setBorder(finalStyle);
		
		emailL = new JLabel("Email:", JLabel.LEFT);
		emailL.setFont(FONT_1);
		emailL.setForeground(Color.GRAY);

		nameL = new JLabel("Nome:", JLabel.LEFT);
		nameL.setFont(FONT_1);
		nameL.setForeground(Color.GRAY);
		
		surnameL = new JLabel("Cognome:", JLabel.LEFT);
		surnameL.setFont(FONT_1);
		surnameL.setForeground(Color.GRAY);
		
		nicknameL = new JLabel("Nickname:", JLabel.LEFT);
		nicknameL.setFont(FONT_1);
		nicknameL.setForeground(Color.GRAY);

		emailF = new JTextField("");
		emailF.setPreferredSize(new Dimension(200, 25));

		nameF = new JTextField("");
		nameF.setPreferredSize(new Dimension(200, 25));

		surnameF = new JTextField("");
		surnameF.setPreferredSize(new Dimension(200, 25));
		
		nicknameF = new JTextField("");
		nicknameF.setPreferredSize(new Dimension(200, 25));

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
		add(nameL, g);
		g.gridx = 1;
		g.gridy = 2;
		add(nameF, g);

		g.insets = new Insets(10, 20, 20, 20);
		
		g.gridx = 0;
		g.gridy = 3;
		add(surnameL, g);
		g.gridx = 1;
		g.gridy = 3;
		add(surnameF, g);
		
		g.insets = new Insets(10, 20, 20, 20);

		g.gridx = 0;
		g.gridy = 4;
		add(nicknameL, g);
		g.gridx = 1;
		g.gridy = 4;
		add(nicknameF, g);
		
		g.insets = new Insets(20, 20, 20, 20);
		
		g.gridx = 0;
		g.gridy = 5;
		add(back, g);		

		g.insets = new Insets(20, 20, 20, 20);
		
		g.gridx = 1;
		g.gridy = 5;
		add(enter, g);
		
		setBackground(Color.BLUE);
		setPreferredSize(new Dimension(600, 400));
	}

	private class ButtonListener2 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton)e.getSource();
			if(source == enter) {
				try {
					boolean agg = false;
					if(emailF.getText() != "" && nicknameF.getText() != "" && nameF.getText() != "") 
						agg = Client.addUser(emailF.getText() , nameF.getText(), surnameF.getText(), nicknameF.getText());
					Client.email = emailF.getText();
					emailF.setText("");
					nicknameF.setText("");
					nameF.setText("");
					surnameF.setText("");
					if(agg) {
						registerFrame.dispose();
						new CodeFrame();
					} else {
						Client.email = "";
					}
				} catch(RemoteException exc) {}	
			} else if(source == back) {
				registerFrame.dispose();
				new LogFrame();
			}

		}
	}
}

