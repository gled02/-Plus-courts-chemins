package graphe;

import java.util.ArrayList;
import java.util.List;

public class StdTransformedGraph implements TransformedGraph {
	
	// ATTRIBUTS
	
	private int nbOfNodes;
	private int nbOfPlaces;
	private List<Couple<CharSequence, CharSequence>> nameOfNodes =
			new ArrayList<Couple<CharSequence, CharSequence>>();
	private Couple<CharSequence, CharSequence> initialNode;
	private List<Triplet<Couple<CharSequence, CharSequence>,
				Couple<CharSequence, CharSequence>, Integer>> arcs = 
			new ArrayList<Triplet<Couple<CharSequence, CharSequence>,
				Couple<CharSequence, CharSequence>, Integer>>();
	private List<Couple<CharSequence, CharSequence>> nameOfPlaces =
			new ArrayList<Couple<CharSequence, CharSequence>>();
	
	// CONSTRUCTEUR
	
	public StdTransformedGraph(Graph graph) {
		List<Couple<CharSequence, CharSequence>> nodes = 
				new ArrayList<Couple<CharSequence, CharSequence>>();
		for (int i = 0; i < graph.getNameOfNodes().size(); ++i) {
			for (int j = 0; j < graph.getNameOfNodes().size(); ++j) {
				Couple<CharSequence, CharSequence> c =
						new StdCouple<CharSequence, CharSequence>(
								graph.getNameOfNodes().get(i), 
								graph.getNameOfNodes().get(j));
				nodes.add(c);
				nameOfNodes.add(c);
			}
		}
		for (Triplet<CharSequence, CharSequence, Integer> a : graph.getArcs()) {
			for (Couple<CharSequence, CharSequence> first : nodes) {
				if (first.getFirstNode().equals(a.getInitialNode())) {
					for (Couple<CharSequence, CharSequence> second : nodes) {
						if (second.getFirstNode().equals(a.getTerminalNode())
								&& first.getSecondNode().equals(
										second.getSecondNode())) {
							Triplet<Couple<CharSequence, CharSequence>,
							Couple<CharSequence, CharSequence>, Integer> t =
								new StdTriplet
								<Couple<CharSequence, CharSequence>,
								Couple<CharSequence, CharSequence>, Integer>(
										first, second, a.getTime());
							arcs.add(t);
						}
					}
				}
				if (first.getSecondNode().equals(a.getInitialNode())) {
					for (Couple<CharSequence, CharSequence> second : nodes) {
						if (second.getSecondNode().equals(a.getTerminalNode())
								&& first.getFirstNode().equals(
										second.getFirstNode())) {
							Triplet<Couple<CharSequence, CharSequence>,
							Couple<CharSequence, CharSequence>, Integer> t =
								new StdTriplet
								<Couple<CharSequence, CharSequence>,
								Couple<CharSequence, CharSequence>, Integer>(
										first, second, a.getTime());
							arcs.add(t);
						}
					}
				}
			}
		}
		initialNode = graph.getNameOfInitialNodes();
		nbOfNodes = nameOfNodes.size();
		
		for (CharSequence c : graph.getNameOfPlaces()) {
			nameOfPlaces.add(new StdCouple<CharSequence, CharSequence>(c, c));
		}
		nbOfPlaces = nameOfPlaces.size();
	}
	
	// REQUETES
	
	@Override
	public int getNbOfNodes() {
		return nbOfNodes;
	}
	
	@Override
	public List<Couple<CharSequence, CharSequence>> getNameOfNodes() {
		return nameOfNodes;
	}
	
	@Override
	public Couple<CharSequence, CharSequence> getInitialNode() {
		return initialNode;
	}
	
	@Override
	public List<Triplet<Couple<CharSequence, CharSequence>,
	Couple<CharSequence, CharSequence>, Integer>> getArcs() {
		return arcs;
	}
	
	@Override
	public int getNbOfPlaces() {
		return nbOfPlaces;
	}
	
	@Override
	public List<Couple<CharSequence, CharSequence>> getNameOfPlaces() {
		return nameOfPlaces;
	}
	
	@Override
	public Couple<CharSequence, CharSequence> getNode(int i) {
		return getNameOfNodes().get(i);
	}
	
	// COMMANDES
	
	@Override
	public Triplet<Couple<CharSequence, CharSequence>,
	Couple<CharSequence, CharSequence>, Integer>
	getArc(Couple<CharSequence, CharSequence> node1, 
			Couple<CharSequence, CharSequence> node2) {
		for (Triplet<Couple<CharSequence, CharSequence>,
	Couple<CharSequence, CharSequence>, Integer> t : getArcs()) {
			if (t.getInitialNode().equals(node1) 
					&& t.getTerminalNode().equals(node2)) {
				return t;
			}
		}
		return new StdTriplet<Couple<CharSequence, CharSequence>,
				Couple<CharSequence, CharSequence>, Integer>(
						node1, node2, null);
	}
	
	@Override
	public int getIndex(Couple<CharSequence, CharSequence> c) {
		for (int i = 0; i < getNbOfNodes(); ++i) {
			if (getNameOfNodes().get(i).getFirstNode().equals(c.getFirstNode())
					&& getNameOfNodes().get(i).getSecondNode()
					.equals(c.getSecondNode())) {
				return i;
			}
		}
		return -1;
	}
}
