package graphe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

import util.Contract;

import javax.swing.JOptionPane;



public class StdGraph implements Graph {
	
	// ATTRIBUTS
	
	private int nbOfNodes;
	private List<CharSequence> nameOfNodes = new ArrayList<CharSequence>();
	private int nbOfPlaces;
	private List<CharSequence> nameOfPlaces = new ArrayList<CharSequence>();
	private Couple<CharSequence, CharSequence> nameOfInitialNodes;
	private List<Triplet<CharSequence, CharSequence, Integer>> arcs = 
			new ArrayList<Triplet<CharSequence, CharSequence, Integer>>();
	
	// CONSTRUCTEUR
	
	public StdGraph(File file) {
		Contract.checkCondition(file != null, "StdGraph: Invalid file");
		CharSequence[] lineSplit;
		String line;
		int i = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				switch (i) {
				case 0:
					nbOfNodes = Integer.parseInt(line);
					break;
				case 1:
					lineSplit = line.split(";");
					for (CharSequence k : lineSplit) {
						nameOfNodes.add(k);
					}
					break;
				case 2:
					nbOfPlaces = Integer.parseInt(line);
					break;
				case 3:
					lineSplit = line.split(";");
					for (CharSequence k : lineSplit) {
						nameOfPlaces.add(k);
					}
					break;
				case 4:
					String[] lineSplitCouple = line.split(";");
					nameOfInitialNodes =
							new StdCouple<CharSequence, CharSequence>(
							lineSplitCouple[0], lineSplitCouple[1]);
					break;
				case 5:
					String[] lineSplitTriplet = line.split(";");
					for (int j = 0; j < lineSplitTriplet.length; ++j) {
						CharSequence[] t = lineSplitTriplet[j].split(",");
						arcs.add(
						  new StdTriplet<CharSequence, CharSequence, Integer>(
									t[0], t[1], Integer.parseInt(
											(String) t[2])));
					}
					break;
				 default:
				}
				++i;
			}
			br.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erreur fichier",
					"Erreur !", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// REQUETES
	
	@Override
	public int getNbOfNodes() {
		return nbOfNodes;
	}
	
	@Override
	public List<CharSequence> getNameOfNodes() {
		return nameOfNodes;
	}
	
	@Override
	public int getNbOfPlaces() {
		return nbOfPlaces;
	}
	
	@Override
	public List<CharSequence> getNameOfPlaces() {
		return nameOfPlaces;
	}
	
	@Override
	public Couple<CharSequence, CharSequence> getNameOfInitialNodes() {
		return nameOfInitialNodes;
	}
	
	@Override
	public List<Triplet<CharSequence, CharSequence, Integer>> getArcs() {
		return arcs;
	}
	
	// COMMANDES
	
	@Override
	public Triplet<CharSequence, CharSequence, Integer>
	getArc(CharSequence node1, CharSequence node2) {
		for (Triplet<CharSequence, CharSequence, Integer> t : getArcs()) {
			if (t.getInitialNode().equals(node1) 
					&& t.getTerminalNode().equals(node2)) {
				return t;
			}
		}
		return null;
	}
}
