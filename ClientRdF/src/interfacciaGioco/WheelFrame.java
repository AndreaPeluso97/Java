package interfacciaGioco;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;


import gioco.WheelOfFortune;

public class WheelFrame extends JFrame {
	private static final long serialVersionUID=1;
	
	public WheelFrame() throws UnsupportedAudioFileException, 
    IOException, LineUnavailableException{
		super("Schermata Player");
		try {
            BufferedImage image = ImageIO.read(new File("immagini/IconaRDF.png"));
            setIconImage(image);
		} catch (IOException e) {
            e.printStackTrace();
		}
		setLayout(new FlowLayout());
		
		WheelOfFortune game = new WheelOfFortune();
		PhrasePanel frase = new PhrasePanel(game);
		FirstPanel firstP = new FirstPanel(game);
		WheelPanel ruota = new WheelPanel(game, firstP, frase, this);
		
		add(firstP);
		add(frase);
		add(ruota);
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 720); //dimensioni finestra di gioco
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
	}
}
