package application.cell;

import application.Log;
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
	this.hunger = 0;
	this.age = 0;

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

    private int hunger;

    public int getHunger() {
	return hunger;
    }

    public void setHunger(int hunger) {
	this.hunger = hunger;
    }

    public void becomeHungrier() {
	this.hunger++;
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
	    
	    Log.getInstance().add("Go to: x = " + cells[x].x + "; y = " + cells[x].y);
	    
	    cells[x].setNewType(type);
	    setNewType(CellType.EMPTY);
	    return true;
	}

	return false;
    }

    public boolean eat(CellType cellType) {
	if (hunger > 0) {
	    Cell[] cells = neighbourList.getCells(cellType);

	    if (!Utilities.getInstance().isEmpty(cells)) {
		int min = hunger;
		int max = type.getMaxHunger();

		Log.getInstance().add("Eat: Min = " + min + "; Max = " + max);
		
		if (min > 0 && max > 0 && min <= max && Utilities.getInstance().generateRandom(min, max) == min) {
		    int x = Utilities.getInstance().generateRandom(0,
			    Utilities.getInstance().getArrayLength(cells) - 1);
		    cells[x].setNewType(type);
		    setNewType(CellType.EMPTY);
		    return true;
		}
	    }
	}

	return false;
    }

    // TODO: Predator is reproducing itself though no prey
    
    public boolean reproduce() {
	if (age >= type.getMinLitterAge() && age <= type.getMaxLitterAge()) {
	    Cell[] cells = neighbourList.getCells(CellType.EMPTY);

	    if (!Utilities.getInstance().isEmpty(cells) && hunger < type.getMaxHunger()) {
		Log.getInstance().add("Reproduce: Age = " + age + "; Hunger = " + hunger);
		
		// The less animals of the same type around the animal the more offsprings
		for (int i = 0; i < Utilities.getInstance().getArrayLength(cells); i++) {
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
	if (hunger > type.getMaxHunger() || age > type.getMaxAge()) {
	    Log.getInstance().add("Die: Age = " + age + "; Hunger = " + hunger);

	    setNewType(CellType.EMPTY);
	    return true;
	}

	return false;
    }
}
