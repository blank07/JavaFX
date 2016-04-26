package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


public class MainController {

	
	@FXML
	private Label l_timer;
	@FXML
	private Pane borderContainer;
	@FXML
	private Label l_score;

	public static Pane cell[][] = new Pane[11][11];
	public static Player player= new Player();
	public static Monster monster = new Monster();
	public static Monster childMonster= new Monster();

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
	private int timeToMove=94;
	private Thread t2;
	private boolean littleMonsterExisted=false;
	
	
	GridPane game = new GridPane();
	int location[] = new int[2];
	
	public static void setInitialStyle(){
		MainController.cell[Player.PlayerX][Player.PlayerY].setStyle(stylePlayer);
		MainController.cell[Monster.MonsterInitialX][Monster.MonsterInitialY].setStyle(styleMonster);
	}
  
	public void Start(ActionEvent event2) {
		// Create Player and Monster
		
		new Thread(new Runnable() {
			private String direction;

			@Override
			public void run() {
				int time = 100;
				int score = 0;
				while (time >= 0) {
					
 					String t = Integer.toString(time);
 					String s = Integer.toString(score);
					if(time==97||time==90){
						if(!littleMonsterExisted){
							Start2(null);
							littleMonsterExisted =true;
						}
						
					}
					time--;
					score++;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							l_timer.setText(t);
							l_score.setText("Score: "+s);
							// Control Monster
							direction = monster.Get_Direction(Player.PlayerX, Player.PlayerY);
							location = monster.GetNewLocation(direction,monster);
							monster.Cell_Move(location[0], location[1]);
							checkResult();
						}
					});
					delay(1000);
					 
				}
			}
		}).start();
	}
	
	public void Start2(ActionEvent event2) {
	t2 = new Thread(new Runnable() {
			private String direction;

			@Override
			public void run() {
				int time = 100;

				while (time >= 0) {
				 	time--;
				 	
					Platform.runLater(new Runnable() {
			
						@Override
						public void run() {
							
							unitTime--;
							// Control Monster
							direction = childMonster.Get_Direction(Player.PlayerX, Player.PlayerY);
							location = childMonster.GetNewLocation(direction,childMonster);
						 	if(unitTime>timeToMove)
						 	childMonster.Cell_Move(5, 5);
							if(unitTime<=timeToMove)
							childMonster.Cell_Move(location[0], location[1]);
						}
					});
					delay(2000);
				}
			}
		});
		t2.start();
	}
	
	public void handleKeyPressed(KeyEvent event) throws Exception {}
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
		player.Cell_Move(x, y);
	}

	public void moveD(ActionEvent event) throws Exception {
		int x = Player.PlayerX;
		int y = Player.PlayerY;
		if (y < 10)
			y++;
		player.Cell_Move(x, y);
	}

	public void moveL(ActionEvent event) throws Exception {
		int x = Player.PlayerX;
		int y = Player.PlayerY;
		if (x > 0)
			x--;
		player.Cell_Move(x, y);
	}

	public void moveR(ActionEvent event) throws Exception {
		int x = Player.PlayerX;
		int y = Player.PlayerY;
		if (x < 10)
			x++;
		player.Cell_Move(x, y);
	}

	public void delay(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	public void Setting(ActionEvent event) throws Exception {

	}


	
	public void checkResult(){
		if(Player.PlayerX==monster.MonsterX && Player.PlayerY==monster.MonsterY){
			System.out.println("lose");
		}
		if(Player.PlayerX==childMonster.MonsterX && Player.PlayerY==childMonster.MonsterY){
			if(unitTime>timeToMove){
				try{
					t2.stop();
					System.out.println("little monster died");
					littleMonsterExisted = false;
				}catch(Exception e){
				}
			}else{
				System.out.println("little monster killed u");
			}
			
		}
	}
}
