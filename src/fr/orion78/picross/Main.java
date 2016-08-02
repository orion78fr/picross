package fr.orion78.picross;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import fr.orion78.picross.model.Grid;
import fr.orion78.picross.solver.Solver;
import fr.orion78.picross.view.MainView;

public class Main {

	public Main() {
		throw new RuntimeException(this.getClass().getName() + " is not instanciable!");
	}

	public static void main(String[] args) {
		// TODO
		
		File f = new File("parachute.txt");
		char[] c = new char[(int) f.length()];
		Reader r;
		try {
			r = new FileReader(f);
		
			r.read(c);
		
			r.close();
		} catch (IOException e) {
			
			return;
		}
		String s = new String(c);
		
		String[] sep = s.split("[\n][\n]+");
		
		String[] colsStr = sep[0].split("[\n]");
		int[][] cols = new int[colsStr.length][];
		for(int i = 0; i < colsStr.length; i++){
			String[] splittedCol = colsStr[i].split(" ");
			cols[i] = new int[splittedCol.length];
			for(int j = 0; j < splittedCol.length; j++){
				cols[i][j] = Integer.parseInt(splittedCol[j]);
			}
		}
		
		String[] rowsStr = sep[1].split("[\n]");
		int[][] rows = new int[rowsStr.length][];
		for(int i = 0; i < rowsStr.length; i++){
			String[] splittedRow = rowsStr[i].split(" ");
			rows[i] = new int[splittedRow.length];
			for(int j = 0; j < splittedRow.length; j++){
				rows[i][j] = Integer.parseInt(splittedRow[j]);
			}
		}
		
		/*int[][] cols = {
				{1,1,1,1},
				{2,3,2},
				{6,1,1},
				{4,1,4},
				{2,1,3},
				{1,1,4},
				{1,3,3},
				{5,2},
				{3,2,1,2},
				{4,1,1,3},
				{2,2,5},
				{1,1,4},
				{2,1,6},
				{7,1},
				{3,2},
				{2,1,1,3},
				{2,2,4,1},
				{3,7,1},
				{2,3,1,1},
				{2,1,1}
		};
		
		int[][] rows = {
				{3},
				{4,2,6},
				{4,1,7},
				{4,3,3,3},
				{4,5,1},
				{3,3,1,3},
				{3,2,2,3},
				{3,3,2,3},
				{3,1,3},
				{2,3,3},
				{9,3},
				{5,3,4},
				{4,3,3},
				{1,1,3,5},
				{4,2}
		};*/
		
		/*int[][] cols = {
				{8},
				{3,6},
				{4,4,2},
				{2,1,9},
				{1,2,2},
				
				{2,1,2},
				{4,2},
				{2,1,4},
				{2,2},
				{3},
				
				{2,1},
				{4},
				{4},
				{5,3},
				{2,1,2,2},
				
				{1,1,1,1,1},
				{1,1,2,2},
				{4,3},
				{1,5},
				{1,1,1}
		};
		
		int[][] rows = {
				{1},
				{2},
				{5,6},
				{2,3,2,1},
				{2,1,3,1,1},
				
				{1,1,1,1,7},
				{2,5,4,1},
				{4,5,1},
				{4,1,2,3,1},
				{6,6,4},
				
				{8,1,1,1},
				{2,1,2,2,2},
				{1,2,3},
				{2,2},
				{2}
		};*/
				
		Grid g = new Grid(cols.length, rows.length, cols, rows);
		
		long debut = System.currentTimeMillis();
		Solver.solve(g);
		long fin = System.currentTimeMillis();
		System.out.println((double)(fin-debut)/1000 + " s");
		
		new MainView(g).setVisible(true);
	}

}
