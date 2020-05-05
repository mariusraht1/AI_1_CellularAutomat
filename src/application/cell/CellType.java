package application.cell;

import javafx.scene.paint.Color;

public enum CellType {
    EMPTY(Color.WHITE), PREDATOR(Color.RED), PREY(Color.DODGERBLUE), GRASS1(Color.KHAKI), GRASS2(Color.GREENYELLOW),
    GRASS3(Color.LIMEGREEN);

    private Color color;

    public Color getColor() {
	return color;
    }

    public void setColor(Color color) {
	this.color = color;
    }

    private int maxLitter;

    public int getMaxLitter() {
	return maxLitter;
    }

    public void setMaxLitter(int maxLitter) {
	this.maxLitter = maxLitter;
    }

    private CellType(Color color) {
	this.setColor(color);
    }

    private CellType(Color color, int maxLitter) {
	this.setColor(color);
	this.setMaxLitter(maxLitter);
    }

    public int getNumOfCells() {
	int numOfCells = 0;

	for (int i = 0; i < CellList.getInstance().getCells().length; i++) {
	    for (int j = 0; j < CellList.getInstance().getCells()[i].length; j++) {
		if (CellList.getInstance().getCells()[i][j].getType().equals(this)) {
		    numOfCells++;
		}
	    }
	}

	return numOfCells;
    }

    public double getRelationTo(CellType cellType) {
	double numOfCellsType1 = getNumOfCells();
	double numOfCellsType2 = cellType.getNumOfCells();
	double total = numOfCellsType1 + numOfCellsType2;

	return numOfCellsType1 / total;
    }
}