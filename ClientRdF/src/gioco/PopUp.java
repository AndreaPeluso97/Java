package gioco;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PopUp {
	private JDialog d;
	private JLabel title;
	private JLabel body;
	
	public PopUp(String t, String msg, Color col, Component ref) {
		d=new JDialog();
		d.setSize(400, 150);
		title = new JLabel(t);
		title.setFont(new Font("Open Sans Light", Font.BOLD, 15));
		title.setForeground(col);
		title.setBounds(130,10,200,20);
		body =new JLabel("<html>" + msg.replaceAll("\n", "<br/>") + "</html>", SwingConstants.CENTER);
		body.setBounds(50,50,30,20);
		d.setModal(false);
		d.setAlwaysOnTop(true);
		d.setResizable(false);
		d.setVisible(true);
		if(ref != null) {
			d.setLocationRelativeTo(ref);
		} else {
			d.setLocation(500, 270);
		}
	}
	
	public void add() {
		d.add(title);
		d.add(body);
	}
	
	public void setSize(int width, int height) {
		d.setSize(width, height);
	}
	
	public void setBodyBounds(int x, int y, int width, int height) {
		body.setBounds(x, y, width, height);
	}
	
	public void setTitleBounds(int x, int y, int width, int height) {
		title.setBounds(x, y, width, height);
	}
}
