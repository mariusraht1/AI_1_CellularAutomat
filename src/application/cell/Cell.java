package application.cell;

import application.Main;
import application.Utilities;
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
	}

	return false;
    }

    // Alternatively: HUNGER attribute
    public boolean eat(CellType cellType) {
	Cell[] cells = neighbourList.getCells(cellType);

	if (!Utilities.getInstance().isEmpty(cells)) {
	    // Probability of eating: p = 1 - r
	    double p = 1 - type.getRelationTo(cellType);
	    int max = (int) (1 / p);

	    if (Utilities.getInstance().generateRandom(1, max) == 1) {
		int x = Utilities.getInstance().generateRandom(0, Utilities.getInstance().getArrayLength(cells) - 1);
		cells[x].setNewType(type);
		setNewType(CellType.EMPTY);
		return true;
	    }
	}

	return false;
    }

    public boolean reproduce() {
	Cell[] cells = neighbourList.getCells(CellType.EMPTY);

	if (!Utilities.getInstance().isEmpty(cells)) {
	    CellType oppositeCellType = CellType.PREDATOR;

	    if (type.equals(CellType.PREDATOR)) {
		oppositeCellType = CellType.PREY;
	    }

	    // Probability of reproduction: p = 1 - r
	    double p = 1 - type.getRelationTo(oppositeCellType);
	    int max = (int) (1 / p);

	    if (Utilities.getInstance().generateRandom(1, max) == 1) {

		// Birth rate: Give birth to x offsprings
		// b = (1 - r) * maxNumOfLitter
		int b = (int) (p * Main.MaxNumOfLitter);

		for (int i = 0; i < b; i++) {
		    if (cells[i] != null) {
			cells[i].setNewType(type);
		    } else {
			break;
		    }
		}

		return true;
	    }
	}

	return false;
    }

    public boolean die() {
	CellType oppositeCellType = CellType.PREDATOR;

	if (type.equals(CellType.PREDATOR)) {
	    oppositeCellType = CellType.PREY;
	}

	// Probability of death: p = r
	double p = type.getRelationTo(oppositeCellType);
	int max = (int) (1 / p);

	if (Utilities.getInstance().generateRandom(1, max) == 1) {
	    setNewType(CellType.EMPTY);
	    return true;
	}

	return false;
    }
}
