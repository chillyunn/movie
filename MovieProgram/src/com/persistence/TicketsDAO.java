package com.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.setting.ConnectSetting;

public class TicketsDAO {
	public ArrayList<TicketsDTO> selectAll() {
		ArrayList<TicketsDTO> dtos = new ArrayList<TicketsDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM Tickets";
		try {
			conn = ConnectSetting.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				String TicId = rs.getString("TicId");
				String ScrId = rs.getString("ScrId");
				String ThtId = rs.getString("ThtId");
				String SeatId = rs.getString("SeatId");
				String GusId = rs.getString("GusId");
				String TtId = rs.getString("TtId");
				String MovTitle = rs.getString("MovTitle");

				TicketsDTO dto = new TicketsDTO(TicId, ScrId, ThtId, SeatId, GusId, TtId, MovTitle);
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

	public ArrayList<TicketsDTO> insert(String TicId, String ScrId, String ThtId, String SeatId, String GusId,
			String TtId, String MovTitle) {
		ArrayList<TicketsDTO> dtos = new ArrayList<TicketsDTO>();

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;

		String preQuery = "INSERT INTO Tickets(TicId, ScrId, ThtId, SeatId, GusId, TtId) VALUES(?,?,?,?,?,?,?)";
		try {
			conn = ConnectSetting.getConnection();
			pstmt = conn.prepareStatement(preQuery);
			pstmt.setString(1, TicId);
			pstmt.setString(2, ScrId);
			pstmt.setString(3, ThtId);
			pstmt.setString(4, SeatId);
			pstmt.setString(5, GusId);
			pstmt.setString(6, TtId);
			pstmt.setString(7, MovTitle);
			pstmt.executeUpdate();
			System.out.println("INSERT성공: " + TicId);
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
