package com.persistence;

import java.sql.*;
import java.util.ArrayList;
import com.oracle.DBConnection;

public class AccountsDAO {
	public static ArrayList<AccountsDTO> selectAll() {
		ArrayList<AccountsDTO> dtos = new ArrayList<AccountsDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM ACCOUNTS";
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				String AccId = rs.getString("AccId");
				String AccPw = rs.getString("AccPw");
				String AccBank = rs.getString("AccBank");
				String AccSerial = rs.getString("AccSerial");
				String AccBalance = rs.getString("AccBalance");
				AccountsDTO dto = new AccountsDTO(AccId, AccPw, AccBank, AccSerial, AccBalance);
				dtos.add(dto);
			}
		} catch (SQLException sqle) {
			System.out.println("SELECT문에서 예외 발생");
			sqle.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return dtos;
	}

	public static ArrayList<AccountsDTO> insert(String AccId, String AccPw, String AccBank, String AccSerial,
			String AccBalance) {
		ArrayList<AccountsDTO> dtos = new ArrayList<AccountsDTO>();

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;

		String preQuery = "INSERT INTO Accounts(AccId, AccPw, AccBank, AccSerial, AccBalance) VALUES(?,?,?,?,?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(preQuery);
			pstmt.setString(1, AccId);
			pstmt.setString(2, AccPw);
			pstmt.setString(3, AccBank);
			pstmt.setString(4, AccSerial);
			pstmt.setString(5, AccBalance);
			pstmt.executeUpdate();
			System.out.println("INSERT성공: " + AccId);
		} catch (SQLException sqle) {
			System.out.println("INSERT문에서 예외 발생");
			sqle.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return dtos;

	}

	public ArrayList<AccountsDTO> updateBalance(String base, String goal, String val) {
		ArrayList<AccountsDTO> dtos = new ArrayList<AccountsDTO>();
		PreparedStatement pstmt = null;
		Connection conn = null;

		String SQL = "UPDATE Accounts SET AccBalance=? WHERE AccId=?";
		String SQL2 = "SELECT AccBalance FROM Accounts WHERE AccId=?";
		String resultBase = null, resultGoal = null;
		ResultSet rs = null;

		
		try {
			conn = getConnection();
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(SQL2);
			pstmt.setString(1, base);
			rs = pstmt.executeQuery();
			rs.next();
			resultBase = rs.getString(1);
			
			rs = null;
			pstmt = null;

			pstmt = conn.prepareStatement(SQL2);
			pstmt.setString(1, goal);
			rs = pstmt.executeQuery();
			rs.next();
			resultGoal = rs.getString(1);

			pstmt = null;
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, Integer.toString(Integer.parseInt(resultBase) - Integer.parseInt(val)));
			pstmt.setString(2, base);
			pstmt.executeUpdate();
			

			pstmt = null;
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, Integer.toString(Integer.parseInt(resultGoal) + Integer.parseInt(val)));
			pstmt.setString(2, goal);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException sqle) {
			System.out.println("UPDATE_BALNCE문에서 예외 발생");
			if(conn!=null)try{conn.rollback();}catch(SQLException sqle1) {}
			sqle.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		
		return dtos;
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
