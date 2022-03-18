package site;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import user.userDTO;

public class siteDAO {
	public int[] moduleStatusCheck() {

		int n = 0;
		int[] module_Status = { 0, 0, 0, 0, 0 };
		ResultSet rs = null;

		Connection conn = null;
		PreparedStatement pstm = null;

		try {
			String jdbcUrl = "jdbc:mysql://localhost:3306/ioc?useUnicode=true&characterEncoding=utf8";
			String dbId = "root";
			String dbPass = "Dlqudgh1!";

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			int no = 0;
			boolean s = false;
			boolean ss = false;

			// 계정에 따른 CODE 존재여부 확인
			String test = "SELECT no, c FROM programs limit 5";
			pstm = conn.prepareStatement(test);
			rs = pstm.executeQuery();

			while (rs.next()) {
				module_Status[no] = rs.getInt(2);
				no += 1;
			}

			return module_Status;

		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();

				if (pstm != null)
					pstm.close();

				if (conn != null)
					conn.close();

			} catch (Exception e) {
			}
		}

		return module_Status;

	}

	public ArrayList<siteDTO> getMail() {
		int no = 0;
		ResultSet rs = null;

		ArrayList<siteDTO> out = new ArrayList<siteDTO>();
		Connection conn = null;
		PreparedStatement pstm = null;

		try {
			String jdbcUrl = "jdbc:mysql://localhost:3306/ioc?useUnicode=true&characterEncoding=utf8";
			String dbId = "root";
			String dbPass = "Dlqudgh1!";

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			// 계정에 따른 CODE 존재여부 확인
			String test = "SELECT no, a,b,c FROM user ";
			System.out.println("Email Data 조회");
			pstm = conn.prepareStatement(test);
			rs = pstm.executeQuery();

			while (rs.next()) {
				siteDTO site = new siteDTO();
				site.setNo(rs.getInt(1));
				site.setEmail(rs.getString(2));
				site.setPw(rs.getString(3));
				site.setName(rs.getString(4));
				out.add(site);

			}

			return out;

		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();

				if (pstm != null)
					pstm.close();

				if (conn != null)
					conn.close();

			} catch (Exception e) {
			}
		}

		return out;
	}

	public String sendMessage(String email, String message) {
		// TODO Auto-generated method stub
		int n = 0;

		ResultSet rs = null;

		Connection conn = null;
		PreparedStatement pstm = null;

		try {
			String jdbcUrl = "jdbc:mysql://localhost:3306/ioc?useUnicode=true&characterEncoding=utf8";
			String dbId = "root";
			String dbPass = "Dlqudgh1!";

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String name = "";
			boolean s = false;
			int no1 = 0;

			// 이름가져옴
			String test = "SELECT * FROM user WHERE a = ?";

			pstm = conn.prepareStatement(test);
			pstm.setString(1, email);
			rs = pstm.executeQuery();

			while (rs.next()) {
				name = rs.getString(4);
			}

			// 메시지번호가져옴
			String test1 = "SELECT * FROM messages";
			pstm = conn.prepareStatement(test1);
			rs = pstm.executeQuery();

			while (rs.next()) {
				no1 = rs.getInt(1);
			}

			// 메시지 입력
			// no , date, message, who
			String sqlqq = "INSERT INTO messages VALUES(?,?,?,?)";
			pstm = conn.prepareStatement(sqlqq);

			// 현재 날짜/시간
			LocalDateTime now = LocalDateTime.now();
			String formatedNow = now.format(DateTimeFormatter.ofPattern("MM/dd HH:mm:ss"));

			pstm.setInt(1, no1 + 1);
			pstm.setString(2, formatedNow);

			if (message.equals("")) {
				pstm.setString(3, name);
			} else {
				pstm.setString(3, message);
			}

			pstm.setString(4, name);
			pstm.executeUpdate();

			return "[" + formatedNow + "] " + name + " : " + message;

		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();

				if (pstm != null)
					pstm.close();

				if (conn != null)
					conn.close();

			} catch (Exception e) {
			}
		}
		return "";

	}

	public String getMessage() {
		// TODO Auto-generated method stub
		int n = 0;

		ResultSet rs = null;

		Connection conn = null;
		PreparedStatement pstm = null;

		try {
			String jdbcUrl = "jdbc:mysql://localhost:3306/ioc?useUnicode=true&characterEncoding=utf8";
			String dbId = "root";
			String dbPass = "Dlqudgh1!";

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			String formatedNow = "";
			String name = "";
			String message = "";
			String all = "";
			// message 가져옴
			String test1 = "select * from (SELECT * FROM messages ORDER BY no DESC) as Q ORDER BY no asc";
			pstm = conn.prepareStatement(test1);
			rs = pstm.executeQuery();

			while (rs.next()) {
				formatedNow = rs.getString(2);
				name = rs.getString(4);
				message = rs.getString(3);
				all = all + "[" + formatedNow + "] " + name + " : " + message + "<br>";
			}

			return all;

		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();

				if (pstm != null)
					pstm.close();

				if (conn != null)
					conn.close();

			} catch (Exception e) {
			}
		}
		return "";
	}

}
