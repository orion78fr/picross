package fr.orion78.picross.model;

import java.util.Arrays;

import fr.orion78.picross.solver.StaticPoints;
import fr.orion78.picross.solver.exception.NoStaticPointsException;

public class Row {
	private int[] values;

	public Row(int[] values) {
		this.values = values;
	}

	public int[] getValues() {
		return values;
	}
	
	public int[] computePossibleStates3(int[] currentRow){
		int remainingCells = 0;
		for(int i = 0; i < currentRow.length; i++){
			if(currentRow[i] == 0){
				remainingCells++;
			}
		}
		StaticPoints sp = new StaticPoints(remainingCells);
		
		int sum = 0;
		for(int i = 0; i < this.values.length; i++){
			sum += this.values[i];
		}
		// Spaces needed - mandatory spaces
		int remainingSpaces = (currentRow.length - sum) - (this.values.length - 1);
		
		int[] currentRes = new int[currentRow.length];
		
		try {
			computePossibleStatesHelper(sp, currentRow, currentRes, 0, 0, remainingSpaces);
		} catch(NoStaticPointsException e) {
			// So, nothing to do here, it's for fast-exit
		}
		
		return sp.getResult();
	}
	
	private void computePossibleStatesHelper(StaticPoints sp, int[] currentRow,
			int[] currentRes, int resIndex, int valuesIndex, int remainingSpaces)
					throws NoStaticPointsException{
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
				sp.add(newRes);
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
					computePossibleStatesHelper(sp, currentRow, newRes,
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
