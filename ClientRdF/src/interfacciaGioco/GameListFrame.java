package interfacciaGioco;

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
import java.util.LinkedHashMap;
import java.util.Set;

import gioco.Client;
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

public class GameListFrame extends JFrame{
	private static final long serialVersionUID=1;

	private static final Font FONT = new Font("Yu Gothic UI Semibold", Font.PLAIN, 15);

	private JButton back, play, observ, update;

	private ButtonListener10 buttonListener;

	private LinkedHashMap<Long,String> dati;

	private JScrollPane window;
	
	private JPanel game;
	
	private GameInterface gameX;
	
	private Border style = BorderFactory.createRaisedBevelBorder();
	private Border border = BorderFactory.createEtchedBorder(Color.CYAN.darker(), Color.CYAN.darker());
	private Border finalStyle = BorderFactory.createCompoundBorder(border, style);
	
	private GridBagConstraints g = new GridBagConstraints();
	
	private Set<Long> chiavi;

	public GameListFrame(LinkedHashMap<Long,String> d) {
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
				if(!(mail.equals(Client.email))) {
					g.gridy += 1;
					try {
						observ = new JButton(" OSSERVA ");
						observ.addActionListener(buttonListener);
						observ.setFont(FONT);
						observ.setContentAreaFilled(false);
						observ.setOpaque(true);
						observ.setBackground(Color.YELLOW);
						observ.setBorder(finalStyle);
						observ.setBounds(313, 96, 89, 23);
						observ.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						observ.setName("obId:" + c);

						play = new JButton(" GIOCA ");
						play.addActionListener(buttonListener);
						play.setFont(FONT);
						play.setContentAreaFilled(false);
						play.setOpaque(true);
						play.setBackground(Color.GREEN);
						play.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						play.setBorder(finalStyle);
						play.setBounds(183, 96, 89, 23);
						play.setName("plId:" + c);

						gameX = new GameInterface(c, Client.nPlayers(c), mail);
						if(Client.nPlayers(c)==3) {
							play.setEnabled(false);
							play.setBackground(Color.GRAY.brighter());
							Border bdr = BorderFactory.createEtchedBorder(Color.GRAY.darker(), Color.GRAY.darker());
							Border fS = BorderFactory.createCompoundBorder(bdr, style);
							play.setBorder(fS);
						}
						
						gameX.add(play);
						
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
				new PlayerFrame();
			} else if (source == update) {
					try {
						new GameListFrame(Client.gameList());
						dispose();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
			} else if(source.getName().contains("plId")) {
				try {
					Client.addObs();
				} catch (RemoteException e3) {
					e3.printStackTrace();
				}
				Client.player = true;
				String s = source.getName();
				String sub = s.substring(s.indexOf(':')+1, s.length());
				long idG = Long.parseLong(sub);
				Client.idGame = idG;
				try {
					if(Client.nPlayers(Client.idGame)==3) {
						PopUp pu = new PopUp("Errore", "Ops, la partita è già iniziata!", Color.RED, null);
						pu.add();
						new GameListFrame(Client.gameList());
						dispose();
					} else {
						dispose();
						try {
							if(Client.logGame()) { 
								try {
									if(Client.nPlayers(Client.idGame)!=3) {
										new LoadingFrame(Client.nPlayers(Client.idGame));
									} 
								} catch (RemoteException e2) {
									e2.printStackTrace();
								}
							} else {
								PopUp pu = new PopUp("Errore", "Ops, non ci sono nuove frasi disponibili! \nSarà inviata una mail agli amministratori", Color.RED, null);
								pu.add();
								new PlayerFrame();
							}
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					}
				} catch (RemoteException e3) {
					e3.printStackTrace();
				}
				
			} else if(source.getName().contains("obId")) {
				Client.observer = true;
				String s = source.getName();
				String sub = s.substring(s.indexOf(':')+1, s.length());
				long idG = Long.parseLong(sub);
				Client.idGame = idG;
				
				try {
					if(Client.logObGame()) {
						try {
							dispose();
							if(Client.nPlayers(Client.idGame)!=3) {
								new LoadingFrame(Client.nPlayers(Client.idGame));
								Client.addObs();
							} else {
								new ObserverFrame(1);
								Client.addObs();
							}
						} catch (RemoteException e2) {
							e2.printStackTrace();
						}
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			} 

		}
	}


}
