package application.cell;

import application.Main;
import javafx.scene.control.Label;

public class CellAction {
    private static CellAction instance;

    public static CellAction getInstance() {
	if (instance == null) {
	    instance = new CellAction();
	}

	return instance;
    }

    protected CellAction() {
    }

    public void play(int numOfSteps, Label lbl_numOfPredator, Label lbl_numOfPrey) throws Exception {
	for (int n = 1; n <= numOfSteps; n++) {
	    step();
	    updateCells();

	    int maxNumOfCells = Main.getEnvironment().getMaxNumOfCells();
	    int numOfPredator = CellType.PREDATOR.getNumOfCells();
	    int numOfPrey = CellType.PREY.getNumOfCells();
	    int sum = numOfPredator + numOfPrey;

	    // 1. Attractor
	    if(sum == maxNumOfCells) {
		break;
	    }
	    
	    lbl_numOfPredator.setText(String.valueOf(numOfPredator));
	    lbl_numOfPrey.setText(String.valueOf(numOfPrey));

	    if (n < numOfSteps) {
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
			    cell.goTo(CellType.PREY);
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
}
