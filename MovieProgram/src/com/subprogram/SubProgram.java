package com.subprogram;

import java.sql.*;
import java.util.Scanner;

import com.persistence.*;

public class SubProgram {

	final int pay = 10000;

	public void Statistics(String ThtName) throws SQLException {
		String ThtAddress;
		String ScreenCount;
		String Balance;
		String TimeTableId;
		String StartTime;

		Connection conn = null;
		conn = getConnection();
		CallableStatement cs = conn.prepareCall("{call statistics(?,?,?,?,?)}");
		try {
			cs.setString(1, ThtName);
			cs.registerOutParameter(2, Types.LONGNVARCHAR);
			cs.registerOutParameter(3, Types.LONGNVARCHAR);
			cs.registerOutParameter(4, Types.LONGNVARCHAR);
			cs.registerOutParameter(5, Types.LONGNVARCHAR);
			
			cs.execute();
			ThtAddress = cs.getString(2);
			ScreenCount = cs.getString(3);
			Balance = cs.getString(4);
			TimeTableId = cs.getString(5);
			
			System.out.println("영화관 이름: "+ThtName);
			System.out.println("영화관 위치: "+ThtAddress);
			System.out.println("상영관 수: "+ScreenCount);
			System.out.println("영화관수익"+Balance);
			System.out.println("상영횟수"+TimeTableId);
		} catch (SQLException sqle) {
			System.out.println("UPDATE문에서 예외발생");
			sqle.printStackTrace();
		} finally {
			try {
				if (cs != null) {
					cs.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}

	}

	public void UnBooking(String gusid,String ticid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String SQL = "SELECT movtitle FROM Tickets WHERE ticid=?";
		
		conn = getConnection();
		try {
			cstmt = conn.prepareCall("{CALL UNBOOKING(?,?,?)}");
			cstmt.setString(1, gusid);
			cstmt.setString(2, ticid);
			cstmt.setInt(3, pay);

			
			
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, ticid);
			rs = pstmt.executeQuery();
			rs.next();
			String movtitle = rs.getString(1);
		
			while (rs.next()) {
				movtitle = rs.getString("movtitle");
			}
			MoviesDAO moviesDAO = new MoviesDAO();
			cstmt.execute();
			moviesDAO.update(movtitle);
			
			System.out.println("고객id: "+gusid+"티켓id: "+ticid+"취소 완료");
		} catch (SQLException sqle) {
			System.out.println("UNBOOKING문에서 예외 발생");
			sqle.printStackTrace();
		} finally {
			try {
				if(cstmt != null)
				{
					cstmt.close();
				}
				if(pstmt != null)
					pstmt.close();
				if(rs != null)
					rs.close();
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	public void Booking(String gusid, String thtid, String scrid, String movtitle, String ttid,String seatid) 
	{
		Connection conn = null;
		CallableStatement cstmt = null;
		conn = getConnection();
		try {
			cstmt = conn.prepareCall("{CALL BOOKING(?,?,?,?,?,?)}");
			cstmt.setString(1, gusid);
			cstmt.setString(2, thtid);
			cstmt.setString(3, scrid);
			cstmt.setString(4, movtitle);
			cstmt.setString(5, ttid);
			cstmt.setString(6, seatid);

			cstmt.execute();
			
			MoviesDAO moviesDAO = new MoviesDAO();
			moviesDAO.update(movtitle);
			System.out.println("고객id: "+gusid+"영화관id: "+thtid+"상영관id: "+scrid+"영화 제목"+movtitle+"상영번호"+ttid+"좌석번호: "+ seatid+"예매 완료");
		} catch (SQLException sqle) {
			System.out.println("BOOKING문에서 예외 발생");
			sqle.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (cstmt != null)
				{
					cstmt.close();
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			String user = "MOVIE";
			String pw = "123";
			String url = "jdbc:oracle:thin:@localhost:1521:xe";

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

}
