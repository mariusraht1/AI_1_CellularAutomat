package application.view;

import application.cell.CellType;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CellTypeConfigScene {
	@FXML
	private TextField tb_maxAge;
	@FXML
	private TextField tb_maxHunger;
	@FXML
	private TextField tb_minLitterAge;
	@FXML
	private TextField tb_maxLitterAge;
	@FXML
	private TextField tb_maxLitter;
	
	private static CellType cellType;
	
	public static void setCellType(CellType cellType) {
		CellTypeConfigScene.cellType = cellType;
	}
	
	@FXML
	private void initialize() {
		tb_maxAge.setText(Integer.toString(cellType.getMaxAge()));
		tb_maxHunger.setText(Integer.toString(cellType.getMaxHunger()));
		tb_minLitterAge.setText(Integer.toString(cellType.getMinLitterAge()));
		tb_maxLitterAge.setText(Integer.toString(cellType.getMaxLitterAge()));
		tb_maxLitter.setText(Integer.toString(cellType.getMaxLitter()));
	}
	
	@FXML
	private void onAction_btnSetConfig() {
		MainScene.setCanceledCellTypeConfig(false);
		
		
		
		Stage dialog = (Stage) tb_maxAge.getScene().getWindow();		
		dialog.close();
	}
	
	@FXML
	private void onAction_btnCancel() {
		MainScene.setCanceledCellTypeConfig(true);
		
		Stage dialog = (Stage) tb_maxAge.getScene().getWindow();		
		dialog.close();
	}
	
	@FXML
	private void onAction_btnReset() {
		initialize();
	}
}
