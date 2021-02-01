package graphe;

import java.util.List;

/**
 *  Une interface qui permet de construire un graphe à partir d'un fichier.
 *  @cons <pre>
 *     $ARGS$ 
 *     		File file
 *     $PRE$
 *         file != null
 *     $POST$
 *         getNbOfNodes() == la première ligne du fichier 
 *         getNameOfNodes() == la deuxième ligne du fichier
 *         getNbOfPlaces() == la troisième ligne du fichier
 *         getNameOfPlaces() == la quatrième ligne du fichier
 *         getNameOfInitialNodes() == la cinquième ligne du fichier
 *         getArcs() == la sixième ligne du fichier </pre>
 */
public interface Graph {
	
	// REQUETES
	
	/**
	 * Le nombre de nœuds du graphe.
	 */
	int getNbOfNodes();
	
	/**
	 * Liste des noms des sommets du graphe.
	 */
	List<CharSequence> getNameOfNodes();
	
	/**
	 * Le nombre de lieux de rendez-vous du graphe.
	 */
	int getNbOfPlaces();
	
	/**
	 * Liste des noms des sommets étant un lieu de rendez-vous du graphe.
	 */
	List<CharSequence> getNameOfPlaces();
	
	/**
	 * Couple représentant les deux noms de sommets initiaux du graphe.
	 */
	Couple<CharSequence, CharSequence> getNameOfInitialNodes();
	
	/**
	 * Liste de triplets représentant les arcs du graphe.
	 */
	List<Triplet<CharSequence, CharSequence, Integer>> getArcs();	
	
	// COMMANDES
	
	/**
	 * Renvoie un triplet qui représente un arc ayant comme sommet initial 
	 * node1 et sommet final node2.
	 * @post <pre>
	 * 	  forall t:getArcs() :
			si t.getInitialNode().equals(node1) 
				&& t.getTerminalNode().equals(node2)) alors
			 renvoie t </pre>
	 */
	Triplet<CharSequence, CharSequence, Integer>
	getArc(CharSequence node1, CharSequence node2);
}
