package interfacciaGioco;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import gioco.PopUp;

public class PlayerFrame extends JFrame{
	private static final long serialVersionUID=1;

	public PlayerFrame(String nickname) { //passo il giocatore che ha terminato e lo stampo
		new PlayerFrame();
		PopUp p = new PopUp("Partita Terminata", "Ci dispiace, il giocatore " + nickname + "\nè uscito dalla partita", Color.RED, null);
		p.add();
	}

	public PlayerFrame() {
		super("Schermata Iniziale");

		PlayerPanel player = new PlayerPanel(this);

		try {
			BufferedImage image = ImageIO.read(new File("immagini/IconaRDF.png"));
			setIconImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}

		setLayout(new FlowLayout());
		add(player);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500); //dimensioni finestra
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
}
