package application.view;

import application.History;
import application.Log;
import application.Main;
import application.cell.CellType;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class MainScene {
    @FXML
    private TextField tb_numOfSteps;
    @FXML
    private Label lbl_numOfPredator;
    @FXML
    private Label lbl_numOfPrey;
    @FXML
    private TextField tb_numOfPredator;
    @FXML
    private TextField tb_numOfPrey;
    @FXML
    private TextField tb_sizeOfAxis;
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
	Log.getInstance().setOutputControl(lv_console);

	tb_numOfSteps.setText(String.valueOf(Main.DefaultNumOfSteps));
	tb_sizeOfAxis.setText(String.valueOf(Main.DefaultSizeOfAxis));
	tb_numOfPredator.setText(String.valueOf(Main.DefaultNumOfPredator));
	tb_numOfPrey.setText(String.valueOf(Main.DefaultNumOfPrey));
	lc_population.setTitle("Population");

	Main.getEnvironment().setOptions(gp_environment, Main.DefaultSizeOfAxis, Main.DefaultNumOfPredator,
		Main.DefaultNumOfPrey);

	int numOfPredator = CellType.PREDATOR.getNumOfCells();
	int numOfPrey = CellType.PREY.getNumOfCells();

	predatorSeries.setName("Predator");
	preySeries.setName("Prey");

	if (!lc_population.getData().contains(predatorSeries)) {
	    lc_population.getData().add(predatorSeries);
	}

	if (!lc_population.getData().contains(preySeries)) {
	    lc_population.getData().add(preySeries);
	}

	History.getInstance().clear(predatorSeries, preySeries);
	History.getInstance().add(numOfPredator, numOfPrey, predatorSeries, preySeries);
	
	lbl_numOfPredator.setText(String.valueOf(CellType.PREDATOR.getNumOfCells()));
	lbl_numOfPrey.setText(String.valueOf(CellType.PREY.getNumOfCells()));
	lc_population.setTitle("Population (" + Main.getEnvironment().getNumOfRounds() + ")");
    }

    @FXML
    private void onAction_btnReset() {
	initialize();
    }

    @FXML
    private void onAction_btnPlay() {
	try {
	    int numOfSteps = Integer.parseInt(tb_numOfSteps.getText());

	    if (numOfSteps > Main.MaxNumOfSteps || numOfSteps <= 0) {
		tb_numOfSteps.setText(String.valueOf(Main.DefaultNumOfSteps));
	    } else {
		Main.getEnvironment().play(numOfSteps, lbl_numOfPredator, lbl_numOfPrey, lc_population, predatorSeries, preySeries);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @FXML
    private void onAction_btnSetOptions() {
	try {
	    int sizeOfAxis = Integer.parseInt(tb_sizeOfAxis.getText());
	    int maxNumOf = Main.getEnvironment().getMaxNumOfCells();
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
		tb_sizeOfAxis.setText(String.valueOf(Main.DefaultSizeOfAxis));
	    } else {
		initialize();
		Main.getEnvironment().setOptions(gp_environment, sizeOfAxis, numOfPredator, numOfPrey);
		tb_sizeOfAxis.setText(Integer.toString(sizeOfAxis));
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @FXML
    private void onAction_btnExport() {
	History.getInstance().export();
	History.getInstance().showExport();
    }
}
