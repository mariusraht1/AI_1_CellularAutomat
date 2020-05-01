package application;

import javafx.scene.paint.Color;

public enum CellType {
	DUMMY(Color.WHITE),
	PREDATOR(Color.RED),
	PREY(Color.DODGERBLUE),
	GRASS1(Color.KHAKI),
	GRASS2(Color.GREENYELLOW),
	GRASS3(Color.LIMEGREEN);
	
	private Color color;
	
	private CellType(Color color) {
		this.setColor(color);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}