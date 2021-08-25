package interfacciaAccessoAdmin;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

//import javax.swing.SwingUtilities;
//import javax.swing.UIManager;

public class RegisterFrameAdmin extends JFrame{
	private static final long serialVersionUID=1;
	
	public RegisterFrameAdmin() {
		super("Schermata di Registrazione Admin");
		
		RegisterPanelAdmin register = new RegisterPanelAdmin(this);
		
		try {
            BufferedImage image = ImageIO.read(new File("immagini/IconaRDF.png"));
            setIconImage(image);
		} catch (IOException e) {
            e.printStackTrace();
		}
		
		setLayout(new FlowLayout());
		add(register);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400); //dimensioni finestra
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
	}
	

}
