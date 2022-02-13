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
			String dbPass = "!Hg1373002934";

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
			System.out.println("����� ����Ǿ����ϴ�.");
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

			pstm = conn.prepareStatement(query);
			pstm.setInt(1, no + 1);
 
			if (mailAddress.equals("DLZ1160@s-oil.com"))
				mailAddress = "���Ȱ�����";
			if (mailAddress.equals("sungwoo.kwon@s-oil.com"))
				mailAddress = "�����";
			if (mailAddress.equals("jsh0119@s-oil.com"))
				mailAddress = "��ȯ";
			if (mailAddress.equals("kmh0816@s-oil.com"))
				mailAddress = "����";
			if (mailAddress.equals("bh.lee@s-oil.com"))
				mailAddress = "��ȣ";
			if (mailAddress.equals("ksm0117@s-oil.com"))
				mailAddress = "����";
			if (mailAddress.equals("lyj0409@s-oil.com"))
				mailAddress = "����";
			if (mailAddress.equals("khw1205@s-oil.com"))
				mailAddress = "����";

			if (string.equals("CVE")) {
				pstm.setString(2,
						dateToStr + " : " + ipAddress + "�Բ��� CVE ���� ����� ��û�߽��ϴ�.(" + count1 + "��) ���� : " + mailAddress);

			}
			if (string.equals("IOC")) {
				pstm.setString(2,
						dateToStr + " : " + ipAddress + "�Բ��� IOC ���� ����� ��û�߽��ϴ�.(" + count1 + "��) ���� : " + mailAddress);
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
			String dbPass = "!Hg1373002934";

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn2 = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
			// System.out.println("����� ����Ǿ����ϴ�.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		String query2 = "SELECT no, text FROM (SELECT no, text FROM log  ORDER BY no desc limit 10) as logT ORDER BY NO ASC";
		String query3 = "SELECT no FROM log limit 10";

		try {

			// �ְ� no+1 ���� ��ȸ �Ͽ� ����

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