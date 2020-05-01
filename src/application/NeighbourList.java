package application;

public class NeighbourList {
	private Cell[] content;
	
	public Cell[] getContent()
	{
		return content;
	}
	
	public void setContent(Cell[] content)
	{
		this.content = content;
	}
	
	public int getLength()
	{
		return content.length;
	}
	
	public NeighbourList() {
		content = new Cell[Main.getEnvironment().getNumOfNeighbours()];
	}
	
	public void set(int i, Cell cell)
	{
		content[i] = cell;
	}
	
	public Cell getCell(CellType cellType)
	{
		Cell cell = null;
		
		for (int i = 0; i < content.length; i++) {
			if (content[i].getType().equals(cellType)) {
				cell = content[i];
			}
		}
		
		return cell;
	}
}
