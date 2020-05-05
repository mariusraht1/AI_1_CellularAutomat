package application.environment;

import application.History;
import application.Main;
import application.Utilities;
import application.cell.Cell;
import application.cell.CellList;
import application.cell.CellType;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
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
		    wasGenerated = true;
		} else if (n == 10) {
		    break;
		}

		n++;
	    }
	}
    }

    public void setSizeOfAxis(int sizeOfAxis, GridPane gp_environment) {
	Main.getEnvironment().setWidth(sizeOfAxis);
	Main.getEnvironment().setHeight(sizeOfAxis);
	Main.getEnvironment().generateGrid(gp_environment);
    }

    protected void generateGrid(GridPane gp_environment) {
	gp_environment.getChildren().clear();
	CellList.setInstance(null);

	for (int i = 0; i < Main.getEnvironment().getWidth(); i++) {
	    for (int j = 0; j < Main.getEnvironment().getHeight(); j++) {
		Rectangle shape = new Rectangle(8, 8, Color.WHITE);
		shape.setStroke(Color.DIMGREY);
		GridPane.setMargin(shape, new Insets(1));

		CellList.getInstance().getCells()[i][j] = new Cell(CellType.EMPTY, shape, i, j);

		gp_environment.add(shape, i, j);
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
    
    public void updateCells() {
	for (int i = 0; i < Main.getEnvironment().getWidth(); i++) {
	    for (int j = 0; j < Main.getEnvironment().getHeight(); j++) {
		Cell cell = CellList.getInstance().getCell(i, j);
		if (cell.getNewType() != null) {
		    cell.setType(cell.getNewType());
		    cell.setNewType(null);
		}
	    }
	}
    }
    
    public void play(int numOfSteps, Label lbl_numOfPredator, Label lbl_numOfPrey) throws Exception {
	for (int n = 1; n <= numOfSteps; n++) {
	    step();
	    updateCells();
	    
	    int maxNumOfCells = Main.getEnvironment().getMaxNumOfCells();
	    int numOfPredator = CellType.PREDATOR.getNumOfCells();
	    int numOfPrey = CellType.PREY.getNumOfCells();
	    int sum = numOfPredator + numOfPrey;

	    History.getInstance().add(numOfPredator, numOfPrey);
	    
	    lbl_numOfPredator.setText(String.valueOf(numOfPredator));
	    lbl_numOfPrey.setText(String.valueOf(numOfPrey));

	    if (sum == maxNumOfCells) {
		break; // 1. Attractor
	    } else if (n < numOfSteps) {
		Thread.sleep(1000);
	    }
	}
    }
    
    public void step() {
	for (int i = 0; i < Main.getEnvironment().getWidth(); i++) {
	    for (int j = 0; j < Main.getEnvironment().getHeight(); j++) {
		int x = 0;
		boolean actionDone = false;
		Cell cell = CellList.getInstance().getCell(i, j);

		switch (cell.getType()) {
		case PREDATOR:
		    // 1. Try to eat available prey (W=1)
		    // 2. Try to reproduce yourself (W=0-1)
		    // 3. Try (not) to die (W=0-1)
		    // 4. Go forward (W=1; random empty field)
		    while (!actionDone) {
			switch (x) {
			case 0:
			    cell.eat(CellType.PREY);
			    break;
			case 1:
			    actionDone = cell.reproduce();
			    break;
			case 2:
			    actionDone = cell.die();
			    break;
			default:
			    actionDone = true;
			    cell.goTo(CellType.EMPTY);
			    break;
			}

			x++;
		    }
		    break;
		case PREY:
		    // 1. Try to reproduce yourself (W=0-1)
		    // 2. Try (not) to die (W=0-1)
		    // 3. Go forward (W=1; random empty field)
		    while (!actionDone) {
			switch (x) {
			case 0:
			    actionDone = cell.reproduce();
			    break;
			case 1:
			    actionDone = cell.die();
			    break;
			default:
			    actionDone = true;
			    cell.goTo(CellType.EMPTY);
			    break;
			}

			x++;
		    }
		    break;
		default:
		    break;
		}
	    }
	}
    }
}
