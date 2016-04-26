package application;

public class Player extends Moveable {
	public static int PlayerX;
	public static int PlayerY;
	public static int nougat;
	public static int calories;
	public static int calories_cost;
    public static boolean result;
    
	public Player(int c, int c_c, int n) {
		PlayerX = 0;
		PlayerY = 0;
		nougat = n;
		calories = c;
		calories_cost = c_c;
		//MainController.cell[PlayerX][PlayerY].setStyle(MainController.stylePlayer);
	}

	@Override
	public void Cell_Move(int x, int y) {
		/**
		 *  check if next location is valid
		 */
		boolean result = checkValid(x, y);
		if (result == true) {
			/**
			 *  change current location back
			 */
			if ((PlayerX % 5 == 0) || (PlayerY % 5 == 0 && PlayerX % 5 != 0)) {
				if (PlayerX % 5 == 0 && PlayerY % 5 == 0)
					MainController.cell[PlayerX][PlayerY].setStyle(MainController.stylePoint);
				else
					MainController.cell[PlayerX][PlayerY].setStyle(MainController.styleClear);
			}

			/**
			 *  move player to new location
			 */
			MainController.cell[x][y].setStyle(MainController.stylePlayer);
			PlayerX = x;
			PlayerY = y;
		}
	}


}
