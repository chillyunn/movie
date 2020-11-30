package com.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TheatersDAO {
	public ArrayList<TheatersDTO> selectAll() {
		ArrayList<TheatersDTO> dtos = new ArrayList<TheatersDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM Theaters";
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				String ThtId = rs.getString("ThtId");
				String ThtAddress = rs.getString("ThtAddress");
				TheatersDTO dto = new TheatersDTO(ThtId, ThtAddress);
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

	public ArrayList<TheatersDTO> selectId() {
		ArrayList<TheatersDTO> dtos = new ArrayList<TheatersDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT ThtID FROM Theaters";
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				String ThtId = rs.getString("ThtId");
				TheatersDTO dto = new TheatersDTO(ThtId);
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

	public static ArrayList<TheatersDTO> insert(String id, String address) {
		ArrayList<TheatersDTO> dtos = new ArrayList<TheatersDTO>();

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String preQuery = "INSERT INTO Theaters(ThtId,ThtAddress) VALUES(?,?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(preQuery);
			pstmt.setString(1, id);
			pstmt.setString(2, address);
			pstmt.executeUpdate();
			System.out.println("INSERT성공: " + id + "|" + address);
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

	public static ArrayList<TheatersDTO> update(String ThtId,String ThtAddress) {
		ArrayList<TheatersDTO> dtos = new ArrayList<TheatersDTO>();
		PreparedStatement pstmt = null;
		Connection conn = null;
		String SQL = "UPDATE Theaters SET ThtAddress=? WHERE ThtId=?";
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, ThtAddress);
			pstmt.setString(2, ThtId);
			pstmt.executeUpdate();
			System.out.println("UPDATE성공: " + ThtAddress);
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
