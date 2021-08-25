package interfacciaAccessoAdmin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import gioco.AdminRdF;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.Border;

import interfacciaGiocoAdmin.AdminFrame;

public class PasswPanelAdmin extends JPanel{
	private static final long serialVersionUID=1;
	
	private static final Font FONT = new Font("Open Sans Light", Font.BOLD, 20);
	
	private ButtonListener8 buttonListener;
	
	private PasswFrameAdmin passwFrame;
	
	private JLabel passwL;
	
	private JPasswordField passwF;
	
	private JButton enter;
	
	public PasswPanelAdmin(PasswFrameAdmin frame) {
		super(new GridBagLayout());
		
		passwFrame = frame;
		
		buttonListener = new ButtonListener8();
		
		Border style = BorderFactory.createRaisedBevelBorder();
		Border border = BorderFactory.createEtchedBorder(Color.CYAN.darker(), Color.CYAN.darker());
		Border finalStyle = BorderFactory.createCompoundBorder(border, style);
		
		enter = new JButton("CONFERMA");
		enter.addActionListener(buttonListener);
		enter.setFont(FONT);
		enter.setContentAreaFilled(false);
		enter.setOpaque(true);
		enter.setBackground(Color.CYAN.brighter());
		enter.setBorder(finalStyle);
		
		passwL = new JLabel("Inserisci Password:", JLabel.LEFT);
		passwL.setFont(FONT);
		passwL.setForeground(Color.GRAY);
		
		passwF = new JPasswordField("");
		passwF.setPreferredSize(new Dimension(200, 25));
		
		GridBagConstraints g = new GridBagConstraints();
		
		g.insets = new Insets(-30, -100, 20, 0);
		
		g.gridy = 0;
		add(passwL, g);
		
		g.insets = new Insets(-30, -120, 20, 0);
		
		g.gridy = 0;
		add(passwF, g);
		
		g.insets = new Insets(20, 150, 20, 80);
		
		g.gridy = 1;
		add(enter, g);
		
		setBackground(Color.BLUE.darker());
		setPreferredSize(new Dimension(600, 450));
	}
	
	private class ButtonListener8 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton)e.getSource();
			if(source == enter) {
				try {
					AdminRdF.updatePasswordCode(AdminRdF.email, String.valueOf(passwF.getPassword()), PasswFrameAdmin.cod);
					passwFrame.dispose();
					new AdminFrame();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			} 

		}
	}
}
