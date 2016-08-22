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

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Pair)){
			return false;
		}
		Pair p = (Pair) obj;
		return p.t.equals(this.t) && p.row == this.row;
	}
	@Override
	public int hashCode() {
		return this.row;
	}
	
	@Override
	public String toString() {
		return (t.equals(PairType.COL) ? "Column " : "Row ") + row;
	}
}
