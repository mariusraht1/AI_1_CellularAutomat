package application;

import javafx.scene.shape.Shape;

public class Cell {
	private Shape shape;

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	private int x;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	private int y;

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Cell(Shape shape, int x, int y) {
		this.shape = shape;
		this.x = x;
		this.y = y;
	}
}
