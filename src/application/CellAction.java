package application;

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
	    updateCells(lbl_numOfPredator, lbl_numOfPrey);

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
		    // 1. Beute vorhanden -> essen (W=1)
		    // 2. Nachwuchs zeugen (W=0-1)
		    // 3. Sterben (W=0-1)
		    // 4. Weitergehen (W=1; zufälliges freies Feld)
		    while (!actionDone) {
			switch (x) {
			case 0:
			    actionDone = cell.goTo(CellType.PREY);
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
		    // 1. Nachwuchs zeugen -> paaren (W=0-1)
		    // 2. Sterben (W=0-1)
		    // 3. Weitergehen (zufälliges freies Feld)
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

    public void updateCells(Label lbl_numOfPredator, Label lbl_numOfPrey) {
	for (int i = 0; i < Main.getEnvironment().getWidth(); i++) {
	    for (int j = 0; j < Main.getEnvironment().getHeight(); j++) {
		Cell cell = CellList.getInstance().getCell(i, j);
		if (cell.getNewType() != null) {
		    cell.setType(cell.getNewType());
		}
	    }
	}

	lbl_numOfPredator.setText(String.valueOf(CellType.PREDATOR.getNumOfCells()));
	lbl_numOfPrey.setText(String.valueOf(CellType.PREY.getNumOfCells()));
    }
}
