package application;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class Player extends Moveable {
	public static int PlayerX; // player coordinate x
	public static int PlayerY; // player coordinate y
	public static int nougat; // nougat
	public static int calories; // Calories
	public static int calories_cost; // Calories for each move
	public static boolean result; // Game result
	private final static Logger kLogger = Logger.getLogger(Player.class.getName());

	// Constructor
	public Player(int c, int c_c, int n) {
		PlayerX = 0; // Coordinate X
		PlayerY = 0; // Coordinate Y
		nougat = n;
		calories = c + n;
		calories_cost = c_c;
	}

	@Override
	public void Cell_Move(int x, int y) {
		// check if next location is valid
		boolean result = checkValid(x, y);

		if (PlayerX == MainController.tunnelLocation[0] && PlayerY == MainController.tunnelLocation[1]
				&& MainController.tunnelFlag == true) {
			if (calories - 20 >= 0) {
				// move player to new location
				MainController.cell[MainController.tunnelEnd[0]][MainController.tunnelEnd[1]]
						.setStyle(MainController.stylePlayer);
				setOriginal(PlayerX, PlayerY);
				PlayerX = MainController.tunnelEnd[0];
				PlayerY = MainController.tunnelEnd[1];
				calories = calories - 20;
			} else {
				MainController.gameResult = "No Enough Energy.";
				return;
			}

		} else {
			if (result == true) {
				// change current location back
				String style = MainController.cell[PlayerX][PlayerY].getStyle();
				if (style != MainController.styleTrap) {
					setOriginal(PlayerX, PlayerY);
				}
				// move player to new location
				MainController.cell[x][y].setStyle(MainController.stylePlayer);
				PlayerX = x;
				PlayerY = y;
				calories = calories - calories_cost;
			}
			// check if there is nougats
			checkNougats(PlayerX, PlayerY);

			MainController.calories = calories;
		}
	}

	/**
	 * Check if there is a nougat on the cell
	 * 
	 * @param coordinate
	 *            x
	 * @param coordinate
	 *            y
	 */
	private void checkNougats(int x, int y) {
		Node nt = MainController.cell[x][y].getChildren().get(0);
		// check if the player get new calories
		if (nt instanceof Label) {
			if (((Label) nt).getText().equals("     N")) {
				calories = calories + nougat;
				((Label) nt).setText("");
			}
		}
	}

	/**
	 * Set Trap
	 * 
	 * @param coordinate
	 *            x
	 * @param coordinate
	 *            y
	 */
	public void setTrap(int x, int y) {
		MainController.cell[x][y].setStyle(MainController.styleTrap);
		calories = calories - 50;
		MainController.calories = calories;
		kLogger.setLevel(Level.INFO);
		kLogger.info("Log information : Trap built...");
	}

	/**
	 * Move player to new place
	 */
	public boolean movePlayer(String d, boolean playerCanMove, boolean shiftPressed, boolean ctrlPressed) {
			int s = 1; // the space the player can move
			if (shiftPressed) {
				s = 2;
			}
			if (ctrlPressed) {
				s = 3;
			}
			boolean result = true;

			int x = PlayerX;
			int y = PlayerY;

			switch (d) {
			case MainController.UP:
				if (y >= s)
					y -= s;
				break;
			case MainController.DOWN:
				if (y <= 10 - s)
					y += s;
				break;
			case MainController.LEFT:
				if (x >= s)
					x -= s;
				break;
			case MainController.RIGHT:
				if (x <= 10 - s)
					x += s;
				break;
			default:
				;
			}
			if (playerCanMove) {
				if (Player.calories - Player.calories_cost >= 0) {
					if (PlayerX == MainController.tunnelLocation[0] && PlayerY == MainController.tunnelLocation[1]
							&& MainController.tunnelFlag == true) {
						if (Player.calories - 20 >= 0) {
							Cell_Move(x, y);
						} else {
							MainController.gameResult = "You Don't have Enough Energy.";
							result = false;
						}
					} else {
						Cell_Move(x, y);
					}

				} else {
					MainController.gameResult = "You Don't have Enough Energy.";
					result = false;
				}
			} 
		return result;
	}

	/**
	 * build a tunnel
	 * 
	 * @return
	 */
	public int[] buildTunnel() {
		int endLocation[] = new int[2];
		endLocation = setTunnel(MainController.styleTunnel, PlayerX, PlayerY);
		calories = calories - 50;
		MainController.calories = calories;
		kLogger.setLevel(Level.INFO);
		kLogger.info("Log information : Tunnel built...");
		return endLocation;
	}

	/**
	 * Set tunnel style
	 * 
	 * @param style
	 *            for tunnel
	 * @param coordinate
	 *            x
	 * @param coordinate
	 *            y
	 * @return location of the tunnel end
	 */
	private int[] setTunnel(String style, int x, int y) {
		int endLocation[] = new int[2];
		if (x % 5 == 0 && !(y % 5 == 0)) {
			if (x == 10) {
				for (int i = 9; i > 5; i--) {
					MainController.cell[i][y].setStyle(style);
				}
				endLocation[0] = 5;
				endLocation[1] = y;
			} else {
				for (int j = x + 1; j < x + 5; j++) {
					MainController.cell[j][y].setStyle(style);
				}
				endLocation[0] = x + 5;
				endLocation[1] = y;
			}
		}
		if (y % 5 == 0 && !(x % 5 == 0)) {
			if (y == 10) {
				for (int i = 9; i > 5; i--) {
					MainController.cell[PlayerX][i].setStyle(style);
				}
				endLocation[0] = x;
				endLocation[1] = 5;
			} else {
				for (int j = y + 1; j < y + 5; j++) {
					MainController.cell[x][j].setStyle(style);
				}
				endLocation[0] = x;
				endLocation[1] = y + 5;
			}
		}
		return endLocation;
	}
}
