package application.environment;

import application.Cell;

public interface EnvironmentInterface {
	public int getNumOfNeighbours();
	public int getWidth();
	public int getHeight();
	public Cell[] getNeighbours(Cell cell);
}
