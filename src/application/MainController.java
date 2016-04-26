package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController {

	@FXML
	private Label l_timer;
	@FXML
	private Pane borderContainer;
	@FXML
	private Label l_score;
	@FXML
	private Label h_score;
	@FXML
	public Label gresult;
	@FXML
	private Label l_cleft;
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

	public static int calories = 60;
	public static int calories_cost = 2;
	public static int nougat = 6;
	public static int duration = 100;

	public static Pane cell[][] = new Pane[11][11];
	public static Player player = new Player(calories, calories_cost, nougat);
	public static Monster monster = new Monster();
	public static Monster childMonster = new Monster();

	public static final String styleMonster = "-fx-background-color : green;-fx-border-color : black";
	public static final String stylePlayer = "-fx-background-color : yellow;-fx-border-color : black";
	public static final String styleClear = "-fx-background-color : white;-fx-border-color : black";
	public static final String stylePoint = "-fx-background-color : blue;-fx-border-color : black";
	public static final String UP = "U";
	public static final String DOWN = "D";
	public static final String LEFT = "L";
	public static final String RIGHT = "R";
	public static final String LEAP = "LEAP";

	private static int unitTime = 100;
	private int timeToMove = 94;
	private Thread t1;
	private Thread t2;
	private Boolean playerCanMove = false;
	private boolean littleMonsterExisted = false;

	GridPane game = new GridPane();
	int location[] = new int[2];

	public static void setInitialStyle() {
		MainController.cell[Player.PlayerX][Player.PlayerY].setStyle(stylePlayer);
		MainController.cell[Monster.MonsterInitialX][Monster.MonsterInitialY].setStyle(styleMonster);
	}

	public void Start(ActionEvent event2) {
		// Create Player and Monster
		t1 = new Thread(new Runnable() {
			private String direction;

			@Override
			public void run() {
				int time = 100;
				int score = 0;
				while (time >= 0) {

					String t = Integer.toString(time);
					String s = Integer.toString(score);
					String c = Integer.toString(Player.calories);

					if (time == 97 || time == 90) {
						if (!littleMonsterExisted) {
							Start2(null);
							littleMonsterExisted = true;
						}

					}
					time--;
					score++;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							l_timer.setText(t);
							l_cleft.setText(c);
							l_score.setText(s);
							// Control Monster
							direction = monster.Get_Direction(Player.PlayerX, Player.PlayerY);
							location = monster.GetNewLocation(direction, monster);
							monster.Cell_Move(location[0], location[1]);
							checkResult();
						}
					});
					delay(1000);

				}
			}
		});
		t1.start();
		playerCanMove = true;
	}

	public void Start2(ActionEvent event2) {
		t2 = new Thread(new Runnable() {
			private String direction;

			@Override
			public void run() {
				int time = 100;

				while (time >= 0) {
					time--;
					String c = Integer.toString(Player.calories);
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							l_cleft.setText(c);
							unitTime--;
							// Control Monster
							direction = childMonster.Get_Direction(Player.PlayerX, Player.PlayerY);
							location = childMonster.GetNewLocation(direction, childMonster);
							if (unitTime > timeToMove)
								childMonster.Cell_Move(5, 5);
							if (unitTime <= timeToMove)
								childMonster.Cell_Move(location[0], location[1]);
						}
					});
					delay(2000);
				}
			}
		});
		t2.start();
	}

	public void handleKeyPressed(KeyEvent event) throws Exception {
	}

	public void handleKeyReleased(KeyEvent event) throws Exception {

		if (event.getCode() == KeyCode.ENTER) {
			Start(null);
			// Start2(null);
		}
		if (event.getCode() == KeyCode.UP) {
			moveU(null);
		}
		if (event.getCode() == KeyCode.DOWN) {
			moveD(null);
		}
		if (event.getCode() == KeyCode.RIGHT) {
			moveR(null);
		}
		if (event.getCode() == KeyCode.LEFT) {
			moveL(null);
		}
		checkResult();
	}

	public void moveU(ActionEvent event) throws Exception {
		int x = Player.PlayerX;
		int y = Player.PlayerY;
		if (y > 0)
			y--;
		if(playerCanMove){
			if (Player.calories - Player.calories_cost >= 0)
				player.Cell_Move(x, y);
			else {
				gresult.setText("No Enough Energy. Game Over!");
				return;
			}
		}else{
			return;
		}
	}

	public void moveD(ActionEvent event) throws Exception {
		int x = Player.PlayerX;
		int y = Player.PlayerY;
		if (y < 10)
			y++;
		if(playerCanMove){
			if (Player.calories - Player.calories_cost >= 0)
				player.Cell_Move(x, y);
			else {
				gresult.setText("No Enough Energy. Game Over!");
				return;
			}
		}else{
			return;
		}
	}

	public void moveL(ActionEvent event) throws Exception {
		int x = Player.PlayerX;
		int y = Player.PlayerY;
		if (x > 0)
			x--;
		if(playerCanMove){
			if (Player.calories - Player.calories_cost >= 0)
				player.Cell_Move(x, y);
			else {
				gresult.setText("No Enough Energy. Game Over!");
				return;
			}
		}else{
			return;
		}
	}

	public void moveR(ActionEvent event) throws Exception {
		int x = Player.PlayerX;
		int y = Player.PlayerY;
		if (x < 10)
			x++;
		if(playerCanMove){
			if (Player.calories - Player.calories_cost >= 0)
				player.Cell_Move(x, y);
			else {
				gresult.setText("No Enough Energy. Game Over!");
				return;
			}
		}else{
			return;
		}
	}

	public void delay(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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

	public void Pause() {
		try {
			t1.suspend();
			t2.suspend();
		} catch (Exception e) {

		}
	}

	public void Resume() {
		try {
			t1.resume();
			t2.resume();
		} catch (Exception e) {

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

	public void checkResult() {
		if (Player.PlayerX == monster.MonsterX && Player.PlayerY == monster.MonsterY) {
			gameOver();
		}
		if (Player.PlayerX == childMonster.MonsterX && Player.PlayerY == childMonster.MonsterY) {
			if (unitTime > timeToMove) {
				try {
					t2.stop();
					littleMonsterExisted = false;
				} catch (Exception e) {
				}
			} else {
				gameOver();
			}

		}
	}
	
	public void gameOver(){
		playerCanMove = false;
		gresult.setText("You Caught by Monster. Game Over!");
		try{
			t1.stop();
			t2.stop();
		}catch(Exception E){
			
		}
	}
}
