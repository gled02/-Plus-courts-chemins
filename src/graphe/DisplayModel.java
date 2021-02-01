package graphe;

import java.io.File;

/**
 *  Une interface qui permet d'afficher le graphe à partir d'un fichier.
 *  @cons <pre>
 *     $ARGS$ 
 *     		File file
 *     $PRE$
 *         file != null
 *     $POST$
 *         getFile() == file
 *         getGraph() == le graphe associé à ce fichier
 *         getTypeDisplay() == null
 *          </pre>
 */
public interface DisplayModel extends ObservableModel {
	
	// REQUETES
	
	/**
	 * Le fichier du graphe.
	 */
	File getFile();
	
	/**
	 * Le graphe construit.
	 */
	Graph getGraph();
	
	/**
	 * Le mode d'affichage.
	 */
	TypeDisplay getTypeDisplay();
	
	// COMMANDES
	
	/**
     * Change le fichier du graphe.
     * @pre <pre>
     *     file != null </pre>
     * @post <pre>
     *     getFile().equals(file) </pre>
     */
	void changeFor(File file);
	
	/**
	 * Réinitialise le mode d'affichage.
	 * @post <pre>
     *     getTypeDisplay().equals(null) </pre>
	 */
	void clear();
	
	/**
	 * Change le mode d'affichage au mode i.
	 * @post <pre>
     *     getTypeDisplay().equals(i) </pre>
	 */
	void setTypeDisplay(TypeDisplay i);

	/**
	 * Affiche le résultat selon le mode d'affichage.
	 */
	void display();
	
}
