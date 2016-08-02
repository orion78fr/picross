package fr.orion78.picross.model;

public class Grid {
	private int w;
	private int h;
	
	private Row[] rows;
	private Row[] columns;
	
	private int[][] values;

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
			throw new RuntimeException("Incorrect rows or columns : sum mismatch");
		}
		
		this.w = w;
		this.h = h;
		
		this.columns = new Row[w];
		this.rows = new Row[h];
		for(int i = 0; i < w; i++){
			this.columns[i]= new Row(columns[i]);
		}
		for(int i = 0; i < h; i++){
			this.rows[i]= new Row(rows[i]);
		}
		
		this.values = new int[w][h];
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

	public Row[] getColumns() {
		return columns;
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
		if(this.values[x][y] != 0 && this.values[x][y] != value){
			System.out.println("WARNING something probably went wrong");
		}
		this.values[x][y] = value;
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
