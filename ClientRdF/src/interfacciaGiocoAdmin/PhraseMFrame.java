package interfacciaGiocoAdmin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Cursor;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import gioco.AdminRdF;
import gioco.Phrases;

import java.awt.Component;
import javax.swing.border.MatteBorder;

public class PhraseMFrame {

	private JFrame frame;

	private JTable table;
	private JButton importExc;
	private JButton addPhrase;
	private JButton save;
	private Hashtable<Integer,Phrases> phrases;
	private Set<Integer> keys;
	private List<Integer> kList;
	private int lastId;
	private JButton delete;
	private JButton back;

	public PhraseMFrame() {
		try {
			phrases = AdminRdF.getSt().getPhrases();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		initialize();
	}

	private void initialize() {
		frame = new JFrame("Schermata Gestione Frasi");

		try {
			BufferedImage image = ImageIO.read(new File("immagini/IconaRDF.png"));
			frame.setIconImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Border style = BorderFactory.createRaisedBevelBorder();
		Border border = BorderFactory.createEtchedBorder(Color.CYAN.darker(), Color.CYAN.darker());
		Border finalStyle = BorderFactory.createCompoundBorder(border, style);

		back = new JButton(new ImageIcon("immagini/arrowicon.png"));
		back.setBounds(28, 24, 55, 23);
		back.setBorder(finalStyle);
		back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		back.setContentAreaFilled(false);
		back.setOpaque(true);
		back.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				frame.dispose();
				new AdminFrame();
			} 
		} );
		back.setBackground(Color.CYAN.brighter());
		frame.getContentPane().add(back);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 70, 534, 357);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		table.setRowSelectionAllowed(false);
		table.setAlignmentX(Component.RIGHT_ALIGNMENT);
		DefaultTableModel dtm = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Id_Frase", "Tema", "Corpo"
				}
				) {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return column == 1 || column ==2;
			} 
		};
		table.setModel(dtm);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(270);

		keys = phrases.keySet();
		lastId = keys.size();
		kList = new ArrayList<Integer>(keys) ;        //set -> list

		//Sort the list
		Collections.sort(kList);

		for(Integer c: kList) {
			Phrases p = phrases.get(c);
			dtm.addRow(new Object[] {c , p.getTema(), p.getCorpo() });
		}

		scrollPane.setViewportView(table);

		frame.getContentPane().setLayout(null);

		delete = new JButton("Elimina");
		delete.setBounds(343, 24, 89, 23);
		delete.setBorder(finalStyle);
		delete.setToolTipText("Elimina i file attualmente selezionati");
		delete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		delete.setFont(new Font("Dialog", Font.BOLD, 17));
		delete.setContentAreaFilled(false);
		delete.setOpaque(true);
		delete.setBackground(Color.CYAN.brighter());
		delete.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				removeSelectedRows(table);
				table.setModel(dtm);
				table.repaint();
			} 
		} );
		frame.getContentPane().add(delete);

		save = new JButton("Salva");
		save.setBounds(256, 437, 89, 23);
		save.setBorder(finalStyle);
		save.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		save.setFont(new Font("Dialog", Font.BOLD, 17));
		save.setContentAreaFilled(false);
		save.setOpaque(true);
		save.setBackground(Color.CYAN.brighter());
		save.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				DefaultTableModel dtm = new DefaultTableModel(
						new Object[][] {
						},
						new String[] {
								"Id_Frase", "Tema", "Corpo"
						}
						) {

					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int row, int column) {
						return column == 1 || column ==2;
					} 
				};
				Object[][] phrases = getTableData(table);
				int nRow = phrases.length;
				for (int i = 0 ; i < nRow ; i++) 
					dtm.addRow(new Object[] {phrases[i][0], phrases[i][1], phrases[i][2]});

				table.setModel(dtm);		
				table.getColumnModel().getColumn(0).setResizable(false);
				table.getColumnModel().getColumn(1).setResizable(false);
				table.getColumnModel().getColumn(2).setResizable(false);
				table.getColumnModel().getColumn(2).setPreferredWidth(270);
				table.repaint();
				try {
					AdminRdF.getSt().updatePhrases(getTableData(table));
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			} 
		} );
		frame.getContentPane().add(save);

		importExc = new JButton("Importa(Excel)");
		importExc.setBounds(171, 23, 136, 26);
		importExc.setBorder(finalStyle);
		importExc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		importExc.setFont(new Font("Dialog", Font.BOLD, 17));
		importExc.setContentAreaFilled(false);
		importExc.setOpaque(true);
		importExc.setToolTipText("Inserire solo file in formato csv");
		importExc.setBackground(Color.CYAN.brighter());
		importExc.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				JFileChooser fileChooser = new JFileChooser();
				int result= fileChooser.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
					String url = fileChooser.getSelectedFile().getAbsolutePath();
					if(url.contains(".csv")) {
						SendCsvFile(url);
						
						DefaultTableModel dtm = new DefaultTableModel(
								new Object[][] {
								},
								new String[] {
										"Id_Frase", "Tema", "Corpo"
								}
								) {

							private static final long serialVersionUID = 1L;

							public boolean isCellEditable(int row, int column) {
								return column == 1 || column ==2;
							} 
						};
						
						
						try {
							phrases = AdminRdF.getSt().getPhrases();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						keys = phrases.keySet();
						lastId = keys.size();
						kList = new ArrayList<Integer>(keys) ;        //set -> list

						//Sort the list
						Collections.sort(kList);

						for(Integer c: kList) {
							Phrases p = phrases.get(c);
							dtm.addRow(new Object[] {c , p.getTema(), p.getCorpo() });
						}
						table.setModel(dtm);
						table.getColumnModel().getColumn(0).setResizable(false);
						table.getColumnModel().getColumn(1).setResizable(false);
						table.getColumnModel().getColumn(2).setResizable(false);
						table.getColumnModel().getColumn(2).setPreferredWidth(270);
					} else {
						JOptionPane.showMessageDialog(frame, "Ops, formato del file non valido", "Errore", JOptionPane.ERROR_MESSAGE);
					}					
				}
			} 
		} );
		frame.getContentPane().add(importExc);

		addPhrase = new JButton("Aggiungi");
		addPhrase.setBounds(464, 23, 101, 26);
		addPhrase.setBorder(finalStyle);
		addPhrase.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addPhrase.setFont(new Font("Dialog", Font.BOLD, 17));
		addPhrase.setContentAreaFilled(false);
		addPhrase.setOpaque(true);
		addPhrase.setBackground(Color.CYAN.brighter());
		addPhrase.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				lastId++;
				dtm.addRow(new Object[] {lastId , "<< tema >>", "<< frase >>" });
				table.setModel(dtm);
				table.getColumnModel().getColumn(0).setResizable(false);
				table.getColumnModel().getColumn(1).setResizable(false);
				table.getColumnModel().getColumn(2).setResizable(false);
				table.getColumnModel().getColumn(2).setPreferredWidth(270);
				table.repaint();
				JScrollBar vertical = scrollPane.getVerticalScrollBar();
				frame.validate();
				vertical.setValue(vertical.getMaximum());
			} 
		} );
		frame.getContentPane().add(addPhrase);

		frame.setSize(600, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.BLUE.darker());
		frame.setVisible(true);
	}

	public void removeSelectedRows(JTable table){
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		int[] rows = table.getSelectedRows();
		for(int i=0;i<rows.length;i++){
			model.removeRow(rows[i]-i);
		}
	}

	public Object[][] getTableData (JTable table) { 
		DefaultTableModel dtm = (DefaultTableModel) table.getModel(); 
		int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount(); 
		Object[][] tableData = new Object[nRow][nCol]; 
		for (int i = 0 ; i < nRow ; i++) 
			for (int j = 0 ; j < nCol ; j++) 
				tableData[i][j] = dtm.getValueAt(i,j); 
		return tableData; 
	}

	public void SendCsvFile(String url){

		ArrayList<Object[]> rowList = new ArrayList<Object[]>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(url),"UTF-8"))) {
			String line;
			int i=0;
			while ((line = br.readLine()) != null) {
				line=i+","+line;
				Object[] lineItems = line.split(",");
				rowList.add(lineItems);
				i++;
			}
			br.close();
		}
		catch(Exception e){ 
		}
		rowList.remove(0);
		Object[][] matrix = new Object[rowList.size()][];
		for (int i = 0; i < rowList.size(); i++) {
			Object[] row = rowList.get(i);
			matrix[i] = row;
		}

		try {
			//invio dati sotto forma di matrice al metodo updatePhrases
			AdminRdF.getSt().updatePhrases(matrix);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
