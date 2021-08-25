package interfacciaGioco;

import javax.swing.BorderFactory;
import javax.swing.border.*;

import gioco.*;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.rmi.RemoteException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;



public class WheelPanel extends JPanel {
	private static final long serialVersionUID=1;
	
	private static final Font FONT = new Font("Open Sans Light", Font.BOLD, 20);

	private static WheelOfFortune game;

	private static FirstPanel firstP;

	private static PhrasePanel phraseP;

	private static WheelFrame wheelFrame;

	private static final Map<String, Integer> VALUES;

	private static final int DEGREES = 15; //20

	private static final String[] IMG_NAME;

	private final Map<String, Image> IMAGES;

	private List<String> ImagesNames; //contiene le immagini relative ad ogni spicchio della ruota

	private final AudioPlayer WHEEL_START;

	private static AudioPlayer GREAT;

	private static  AudioPlayer BAD_CHOICE;

	private static  AudioPlayer VOWELS_TERMINATED;

	private ButtonListener buttonListener;

	private Timer wheelTimer;

	private static Timer timer;

	private int nLaps, seconds;

	private Random rnd;

	private static String spaceLanded;

	private JPanel lettersPanel;

	private static JButton[] letterButtons;

	private static JButton startWheel;

	private static JButton solvePhrase;

	private JButton quit;

	private static JTextArea wheelState;

	private static JTextArea timeA;

	private JLabel timeL, mancheL;
	
	private static JTextField mancheF;

	private boolean wait = false;

	static {

		IMG_NAME =
				new String[] {"300B.png", "600R.png", "400C.png", "500V.png",
						"300G.png", "600RS.png", "1000B.png", "PERDER.png", "300AZ.png",
						"400V.png", "700G.png", "500RS.png", "300B1.png", "400R.png",
						"PERDEA.png", "300V.png", "500G.png", "400RS.png", "600B.png",
						"500R.png", "400C1.png", "300V.png", "Jolly.png", "PASSAV.png"};

		VALUES = new HashMap<String, Integer>();

		VALUES.put("300B", new Integer(300));
		VALUES.put("600R", new Integer(600));
		VALUES.put("400C", new Integer(400));
		VALUES.put("500V", new Integer(500));
		VALUES.put("300G", new Integer(300));
		VALUES.put("600RS", new Integer(600));
		VALUES.put("1000B", new Integer(1000));
		VALUES.put("300AZ", new Integer(300));
		VALUES.put("400V", new Integer(400));
		VALUES.put("700G", new Integer(700));
		VALUES.put("500RS", new Integer(500));
		VALUES.put("300B1", new Integer(300));
		VALUES.put("400R", new Integer(400));
		VALUES.put("300V", new Integer(300));
		VALUES.put("500G", new Integer(500));
		VALUES.put("400RS", new Integer(400));
		VALUES.put("600B", new Integer(600));
		VALUES.put("500R", new Integer(500));
		VALUES.put("400C1", new Integer(400));
		VALUES.put("300V", new Integer(300));
		VALUES.put("Jolly", new Integer(0));
		VALUES.put("PASSAV", new Integer(0));
		VALUES.put("PERDEA", new Integer(0));
		VALUES.put("PERDER", new Integer(0));
	}

	public WheelPanel(WheelOfFortune g, FirstPanel fP, PhrasePanel phP, WheelFrame frame) throws UnsupportedAudioFileException, 
	IOException, LineUnavailableException {
		super();

		wheelFrame = frame;
		game = g;
		firstP = fP;
		phraseP = phP;


		WHEEL_START = new AudioPlayer("suoni/spinningWheel.wav");
		GREAT = new AudioPlayer("suoni/goodGuess.wav");
		BAD_CHOICE = new AudioPlayer("suoni/badGuess.wav");
		//BANKRUPT = new AudioPlayer("suoni/bankrupt.wav");
		VOWELS_TERMINATED = new AudioPlayer("suoni/noMoreVowels.wav");

		Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
		IMAGES = new HashMap<String, Image>();

		for (String imageName : IMG_NAME) {
			IMAGES.put(imageName,defaultToolkit.getImage("immagini/" + imageName));
		}

		IMAGES.put("arrow.png",defaultToolkit.getImage("immagini/arrow.png"));

		rnd = new Random();

		wheelTimer = new Timer(25, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String value = ImagesNames.get(0);
				ImagesNames.remove(0);
				ImagesNames.add(value);
				wheelTimer.setDelay((wheelTimer.getDelay())+5);
				repaint();
				nLaps = rnd.nextInt(150)+200;
				if(wheelTimer.getDelay() >= nLaps) {
					wheelTimer.stop();
					WHEEL_START.pause();
					if(!wheelTimer.isRunning()) {
						spaceLanded = ImagesNames.get(11);	 
						resultManager(spaceLanded); 
						try {
							String x = spaceLanded.substring(0,spaceLanded.indexOf('.'));
							int v = VALUES.get(x); 
							if(v>0) {
								String s = String.valueOf(v);
								Client.notifyWheelStop("$"+ s);
							} else {
								if((x.contains("PASSA")) && (game.getBonus()>0)) {
									String s = String.valueOf(v);
									Client.notifyWheelStop(s);
								}
							}
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						wheelTimer.setDelay(25); //reset del timer
					}
				}
			}
		});

		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timeA.setText("" + seconds);
				if(seconds==0) {
					wheelState.setText("Tempo Scaduto");
					JOptionPane.getRootFrame().dispose();
					if(!checkBonus()) {
						tempoScaduto();
						try {
							Client.updateMove(WheelOfFortune.getManche(), Client.email, 0, "TS");
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					}
					timeA.setText("");
					timer.stop();
				}  
				seconds = seconds-1;
			}
		});

		letterButtons = new JButton[26];
		buttonListener = new ButtonListener();

		for (int i = 0; i < letterButtons.length; i++) {
			letterButtons[i] = new JButton("" + (char)(i + 65));
			letterButtons[i].addActionListener(buttonListener);
			letterButtons[i].setEnabled(false);
		}

		lettersPanel = new JPanel();
		lettersPanel.setPreferredSize(new Dimension(300, 200));
		lettersPanel.setLayout(new GridLayout(6, 5, 2, 2));

		//vocali rosse e consonanti blu
		for (int i = 0; i < letterButtons.length; i++) {
			letterButtons[i].setBackground((i == 0 || i == 4 || i == 8 || i == 14 || i == 20) ? Color.RED : Color.BLUE);
			lettersPanel.add(letterButtons[i]);
		}
		
		mancheL = new JLabel("Manche:", JLabel.RIGHT);
		mancheL.setFont(FONT);
		
		mancheF = new JTextField("" + WheelOfFortune.getManche(), JTextField.CENTER);
		mancheF.setPreferredSize(new Dimension(20,20));
		mancheF.setEditable(false);
        mancheF.setFont(FONT);

		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		
		Border style = BorderFactory.createRaisedBevelBorder();
		Border border = BorderFactory.createEtchedBorder(Color.RED, Color.RED.darker());
		Border finalStyle = BorderFactory.createCompoundBorder(border, style);

		startWheel = new JButton(" GIRA LA RUOTA ");
		startWheel.addActionListener(buttonListener);
		startWheel.setBorder(raisedbevel);
		startWheel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));

		solvePhrase = new JButton(" RISOLVI FRASE ");
		solvePhrase.addActionListener(buttonListener);
		solvePhrase.setBorder(raisedbevel);
		solvePhrase.setFont(new Font("Comic Sans MS", Font.BOLD, 12));

		quit = new JButton(" ESCI ");
		quit.addActionListener(buttonListener);
		quit.setContentAreaFilled(false);
		quit.setOpaque(true);
		quit.setBackground(Color.RED);
		quit.setBorder(finalStyle);
		quit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		quit.setFont(new Font("Comic Sans MS", Font.BOLD, 12));

		timeL = new JLabel("Timer:", JLabel.LEFT);
		timeL.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		timeA = new JTextArea();
		timeA.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		timeA.setEditable(false);

		wheelState = new JTextArea();
		wheelState.setFont(new Font("Tahoma", Font.PLAIN, 18));
		wheelState.setEditable(false);
		wheelState.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		wheelState.setLineWrap(true);
		wheelState.setWrapStyleWord(true);
		
		Box mancheBox = Box.createHorizontalBox();
		mancheBox.add(Box.createHorizontalStrut(50));
		mancheBox.add(mancheL);
		mancheBox.add(Box.createHorizontalStrut(5));
		mancheBox.add(mancheF);
		
		mancheBox.setMaximumSize(new Dimension(270,25));

		Box optionButtonsBox = Box.createVerticalBox();
		optionButtonsBox.add(mancheBox);
		optionButtonsBox.add(Box.createVerticalStrut(30));
		optionButtonsBox.add(startWheel);
		optionButtonsBox.add(Box.createVerticalStrut(10));
		optionButtonsBox.add(solvePhrase);
		optionButtonsBox.add(Box.createVerticalStrut(40));
		optionButtonsBox.add(quit);
		
		optionButtonsBox.setMaximumSize(new Dimension(300, 300));

		Box timerButtonsBox = Box.createHorizontalBox();
		timerButtonsBox.add(timeL);
		timerButtonsBox.add(Box.createHorizontalStrut(5));
		timerButtonsBox.add(timeA);
		timerButtonsBox.add(Box.createHorizontalStrut(150));

		Box letterButtonsBox = Box.createVerticalBox();
		letterButtonsBox.add(Box.createVerticalStrut(50));
		letterButtonsBox.add(lettersPanel);
		letterButtonsBox.add(Box.createVerticalStrut(10));
		letterButtonsBox.add(wheelState);
		letterButtonsBox.add(Box.createVerticalStrut(10));
		letterButtonsBox.add(timerButtonsBox);
		letterButtonsBox.add(Box.createVerticalStrut(200));

		Box outsideBox = Box.createHorizontalBox();
		outsideBox.add(Box.createHorizontalStrut(20));
		outsideBox.add(optionButtonsBox);
		outsideBox.add(Box.createHorizontalStrut(550));
		outsideBox.add(letterButtonsBox);
		outsideBox.add(Box.createHorizontalStrut(20));
		
		outsideBox.setPreferredSize(new Dimension(1000, 450));

		add(outsideBox);

		newGame();
	}

	public void newGame() {
		wheelState.setText("Benvenuti a: LA RUOTA DELLA FORTUNA!\n"
				+ "Gira la ruota per indovinare la frase nascosta");
		firstP.resetValori();

		phraseP.newGame();

		ImagesNames = new ArrayList<String>();

		for (String name : IMG_NAME) {
			ImagesNames.add(name);
		}

		bloccaConsonanti(false);
		bloccaVocali(false);
		solvePhrase.setEnabled(false);
		startWheel.setEnabled(true);

		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2D = (Graphics2D)g.create();

		// imposta l'origine e ruota (485, 190)
		g2D.translate(485, 190);
		g2D.rotate(Math.toRadians(-107));

		// Disegno lo spazio per la ruota (-29, -25)
		for (int i = 0; i < ImagesNames.size(); ++i) {
			g2D.drawImage(IMAGES.get(ImagesNames.get(i)), -29, -25, this);  
			g2D.rotate(Math.toRadians(-DEGREES));
		}

		// Disegna il puntatore della ruota (210, 180)
		g.drawImage(IMAGES.get("arrow.png"), 275, 180, this);

		// reset dell'origine
		g2D.translate(-485, -285);
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton)e.getSource();

			for (int c = 0; c < letterButtons.length; c++) {
				if (source == letterButtons[c]) {	
					timer.stop();
					timeA.setText("");
					int occorrenze = 0;
					char l = (char)(c+65);
					String s = "" + l;
					if (c == 0 || c == 4 || c == 8 || c == 14 || c == 20) {
						if(WheelOfFortune.isAllVowelsGuessed()) {
							BAD_CHOICE.play();
							PopUp pu = new PopUp("Vocali Terminate", "Non puoi selezionare le vocali se sono finite...", Color.RED, null);
							pu.add();
							checkBonus();
						} else {
							if(wheelState.getText().contains("consonante")) {
								PopUp pu = new PopUp("Lettera Errata", "Hai selezionato una vocale...", Color.RED, null);
								pu.add();
								checkBonus();
							} else if(firstP.getScore()<1000){
								PopUp pu = new PopUp("Punteggio Insufficiente", "Non hai un ammontare di punti sufficiente", Color.RED, null);
								pu.add();
								checkBonus();
							} else if(game.getGuessedLetters().contains(l)) {
								PopUp pu = new PopUp("Lettera Ripetuta", "La lettera selezionata è già stata inserita in precedenza", Color.RED, null);
								pu.add();
								checkBonus();
							} else {
								occorrenze = game.revealsLetter((char)(c + 65));
								firstP.aggiungiPunti(-1000); //sottrae 1000$ per ogni vocale comprata
								
								if(WheelOfFortune.isAllConsonantsGuessed()) {
									VOWELS_TERMINATED.play();
									PopUp pu = new PopUp("Vocali Terminate", "Attenzione, le vocali sono terminate \nevita di selezionarle", Color.RED, null);
									pu.add();
									try {
										Client.updateMove(WheelOfFortune.getManche(),Client.email, -1000 ,s+"allvoc");
									} catch (RemoteException e1) {
										e1.printStackTrace();
									}
								} else {
									try {
										Client.updateMove(WheelOfFortune.getManche(),Client.email, -1000 ,s+"voc");
									} catch (RemoteException e1) {
										e1.printStackTrace();
									}
								}
								
								if (occorrenze > 0) {
									GREAT.play();
									wheelState.setText((occorrenze == 1 ? "C'è " : "Ci sono ")
											+ occorrenze + " " + (char)(c + 65)
											+ " nella frase! Gira la"
											+ "ruota o compra una vocale");
									seconds = 5;
									timer.start();
								} else if(game.getGuessedLetters().contains(l)) {
									PopUp pu = new PopUp("Lettera Ripetuta", "La lettera selezionata è già stata inserita in precedenza\nil turno passa al prossimo giocatore", Color.RED, null);
									pu.add();
									try {
										Client.changeTurn();
									} catch (RemoteException e1) {
										e1.printStackTrace();
									}
								} else {
									BAD_CHOICE.play();
									if(!checkBonus()) {
										wheelState.setText("Ci dispiace, non ci sono "
												+ (char)(c + 65) + " nella frase");
									}
								}
							}
						}
					} else {
						//se la lettera è una consonante
						
						if(WheelOfFortune.isAllConsonantsGuessed()) {
							BAD_CHOICE.play();
							PopUp pu = new PopUp("Consonanti Terminate", "Non puoi selezionare le consonanti se sono finite...", Color.RED, null);
							pu.add();
							checkBonus();
						} else if(game.getGuessedLetters().contains(l)) {
							PopUp pu = new PopUp("Lettera Ripetuta", "La lettera selezionata è già stata inserita in precedenza", Color.RED, null);
							pu.add();
							checkBonus();
						} else {
							solvePhrase.setEnabled(true);
							startWheel.setEnabled(true);
							occorrenze = game.revealsLetter((char)(c + 65));
							if (occorrenze > 0) {
								seconds = 5;
								timer.start();
								GREAT.play();

								int punti = VALUES.get(spaceLanded.substring(0,spaceLanded.indexOf('.')));

								firstP.aggiungiPunti(punti * occorrenze);
								
								if(WheelOfFortune.isAllConsonantsGuessed()) {
									VOWELS_TERMINATED.play();
									PopUp pu = new PopUp("Consonanti Terminate", "Attenzione, le consonanti sono terminate \nevita di selezionarle", Color.RED, null);
									pu.add();
									try {
										Client.updateMove(WheelOfFortune.getManche(),Client.email, (punti * occorrenze),s+"allcns");
									} catch (RemoteException e1) {
										e1.printStackTrace();
									}
								} else {
									try {
										Client.updateMove(WheelOfFortune.getManche(),Client.email, (punti * occorrenze),s+"cns");
									} catch (RemoteException e1) {
										e1.printStackTrace();
									}
								}

								wheelState.setText((occorrenze == 1 ? "C'è" : "Ci sono") + " "
										+ occorrenze + " " + (char)(c + 65)
										+ " nella frase! Hai guadagnato $" + punti * occorrenze
										+ "! Gira ancora la ruota o "
										+ "compra una vocale");
								
								if(WheelOfFortune.isAllConsonantsGuessed()) {
									solvePhrase.setEnabled(true);
									bloccaConsonanti(true);
								} else {
									bloccaConsonanti(false);
								}
								bloccaVocali(true);
							} else {
								BAD_CHOICE.play();
								try {
									Client.updateMove(WheelOfFortune.getManche(),Client.email, 0 ,s);
								} catch (RemoteException e1) {
									e1.printStackTrace();
								}
								PopUp pu = new PopUp("Lettera Errata", "Ci dispiace, la lettera non è presente nella frase misteriosa", Color.RED, null);
								pu.add();
								checkBonus();
							}
						}
						
					}
				}
			}


			if (source == startWheel) {
				if(WheelOfFortune.isAllConsonantsGuessed()) {
					PopUp pu = new PopUp("Consonanti Terminate", "Non puoi girare la ruota se le consonanti sono finite...\nil turno passa al prossimo giocatore", Color.RED, null);
					pu.add();
					try {
						Client.changeTurn();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						Client.updateMove(WheelOfFortune.getManche(), Client.email, 0, "GR");
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					if(timer.isRunning()) {
						timer.stop();
						timeA.setText("");
					}
					WHEEL_START.loop();
					wheelTimer.start();
					solvePhrase.setEnabled(false);
					bloccaTasti(false);
					wheelState.setText("La ruota gira...");
				}
			}else if (source == solvePhrase) {
				solvePhrase.setEnabled(false);
				if(timer.isRunning()) {
					timer.stop();
					timeA.setText("");
				} 
				seconds = 10;
				timer.start();
				
				String risolvi = JOptionPane.showInputDialog(null,"Perfavore, risolvi la frase: ",
						"Risolvi la Frase",JOptionPane.PLAIN_MESSAGE);
				

				if (risolvi != null) {
					risolvi = risolvi.replaceAll("'", " ");
					risolvi = risolvi.replaceAll("è", "e");
					risolvi = risolvi.replaceAll("à", "a");
					risolvi = risolvi.replaceAll("ì", "i");
					risolvi = risolvi.replaceAll("ò", "o");
					risolvi = risolvi.replaceAll("ù", "u");
					
					StringBuilder risolviT = new StringBuilder();
					String frase = game.getPhrase();
					StringBuilder fraseT = new StringBuilder();
					for (int i = 0; i < frase.length(); ++i) {
						if (frase.charAt(i) != ' ') {
							fraseT.append(frase.charAt(i)); //aggiunge la sequenza di lettere di frase a fraseT
						}
					}
					
					for (int i = 0; i < risolvi.length(); ++i) {
						if (risolvi.charAt(i) != ' ') {
							risolviT.append(risolvi.charAt(i)); //aggiunge la sequenza di lettere inserite a risolviT
						}
					}

					if (risolviT.toString() != "") {
						timer.stop();
						timeA.setText("");
						if (risolviT.toString().compareToIgnoreCase(fraseT.toString()) == 0) { //se risolviT è uguale a fraseT
							vittoria();
						} else {
							bloccaTasti(false);
							PopUp p = new PopUp("Frase Errata", "Ci dispiace, la frase inserita non corrisponde \nalla frase misteriosa", Color.RED, null);
							p.add();
							wheelState.setText("Frase errata, hai perso il turno.");
							try {
								Client.changeTurn();
							} catch (RemoteException ex) {
								ex.printStackTrace();
							}
						}
					}
				} else {
					timer.stop();
					timeA.setText("");
					PopUp pu = new PopUp("Azione Proibita", "Non puoi tornare indietro!\nIl turno passa al prossimo giocatore", Color.RED, null);
					pu.add();
					try {
						Client.changeTurn();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}

			} else if (source == quit) {
				if(timer.isRunning()) {
					timer.stop();
				}
				wheelFrame.dispose();
				new PlayerFrame();
				try {
					Client.updateMove(WheelOfFortune.getManche(),Client.email, 0, "esce");
					Client.endGame();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}	

			phraseP.repaint();
		}
	} 

	private void vittoria() { //controllo vincitore da aggiungere
		if(WheelOfFortune.getManche() == 5){
			game.revealsPhrase();
			phraseP.repaint();
			try {
				String winner = Client.victory(firstP.getScore());
				if(winner.equals(Client.email)) {
					PopUp pu = new PopUp("Vittoria Partita", "Congratulazioni, hai vinto la mance " + WheelOfFortune.getManche() + " e la partita!", Color.BLUE, null);
					pu.add();
				} else {
					PopUp pu = new PopUp("Game Over", "Hai vinto la mance " + WheelOfFortune.getManche() + " ma hai perso la partita!", Color.RED, null);
					pu.add();
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			wheelFrame.dispose();
			try {
				Client.getSt().removeObs(Client.getClientStub());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			new PlayerFrame();
		} else {
			game.revealsPhrase();
			phraseP.repaint();
			firstP.addTotScore(firstP.getScore()); 
			PhrasePanel.resetPoints();
			PopUp pu = new PopUp("Vittoria Manche", "Congratulazioni, hai vinto la mance " + WheelOfFortune.getManche() + "!", Color.GREEN, null);
			pu.add();
			wheelState.setText("Hai vinto la mance numero" + WheelOfFortune.getManche());
			addManche();
			try {
				Client.updateManche(firstP.getScore());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			nuovaManche();
		}
	}

	public static void updateMancheObs() {
		game.revealsPhrase();
		phraseP.repaint();
		PopUp pu = new PopUp("Manche Terminata", "Un giocatore ha vinto, cambia la manche", Color.RED, null);
		pu.add();
		wheelState.setText("Hai perso la manche numero: " + WheelOfFortune.getManche() + " ma puoi ancora vincere la partita!");
		addManche();
		nuovaManche();
		startWheel.setEnabled(false);
	}

	private boolean checkBonus() {
		if(game.getBonus() > 0) {
			wheelState.setText("Ci dispiace, hai perso il turno.\nMa hai la possibilità di utilizzare il jolly");
			seconds = 5;
			timer.start();
			int r = JOptionPane.showConfirmDialog(null, "Vuoi utilizzare il Jolly?");
			if(r==0) { //il giocatore mantiene il turno
				timer.stop();
				try {
					Client.updateMove(WheelOfFortune.getManche(),Client.email, 0 ,"UJ");
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				firstP.diminuisciBonus();
				startWheel.setEnabled(true);
				solvePhrase.setEnabled(true);
				wheelState.setText("Hai utilizzato il Jolly!\nOra puoi continuare a giocare il tuo turno");
				return true;
			}else {
				timer.stop();
				bloccaTasti(false);
				wheelState.setText("Hai perso il turno.");
				try {
					Client.changeTurn();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				return false;
			}
		} else {
			bloccaTasti(false);
			wheelState.setText("Ci dispiace, hai perso il turno");
			try {
				Client.changeTurn();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			return false;
		}
	}

	private void resultManager(String spaceLanded) {
		if (spaceLanded.equals("PASSAV.png")) { //da modificare (passa il turno al prossimo player)
			if(!checkBonus()) {
				try {
					Client.updateMove(WheelOfFortune.getManche(),Client.email, 0 ,"Passa");
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		} else if (spaceLanded.contains("PERDE")) {
			bloccaTasti(false);
			firstP.resetPunti();
			wheelState.setText("Ci dispiace, hai perso un turno, e "
					+ "il tuo punteggio scende a 0");
			PopUp pu = new PopUp("Che Sfortuna!", "Hai pescato il PERDE e quindi il tuo punteggio scende a zero;\nIl turno passa al prossimo giocatore", Color.RED, null);
			pu.add();
			try {
				Client.updateMove(WheelOfFortune.getManche(),Client.email, 0 ,"Perde");
				Client.changeTurn();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		} else if (spaceLanded.equals("Jolly.png")){
			firstP.aggiungiBonus();
			startWheel.setEnabled(true);
			solvePhrase.setEnabled(true);
			bloccaLettere(false);
			try {
				Client.updateMove(WheelOfFortune.getManche(), Client.email, 0, "TJ");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			wheelState.setText("Hai guadagnato un Jolly!\nGira La ruota per continuare");
		}else {
			startWheel.setEnabled(false); 
			bloccaConsonanti(true);
			bloccaVocali(true);
			wheelState.setText("Seleziona una consonante");
			wait = true;
		}

		if(wait) {
			seconds = 5;
			timer.start();
			wait = false;
		}
	}

	private static void addManche() {
		game.addManche();
		mancheF.setText("" + WheelOfFortune.getManche());
	}
	
	private void tempoScaduto() {
		PopUp pu = new PopUp("Tempo Scaduto", "Ci dispiace, il tempo è scaduto", Color.RED, null);
		pu.add();
		bloccaTasti(false);
		timeA.setText("");
	}

	public static void remoteClose() throws RemoteException {
		wheelFrame.dispose();
		new PlayerFrame();
		Client.removeMe();
	}

	public static void updatePanel(String name) {
		char n = name.charAt(0);				
		game.revealsLetter(n);
		phraseP.repaint();
	}
	
	public static void setWheelState(String s) {
		wheelState.setText(s);
	}

	public static void quit(String nick) {
		Client.player = false;
		wheelFrame.dispose();
		new PlayerFrame(nick);
	}

	private static void nuovaManche() {
		if(WheelOfFortune.getManche() == 5) {
			wheelState.setText("Inizia l'ultima manche!");
		} else {
			wheelState.setText("Inizia la manche numero " + WheelOfFortune.getManche());
		}
		firstP.resetPunti();
		phraseP.newGame();		
		bloccaConsonanti(false);
		bloccaVocali(false);
		solvePhrase.setEnabled(false);
		startWheel.setEnabled(true);
	}

	private void bloccaLettere(boolean b) {
		bloccaConsonanti(b);
		bloccaVocali(b);
	}

	private static void bloccaVocali(boolean b) {
		letterButtons[0].setEnabled(b);
		letterButtons[4].setEnabled(b);
		letterButtons[8].setEnabled(b);
		letterButtons[14].setEnabled(b);
		letterButtons[20].setEnabled(b);
	}

	private static void bloccaConsonanti(boolean b) {
		for (int i = 0; i < letterButtons.length; ++i) {
			if (!(i == 0 || i == 4 || i == 8 || i == 14 || i == 20)) {
				letterButtons[i].setEnabled(b);
			}
		}
	}

	public static void bloccaTasti(boolean b) {
		bloccaConsonanti(b);
		bloccaVocali(b);
		solvePhrase.setEnabled(b);
		startWheel.setEnabled(b);
	}

	public static void wheelstart(boolean b) {
		startWheel.setEnabled(b);
	}
	
	public static void unlockSolvePhrase(boolean b) {
		solvePhrase.setEnabled(b);
	}
}

