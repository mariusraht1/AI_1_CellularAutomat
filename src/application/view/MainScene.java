package application.view;

import application.Cell;
import application.CellAction;
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
		
		CellList.getInstance().determineNeighbours();
		
		Cell cell = CellList.getInstance().getCell(2, 7);
		cell.setType(CellType.PREY);
		cell = CellList.getInstance().getCell(2, 6);
		cell.setType(CellType.PREY);
		
		cell = CellList.getInstance().getCell(7, 1);
		cell.setType(CellType.PREDATOR);
	}
	
	@FXML
	private void onAction_btnReset()
	{
		initialize();
	}
	
	@FXML
	private void onAction_btnPlus_1()
	{
		CellAction.getInstance().step();
	}
	
	@FXML
	private void onAction_btnPlay_100()
	{
		
	}
}
