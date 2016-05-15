package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class RegisterController {

	@FXML
	private Label lbl; // label to show the result 
	@FXML
	private TextField userN; // user name TextField
	@FXML
	private TextField passW; // password TextField
	@FXML
	private TextField nam; // name TextField
	@FXML
	private TextField addr; //address TextField

	/**
	 * Register a new user
	 * 
	 * @param e
	 * @throws IOException
	 */
	public void signup(ActionEvent e) throws IOException {
		try {
			String url = "jdbc:mysql://localhost:3306/sc?useSSL=false";
			String usernameDB = "root";
			String passwordDB = "password";

			String usernameR = userN.getText();
			String passwordR = passW.getText();
			String nameR = nam.getText();
			String addressR = addr.getText();
			String result = "";
			boolean userExists = false;
			int idMax = 0;

			// Create a connection
			Connection myConn = DriverManager.getConnection(url, usernameDB, passwordDB);
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from sc.LoginDB");

			// Check input data
			while (myRs.next()) {
				if ("".equals(usernameR.toString())) {
					result = "Username cannot be empty";
					userExists = true;
					break;
				}
				if ("".equals(passwordR.toString())) {
					result = "Password cannot be empty";
					userExists = true;
					break;
				}
				if ("".equals(nameR.toString())) {
					result = "Name cannot be empty";
					userExists = true;
					break;
				}
				if ("".equals(addressR.toString())) {
					result = "Address cannot be empty";
					userExists = true;
					break;
				}
				if (myRs.getString("username").equals(usernameR.toString())) {
					result = "Username existed";
					userExists = true;
					break;
				}
			}
			// Insert new record to database
			if (!userExists) {
				ResultSet maxId = myStmt.executeQuery("SELECT max(id) FROM sc.LoginDB");
				if (maxId.next()) {
					idMax = maxId.getInt("max(id)");
				}
				idMax++;
				String sql = "insert into sc.LoginDB " + " (id,username,password,name,address,score)" + " values ("
						+ idMax + ",'" + usernameR.toString() + "','" + passwordR.toString() + "','" + nameR.toString()
						+ "','" + addressR.toString() + "','" + "0" + "')";
				myStmt.executeUpdate(sql);
				result = "Succeed";

			}
			//output successful text
			if (result.equals("Succeed")) {
				lbl.setTextFill(Color.GREEN);
			} else {
				lbl.setTextFill(Color.RED);
			}
			lbl.setText(result);

		} catch (SQLException ex) {
			lbl.setText("Not Connecting to DataBase");
			lbl.setTextFill(Color.RED);
		}

	}

}
