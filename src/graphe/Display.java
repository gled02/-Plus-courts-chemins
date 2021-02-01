package graphe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Display {
	
	// ATTRIBUTS
	
	private JFrame mainFrame;
	private JLabel grapheFile;
	private JLabel algo;
	private JLabel graphe;
	private JTextField grapheFileName;
	private JTextArea grapheDisplay;
	private JButton grapheFileButton;
	private JButton clearButton;
	private JRadioButton algo1;
	private JRadioButton algo2;
	private JComboBox grapheType;
	private DisplayModel model;
	private JFileChooser chooser;
	private File f;
	
	// CONSTRUCTEURS
	
	public Display() throws IOException {
		createModel();
		createView();
		placeComponents();
		createController();
	}
	
	// COMMANDES
	
	/**
     * Rend l'application visible au centre de l'écran.
    */
	public void display() {
		refresh();
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
	// OUTILS
	
	private void createModel() throws IOException {
		model = new StdDisplayModel();
	}
	
	private void createView() {
		final int width = 600;
		final int height = 300;
		mainFrame = new JFrame("Lieu de rendez-vous optimal");
		mainFrame.setPreferredSize(new Dimension(width, height));
		
		grapheFile = new JLabel("Fichier du graphe: ");
		algo = new JLabel("Algos: ");
		graphe = new JLabel("Représentation: ");
	
		grapheFileButton = new JButton("Parcourir...");
		clearButton = new JButton("Effacer");
		algo1 = new JRadioButton("Algo1");
		algo2 = new JRadioButton("Algo2");

		grapheType = new JComboBox();
		grapheType.setModel(new DefaultComboBoxModel());
		grapheType.addItem(null);
		grapheType.addItem("Graphe");
		grapheType.addItem("TGraphe");
	
		grapheFileName = new JTextField();
		final int nbColumns = 20;
		grapheFileName.setColumns(nbColumns);
		grapheDisplay = new JTextArea();
		grapheDisplay.setEditable(false);
		
		PrintStream printStream = new PrintStream(
				new CustomOutputStream(grapheDisplay));
		System.setOut(printStream);
		System.setErr(printStream);
	}
	
	private void placeComponents() { 
		JPanel p = new JPanel(new FlowLayout()); {
			p.add(grapheFile);
			p.add(grapheFileName);
			p.add(grapheFileButton);
		}
		mainFrame.add(p, BorderLayout.NORTH);
		
		p = new JPanel(new GridLayout(3, 0)); {
			JPanel q = new JPanel(new BorderLayout()); {
					JPanel s = new JPanel(new FlowLayout()); {
						s.add(algo, new FlowLayout(FlowLayout.RIGHT));
						s.add(algo1, new FlowLayout(FlowLayout.LEFT));
						s.add(algo2, new FlowLayout(FlowLayout.LEFT));
					}
					q.add(s, BorderLayout.SOUTH);
				}
			p.add(q);
			
		    q = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
		    	JPanel s = new JPanel(new FlowLayout(FlowLayout.RIGHT)); {
		    		s.add(graphe);
		    	}
		    	q.add(s);
		    	s = new JPanel(new FlowLayout(FlowLayout.LEFT)); {
		    		s.add(grapheType);
		    	}
			    q.add(s);
			    
		    }
		    p.add(q);
		}	
		mainFrame.add(p, BorderLayout.WEST);
		
		
		p = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
			p.add(clearButton);
		}
		mainFrame.add(p, BorderLayout.SOUTH);
		
		
		JScrollPane pC = new JScrollPane(grapheDisplay);
		mainFrame.add(pC, BorderLayout.CENTER);
	}
	
	private void createController() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		model.addObserver(new Observer() {
			@Override
	    	public void update(Observable o, Object arg) {
	    		refresh();
	    	}
	    });
		
		clearButton.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent e) {
				model.clear();
			}
		});
		
		grapheFileName.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent e) {
				f = new File(grapheFileName.getText());
				model.changeFor(f);
			}
		});
		
		grapheFileButton.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent e) {
				 chooser = new JFileChooser();
				 int v = chooser.showOpenDialog(mainFrame);
				 if (v == JFileChooser.APPROVE_OPTION) {
					 model.changeFor(chooser.getSelectedFile());
				 }
			}
		});
		
		grapheType.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent e) {
				switch (grapheType.getSelectedIndex()) {
					case 1:
						model.setTypeDisplay(TypeDisplay.GRAPHE);
						break;
					case 2:
						model.setTypeDisplay(TypeDisplay.TGRAPHE);
						break;
					default:
				}
			}
		});
		
		algo1.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent e) {
				if (model.getGraph() == null) {
					JOptionPane.showMessageDialog(null,
							"Le graphe n'a pas été construit",
							"Erreur !", JOptionPane.ERROR_MESSAGE);
					algo1.setSelected(false);
				} else {
					if (algo1.isSelected()) {
						model.setTypeDisplay(TypeDisplay.ALGO1);
					}
				}
			}
		});
		
		
		algo2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getGraph() == null) {
					JOptionPane.showMessageDialog(null,
							"Le graphe n'a pas été construit",
							"Erreur !", JOptionPane.ERROR_MESSAGE);
					algo2.setSelected(false);
				} else {
					if (algo2.isSelected()) {
						model.setTypeDisplay(TypeDisplay.ALGO2);
					}
				}
			}
		});
	}
	
	private void refresh() {
		if (model.getTypeDisplay() == null) {
			algo1.setSelected(false);
			algo2.setSelected(false);
			grapheType.setSelectedIndex(0);
			grapheDisplay.setText("");
		}
		
		if (model.getFile() != null) {
			grapheType.setEnabled(true);
			algo1.setEnabled(true);
			algo2.setEnabled(true);
			grapheFileName.setText(model.getFile().getAbsolutePath());
			model.display();
		} else {
			grapheFileName.setText("");
			grapheType.setEnabled(false);
			algo1.setEnabled(false);
			algo2.setEnabled(false);
		}
	}
	
	// POINT D'ENTREE
	 
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new Display().display();
				} catch (IOException e) {
					
				}
			}
		});
	}
	
}
