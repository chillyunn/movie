package com.protocol;

import java.io.*;

public class Protocol {
	// 프로토콜
	public static final int PT_UNDEFINED = -1; // 프로토콜이 지정되어 있지 않은 경우
	public static final int PT_EXIT = 0; // 프로그램 종료
	public static final int PT_LOGIN = 1;
	public static final int PT_SIGN_UP = 2;
	public static final int PT_FIND = 3;
	public static final int PT_THEATER = 4;
	public static final int PT_MOVIE = 5;
	public static final int PT_BOOKING = 6;
	public static final int PT_REVIEW = 7;
	public static final int PT_DETAIL = 8;

	public static final int LEN_PROTOCOL_TYPE = 1; // 프로토콜 타입 길이
	public static final int LEN_PROTOCOL_CODE = 1; // 프로토콜 타입 길이
	public static final int LEN_MAX = 1000; // 패킷이 가질수 있는 최대 길이

	public static final int LEN_DATA_DATE = 13;
	public static final int LEN_DATA_ID = 21;
	public static final int LEN_DATA_PWD = 21;
	public static final int LEN_DATA_DETAIL = 36; // 나이3 성별1 번호11 이름20
	public static final int LEN_DATA_ACCOUNT = 63; // PW20 은행20 계좌번호20
	public static final int LEN_DATA_FIND = 41; // 이름20 id20 or 번호11
	public static final int LEN_DATA_THEATER_ID = 21;
	public static final int LEN_DATA_THEATER_ADDRESS = 101;
	public static final int LEN_DATA_THEATER_SCREEN = 21;
	public static final int LEN_DATA_MOVIE_TITLE = 201;
	public static final int LEN_DATA_MOVIE_TIMETABLE_ID = 3;
	public static final int LEN_DATA_SEATS = 4;
	public static final int LEN_DATA_SEATS_MAX = 301; // 3(001,002...) * 100자리
	public static final int LEN_DATA_BOOKING_ID = 5;
	public static final int LEN_DATA_REVIEW_ID = 5;
	public static final int LEN_DATA_REVIEW_CONTENT = 501;
	public static final int LEN_DATA_REVIEW_GRADE = 3;
	public static final int LEN_DATA_THEATER_DETAIL = 16; // 상영관수2 수익10 상영횟수3

	protected int protocolType;
	protected int protocolCode;
	private byte[] packet; // 프로토콜과 데이터의 저장공간이 되는 바이트 배열

	public Protocol() { // 생성자
		this(PT_UNDEFINED, PT_UNDEFINED);
	}

	public Protocol(int protocolType, int protocolCode) { // 생성자
		this.protocolType = protocolType;
		this.protocolCode = protocolCode;
		getPacket(protocolType, protocolCode);
	}

	public void setPacket(int pt_type, int pt_code, byte[] buf) {
		packet = null;
		packet = getPacket(pt_type, pt_code);
		protocolType = pt_type;
		protocolCode = pt_code;
		System.arraycopy(buf, 0, packet, 0, packet.length);
	}

	public byte[] getPacket() {
		return packet;
	}

	public byte[] getPacket(int protocolType, int protocolCode) {
		if (packet == null) {
			switch (protocolType) {
			case PT_LOGIN:
				switch (protocolCode) {
				case 1:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_ID + LEN_DATA_PWD];
					break;
				case 2:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + 1];
					break;

				}
				break;
			case PT_SIGN_UP:
				switch (protocolCode) {
				case 1:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_ID + LEN_DATA_PWD
							+ LEN_DATA_DETAIL];
					break;
				case 2:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + 1];
					break;
				case 3:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_ID];
					break;
				case 4:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + 1];
					break;
				case 5:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_ACCOUNT];
					break;
				case 6:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + 1];
					break;
				}
				break;
			case PT_FIND:
				switch (protocolCode) {
				case 1:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE];
					break;
				case 2:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_FIND];
					break;
				case 3:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_ID];
					break;
				case 4:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE];
					break;
				case 5:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_FIND];
					break;
				case 6:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_PWD];
					break;
				}
				break;
			case PT_THEATER:
				switch (protocolCode) {
				case 1:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE];
					break;
				case 2:
					packet = new byte[LEN_MAX];
					break;
				case 3:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_THEATER_ID
							+ LEN_DATA_THEATER_ADDRESS];
					break;
				case 4:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + 1];
					break;
				case 5:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_THEATER_ID];
					break;
				case 6:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + 1];
					break;
				case 7:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_THEATER_ID + LEN_DATA_THEATER_ID
							+ LEN_DATA_THEATER_ADDRESS];
					break;
				case 8:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + 1];
					break;
				case 9:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE];
					break;
				case 10:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_THEATER_SCREEN + LEN_DATA_SEATS];
					break;
				case 11:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_THEATER_ADDRESS];
					break;
				case 12:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_THEATER_SCREEN + LEN_DATA_SEATS];
					break;
				case 13:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + 1];
					break;
				case 14:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_THEATER_SCREEN];
					break;
				case 15:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + 1];
					break;
				}
				break;
			case PT_MOVIE:
				switch (protocolCode) {
				case 1:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_MOVIE_TITLE];
					break;
				case 2:
					packet = new byte[LEN_MAX];
					break;
				case 3:
					packet = new byte[LEN_MAX];
					break;
				case 4:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + 1];
					break;
				case 5:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_MOVIE_TITLE];
					break;
				case 6:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + 1];
					break;
				case 7:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_THEATER_ID
							+ LEN_DATA_THEATER_SCREEN + LEN_DATA_DATE];
					break;
				case 8:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_MOVIE_TITLE + LEN_DATA_DATE
							+ LEN_DATA_MOVIE_TIMETABLE_ID];
					break;
				case 9:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_THEATER_ID
							+ LEN_DATA_THEATER_SCREEN + LEN_DATA_MOVIE_TITLE + LEN_DATA_DATE];
					break;
				case 10:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + 1];
					break;
				case 11:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_THEATER_ID
							+ LEN_DATA_THEATER_SCREEN + LEN_DATA_DATE];
					break;
				case 12:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_MOVIE_TITLE + LEN_DATA_DATE
							+ LEN_DATA_MOVIE_TIMETABLE_ID];
					break;
				case 13:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_MOVIE_TIMETABLE_ID];
					break;
				case 14:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + 1];
					break;
				}
				break;
			case PT_BOOKING:
				switch (protocolCode) {
				case 1:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_THEATER_ID + LEN_DATA_DATE
							+ LEN_DATA_MOVIE_TITLE];
					break;
				case 2:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_THEATER_SCREEN
							+ LEN_DATA_MOVIE_TIMETABLE_ID + LEN_DATA_SEATS];
					break;
				case 3:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_ID + LEN_DATA_THEATER_ID
							+ LEN_DATA_THEATER_SCREEN + LEN_DATA_MOVIE_TIMETABLE_ID];
					break;
				case 4:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_SEATS_MAX];
					break;
				case 5:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_SEATS];
					break;
				case 6:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + 1];
					break;
				case 7:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_ID];
					break;
				case 8:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_BOOKING_ID + LEN_DATA_MOVIE_TITLE
							+ LEN_DATA_MOVIE_TIMETABLE_ID];
					break;
				case 9:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_BOOKING_ID];
					break;
				case 10:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + 1];
					break;
				}
				break;
			case PT_REVIEW:
				switch (protocolCode) {
				case 1:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_ID];
					break;
				case 2:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_REVIEW_ID + LEN_DATA_MOVIE_TITLE
							+ LEN_DATA_REVIEW_CONTENT];
					break;
				case 3:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_MOVIE_TITLE
							+ LEN_DATA_REVIEW_GRADE + LEN_DATA_REVIEW_CONTENT];
					break;
				case 4:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + 1];
					break;
				case 5:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_ID + LEN_DATA_REVIEW_ID];
					break;
				case 6:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + 1];
					break;
				case 7:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_REVIEW_ID + LEN_DATA_REVIEW_GRADE
							+ LEN_DATA_REVIEW_CONTENT];
					break;
				case 8:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + 1];
					break;
				}
				break;
			case PT_DETAIL:
				switch (protocolCode) {
				case 1:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_ID];
					break;
				case 2:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_ID + LEN_DATA_DETAIL];
					break;
				case 3:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_ID + LEN_DATA_PWD
							+ LEN_DATA_DETAIL];
					break;
				case 4:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + 1];
					break;
				case 5:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_THEATER_ID];
					break;
				case 6:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE + LEN_DATA_THEATER_ID
							+ LEN_DATA_THEATER_ADDRESS + LEN_DATA_THEATER_DETAIL];
					break;
				}
				break;
			case PT_EXIT:
				packet = new byte[LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE];
				break;
			case PT_UNDEFINED:
				packet = new byte[LEN_MAX];
				break;
			} // end switch
		} // end if
		packet[0] = (byte) protocolType; // packet 바이트 배열의 1,2 번째 바이트에 프로토콜 타입,코드 설정
		packet[1] = (byte) protocolCode;
		return packet;
	}

	public void setData(String[] in) {
		String data = in[0] + ";";
		for (int i = 1; i < in.length; i++) {
			data += in[i] + ";";
		}

		System.arraycopy(data.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE,
				data.trim().getBytes().length);
	}

	public String[] getData() {
		String data = new String(packet);
		data = data.substring(LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE);
		String[] result = data.split(";");
		return result;
	}

	public void setResult(String in) {
		System.arraycopy(in.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE,
				in.trim().getBytes().length);
	}

	public String getResult() {
		// String(byte[] bytes, int offset, int length)
		return new String(packet, LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE, 1).trim();
	}

//	public String getData() {
//		return new String(packet, LEN_PROTOCOL_TYPE + LEN_PROTOCOL_CODE, LEN_MAX - 2).trim();
//	}

}