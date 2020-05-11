package application.environment;

import application.cell.Cell;
import application.cell.CellList;
import application.cell.NeighbourList;

public class Environment_Moore extends Environment {
	private static final int NumOfNeighbours = 8;

	private static Environment_Moore instance;

	public static Environment_Moore getInstance() {
		if (instance == null) {
			instance = new Environment_Moore();
		}

		return instance;
	}

	private Environment_Moore() {
	};

	public NeighbourList getNeighbours(Cell cell) {
		int n = 0;
		NeighbourList neighbours = new NeighbourList();
		Cell[][] cells = CellList.getInstance().getCells();

		// x-- | y-- x-- | y x-- | y++
		// x | y-- x++ | y x | y++
		// x++ | y-- x++ | y++
		for (int i = 0; i < NumOfNeighbours; i++) {
			try {
				int x = cell.getX();
				int y = cell.getY();

				switch (i) {
				case 0:
					x -= 1;
					y -= 1;
					break;
				case 1:
					y -= 1;
					break;
				case 2:
					x += 1;
					y -= 1;
					break;
				case 3:
					x -= 1;
					break;
				case 4:
					x += 1;
					break;
				case 5:
					x -= 1;
					y += 1;
					break;
				case 6:
					y += 1;
					break;
				case 7:
					x += 1;
					y += 1;
					break;
				}

				if (x < 0 || y < 0) {
					continue;
				} else {
					neighbours.set(n, cells[x][y]);
					n++;
				}
			} catch (IndexOutOfBoundsException e) {
			}
		}

		return neighbours;
	}

	@Override
	public int getNumOfNeighbours() {
		return NumOfNeighbours;
	}
}
