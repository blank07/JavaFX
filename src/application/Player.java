package application;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class Player extends Moveable {
	public static int PlayerX;
	public static int PlayerY;
	public static int nougat;
	public static int calories;
	public static int calories_cost;
	public static boolean result;

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

			// check if there is nougats
			checkNougats(x, y);
		}
	}

	/**
	 * Check if there is a nougat on the cell
	 * 
	 * @param x
	 * @param y
	 */
	private void checkNougats(int x, int y) {
		Node nt = MainController.cell[x][y].getChildren().get(0);

		if (nt instanceof Label) {
			if (((Label) nt).getText().equals("     N")) {
				calories = calories + nougat;
				((Label) nt).setText("");
			}
		}
	}

	public void setTrap(int x, int y) {
		MainController.cell[x][y].setStyle(MainController.styleTrap);
	}

	public boolean movePlayer(String d, boolean playerCanMove) {
		boolean result = true;
		int x = PlayerX;
		int y = PlayerY;

		switch (d) {
		case MainController.UP:
			if (y > 0)
				y--;
			break;
		case MainController.DOWN:
			if (y < 10)
				y++;
			break;
		case MainController.LEFT:
			if (x > 0)
				x--;
			break;
		case MainController.RIGHT:
			if (x < 10)
				x++;
			break;
		default:

		}
		if (playerCanMove) {
			if (Player.calories - Player.calories_cost >= 0)
				Cell_Move(x, y);
			else {
				MainController.gameResult = "No Enough Energy. Game Over!";
				result = false;
			}
		} else {
			result = false;
		}
		return result;

	}

}
