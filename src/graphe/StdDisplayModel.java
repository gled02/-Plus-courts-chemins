package graphe;

import java.io.File;
import java.util.Observable;

import javax.swing.JOptionPane;

import util.Contract;

public class StdDisplayModel extends Observable implements DisplayModel {
	
	// ATTRIBUTS
	
	private File f;
	private Graph graph;
	private TransformedGraph tGraph;
	private TypeDisplay setDisplay;
	private OptimalPlace path;
	
	
	// CONSTRUCTEURS
	
	public StdDisplayModel() {
		f = null;
		setDisplay = null;
		path = null;
	}
	
	public StdDisplayModel(File file) {
		Contract.checkCondition(file != null, "StdDisplayModel: file invalid");
		
		f = file;
		graph = new StdGraph(file);
		tGraph = new StdTransformedGraph(graph);
		setDisplay = null;
		path = new StdOptimalPlace(file);
	}
	
	// REQUETES
	
	public File getFile() {
		return f;
	}
	
	public Graph getGraph() {
		return graph;
	}
	
	public TypeDisplay getTypeDisplay() {
		return setDisplay;
	}
	
	// COMMANDES
	
	@Override
	public void changeFor(File file) {
		Contract.checkCondition(file != null, "changeFor: file invalid");
		
		f = file;
		
		setChanged();
		notifyObservers();
	}
	
	@Override
	public void clear() {
		setDisplay = null;
		
		setChanged();
		notifyObservers();
	}
	
	@Override
	public void setTypeDisplay(TypeDisplay type) {
		setDisplay = type;
		
		setChanged();
		notifyObservers();
	}
	
	public void display() {
		if (getTypeDisplay() != null) {
		switch (getTypeDisplay()) {
			case ALGO1:
				algo1Description();
				break;
			case ALGO2: 
				algo2Description();
				break;
			case GRAPHE:
				grapheDescription();
				break;
			case TGRAPHE:
				tgrapheDescription();
				break;
			default:
				System.out.println("No display");
			}
		}
	}
	
	// OUTILS
	
	/**
	 * Affiche le graphe d'origine.
	 */
	private void grapheDescription() {
		graph = new StdGraph(getFile());
		System.out.println("Graphe");
		System.out.println("Nombre de sommets : " + graph.getNbOfNodes());
		System.out.println("Nombre de lieux de rendez-vous : "
				+ graph.getNbOfPlaces());
		System.out.println("Arcs:");
		for (int i = 0; i < graph.getArcs().size(); ++i) {
			System.out.println(
				graph.getArcs().get(i).getInitialNode().toString()
				+ " -> " + graph.getArcs().get(i).getTerminalNode().toString()
				+ " : " + graph.getArcs().get(i).getTime().toString());
		}
		System.out.println("Nom des sommets initiaux : " 
			+ graph.getNameOfInitialNodes().getFirstNode().toString()
			+ ", " + graph.getNameOfInitialNodes().getSecondNode().toString());
		System.out.println("Nom des sommets :");
		System.out.print("{ ");
		for (int i = 0; i < graph.getNbOfNodes(); ++i) {
			System.out.print(graph.getNameOfNodes().get(i));
			if (i != graph.getNbOfNodes() - 1) {
				System.out.print(", ");
			}
		}
		System.out.println("}");
		System.out.println("Nom des lieux de rendez-vous :");
		System.out.print("{ ");
		for (int i = 0; i < graph.getNbOfPlaces(); ++i) {
			System.out.print(graph.getNameOfPlaces().get(i));
			if (i != graph.getNbOfPlaces() - 1) {
				System.out.print(", ");
			}
		}
		System.out.println("}");
		System.out.println();
		System.out.println();
	}
	
	/**
	 * Affiche le graphe transformé.
	 */
	private void tgrapheDescription() {
		if (getGraph() != null) {
			tGraph = new StdTransformedGraph(getGraph());
			System.out.println("Le nouveau graphe");
			System.out.println("Nombre de sommets : " + tGraph.getNbOfNodes());
			System.out.println("Nombre de lieux de rendez-vous : "
					+ tGraph.getNbOfPlaces());
			System.out.println("Arcs:");
			for (int i = 0; i < tGraph.getArcs().size(); ++i) {
				System.out.println(
				tGraph.getArcs().get(i).getInitialNode().getFirstNode()
							.toString()
						+ ", " + tGraph.getArcs().get(i).getInitialNode()
							.getSecondNode().toString()
						+ " -> " + tGraph.getArcs().get(i).getTerminalNode()
							.getFirstNode().toString()
						+ ", " + tGraph.getArcs().get(i).getTerminalNode()
							.getSecondNode().toString()
						+ " : " + tGraph.getArcs().get(i).getTime().toString());
			}
			System.out.println("Sommet initial : " 
					+ "(" + tGraph.getInitialNode().getFirstNode() + ", "
					+ " " + tGraph.getInitialNode().getSecondNode() + ")");
			System.out.println("Nom des sommets : ");
			int k = 0;
			System.out.println("{ ");
			for (int i = 0; i < tGraph.getNbOfNodes(); ++i) {
				System.out.print("("
						+ tGraph.getNameOfNodes().get(i).getFirstNode()
						+ ", " + tGraph.getNameOfNodes().get(i).getSecondNode()
						+ ") ");
				if (i != tGraph.getNbOfNodes() - 1) {
					System.out.print(", ");
				}
				if (k == 6) {
					System.out.println();
					k = 0;
				} else {
					++k;
				}
			}
			System.out.println("}");
			System.out.println("Nom des lieux de rendez-vous : ");
			System.out.print("{ ");
			for (int i = 0; i < tGraph.getNbOfPlaces(); ++i) {
				System.out.print("(" + tGraph.getNameOfPlaces().
						get(i).getFirstNode() + ", " + tGraph.getNameOfPlaces()
						.get(i).getSecondNode() + ") ");
				if (i != tGraph.getNbOfPlaces() - 1) {
					System.out.print(", ");
				}
			}
			System.out.println("}");
			System.out.println();
			System.out.println();
		} else {
			JOptionPane.showMessageDialog(null,
					"Le graphe n'a pas été construit",
					"Erreur !", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Affiche le lieu de rendez-vous optimal, minimisant la somme des temps de 
	 * parcours des deux amis.
	 */
	private void algo1Description() {
		if (getGraph() != null) {
			System.out.println("Lieu de rendez-vous optimal selon le temps : ");
		    path = new StdOptimalPlace(getFile());
		    if (path != null) {
		    	System.out.println("Floyd-Warshall : " 
		    			+ path.optimalNodeByTime());
		    	System.out.print("Dijkstra : ");
		    	path.dijkstra();
		    }
		    System.out.println();
		    System.out.println();
		}
	}
	
	/**
	 * Affiche le lieu de rendez-vous optimal, minimisant la somme du nombre 
	 * d'arcs parcourus des deux amis.
	 */
	private void algo2Description() {
		if (getGraph() != null) {
			System.out.print("Lieu de rendez-vous optimal selon le nombre"
					+ " d'arcs : ");
			path = new StdOptimalPlace(getFile());
			if (path != null) {
				System.out.println(path.optimalNodeByNbNodes());
			}
			System.out.println();
			System.out.println();
		}
	}	
}

