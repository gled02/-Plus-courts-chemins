package graphe;

import util.Contract;

public class StdTriplet<IN, TN, T> implements Triplet<IN, TN, T> {
	
	// ATTRIBUTS
	
	private final IN initialNode;
	private final TN terminalNode;
	private final T time;
	
	// CONSTRUCTEUR
	
	public StdTriplet(IN first, TN second, T third) {
		Contract.checkCondition(first != null && second != null,
				"StdTriplet: Invalid arguments");
		
		initialNode = first;
		terminalNode = second;
		time = third;
	}
	
	// REQUETES
	
	@Override
	public IN getInitialNode() {
		return initialNode;
	}
	
	@Override
	public TN getTerminalNode() {
		return terminalNode;
	}
	
	@Override
	public T getTime() {
		return time;
	}
	
	// COMMANDES
	
	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if (other instanceof StdTriplet<?, ?, ?>) {
			StdTriplet<?, ?, ?> c = (StdTriplet<?, ?, ?>) other;
			result = other instanceof StdTriplet<?, ?, ?> 
					&& this.getInitialNode() == c.getInitialNode()
					&& this.getTerminalNode() == c.getTerminalNode()
					&& this.getTime() == c.getTime();
		}
		return result;
	}
	
	@Override public int hashCode() {
		 final int n1 = 17;
		 final int n2 = 31;
		 int hash = n1;
	     hash = hash * n2 + getInitialNode().hashCode();
	     hash = hash * n2 + getTerminalNode().hashCode();
	     hash = hash * n2 + getTime().hashCode();
	     return hash;
	}
}
