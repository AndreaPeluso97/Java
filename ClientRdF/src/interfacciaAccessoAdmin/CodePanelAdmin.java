package interfacciaAccessoAdmin;

import javax.swing.JPanel;
import gioco.AdminRdF;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class CodePanelAdmin extends JPanel{
	private static final long serialVersionUID=1;
	
	private static final Font FONT_1 = new Font("Open Sans Light", Font.BOLD, 20);
	
	private static final Font FONT_2 = new Font("Open Sans Light", Font.PLAIN, 20);
	
	private CodeFrameAdmin codeFrame;
	
	private JLabel codeL, infoL;
	
	private JButton enter, back;
	
	private ButtonListener4 buttonListener;
	
	private JTextField codeF;
	
	public CodePanelAdmin(CodeFrameAdmin frame) {
		super(new GridBagLayout());
		
		buttonListener = new ButtonListener4();
		
		codeFrame = frame;
		
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
		
		infoL = new JLabel("Ti è stato inviato un codice per email");
		infoL.setFont(FONT_1);
		infoL.setForeground(Color.ORANGE);
		
		codeL = new JLabel("Inserisci il codice:");
		codeL.setFont(FONT_1.deriveFont(Font.ITALIC));
		codeL.setForeground(Color.GRAY);
		
		codeF = new JTextField("");
		codeF.setPreferredSize(new Dimension(200, 25));
		
		GridBagConstraints g = new GridBagConstraints();
		
		g.insets = new Insets(-20, 80, 20, 100);
		
		g.gridy = 1;
		add(infoL, g);
		
		g.insets = new Insets(30, 20, 20, 240);
		
		g.gridx = 0;
		g.gridy = 2;
		add(codeL, g);
		
		g.insets = new Insets(30, 180, 20, 20);
	
		g.gridy = 2;
		add(codeF, g);
		
		g.insets = new Insets(20, 220, 20, 20);
		
		g.gridx = 0;
		g.gridy = 3;
		add(enter, g);
		
		g.insets = new Insets(20, 20, 20, 200);
		
		g.gridy = 3;
		add(back, g);
		
		setBackground(Color.BLUE.darker());
		setPreferredSize(new Dimension(600, 400));
	}
	
	private class ButtonListener4 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton)e.getSource();
			if(source == enter) {
				try {
					if(AdminRdF.verCode(AdminRdF.email, codeF.getText())) {
							JOptionPane.showMessageDialog(null, "Utente Registrato" + "\nLa registrazione è andata a buon fine",
							"Registrato Con Successo", JOptionPane.INFORMATION_MESSAGE);
							new PasswFrameAdmin(codeF.getText());
							codeFrame.dispose();
					  } else {
					  		codeF.setText("");
					  		JOptionPane.showMessageDialog(null, "Codice Errato" + "\nIl codice non corrisponde, riprova",
							"Codice Inserito Errato", JOptionPane.INFORMATION_MESSAGE);
					  }
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				  
			} else if(source == back) {
				codeFrame.dispose();
				new LogFrameAdmin();
			}

		}
	}
}
