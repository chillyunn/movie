package com.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.oracle.DBConnection;

public class AdminsDAO {
	public static ArrayList<AdminsDTO> selectAll() {
		ArrayList<AdminsDTO> dtos = new ArrayList<AdminsDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM ADMINS";
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				String AdmId = rs.getString("AdmId");
				String AdmPw = rs.getString("AdmPw");
				String ThtId = rs.getString("ThtId");
				AdminsDTO dto = new AdminsDTO(AdmId, AdmPw, ThtId);
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

	public static ArrayList<AdminsDTO> insert(String AdmId, String AdmPw, String ThtId) {
		ArrayList<AdminsDTO> dtos = new ArrayList<AdminsDTO>();

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;

		String preQuery = "INSERT INTO Admins(AdmId,AdmPw,ThtId) VALUES(?,?,?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(preQuery);
			pstmt.setString(1, AdmId);
			pstmt.setString(2, AdmPw);
			pstmt.setString(3, ThtId);
			pstmt.executeUpdate();
			System.out.println("INSERT성공: " + AdmId);
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

	public ArrayList<AdminsDTO> update(String admId, String thtId, String pw) {
		ArrayList<AdminsDTO> dtos = new ArrayList<AdminsDTO>();
		PreparedStatement pstmt = null;
		Connection conn = null;
		String SQL = "UPDATE Admins SET ThtId=?, AdmPw=? WHERE AdmId=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, thtId);
			pstmt.setString(2, pw);
			pstmt.setString(3, admId);

			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("UPDATE_ADMIN문에서 예외 발생");
			sqle.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
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
