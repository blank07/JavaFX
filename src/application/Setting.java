package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class Setting {
	@FXML
	private TextField tf_duration; // Duration TextField 
	@FXML
	private TextField tf_calories; // Calories TextField 
	@FXML
	private TextField tf_cal_cost; // Calories cost TextField
	@FXML
	private TextField tf_cal_nougat; // Calories nougat TextField 
	@FXML
	private RadioButton r_slow; // Speed of the Monster can move(slow)
	@FXML
	private RadioButton r_medium; // Speed of the Monster can move(medium)
	@FXML
	private RadioButton r_fast; // Speed of the Monster can move(fast)
	@FXML
	private CheckBox Invi; // the invisibility ability of the monster

	/**
	 * Update the value for each variable
	 * 
	 * @param event
	 */
	public void Save(ActionEvent event1) {
		System.out.println(" Saving...");

		// Duration
		if (tf_duration.getText() != null) {
			String temp = tf_duration.getText();
			MainController.duration = Integer.parseInt(temp);
		}

		// Initial Calories
		if (tf_calories.getText() != null) {
			String temp = tf_calories.getText();
			MainController.calories = Integer.parseInt(temp);
		}

		// Calories cost
		if (tf_cal_cost.getText() != null) {
			String temp = tf_cal_cost.getText();
			MainController.calories_cost = Integer.parseInt(temp);
		}

		// Calories per nougat
		if (tf_cal_nougat.getText() != null) {
			String temp = tf_cal_nougat.getText();
			MainController.nougat = Integer.parseInt(temp);
		}

		// Monster Step speed
		if (r_slow.isSelected()) {
			MainController.delay_t = 1000;
		}
		if (r_medium.isSelected()) {
			MainController.delay_t = 600;
		}
		if (r_fast.isSelected()) {
			MainController.delay_t = 300;
		}
		if (Invi.isSelected()) {
			Monster.invisibleAllowed = true;
		}
	}

}
