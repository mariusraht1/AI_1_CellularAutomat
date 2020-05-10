package application.cell;

import javafx.scene.paint.Color;

public enum CellType {
    EMPTY(Color.WHITE), PREDATOR(Color.RED, 5, 4, 2, 4, 3), PREY(Color.DODGERBLUE, 5, 4, 2, 4, 3);

    private Color color;

    public Color getColor() {
	return color;
    }

    public void setColor(Color color) {
	this.color = color;
    }

    private int maxAge;

    public int getMaxAge() {
	return maxAge;
    }

    public void setMaxAge(int maxAge) {
	this.maxAge = maxAge;
    }

    private int maxLitter;

    public int getMaxLitter() {
	return maxLitter;
    }

    public void setMaxLitter(int maxLitter) {
	this.maxLitter = maxLitter;
    }

    private int minLitterAge;
    
    public int getMinLitterAge() {
	return minLitterAge;
    }

    public void setMinLitterAge(int minLitterAge) {
	this.minLitterAge = minLitterAge;
    }
    
    private int maxLitterAge;

    public int getMaxLitterAge() {
	return maxLitterAge;
    }

    public void setMaxLitterAge(int maxLitterAge) {
	this.maxLitterAge = maxLitterAge;
    }

    private int maxHunger;

    public int getMaxHunger() {
	return maxHunger;
    }

    public void setMaxHunger(int maxHunger) {
	this.maxHunger = maxHunger;
    }

    private CellType(Color color) {
	this.setColor(color);
    }

    private CellType(Color color, int maxAge, int maxLitter, int minLitterAge, int maxLitterAge, int maxHunger) {
	this.setColor(color);
	this.setMaxAge(maxAge);
	this.setMaxLitter(maxLitter);
	this.setMaxLitterAge(maxLitterAge);
	this.setMaxHunger(maxHunger);
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