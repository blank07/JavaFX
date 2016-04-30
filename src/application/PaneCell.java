package application;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class PaneCell extends Pane {

	public PaneCell(int x, int y) {
		String s = "     N";
		Label l = new Label(s);
		
		String ss = " ";
		Label ll = new Label(ss);

		if ((x % 5 == 0) || (y % 5 == 0 && x % 5 != 0)) {
			if (x == 0 && y == 0) {
				this.getChildren().add(ll);
			} else {
				this.getChildren().add(l);
			}

			if (x % 5 == 0 && y % 5 == 0) {
				this.setStyle("-fx-background-color : blue;-fx-border-color : black");
			} else
				this.setStyle("-fx-background-color : white;-fx-border-color : black");
		}
		this.setPrefSize(50, 50);
	}
}
