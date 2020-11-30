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

public class GuestsDAO {
	public static ArrayList<GuestsDTO> selectAll() {
		ArrayList<GuestsDTO> dtos = new ArrayList<GuestsDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM Guests";
		try {
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				String GusId = rs.getString("GusId");
				String GusPw = rs.getString("GusPw");
				String GusName = rs.getString("GusName");
				String GusAge = rs.getString("GusAge");
				String GusGender = rs.getString("GusGender");
				String GusPhone = rs.getString("GusPhone");

				GuestsDTO dto = new GuestsDTO(GusId, GusPw, GusName, GusAge, GusGender, GusPhone);
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

	public static ArrayList<GuestsDTO> selectId() {
		ArrayList<GuestsDTO> dtos = new ArrayList<GuestsDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT GusId FROM Guests";
		try {
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				String GusId = rs.getString("GusId");

				GuestsDTO dto = new GuestsDTO(GusId, 0);
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

	public static ArrayList<GuestsDTO> selectGender() {
		ArrayList<GuestsDTO> dtos = new ArrayList<GuestsDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT GusGender FROM Guests";
		try {
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				String GusGender = rs.getString("GusGender");

				GuestsDTO dto = new GuestsDTO(GusGender, 0);
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

	public static ArrayList<GuestsDTO> selectAge() {
		ArrayList<GuestsDTO> dtos = new ArrayList<GuestsDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT GusAge FROM Guests";
		try {
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				String GusAge = rs.getString("GusAge");

				GuestsDTO dto = new GuestsDTO(GusAge, 2);
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

	public static ArrayList<GuestsDTO> insert(String GusId, String GusPw, String GusName, String GusAge,
			String GusGender, String GusPhone) {
		ArrayList<GuestsDTO> dtos = new ArrayList<GuestsDTO>();

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String preQuery = "INSERT INTO Guests(GusId,GusPw,GusName,GusAge,GusGender,GusPhone) VALUES(?,?,?,?,?,?)";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(preQuery);
			pstmt.setString(1, GusId);
			pstmt.setString(2, GusPw);
			pstmt.setString(3, GusName);
			pstmt.setString(4, GusAge);
			pstmt.setString(5, GusGender);
			pstmt.setString(6, GusPhone);
			pstmt.executeUpdate();
			System.out.println("INSERT성공: " + GusId);
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

	public static boolean EqualId(String id) {
		ArrayList<GuestsDTO> dtos = new ArrayList<GuestsDTO>();
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String GusId = null;
		String SQL = "SELECT GusId FROM Guests WHERE Gusid = ?";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery(SQL);
			while (rs.next()) {
				GusId = rs.getString("GusId");
			}

		} catch (SQLException sqle) {
			System.out.println("SELECT문에서 예외 발생");
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
		if (id.equals(GusId))
			return true;
		else
			return false;
	}

	public ArrayList<GuestsDTO> update(String gusId, String pw, String gusName, String gusAge, String gusPhone) {
		ArrayList<GuestsDTO> dtos = new ArrayList<GuestsDTO>();
		PreparedStatement pstmt = null;
		Connection conn = null;
		String SQL = "UPDATE Guests SET GusPw=?, GusName=?, GusAge=?, GusPhone=? WHERE GusId=?";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, pw);
			pstmt.setString(2, gusName);
			pstmt.setString(3, gusAge);
			pstmt.setString(4, gusPhone);
			pstmt.setString(5, gusId);

			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("UPDATE_GUEST문에서 예외 발생");
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