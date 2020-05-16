package application.environment;

import application.cell.Cell;
import application.cell.CellList;
import application.cell.NeighbourList;

public class Environment_VonNeumann extends Environment {
    private static final int NumOfNeighbours = 4;

    @Override
    public int getNumOfNeighbours() {
	return NumOfNeighbours;
    }

    private static Environment_VonNeumann instance;

    public static Environment_VonNeumann getInstance() {
	if (instance == null) {
	    instance = new Environment_VonNeumann();
	}

	return instance;
    }

    private Environment_VonNeumann() {
    };

    @Override
    public NeighbourList getNeighbours(Cell cell) {
	NeighbourList neighbours = new NeighbourList();
	Cell[][] cells = CellList.getInstance().getCells();

	// x | y++
	// x-- | y x | y-- x++ | y
	for (int i = 0; i < NumOfNeighbours; i++) {
	    try {
		int x = cell.getX();
		int y = cell.getY();

		switch (i) {
		case 0:
		    y += 1;
		    break;
		case 1:
		    x -= 1;
		    break;
		case 2:
		    x += 1;
		    break;
		case 3:
		    y -= 1;
		    break;
		}

		if (x < 0 || y < 0) {
		    continue;
		} else {
		    neighbours.set(i, cells[x][y]);
		}
	    } catch (IndexOutOfBoundsException e) {
	    }
	}

	return neighbours;
    }

    @Override
    public String toString() {
	return "Von Neumann-Umgebung (4)";
    }
}
