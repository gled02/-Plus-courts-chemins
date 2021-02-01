package graphe;

public class StdCouple<IN1, IN2> implements Couple<IN1, IN2> {
	
	// ATTRIBUTS
	
	private final IN1 firstNode;
	private final IN2 secondNode;
	
	// CONSTRUCTEUR
	
	public StdCouple(IN1 first, IN2 second) {
		firstNode = first;
		secondNode = second;
	}
	
	// REQUETES
	
	@Override
	public IN1 getFirstNode() {
		return firstNode;
	}
	
	@Override
	public IN2 getSecondNode() {
		return secondNode;
	}
}
