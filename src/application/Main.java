package application;

import java.io.IOException;

import application.environment.Environment;
import application.environment.Environment_Moore;
import application.environment.Environment_VonNeumann;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Cellular Automat for Predator-Prey Model
 * 
 * @author Marius Raht
 * @version 11.05.2020-001
 */
public class Main extends Application {
    private static ObservableList<Environment> supportedEnvironments = FXCollections
	    .observableArrayList(Environment_Moore.getInstance(), Environment_VonNeumann.getInstance());

    public static ObservableList<Environment> getSupportedEnvironments() {
	return supportedEnvironments;
    }

    private static Environment environment;

    public static Environment getEnvironment() {
	return environment;
    }
    
    public static void setEnvironment(Environment environment) {
	Main.environment = environment;
    }

    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
	return primaryStage;
    }

    public static final int MaxNumOfSteps = 500;
    public static final int DefaultNumOfSteps = 1;
    public static final int MaxSuggestedSizeOfAxis = 50;
    public static final int DefaultSizeOfAxis = 30;
    public static final int DefaultNumOfPrey = 50;
    public static final int DefaultNumOfPredator = 10;

    @Override
    public void start(Stage primaryStage) {
	try {
	    Main.primaryStage = primaryStage;
	    Main.environment = Environment_Moore.getInstance();

	    primaryStage.setTitle("CA: Predator-Prey Model");
	    primaryStage.centerOnScreen();

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
