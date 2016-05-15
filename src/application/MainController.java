package application;

import java.io.IOException;
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
	private Label l_timer; // timer
	@FXML
	private Pane borderContainer; // Container for the cells of the game board
	@FXML
	private Label l_score; // Score the player got
	@FXML
	private Label h_score; // Highest score
	@FXML
	private Label newHighScore; // New highest Score
	@FXML
	public Label gresult; // Text of Game result
	@FXML
	private Label l_cleft; // Calories left
	@FXML
	private static Button resetBtn;

	public static boolean tunnelFlag; // Flag to identify Tunnel Exist
	public static int calories = 40; // Calories for player, initial as 40
	public static int calories_cost = 2; // Calories cost for each move
	public static int nougat = 6; // calories get from each nougat
	public static int duration = 100; // Duration of the game
	public static int delay_t = 1000; // time unit for the monster to move
	public static int trapTime = 0; // Time to build trap
	public static int tunnelTime = 0; // time to build tunnel
	public static int tunnelLocation[] = new int[2]; // location of the tunnel
	public static int tunnelEnd[] = new int[2]; // location of the tunnel end
	public static int trapLocation[] = new int[2]; // location of the trap
	public static String gameResult = null; // Text for game result

	public static Pane cell[][] = new Pane[11][11]; // Game Board
	public static Player player = new Player(calories, calories_cost, nougat); // Player
	public static Monster monster = new Monster(); // New Monster set
	public static Monster childMonster = new Monster(); // Baby Monster

	// Pane Style for Monster
	public static final String styleMonster = "-fx-background-color : green;-fx-border-color : black";
	// Pane Style for Player
	public static final String stylePlayer = "-fx-background-color : yellow;-fx-border-color : black";
	// Pane Style for Clear Pane
	public static final String styleClear = "-fx-background-color : white;-fx-border-color : black";
	// Pane Style for Bule point
	public static final String stylePoint = "-fx-background-color : blue;-fx-border-color : black";
	// Pane Style for Trap
	public static final String styleTrap = "-fx-background-color : black;-fx-border-color : black";
	// Pane Style for Tunnel
	public static final String styleTunnel = "-fx-background-color : orange;-fx-border-color : black";

	public static final String UP = "U"; // Constant For "Up"
	public static final String DOWN = "D"; // Constant For "Down"
	public static final String LEFT = "L"; // Constant For "Left"
	public static final String RIGHT = "R"; // Constant For "Right"
	public static final String LEAP = "LEAP";// Constant For "Leap"

	private static int unitTime = duration; // Duration for baby monster
	private int timeToMove = (int) (duration * 0.95); // time baby monster
	private Thread t1; // Thread 1
	private Thread t2; // Thread 2
	private boolean playerCanMove = false; // Flag to identify player moveable
	private boolean littleMonsterExisted = false; // check if baby monster
													// existe
	private boolean shiftPressed = false; // constant for shift button
	private boolean ctrlPressed = false; // constant for control button

	// Create game pane
	GridPane game = new GridPane();
	int location[] = new int[2];

	/**
	 * Set initial style for the game pane
	 */
	public static void setInitialStyle() {
		MainController.cell[Player.PlayerX][Player.PlayerY].setStyle(stylePlayer);
		MainController.cell[Monster.MonsterInitialX][Monster.MonsterInitialY].setStyle(styleMonster);
	}

	public void Reset() throws IOException {
		l_timer.setText("100");
		playerCanMove = false;
		if (littleMonsterExisted) {
			try {
				t2.stop();
			} catch (Exception E) {
			}
			littleMonsterExisted = false;
			MainController.cell[childMonster.MonsterX][childMonster.MonsterY].setStyle(styleClear);
		}
		try {
			t1.stop();
		} catch (Exception E) {
		}
		MainController.cell[monster.MonsterX][monster.MonsterY].setStyle(styleClear);
		monster.MonsterX = 5;
		monster.MonsterY = 5;
		MainController.cell[Monster.MonsterInitialX][Monster.MonsterInitialY].setStyle(styleMonster);

		MainController.cell[Player.PlayerX][Player.PlayerY].setStyle(styleClear);
		Player.PlayerX = 0;
		Player.PlayerY = 0;
		MainController.cell[Player.PlayerX][Player.PlayerY].setStyle(stylePlayer);

	}

	/**
	 * Action handler for action button Start the game, create a thread for
	 * monster
	 * 
	 * @param Event
	 */
	public void Start(ActionEvent event2) {
		// Create Player and Monster
		t1 = new Thread(new Runnable() {
			private String direction;

			@Override
			public void run() {
				int time = duration;
				int score = 0;
				while (time >= 0) {
                    boolean b = false;
					String t = Integer.toString(time); // timer
					String s = Integer.toString(score); // player score

					// Control Trap
					if (trapTime > 0) {
						trapTime--;
						if (trapTime == 0) {
							player.setOriginal(trapLocation[0], trapLocation[1]);
						}
					}

					// Control Tunnel Building Time
					if (tunnelTime > 0) {
						tunnelTime--;
						if (tunnelTime == 0) {

						}
					}
					// Create baby monster
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
							l_timer.setText(t); // timer
							// l_cleft.setText(c); // calories
							l_score.setText(s); // score
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

	/**
	 * Create thread for baby monster
	 * 
	 * @param Event
	 */
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

	/**
	 * Key action handler for player skip
	 * 
	 * @param Event
	 * @throws Exception
	 */
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

	/**
	 * Handler for key release event
	 * 
	 * @param Event
	 * @throws Exception
	 */
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
		boolean result = player.movePlayer(UP, playerCanMove, shiftPressed, ctrlPressed);
		if (result == false) {
			gresult.setText(gameResult); // display game result
			gameOver();
			return;
		} else {
			String c = Integer.toString(Player.calories); // calories
			l_cleft.setText(c); // calories
		}
	}

	// Move down
	public void moveD(ActionEvent event) throws Exception {
		boolean result = player.movePlayer(DOWN, playerCanMove, shiftPressed, ctrlPressed);
		if (result == false) {
			gresult.setText(gameResult); // display game result
			gameOver();
			return;
		} else {
			String c = Integer.toString(Player.calories); // calories
			l_cleft.setText(c); // calories
		}
	}

	// Move left
	public void moveL(ActionEvent event) throws Exception {
		boolean result = player.movePlayer(LEFT, playerCanMove, shiftPressed, ctrlPressed);
		if (result == false) {
			gresult.setText(gameResult); // display game result
			gameOver();
			return;
		} else {
			String c = Integer.toString(Player.calories); // calories
			l_cleft.setText(c); // calories
		}
	}

	// Move down
	public void moveR(ActionEvent event) throws Exception {
		boolean result = player.movePlayer(RIGHT, playerCanMove, shiftPressed, ctrlPressed);
		if (result == false) {
			gresult.setText(gameResult); // display game result
			gameOver();
			return;
		} else {
			String c = Integer.toString(Player.calories); // calories
			l_cleft.setText(c); // calories
		}
	}

	/**
	 * time interval for each move
	 * 
	 * @param time
	 */
	public void delay(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Action handler for trap
	 * 
	 * @param Event
	 */
	public void Trap(ActionEvent event1) {
		// System.out.println("trap");
		if (Player.calories - Player.calories_cost >= 50 && trapTime == 0) {
			player.setTrap(Player.PlayerX, Player.PlayerY);
			trapTime = 5;
			trapLocation[0] = Player.PlayerX;
			trapLocation[1] = Player.PlayerY;
			String c = Integer.toString(Player.calories); // calories
			l_cleft.setText(c); // calories
		}
	}

	/**
	 * Button handler for tunnel
	 */
	public void Tunnel() {
		if (Player.calories - Player.calories_cost >= 50 && tunnelFlag == false) {
			tunnelLocation[0] = Player.PlayerX;
			tunnelLocation[1] = Player.PlayerY;
			tunnelEnd = player.buildTunnel();
			tunnelTime = 3;
			tunnelFlag = true;
			String c = Integer.toString(Player.calories); // calories
			l_cleft.setText(c); // calories
		}
	}

	/**
	 * Pause the game
	 */
	public void Pause() {
		try {
			playerCanMove = false;
			t1.suspend();
			t2.suspend();
		} catch (Exception e) {

		}
	}

	/**
	 * Resume the game
	 */
	public void Resume() {
		try {
			playerCanMove = true;
			t1.resume();
			t2.resume();
		} catch (Exception e) {
		}
	}

	/**
	 * Setting game variables
	 * 
	 * @param Event
	 * @throws Exception
	 */
	public void Setting(ActionEvent event) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Setting.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Check game result
	 */
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

	/**
	 * Save player score to data base
	 */
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

	/**
	 * Game over process
	 */
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
