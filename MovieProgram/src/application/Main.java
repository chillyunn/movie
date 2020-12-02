package application;

import java.io.IOException;
import java.net.Socket;

import com.oracle.DBConnection;
import com.setting.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
			Scene scene = new Scene(root, 800, 600);
			primaryStage.setTitle("Movie");
			primaryStage.setScene(scene);
			try {
				LogInController.LoginSocket = ConnectSetting.connectServer();

			} catch (IOException e) {
				e.printStackTrace();
			}

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}