package fr.orion78.picross.solver;

import java.util.Arrays;
import java.util.List;

import fr.orion78.picross.model.Grid;
import fr.orion78.picross.model.Row;

public class Solver {

	public Solver() {
		throw new RuntimeException(this.getClass().getName() + " is not instanciable!");
	}

	public static void solve(Grid g) {
		int w = g.getWidth();
		int h = g.getHeight();
		Row[] columns = g.getColumns();
		Row[] rows = g.getRows();
		
		FrequencySet<Pair> modified = new FrequencySet<Pair>();
		
		// First pass : for each column then row, do the line method
		for(int i = 0; i < w; i++){
			int[] values = columns[i].getValues();
			int sum = 0;
			
			// Count minimum size
			for(int j : values){
				sum += j;
			}
			sum += values.length - 1;
			
			// Set obligatory black cells
			int currentCell = 0;
			int remaining = h - sum;
			
			for(int j = 0; j < values.length; j++){
				if(values[j] - remaining > 0){
					currentCell += remaining;
					for(int k = 0; k < values[j] - remaining; k++, currentCell++){
						g.set(i,currentCell,1);
						modified.add(new Pair(PairType.ROW, currentCell));
						modified.add(new Pair(PairType.COL, i));
					}
				} else {
					currentCell += values[j];
				}
				currentCell++;
			}
		}
		for(int i = 0; i < h; i++){
			int[] values = rows[i].getValues();
			int sum = 0;
			
			// Count minimum size
			for(int j : values){
				sum += j;
			}
			sum += values.length - 1;
			
			// Set obligatory black cells
			int currentCell = 0;
			int remaining = w - sum;
			
			for(int j = 0; j < values.length; j++){
				if(values[j] - remaining > 0){
					currentCell += remaining;
					for(int k = 0; k < values[j] - remaining; k++, currentCell++){
						g.set(currentCell,i,1);
						modified.add(new Pair(PairType.COL, currentCell));
						modified.add(new Pair(PairType.ROW, i));
					}
				} else {
					currentCell += values[j];
				}
				currentCell++;
			}
		}
		
		// For each modified row, compute all new possible states, and set all identical cells
		int iter = 0;
		while(!modified.isEmpty()){
			iter++;
			
			Pair p = modified.getBest();
			
			System.out.println(p);
			
			if(p.getType().equals(PairType.COL)){
				List<int[]> st = columns[p.getRow()].computePossibleStates(g.getValues()[p.getRow()]);
				int[] result = Arrays.copyOf(st.get(0), h);
				
				for(int j = 1; j < st.size(); j++){
					for(int k = 0; k < h; k++){
						if(result[k] != st.get(j)[k]){
							result[k] = 0;
						}
					}
				}
				
				for(int k = 0; k < h; k++){
					if(result[k] != 0 && g.getValues()[p.getRow()][k] != result[k]){
						g.set(p.getRow(), k, result[k]);
						modified.add(new Pair(PairType.ROW, k));
						modified.add(new Pair(PairType.COL, p.getRow()));
					}
				}
			} else {
				List<int[]> st = rows[p.getRow()].computePossibleStates(g.getRowValues(p.getRow()));
				int[] result = Arrays.copyOf(st.get(0), w);
				
				for(int j = 1; j < st.size(); j++){
					for(int k = 0; k < w; k++){
						if(result[k] != st.get(j)[k]){
							result[k] = 0;
						}
					}
				}
				
				for(int k = 0; k < w; k++){
					if(result[k] != 0 && g.getValues()[k][p.getRow()] != result[k]){
						g.set(k, p.getRow(), result[k]);
						modified.add(new Pair(PairType.COL, k));
						modified.add(new Pair(PairType.ROW, p.getRow()));
					}
				}
			}
			
			
			if(iter == 1){
				break;
			}

			System.out.println(iter);
		}

		System.out.println(iter + " iter");
	}

}
