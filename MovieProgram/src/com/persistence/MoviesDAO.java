package com.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MoviesDAO {
	public ArrayList<MoviesDTO> selectAll() {
		ArrayList<MoviesDTO> dtos = new ArrayList<MoviesDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM Movies";
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				String MovTitle = rs.getString("MovTitle");
				String MovDirector = rs.getString("MovDirector");
				String MovActor = rs.getString("MovActor");
				String MovGenre = rs.getString("MovGenre");
				String MovStory = rs.getString("MovStory");
				String MovOpenDate = rs.getString("MovOpenDate");
				String MovView = rs.getString("MovView");
				String MovRuntime = rs.getString("MovRuntime");
				String MovRating = rs.getString("MovRating");
				String MovTrailer = rs.getString("MovTrailer");

				MoviesDTO dto = new MoviesDTO(MovTitle, MovDirector, MovActor, MovGenre, MovStory, MovOpenDate, MovView,
						MovRuntime, MovRating, MovTrailer);
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

	public ArrayList<MoviesDTO> selectTitle() {
		ArrayList<MoviesDTO> dtos = new ArrayList<MoviesDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT MovTitle FROM Movies";
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				String MovTitle = rs.getString("MovTitle");
				MoviesDTO dto = new MoviesDTO(MovTitle);
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

	public static  ArrayList<MoviesDTO> insert(String MovTitle, String MovDirector, String MovActor, String MovGenre,
			String MovStory, String MovOpenDate, String MovView, String MovRuntime, String MovRating,
			String MovTrailer) {
		ArrayList<MoviesDTO> dtos = new ArrayList<MoviesDTO>();

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String preQuery = "INSERT INTO Movies(MovTitle,MovDirector,MovActor,MovGenre,MovStory,MovOpenDate,MovView,MovRuntime,MovRating,MovTrailer) VALUES(?,?,?,?,?,?,?,?,?,?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(preQuery);
			pstmt.setString(1, MovTitle);
			pstmt.setString(2, MovDirector);
			pstmt.setString(3, MovActor);
			pstmt.setString(4, MovGenre);
			pstmt.setString(5, MovStory);
			pstmt.setString(6, MovOpenDate);
			pstmt.setString(7, MovView);
			pstmt.setString(8, MovRuntime);
			pstmt.setString(9, MovRating);
			pstmt.setString(10, MovTrailer);
			pstmt.executeUpdate();
			System.out.println("INSERT성공: " + MovTitle);
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

	public ArrayList<MoviesDTO> update(String title) {
		ArrayList<MoviesDTO> dtos = new ArrayList<MoviesDTO>();
		PreparedStatement pstmt = null;
		Connection conn = null;
		String SQL = "UPDATE Movies SET MovView=? WHERE MovTitle=?";
		String SQL2 = "SELECT COUNT(*) FROM Tickets WHERE MovTitle=?";
		String resultView = null;
		String resultRating = null;

		ResultSet rs = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(SQL2);
			pstmt.setString(1, title);
			rs = pstmt.executeQuery();
			rs.next();
			resultView = rs.getString(1);
			
			pstmt = null;
			rs = null;


			pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, resultView);
			pstmt.setString(2, title);

			pstmt.executeUpdate();

		} catch (SQLException sqle) {
			System.out.println("UPDATE_BALNCE문에서 예외 발생");
			if(conn!=null)try{conn.rollback();}catch(SQLException sqle1) {}
			sqle.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}

		return dtos;
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			String user = "MOVIE";
			String pw = "123";
			String url = "jdbc:oracle:thin:@localhost:1521:xe";

			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pw);

			System.out.println("Database에 연결되었습니다.\n");

		} catch (ClassNotFoundException cnfe) {
			System.out.println("DB 드라이버 로딩 실패 :" + cnfe.toString());
		} catch (SQLException sqle) {
			System.out.println("DB 접속실패 : " + sqle.toString());
		} catch (Exception e) {
			System.out.println("Unkonwn error");
			e.printStackTrace();
		}
		return conn;
	}
}
