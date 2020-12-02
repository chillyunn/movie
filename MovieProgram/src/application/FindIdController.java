package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import com.setting.Protocol;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FindIdController implements Initializable {
	public static Socket FindIdSocket;
	@FXML
	private BorderPane FindId;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtSphone;
	@FXML
	private TextField txtLphone;
	@FXML
	private ComboBox comboFphone;
	@FXML
	private Text txtId;
	@FXML
	private Button btnFindId;
	@FXML
	private Button btnCancle;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comboFphone.setItems(fPhoneList);
	}

	private ObservableList<String> fPhoneList = FXCollections.observableArrayList("010", "011");

	public void handleBtnFindId(ActionEvent event) throws IOException {
		OutputStream os = FindIdSocket.getOutputStream();
		InputStream is = FindIdSocket.getInputStream();
		Protocol protocol = new Protocol();
		byte[] buf = protocol.getPacket();
		String[] data = new String[2];

		data[0] = txtName.getText(); // 이름
		data[1] = comboFphone.getValue().toString() + txtSphone.getText() + txtLphone.getText(); // 번호
		for (int i = 0; i < data.length; i++) {
			if (data[i].contains(";")) {
				txtId.setText("';'은 금지된 문자 입니다.");
				return;
			}
		}
		// 프로토콜작업
		protocol = new Protocol(Protocol.PT_FIND, 1);
		protocol.setData(data);

		System.out.println("이름,번호 전송");
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
		case Protocol.PT_FIND:
			System.out.println("서버가 ID찾기 결과 전송.");
			switch (packetCode) {
			case 2:
				String[] result = protocol.getData();
				txtId.setText(result[0]);
				break;
			case 3:
				txtId.setText("존재하지 않는 고객입니다.");
				break;
			}
		}
	}

	public void handleBtnCancle(ActionEvent event) {
		try {
			Stage idfindstage = (Stage) btnCancle.getScene().getWindow();
			idfindstage.close();
			FXMLLoader login = new FXMLLoader(getClass().getResource("LogIn.fxml"));
			Parent root = (Parent) login.load();
			Stage stage = new Stage();
			stage.setTitle("LogIn");
			stage.setScene(new Scene(root));
			LogInController.LoginSocket = FindIdSocket;
			stage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
