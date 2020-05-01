package application;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MainScene {
	@FXML
	private GridPane gp_environment;

	@FXML
	private void initialize() {
		gp_environment.getChildren().clear();
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				Circle circle = new Circle(10, Color.WHITE);
				circle.setStroke(Color.BLACK);
				GridPane.setMargin(circle, new Insets(5));
				
				CellController.getInstance().getCells()[i][j] = new Cell(circle, i, j);
				
				gp_environment.add(circle, i, j);
			}
		}
		
		
	}
	
	@FXML
	private void onAction_btnReset() {
		initialize();
	}
	
	@FXML
	private void onAction_btnPlus_1() {
		
	}
	
	@FXML
	private void onAction_btnPlay_100() {
		
	}
}
