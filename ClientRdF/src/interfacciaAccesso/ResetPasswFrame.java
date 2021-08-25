package interfacciaAccesso;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import gioco.Client;

public class ResetPasswFrame extends JFrame{
	private static final long serialVersionUID=1;
	
	private static final Font FONT = new Font("Open Sans Light", Font.BOLD, 20);
	
	private static final Font FONT_1 = new Font("Open Sans Light", Font.PLAIN, 20);
	
	private ButtonListener9 buttonListener;
	
	private JButton enter, back;
	
	private JLabel emailL;
	
	private JTextField emailF;
	
	public ResetPasswFrame() {
		super("Reset Password");
		
		try {
            BufferedImage image = ImageIO.read(new File("immagini/IconaRDF.png"));
            setIconImage(image);
		} catch (IOException e) {
            e.printStackTrace();
		}
		
		buttonListener = new ButtonListener9();
		
		Border stile = BorderFactory.createRaisedBevelBorder();
		Border bordo = BorderFactory.createEtchedBorder(Color.CYAN.darker(), Color.CYAN.darker());
		Border stileFinale = BorderFactory.createCompoundBorder(bordo, stile);
		
		back = new JButton(new ImageIcon("immagini/arrowicon.png"));
		back.addActionListener(buttonListener);
		back.setFont(FONT_1);
		back.setContentAreaFilled(false);
		back.setOpaque(true);
		back.setBackground(Color.CYAN.brighter());
		back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		back.setBorder(stileFinale);
		
		enter = new JButton("INVIA");
		enter.addActionListener(buttonListener);
		enter.setFont(FONT);
		enter.setContentAreaFilled(false);
		enter.setOpaque(true);
		enter.setBackground(Color.CYAN.brighter());
		enter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		enter.setBorder(stileFinale);
		
		emailL = new JLabel("Inserisci l'email: ");
		emailL.setFont(FONT);
		emailL.setForeground(Color.GRAY);
		
		emailF = new JTextField("");
		emailF.setPreferredSize(new Dimension(200, 25));
		
		JPanel emailP = new JPanel(new GridBagLayout());
		emailP.setBackground(Color.BLUE.darker());
		
		GridBagConstraints g = new GridBagConstraints();
		
		g.insets = new Insets(-30, 20, 20, 20);
		
		g.gridx = 0;
		g.gridy = 1;
		emailP.add(emailL, g);
		
		g.gridx = 1;
		emailP.add(emailF, g);
		
		g.insets = new Insets(20, 20, 20, 20);
		
		g.gridx = 0;
		g.gridy = 2;
		emailP.add(back, g);
		
		g.gridx = 1;
		g.gridy = 2;
		emailP.add(enter, g);
		
		setLayout(new FlowLayout());
		add(emailP);
		
		emailP.setPreferredSize(new Dimension(500, 400));
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400); //dimensioni finestra di gioco
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
	}
	
	private class ButtonListener9 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton)e.getSource();
			if(source == enter) {
				try {
					if(Client.resetPassword(emailF.getText())) {
						JOptionPane.showMessageDialog(null, "Reset Password" + "\nTi è stata inviata una mail con una nuova password",
								"Reset Della Password", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				dispose();
				new LogFrame();					
			} else if(source == back) {
				dispose();
				new LogFrame();
			}

		}
	}
}
