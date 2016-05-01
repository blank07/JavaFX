package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
	private Label newHighScore;
	@FXML
	public Label gresult;
	@FXML
	private Label l_cleft;

	public static boolean tunnelFlag;
	public static int calories = 40;
	public static int calories_cost = 2;
	public static int nougat = 6;
	public static int duration = 100;
	public static int delay_t = 1000;
	public static int trapTime = 0;
	public static int tunnelTime = 0;
	public static int tunnelLocation[] = new int[2];
	public static int tunnelEnd[] = new int[2];
	public static int trapLocation[] = new int[2];
	public static String gameResult = null;

	public static Pane cell[][] = new Pane[11][11];
	public static Player player = new Player(calories, calories_cost, nougat);
	public static Monster monster = new Monster();
	public static Monster childMonster = new Monster();

	public static final String styleMonster = "-fx-background-color : green;-fx-border-color : black";
	public static final String stylePlayer = "-fx-background-color : yellow;-fx-border-color : black";
	public static final String styleClear = "-fx-background-color : white;-fx-border-color : black";
	public static final String stylePoint = "-fx-background-color : blue;-fx-border-color : black";
	public static final String styleTrap = "-fx-background-color : black;-fx-border-color : black";
	public static final String styleTunnel = "-fx-background-color : orange;-fx-border-color : black";
	

	public static final String UP = "U";
	public static final String DOWN = "D";
	public static final String LEFT = "L";
	public static final String RIGHT = "R";
	public static final String LEAP = "LEAP";

	private static int unitTime = duration;
	private int timeToMove = (int) (duration * 0.95);
	private Thread t1;
	private Thread t2;
	private Boolean playerCanMove = false;
	private boolean littleMonsterExisted = false;
	private boolean shiftPressed = false;
	private boolean ctrlPressed = false;

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
				int time = duration;
				int score = 0;
				while (time >= 0) {

					String t = Integer.toString(time);
					String s = Integer.toString(score);
					String c = Integer.toString(Player.calories);

					// Control Trap
					if (trapTime > 0) {
						trapTime--;
						if (trapTime == 0) {
							player.setOriginal(trapLocation[0], trapLocation[1]);
						}
					}
					
					//Control Tunnel Building Time
					if(tunnelTime >0){
						tunnelTime--;
						if(tunnelTime == 0){
							
						}
					}

					if (time == (int) (duration * 0.9) || time == (int) (duration * 0.8)) {
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
					delay(delay_t);
				}
			}
		});
		t1.start();
		try {
			h_score.setText(LoginController.LoginUserScore);
		} catch (Exception e) {

		}
		playerCanMove = true;
	}

	public void Start2(ActionEvent event2) {
		t2 = new Thread(new Runnable() {
			private String direction;

			@Override
			public void run() {
				int time = duration;

				while (time >= 0) {
					time--;
					// String c = Integer.toString(Player.calories);
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// l_cleft.setText(c);
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
					delay(delay_t * 2);
				}
			}
		});
		t2.start();
	}

	public void handleKeyPressed(KeyEvent event) throws Exception {
		if (event.getCode() == KeyCode.CONTROL) {
			ctrlPressed = true;
		}
		if (event.getCode() == KeyCode.SHIFT) {
			shiftPressed = true;
		}
		
		if (event.getCode() == KeyCode.ENTER) {
			Start(null);
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

	public void handleKeyReleased(KeyEvent event) throws Exception {
		if (event.getCode() == KeyCode.CONTROL) {
			ctrlPressed = false;
		}
		if (event.getCode() == KeyCode.SHIFT) {
			shiftPressed = false;
		}
	}

	public void buttonUClicked(MouseEvent mouseEvent) {
		if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

			if (mouseEvent.getClickCount() == 2) {
				System.out.println("Double clicked A_button");
			}
			if (mouseEvent.getClickCount() == 1) {
				System.out.println("Single clicked A_button");
			}
			if (mouseEvent.getClickCount() == 3) {
				System.out.println("Triple clicked A_button");
			}
		}
	}

	// Move up
	public void moveU(ActionEvent event) throws Exception {
		boolean result = player.movePlayer(UP, playerCanMove,shiftPressed,ctrlPressed);
		if (result == false) {
			gresult.setText(gameResult);
			gameOver();
			return;
		}
	}

	// Move down
	public void moveD(ActionEvent event) throws Exception {
		boolean result = player.movePlayer(DOWN, playerCanMove,shiftPressed,ctrlPressed);
		if (result == false) {
			gresult.setText(gameResult);
			gameOver();
			return;
		}
	}

	// Move left
	public void moveL(ActionEvent event) throws Exception {
		boolean result = player.movePlayer(LEFT, playerCanMove,shiftPressed,ctrlPressed);
		if (result == false) {
			gresult.setText(gameResult);
			gameOver();
			return;
		}
	}

	// Move down
	public void moveR(ActionEvent event) throws Exception {
		boolean result = player.movePlayer(RIGHT, playerCanMove,shiftPressed,ctrlPressed);
		if (result == false) {
			gresult.setText(gameResult);
			gameOver();
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

	public void Trap(ActionEvent event1) {
		//System.out.println("trap");
		if (Player.calories - Player.calories_cost >= 50 && trapTime == 0) {
			player.setTrap(Player.PlayerX, Player.PlayerY);
			trapTime = 5;
			trapLocation[0] = Player.PlayerX;
			trapLocation[1] = Player.PlayerY;
		}
	}
	
	public void Tunnel(){
		tunnelLocation[0] = Player.PlayerX;
		tunnelLocation[1]= Player.PlayerY;
		tunnelEnd = player.buildTunnel();
		tunnelTime = 3;
		tunnelFlag = true;
	}

	public void Pause() {
		try {
			playerCanMove = false;
			t1.suspend();
			t2.suspend();
		} catch (Exception e) {

		}
	}

	public void Resume() {
		try {
			playerCanMove = true;
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
			gresult.setText("You Caught by Monster. Game Over!");
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
				gresult.setText("You Caught by Monster. Game Over!");
				gameOver();
			}

		}
	}

	public void saveScore() {
		if (Integer.parseInt(l_score.getText()) > Integer.parseInt(h_score.getText())) {

			try {
				String url = "jdbc:mysql://localhost:3306/sc?useSSL=false";
				String usernameDB = "root";
				String passwordDB = "password";
				Connection myConn = DriverManager.getConnection(url, usernameDB, passwordDB);
				Statement myStmt = myConn.createStatement();
				myStmt.executeUpdate("update sc.logindb set score = '" + l_score.getText().toString() + "' where id = "
						+ LoginController.LoginUserId + ";");
				newHighScore.setText("New High Score!!");

			} catch (Exception e) {
			}
		}
	}

	public void gameOver() {
		playerCanMove = false;
		try {
			t1.stop();
			t2.stop();
		} catch (Exception Ex) {

		}
		try {
			saveScore();
		} catch (Exception E) {

		}
	}
}
