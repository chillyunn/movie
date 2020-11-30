package application;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;

public class RootController  {
	@FXML private TextField txtTitle;
	@FXML private PasswordField txtPassword;
	@FXML private ComboBox<String> comboPublic;
	@FXML private Button btnLogin;
	@FXML private Button btnCancel;
	
	
	public void handleBtnLoginAction(ActionEvent e) {
		String title = txtTitle.getText();
		System.out.println("title: " + title);
		
		String password = txtPassword.getText();
		System.out.println("password: " + password);
	
		}
	
	public void handleBtnCancelAction(ActionEvent e) {
		Platform.exit();
	}
}
