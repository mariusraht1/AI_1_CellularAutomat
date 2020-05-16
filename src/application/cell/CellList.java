package application.cell;

import application.Main;

public class CellList {
    private Cell[][] cells;

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

    public static void setInstance(CellList instance) {
	CellList.instance = instance;
    }

    protected CellList() {
	cells = new Cell[Main.getEnvironment().getWidth()][Main.getEnvironment().getHeight()];
    }

    public Cell getCell(int x, int y) {
	return cells[x][y];
    }

    public void determineNeighbours() {
	for (int i = 0; i < cells.length; i++) {
	    for (int j = 0; j < cells[i].length; j++) {
		cells[i][j].getNeighbours();
	    }
	}
    }
}
