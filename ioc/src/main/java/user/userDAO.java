package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class userDAO {

	public static boolean login(userDTO user) {

		ResultSet rs = null;
		String sql = "SELECT exists (select * FROM user WHERE a= '" + user.getId() + "' AND b = '" + user.getPw()
				+ "') As Q";

		System.out.println(sql);
		Connection conn = null;
		PreparedStatement pstm = null;

		try {
			String jdbcUrl = "jdbc:mysql://localhost:3306/ioc?useUnicode=true&characterEncoding=utf8";
			String dbId = "root";
			String dbPass = "!Hg1373002934";

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int n = 0;
		boolean no = false;
		try {

			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			if (rs != null) {
				rs.next();
				System.out.println("OK");
				no = rs.getBoolean(1);
				System.out.println(no);
				return no;
			} else {
				System.out.println("FAIL");
			}
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
		return no;
	}

	public static boolean getEmail(String email) {

		ResultSet rs = null;
		String sql = "SELECT exists (select * FROM user WHERE a= '" + email + "') As Q";

		System.out.println(sql);
		Connection conn = null;
		PreparedStatement pstm = null;

		try {
			String jdbcUrl = "jdbc:mysql://localhost:3306/ioc?useUnicode=true&characterEncoding=utf8";
			String dbId = "root";
			String dbPass = "!Hg1373002934";

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int n = 0;
		boolean no = false;
		try {

			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			if (rs != null) {
				rs.next();
				System.out.println("OK");
				no = rs.getBoolean(1);
				System.out.println(no);
				return no;
			} else {
				System.out.println("FAIL");
			}
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
		return no;
	}

	// 이메일계정이 없어서 코드를 삽입
	public static int setCode(String email) {
		int n = 0;

		Random random = null;
		int code = (int)((Math.random()*10000));

		ResultSet rs = null;
		 
		Connection conn = null;
		PreparedStatement pstm = null;

		try {
			String jdbcUrl = "jdbc:mysql://localhost:3306/ioc?useUnicode=true&characterEncoding=utf8";
			String dbId = "root";
			String dbPass = "!Hg1373002934";

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			int no = 0;
			int s = 0;
			
			
			String querya = "select no, a,b,c from code WHERE a = ? AND c = '0' limit 1";
			pstm = conn.prepareStatement(querya);   
			pstm.setString(1, email);
            rs = pstm.executeQuery();
            
            
            rs.next();
           	no = rs.getInt(4);
           	if(s == 0) {
           		System.out.println("인증코드가 이미 발송되었습니다.");
           		return 4; 
           		
           	}
						
			
			String query = "select no from code ORDER BY no desc limit 1";
			pstm = conn.prepareStatement(query);        
            rs = pstm.executeQuery();
            
            rs.next();
           	no = rs.getInt(1);
           	System.out.println(no);
           
           	// no , email, code, satus
           	String sql = "INSERT INTO code VALUES(?,?,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, no+1);
			pstm.setString(2, email);
			pstm.setString(3, Integer.toString(code));
			pstm.setString(4, "0");
 			pstm.executeUpdate();
		 
			return 1;
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

		////////////////////////////////////////////////////////////
		try {
			String jdbcUrl = "jdbc:mysql://localhost:3306/ioc?useUnicode=true&characterEncoding=utf8";
			String dbId = "root";
			String dbPass = "!Hg1373002934";

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
}
