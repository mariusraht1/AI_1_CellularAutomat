package application.cell;

import application.Main;

public class NeighbourList {
    private Cell[] content;

    public Cell[] getContent() {
	return content;
    }

    public void setContent(Cell[] content) {
	this.content = content;
    }

    public int getLength() {
	return content.length;
    }

    public NeighbourList() {
	content = new Cell[Main.getEnvironment().getNumOfNeighbours()];
    }

    public void set(int i, Cell cell) {
	content[i] = cell;
    }

    public Cell[] getCells(CellType cellType) {
	int x = 0;
	Cell[] cells = new Cell[Main.getEnvironment().getNumOfNeighbours()];

	for (int i = 0; i < content.length; i++) {
	    if (content[i] != null && content[i].getType().equals(cellType)) {
		cells[x] = content[i];
		x++;
	    }
	}

	return cells;
    }
}
