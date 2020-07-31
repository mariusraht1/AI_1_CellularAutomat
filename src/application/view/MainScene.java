package application.view;

import application.History;
import application.Log;
import application.Main;
import application.cell.CellType;
import application.environment.Environment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainScene {
	@FXML
	private TextField tf_numOfSteps;
	@FXML
	private Label lbl_numOfPredator;
	@FXML
	private Label lbl_numOfPrey;
	@FXML
	private TextField tf_numOfPredator;
	@FXML
	private TextField tf_numOfPrey;
	@FXML
	private TextField tf_sizeOfAxis;
	@FXML
	private ComboBox<Environment> cb_environment;
	@FXML
	private CheckBox chk_animate;
	@FXML
	private LineChart<Integer, Integer> lc_population;
	@FXML
	private ListView<String> lv_console;
	@FXML
	private GridPane gp_environment;

	private Series<Integer, Integer> predatorSeries = new Series<Integer, Integer>();
	private Series<Integer, Integer> preySeries = new Series<Integer, Integer>();

	@FXML
	private void initialize() {
		Main.getEnvironment().cancelPlayTask();
		Log.getInstance().setOutputControl(lv_console);

		initEnvironment();
		initChart();
		initHistory();
		initGUI();
	}

	private void initEnvironment() {
		Main.getEnvironment().setOptions(gp_environment, Main.DefaultSizeOfAxis, Main.DefaultNumOfPredator,
				Main.DefaultNumOfPrey);
	}

	private void initGUI() {
		cb_environment.setItems(Main.getSupportedEnvironments());
		cb_environment.getSelectionModel().select(Main.getEnvironment());

		tf_numOfSteps.setText(String.valueOf(Main.DefaultNumOfSteps));
		tf_sizeOfAxis.setText(String.valueOf(Main.DefaultSizeOfAxis));
		tf_numOfPredator.setText(String.valueOf(Main.DefaultNumOfPredator));
		tf_numOfPrey.setText(String.valueOf(Main.DefaultNumOfPrey));

		lbl_numOfPredator.setText(String.valueOf(CellType.PREDATOR.getNumOfCells()));
		lbl_numOfPrey.setText(String.valueOf(CellType.PREY.getNumOfCells()));
		lc_population.setTitle("Population (" + Main.getEnvironment().getNumOfRounds() + ")");
	}

	private void initHistory() {
		int numOfPredator = CellType.PREDATOR.getNumOfCells();
		int numOfPrey = CellType.PREY.getNumOfCells();

		History.getInstance().clear(predatorSeries, preySeries);
		History.getInstance().add(numOfPredator, numOfPrey, predatorSeries, preySeries);
	}

	private void initChart() {
		predatorSeries.setName(CellType.PREDATOR.getName());
		preySeries.setName(CellType.PREY.getName());

		if (!lc_population.getData().contains(predatorSeries)) {
			lc_population.getData().add(predatorSeries);
		}

		if (!lc_population.getData().contains(preySeries)) {
			lc_population.getData().add(preySeries);
		}

		predatorSeries.nodeProperty().get().setStyle("-fx-stroke-width: 1px;");
		preySeries.nodeProperty().get().setStyle("-fx-stroke-width: 1px;");

		lc_population.setStyle(String.format("chart_color_1: %s; chart_color_2: %s;",
				format(CellType.PREDATOR.getColor()), format(CellType.PREY.getColor())));
		lc_population.setCreateSymbols(false);
	}

	private String format(Color c) {
		int r = (int) (255 * c.getRed());
		int g = (int) (255 * c.getGreen());
		int b = (int) (255 * c.getBlue());

		return String.format("#%02x%02x%02x", r, g, b);
	}

	@FXML
	private void onAction_btnReset() {
		initialize();
	}

	@FXML
	private void onAction_btnPlay() {
		try {
			int numOfSteps = Integer.parseInt(tf_numOfSteps.getText());

			if (numOfSteps <= 0) {
				tf_numOfSteps.setText(String.valueOf(Main.DefaultNumOfSteps));
			} else if (numOfSteps > Main.MaxNumOfSteps) {
				tf_numOfSteps.setText(String.valueOf(Main.MaxNumOfSteps));
			} else {
				Main.getEnvironment().runPlay(chk_animate.isSelected(), numOfSteps, lbl_numOfPredator, lbl_numOfPrey,
						lc_population, predatorSeries, preySeries);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onAction_btnSetOptions() {
		setOptions();
	}

	public void setOptions() {
		try {
			int sizeOfAxis = Integer.parseInt(tf_sizeOfAxis.getText());
			int maxNumOf = Main.getEnvironment().getMaxNumOfCells();
			int numOfPredator = Integer.parseInt(tf_numOfPredator.getText());
			int numOfPrey = Integer.parseInt(tf_numOfPrey.getText());
			int sum = numOfPredator + numOfPrey;

			if (sum > maxNumOf || sum <= 0) {
				tf_numOfPredator.setText(String.valueOf(Main.DefaultNumOfPredator));
				tf_numOfPrey.setText(String.valueOf(Main.DefaultNumOfPrey));
			} else if (numOfPredator > maxNumOf) {
				tf_numOfPredator.setText(String.valueOf(Main.DefaultNumOfPredator));
			} else if (numOfPrey > maxNumOf) {
				tf_numOfPrey.setText(String.valueOf(Main.DefaultNumOfPrey));
			} else if (sizeOfAxis > Main.MaxSuggestedSizeOfAxis || sizeOfAxis <= 0) {
				tf_sizeOfAxis.setText(String.valueOf(Main.DefaultSizeOfAxis));
			} else {
				initialize();
				Main.getEnvironment().setOptions(gp_environment, sizeOfAxis, numOfPredator, numOfPrey);
				History.getInstance().clear(predatorSeries, preySeries);
				History.getInstance().add(numOfPredator, numOfPrey, predatorSeries, preySeries);

				// Update after initialize
				tf_sizeOfAxis.setText(Integer.toString(sizeOfAxis));
				tf_numOfPredator.setText(Integer.toString(numOfPredator));
				tf_numOfPrey.setText(Integer.toString(numOfPrey));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onAction_cbSetEnvironment() {
		Environment selectedEnvironment = cb_environment.getSelectionModel().getSelectedItem();

		if (!selectedEnvironment.equals(Main.getEnvironment())) {
			Main.setEnvironment(selectedEnvironment);
			initialize();
		}
	}

	@FXML
	private void onAction_btnExport() {
		History.getInstance().export();
		History.getInstance().showExport();
	}

	private static boolean canceledCellTypeConfig;

	public static boolean isCanceledCellTypeConfig() {
		return canceledCellTypeConfig;
	}

	public static void setCanceledCellTypeConfig(boolean canceledCellTypeConfig) {
		MainScene.canceledCellTypeConfig = canceledCellTypeConfig;
	}

	@FXML
	private void onAction_btnConfigPredator() {
		canceledCellTypeConfig = true;
		openCellTypeConfigScene(CellType.PREDATOR, this);
	}

	@FXML
	private void onAction_btnConfigPrey() {
		canceledCellTypeConfig = true;
		openCellTypeConfigScene(CellType.PREY, this);
	}

	private void openCellTypeConfigScene(CellType cellType, MainScene mainScene) {
		try {
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(Main.getPrimaryStage());
			dialog.setTitle(cellType.getName());

			CellTypeConfigScene.setMainScene(mainScene);
			CellTypeConfigScene.setCellType(cellType);

			Scene scene = new Scene(
					FXMLLoader.load(Main.class.getResource("/application/view/CellTypeConfigScene.fxml")));
			dialog.setScene(scene);
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
