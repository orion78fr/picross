package fr.orion78.picross.model;

public class Grid {
	private int w;
	private int h;
	
	private Row[] rows;
	private Row[] columns;
	
	private int[][] values;
	
	private int maxNumCol;
	private int maxNumRow;

	public Grid(int w, int h, int[][] columns, int[][] rows) {
		if(columns.length != w){
			throw new RuntimeException("Not enough columns");
		}
		if(rows.length != h){
			throw new RuntimeException("Not enough rows");
		}
		
		int suma = 0, sumb = 0;
		for(int[] a : columns){
			for(int b : a){
				suma += b;
			}
		}
		for(int[] a : rows){
			for(int b : a){
				sumb += b;
			}
		}
		if(suma != sumb){
			throw new RuntimeException("Incorrect rows or columns : sum mismatch (" + suma + ", " + sumb + ")");
		}
		
		this.w = w;
		this.h = h;
		
		this.columns = new Row[w];
		this.rows = new Row[h];
		this.maxNumCol = columns[0].length;
		this.maxNumRow = rows[0].length;
		for(int i = 0; i < w; i++){
			this.columns[i]= new Row(columns[i]);
			if(columns[i].length > this.maxNumCol){
				this.maxNumCol = columns[i].length;
			}
		}
		for(int i = 0; i < h; i++){
			this.rows[i]= new Row(rows[i]);
			if(rows[i].length > this.maxNumRow){
				this.maxNumRow = rows[i].length;
			}
		}
		
		this.values = new int[w][h];
	}

	public int getMaxNumCol() {
		return maxNumCol;
	}

	public int getMaxNumRow() {
		return maxNumRow;
	}

	public int getWidth() {
		return w;
	}

	public int getHeight() {
		return h;
	}

	public Row[] getRows() {
		return rows;
	}
	
	public Row getRow(int row){
		return rows[row];
	}

	public Row[] getColumns() {
		return columns;
	}
	
	public Row getColumn(int col) {
		return columns[col];
	}

	public int[][] getValues() {
		return values;
	}
	
	public int[] getRowValues(int row){
		int[] result = new int[w];
		for(int i = 0; i < w; i++){
			result[i] = values[i][row];
		}
		return result;
	}

	public void set(int x, int y, int value){
		/*if(this.values[x][y] != 0 && this.values[x][y] != value){
			System.out.println("WARNING something probably went wrong");
		}*/
		this.values[x][y] = value;
	}
	
	public boolean isCorrect(){
		if(!isCompleted()){
			return false;
		}
		// Verify columns
		// TODO
		/*for(int i = 0; i < this.w; i++){
			if(columns[i].computePossibleStates2(values[i]).size() != 1){
				return false;
			}
		}
		// Verify rows
		for(int i = 0; i < this.h; i++){
			if(rows[i].computePossibleStates2(getRowValues(i)).size() != 1){
				return false;
			}
		}*/
		
		return true;
	}
	
	public boolean isCompleted(){
		for(int i = 0; i < this.w; i++){
			for(int j = 0; j < this.h; j++){
				if(this.values[i][j] == 0){
					return false;
				}
			}
		}
		return true;
	}
}
