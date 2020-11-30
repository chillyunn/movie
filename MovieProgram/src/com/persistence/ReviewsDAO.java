package com.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ReviewsDAO {
	public ArrayList<ReviewsDTO> selectAll() {
		ArrayList<ReviewsDTO> dtos = new ArrayList<ReviewsDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM Reviews";
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				String RevId = rs.getString("RevId");
				String GusId = rs.getString("GusId");
				String MovTitle = rs.getString("MovTitle");
				String RevDate = rs.getString("RevDate");
				String RevContent = rs.getString("RevContent");
				String RevGrad = rs.getString("RevGrade");

				ReviewsDTO dto = new ReviewsDTO(RevId, GusId, MovTitle, RevDate, RevContent, RevGrad);
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

	public static ArrayList<ReviewsDTO> insert(String RevId, String GusId, String MovTitle, String RevDate, String RevContent,
			String RevGrade) {
		ArrayList<ReviewsDTO> dtos = new ArrayList<ReviewsDTO>();

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;

		String preQuery = "INSERT INTO Reviews(RevId,GusId,MovTitle,RevDate,Revcontent,RevGrade) VALUES(?,?,?,?,?,?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(preQuery);
			pstmt.setString(1, RevId);
			pstmt.setString(2, GusId);
			pstmt.setString(3, MovTitle);
			pstmt.setString(4, RevDate);
			pstmt.setString(5, RevContent);
			pstmt.setString(6, RevGrade);
			pstmt.executeUpdate();
			System.out.println("INSERT성공: " + RevId);
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
	
	public static ArrayList<ReviewsDTO> update(String GusId,String MovTitle, String RevContent, String RevGrade) {
		ArrayList<ReviewsDTO> dtos = new ArrayList<ReviewsDTO>();
		PreparedStatement pstmt = null;
		Connection conn = null;
		String SQL = "UPDATE Reviews SET RevContent=?,RevGrade=?,RevDate=SYSDATE WHERE GusId=? AND MovTitle=?";
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, RevContent);
			pstmt.setString(2, RevGrade);
			pstmt.setString(3, GusId);
			pstmt.setString(4, MovTitle);
			pstmt.executeUpdate();
			System.out.println("UPDATE성공: " + GusId);
		} catch (SQLException sqle) {
			System.out.println("UPDATE문에서 예외발생");
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
