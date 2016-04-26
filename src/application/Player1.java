package application;

public class Player1 extends Moveable {

	@Override
	public void Cell_Move(int x, int y) {
		// TODO Auto-generated method stub
		
	}
/**	public static int PlayerX;
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

		MainController.cell[PlayerX][PlayerY].setStyle(MainController.stylePlayer);
	}

	@Override
	public void Cell_Move(int x, int y) {

		// check if next location is valid
		boolean valid = checkValid(x, y);
		if (valid == true) {
			// change current location back
			if ((PlayerX % 5 == 0) || (PlayerY % 5 == 0 && PlayerX % 5 != 0)) {
				if (PlayerX % 5 == 0 && PlayerY % 5 == 0)
					MainController.cell[PlayerX][PlayerY].setStyle(MainController.stylePoint);
				else
					MainController.cell[PlayerX][PlayerY].setStyle(MainController.styleClear);
			}

			// move player to new location
			MainController.cell[x][y].setStyle(MainController.stylePlayer);
			PlayerX = x;
			PlayerY = y;

			Player.calories = Player.calories - Player.calories_cost;

			// Check if player and monster at the same spot
		    result = checkResult();
			if (result == true) {
				System.out.println("result ......");
				MainController.cell[x][y].setStyle(MainController.gameLose);
				return;
			}
		}
	}

	@Override
	public boolean checkResult() {
		boolean result = false;
	
		if (Monster.MonsterX == PlayerX && Monster.MonsterY == PlayerY)
			result = true;
		System.out.println("result ..xxxxxxxx...."+result);
		return result;
	}*/
}
