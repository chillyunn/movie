package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import com.setting.ConnectSetting;
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

public class SignUpController implements Initializable {
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtPwd;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtSecond;
	@FXML
	private TextField txtLast;
	@FXML
	private Text txtError;
	@FXML
	private BorderPane SignUp;
	@FXML
	private Button btnCommit;
	@FXML
	private Button btnCancle;
	@FXML
	private ComboBox comboAge;
	@FXML
	private ComboBox comboGender;
	@FXML
	private ComboBox comboFphone;
	public static Socket SignSocket;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnCommit.setOnAction(e -> {
			try {
				handleBtnSignAction(e);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		comboAge.setItems(ageList);
		comboGender.setItems(genderList);
		comboFphone.setItems(fPhoneList);
	}

	public void handleBtnSignAction(ActionEvent e) throws IOException {
		OutputStream os = SignSocket.getOutputStream();
		InputStream is = SignSocket.getInputStream();
		Protocol protocol = new Protocol();
		byte[] buf = protocol.getPacket();
		String[] data = new String[6];

		data[0] = txtId.getText(); // id
		data[1] = txtPwd.getText(); // password
		data[2] = txtName.getText(); // 이름
		data[3] = comboAge.getValue().toString(); // 나이
		data[4] = comboGender.getValue().toString(); // 성별
		data[5] = comboFphone.getValue().toString() + txtSecond.getText() + txtLast.getText(); // 번호
		for (int i = 0; i < data.length; i++) {
			if (data[i].contains(";")) {
				txtError.setText("';'은 금지된 문자 입니다.");
				return;
			}
		}
		// 프로토콜작업
		protocol = new Protocol(Protocol.PT_SIGN_UP, 1);
		protocol.setData(data);
		System.out.println("회원가입 정보 전송");
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
			case 2:
				System.out.println("서버가 회원가입 결과 전송.");
				String result = protocol.getResult();
				if (result.equals("0")) {
					System.out.println("가입 성공");
					try {
						Stage loginstage = (Stage) btnCommit.getScene().getWindow();
						loginstage.close();
						FXMLLoader addAccount = new FXMLLoader(getClass().getResource("AddAccount.fxml"));
						Parent root = (Parent) addAccount.load();
						Stage stage = new Stage();
						stage.setTitle("AddAccount");
						stage.setScene(new Scene(root));
						AddAccountController.AddAccSocket = SignSocket;
						stage.show();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else if (result.equals("1")) {
					System.out.println("ID중복으로 인한 가입 실패");
					txtError.setText("ID 중복입니다.");
				} else {
					System.out.println("알 수 없는 오류");
				}
			}
		}
	}

	private ObservableList<String> ageList = FXCollections.observableArrayList("14", "15", "16", "17", "18", "19", "20",
			"21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38",
			"39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56",
			"57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74",
			"75", "76", "77", "78", "79", "80");

	private ObservableList<String> genderList = FXCollections.observableArrayList("남자", "여자");

	private ObservableList<String> fPhoneList = FXCollections.observableArrayList("010", "011");

	public void handleBtnCancle(ActionEvent event) {
		try {
			Stage loginstage = (Stage) btnCancle.getScene().getWindow();
			loginstage.close();
			FXMLLoader login = new FXMLLoader(getClass().getResource("LogIn.fxml"));
			Parent root = (Parent) login.load();
			Stage stage = new Stage();
			stage.setTitle("LogIn");
			stage.setScene(new Scene(root));
			LogInController.LoginSocket = SignSocket;
			stage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
