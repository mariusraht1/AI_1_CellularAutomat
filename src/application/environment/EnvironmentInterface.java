package application.environment;

import application.cell.Cell;
import application.cell.NeighbourList;

public interface EnvironmentInterface {
	public int getNumOfNeighbours();

	public int getWidth();

	public int getHeight();

	public NeighbourList getNeighbours(Cell cell);
}
