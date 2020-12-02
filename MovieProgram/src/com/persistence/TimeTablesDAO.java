package com.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.setting.ConnectSetting;

public class TimeTablesDAO {
	public ArrayList<TimeTablesDTO> selectAll() {
		ArrayList<TimeTablesDTO> dtos = new ArrayList<TimeTablesDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM TimeTables";
		try {
			conn = ConnectSetting.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				String TtId = rs.getString("TtId");
				String MovTitle = rs.getString("MovTitle");
				String ThtId = rs.getString("ThtId");
				String ScrId = rs.getString("ScrId");
				String TtTime = rs.getString("TtTime");

				TimeTablesDTO dto = new TimeTablesDTO(TtId, MovTitle, ThtId, ScrId, TtTime);
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
	public static String[] selectTimetable(String thtId) {
		ArrayList<String> list = new ArrayList<String>();
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String[]result = new String[list.size()];
		
		String SQL = "SELECT * FROM Movies where thtId = ?";
		try {
			conn = ConnectSetting.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, thtId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("TtId"));
				list.add(rs.getString("MovTitle"));
				list.add(rs.getString("ThtId"));
				list.add(rs.getString("ScrId"));
				list.add(rs.getString("TtTime"));
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
		return result;
	}

	public static void insert(String TtId, String MovTitle, String ThtId, String ScrId, String TtTime) {
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;

		String preQuery = "INSERT INTO TimeTables(TtId, MovTitle, ThtId, ScrId, TtTime) VALUES(?,?,?,?,?)";
		try {
			conn = ConnectSetting.getConnection();
			pstmt = conn.prepareStatement(preQuery);
			pstmt.setString(1, TtId);
			pstmt.setString(2, MovTitle);
			pstmt.setString(3, ThtId);
			pstmt.setString(4, ScrId);
			pstmt.setString(5, TtTime);
			pstmt.executeUpdate();
			System.out.println("INSERT성공: " + TtId);
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

	}
	public static boolean EqualTime(String thtId,String scrId, String movtitle, String ttTime) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String result = null;
		String SQL = "SELECT thtId FROM timetables WHERE thtId = ? and scrId =? and movtitle =? and ttTime = ?";
		try {
			conn = ConnectSetting.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, thtId);
			pstmt.setString(1, scrId);
			pstmt.setString(1, movtitle);
			pstmt.setString(1, ttTime);
			
			rs = pstmt.executeQuery();
			if(rs.next())
				result = rs.getString("thtId");
			else
				result = "";

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
		if (thtId.equals(result))
			return true;
		else
			return false;
	}
	public static void delete(String thtId,String scrId, String movtitle, String ttTime)
	{
		PreparedStatement pstmt = null;
		Connection conn = null;
		String SQL = "DELETE FROM timetables WHERE thtId = ? and scrId =? and movtitle =? and ttTime = ?";

		try {
			conn = ConnectSetting.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, thtId);
			pstmt.setString(1, scrId);
			pstmt.setString(1, movtitle);
			pstmt.setString(1, ttTime);
			pstmt.executeUpdate();
			System.out.println("DELETE성공 ");
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