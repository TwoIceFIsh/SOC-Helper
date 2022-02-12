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

	public int insert_log(String dateToStr, String ipAddress, int count1, String string) {
		try {
			String jdbcUrl = "jdbc:mysql://localhost:3306/ioc?useUnicode=true&characterEncoding=utf8";
			String dbId = "root";
			String dbPass = "!Hg1373002934";

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
			System.out.println("제대로 연결되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		int n = 0;
		int no = 0;
		String query = "INSERT INTO log values(?,?)";
		String query2 = "SELECT MAX(no) FROM log";
		String query3 = "SELECT address FROM site_status WHERE no = 1";
		String address = "";

		try {

			pstm = conn.prepareStatement(query2);
			resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				no = resultSet.getInt(1);

			}

			pstm = conn.prepareStatement(query3);
			resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				address = resultSet.getString(1);
			}

			pstm = conn.prepareStatement(query);
			pstm.setInt(1, no + 1);

			String result = "";
			if (address.equals("DLZ1160@s-oil.com"))
				result = "보안관제팀";
			if (address.equals("sungwoo.kwon@s-oil.com"))
				result = "부장님";
			if (address.equals("jsh0119@s-oil.com"))
				result = "승환";
			if (address.equals("kmh0816@s-oil.com"))
				result = "명훈";
			if (address.equals("bh.lee@s-oil.com"))
				result = "병호";
			if (address.equals("ksm0117@s-oil.com"))
				result = "서임ㄴ";
			if (address.equals("lyj0409@s-oil.com"))
				result = "예지";
			if (address.equals("khw1205@s-oil.com"))
				result = "형욱";

			if (string.equals("CVE"))
				pstm.setString(2,
						dateToStr + " : " + ipAddress + "님께서 CVE 정보 등록을 요청했습니다.(" + count1 + "건) 수신 : " + result);
			if (string.equals("IOC"))
				pstm.setString(2,
						dateToStr + " : " + ipAddress + "님께서 IOC 정보 등록을 요청했습니다.(" + count1 + "건) 수신 : " + result);

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
			String dbPass = "!Hg1373002934";

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn2 = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
			// System.out.println("제대로 연결되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		String query2 = "SELECT * FROM log  ORDER BY no DESC limit 10";
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

			log = new String[nono+1];

			pstm2 = conn2.prepareStatement(query2);
			resultSet2 = pstm2.executeQuery();

			while (resultSet2.next()) {
				// no[count] = Integer.toString(resultSet2.getInt(1));
				log[count] = resultSet2.getString(2);
				System.out.println("log[count] " + log[count]);
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
