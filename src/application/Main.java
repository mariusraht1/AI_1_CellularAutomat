package application;

import java.io.IOException;

import application.environment.Environment;
import application.environment.Environment_Moore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Zellulärer Automat für Räuber-Beute-Modell
 * 
 * @author Marius Raht
 * @version 03.05.2020-001
 */
public class Main extends Application {
    private static Environment environment;

    public static Environment getEnvironment() {
	return environment;
    }

    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
	return primaryStage;
    }

    public static final int MaxNumOfSteps = 500;
    public static final int DefaultNumOfSteps = 1;
    public static final int MaxSuggestedSizeOfAxis = 50;
    public static final int DefaultSizeOfAxis = 10;
    public static final int DefaultNumOfPrey = 5;
    public static final int DefaultNumOfPredator = 1;

    @Override
    public void start(Stage primaryStage) {	
	Main.primaryStage = primaryStage;
	Main.environment = Environment_Moore.getInstance();

	primaryStage.setTitle("CA: Predator-Prey Model (2)");

	primaryStage.setMaximized(false);
	primaryStage.setResizable(false);
	primaryStage.centerOnScreen();

	try {
	    Scene scene = new Scene(FXMLLoader.load(Main.class.getResource("/application/view/MainScene.fxml")));

	    primaryStage.setScene(scene);
	    primaryStage.show();

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {
	launch(args);
    }
}
