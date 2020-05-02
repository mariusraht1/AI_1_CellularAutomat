package application.environment;

import application.Cell;
import application.CellList;
import application.CellType;
import application.Main;
import application.Utilities;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Environment implements EnvironmentInterface {
	private int width = 50;
	
	public int getWidth()
	{
		return width;
	}
	
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	private int height = 50;
	
	public int getHeight()
	{
		return height;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}
	
	protected Environment() {}
	
	public void generateCells(CellType cellType, int numOfCells)
	{
		for (int i = 1; i <= numOfCells; i++) {
			int n = 1;
			boolean wasGenerated = false;
			
			while (!wasGenerated) {
				int x = Utilities.getInstance().generateRandom(0, Main.getEnvironment().getWidth());
				int y = Utilities.getInstance().generateRandom(0, Main.getEnvironment().getHeight());
				Cell cell = CellList.getInstance().getCells()[x][y];
				
				if (cell.getType().equals(CellType.EMPTY)) {
					cell.setType(cellType);
					wasGenerated = true;
				} else if (n == 10) {
					break;
				}
				
				n++;
			}
		}
	}
	
	public void setSizeOfAxis(int sizeOfAxis, GridPane gp_environment)
	{
		Main.getEnvironment().setWidth(sizeOfAxis);
		Main.getEnvironment().setHeight(sizeOfAxis);
		Main.getEnvironment().generateEnvironment(gp_environment);
	}
	
	protected void generateEnvironment(GridPane gp_environment)
	{
		gp_environment.getChildren().clear();
		
		for (int i = 0; i < Main.getEnvironment().getWidth(); i++) {
			for (int j = 0; j < Main.getEnvironment().getHeight(); j++) {
				Rectangle shape = new Rectangle(8, 8, Color.WHITE);
				shape.setStroke(Color.DIMGREY);
				GridPane.setMargin(shape, new Insets(1));
				
				// TODO ArrayIndexOutOfBoundsException
				CellList.getInstance().getCells()[i][j] = new Cell(CellType.EMPTY, shape, i, j);
				
				gp_environment.add(shape, i, j);
			}
		}
		
		CellList.getInstance().determineNeighbours();
	}
}
