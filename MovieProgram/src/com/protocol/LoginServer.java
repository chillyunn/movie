package com.protocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

import com.oracle.DBConnection;

public class LoginServer {
	public static void main(String[] args) {
		try {
			ServerSocket sSocket = new ServerSocket(3000);
			Socket socket = null;
			Connection conn = DBConnection.getConnection();
			try {
				System.out.println("**서버 실행**");
				// 다수의 클라이언트와 통신하기 위해 loop
				while (true) {
					System.out.println("클라이언트 접속 대기중...");
					socket = sSocket.accept(); // 클라이언트 접속시 새로운 소켓이 리턴
					ServerThread st = new ServerThread(socket);
					st.start();
					System.out.println(socket.getInetAddress() + "님 입장");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (socket != null)
				socket.close();
			if (sSocket != null)
				sSocket.close();
			System.out.println("**서버 종료**");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}