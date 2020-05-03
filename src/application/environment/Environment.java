package application.environment;

import application.Cell;
import application.CellList;
import application.CellType;
import application.Main;
import application.Utilities;
import javafx.geometry.Insets;
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
}
