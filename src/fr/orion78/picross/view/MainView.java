package fr.orion78.picross.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.orion78.picross.model.Grid;

public class MainView extends JFrame {
	private static final long serialVersionUID = -805650085889143277L;
	private static int SCALE = 25;

	public MainView(Grid g) {
		super("Demo");
		this.getContentPane().add(new JPanel() {
			private static final long serialVersionUID = 1622542075293788262L;

			@Override
			protected void paintComponent(Graphics gr) {
				super.paintComponent(gr);
				// Draw Background
				gr.setColor(Color.WHITE);
				gr.fillRect(0, 0, g.getWidth()*(SCALE+1), g.getHeight()*(SCALE+1));
				
				// Draw Cells
				int[][] values = g.getValues();
				for(int i = 0 ; i < values.length; i++){
					for(int j = 0; j < values[i].length; j++){
						if(values[i][j] == 1){
							gr.setColor(Color.BLACK);
							gr.fillRect(i*(SCALE+1), j*(SCALE+1), SCALE, SCALE);
						} else if(values[i][j] == -1){
							gr.setColor(Color.BLUE);
							gr.drawLine(i*(SCALE+1), j*(SCALE+1), i*(SCALE+1) + SCALE, j*(SCALE+1) + SCALE);
							gr.drawLine(i*(SCALE+1), j*(SCALE+1) + SCALE, i*(SCALE+1) + SCALE, j*(SCALE+1));
						}
					}
				}
				
				// Draw Grid
				gr.setColor(Color.RED);
				for(int i = 0; i < g.getWidth()-1; i++){
					gr.drawLine((i+1)*(SCALE+1) - 1, 0, (i+1)*(SCALE+1) - 1, g.getHeight()*(SCALE+1));
				}
				for(int i = 0; i < g.getHeight()-1; i++){
					gr.drawLine(0, (i+1)*(SCALE+1) - 1, g.getWidth()*(SCALE+1), (i+1)*(SCALE+1) - 1);
				}
			}
		});
		this.setBounds(0, 0, 2000, 1300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
