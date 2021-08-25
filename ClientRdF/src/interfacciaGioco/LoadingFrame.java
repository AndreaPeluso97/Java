package interfacciaGioco;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
//import javax.swing.SwingUtilities;
//import javax.swing.UIManager;
import javax.swing.border.Border;
import gioco.Client;
import gioco.PopUp;



public class LoadingFrame extends JFrame{
	private static final long serialVersionUID=1;
	
	private static final Font FONT = new Font("Open Sans Light", Font.BOLD, 10);
	
	public static JLabel playersLabel;
	
	public static LoadingFrame frame;

	private JLabel label;
	
	private JButton back;
	
	private static int i;
	
	public LoadingFrame(int n) {
		super("Schermata di Caricamento");
		i = n;
		
		frame = this;
		
		try {
            BufferedImage image = ImageIO.read(new File("immagini/IconaRDF.png"));
            setIconImage(image);
		} catch (IOException e) {
            e.printStackTrace();
		}
		
		URL url = this.getClass().getResource("/gif/loading.gif");
        Icon icon = new ImageIcon(url);
        
        Border style = BorderFactory.createRaisedBevelBorder();
		Border border = BorderFactory.createEtchedBorder(Color.RED, Color.RED.darker());
		Border finalStyle = BorderFactory.createCompoundBorder(border, style);

		back = new JButton("ESCI");
		back.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				try {
					if(Client.player) {
						Client.player = false;
						Client.exitLobby();
					} else if(Client.observer) {
						Client.observer = false;
						Client.exitLobbyObs();
					}
					
					dispose();
					Client.removeMe();
					new GameListFrame(Client.gameList());
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			} 
		} );
		back.setFont(FONT);
		back.setContentAreaFilled(false);
		back.setOpaque(true);
		back.setBackground(Color.RED.brighter());
		back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		back.setBorder(finalStyle);
        
        label = new JLabel(icon);
        playersLabel = new JLabel();
        
        playersLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        playersLabel.setText( i + "/3");
        playersLabel.setForeground(Color.WHITE);
        
        JPanel gif = new JPanel();
        gif.setLayout(new GridBagLayout());
        
        GridBagConstraints g = new GridBagConstraints();        
       
        g.gridy = 0;
        g.gridx = 0;
        g.insets = new Insets(5, 0, 0, 0);
        gif.add(playersLabel, g);
        g.insets = new Insets(60, 0, 0, 0);
        gif.add(back, g);
        g.insets = new Insets(0, 0, 0, 0);
        gif.add(label, g);
        
        
        setLayout(new GridBagLayout());
		add(gif);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400); //dimensioni finestra
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true); 
	}
	
	public static void updatePlayers() throws RemoteException {
		playersLabel.setText(i + "/3");
		if(i==3) {
			frame.dispose();
		} else if(i==0) {
			frame.dispose();
			if(Client.player) {
				Client.player = false;
			} else if(Client.observer) {
				Client.observer = false;
			}
			new GameListFrame(Client.gameList());
			PopUp pu = new PopUp("Partita Annullata", "Ops, tutti i giocatori sono usciti! \nLa partita è stata annullata", Color.RED, null);
			pu.add();
		}
	}
	
	public static void addPlayer(int n) {
		i = i+n;
		playersLabel.setText(""+i);
	}

}
