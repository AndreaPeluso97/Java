package interfacciaAccesso;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class LogFrame extends JFrame{
	private static final long serialVersionUID=1;
	
	public LogFrame() {
		super("Schermata di Accesso");
		
		LogPanel log = new LogPanel(this);
		
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
	
	public static void main(String[] args) {
        try {
            // Use system-specific UI if possible
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Proceed without system-specific UI
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
            	try {
            		new LogFrame();
            	} catch(Exception e) {}
            }
        });
    }
	
}
