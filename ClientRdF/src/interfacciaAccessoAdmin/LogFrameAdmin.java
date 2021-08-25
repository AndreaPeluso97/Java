package interfacciaAccessoAdmin;


import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class LogFrameAdmin extends JFrame{
	private static final long serialVersionUID=1;
	
	public LogFrameAdmin() {
		super("Schermata di Accesso Admin");
		
		LogPanelAdmin log = new LogPanelAdmin(this);
		
		try {
            BufferedImage image = ImageIO.read(new File("immagini/IconaRDF.png"));
            setIconImage(image);
		} catch (IOException e) {
            e.printStackTrace();
		}
		
		setLayout(new FlowLayout());
		add(log);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400); //dimensioni finestra
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
	}
	
	
	
}

