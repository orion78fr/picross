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
		
		File f = new File("beach_side.txt");
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

		
		Grid g = new Grid(cols.length, rows.length, cols, rows);
		
		long debut = System.currentTimeMillis();
		Solver.solve(g);
		long fin = System.currentTimeMillis();
		System.out.println((double)(fin-debut)/1000 + " s");
		
		new MainView(g).setVisible(true);
	}

}
