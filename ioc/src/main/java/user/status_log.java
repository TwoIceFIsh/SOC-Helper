package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class status_log {

	Connection conn = null;
	PreparedStatement pstm = null;
	ResultSet resultSet = null;
	Connection conn2 = null;
	PreparedStatement pstm2 = null;
	ResultSet resultSet2 = null;

	public int insert_log(String dateToStr, String ipAddress, int count1, String string, String mailAddress) {
		try {
			String jdbcUrl = "jdbc:mysql://localhost:3306/ioc?useUnicode=true&characterEncoding=utf8";
			String dbId = "root";
			String dbPass = "Dlqudgh1!";

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
		 
		} catch (Exception e) {
			e.printStackTrace();
		}

		int n = 0;
		int no = 0;
		String query = "INSERT INTO log values(?,?,?,?,?,?)";
		String query2 = "SELECT MAX(no) FROM log";

		try {

			pstm = conn.prepareStatement(query2);
			resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				no = resultSet.getInt(1);

			}
			
			
			String query3 = "SELECT no FROM jobq WHERE ipip = ? AND time = ?";
			pstm = conn.prepareStatement(query3);
			pstm.setString(1, ipAddress);
			pstm.setString(2, dateToStr);

			resultSet = pstm.executeQuery();

			int k = 0;
			while (resultSet.next()) {
				k = resultSet.getInt("no");
			}
			
			
			
			
			
			
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, no+1);

			 
			if (string.equals("CVE")) {
				pstm.setString(2, dateToStr + " : 작업[" + Integer.toString(k) + "] : " + ipAddress
						+ "님께서 CVE 작업 요청 [" + count1 + "건] ");

			}
			if (string.equals("IOC")) {
				pstm.setString(2, dateToStr + " : 작업[" + Integer.toString(k) + "] : " + ipAddress
						+ "님께서 IOC 작업 요청 [" + count1 + "건] ");
		}

			pstm.setString(3, ipAddress);
			pstm.setString(4, mailAddress);
			pstm.setInt(5, count1);
			pstm.setString(6, dateToStr);

			n = pstm.executeUpdate();

			if (n == 1) {

				return 1;
			} else {
				System.out.println("insert fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();

				if (pstm != null)
					pstm.close();

				if (conn != null)
					conn.close();

			} catch (Exception e) {
			}
		}
		return 0;
	}

	public String[] loglog() {
		String[] log = null;
		try {
			String jdbcUrl = "jdbc:mysql://localhost:3306/ioc?useUnicode=true&characterEncoding=utf8";
			String dbId = "root";
			String dbPass = "Dlqudgh1!";

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn2 = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
			 
		} catch (Exception e) {
			e.printStackTrace();
		}

		String query2 = "SELECT no, text FROM (SELECT no, text FROM log  ORDER BY no desc limit 10) as logT ORDER BY NO ASC";
		String query3 = "SELECT no FROM log limit 10";

		try {

			// 최고 no+1 값을 조회 하여 저장

			int count = 0;
			int nono = 0;
			pstm2 = conn2.prepareStatement(query3);
			resultSet2 = pstm2.executeQuery();

			while (resultSet2.next()) {

				nono = resultSet2.getInt(1);

			}

			log = new String[nono + 1];

			pstm2 = conn2.prepareStatement(query2);
			resultSet2 = pstm2.executeQuery();

			while (resultSet2.next()) {
				// no[count] = Integer.toString(resultSet2.getInt(1));
				log[count] = resultSet2.getString(2);
				//System.out.println("log[count] " + log[count]);
				count += 1;
			}

			log[count] = Integer.toString(nono);

			return log;

		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet2 != null)
					resultSet2.close();

				if (pstm2 != null)
					pstm2.close();

				if (conn2 != null)
					conn2.close();

			} catch (Exception e) {
			}
		}
		return log;
	}
}
