package application.view;

import java.util.Random;

import application.Cell;
import application.CellList;
import application.CellType;
import application.Main;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MainScene {
	@FXML
	private GridPane gp_environment;
	
	@FXML
	private void initialize()
	{
		gp_environment.getChildren().clear();
		
		for (int i = 0; i < Main.getEnvironment().getWidth(); i++) {
			for (int j = 0; j < Main.getEnvironment().getHeight(); j++) {
				Circle circle = new Circle(10, Color.WHITE);
				circle.setStroke(Color.BLACK);
				GridPane.setMargin(circle, new Insets(5));
				
				CellList.getInstance().getCells()[i][j] = new Cell(CellType.DUMMY, circle, i, j);
				
				gp_environment.add(circle, i, j);
			}
		}
		
		Cell cell = CellList.getInstance().getCell(2, 7);
		cell.changeTo(CellType.PREY);
		cell = CellList.getInstance().getCell(2, 6);
		cell.changeTo(CellType.PREY);
		
		cell = CellList.getInstance().getCell(7, 1);
		cell.changeTo(CellType.PREDATOR);
	}
	
	@FXML
	private void onAction_btnReset()
	{
		initialize();
	}
	
	@FXML
	private void onAction_btnPlus_1()
	{
		for (int i = 0; i < Main.getEnvironment().getWidth(); i++) {
			for (int j = 0; j < Main.getEnvironment().getHeight(); j++) {
				Cell cell = CellList.getInstance().getCell(i, j);
				
				if (cell.getType().equals(CellType.PREDATOR)) {
					for (int n = 0; n < cell.getNeighbours().length; n++) {
						
					}
				} else if (cell.getType().equals(CellType.PREY)) {
					
				}
			}
		}
	}
	
	@FXML
	private void onAction_btnPlay_100()
	{
		
	}
}
