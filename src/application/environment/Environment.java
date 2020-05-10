package application.environment;

import application.History;
import application.Log;
import application.Main;
import application.Utilities;
import application.cell.Cell;
import application.cell.CellList;
import application.cell.CellType;
import javafx.geometry.Insets;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Environment implements EnvironmentInterface {
    private int width = 50;

    public int getWidth() {
	return width;
    }

    public void setWidth(int width) {
	this.width = width;
    }

    private int height = 50;

    public int getHeight() {
	return height;
    }

    public void setHeight(int height) {
	this.height = height;
    }

    private int numOfRounds = 0;

    public int getNumOfRounds() {
	return numOfRounds;
    }

    public void setNumOfRounds(int numOfRounds) {
	this.numOfRounds = numOfRounds;
    }

    protected Environment() {
    }

    public void generatePredefinedCells(CellType cellType, int numOfCells) {
	for (int i = 1; i <= numOfCells; i++) {
	    int n = 1;
	    boolean wasGenerated = false;

	    while (!wasGenerated) {
		int x = Utilities.getInstance().generateRandom(0, Main.getEnvironment().getWidth() - 1);
		int y = Utilities.getInstance().generateRandom(0, Main.getEnvironment().getHeight() - 1);
		Cell cell = CellList.getInstance().getCells()[x][y];

		if (cell.getType().equals(CellType.EMPTY)) {
		    cell.setType(cellType);
		    cell.growOlder();
		    cell.becomeHungrier();
		    wasGenerated = true;
		} else if (n == 10) {
		    break;
		}

		n++;
	    }
	}
    }

    public void setSizeOfAxis(int sizeOfAxis, GridPane gp_environment) {
	setWidth(sizeOfAxis);
	setHeight(sizeOfAxis);
	generateGrid(gp_environment);
	setNumOfRounds(0);
    }

    protected void generateGrid(GridPane gp_environment) {
	gp_environment.getChildren().clear();
	CellList.setInstance(null);

	for (int y = 0; y < Main.getEnvironment().getHeight(); y++) {
	    for (int x = 0; x < Main.getEnvironment().getWidth(); x++) {
		Rectangle shape = new Rectangle(8, 8, Color.WHITE);
		shape.setStroke(Color.DIMGREY);
		GridPane.setMargin(shape, new Insets(1));
		Tooltip tooltip = new Tooltip(x + "/" + y);
		Tooltip.install(shape, tooltip);

		CellList.getInstance().getCells()[x][y] = new Cell(CellType.EMPTY, shape, x, y);

		gp_environment.add(shape, x, y);
	    }
	}

	Main.getPrimaryStage().sizeToScene();
	Main.getPrimaryStage().centerOnScreen();
    }

    public void setOptions(GridPane gp_environment, int sizeOfAxis, int numOfPredator, int numOfPrey) {
	setSizeOfAxis(sizeOfAxis, gp_environment);

	generatePredefinedCells(CellType.PREDATOR, numOfPredator);
	generatePredefinedCells(CellType.PREY, numOfPrey);

	CellList.getInstance().determineNeighbours();
    }

    public int getMaxNumOfCells() {
	return width * height;
    }

    public void play(int numOfSteps, Label lbl_numOfPredator, Label lbl_numOfPrey,
	    Series<Integer, Integer> predatorSeries, Series<Integer, Integer> preySeries) throws Exception {
	for (int n = 1; n <= numOfSteps; n++) {
	    this.numOfRounds++;

	    String numOfRounds = "000";

	    if (this.numOfRounds < 10) {
		numOfRounds = "00" + this.numOfRounds;
	    } else if (this.numOfRounds < 100) {
		numOfRounds = "0" + this.numOfRounds;
	    } else {
		numOfRounds = Integer.toString(this.numOfRounds);
	    }

	    Log.getInstance().add("*****************************************");
	    Log.getInstance().add("***** Round " + numOfRounds + " ***********************");
	    Log.getInstance().add("*****************************************");

	    step();
	    updateCells();

	    int maxNumOfCells = Main.getEnvironment().getMaxNumOfCells();
	    int numOfPredator = CellType.PREDATOR.getNumOfCells();
	    int numOfPrey = CellType.PREY.getNumOfCells();
	    int sum = numOfPredator + numOfPrey;

	    History.getInstance().add(numOfPredator, numOfPrey, predatorSeries, preySeries);

	    lbl_numOfPredator.setText(String.valueOf(numOfPredator));
	    lbl_numOfPrey.setText(String.valueOf(numOfPrey));

	    if (sum == maxNumOfCells) {
		break; // Attractor
	    }
	}
    }

    public void step() {
	for (int y = 0; y < Main.getEnvironment().getHeight(); y++) {
	    for (int x = 0; x < Main.getEnvironment().getWidth(); x++) {
		int n = 0;
		boolean actionDone = false;
		Cell cell = CellList.getInstance().getCell(x, y);

		CellType eatableCellType = CellType.EMPTY;

		switch (cell.getType()) {
		case PREDATOR:
		    eatableCellType = CellType.PREY;
		    break;
		case PREY:
		    eatableCellType = CellType.EMPTY;
		    break;
		default:
		    continue;
		}

		Log.getInstance().add(cell.getType().toString() + "[" + x + "|" + y + "]:");

		while (!actionDone) {
		    switch (n) {
		    case 0:
			actionDone = cell.die();
			break;
		    case 1:
			cell.eat(eatableCellType);
			break;
		    case 2:
			actionDone = cell.reproduce();
			break;
		    default:
			actionDone = true;
			cell.goTo(CellType.EMPTY);
			break;
		    }

		    n++;
		}
	    }
	}
    }

    public void updateCells() {
	for (int y = 0; y < Main.getEnvironment().getWidth(); y++) {
	    for (int x = 0; x < Main.getEnvironment().getHeight(); x++) {
		Cell cell = CellList.getInstance().getCell(x, y);
		if (cell.getNewState() != null) {
		    cell.applyNewState();
		}

		if (cell.getType().equals(CellType.PREY) || cell.getType().equals(CellType.PREDATOR)) {
		    cell.growOlder();
		    cell.becomeHungrier();

		    Tooltip tooltip = new Tooltip(
			    x + "/" + y + "; HUNGER = " + cell.getHunger() + "; AGE = " + cell.getAge());
		    Tooltip.install(cell.getShape(), tooltip);
		}
	    }
	}
    }

}
