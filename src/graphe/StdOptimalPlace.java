package graphe;

import util.Contract;

import java.io.File;

public class StdOptimalPlace implements OptimalPlace {
	
	// ATTRIBUTS
	
	private Graph graph;
	private TransformedGraph tGraph;
	
	// CONSTRUCTEUR
	
	public StdOptimalPlace(File file) {
		Contract.checkCondition(file != null, "StdOptimalPlace: Invalid file");
		
		graph = new StdGraph(file);
		tGraph = new StdTransformedGraph(graph);
	}
	
	// REQUETES
	
	@Override
	public Graph getGraph() {
		return graph;
	}
	
	@Override
	public TransformedGraph getTGraph() {
		return tGraph;
	}
	
	@Override
	public Integer[][] getTGraphMatrix() {
		Integer[][] dTbl =
			new Integer[getTGraph().getNbOfNodes()][getTGraph().getNbOfNodes()];
		for (int i = 0; i < getTGraph().getNbOfNodes(); ++i) {
			for (int j = 0; j < getTGraph().getNbOfNodes(); ++j) {
				if (i == j) {
					dTbl[i][j] = 0;
				} else {
					dTbl[i][j] = getTGraph().getArc(
							getTGraph().getNameOfNodes().get(i),
							getTGraph().getNameOfNodes().get(j)).getTime();
				}
			}
		}
		for (int k = 0; k < dTbl.length; ++k) {
			for (int i = 0; i < dTbl.length; ++i) {
				for (int j = 0; j < dTbl.length; ++j) {
					if (dTbl[i][k] != null && dTbl[k][j] != null) {
						if (dTbl[i][j] == null) {
							dTbl[i][j] = dTbl[i][k] + dTbl[k][j];
						} else {
							dTbl[i][j] = Math.min(dTbl[i][j],
									dTbl[i][k] + dTbl[k][j]);
						}
					}
				}
			}
		}
		return dTbl;
	}
	
	// COMMANDES
	
	@Override
	public CharSequence optimalNodeByTime() {
		int initialIndex = getTGraph().getIndex(getTGraph().getInitialNode());
		Integer[][] d = floydWarshall(timeMatrix());
		int min = Integer.MAX_VALUE;
		int k;
		CharSequence res = null;
		for (Couple<CharSequence, CharSequence> i 
				: getTGraph().getNameOfPlaces()) {
			k = getTGraph().getIndex(i);
			if (d[initialIndex][k] < min) {
				min = d[initialIndex][k];
				res = i.getFirstNode();
			}
		}
		return res;
	}
	
	@Override
	public CharSequence optimalNodeByNbNodes() {
		int initialIndex = getTGraph().getIndex(getTGraph().getInitialNode());
		Integer[][] d = floydWarshall(nbMatrix());
		int min = Integer.MAX_VALUE;
		int k;
		Couple<CharSequence, CharSequence> res = null;
		for (Couple<CharSequence, CharSequence> i 
				: getTGraph().getNameOfPlaces()) {
			k = getTGraph().getIndex(i);
			if (d[initialIndex][k] == min) {
				if (time(getTGraph().getInitialNode(), i) 
						< time(getTGraph().getInitialNode(), res)) {
					min = time(getTGraph().getInitialNode(), i);
					res = i;
				}
			}
			if (0 < d[initialIndex][k] && d[initialIndex][k] < min) {
				min = d[initialIndex][k];
				res = i;
			}
		}
		return res.getFirstNode();
	}
   
    @Override
    public void dijkstra() {
    	Integer [][] time = timeMatrix();
    	
    	CharSequence initialNode = getTGraph().getInitialNode().getFirstNode();
    	
        Boolean [] f = new Boolean[getTGraph().getNbOfNodes()];
        Integer [] d = new Integer[getTGraph().getNbOfNodes()];
        Integer [] pi = new Integer[getTGraph().getNbOfNodes()];
        
        for (int i = 0; i < getTGraph().getNbOfNodes(); ++i) {
            d[i] = Integer.MAX_VALUE;
            f[i] = false;
            pi[i] = 0; 
        }
        
        if (index(initialNode) != -1) {
        	d[index(initialNode)] = 0;
        	for (int i = 0; i < getTGraph().getNbOfNodes() - 1; ++i) {
        		int u = minDistance(d, f);
        		f[u] = true;
        		for (int v = 0; v < getTGraph().getNbOfNodes(); ++v) {
        			if (!f[v] && time[u][v] != null 
        					&& d[u] != Integer.MAX_VALUE 
        					&& d[u] + time[u][v] < d[v]) {
        				d[v] = d[u] + time[u][v];
        				pi[v] = u;
        			}
        		}
        	}
        	printSolutionDijkstra(d);
        }
    }

	// OUTILS
	
    /**
     * 
     * @param initial
     * @param terminal
     * @return
     * 		Renvoie la plus courte distance (temps) entre 2 sommets donnés en 
     *	    paramètres du graphe transformé.
     */
	private Integer time(Couple<CharSequence, CharSequence> initial,
			Couple<CharSequence, CharSequence> terminal) {
		int initialIndex = getTGraph().getIndex(initial);
		int terminalIndex = getTGraph().getIndex(terminal);
		Integer[][] d = floydWarshall(timeMatrix());
		return d[initialIndex][terminalIndex];
	}
	
	/**
	 * 
	 * @param p : la matrice des distances du graphe transformé.
	 * @return
	 * 		Renvoie la matrice des plus courtes distances calculée avec 
	 * 		l'algorithme de Floyd-Warshall vu en cours.
	 */
	private Integer[][] floydWarshall(Integer[][] p) {
		for (int k = 0; k < getTGraph().getNbOfNodes(); ++k) {
			for (int i = 0; i < getTGraph().getNbOfNodes(); ++i) {
				for (int j = 0; j < getTGraph().getNbOfNodes(); ++j) {
					if (i != j) {
						if (p[i][k] != null && p[k][j] != null) {
							if (p[i][j] == null) {
								p[i][j] = p[i][k] + p[k][j];
							} else {
								p[i][j] = Math.min(p[i][j], p[i][k] + p[k][j]);
							}
						}
					} else {
						p[i][j] = 0;
					}
				}
			}
		}
		return p;
	}
	
	/**
	 * 
	 * @param d : tableau de réels qui répresente l'estimation de la plus courte
	 * 			  distance du sommet initial vers tous les autres sommets.
	 * 			  (tableau des plus courtes distances).
	 * @param f : tableau de booléens qui décrit les sommets fermés.
	 * @return
	 * 		Renvoie l'indice d'un sommet du graphe transformé, tel que ce sommet
	 * 		ne soit pas fermé et que l'estimation de la plus courte distance du 
	 * 		sommet initial vers ce sommet (d[u]) soit minimal.
	 */
	private int minDistance(Integer []d, Boolean []f) {
		 int min = Integer.MAX_VALUE;
	     int minIndex = -1;
	     for (int u = 0; u < getTGraph().getNbOfNodes(); ++u) {
	    	 if (!f[u] && d[u] <= min) {
	    		 min = d[u];
	             minIndex = u;
	         }
	     }
	     return minIndex;
	 }
	
	/**
	 * 
	 * @param node
	 * @return
	 * 		Renvoie l'indice du sommet pris en paramètre (node) dans le graphe
	 * 		transformé. Renvoie -1 si le sommet n'existe pas. 
	 */
	 private int index(CharSequence node) {
		 for (int i = 0; i < getTGraph().getNameOfNodes().size(); ++i) {
			 if (getTGraph().getNameOfNodes().get(i).getFirstNode()
					 	.equals(node)) {
				 return i;
			 }
		 }
		 return -1;
	 }
	
	 /**
	  * 
	  * @param d : tableau de réels qui répresente l'estimation de la plus 
	  * 		   courte distance du sommet initial vers tous les autres 
	  * 		   sommets. (tableau des plus courtes distances).
	  * 
	  * Affiche le lieu de rendez-vous optimal minimisant la somme des temps de 
	  * parcours d'un graph transformé. 	
	  */
	 private void printSolutionDijkstra(Integer [] d) {
		 int min = Integer.MAX_VALUE;
		 int k;
		 CharSequence res = null;
		 for (Couple<CharSequence, CharSequence> i 
				 : getTGraph().getNameOfPlaces()) {
			 k = getTGraph().getIndex(i);
			 if (d[k] < min) {
				 min = d[k];
				 res = i.getFirstNode();
			 }
		 }
		 System.out.println(res);
	 }
	 
	/**
	 * 
	 * @return
	 * 		Renvoie la matrice des distances du graphe transformé.(Correspond à
	 * 	    l'initialisation de la matrice d dans l'algorithme de
	 * 		Floyd-Warshall).
	 */
	private Integer[][] timeMatrix() {
		Integer[][] p = new Integer[getTGraph().getNbOfNodes()]
				[getTGraph().getNbOfNodes()];
		for (int i = 0; i < getTGraph().getNbOfNodes(); ++i) {
			for (int j = 0; j < getTGraph().getNbOfNodes(); ++j) {
				if (i == j) {
					p[i][j] = 0;
				} else {
					p[i][j]  = getTGraph().getArc(
							getTGraph().getNameOfNodes().get(i),
							getTGraph().getNameOfNodes().get(j)).getTime();
				}
			}
		}
		return p;
	}
	
	/**
	 * 
	 * @return
	 * 		Renvoie la matrice des distances calculée à partir du graphe
	 * 		transformé dans lequel le poids de chaque arc est 1.
	 */
	private Integer[][] nbMatrix() {
		Integer[][] p = new Integer[getTGraph().getNbOfNodes()]
				[getTGraph().getNbOfNodes()];
		Integer[][] n = gnbMatrix();
		boolean[][] adj = adjMatrix();
		Couple<CharSequence, CharSequence> one, two;
		for (int i = 0; i < getTGraph().getNbOfNodes(); ++i) {
			one = getTGraph().getNode(i);
			for (int j = 0; j < getTGraph().getNbOfNodes(); ++j) {
				two = getTGraph().getNode(j);
				if (adj[i][j]) {
					p[i][j] = n[getGraph().getNameOfNodes().indexOf(
							one.getFirstNode())][getGraph().getNameOfNodes()
							                     .indexOf(two.getFirstNode())]
								+ n[getGraph().getNameOfNodes().indexOf(
							one.getSecondNode())][getGraph().getNameOfNodes()
							                     .indexOf(two.getSecondNode())];
				} else {
					p[i][j] = null;
				}
			}
		}
		return p;
	}
	
	/**
	 * 
	 * @return
	 * 		Renvoie la matrice des distances calculée à partir du graphe
	 * 		d'origine dans lequel le poids de chaque arc est 1.
	 */
	private Integer[][] gnbMatrix() {
		Integer[][] p = new Integer[getGraph().getNbOfNodes()]
				[getGraph().getNbOfNodes()];
		boolean[][] adj = gadjMatrix();
		for (int i = 0; i < getGraph().getNbOfNodes(); ++i) {
			for (int j = 0; j < getGraph().getNbOfNodes(); ++j) {
				if (adj[i][j]) {
					p[i][j] = 1;
				} else {
					p[i][j] = null;
				}
			}
		}
		
		for (int k = 0; k < getGraph().getNbOfNodes(); ++k) {
			for (int i = 0; i < getGraph().getNbOfNodes(); ++i) {
				for (int j = 0; j < getGraph().getNbOfNodes(); ++j) {
					if (i != j) {
						if (p[i][k] != null && p[k][j] != null) {
							if (p[i][j] == null) {
								p[i][j] = p[i][k] + p[k][j];
							} else {
								p[i][j] = Math.min(p[i][j], p[i][k] + p[k][j]);
							}
						}
					} else {
						p[i][j] = 0;
					}
				}
			}
		}
		return p;
	}
	
	/**
	 * 
	 * @return
	 * 		Renvoie la matrice d'adjacence calculée à partir du graphe 
	 * 		transformé.
	 */
	private boolean[][] adjMatrix() {
		boolean[][] adj = new boolean
				[getTGraph().getNbOfNodes()][getTGraph().getNbOfNodes()];
		for (int i = 0; i < getTGraph().getNbOfNodes(); ++i) {
			for (int j = 0; j < getTGraph().getNbOfNodes(); ++j) {
				if (getTGraph().getArc(getTGraph().getNameOfNodes().get(i),
					getTGraph().getNameOfNodes().get(j)) != null) {
					adj[i][j] = true;
				} else {
					adj[i][j] = false;
				}
			}
		}
		return adj;
	}
	
	/**
	 * 
	 * @return
	 * 		Renvoie la matrice d'adjacence calculée à partir du graphe 
	 * 		d'origine.
	 */
	private boolean[][] gadjMatrix() {
		boolean[][] adj = new boolean
				[getGraph().getNbOfNodes()][getGraph().getNbOfNodes()];
		for (int i = 0; i < getGraph().getNbOfNodes(); ++i) {
			for (int j = 0; j < getGraph().getNbOfNodes(); ++j) {
				if (getGraph().getArc(getGraph().getNameOfNodes().get(i),
					getGraph().getNameOfNodes().get(j)) != null) {
					adj[i][j] = true;
				} else {
					adj[i][j] = false;
				}
			}
		}
		return adj;
	}
}
