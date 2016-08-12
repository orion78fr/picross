package fr.orion78.picross.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Row {
	private int[] values;

	public Row(int[] values) {
		this.values = values;
	}

	public int[] getValues() {
		return values;
	}
	
	private static List<int[]> computeSpaces(int nbCell, int spaces){
		List<int[]> l = new ArrayList<int[]>();
		
		if(nbCell == 1){
			// Trivial case: there is only one cell
			int[] result = new int[nbCell];
			result[0] = spaces;
			l.add(result);
		} else if(spaces == 0){
			// Trivial case: there is zero spaces
			int[] result = new int[nbCell];
			l.add(result);
		} else if(spaces == 1){
			// Trivial case: there is only one space
			for(int i = 0; i < nbCell; i++){
				int[] result = new int[nbCell];
				result[i] = 1;
				l.add(result);
			}
		} else {
			for(int i = 0; i <= spaces; i++){
				List<int[]> subList = computeSpaces(nbCell-1, i);
				for(int[] sub : subList){
					int[] result = new int[nbCell];
					result[0] = spaces-i;
					for(int j = 0; j < nbCell - 1; j++){
						result[j+1] = sub[j];
					}
					l.add(result);
				}
			}
		}
		
		return l;
	}
	
	private List<int[]> cachedResult = null;
	
	public List<int[]> computePossibleStates(int[] currentRow, boolean discardCache){
		if(discardCache){
			cachedResult = null;
		}
		return computePossibleStates(currentRow);
	}
	
	public List<int[]> computePossibleStates(int[] currentRow){
		if(cachedResult == null){
			cachedResult = new ArrayList<int[]>();
		
			int sum = 0;
			for(int i = 0; i < this.values.length; i++){
				sum += this.values[i];
			}
			// Spaces needed - mandatory spaces
			int spaces = (currentRow.length - sum) - (this.values.length - 1);
			
			List<int[]> possibleSpaces = computeSpaces(this.values.length + 1, spaces);
			
			for(int[] possibleSpace : possibleSpaces){
				int[] rowRes = new int[currentRow.length];
				int index = 0;
				
				// Beginning spaces
				for(int j = 0; j < possibleSpace[0]; j++, index++){
					rowRes[index] = -1;
				}
				
				// Values + intermediary spaces
				for(int i = 0; i < this.values.length; i++){
					for(int j = 0; j < this.values[i]; j++, index++){
						rowRes[index] = 1;
					}
					// Mandatory space
					if(i != this.values.length - 1){
						rowRes[index] = -1;
						index++;
					}
					for(int j = 0; j < possibleSpace[i+1]; j++, index++){
						rowRes[index] = -1;
					}
				}
				
				if(isCompatible(rowRes, currentRow)){
					cachedResult.add(rowRes);
				}
			}
		} else {
			Iterator<int[]> it = cachedResult.iterator();
			while(it.hasNext()){
				int[] next = it.next();
				if(!isCompatible(next, currentRow)){
					it.remove();
				}
			}
		}
		
		return cachedResult;
	}

	private boolean isCompatible(int[] rowRes, int[] currentRow) {
		for(int i = 0; i < rowRes.length; i++){
			if(currentRow[i] == -1 && rowRes[i] != -1){
				return false;
			} else if(currentRow[i] == 1 && rowRes[i] != 1){
				return false;
			}
		}
		return true;
	}
}
