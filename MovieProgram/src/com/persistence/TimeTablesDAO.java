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

	public ArrayList<TimeTablesDTO> insert(String TtId, String MovTitle, String ThtId, String ScrId, String TtTime) {
		ArrayList<TimeTablesDTO> dtos = new ArrayList<TimeTablesDTO>();

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