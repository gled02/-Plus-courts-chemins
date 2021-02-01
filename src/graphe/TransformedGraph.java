package graphe;

import java.util.List;

/**
 * Une interface qui permet de transfomer un graphe. Dans ce nouveau graphe,
 * chaque sommet est une paire de sommets du graphe d’origine correspondant à 
 * la position de chacun des deux amis, et les arcs correspondent au déplacement
 * de l’un des deux amis.
 * @cons <pre>
 *     $ARGS$ 
 *     		Graph graph
 *     $POST$
 *     	    getNbOfNodes() == getNameOfNodes().size()
 *     		forall i:graph.getNameOfNodes() :
 *     			forall j:graph.getNameOfNodes() :
 *     				getNameOfNodes() = Couple(i,j)
 *          getInitialNode() == graph.getNameOfInitialNodes();
 *     	    getArcs() == les arcs du graphe transformé </pre>
 *
 */
public interface TransformedGraph {

	// REQUETES
	
	/**
	 * Le nombre de nœuds du graphe transformé.
	 */
	int getNbOfNodes();
	
	/**
	 *  Liste des noms des sommets du graphe transformé.
	 */
	List<Couple<CharSequence, CharSequence>> getNameOfNodes();
	
	/**
	 * Le sommet initial du graphe transformé.
	 */
	Couple<CharSequence, CharSequence> getInitialNode();
	
	/**
	 * Liste de triplets représentant les arcs du graphe transformé.
	 */
	List<Triplet<Couple<CharSequence, CharSequence>,
	Couple<CharSequence, CharSequence>, Integer>> getArcs();
	
	/**
	 *  Liste des noms des sommets étant un lieu de rendez-vous 
	 *  du graphe transformé.
	 */
	List<Couple<CharSequence, CharSequence>> getNameOfPlaces();
	
	/**
	 * Le nombre de lieux de rendez-vous du graphe transformé.
	 */
	int getNbOfPlaces();
	
	/**
	 * Le sommet qui se trouve à l'indice i.
	 */
	Couple<CharSequence, CharSequence> getNode(int i);
	
	// COMMANDES
	
	/**
	 * Renvoie un triplet qui représente un arc ayant comme sommet initial 
	 * node1 et sommet final node2.
	 * @post <pre>
	 * 	  forall t:getArcs() :
	 *		 si t.getInitialNode().equals(node1) 
	 *			&& t.getTerminalNode().equals(node2)) alors
	 *		 	renvoie t </pre>
	 */
	Triplet<Couple<CharSequence, CharSequence>,
	Couple<CharSequence, CharSequence>, Integer> 
		getArc(Couple<CharSequence, CharSequence> node1, 
				Couple<CharSequence, CharSequence> node2);
	
	
	/**
	 * Renvoie l'indice du sommet répresenté par la variable c, 
	 * dans le graphe transformé.
	 * @post <pre>
	 * forall i de 0 à getNbOfNodes() - 1
	 *	  si getNameOfNodes().get(i).getFirstNode().equals(c.getFirstNode())
	 *				&& getNameOfNodes().get(i).getSecondNode()
	 *				.equals(c.getSecondNode()) alors
	 *			revoie i </pre>
	 */
	int getIndex(Couple<CharSequence, CharSequence> c);
}
