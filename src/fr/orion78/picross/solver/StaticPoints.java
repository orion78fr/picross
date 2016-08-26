package fr.orion78.picross.solver;

import java.util.Arrays;

import fr.orion78.picross.solver.exception.IncorrectSizeException;
import fr.orion78.picross.solver.exception.NoStaticPointsException;

public class StaticPoints {
	private int[] points = null;
	private int remaining;
	
	public StaticPoints(int remaining) {
		this.remaining = remaining;
	}
	
	public void add(int[] value) throws NoStaticPointsException{
		if(this.points == null){
			this.points = Arrays.copyOf(value, value.length);
		} else {
			if(this.points.length != value.length){
				throw new IncorrectSizeException();
			}
			for(int i = 0; i < value.length; i++){
				if(this.points[i] != 0 && this.points[i] != value[i]){
					this.points[i] = 0;
					this.remaining--;
				}
			}
			if(this.remaining == 0){
				throw new NoStaticPointsException();
			}
		}
	}
	
	public int[] getResult(){
		return this.points;
	}
}
