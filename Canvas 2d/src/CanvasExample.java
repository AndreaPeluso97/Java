
import java.awt.*;  

import javax.swing.JFrame;
public class CanvasExample  
{  
	public CanvasExample()  
	{  
		JFrame f= new JFrame("Canvas Example");  
		f.add(new MyCanvas());  
		f.setLayout(null);  
		f.setSize(400, 400);  
		f.setVisible(true);  
		f.setDefaultCloseOperation(JFrame.MAXIMIZED_HORIZ);
		System.out.print(JFrame.DISPOSE_ON_CLOSE);
		
	}  
	public static void main(String args[])  
	{  
		new CanvasExample();  
	}  
}  
class MyCanvas extends Canvas  
{  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyCanvas() {  
		setBackground (Color.GRAY);  
		setSize(300, 300);  
	}  
	
	public void paint(Graphics g)  
	{  
		g.setColor(Color.red);  
		g.fillOval(175, 75, 150, 75);  
	}  
}   