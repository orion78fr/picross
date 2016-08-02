package fr.orion78.picross.solver;

public class Pair {
	private PairType t;
	private int row;

	public PairType getType() {
		return t;
	}

	public int getRow() {
		return row;
	}

	public Pair(PairType t, int row) {
		super();
		this.t = t;
		this.row = row;
	}
}
