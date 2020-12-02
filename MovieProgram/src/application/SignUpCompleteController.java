package application;

import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class SignUpCompleteController implements Initializable{
	@FXML private BorderPane SignUpComplete;
	@FXML private Button btnCommit;
	public static Socket SignCompleteSocket;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnCommit.setOnAction(e->handleBtnAddAccountAction(e));
	}
	
	public void handleBtnAddAccountAction(ActionEvent e) {
		try {
			Stage completestage = (Stage) btnCommit.getScene().getWindow();
			completestage.close();
			FXMLLoader login = new FXMLLoader(getClass().getResource("LogIn.fxml"));
			Parent root = (Parent) login.load();
			Stage stage = new Stage();
			stage.setTitle("SignUpComplete");
			stage.setScene(new Scene(root));
			LogInController.LoginSocket = SignCompleteSocket;
			stage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
