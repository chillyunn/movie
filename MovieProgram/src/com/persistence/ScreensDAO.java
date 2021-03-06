package com.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.setting.ConnectSetting;

public class ScreensDAO {

	public ArrayList<ScreensDTO> selectAll() {
		ArrayList<ScreensDTO> dtos = new ArrayList<ScreensDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM Screens";
		try {
			conn = ConnectSetting.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				String ScrId = rs.getString("ScrId");
				String ThtId = rs.getString("ThtId");
				String ScrType = rs.getString("ScrType");
				String ScrPremium = rs.getString("ScrPremium");

				ScreensDTO dto = new ScreensDTO(ScrId, ThtId, ScrType, ScrPremium);
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

	public ArrayList<ScreensDTO> selectScrIdThtId() {
		ArrayList<ScreensDTO> dtos = new ArrayList<ScreensDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT ScrId, ThtId FROM Screens";
		try {
			conn = ConnectSetting.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				String ScrId = rs.getString("ScrId");
				String ThtId = rs.getString("ThtId");

				ScreensDTO dto = new ScreensDTO(ScrId, ThtId);
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

	public static String[] selectId(String thtId) {
		ArrayList<String> list = new ArrayList<String>();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		Connection conn = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String ScrId = null;
		int screenCount = 0;
		String[] result = new String[list.size()];
		String SQL1 ="SELECT thtAddress FROM Theaters WHERE ThtId = ?";
		String SQL = "SELECT ScrId FROM Screens WHERE ThtId = ?";
		String SQL2 = "SELECT count(*) FROM seats where scrid = ? and thtId = ?";
		try {
			conn = ConnectSetting.getConnection(); //SQL1 구문 실행
			
			pstmt1 = conn.prepareStatement(SQL1);
			pstmt1.setString(1, thtId);
			rs1 = pstmt1.executeQuery();
			if (rs1.next()) {//ScrId 첫 값 얻기
				String thtAddress = rs1.getString("thtAddress");
				list.add(thtAddress);
			}
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, thtId);
			rs = pstmt.executeQuery();
			
			if (rs.next()) { //ScrId 첫 값 얻기
				ScrId = rs.getString("ScrId");
			}
			pstmt2 = conn.prepareStatement(SQL2); //SQL2 구문 실행
			pstmt2.setString(1, ScrId);
			pstmt2.setString(2, thtId);
			rs2 = pstmt2.executeQuery();
			if (rs2.next()) {
				screenCount = rs2.getInt(1);
				list.add(ScrId +"/"+ screenCount); //상영관ID+좌석수 저장
				//ScrId 첫값얻은후 첫 상영관+좌석수 리스트에 저장
			while (true) {
				if (rs.next()) {
					ScrId = rs.getString("ScrId");
				} else
					break;

				if (rs2.next()) {
					screenCount = rs2.getInt(1);
				
				}
				list.add(ScrId +"/"+ screenCount); //상영관ID+좌석수 저장
			}//sql문 끝날때까지 상영관+좌석수 리스트에 저장
			result = list.toArray(result);
			}
			} catch (SQLException sqle) {
			System.out.println("SELECT문에서 예외 발생");
			sqle.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (rs1 != null) {
					rs.close();
				}
				if (rs2 != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (pstmt1 != null) {
					pstmt.close();
				}
				if (pstmt2 != null) {
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

	public static ArrayList<ScreensDTO> insert(String ScrId, String ThtId, String ScrType, String ScrPremium) {
		ArrayList<ScreensDTO> dtos = new ArrayList<ScreensDTO>();

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;

		String preQuery = "INSERT INTO Screens(ScrId, ThtId, ScrType, ScrPremium) VALUES(?,?,?,?)";
		try {
			conn = ConnectSetting.getConnection();
			pstmt = conn.prepareStatement(preQuery);
			pstmt.setString(1, ScrId);
			pstmt.setString(2, ThtId);
			pstmt.setString(3, ScrType);
			pstmt.setString(4, ScrPremium);
			pstmt.executeUpdate();
			System.out.println("INSERT성공: " + ScrId);
		} catch (SQLException sqle) {
			System.out.println("SELECT문에서 예외 발생");
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
		String ScrId = null;
		String SQL = "SELECT ScrId FROM Theaters WHERE ScrId = ?";
		try {
			conn = ConnectSetting.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next())
				ScrId = rs.getString("ScrId");
			else
				ScrId = "";

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
		if (id.equals(ScrId))
			return true;
		else
			return false;
	}
	public static void delete(String scrId)
	{
		PreparedStatement pstmt = null;
		Connection conn = null;
		String SQL = "DELETE FROM Screens WHERE scrId=?";

		try {
			conn = ConnectSetting.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, scrId);
			pstmt.executeUpdate();
			System.out.println("DELETE성공: " + scrId);
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
