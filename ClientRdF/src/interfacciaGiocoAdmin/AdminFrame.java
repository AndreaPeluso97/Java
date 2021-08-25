package interfacciaGiocoAdmin;


import gioco.PopUp;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class AdminFrame extends JFrame{
	private static final long serialVersionUID=1;

	public AdminFrame(String nickname) { //passo il giocatore che ha terminato e lo stampo
	    new AdminFrame();
	    PopUp p = new PopUp("Partita Terminata", "Ci dispiace, il giocatore " + nickname + " \nè uscito dalla partita", Color.RED, null);
	    p.add();
	  }

	public AdminFrame() {
		super("Schermata Iniziale Admin");

		AdminPanel player = new AdminPanel(this);

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

