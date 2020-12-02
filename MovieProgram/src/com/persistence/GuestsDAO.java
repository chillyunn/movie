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

public class GuestsDAO {
	public static ArrayList<GuestsDTO> selectAll() {
		ArrayList<GuestsDTO> dtos = new ArrayList<GuestsDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM Guests";
		try {
			conn = ConnectSetting.getConnection();
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
			conn = ConnectSetting.getConnection();
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
			conn = ConnectSetting.getConnection();
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
			conn = ConnectSetting.getConnection();
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
			conn = ConnectSetting.getConnection();
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
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String GusId = null;
		String SQL = "SELECT GusId FROM Guests WHERE Gusid = ?";
		try {
			conn = ConnectSetting.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next())
				GusId = rs.getString("GusId");
			else
				GusId = "";

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
		if (id.equals(GusId))
			return true;
		else
			return false;
	}
	public static String findId(String name,String phone) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String GusId = null;
		String SQL = "SELECT GusId FROM Guests WHERE GusName = ? and GusPhone = ?";
		try {
			conn = ConnectSetting.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			rs = pstmt.executeQuery();
			if(rs.next())
				GusId = rs.getString("GusId");
			else
				GusId = "";

		} catch (SQLException sqle) {
			System.out.println("findlId문에서 예외 발생");
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
		return GusId;
	}
	public static String findPw(String id,String name) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String GusPw = null;
		String SQL = "SELECT GusPw FROM Guests WHERE GusId = ? and GusName = ?";
		try {
			conn = ConnectSetting.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			rs = pstmt.executeQuery();
			if(rs.next())
				GusPw = rs.getString("GusPw");
			else
				GusPw = "";

		} catch (SQLException sqle) {
			System.out.println("findlPw문에서 예외 발생");
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
		return GusPw;
	}

	public ArrayList<GuestsDTO> update(String gusId, String pw, String gusName, String gusAge, String gusPhone) {
		ArrayList<GuestsDTO> dtos = new ArrayList<GuestsDTO>();
		PreparedStatement pstmt = null;
		Connection conn = null;
		String SQL = "UPDATE Guests SET GusPw=?, GusName=?, GusAge=?, GusPhone=? WHERE GusId=?";
		try {
			conn = ConnectSetting.getConnection();
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