package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
