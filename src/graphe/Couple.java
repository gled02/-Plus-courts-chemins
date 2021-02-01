package graphe;

/**
 * Modélise les couples d'éléments de types quelconques notés <IN1, IN2>.
 * @cons <pre>
 *     $ARGS$
 *     	  IN1 first
 *     	  IN1 second	
 *     $POST$
 *     	  getFirstNode() == first
 *    	  getFirstNode() == second </pre> 
 */
public interface Couple<IN1, IN2> {
	
	// REQUETES
	
	/**
	 * Le premier élément du couple.
	 */
	IN1 getFirstNode();
	
	/**
	 * Le deuxième élément du couple.
	 */
	IN2 getSecondNode();
}
