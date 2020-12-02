package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import com.setting.Protocol;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class AddAccountController implements Initializable {
	@FXML
	private BorderPane AddAccount;
	@FXML
	private ComboBox comboBank;
	@FXML
	private TextField txtSerial;
	@FXML
	private TextField txtBankPwd;
	@FXML
	private Button btnCommit;
	@FXML
	private Button btnPass;
	@FXML
	private Text txtError;
	public static Socket AddAccSocket;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnCommit.setOnAction(e -> {
			try {
				handleBtnAddAccountAction(e);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		comboBank.setItems(bankList);
	}

	private ObservableList<String> bankList = FXCollections.observableArrayList("국민", "신한", "외한", "우리", "시티", "경남",
			"광주", "대구", "부산", "전북", "제주", "농협", "수협", "기업");

	public void handleBtnAddAccountAction(ActionEvent e) throws IOException {
		OutputStream os = AddAccSocket.getOutputStream();
		InputStream is = AddAccSocket.getInputStream();
		Protocol protocol = new Protocol();
		byte[] buf = protocol.getPacket();

		String[] data = new String[3];
		data[0] = comboBank.getValue().toString();
		data[1] = txtSerial.getText();
		data[2] = txtBankPwd.getText();
		
		for (int i = 0; i < data.length; i++) {
			if (data[i].contains(";")) {
				txtError.setText("';'은 금지된 문자 입니다.");
				return;
			}
		}
		// 프로토콜작업
		protocol = new Protocol(Protocol.PT_SIGN_UP, 3);
		protocol.setData(data);
		System.out.println("계좌 정보 전송");
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
		case Protocol.PT_SIGN_UP:
			switch (packetCode) {
			case 4:
				System.out.println("서버가 계좌등록 결과 전송.");
				String result = protocol.getResult();
				if (result.equals("0")) {
					System.out.println("가입 성공");
					try {
						Stage accountstage = (Stage) btnCommit.getScene().getWindow();
						accountstage.close();
						FXMLLoader login = new FXMLLoader(getClass().getResource("SignUpComplete.fxml"));
						Parent root = (Parent) login.load();
						Stage stage = new Stage();
						stage.setTitle("LogIn");
						stage.setScene(new Scene(root));
						SignUpCompleteController.SignCompleteSocket = AddAccSocket;
						stage.show();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else if (result.equals("1")) {
					System.out.println("가입 실패");
					txtError.setText("다시 시도해 주십시요.");
				} else {
					System.out.println("알 수 없는 오류");
					txtError.setText("알 수 없는 오류.");
				}
			}
		}
	}

	public void handleBtnPass(ActionEvent event) {
		try {
			Stage loginstage = (Stage) btnPass.getScene().getWindow();
			loginstage.close();
			FXMLLoader login = new FXMLLoader(getClass().getResource("LogIn.fxml"));
			Parent root = (Parent) login.load();
			Stage stage = new Stage();
			stage.setTitle("LogIn");
			stage.setScene(new Scene(root));
			LogInController.LoginSocket = AddAccSocket;
			stage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
