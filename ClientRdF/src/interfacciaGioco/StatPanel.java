package interfacciaGioco;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class StatPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTextField value;

	public StatPanel(String stat, String x) {
		setBackground(new Color(240, 230, 140));
		setBorder(new LineBorder(Color.GRAY, 2, true));
		//setBackground());
		setLayout(null);
		
		JLabel st = new JLabel(stat);
		st.setBounds(10, 11, 284, 26);
		add(st);
		
		value = new JTextField(x);
		value.setHorizontalAlignment(SwingConstants.CENTER);
		value.setFont(new Font("Tahoma", Font.PLAIN, 15));
		value.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		value.setEditable(false);
		if(x.length()<5) {
			value.setBounds(380, 10, 50, 25);
		} else {
			value.setBounds(276, 10, 164, 25);
		}
		
		
		add(value);
		setPreferredSize(new Dimension(450, 46));
	}
}
