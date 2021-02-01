package graphe;

/**
 * Modélise les triplets d'éléments de types quelconques notés <IN, TN, T>.
 * @cons <pre>
 *     $ARGS$
 *     	  IN first
 *     	  TN second
 *     	  T third
 *     $PRE$
 *     	  first != null 
 *     	  second != null
 *     $POST$
 *     	  getInitialNode() == first
 *    	  getTerminalNode() == second 
 *    	  getTime() == third </pre>
 */
public interface Triplet<IN, TN, T> {
	
	// REQUETES
	
	/**
	 * Le premier élément du triplet.
	 */
	IN getInitialNode();
	
	/**
	 * Le deuxième élément du triplet.
	 */
	TN getTerminalNode();
	
	/**
	 * Le troisième élément du triplet.
	 */
	T getTime();
	
	// COMMANDES
	
	/**
	 * Teste l'équivalence de ce triplet avec other.
	 */
	@Override
	boolean equals(Object other);
	
	/**
     * Fonction de dispersion définie sur les triplets.
     */
    @Override
    int hashCode();
}
