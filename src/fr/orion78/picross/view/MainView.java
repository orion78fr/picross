package fr.orion78.picross.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import jpen.PButton;
import jpen.PKind;
import jpen.PLevel;
import jpen.PLevelEvent;
import jpen.event.PenAdapter;
import jpen.owner.multiAwt.AwtPenToolkit;
import fr.orion78.picross.model.Grid;

public class MainView extends JFrame {
	private static final long serialVersionUID = -805650085889143277L;
	private static int SCALE = 15;

	private boolean finished = false;
	
	public MainView(Grid g) {
		super("Demo");
		
		// Get size of the numbers
		int xOffset = g.getMaxNumRow() * (SCALE+1) + 1;
		int yOffset = g.getMaxNumCol() * (SCALE+1) + 1;
		
		JPanel pane = new JPanel() {
			private static final long serialVersionUID = 1622542075293788262L;

			@Override
			protected void paintComponent(Graphics gr) {
				super.paintComponent(gr);
				
				// Draw Background
				gr.setColor(Color.WHITE);
				gr.fillRect(0, 0, xOffset + g.getWidth()*(SCALE+1) + g.getWidth()/5,
						yOffset + g.getHeight()*(SCALE+1) + g.getHeight()/5);
								
				// Draw Numbers
				Font f = new Font("Arial", Font.PLAIN, SCALE);
				gr.setFont(f);
				gr.setColor(Color.BLACK);
				// http://stackoverflow.com/questions/1779708/java-awt-fitting-text-in-a-box
				// Seems to work...
				FontMetrics fm = gr.getFontMetrics();
				double shrink = (double)SCALE / fm.getHeight();
				double newSize = SCALE * shrink;
				double newAsc = fm.getAscent() * shrink;
				int textOffset = (int)newAsc - fm.getLeading();
				f = f.deriveFont((float)newSize);
				gr.setFont(f);
				fm = gr.getFontMetrics();
				
				for(int i = 0; i < g.getHeight(); i++){
					int[] r = g.getRow(i).getValues();
					for(int j = 0; j < r.length; j++){
						String s = String.valueOf(r[j]);
						gr.drawString(s, j*(SCALE+1) + (int)((SCALE - fm.getStringBounds(s, gr).getMaxX())/2),
								yOffset + i*(SCALE+1) + textOffset + i/5 + 2);
					}
				}
				for(int i = 0; i < g.getWidth(); i++){
					int[] r = g.getColumn(i).getValues();
					for(int j = 0; j < r.length; j++){
						String s = String.valueOf(r[j]);
						gr.drawString(s, xOffset + (i)*(SCALE+1) + (int)((SCALE - fm.getStringBounds(s, gr).getMaxX())/2) + i/5,
								j *(SCALE+1) + textOffset);
					}
				}
				
				// Draw Cells
				int[][] values = g.getValues();
				for(int i = 0 ; i < values.length; i++){
					for(int j = 0; j < values[i].length; j++){
						if(values[i][j] == 1){
							gr.setColor(Color.BLACK);
							gr.fillRect(xOffset + i*(SCALE+1) + i/5, yOffset + j*(SCALE+1) + j/5, SCALE, SCALE);
						} else if(values[i][j] == -1){
							gr.setColor(Color.BLUE);
							gr.drawLine(xOffset + i*(SCALE+1) + i/5, yOffset + j*(SCALE+1) + j/5,
									xOffset + i*(SCALE+1) + SCALE + i/5, yOffset + j*(SCALE+1) + SCALE + j/5);
							gr.drawLine(xOffset + i*(SCALE+1) + i/5, yOffset + j*(SCALE+1) + SCALE + j/5,
									xOffset + i*(SCALE+1) + SCALE + i/5, yOffset + j*(SCALE+1) + j/5);
						}
					}
				}
				
				// Draw Grid
				gr.setColor(Color.RED);
				for(int i = 0; i < g.getWidth(); i++){
					gr.drawLine(xOffset + (i)*(SCALE+1) - 1 + i/5, 0,
							xOffset + (i)*(SCALE+1) - 1 + i/5, yOffset + g.getHeight()*(SCALE+1) + g.getHeight()/5 - 1);
					if(i%5==0){
						gr.drawLine(xOffset + (i)*(SCALE+1) - 1 + i/5 - 1, 0,
								xOffset + (i)*(SCALE+1) - 1 + i/5 - 1, yOffset + g.getHeight()*(SCALE+1) + g.getHeight()/5 - 1);
					}
				}
				for(int i = 0; i < g.getHeight(); i++){
					gr.drawLine(0, yOffset + (i)*(SCALE+1) - 1 + i/5,
							xOffset + g.getWidth()*(SCALE+1) + g.getWidth()/5 - 1, yOffset + (i)*(SCALE+1) - 1 + i/5);
					if(i%5==0){
						gr.drawLine(0, yOffset + (i)*(SCALE+1) - 1 + i/5 - 1,
								xOffset + g.getWidth()*(SCALE+1) + g.getWidth()/5 - 1, yOffset + (i)*(SCALE+1) - 1 + i/5 - 1);
					}
				}
			}
		};
		this.getContentPane().add(pane);
		this.setBounds(0, 0, 2000, 1300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		AwtPenToolkit.addPenListener(this.getContentPane(), new PenAdapter() {
		    @Override
		    public void penLevelEvent(PLevelEvent ev) {
		    	if(finished){
		    		return;
		    	}
		    	
		    	float pressure=ev.pen.getLevelValue(PLevel.Type.PRESSURE);
		    	
		    	if(pressure > 0){
			    	float x = ev.pen.getLevelValue(PLevel.Type.X);
			    	float y = ev.pen.getLevelValue(PLevel.Type.Y);
			    	int cellX = (int)(x-xOffset - x/(SCALE*5)) / (SCALE+1);
			    	int cellY = (int)(y-yOffset - y/(SCALE*5)) / (SCALE+1);
			    	
			    	if(cellX < 0 || cellX >= g.getWidth() || cellY < 0 || cellY >=g.getHeight()){
			    		// OOB
			    		return;
			    	}
			    	
			    	PKind.Type kindType=ev.pen.getKind().getType();
			    	boolean rightButton = ev.pen.getButtonValue(PButton.Type.RIGHT);
			    	
			    	switch(kindType){
					case CURSOR:
						return;
					case CUSTOM:
						return;
					case ERASER:
						g.set(cellX, cellY, 0);
						break;
					case IGNORE:
						return;
					case STYLUS:
						if(rightButton){
							g.set(cellX, cellY, -1);
						} else {
							g.set(cellX, cellY, 1);
						}
						break;
					default:
						return;
			    	}

					pane.repaint();
					
					if(g.isCorrect()){
						JOptionPane.showMessageDialog(null, "Bravo!");
						finished = true;
					}
		    	}
		    }
		});
	}
}
