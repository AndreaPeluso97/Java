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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class EditPanelAdmin extends JPanel{
	private static final long serialVersionUID=1;
	
	private static final Font FONT_1 = new Font("Open Sans Light", Font.BOLD, 20);
	
	private static final Font FONT_2 = new Font("Open Sans Light", Font.PLAIN, 20);
	
	private ButtonListener6 buttonListener;
	
	private JButton enter, back;
	
	private EditFrameAdmin editFrame;
	
	private JLabel nameL, surnameL, nicknameL;
	
	private JTextField nameF, surnameF, nicknameF;
	
	public EditPanelAdmin(EditFrameAdmin editFrameAdmin) {
		super(new GridBagLayout());
		
		editFrame = editFrameAdmin;
		
		buttonListener = new ButtonListener6();
		
		Border style = BorderFactory.createRaisedBevelBorder();
		Border border = BorderFactory.createEtchedBorder(Color.CYAN.darker(), Color.CYAN.darker());
		Border finalStyle = BorderFactory.createCompoundBorder(border, style);
		
		enter = new JButton("INVIA data");
		enter.addActionListener(buttonListener);
		enter.setFont(FONT_1);
		enter.setContentAreaFilled(false);
		enter.setOpaque(true);
		enter.setBackground(Color.CYAN.brighter());
		enter.setBorder(finalStyle);
		
		back = new JButton(new ImageIcon("immagini/arrowicon.png"));
		back.addActionListener(buttonListener);
		back.setFont(FONT_2);
		back.setContentAreaFilled(false);
		back.setOpaque(true);
		back.setBackground(Color.CYAN.brighter());
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
		
		nameF = new JTextField(EditFrameAdmin.data[0]);
		nameF.setPreferredSize(new Dimension(200, 25));
		
		surnameF = new JTextField(EditFrameAdmin.data[1]);
		surnameF.setPreferredSize(new Dimension(200, 25));
		
		nicknameF = new JTextField(EditFrameAdmin.data[2]);
		nicknameF.setPreferredSize(new Dimension(200, 25));
		
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
				new AdminFrame();
			}else if (source == enter) {
				try {
					AdminRdF.updateProfile(nameF.getText(), surnameF.getText(), nicknameF.getText());
					editFrame.dispose();
					
				  		new AdminFrame();
					
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}

		}
	}
}
