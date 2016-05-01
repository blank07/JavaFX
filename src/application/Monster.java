package application;

public class Monster extends Moveable {

	public static int MonsterInitialX = 5;
	public static int MonsterInitialY = 5;
	public int MonsterX;
	public int MonsterY;
	public int visibilityCounter = 0;
	public static boolean invisibleAllowed = false;
	
	public Monster() {
		MonsterX = MonsterInitialX;
		MonsterY = MonsterInitialY;
	//	MainController.cell[MonsterX][MonsterY].setStyle(MainController.styleMonster);
	}

	public String Get_Direction(int pX, int pY) {
		String direction = null;

		// Leap
		if ((pX % 5 == 0 && pX == MonsterX)||(pY % 5 == 0 && pY == MonsterY)){
			direction = MainController.LEAP;
		}
		
		// 1 This will not be executed
		else if (pX % 5 == 0 && pX == MonsterX) {
			if (pY < MonsterY) {
				direction = MainController.UP;
			} else {
				direction = MainController.DOWN;
			}
		}

		else if (pY % 5 == 0 && pY == MonsterY) {
			if (pX > MonsterX) {
				direction = MainController.RIGHT;
			} else {
				direction = MainController.LEFT;
			}
		}
		// L
		else if (MonsterY % 5 == 0 && pX % 5 == 0) {
			if (pX > MonsterX) {
				direction = MainController.RIGHT;
			} else {
				direction = MainController.LEFT;
			}
		}

		else if (MonsterX % 5 == 0 && pY % 5 == 0) {
			if (pY < MonsterY) {
				direction = MainController.UP;
			} else {
				direction = MainController.DOWN;
			}
		}

		// Z
		else if (MonsterY % 5 == 0 && pY % 5 == 0 && (pX - 5) * (MonsterX - 5) < 0) {
			if (pX > MonsterX) {
				direction = MainController.RIGHT;
			} else {
				direction = MainController.LEFT;
			}
		}

		else if (MonsterX % 5 == 0 && pX % 5 == 0 && (pY - 5) * (MonsterY - 5) < 0) {
			if (pY < MonsterY) {
				direction = MainController.UP;
			} else {
				direction = MainController.DOWN;
			}
		}
		// C
		else if (MonsterY % 5 == 0 && pY % 5 == 0 && (pX - 5) * (MonsterX - 5) > 0) {
			if (MonsterX % 5 == 4) {
				direction = MainController.RIGHT;
			} else if (MonsterX % 5 == 1) {
				direction = MainController.LEFT;
			} else if (MonsterX % 5 == 2) {
				if (pX > MonsterX) {
					direction = MainController.RIGHT;
				} else {
					direction = MainController.LEFT;
				}
			} else if (MonsterX % 5 == 3) {
				if (pX < MonsterX) {
					direction = MainController.LEFT;
				} else {
					direction = MainController.RIGHT;
				}
			}
		}

		else if (MonsterX % 5 == 0 && pX % 5 == 0 && (pY - 5) * (MonsterY - 5) > 0) {
			if (MonsterY % 5 == 4) {
				direction = MainController.DOWN;
			} else if (MonsterY % 5 == 1) {
				direction = MainController.UP;
			} else if (MonsterY % 5 == 2) {
				if (pY > MonsterY) {
					direction = MainController.DOWN;
				} else {
					direction = MainController.UP;
				}
			} else if (MonsterY % 5 == 3) {
				if (pY < MonsterY) {
					direction = MainController.UP;
				} else {
					direction = MainController.DOWN;
				}
			}
		}

		return direction;

	}

	/**
	 * private String getSection(int x, int y) { // section1 if ((y == 0 && x <
	 * 5) || (x == 0 && y < 5)) { } return null; }
	 */
	public int[] GetNewLocation(String direction,Monster m) {
		int location[] = new int[2];
		if (direction.equals(MainController.UP)) {
			location[0] = m.MonsterX;
			location[1] = m.MonsterY - 1;
		}

		else if (direction.equals(MainController.DOWN)) {
			location[0] = m.MonsterX;
			location[1] = m.MonsterY + 1;
		}

		else if (direction.equals(MainController.LEFT)) {
			location[0] = m.MonsterX - 1;
			location[1] = m.MonsterY;
		}

		else if (direction.equals(MainController.RIGHT)) {
			location[0] = m.MonsterX + 1;
			location[1] = m.MonsterY;
		}
		else if	(direction.equals(MainController.LEAP)){
			location[0] = Player.PlayerX;
			location[1] = Player.PlayerY;
		}
		return location;
	}

	@Override
	public void Cell_Move(int x, int y) {
		 //check if next location is valid
		boolean result = checkValid(x, y);
		if (result == true) {
			// change current location back
			/**if ((MonsterX % 5 == 0) || (MonsterY % 5 == 0 && MonsterX % 5 != 0)) {
				if (MonsterX % 5 == 0 && MonsterY % 5 == 0)
					MainController.cell[MonsterX][MonsterY].setStyle(MainController.stylePoint);
				else
					MainController.cell[MonsterX][MonsterY].setStyle(MainController.styleClear);
			}*/
			
			setOriginal(MonsterX,MonsterY);
			
			 //move player to new location
			if (invisibleAllowed&&visibilityCounter > 5 ) {
				if (x % 5 == 0 && y % 5 == 0) {
					MainController.cell[x][y].setStyle("-fx-background-color : blue;-fx-border-color : black");
				} else{
					MainController.cell[x][y].setStyle("-fx-background-color : white;-fx-border-color : black");
				}
			} else {
				MainController.cell[x][y].setStyle(MainController.styleMonster);
			}
			visibilityCounter++;
			if (visibilityCounter > 10) {
				visibilityCounter = 0;
			}
			MonsterX = x;
			MonsterY = y;
		}
	}

}
