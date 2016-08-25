package fr.orion78.picross.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Row {
	private int[] values;

	public Row(int[] values) {
		this.values = values;
	}

	public int[] getValues() {
		return values;
	}
	
	public List<int[]> computePossibleStates2(final int[] currentRow){
		List<int[]> l = new ArrayList<int[]>();
		
		int sum = 0;
		for(int i = 0; i < this.values.length; i++){
			sum += this.values[i];
		}
		// Spaces needed - mandatory spaces
		int remainingSpaces = (currentRow.length - sum) - (this.values.length - 1);
		
		int[] currentRes = new int[currentRow.length];
		
		computePossibleStatesHelper(l, currentRow, currentRes, 0, 0, remainingSpaces);
		
		return l;
	}
	
	private void computePossibleStatesHelper(final List<int[]> l, final int[] currentRow,
			final int[] currentRes, final int resIndex, final int valuesIndex, final int remainingSpaces){
		// We make a copy to be sure we don't mess with the other iterations.
		// That's why the field is final, just in case.
		int[] newRes = Arrays.copyOf(currentRes, currentRes.length);
		
		// Trivial case
		if(valuesIndex == this.values.length){
			// We used all the values in the row, the only things remaining are spaces.
			for(int i = resIndex; i < newRes.length; i++){
				newRes[i] = -1;
			}
			if(isCompatible(newRes, currentRow)){
				l.add(newRes);
				if(l.size() % 10000 == 0){
					System.out.println(l.size());
				}
			}
		} else {
			for(int i = 0; i <= remainingSpaces; i++){
				// We add an amount of space ...
				for(int j = resIndex; j < resIndex+i; j++){
					newRes[j] = -1;
				}
				// ... then the next value ...
				for(int j = resIndex+i; j < resIndex+i+this.values[valuesIndex]; j++){
					newRes[j] = 1;
				}
				// ... plus the mandatory space if we are not at the end.
				if(valuesIndex+1 != this.values.length){
					newRes[resIndex + i + this.values[valuesIndex]] = -1;
				}
				if(isCompatible(newRes, currentRow)){
					// If it's still compatible with the current row, we can continue to try
					computePossibleStatesHelper(l, currentRow, newRes,
							resIndex + i + this.values[valuesIndex] + (valuesIndex+1 != this.values.length ? 1:0), valuesIndex + 1,
							remainingSpaces - i);
				}
			}
		}
	}

	private boolean isCompatible(int[] rowRes, int[] currentRow) {
		for(int i = 0; i < rowRes.length; i++){
			if(rowRes[i] == 0){
				// The line resolution isn't finished
				break;
			}
			if(currentRow[i] == -1 && rowRes[i] != -1){
				return false;
			} else if(currentRow[i] == 1 && rowRes[i] != 1){
				return false;
			}
		}
		return true;
	}
}
