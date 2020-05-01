package application;

public class CellController {
	private Cell[][] cells = new Cell[Main.getEnvironment().getWidth()][Main.getEnvironment().getHeight()];
	
	public Cell[][] getCells() {
		return cells;
	}
	
	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
	
	private static CellController instance;
	
	public static CellController getInstance() {
		if (instance == null) {
			instance = new CellController();
		}
		
		return instance;
	}
	
	private CellController() {}
	
	public Cell getCell(int x, int y) {
		return cells[x][y];
	}
}
