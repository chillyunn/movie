package com.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import com.setting.ConnectSetting;

public class TheatersDAO {
	public ArrayList<TheatersDTO> selectAll() {
		ArrayList<TheatersDTO> dtos = new ArrayList<TheatersDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM Theaters";
		try {
			conn = ConnectSetting.getConnection();
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
			conn = ConnectSetting.getConnection();
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
	public static boolean EqualId(String id) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String ThtId = null;
		String SQL = "SELECT ThtId FROM Theaters WHERE ThtId = ?";
		try {
			conn = ConnectSetting.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next())
				ThtId = rs.getString("ThtId");
			else
				ThtId = "";

		} catch (SQLException sqle) {
			System.out.println("equalId문에서 예외 발생");
			sqle.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
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
		if (id.equals(ThtId))
			return true;
		else
			return false;
	}
	public static ArrayList<TheatersDTO> insert(String id, String address) {
		ArrayList<TheatersDTO> dtos = new ArrayList<TheatersDTO>();

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String preQuery = "INSERT INTO Theaters(ThtId,ThtAddress) VALUES(?,?)";
		try {
			conn = ConnectSetting.getConnection();
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

	public static void update(String oldId,String newId, String ThtAddress) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		String SQL = "UPDATE Theaters SET ThtId=?,ThtAddress=? WHERE ThtId=?";

		try {
			conn = ConnectSetting.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, newId);
			pstmt.setString(2, ThtAddress);
			pstmt.setString(3, oldId);
			pstmt.executeUpdate();
			System.out.println("UPDATE성공: " + newId);
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
	}
	public static void delete(String ThtId)
	{
		PreparedStatement pstmt = null;
		Connection conn = null;
		String SQL = "DELETE FROM Theaters WHERE ThtId=?";

		try {
			conn = ConnectSetting.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, ThtId);
			pstmt.executeUpdate();
			System.out.println("DELETE성공: " + ThtId);
		} catch (SQLException sqle) {
			System.out.println("DELETE문에서 예외발생");
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
	}

}
