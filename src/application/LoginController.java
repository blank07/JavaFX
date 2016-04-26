package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class LoginController {
	
	@FXML
	private Label lb;
	@FXML
	private TextField uname;
	@FXML
	private PasswordField pword;

	public void LoginWithDB(ActionEvent e) throws IOException, SQLException {
		
		try{
			String url = "jdbc:mysql://localhost:3306/sc?useSSL=false";
			String usernameDB = "root";
			String passwordDB = "password";
			
			String usernameL = uname.getText();
			String passwordL = pword.getText();
			String result="";
			
			Connection myConn = DriverManager.getConnection(url, usernameDB, passwordDB);
			Statement myStmt = myConn.createStatement();

			ResultSet myRs = myStmt.executeQuery("select * from sc.LoginDB");
			
			while(myRs.next()){
				if(usernameL.toString().equals(myRs.getString("username"))){
					result= "Wrong Password";
					if(passwordL.toString().equals(myRs.getString("password"))){
						result = "Succeed";
						
						Main.primaryStageLogin.hide();
						Stage gameStage = new Stage();
						Pane root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
						// Create game board, player, monster
						Board game = new Board();
						root.getChildren().addAll(game);
						Scene scene = new Scene(root);
						gameStage.setScene(scene);
						gameStage.show();
						MainController.setInitialStyle();
					
					}
					break;
				}else{
					result = "User Not Exist";
				}
			}
			lb.setText(result);
			if(result.equals("Succeed")){
				lb.setTextFill(Color.GREEN);
			}else{
				lb.setTextFill(Color.RED);
			}
			
		}catch(Exception ex){
			lb.setText("Not Connecting to DataBase");
			lb.setTextFill(Color.RED);
		}
		
		
}
	
	public void Login(ActionEvent event) throws Exception {

		Main.primaryStageLogin.hide();

		Stage gameStage = new Stage();
		Pane root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));

		// Create game board, player, monster
		Board game = new Board();
		root.getChildren().addAll(game);

		Scene scene = new Scene(root);
		gameStage.setScene(scene);
		gameStage.show();
		
		MainController.setInitialStyle();
	}

	public void Cancel(ActionEvent event1) {
		System.out.println(" Closing...");
		System.exit(0);
	}
	
	public void Register(ActionEvent event1) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Register.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
}
