package interfacciaAccesso;

import javax.swing.BorderFactory;
import javax.swing.border.*;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LogPanel extends JPanel{
	private static final long serialVersionUID=1;
	
	private static final Font FONT = new Font("Open Sans Light", Font.BOLD, 32);
	
	private LogFrame logFrame;
	
	private JButton login, register, resetPassword;
	
	private ButtonListener buttonListener;
	
	public LogPanel(LogFrame frame) {
		super(new GridBagLayout());
		
		logFrame = frame;
		
		buttonListener = new ButtonListener();
		
		Border style = BorderFactory.createRaisedBevelBorder();
		Border border = BorderFactory.createEtchedBorder(Color.CYAN.darker(), Color.CYAN.darker());
		Border finalStyle = BorderFactory.createCompoundBorder(border, style);
	    
		login = new JButton("Accedi");
		login.setContentAreaFilled(false);
        login.setOpaque(true);
        login.setBackground(Color.CYAN.brighter());
		login.setBorder(finalStyle);
		login.setForeground(Color.BLUE.darker());
		login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		login.addActionListener(buttonListener);
		
		register = new JButton("Registrati");
		register.setBorder(finalStyle);
		register.setContentAreaFilled(false);
        register.setOpaque(true);
        register.setForeground(Color.BLUE.darker());
        register.setBackground(Color.CYAN.brighter());
        register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        register.addActionListener(buttonListener);
		
		resetPassword = new JButton("Reset Password");
		resetPassword.addActionListener(buttonListener);
		resetPassword.setBorder(finalStyle);
		resetPassword.setContentAreaFilled(false);
		resetPassword.setOpaque(true);
		resetPassword.setForeground(Color.BLUE.darker());
		resetPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		resetPassword.setBackground(Color.CYAN.brighter());
		
		login.setFont(FONT);
		register.setFont(FONT);
		resetPassword.setFont(FONT);
		
		GridBagConstraints g = new GridBagConstraints();
		
		g.insets = new Insets(5, 20, 20, 20);
		
		g.gridx = 0;
		g.gridy = 1;
		add(login, g);
		g.gridx = 0;
		g.gridy = 2;
		add(register, g);
		g.gridx = 0;
		g.gridy = 3;
		add(resetPassword, g);
		
		setPreferredSize(new Dimension(600, 400));
		setBackground(Color.BLUE);
	}
	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton)e.getSource();
			if(source == login) {
				new LoginFrame();
				logFrame.dispose();
			}else if(source == register) {
				new RegisterFrame();
				logFrame.dispose();
			}else if(source == resetPassword) {
				new ResetPasswFrame();
				logFrame.dispose();
			}
			
		}
	}
}
