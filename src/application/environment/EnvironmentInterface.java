package application.environment;

import application.Cell;
import application.NeighbourList;

public interface EnvironmentInterface {
    public int getNumOfNeighbours();

    public int getWidth();

    public int getHeight();

    public NeighbourList getNeighbours(Cell cell);
}
