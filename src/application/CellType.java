package application;

import javafx.scene.paint.Color;

public enum CellType
{
	DUMMY(Color.WHITE), PREDATOR(Color.RED), PREY(Color.DODGERBLUE), GRASS1(Color.KHAKI), GRASS2(Color.GREENYELLOW),
	GRASS3(Color.LIMEGREEN);
	
	private Color color;
	
	private CellType(Color color) {
		this.setColor(color);
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public int getNumOfCells()
	{
		int numOfCells = 0;
		
		for (int i = 0; i < CellList.getInstance().getCells().length; i++) {
			for (int j = 0; j < CellList.getInstance().getCells()[i].length; j++) {
				if(CellList.getInstance().getCells()[i][j].getType().equals(this)) {
					numOfCells++;
				}
			}
		}
		
		return numOfCells;
	}
}