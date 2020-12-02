package com.setting;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectSetting {

	public static String DBIP = "211.52.143.104";
//	public static String DBIP = "192.168.43.58";
	public static String DBPort = "1521";

//	public static String ServerIP = "211.52.143.104";
	public static String ServerIP = "localhost";
	public static String ServerPort = "3000";

//	
//	public static String ServerIP = "220.122.253.64";

	public static Connection getConnection() {
		Connection conn = null;
		try {
			String user = "MOVIE";
			String pw = "123";
			String url = "jdbc:oracle:thin:@" + DBIP + ":" + DBPort + ":xe";

			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pw);

			System.out.println("Database에 연결되었습니다.\n");

		} catch (ClassNotFoundException cnfe) {
			System.out.println("DB 드라이버 로딩 실패 :" + cnfe.toString());
		} catch (SQLException sqle) {
			System.out.println("DB 접속실패 : " + sqle.toString());
		} catch (Exception e) {
			System.out.println("Unkonwn error");
			e.printStackTrace();
		}
		return conn;
	}

	public static Socket connectServer() throws IOException {
		Socket socket = new Socket(ServerIP, Integer.parseInt(ServerPort));
		return socket;
	}
}