package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import com.setting.ConnectSetting;
import com.setting.Protocol;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

public class LogInController implements Initializable {
	@FXML
	private TextField txtID;
	@FXML
	private PasswordField txtPWD;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnSignUp;
	@FXML
	private Button btnFindIDPWD;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnSignUp.setOnAction(e -> handleBtnSignUpAction(e));
	}

	public static Socket LoginSocket;

	public void handleBtnLoginAction(ActionEvent e) throws IOException {
		OutputStream os = LoginSocket.getOutputStream();
		InputStream is = LoginSocket.getInputStream();
		Protocol protocol = new Protocol();
		byte[] buf = protocol.getPacket();

		String id = txtID.getText();
		String password = txtPWD.getText();

		// 프로토콜 작업
		protocol = new Protocol(Protocol.PT_LOGIN, 1);

		String data[] = new String[2];
		data[0] = id;
		data[1] = password;
		protocol.setData(data);

		os.write(protocol.getPacket());

		is.read(buf);
		int packetType = buf[0];
		int packetCode = buf[1];
		protocol.setPacket(packetType, packetCode, buf);
		if (packetType == Protocol.PT_EXIT) {
			System.out.println("서버 종료");
			return;
		}

		switch (packetType) {
		case Protocol.PT_LOGIN:
			switch (packetCode) {
			case 2:
				System.out.println("서버가 로그인 결과 전송.");
				String result = protocol.getResult();
				if (result.equals("1")) {
					System.out.println("고객 로그인 성공");
//					Stage loginstage = (Stage) btnSignUp.getScene().getWindow();
//					loginstage.close();
//					FXMLLoader find = new FXMLLoader(getClass().getResource("Guest.fxml"));
//					Parent root = (Parent) find.load();
//					Stage stage = new Stage();
//					stage.setTitle("Guest");
//					stage.setScene(new Scene(root));
//					GuestController.GuestSocket = LoginSocket;
//					GuestController.GuestId = data[0];
//					stage.show();

				} else if (result.equals("2")) {
					System.out.println("관리자 로그인 성공");

//					Stage loginstage = (Stage) btnSignUp.getScene().getWindow();
//					loginstage.close();
//					FXMLLoader find = new FXMLLoader(getClass().getResource("Admin.fxml"));
//					Parent root = (Parent) find.load();
//					Stage stage = new Stage();
//					stage.setTitle("Admin");
//					stage.setScene(new Scene(root));
//					AdminController.AdminSocket = LoginSocket;
//					AdminController.AdminId = data[0];
//					stage.show();

				} else if (result.equals("3")) {
					try {
						FXMLLoader fail = new FXMLLoader(getClass().getResource("LogInFailed.fxml"));
						Parent root = (Parent) fail.load();
						Stage stage = new Stage();
						stage.setTitle("Login Failed(Wrong PassWord)");
						stage.setScene(new Scene(root));
						stage.show();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else if (result.equals("4")) {
					try {
						FXMLLoader fail = new FXMLLoader(getClass().getResource("LogInFailed.fxml"));
						Parent root = (Parent) fail.load();
						Stage stage = new Stage();
						stage.setTitle("Login Failed(Unknown User)");
						stage.setScene(new Scene(root));
						stage.show();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	public void handleBtnFindAction(ActionEvent e) {
		try {
			Stage loginstage = (Stage) btnSignUp.getScene().getWindow();
			loginstage.close();
			FXMLLoader find = new FXMLLoader(getClass().getResource("FindIdPwd.fxml"));
			Parent root = (Parent) find.load();
			Stage stage = new Stage();
			stage.setTitle("FindId/Pwd");
			stage.setScene(new Scene(root));
			FindIdPwdController.FindSocket = LoginSocket;
			stage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void handleBtnSignUpAction(ActionEvent e) {
		try {
			Stage loginstage = (Stage) btnSignUp.getScene().getWindow();
			loginstage.close();
			FXMLLoader signup = new FXMLLoader(getClass().getResource("SignUp.fxml"));
			Parent root = (Parent) signup.load();
			Stage stage = new Stage();
			stage.setTitle("SignUp");
			stage.setScene(new Scene(root));
			SignUpController.SignSocket = LoginSocket;
			stage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void handleBtnCancelAction(ActionEvent e) throws IOException {

		Protocol protocol = new Protocol(Protocol.PT_EXIT, Protocol.PT_EXIT);
		OutputStream os = LoginSocket.getOutputStream();
		os.write(protocol.getPacket());

		Platform.exit();
	}
}