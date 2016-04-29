package application;

public abstract class Moveable {

	public Moveable() {

	}

	public abstract void Cell_Move(int x, int y);

	/**
	 * Check if the cell is within the game panel
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean checkValid(int x, int y) {
		boolean result = false;
		if ((x % 5 == 0) || (y % 5 == 0 && x % 5 != 0)) {
			result = true;
		}
		return result;
	}

	/**
	 * Change the cell back to original style
	 * @param x
	 * @param y
	 */
	public void setOriginal(int x, int y) {
		if ((x % 5 == 0) || (y % 5 == 0 && x % 5 != 0)) {
			if (x % 5 == 0 && y % 5 == 0)
				MainController.cell[x][y].setStyle(MainController.stylePoint);
			else
				MainController.cell[x][y].setStyle(MainController.styleClear);
		}
	}
}
