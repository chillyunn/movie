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

public class FindIdPwdController implements Initializable {
	public static Socket FindSocket;
	@FXML
	private BorderPane FindIdPwd;
	@FXML
	private Button btnId;
	@FXML
	private Button btnPwd;
	@FXML
	private Button btnCancle;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnId.setOnAction(e -> handleBtnId(e));
		btnPwd.setOnAction(e -> handleBtnPwd(e));
	}

	public void handleBtnId(ActionEvent event) {
		try {
			Stage findstage = (Stage) btnId.getScene().getWindow();
			findstage.close();
			FXMLLoader findid = new FXMLLoader(getClass().getResource("FindId.fxml"));
			Parent root = (Parent) findid.load();
			Stage stage = new Stage();
			stage.setTitle("FindId");
			stage.setScene(new Scene(root));
			FindIdController.FindIdSocket = FindSocket;
			stage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void handleBtnPwd(ActionEvent event) {
		try {
			Stage findstage = (Stage) btnPwd.getScene().getWindow();
			findstage.close();
			FXMLLoader findpwd = new FXMLLoader(getClass().getResource("FindPwd.fxml"));
			Parent root = (Parent) findpwd.load();
			Stage stage = new Stage();
			stage.setTitle("FindPwd");
			stage.setScene(new Scene(root));
			FindPwdController.FindPwdSocket = FindSocket;
			stage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void handleBtnCancle(ActionEvent event) {
		try {
			Stage loginstage = (Stage) btnCancle.getScene().getWindow();
			loginstage.close();
			FXMLLoader findpwd = new FXMLLoader(getClass().getResource("LogIn.fxml"));
			Parent root = (Parent) findpwd.load();
			Stage stage = new Stage();
			stage.setTitle("LogIn");
			stage.setScene(new Scene(root));
			LogInController.LoginSocket = FindSocket;
			stage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
