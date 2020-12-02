package com.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.setting.ConnectSetting;

public class SeatsDAO {
	public static ArrayList<SeatsDTO> selectAll() {
		ArrayList<SeatsDTO> dtos = new ArrayList<SeatsDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM Seats";

		try {
			conn = ConnectSetting.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				String SeatId = rs.getString("SeatId");
				String ThtId = rs.getString("ThtId");
				String ScrId = rs.getString("ScrId");

				SeatsDTO dto = new SeatsDTO(SeatId, ThtId, ScrId);
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

	public static ArrayList<SeatsDTO> insert(String SeatId, String ThtId, String ScrId) {
		ArrayList<SeatsDTO> dtos = new ArrayList<SeatsDTO>();
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;

		String preQuery = "INSERT INTO Seats(SeatId,ThtId,ScrId) VALUES(?,?,?)";
		try {
			conn = ConnectSetting.getConnection();
			pstmt = conn.prepareStatement(preQuery);
			pstmt.setString(1, SeatId);
			pstmt.setString(2, ThtId);
			pstmt.setString(3, ScrId);
			pstmt.executeUpdate();
			System.out.println("INSERT성공: " + SeatId);
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

}
