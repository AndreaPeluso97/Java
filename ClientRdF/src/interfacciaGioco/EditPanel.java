package interfacciaGioco;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import gioco.Client;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class EditPanel extends JPanel{
	private static final long serialVersionUID=1;
	
	private static final Font FONT_1 = new Font("Open Sans Light", Font.BOLD, 20);
	
	private static final Font FONT_2 = new Font("Open Sans Light", Font.PLAIN, 20);
	
	private ButtonListener6 buttonListener;
	
	private JButton enter, back;
	
	private EditFrame editFrame;
	
	private JLabel nameL, surnameL, nicknameL;
	
	private JTextField nameF, surnameF, nicknameF;
	
	public EditPanel(EditFrame frame) {
		super(new GridBagLayout());
		
		editFrame = frame;
		
		buttonListener = new ButtonListener6();
		
		Border style = BorderFactory.createRaisedBevelBorder();
		Border border = BorderFactory.createEtchedBorder(Color.CYAN.darker(), Color.CYAN.darker());
		Border finalStyle = BorderFactory.createCompoundBorder(border, style);
		
		enter = new JButton("MODIFICA");
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
		enter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		back.setBorder(finalStyle);
		
		nameL = new JLabel("Nome:", JLabel.LEFT);
		nameL.setFont(FONT_1);
		nameL.setForeground(Color.GRAY);
		
		surnameL = new JLabel("Cognome:", JLabel.LEFT);
		surnameL.setFont(FONT_1);
		surnameL.setForeground(Color.GRAY);
		
		nicknameL = new JLabel("Nickname:", JLabel.LEFT);
		nicknameL.setFont(FONT_1);
		nicknameL.setForeground(Color.GRAY);
		
		nameF = new JTextField(EditFrame.data[0]);
		nameF.setPreferredSize(new Dimension(200, 25));
		nameF.setEditable(false);
		
		surnameF = new JTextField(EditFrame.data[1]);
		surnameF.setPreferredSize(new Dimension(200, 25));
		surnameF.setEditable(false);
		
		nicknameF = new JTextField(EditFrame.data[2]);
		nicknameF.setPreferredSize(new Dimension(200, 25));
		nicknameF.setEditable(false);
		
		GridBagConstraints g = new GridBagConstraints();
		
		g.insets = new Insets(-30, 20, 20, 300);
		
		g.gridy = 0;
		add(back, g);
		
		g.insets = new Insets(20, -70, 20, 20);
		
		g.gridx = 0;
		g.gridy = 1;
		add(nameL, g);
		
		g.insets = new Insets(20, -150, 20, 20);
		
		g.gridx = 1;
		add(nameF, g);
		
		g.insets = new Insets(20, -70, 20, 20);
		
		g.gridx = 0;
		g.gridy = 2;
		add(surnameL, g);
		
		g.insets = new Insets(20, -150, 20, 20);
		
		g.gridx = 1;
		add(surnameF, g);
		
		g.insets = new Insets(20, -70, 20, 20);
		
		g.gridx = 0;
		g.gridy = 3;
		add(nicknameL, g);
		
		g.insets = new Insets(20, -150, 20, 20);
		
		g.gridx = 1;
		add(nicknameF, g);
		
		g.insets = new Insets(20, -400, 20, 20);
		
		g.gridy = 4;
		add(enter, g);
		
		setBackground(Color.BLUE.darker());
		setPreferredSize(new Dimension(600, 450));
	}
	
	private class ButtonListener6 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton)e.getSource();
			if(source == back) {
				editFrame.dispose();
				new PlayerFrame();
			}else if (source == enter) {
				if(enter.getText().equals("MODIFICA")) {
					nameF.setEditable(true);
					surnameF.setEditable(true);
					nicknameF.setEditable(true);
					enter.setText("CONFERMA");
				} else if(enter.getText().equals("CONFERMA")) {
					try {
						Client.updateProfile(nameF.getText(), surnameF.getText(), nicknameF.getText());
						nameF.setEditable(false);
						surnameF.setEditable(false);
						nicknameF.setEditable(false);
						enter.setText("MODIFICA");
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}

		}
	}
}
