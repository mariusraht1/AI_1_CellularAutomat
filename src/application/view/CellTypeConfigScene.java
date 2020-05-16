package application.view;

import application.cell.CellType;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CellTypeConfigScene {
	@FXML
	private TextField tf_maxAge;
	@FXML
	private TextField tf_maxHunger;
	@FXML
	private TextField tf_minLitterAge;
	@FXML
	private TextField tf_maxLitterAge;
	@FXML
	private TextField tf_maxLitter;

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
		tf_maxAge.setText(Integer.toString(cellType.getMaxAge()));
		tf_maxHunger.setText(Integer.toString(cellType.getMaxHunger()));
		tf_minLitterAge.setText(Integer.toString(cellType.getMinLitterAge()));
		tf_maxLitterAge.setText(Integer.toString(cellType.getMaxLitterAge()));
		tf_maxLitter.setText(Integer.toString(cellType.getMaxLitter()));
	}

	@FXML
	private void onAction_btnSetConfig() {
		int maxAge = Integer.parseInt(tf_maxAge.getText());
		int maxHunger = Integer.parseInt(tf_maxHunger.getText());
		int minLitterAge = Integer.parseInt(tf_minLitterAge.getText());
		int maxLitterAge = Integer.parseInt(tf_maxLitterAge.getText());
		int maxLitter = Integer.parseInt(tf_maxLitter.getText());

		if (maxAge <= 0) {
			tf_maxAge.setText(Integer.toString(cellType.getMaxAge()));
		} else if (maxHunger <= 0) {
			tf_maxHunger.setText(Integer.toString(cellType.getMaxHunger()));
		} else if (minLitterAge <= 0 || maxLitterAge <= 0 || minLitterAge > maxLitterAge) {
			tf_minLitterAge.setText(Integer.toString(cellType.getMinLitterAge()));
			tf_maxLitterAge.setText(Integer.toString(cellType.getMaxLitterAge()));
		} else if (maxLitter <= 0) {
			tf_maxLitter.setText(Integer.toString(cellType.getMaxLitter()));
		} else {
			cellType.setMaxAge(maxAge);
			cellType.setMaxHunger(maxHunger);
			cellType.setMinLitterAge(minLitterAge);
			cellType.setMaxLitterAge(maxLitterAge);
			cellType.setMaxLitter(maxLitter);

			MainScene.setCanceledCellTypeConfig(false);
			Stage dialog = (Stage) tf_maxAge.getScene().getWindow();
			dialog.close();
			
			mainScene.setOptions();
		}
	}

	@FXML
	private void onAction_btnCancel() {
		MainScene.setCanceledCellTypeConfig(true);

		Stage dialog = (Stage) tf_maxAge.getScene().getWindow();
		dialog.close();
	}

	@FXML
	private void onAction_btnReset() {
		initialize();
	}
}
