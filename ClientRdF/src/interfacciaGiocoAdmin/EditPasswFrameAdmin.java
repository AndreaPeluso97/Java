package interfacciaGiocoAdmin;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class EditPasswFrameAdmin extends JFrame{
	private static final long serialVersionUID=1;
	
	public EditPasswFrameAdmin() {
		super("Modifica Password Admin");
		
		EditPasswPanelAdmin editP = new EditPasswPanelAdmin(this);
		
		try {
            BufferedImage image = ImageIO.read(new File("immagini/IconaRDF.png"));
            setIconImage(image);
		} catch (IOException e) {
            e.printStackTrace();
		}
		
		setLayout(new FlowLayout());
		add(editP);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450); //dimensioni finestra
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
	}
	
	
}
