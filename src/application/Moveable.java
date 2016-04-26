package application;

public abstract class Moveable {

	public Moveable() {

	}

	public abstract void Cell_Move(int x, int y);
	
	public boolean checkValid(int x, int y) {
		boolean result = false;
		if ((x % 5 == 0) || (y % 5 == 0 && x % 5 != 0)) {
			result = true;
		}
		return result;
	}
}
