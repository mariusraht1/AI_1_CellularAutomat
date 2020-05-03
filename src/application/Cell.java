package application;

import javafx.scene.shape.Shape;

public class Cell {
    private CellType type;

    public CellType getType() {
	return type;
    }

    public void setType(CellType type) {
	this.type = type;
	shape.setFill(type.getColor());
    }

    private CellType newType;

    public CellType getNewType() {
	return newType;
    }

    public void setNewType(CellType newType) {
	this.newType = newType;
    }

    private Shape shape;

    public Shape getShape() {
	return shape;
    }

    public void setShape(Shape shape) {
	this.shape = shape;
    }

    private int x;

    public int getX() {
	return x;
    }

    public void setX(int x) {
	this.x = x;
    }

    private int y;

    public int getY() {
	return y;
    }

    public void setY(int y) {
	this.y = y;
    }

    private NeighbourList neighbourList;

    public NeighbourList getNeighbourList() {
	return neighbourList;
    }

    public void setNeighbourList(NeighbourList neighbourList) {
	this.neighbourList = neighbourList;
    }

    private int age;

    public int getAge() {
	return age;
    }

    public void setAge(int age) {
	this.age = age;
    }

    public void growOlder() {
	this.age++;
    }

    public Cell(CellType type, Shape shape, int x, int y) {
	this.type = type;
	this.shape = shape;
	this.x = x;
	this.y = y;
    }

    public Cell(Cell cell) {
    }

    public NeighbourList getNeighbours() {
	if (neighbourList == null) {
	    neighbourList = Main.getEnvironment().getNeighbours(this);
	}

	return neighbourList;
    }

    public boolean goTo(CellType cellType) {
	Cell[] cells = neighbourList.getCells(cellType);

	if (!Utilities.getInstance().isEmpty(cells)) {
	    int x = Utilities.getInstance().generateRandom(0, Utilities.getInstance().getArrayLength(cells) - 1);
	    cells[x].setNewType(type);
	    setNewType(CellType.EMPTY);

	    return true;
	} else {
	    return false;
	}
    }

    // TODO Fortpflanzung sollte von Beständen abhängen
    // < 0.2 W=1.0
    // < 0.4 W=0.8
    // < 0.6 W=0.6
    // < 0.8 W=0.4
    // < 1.0 W=0.2
    public boolean reproduce() {
	Cell[] cells = neighbourList.getCells(CellType.EMPTY);

	// Je weniger Bestand umso höhere Wahrscheinlichkeit
	int maxW = 12;

	if (type.getNumOfCells() < 10) {
	    maxW = 1;
	}

	if (!Utilities.getInstance().isEmpty(cells) && Utilities.getInstance().generateRandom(1, maxW) == 1) {
	    // Bis zu x Nachkommen werfen
	    for (int i = 0; i < 4; i++) {
		if (cells[i] != null) {
		    cells[i].setNewType(type);
		} else {
		    break;
		}
	    }
	    return true;
	}

	return false;
    }

    // Tod sollte von Beständen abhängen
    // < 0.2 W=0.2
    // < 0.4 W=0.4
    // < 0.6 W=0.6
    // < 0.8 W=0.8
    // < 1.0 W=1.0
    public boolean die() {
	// Je mehr Bestand umso höhere Wahrscheinlichkeit
	int maxW = 2;

	if (type.getNumOfCells() < 10) {
	    maxW = 12;
	}
	
	if (Utilities.getInstance().generateRandom(1, maxW) == 1) {
	    setNewType(CellType.EMPTY);
	    return true;
	} else {
	    return false;
	}
    }
}
