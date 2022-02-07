package ioc;

import java.sql.*;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class dbrw {
	Connection conn = null;
	PreparedStatement pstm = null;
	ResultSet resultSet = null;

	public void dbrw() {

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

	}

	// 파일업로드 후 호출
	public void pathWrite(String fileName, String Path) {
		
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
		
		System.out.println("pathWrite");
		int n = 0;
		int no = 0;
		String query = "INSERT INTO upload values(?,?,?,?,?)";
		String query2 = "SELECT MAX(no) FROM upload";

		try {

			// 최고 no+1r값을 조회 하여 저장

			pstm = conn.prepareStatement(query2);
			resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				no = resultSet.getInt(1);

			}

			LocalDate now = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
			String formatedNow = now.format(formatter);

			pstm = conn.prepareStatement(query);
			pstm.setInt(1, no + 1);
			pstm.setString(2, fileName);
			pstm.setString(3, Path);
			pstm.setString(4, formatedNow);
			pstm.setString(5, "admin");

			n = pstm.executeUpdate();
			if (n == 1) {
				System.out.println(no +" "+ fileName +" insert Success");
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
	}

	public void fileDB(String fileName, String NAME, String DATE) {
		int n = 0;
		String query = "????";

		try {
			pstm = conn.prepareStatement(query);

			pstm.setString(1, "a");

			n = pstm.executeUpdate();
			if (n == 1) {
				System.out.println("insert Success");
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
	}

	public void writeDB(String MD5, String SHA1, String SHA256, String STATUS) {
		int n = 0;
		String query = "????";

		try {
			pstm = conn.prepareStatement(query);

			pstm.setString(1, "a");

			n = pstm.executeUpdate();
			if (n == 1) {
				System.out.println("insert Success");
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

	}

	public void moveDB(String MD5, String SHA1, String SHA256, String STATUS) {
		int n = 0;
		String query = "????";

		try {
			pstm = conn.prepareStatement(query);

			pstm.setString(1, "a");

			n = pstm.executeUpdate();
			if (n == 1) {
				System.out.println("insert Success");
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
	}

	public void statusDB(String MD5, String SHA1, String SHA256, String STATUS) {
		int n = 0;
		String query = "????";

		try {
			pstm = conn.prepareStatement(query);

			pstm.setString(1, "a");

			n = pstm.executeUpdate();
			if (n == 1) {
				System.out.println("insert Success");
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
	}
}