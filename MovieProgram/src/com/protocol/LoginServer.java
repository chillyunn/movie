package com.protocol;

import com.oracle.*;
import com.persistence.*;
import java.net.*;
import java.sql.*;
import java.io.*;
public class LoginServer {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		ServerSocket sSocket = new ServerSocket(3000);
		System.out.println("클라이언트 접속 대기중...");
		Socket socket = sSocket.accept();
		System.out.println("클라이언트 접속");
		Connection conn = DBConnection.getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		// 바이트 배열로 전송할 것이므로 필터 스트림 없이 Input/OutputStream만 사용해도 됨
		OutputStream os = socket.getOutputStream();
		InputStream is = socket.getInputStream();

		// 로그인 정보 요청용 프로토콜 객체 생성 및 전송
		Protocol protocol = null;
	//Protocol protocol = new Protocol(Protocol.PT_LOGIN, 1);
//		os.write(protocol.getPacket());
//		Protocol protocol = new Protocol(Protocol.PT_SIGN_UP, 1);
//		os.write(protocol.getPacket());
		boolean program_stop = false;

		while (true) {
			protocol = new Protocol(); rrr// 새 Protocol 객체 생성 (기본 생성자)
			byte[] buf = protocol.getPacket(); // 기본 생성자로 생성할 때에는 바이트 배열의 길이가 1000바이트로 지정됨
			is.read(buf); // 클라이언트로부터 로그인정보 (ID와 PWD) 수신
			int packetType = buf[0]; // 수신 데이터에서 패킷 타입 얻음
			int packetCode = buf[1];
			protocol.setPacket(packetType, packetCode, buf); // 패킷 타입을 Protocol 객체의 packet 멤버변수에 buf를 복사

			switch (packetType) {r
			case Protocol.PT_EXIT: // 프로그램 종료 수신
				protocol = new Protocol(Protocol.PT_EXIT, 0);
				os.write(protocol.getPacket());
				program_stop = true;
				System.out.println("서버종료");
				break;

			case Protocol.PT_LOGIN:
				switch (packetCode) {
				case 1:
					PreparedStatement pstmt = null;
					// 로그인 정보 수신
					System.out.println("클라이언트가 로그인 정보를 보냈습니다");
					String[] data = protocol.getData();
					String id = data[0];
					String password = data[1];
					System.out.println(id + password);
					String DBpw = "";
					try {
						String sql = "SELECT GusPw FROM Guests WHERE GusId = ?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, id);
						rs = pstmt.executeQuery();
						if (rs.next()) {
							DBpw = rs.getString(1);

							if (password.equals(DBpw)) {
								// 로그인 성공
								protocol = new Protocol(Protocol.PT_LOGIN, 2);
								protocol.setLoginResult("0");
								System.out.println("로그인 성공");
							} else { // 암호 틀림
								protocol = new Protocol(Protocol.PT_LOGIN, 2);
								protocol.setLoginResult("1");
								System.out.println("암호 틀림");
							}

						} else {
							String sql2 = "SELECT AdmPw FROM Admins WHERE AdmId = ?";
							pstmt = conn.prepareStatement(sql2);
							pstmt.setString(1, id);
							rs = pstmt.executeQuery();
							if (rs.next()) {
								DBpw = rs.getString(1);
								if (password.equals(DBpw)) {
									// 로그인 성공
									protocol = new Protocol(Protocol.PT_LOGIN, 2);
									protocol.setLoginResult("0");
									System.out.println("로그인 성공");
								} else { // 암호 틀림
									protocol = new Protocol(Protocol.PT_LOGIN, 2);
									protocol.setLoginResult("1");
									System.out.println("암호 틀림");
								}

							} else { // 아이디 존재 안함
								protocol = new Protocol(Protocol.PT_LOGIN, 2);
								protocol.setLoginResult("2");
								System.out.println("아이디 존재안함");
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("로그인 처리 결과 전송");
					os.write(protocol.getPacket());

				}
				break;
			case Protocol.PT_SIGN_UP:
				switch (packetCode) {
				case 1:
					PreparedStatement pstmt = null;
					// 회원가입 정보 수신
					System.out.println("클라이언트가 회원가입 정보를 보냈습니다");
					String[] data = protocol.getData();
					String id = data[0];
					String password = data[1];
					String name = data[2];
					String age = data[3];
					String gender = data[4];
					String phone = data[5];
					System.out.println(id + password + name + age + gender + phone);
					GuestsDAO.insert(id,password,name,age,gender,phone);
					
					protocol = new Protocol(Protocol.PT_SIGN_UP, 2);
					os.write(protocol.getPacket());
					System.out.println("회원가입 완료 및 결과 전송 완료");
					break;
				case 3:
					System.out.println("클라이언트가 중복확인을 위한 ID정보를 보냈습니다");
					
				}

			}// end switch
			if (program_stop)
				break;

		} // end while

		is.close();
		os.close();
		socket.close();
	}
}
