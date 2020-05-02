package application.view;

import application.CellAction;
import application.CellList;
import application.CellType;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class MainScene {
	@FXML
	private GridPane gp_environment;
	@FXML
	private TextField tb_numOfPredator;
	@FXML
	private TextField tb_numOfPrey;
	@FXML
	private TextField tb_sizeOfAxis;
	
	@FXML
	private void initialize()
	{
		tb_sizeOfAxis.setText(String.valueOf(Main.DefaultSizeOfAxis));
		tb_numOfPredator.setText(String.valueOf(Main.DefaultNumOfPredator));
		tb_numOfPrey.setText(String.valueOf(Main.DefaultNumOfPrey));
		
		Main.getEnvironment().setSizeOfAxis(Main.DefaultSizeOfAxis, gp_environment);
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
	
	@FXML
	private void onAction_btnSetOptions()
	{
		int sizeOfAxis = Integer.parseInt(tb_sizeOfAxis.getText());
		int maxNumOf = Main.getEnvironment().getWidth() * Main.getEnvironment().getHeight();
		int numOfPredator = Integer.parseInt(tb_numOfPredator.getText());
		int numOfPrey = Integer.parseInt(tb_numOfPrey.getText());
		int sum = numOfPredator + numOfPrey;
		
		if (sum > maxNumOf || sum <= 0) {
			tb_numOfPredator.setText(String.valueOf(Main.DefaultNumOfPredator));
			tb_numOfPrey.setText(String.valueOf(Main.DefaultNumOfPrey));
		} else if (numOfPredator > maxNumOf) {
			tb_numOfPredator.setText(String.valueOf(Main.DefaultNumOfPredator));
		} else if (numOfPrey > maxNumOf) {
			tb_numOfPrey.setText(String.valueOf(Main.DefaultNumOfPrey));
		} else if (sizeOfAxis > Main.MaxSuggestedSizeOfAxis || sizeOfAxis <= 0) {
			tb_sizeOfAxis.setText(String.valueOf(Main.DefaultNumOfPrey));
		} else {
			Main.getEnvironment().setSizeOfAxis(sizeOfAxis, gp_environment);
			
			Main.getEnvironment().generateCells(CellType.PREDATOR, numOfPredator);
			Main.getEnvironment().generateCells(CellType.PREY, numOfPrey);
			
			CellList.getInstance().determineNeighbours();
		}
	}
}
