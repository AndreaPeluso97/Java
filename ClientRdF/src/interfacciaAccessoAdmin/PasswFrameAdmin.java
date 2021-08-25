package interfacciaAccessoAdmin;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class PasswFrameAdmin extends JFrame{
	private static final long serialVersionUID=1;
	
	protected static String cod;
	
	public PasswFrameAdmin(String codice) {
		super("Imposta Password Amdin");
		
		cod = codice;
		
		try {
            BufferedImage image = ImageIO.read(new File("immagini/IconaRDF.png"));
            setIconImage(image);
		} catch (IOException e) {
            e.printStackTrace();
		}
		
		PasswPanelAdmin passw = new PasswPanelAdmin(this);
		
		setLayout(new FlowLayout());
		add(passw);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450); //dimensioni finestra
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
	}
	
	
}
