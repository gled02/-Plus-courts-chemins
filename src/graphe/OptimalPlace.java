package graphe;

/**
 *  Une interface qui permet de calculer le lieu de rendez-vous optimal
 *  d'un graphe transformé, selon les spécifications de l'interface 
 *  TransformedGraph, pour lequel le temps de parcours et le nombre d'arcs
 *  soient minimals en appliquant les algorithmes de Floyd-Warshall et Dijkstra.
 *  @cons <pre>
 *     $ARGS$ 
 *     		File file
 *     $PRE$
 *         file != null
 *     $POST$
 *          getGraph() == StdGraph(file)
 *          getTGraph() == StdTransformedGraph(graph) </pre>
 */
public interface OptimalPlace {
	
	// REQUETES
	
	/**
	 * Le graphe contruit à partir du fichier.
	 */
	Graph getGraph();
	
	/**
	 * Le graphe transformé.
	 */
	TransformedGraph getTGraph();
	
	/**
	 * La matrice des distances du graphe transformé.
	 */
	Integer[][] getTGraphMatrix();
	
	// COMMANDES
	
	/**
	 * Renvoie le lieu de rendez-vous optimal, minimisant la somme des temps de 
	 * parcours d'un graphe transformé, en appliquant l'algorithme de Floyd-
	 * Warshall. 
	 * @post <pre>
	 * Soient: 
	 * - le sommet initial du graphe transformé
	 * 		initialIndex = getTGraph().getIndex(getTGraph().getInitialNode())
	 * - la matrice des plus courtes distances calculées par l'algorithme de
	 * 		Floyd-Warshall
	 * 		d = floydWarshall()
	 * - la variable mémorisant le minimum
	 * 		min = Integer.MAX_VALUE
	 * - la variable mémorisant le résultat (le lieu de rendez-vous optimal)
	 * 		res = null
	 * forall i:getTGraph().getNameOfPlaces() :
	 *		si d[initialIndex][k] < min alors
	 *			min = d[initialIndex][getTGraph().getIndex(i)]
	 *			res = i.getFirstNode() </pre>
	 */
	CharSequence optimalNodeByTime();
	
	/**
	 * Renvoie le lieu de rendez-vous optimal, minimisant la somme du nombre
	 * d'arcs parcourus d'un graphe transformé, en appliquant l'algorithme de 
	 * Floyd-Warshall modifié (tous les arcs sont de poids 1).
	 * @post <pre>
	 * Soient: 
	 * - le sommet initial du graphe transformé
	 * 		initialIndex = getTGraph().getIndex(getTGraph().getInitialNode())
	 * - la matrice des plus courtes distances calculées par l'algorithme de
	 * 		Floyd-Warshall modifé
	 * 		d = floydWarshallModif()
	 * - la variable mémorisant le minimum
	 * 		min = Integer.MAX_VALUE
	 * - la variable mémorisant le résultat (le lieu de rendez-vous optimal)
	 * 		res = null
	 * forall i : getTGraph().getNameOfPlaces() :
	 *		si d[initialIndex][getTGraph().getIndex(i)] == min alors
	 * 			si time(getTGraph().getInitialNode(), i) 
	 *					< time(getTGraph().getInitialNode(), res) alors
	 *				min = time(getTGraph().getInitialNode(), i)
	 *				res = i
	 *		si 0 < d[initialIndex][getTGraph().getIndex(i)]
	 *			&& d[initialIndex][getTGraph().getIndex(i)] < min alors
	 *			min = d[initialIndex][getTGraph().getIndex(i)]
	 *			res = i </pre>
	 */
	CharSequence optimalNodeByNbNodes();
	
	/**
	 * Affiche le lieu de rendez-vous optimal, minimisant la somme des temps de 
	 * parcours d'un graph transformé, en appliquant l'algorithme de Dijkstra. 
	 * @post <pre>
	 * 	 	Calcule la matrice des distances de tous les autres sommets à
	 * 		partir du sommet d'origine (sommet initial du graphe transformé),
	 * 		dans le cas où tous les poids des arcs du graphe sont positifs ou
	 * 		nuls (le graphe ne possède pas de circuit absorbant). </pre>
	*/
	void dijkstra();
	
}
