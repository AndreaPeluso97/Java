package interfacciaAccesso;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class PasswFrame extends JFrame{
	private static final long serialVersionUID=1;
	
	protected static String cod;
	
	public PasswFrame(String codice) {
		super("Imposta Password");
		
		cod = codice;
		
		try {
            BufferedImage image = ImageIO.read(new File("immagini/IconaRDF.png"));
            setIconImage(image);
		} catch (IOException e) {
            e.printStackTrace();
		}
		
		PasswPanel passw = new PasswPanel(this);
		
		setLayout(new FlowLayout());
		add(passw);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450); //dimensioni finestra
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
            		new PasswFrame("ciao");
            	} catch(Exception e) {}
            }
        });
    }
}
