package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	private static Environment environment;
	public static Environment getEnvironment() {
		return environment;
	}
	
	private static Stage primaryStage;
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	
	@Override
	public void start(Stage primaryStage) {
		Main.primaryStage = primaryStage;
		Main.environment = Environment_Moore.getInstance();
		
		primaryStage.setTitle("ZA: Räuber-Beute (Fuchs/Gans)");
		
		primaryStage.setMaximized(false);
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
		
		try {
			Scene scene = new Scene(FXMLLoader.load(Main.class.getResource("MainScene.fxml")));
			
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
