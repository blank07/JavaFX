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
    
	public Player(int c, int c_c, int n) {
		PlayerX = 0;
		PlayerY = 0;
		nougat = n;
		calories = c+n;
		calories_cost = c_c;		
	}

	@Override
	public void Cell_Move(int x, int y) {
        // check if next location is valid
		boolean result = checkValid(x, y);
		if (result == true) {
			 //change current location back
			if ((PlayerX % 5 == 0) || (PlayerY % 5 == 0 && PlayerX % 5 != 0)) {
				if (PlayerX % 5 == 0 && PlayerY % 5 == 0)
					MainController.cell[PlayerX][PlayerY].setStyle(MainController.stylePoint);
				else
					MainController.cell[PlayerX][PlayerY].setStyle(MainController.styleClear);
			}

			 //move player to new location
			MainController.cell[x][y].setStyle(MainController.stylePlayer);
			PlayerX = x;
			PlayerY = y;
			
			
			
			//check if there is nougats
			//checkNougats(x,y);
			Node nt = MainController.cell[x][y].getChildren().get(0);
			
			if (nt instanceof Label){
				if(((Label) nt).getText().equals("     N")){
					//add energy from nougats
					calories = calories + nougat-calories_cost;
					((Label) nt).setText("");;
				}else{
					//deduct calories
					calories = calories - calories_cost;
				}
			}	
		}
	}

	/**private void checkNougats(int x, int y) {
		Node nt = MainController.cell[x][y].getChildren().get(0);
		
		if (nt instanceof Label){
			if(((Label) nt).getText().equals("     N")){
				calories = calories + nougat;
				((Label) nt).setText("");;
			}
		}	
	}*/
}
