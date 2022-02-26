package site;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
			String dbPass = "!Hg1373002934";

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

	public int managerCheck() {
		int no = 0;
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

			// 계정에 따른 CODE 존재여부 확인
			String test = "SELECT no, b FROM programs WHERE no = '5' ";
			pstm = conn.prepareStatement(test);
			rs = pstm.executeQuery();

			while (rs.next()) {
				no = rs.getInt(2);
			}

			return no;

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
