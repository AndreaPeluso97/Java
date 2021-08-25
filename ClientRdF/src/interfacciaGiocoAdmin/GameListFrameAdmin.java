package interfacciaGiocoAdmin;


import interfacciaGiocoAdmin.GameInterfaceAdmin;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Set;

import gioco.AdminRdF;
import gioco.PopUp;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
//import javax.swing.SwingUtilities;
//import javax.swing.UIManager;
import javax.swing.border.Border;

public class GameListFrameAdmin extends JFrame{
	private static final long serialVersionUID=1;

	private static final Font FONT = new Font("Yu Gothic UI Semibold", Font.PLAIN, 15);

	private JButton back, observ, update;

	private ButtonListener10 buttonListener;

	private Hashtable<Long,String> dati;

	private JScrollPane window;
	
	private JPanel game;
	
	private GameInterfaceAdmin gameX;
	
	private Border style = BorderFactory.createRaisedBevelBorder();
	private Border border = BorderFactory.createEtchedBorder(Color.CYAN.darker(), Color.CYAN.darker());
	private Border finalStyle = BorderFactory.createCompoundBorder(border, style);
	
	private GridBagConstraints g = new GridBagConstraints();
	
	private Set<Long> chiavi;

	public GameListFrameAdmin(Hashtable<Long,String> d) {
		super("Elenco Partite");

		dati = d;

		buttonListener = new ButtonListener10();

		try {
			BufferedImage image = ImageIO.read(new File("immagini/IconaRDF.png"));
			setIconImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}

		back = new JButton("Indietro");
		back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		back.addActionListener(buttonListener);
		back.setFont(FONT);
		back.setContentAreaFilled(false);
		back.setOpaque(true);
		back.setBackground(Color.CYAN.brighter());
		back.setBorder(finalStyle);
		back.setPreferredSize(new Dimension(100, 25));
		
		update = new JButton("Aggiorna");
		update.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		update.addActionListener(buttonListener);
		update.setFont(FONT);
		update.setContentAreaFilled(false);
		update.setOpaque(true);
		update.setBackground(Color.CYAN.brighter());
		update.setBorder(finalStyle);
		update.setPreferredSize(new Dimension(100, 25));		

		inizialize();
	}
	
	private void inizialize() {
		Container cont = new Container();
		cont.setLayout(new GridLayout());

		game = new JPanel();
		game.setLayout(new GridBagLayout());
		window = new JScrollPane(game, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		game.setBackground(new Color(155, 248, 214));
		
		generateGameList();		

		cont.add(window);			

		add(cont);

		setLayout(new GridLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(650, 450); //dimensioni finestra
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	private void generateGameList() {
		String mail = null;
		chiavi = dati.keySet();
		
		if(chiavi.size()==0) {
			JLabel txt = new JLabel("<html>Al momento non è stata creata alcuna partita<br/>Torna indietro per crearne una o aggiorna la lista</html");
			txt.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			g.insets = new Insets(10, 0, 0, 0);
			
			game.add(txt, g);
			
			g.insets = new Insets(10, -125, 0, 0);

			g.gridx = 0;
			g.gridy = 1;
			game.add(back, g);
			
			g.gridx = 0;
			g.insets = new Insets(10, 125, 0, 0);
			game.add(update, g);
		} else {
			g.insets = new Insets(10, -125, 0, 0);

			g.gridx = 0;
			g.gridy = 0;
			game.add(back, g);
			
			g.gridx = 0;
			g.insets = new Insets(10, 125, 0, 0);
			game.add(update, g);
			
			g.insets = new Insets(10, 0, 0, 0);
			
			for(Long c: chiavi) {
				mail = dati.get(c);
				if(!(mail.equals(AdminRdF.email))) {
					g.gridy += 1;
					try {
						observ = new JButton(" OSSERVA ");
						//observ.addActionListener(buttonListener);
						observ.setFont(FONT);
						observ.setContentAreaFilled(false);
						observ.setOpaque(true);
						observ.setBackground(Color.YELLOW);
						observ.setBorder(finalStyle);
						observ.setBounds(313, 96, 89, 23);
						observ.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						observ.setName("id:" + c);


						gameX = new GameInterfaceAdmin(c, AdminRdF.nPlayers(c), mail);
						
						
						gameX.add(observ);
		
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					game.add(gameX, g);	
				}
			}
		}
		
		
	}

	private class ButtonListener10 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton)e.getSource();
			if(source == back) {
				dispose();
				new AdminFrame();
			} else if (source == update) {
					try {
						new GameListFrameAdmin(AdminRdF.gameList());
						dispose();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
			} else if(source.getName().contains("id")) {
				dispose();
				try {
					AdminRdF.addObs();
				} catch (RemoteException e3) {
					e3.printStackTrace();
				}

				String s = source.getName();
				String sub = s.substring(s.indexOf(':')+1, s.length());
				long idG = Long.parseLong(sub);
				AdminRdF.idGame = idG;

				try {
					if(AdminRdF.logGame()) { 
						try {
							if(AdminRdF.nPlayers(AdminRdF.idGame)!=3) {
								new LoadingFrameAdmin(AdminRdF.nPlayers(AdminRdF.idGame));
							}
						} catch (RemoteException e2) {
							e2.printStackTrace();
						}
					} else {
						PopUp pu = new PopUp("Errore", "Ops, non ci sono nuove frasi disponibili! \nSarà inviata una mail agli amministratori", Color.RED, null);
						pu.add();
						new AdminFrame();
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}  

		}
	}


}
