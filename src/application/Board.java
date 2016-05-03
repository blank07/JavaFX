package application;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Create game board
 * 
 * @author Karen
 *
 */
public class Board extends GridPane {
	public Board() {
		// padding 50px
		this.setStyle("-fx-padding: 50");
		// Creat cells for game board
		for (int y = 0; y < 11; y++) {
			for (int x = 0; x < 11; x++) {
				Pane pcell = new PaneCell(x, y);
				MainController.cell[x][y] = pcell;
				GridPane.setConstraints(MainController.cell[x][y], x, y);
				this.getChildren().addAll(MainController.cell[x][y]);
			}
		}

	}

}
