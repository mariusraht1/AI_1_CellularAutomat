package application;

import javafx.scene.shape.Shape;

public class Cell {
	private CellType type;
	
	public CellType getType()
	{
		return type;
	}
	
	public void setType(CellType type)
	{
		this.type = type;
	}
	
	private Shape shape;
	
	public Shape getShape()
	{
		return shape;
	}
	
	public void setShape(Shape shape)
	{
		this.shape = shape;
	}
	
	private int x;
	
	public int getX()
	{
		return x;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	private int y;
	
	public int getY()
	{
		return y;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	private Cell[] neighbourList = new Cell[Main.getEnvironment().getNumOfNeighbours()];
	
	public Cell[] getNeighbourList()
	{
		return neighbourList;
	}
	
	public void setNeighbourList(Cell[] neighbourList)
	{
		this.neighbourList = neighbourList;
	}
	
	public Cell(CellType type, Shape shape, int x, int y)
	{
		this.shape = shape;
		this.x = x;
		this.y = y;
	}
	
	public Cell(Cell cell) {}
	
	public void changeTo(CellType cellType)
	{
		shape.setFill(cellType.getColor());
	}
	
	public Cell[] getNeighbours()
	{
		if (neighbourList.length == 0) {
			neighbourList = Main.getEnvironment().getNeighbours(this);
		}
		
		return neighbourList;
	}
	
}
