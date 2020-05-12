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

	private static MainScene mainScene;
	
	public static void setMainScene(MainScene mainScene) {
		CellTypeConfigScene.mainScene = mainScene;
	}
	
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
		int maxAge = Integer.parseInt(tb_maxAge.getText());
		int maxHunger = Integer.parseInt(tb_maxHunger.getText());
		int minLitterAge = Integer.parseInt(tb_minLitterAge.getText());
		int maxLitterAge = Integer.parseInt(tb_maxLitterAge.getText());
		int maxLitter = Integer.parseInt(tb_maxLitter.getText());

		if (maxAge <= 0) {
			tb_maxAge.setText(Integer.toString(cellType.getMaxAge()));
		} else if (maxHunger <= 0) {
			tb_maxHunger.setText(Integer.toString(cellType.getMaxHunger()));
		} else if (minLitterAge <= 0 || maxLitterAge <= 0 || minLitterAge > maxLitterAge) {
			tb_minLitterAge.setText(Integer.toString(cellType.getMinLitterAge()));
			tb_maxLitterAge.setText(Integer.toString(cellType.getMaxLitterAge()));
		} else if (maxLitter <= 0) {
			tb_maxLitter.setText(Integer.toString(cellType.getMaxLitter()));
		} else {
			cellType.setMaxAge(maxAge);
			cellType.setMaxHunger(maxHunger);
			cellType.setMinLitterAge(minLitterAge);
			cellType.setMaxLitterAge(maxLitterAge);
			cellType.setMaxLitter(maxLitter);

			MainScene.setCanceledCellTypeConfig(false);
			Stage dialog = (Stage) tb_maxAge.getScene().getWindow();
			dialog.close();
			
			mainScene.setOptions();
		}
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
