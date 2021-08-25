package interfacciaGiocoAdmin;

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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.Border;

public class EditPasswPanelAdmin extends JPanel{
	private static final long serialVersionUID=1;
	
	private static final Font FONT_1 = new Font("Open Sans Light", Font.BOLD, 20);
	
	private static final Font FONT_2 = new Font("Open Sans Light", Font.PLAIN, 20);
	
	private ButtonListener7 buttonListener;
	
	private EditPasswFrameAdmin editPasswFrame;
	
	private JButton back, enter;
	
	private JLabel passwL, newPasswL;
	
	private JPasswordField passwF, newPasswF;
	
	public EditPasswPanelAdmin(EditPasswFrameAdmin frame) {
		super(new GridBagLayout());
		
		editPasswFrame = frame;
		
		buttonListener = new ButtonListener7();
		
		Border style = BorderFactory.createRaisedBevelBorder();
		Border border = BorderFactory.createEtchedBorder(Color.CYAN.darker(), Color.CYAN.darker());
		Border finalStyle = BorderFactory.createCompoundBorder(border, style);
		
		back = new JButton(new ImageIcon("immagini/arrowicon.png"));
		back.addActionListener(buttonListener);
		back.setFont(FONT_2);
		back.setContentAreaFilled(false);
		back.setOpaque(true);
		back.setBackground(Color.CYAN.brighter());
		back.setBorder(finalStyle);
		
		enter = new JButton("CONFERMA");
		enter.addActionListener(buttonListener);
		enter.setFont(FONT_1);
		enter.setContentAreaFilled(false);
		enter.setOpaque(true);
		enter.setBackground(Color.CYAN.brighter());
		enter.setBorder(finalStyle);
		
		passwL = new JLabel("Password Attuale:", JLabel.LEFT);
		passwL.setFont(FONT_1);
		passwL.setForeground(Color.GRAY);
		
		newPasswL = new JLabel("Nuova Password:", JLabel.LEFT);
		newPasswL.setFont(FONT_1);
		newPasswL.setForeground(Color.GRAY);
		
		passwF = new JPasswordField("");
		passwF.setPreferredSize(new Dimension(200, 25));
		
		newPasswF = new JPasswordField("");
		newPasswF.setPreferredSize(new Dimension(200, 25));
		
		GridBagConstraints g = new GridBagConstraints();
		
		g.insets = new Insets(-30, 20, 20, 300);
		
		g.gridy = 0;
		add(back, g);
		
		g.insets = new Insets(20, -70, 20, 20);
		
		g.gridx = 0;
		g.gridy = 1;
		add(passwL, g);
		
		g.insets = new Insets(20, -150, 20, 20);
		
		g.gridx = 1;
		add(passwF, g);
		
		g.insets = new Insets(20, -70, 20, 20);
		
		g.gridx = 0;
		g.gridy = 2;
		add(newPasswL, g);
		
		g.insets = new Insets(20, -150, 20, 20);
		
		g.gridx = 1;
		add(newPasswF, g);
		
		g.insets = new Insets(20, -400, 20, 20);
		
		g.gridy = 4;
		add(enter, g);
		
		setBackground(Color.BLUE.darker());
		setPreferredSize(new Dimension(600, 450));
	}
	
	private class ButtonListener7 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton)e.getSource();
			if(source == back) {
				editPasswFrame.dispose();
				new AdminFrame();
			} else if (source == enter) {
				try {
					if(AdminRdF.updatePasswordCode(AdminRdF.email , String.valueOf(newPasswF.getPassword()), String.valueOf(passwF.getPassword()))) {
						editPasswFrame.dispose();
						
					  		new AdminFrame();
						
					} else {
						passwF.setText("");
						newPasswF.setText("");
						JOptionPane.showMessageDialog(null, "Password Errata" + "\nLa password attuale inserita non è valida, riprovare",
								"Password Non Valida", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}				
			}

		}
	}
}
