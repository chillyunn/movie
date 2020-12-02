package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.persistence.AccountsDAO;
import com.persistence.GuestsDAO;
import com.persistence.ScreensDAO;
import com.persistence.TheatersDAO;
import com.setting.ConnectSetting;
import com.setting.Protocol;

/**
 * 개별 클라이언트와 메세지를 주고 받는 스레드 Socket: 멤버변수, 생성자를 통해서 TestServer에서 할당 받는다. 1.
 * extends Thread 2. run() 클라이언트의 메세지를 주고 받는 비즈니스(Socket)
 * 
 * @author 관리자
 *
 */
public class ServerThread extends Thread {
	// 멤버변수로 선언
	private Socket socket;
	private BufferedReader br = null;
	private PrintWriter pw = null;
	private String userIP;

	ServerThread(Socket s) {
		this.socket = s;
		this.userIP = socket.getInetAddress().toString();
	}

	// 오버라이딩일 경우 throw 불가.
	public void run() {
		try {
			service();
		} catch (IOException e) {
			System.out.println("**" + userIP + "님 접속 종료.");
		} finally {
			try {
				closeAll();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void service() throws IOException {
		Connection conn = ConnectSetting.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		Protocol protocol = null;
		String[] tmp_data = null;
		OutputStream os = socket.getOutputStream();
		InputStream is = socket.getInputStream();
		boolean program_stop = false;

		while (true) {
			protocol = new Protocol(); // 새 Protocol 객체 생성 (기본 생성자)
			byte[] buf = protocol.getPacket(); // 기본 생성자로 생성할 때에는 바이트 배열의 길이가 1000바이트로 지정됨
			is.read(buf); // 클라이언트로부터 로그인정보 (ID와 PWD) 수신
			int packetType = buf[0]; // 수신 데이터에서 패킷 타입 얻음
			int packetCode = buf[1];
			protocol.setPacket(packetType, packetCode, buf); // 패킷 타입을 Protocol 객체의 packet 멤버변수에 buf를 복사

			switch (packetType) {
			case Protocol.PT_EXIT: // 프로그램 종료 수신
				System.out.println("클라이언트 종료");
				throw new IOException();
			case Protocol.PT_LOGIN:
				switch (packetCode) {
				case 1:
					PreparedStatement pstmt = null;
					// 로그인 정보 수신
					System.out.println("클라이언트가 " + "로그인 정보를 보냈습니다");
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
								protocol.setResult("1");
								System.out.println("로그인 성공");
							} else { // 암호 틀림
								protocol = new Protocol(Protocol.PT_LOGIN, 2);
								protocol.setResult("3");
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
									protocol.setResult("2");
									System.out.println("로그인 성공");
								} else { // 암호 틀림
									protocol = new Protocol(Protocol.PT_LOGIN, 2);
									protocol.setResult("3");
									System.out.println("암호 틀림");
								}

							} else { // 아이디 존재 안함
								protocol = new Protocol(Protocol.PT_LOGIN, 2);
								protocol.setResult("4");
								System.out.println("아이디 존재안함");
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("로그인 처리 결과 전송");
					os.write(protocol.getPacket());
					break;
				}
				break;
			case Protocol.PT_SIGN_UP:
				switch (packetCode) {
				case 1: // code1 회원가입 정보 수신 후 id중복검사, 없을 시DB에 등록 -> code2에 data 성공=0,실패=1 보냄
					System.out.println("클라이언트가 회원가입 정보를 보냈습니다");
					String[] data = protocol.getData();
					String id = data[0];
					tmp_data[0] = id;
					String password = data[1];
					String name = data[2];
					String age = data[3];
					String gender = data[4];
					String phone = data[5];
					System.out.println(id + "/" + password + "/" + name + "/" + age + "/" + gender + "/" + phone);
					protocol = new Protocol(Protocol.PT_SIGN_UP, 2);
					// id 중복확인
					if (GuestsDAO.EqualId(id)) {
						System.out.println("서버DB에 동일한 ID 존재");
						protocol.setResult("1");
					} else {
						System.out.println("서버DB에 동일한 ID 없음");
						protocol.setResult("0");
						GuestsDAO.insert(id, password, name, age, gender, phone);
					}

					os.write(protocol.getPacket());
					System.out.println("회원가입 결과 전송 완료");
					break;
				case 3: // code3 계좌정보 수신 후 계좌등록, 성공시 code4 보냄
					data = protocol.getData();
					id = tmp_data[0];
					String bankId = data[0];
					String serial = data[1];
					String pwd = data[2];

					System.out.println(id + "/" + bankId + "/" + serial + "/" + pwd);
					protocol = new Protocol(Protocol.PT_SIGN_UP, 4);
					if (AccountsDAO.insert(id, pwd, bankId, serial, "20000"))
						protocol.setResult("0");
					else
						protocol.setResult("1");

					os.write(protocol.getPacket());
					System.out.println("계좌등록 결과 전송 완료");
					break;
				}
				break;
			case Protocol.PT_FIND:
				switch (packetCode) {
				case 1: // 클라이언트가 id찾기 확인버튼->코드1(고객이름,전화번호) 전송/서버가 수신 후 코드2로 id 전송
					String[] data = protocol.getData();
					String name = data[0];
					String phone = data[1];
					String result = GuestsDAO.findId(name, phone);

					if (!result.equals("")) { // id 찾은 결과 존재 -> data 값에 id 담아서 전송
						System.out.println("일치하는 id 발견");
						protocol = new Protocol(Protocol.PT_FIND, 2);
						protocol.setResult(result);

					} else { // id 찾은 결과 없음 -> data 값에 0 담아서 전송
						System.out.println("일치하는 id 없음");
						protocol = new Protocol(Protocol.PT_FIND, 3);
					}
					os.write(protocol.getPacket());
					System.out.println("id찾기 결과 전송 완료");
					break;
				case 4: // pw찾기 ->클라이언트 코드3(id,이름) 전송 -> 수신 후 코드4로 pw 전송
					data = protocol.getData();
					String id = data[0];
					name = data[1];
					result = GuestsDAO.findPw(id, name);

					if (!result.equals("")) { // pw 찾은 결과 존재 -> data 값에 pw 담아아서 전송
						System.out.println("일치하는 id 발견");
						protocol = new Protocol(Protocol.PT_FIND, 5);
						protocol.setResult(result);

					} else { // pw 찾은 결과 없음 -> data 값에 0 담아서 전송
						System.out.println("일치하는 id 없음");
						protocol = new Protocol(Protocol.PT_FIND, 6);
					}
					os.write(protocol.getPacket());
					System.out.println("id찾기 결과 전송 완료");
					break;
				}
			case Protocol.PT_THEATER:
				switch (packetCode) {
				case 1: // 영화관 목록 전송 요청 => code2로 영화관 목록 전송
					protocol = new Protocol(Protocol.PT_THEATER, 2);
					protocol.setData(TheatersDAO.selectId());
					os.write(protocol.getPacket());
					System.out.println("영화관 목록 전송 완료");
					break;
				case 3: // 영화관 추가 요청 => code4로 영화관 추가 응답 전송
					System.out.println("클라이언트가 영화관 추가 요청을 보냈습니다");
					String[] data = protocol.getData();
					String id = data[0];
					String address = data[1];
					System.out.println(id + "/" + address);
					protocol = new Protocol(Protocol.PT_THEATER, 4);
					// id 중복확인
					if (TheatersDAO.EqualId(id)) {
						System.out.println("서버DB에 동일한 ID 존재");
						protocol.setResult("1");
					} else {
						System.out.println("서버DB에 동일한 ID 없음");
						protocol.setResult("0");
						TheatersDAO.insert(id, address);
					}

					os.write(protocol.getPacket());
					System.out.println("영화관 추가 결과 전송 완료");
					break;
				case 5: // 영화관 삭제 요청
					System.out.println("클라이언트가 영화관 삭제 요청을 보냈습니다");
					data = protocol.getData();
					id = data[0];
					protocol = new Protocol(Protocol.PT_THEATER, 6);
					if (TheatersDAO.EqualId(id)) {
						System.out.println("서버DB에 동일한 ID 존재"); // 삭제
						TheatersDAO.delete(id);
						protocol.setResult("1");
					} else {
						System.out.println("서버DB에 동일한 ID 없음"); // 삭제 실패
						protocol.setResult("2");
					}
					os.write(protocol.getPacket());
					System.out.println("영화관 삭제 결과 전송 완료");
					break;
				case 7: // 영화관 수정 요청
					System.out.println("클라이언트가 영화관 수정 요청을 보냈습니다");
					data = protocol.getData();
					String oldId = data[0];
					String newId = data[1];
					String newAddress = data[2];
					protocol = new Protocol(Protocol.PT_THEATER, 8);
					if (TheatersDAO.EqualId(oldId)) {
						System.out.println("서버DB에 동일한 ID 존재"); // 수정
						TheatersDAO.update(oldId, newId, newAddress);
						protocol.setResult("1");
					} else {
						System.out.println("서버DB에 동일한 ID 없음"); // 수정 실패
						protocol.setResult("2");
					}
					os.write(protocol.getPacket());
					System.out.println("영화관 수정 결과 전송 완료");
					break;
				case 9: // 영화관 버튼 눌렀을 때 그 영화관에 해당하는 상영관id/좌석수 출력
					System.out.println("클라이언트가 영화관 상세정보 요청을 보냈습니다");
					data = protocol.getData();
					String thtId = data[0];
					protocol = new Protocol(Protocol.PT_THEATER, 10);
					protocol.setData(ScreensDAO.selectId(thtId));
					os.write(protocol.getPacket());
					System.out.println("상영관 목록 전송 완료");
					break;
				case 11: // 상영관 추가 요청
					System.out.println("클라이언트가 상영관 추가 요청을 보냈습니다");
					data = protocol.getData();
					String scrId = data[0];
					String scrType = data[1];
					String scrPremium = data[2];
					String seatNum =data[3];
					
					
				}
				break;
			}// end switch
			if (program_stop) {
				break;
			}
		}
	}

	public void closeAll() throws IOException {
		if (socket != null)
			socket.close();
	}
}
