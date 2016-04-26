package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController1 {
/**	@FXML
	private Label labelResult;

	@FXML
	private TextField uname;

	@FXML
	private PasswordField pword;

	@FXML
	private Label l_timer;

	@FXML
	public Label gresult;

	@FXML
	private Label l_cleft;

	@FXML
	private Pane borderContainer;

	@FXML
	private TextField tf_duration;

	@FXML
	private TextField tf_calories;

	@FXML
	private TextField tf_cal_cost;

	@FXML
	private TextField tf_cal_nougat;

	@FXML
	private RadioButton r_slow;

	@FXML
	private RadioButton r_medium;

	@FXML
	private RadioButton r_fast;

	public static Pane cell[][] = new Pane[11][11];
	public static Player player;
	public static Monster monster;

	public static int calories = 60;
	public static int calories_cost = 2;
	public static int nougat = 6;
	public static int duration = 100;

	public static final String styleMonster = "-fx-background-color : green;-fx-border-color : black";
	public static final String stylePlayer = "-fx-background-color : yellow;-fx-border-color : black";
	public static final String styleClear = "-fx-background-color : white;-fx-border-color : black";
	public static final String stylePoint = "-fx-background-color : blue;-fx-border-color : black";
	public static final String gameLose = "-fx-background-color : red;-fx-border-color : black";
	public static final String UP = "U";
	public static final String DOWN = "D";
	public static final String LEFT = "L";
	public static final String RIGHT = "R";

	GridPane game = new GridPane();
	int location[] = new int[2];

	public void Login(ActionEvent event) throws Exception {
		// if (uname.getText().equals("1") && pword.getText().equals("1")) {
		// Open game window
		labelResult.setText("Login Success!");
		// Hide login window
		Main.primaryStageLogin.hide();

		Stage gameStage = new Stage();
		// Parent root =
		// FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
		// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Pane root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));

		// Create game board, player, monster
		Board game = new Board();
		root.getChildren().addAll(game);

		// Create Player and Monster
		player = new Player(calories, calories_cost, nougat);
		monster = new Monster();

		Scene scene = new Scene(root);
		gameStage.setScene(scene);
		gameStage.show();

		// } else {
		// labelResult.setText("Login Failed!");
		// }
	}

	public void Start(ActionEvent event2) {

		new Thread(new Runnable() {
			private String direction;

			@Override
			public void run() {
				int time = duration;

				while (time >= 0) {
					String t = Integer.toString(time);
					time--;
					String c = Integer.toString(Player.calories);
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							l_timer.setText(t);
							l_cleft.setText(c);
							// gresult.setText("game over");
							// Control Monster
							direction = monster.Get_Direction(Player.PlayerX, Player.PlayerY);
							location = monster.GetNewLocation(direction);
							monster.Cell_Move(location[0], location[1]);
							if (Monster.result == true || Player.result == true) {
								gresult.setText("Game Over");
							}

						}
					});
					delay(1000);
					if (Monster.result == true || Player.result == true) {
						// gresult.setText("xxxxxx");
						return;
					}
				}
			}
		}).start();
	}

	public void moveU(ActionEvent event) throws Exception {
		int x = Player.PlayerX;
		int y = Player.PlayerY;
		if (y > 0)
			y--;
		if (Player.calories - Player.calories_cost >= 0)
			player.Cell_Move(x, y);
		else {
			gresult.setText("No Enough Energy. Game Over!");
			return;
		}

		if (Player.result == true)
			gresult.setText("Game Over");

	}

	public void moveD(Event event) throws Exception {
		int x = Player.PlayerX;
		int y = Player.PlayerY;
		
		//event.getEventType();
		
		if (y < 10)
			y++;
		if (Player.calories - Player.calories_cost >= 0)
			player.Cell_Move(x, y);
		else {
			gresult.setText("No Enough Energy. Game Over!");
			return;
		}
		if (Player.result == true)
			gresult.setText("Game Over");
	}

	public void moveL(ActionEvent event) throws Exception {
		int x = Player.PlayerX;
		int y = Player.PlayerY;
		if (x > 0)
			x--;
		if (Player.calories - Player.calories_cost >= 0)
			player.Cell_Move(x, y);
		else {
			gresult.setText("No Enough Energy. Game Over!");
			return;
		}
		if (Player.result == true)
			gresult.setText("Game Over");
	}

	public void moveR(ActionEvent event) throws Exception {
		int x = Player.PlayerX;
		int y = Player.PlayerY;
		if (x < 10)
			x++;
		if (Player.calories - Player.calories_cost >= 0)
			player.Cell_Move(x, y);
		else {
			gresult.setText("No Enough Energy. Game Over!");
			return;
		}
		if (Player.result == true)
			gresult.setText("Game Over");
	}

	public void delay(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void Cancel(ActionEvent event1) {
		System.out.println(" Closing...");
		System.exit(0);
	}

	public void Save(ActionEvent event1) {
		System.out.println(" Saving...");

		// Duration
		if (tf_duration.getText() != null) {
			String temp = tf_duration.getText();
			duration = Integer.parseInt(temp);
		}

		// Initial Calories
		if (tf_calories.getText() != null) {
			String temp = tf_calories.getText();
			calories = Integer.parseInt(temp);
		}

		// Calories cost
		if (tf_cal_cost.getText() != null) {
			String temp = tf_cal_cost.getText();
			calories_cost = Integer.parseInt(temp);
		}

		// Calories per nougat
		if (tf_cal_nougat.getText() != null) {
			String temp = tf_cal_nougat.getText();
			nougat = Integer.parseInt(temp);
		}

		// Monster Step speed
		if (r_slow.isSelected()) {
		}
		if (r_medium.isSelected()) {
		}
		if (r_fast.isSelected()) {
		}
	}

	public void Setting(ActionEvent event) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Setting.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void Register(ActionEvent event1) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Register.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}*/
}
