package interfacciaGiocoAdmin;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class EditFrameAdmin extends JFrame{
	private static final long serialVersionUID=1;
	
	protected static String[] data;
	
	public EditFrameAdmin(String[] d) {
		super("Schermata di Modifica data admin");
		
		data = d;
		
		EditPanelAdmin edit = new EditPanelAdmin(this);
		
		try {
            BufferedImage image = ImageIO.read(new File("immagini/IconaRDF.png"));
            setIconImage(image);
		} catch (IOException e) {
            e.printStackTrace();
		}
		
		setLayout(new FlowLayout());
		add(edit);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450); //dimensioni finestra
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
	}
	

}
