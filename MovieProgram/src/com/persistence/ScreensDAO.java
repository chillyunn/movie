package com.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.oracle.DBConnection;

public class ScreensDAO {

	public ArrayList<ScreensDTO> selectAll() {
		ArrayList<ScreensDTO> dtos = new ArrayList<ScreensDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM Screens";
		try {
			conn = DBConnection.getConnection();
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
			conn = DBConnection.getConnection();
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

	public ArrayList<ScreensDTO> insert(String ScrId, String ThtId, String ScrType, String ScrPremium) {
		ArrayList<ScreensDTO> dtos = new ArrayList<ScreensDTO>();

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;

		String preQuery = "INSERT INTO Screens(ScrId, ThtId, ScrType, ScrPremium) VALUES(?,?,?,?)";
		try {
			conn = DBConnection.getConnection();
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

}
