package application.view;

import application.History;
import application.Main;
import application.cell.CellType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private GridPane gp_environment;

    @FXML
    private void initialize() {
	tb_numOfSteps.setText(String.valueOf(Main.DefaultNumOfSteps));
	tb_sizeOfAxis.setText(String.valueOf(Main.DefaultSizeOfAxis));
	tb_numOfPredator.setText(String.valueOf(Main.DefaultNumOfPredator));
	tb_numOfPrey.setText(String.valueOf(Main.DefaultNumOfPrey));

	Main.getEnvironment().setSizeOfAxis(Main.DefaultSizeOfAxis, gp_environment);
	Main.getEnvironment().setOptions(gp_environment, Main.DefaultSizeOfAxis, Main.DefaultNumOfPredator,
		Main.DefaultNumOfPrey);

	int numOfPredator = CellType.PREDATOR.getNumOfCells();
	int numOfPrey = CellType.PREY.getNumOfCells();
	
	History.getInstance().clear();
	History.getInstance().add(numOfPredator, numOfPrey);

	lbl_numOfPredator.setText(String.valueOf(CellType.PREDATOR.getNumOfCells()));
	lbl_numOfPrey.setText(String.valueOf(CellType.PREY.getNumOfCells()));
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
		Main.getEnvironment().play(numOfSteps, lbl_numOfPredator, lbl_numOfPrey);
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
