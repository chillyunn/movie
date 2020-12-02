package com.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.setting.ConnectSetting;

public class MoviesDAO {
	public ArrayList<MoviesDTO> selectAll() {
		ArrayList<MoviesDTO> dtos = new ArrayList<MoviesDTO>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM Movies";
		try {
			conn = ConnectSetting.getConnection();
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
	public static String[] selectMov(String title) {
		ArrayList<String> list = new ArrayList<String>();
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String[]result = new String[list.size()];
		
		String SQL = "SELECT * FROM Movies where movtitle = ?";
		try {
			conn = ConnectSetting.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, title);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("movTitle"));
				list.add(rs.getString("MovDirector"));
				list.add(rs.getString("MovActor"));
				list.add(rs.getString("MovGenre"));
				list.add(rs.getString("MovStory"));
				list.add(rs.getString("MovOpenDate"));
				list.add(rs.getString("MovView"));
				list.add(rs.getString("MovRuntime"));
				list.add(rs.getString("MovRating"));
				list.add(rs.getString("MovTrailer"));
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

	public static String[] selectTitle() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String[]result = new String[list.size()];
		
		String SQL = "SELECT MovTitle FROM Movies";
		try {
			conn = ConnectSetting.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				list.add(rs.getString("movTitle"));
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

	public static ArrayList<MoviesDTO> insert(String MovTitle, String MovDirector, String MovActor, String MovGenre,
			String MovStory, String MovOpenDate, String MovView, String MovRuntime, String MovRating,
			String MovTrailer) {
		ArrayList<MoviesDTO> dtos = new ArrayList<MoviesDTO>();

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String preQuery = "INSERT INTO Movies(MovTitle,MovDirector,MovActor,MovGenre,MovStory,MovOpenDate,MovView,MovRuntime,MovRating,MovTrailer) VALUES(?,?,?,?,?,?,?,?,?,?)";
		try {
			conn = ConnectSetting.getConnection();
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
		String SQL = "UPDATE Movies SET MovView=?, MovRating=? WHERE MovTitle=?";
		String SQL2 = "SELECT COUNT(*) FROM Tickets WHERE MovTitle=?";

		String resultView = null;
		String resultRating = null;

		ResultSet rs = null;
		try {
			conn = ConnectSetting.getConnection();
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(SQL2);
			pstmt.setString(1, title);
			rs = pstmt.executeQuery();
			rs.next();
			resultView = rs.getString(1);

			pstmt = null;
			rs = null;

			rs = null;

			pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, resultView);
			pstmt.setString(2, resultRating);
			pstmt.setString(3, title);

			pstmt.executeUpdate();

		} catch (SQLException sqle) {
			System.out.println("UPDATE_BALNCE문에서 예외 발생");
			if (conn != null)
				try {
					conn.rollback();
				} catch (SQLException sqle1) {
				}
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
	public static boolean EqualTitle(String title) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String movTitle = null;
		String SQL = "SELECT movTitle FROM Theaters WHERE movTitle = ?";
		try {
			conn = ConnectSetting.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, title);
			rs = pstmt.executeQuery();
			if(rs.next())
				movTitle = rs.getString("movTitle");
			else
				movTitle = "";

		} catch (SQLException sqle) {
			System.out.println("equalTitle문에서 예외 발생");
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
		if (movTitle.equals(title))
			return true;
		else
			return false;
	}
	public static void delete(String movTitle)
	{
		PreparedStatement pstmt = null;
		Connection conn = null;
		String SQL = "DELETE FROM Movies WHERE movTitle=?";

		try {
			conn = ConnectSetting.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, movTitle);
			pstmt.executeUpdate();
			System.out.println("DELETE성공: " + movTitle);
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
