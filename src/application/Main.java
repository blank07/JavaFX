package application;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	static Stage primaryStageLogin = new Stage();
	
	private final static Logger kLogger = Logger.getLogger(Main.class.getName());
	/**
	 * open login board
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStageLogin.setScene(scene);
			primaryStageLogin.show();
		} catch (Exception e) {
			e.printStackTrace();
			kLogger.setLevel(Level.SEVERE);
			kLogger.severe("Cannot open Login");
		}
	}

	public static void main(String[] args) {
		kLogger.setLevel(Level.INFO);
		kLogger.info("Log information : Starting Game...Display Login");
		launch(args);
	}
}
