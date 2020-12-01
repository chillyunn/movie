package com.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import com.oracle.DBConnection;

public class TheatersDAO {
	public ArrayList<TheatersDTO> selectAll() {
		ArrayList<TheatersDTO> dtos = new ArrayList<TheatersDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM Theaters";
		try {
			conn = DBConnection.getConnection();
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

	public static String[] selectId() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String[]result = new String[list.size()];
		
		String SQL = "SELECT ThtID FROM Theaters";
		try {
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				list.add(rs.getString("ThtId"));
			}
			
			result = list.toArray(result);
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
		return result;
	}

	public ArrayList<TheatersDTO> insert(String id, String address) {
		ArrayList<TheatersDTO> dtos = new ArrayList<TheatersDTO>();

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String preQuery = "INSERT INTO Theaters(ThtId,ThtAddress) VALUES(?,?)";
		try {
			conn = DBConnection.getConnection();
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

	public ArrayList<TheatersDTO> update(String ThtId, String ThtAddress) {
		ArrayList<TheatersDTO> dtos = new ArrayList<TheatersDTO>();
		PreparedStatement pstmt = null;
		Connection conn = null;
		String SQL = "UPDATE Theaters SET ThtAddress=? WHERE ThtId=?";

		try {
			conn = DBConnection.getConnection();
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

}
