package application.cell;

import java.util.Optional;

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
	setColor();
    }

    private Cell newState;

    public Cell getNewState() {
	return newState;
    }

    public void setNewState(Optional<CellType> type, Optional<Integer> age, Optional<Integer> hunger) {
	this.newState = new Cell();

	if (type.isPresent()) {
	    this.newState.setType(type.get());
	}

	if (age.isPresent()) {
	    this.newState.setAge(age.get());
	}

	if (hunger.isPresent()) {
	    this.newState.setHunger(hunger.get());
	}
    }

    public void applyNewState() {
	this.type = newState.getType();
	this.age = newState.getAge();
	this.hunger = newState.getHunger();
	this.newState = null;

	setColor();
    }

    private Shape shape;

    public Shape getShape() {
	return shape;
    }

    public void setShape(Shape shape) {
	this.shape = shape;
    }

    private void setColor() {
	if (this.shape != null) {
	    this.shape.setFill(this.type.getColor());
	}
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

    public Cell() {
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

	    cells[x].setNewState(Optional.of(type), Optional.of(age), Optional.of(hunger));
	    setNewState(Optional.of(CellType.EMPTY), Optional.empty(), Optional.empty());
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
		    cells[x].setNewState(Optional.of(type), Optional.empty(), Optional.empty());
		    setNewState(Optional.of(CellType.EMPTY), Optional.empty(), Optional.empty());
		    return true;
		}
	    }
	}

	return false;
    }

    // TODO: Predator is reproducing itself though no prey
    // Expected result: They should die out without prey

    public boolean reproduce() {
	if (age >= type.getMinLitterAge() && age <= type.getMaxLitterAge() && hunger < type.getMaxHunger()) {
	    Cell[] cells = neighbourList.getCells(CellType.EMPTY);

	    if (!Utilities.getInstance().isEmpty(cells)) {
		Log.getInstance().add("Reproduce: Age = " + age + "; Hunger = " + hunger);

		// The less animals of the same type around the animal the more offsprings
		for (int i = 0; i < Utilities.getInstance().getArrayLength(cells); i++) {
		    if (cells[i] != null) {
			cells[i].setNewState(Optional.of(type), Optional.empty(), Optional.empty());
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

	    setNewState(Optional.of(CellType.EMPTY), Optional.empty(), Optional.empty());
	    return true;
	}

	return false;
    }
}