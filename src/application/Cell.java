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
		shape.setFill(type.getColor());
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
	
	private NeighbourList neighbourList;
	
	public NeighbourList getNeighbourList()
	{
		return neighbourList;
	}
	
	public void setNeighbourList(NeighbourList neighbourList)
	{
		this.neighbourList = neighbourList;
	}
	
	private int age;
	
	public int getAge()
	{
		return age;
	}
	
	public void setAge(int age)
	{
		this.age = age;
	}
	
	public void growOlder()
	{
		this.age++;
	}
	
	public Cell(CellType type, Shape shape, int x, int y) {
		this.type = type;
		this.shape = shape;
		this.x = x;
		this.y = y;
	}
	
	public Cell(Cell cell) {}
	
	public NeighbourList getNeighbours()
	{
		if (neighbourList == null) {
			neighbourList = Main.getEnvironment().getNeighbours(this);
		}
		
		return neighbourList;
	}
		
	public boolean goTo(CellType cellType)
	{
		Cell[] cells = neighbourList.getCells(cellType);
		
		if (!Utilities.getInstance().isEmpty(cells)) {
			int x = Utilities.getInstance().generateRandom(0, Utilities.getInstance().getArrayLength(cells));
			cells[x].setType(cellType);
			setType(CellType.DUMMY);
			
			return true;
		} else {
			return false;
		}
	}
	
	public boolean reproduce()
	{
		Cell[] cells = neighbourList.getCells(CellType.DUMMY);
		
		if (!Utilities.getInstance().isEmpty(cells) && Utilities.getInstance().generateRandom(0, 1) == 1) {
			x = Utilities.getInstance().generateRandom(0, Utilities.getInstance().getArrayLength(cells));
			cells[x].setType(type);
			return true;
		}
		
		return false;
	}
	
	public boolean die()
	{
		if (Utilities.getInstance().generateRandom(0, 1) == 1) {
			setType(CellType.DUMMY);
			return true;
		} else {
			return false;
		}
	}
}
