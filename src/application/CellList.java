package application;

public class CellList {
	private Cell[][] cells = new Cell[Main.getEnvironment().getWidth()][Main.getEnvironment().getHeight()];
	
	public Cell[][] getCells() {
		return cells;
	}
	
	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
	
	private static CellList instance;
	
	public static CellList getInstance() {
		if (instance == null) {
			instance = new CellList();
		}
		
		return instance;
	}
	
	private CellList() {}
	
	public Cell getCell(int x, int y) {
		return cells[x][y];
	}
}
