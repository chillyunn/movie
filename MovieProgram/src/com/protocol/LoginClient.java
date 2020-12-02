package com.protocol;

import java.net.*;
import java.io.*;
import com.oracle.*;

public class LoginClient {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		if (args.length < 2)
			System.out.println("사용법 : " + "java LoginClient 주소 포트번호");
		Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
		OutputStream os = socket.getOutputStream();
		InputStream is = socket.getInputStream();

		Protocol protocol = new Protocol();
		byte[] buf = protocol.getPacket();
		
		protocol = new Protocol(Protocol.PT_THEATER, 9);
		String[] tmp = new String[1];
		tmp[0] ="대구광장";
		protocol.setData(tmp);
		os.write(protocol.getPacket());
		while (true) {
			is.read(buf);
			int packetType = buf[0];
			int packetCode = buf[1];
			protocol.setPacket(packetType, packetCode, buf);
			
			if (packetType == Protocol.PT_EXIT) {
				System.out.println("클라이언트 종료");
				break;
			}

			switch (packetType) {
			case Protocol.PT_LOGIN:
				switch (packetCode) {
				case 1:
					System.out.println("서버가 로그인 정보 요청");
					BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
					System.out.print("아이디 : ");
					String id = userIn.readLine();
					System.out.print("암호 : ");
					String pwd = userIn.readLine();
					// 로그인 정보 생성 및 패킷 전송

					protocol = new Protocol(Protocol.PT_LOGIN, 1);
					protocol.setResult(id+pwd);
					System.out.println("로그인 정보 전송");
					os.write(protocol.getPacket());
					break;

				case 2:
					System.out.println("서버가 로그인 결과 전송.");
					String result = protocol.getResult();
					if (result.equals("0")) {
						System.out.println("로그인 성공");
					} else if (result.equals("1")) {
						System.out.println("암호 틀림");
					} else if (result.equals("2")) {
						System.out.println("아이디가 존재하지 않음");
					}
				}
//				protocol = new Protocol(Protocol.PT_EXIT, 0);
//				System.out.println("종료 패킷 전송");
//				os.write(protocol.getPacket());
		break;
			case Protocol.PT_THEATER:
				switch(packetCode)
				{
				case 2:
					String[] result = protocol.getData();
					for(String a:result)
					{
						System.out.println(a);
					}
					
					break;
				case 10:
					result = protocol.getData();
					for(String a:result)
					{
						System.out.println(a);
					}
					break;
					//
				}
			}
		}
		os.close();
		is.close();
		socket.close();
	}
}
