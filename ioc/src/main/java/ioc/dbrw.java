package ioc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class dbrw {
	Connection conn = null;
	PreparedStatement pstm = null;
	ResultSet resultSet = null;
	Connection conn2 = null;
	PreparedStatement pstm2 = null;
	ResultSet resultSet2 = null;

	public dbrw() {

	}

	//////////////////////////////////////////////////////////////////////////
	// CVE 정보수집 readFile > writeLine
	//////////////////////////////////////////////////////////////////////////
	public int readFile(String location, String fileName, String ipAddress) {
		File file = new File(location + "\\" + fileName);
		System.out.println("readData " + location + fileName);
		if (file.exists()) {
			BufferedReader inFile = null;
			try {
				inFile = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
			String sLine = null;
			int no = 0;
			try {
				while ((sLine = inFile.readLine()) != null) {

					// 파일을 한줄씩 읽어서 DB에 writeLine 한다
					no = writeLine(sLine, ipAddress);

				}
				return no;
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return 0;
	}

	// 파일을 한줄씩 읽어서 DB에 writeLine 한다
	public int writeLine(String cve, String ipAddress) {

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

		System.out.println("pathWrite " + cve);
		cve = cve.trim();
		int n = 0;
		int no = 0;
		String query = "INSERT INTO cve values(?,?,?)";
		String query2 = "SELECT MAX(no) FROM cve";

		try {

			pstm = conn.prepareStatement(query2);
			resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				no = resultSet.getInt(1);

			}

			pstm = conn.prepareStatement(query);
			pstm.setInt(1, no + 1);
			pstm.setString(2, cve);
			pstm.setInt(3, 0);
			// pstm.setString(4, "");

			n = pstm.executeUpdate();

			if (n == 1) {
				System.out.println(no + " " + cve + " insert Success");
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

	//////////////////////////////////////////////////////////////////////////
	// IOC 정보수집 readFile2 > writeLine2
	//////////////////////////////////////////////////////////////////////////
	public int readFile2(String location, String fileName, String ipAddress) {
		File file = new File(location + "\\" + fileName);

		if (file.exists()) {
			BufferedReader inFile = null;
			try {
				inFile = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
			String sLine = null;
			int no = 0;;
			// 파일을 한줄씩 읽어서 DB에 writeLine 한다
			try {
				String filePath = location + "\\" + fileName;
				while ((sLine = inFile.readLine()) != null) {

					no = writeLine2(sLine, filePath, returnType(sLine));

				}
				return no;
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return 0;
	}

	// 파일을 한줄씩 읽어서 DB에 writeLine 한다
	public int writeLine2(String DATA, String filePath, String TYPE) {

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

		DATA = DATA.trim();
		int n = 0;
		int no = 0;
		String query = "INSERT INTO work_place values(?,?,?,?,?,?,?,?)";
		String query2 = "SELECT MAX(no) FROM work_place";

		try {

			// 최고 no+1 값을 조회 하여 저장

			pstm2 = conn2.prepareStatement(query2);
			resultSet2 = pstm2.executeQuery();

			while (resultSet2.next()) {
				no = resultSet2.getInt(1);

			}

			pstm2 = conn2.prepareStatement(query);
			System.out.println(TYPE + " :  " + DATA);
			if (TYPE.equals("MD5")) {
				pstm2.setInt(1, no + 1);
				pstm2.setString(2, DATA);
				pstm2.setString(3, "X");
				pstm2.setString(4, "X");
				pstm2.setString(5, "X");
				pstm2.setString(6, "X");
				pstm2.setInt(7, 0);
				pstm2.setString(8, filePath);
			}
			if (TYPE.equals("SHA256")) {

				pstm2.setInt(1, no + 1);
				pstm2.setString(2, "X");
				pstm2.setString(3, DATA);
				pstm2.setString(4, "X");
				pstm2.setString(5, "X");
				pstm2.setString(6, "X");
				pstm2.setInt(7, 0);
				pstm2.setString(8, filePath);
			}
			if (TYPE.equals("SHA1")) {
				pstm2.setInt(1, no + 1);
				pstm2.setString(2, "X");
				pstm2.setString(3, "X");
				pstm2.setString(4, DATA);
				pstm2.setString(5, "X");
				pstm2.setString(6, "X");
				pstm2.setInt(7, 0);
				pstm2.setString(8, filePath);
			}
			if (TYPE.equals("IP")) {
				pstm2.setInt(1, no + 1);
				pstm2.setString(2, "X");
				pstm2.setString(3, "X");
				pstm2.setString(4, "X");
				pstm2.setString(5, DATA);
				pstm2.setString(6, "X");
				pstm2.setInt(7, 0);
				pstm2.setString(8, filePath);
			}
			if (TYPE.equals("URL")) {
				pstm2.setInt(1, no + 1);
				pstm2.setString(2, "X");
				pstm2.setString(3, "X");
				pstm2.setString(4, "X");
				pstm2.setString(5, "X");
				pstm2.setString(6, DATA);
				pstm2.setInt(7, 0);
				pstm2.setString(8, filePath);
			}

			n = pstm2.executeUpdate();

			if (n == 1) {
				System.out.println(TYPE + " :  " + DATA);
				return 1;
			} else {
				System.out.println("insert fail");
			}
		} catch (Exception e) {
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
		return 0;
	}

	//////////////////////////////////////////////////////////////////////////
	// 공통모듈 checkData
	//////////////////////////////////////////////////////////////////////////
	public String returnType(String DATA) {

		DATA = DATA.trim();

		String patternIP = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$";
		if (!DATA.matches("\\.") && !DATA.matches("-")) {
			if (DATA.length() == 64) {

				return "SHA256";
			}
			if (DATA.length() == 40) {

				return "SHA1";
			}
			if (DATA.length() == 32) {

				return "MD5";
			}
		}
		if (Pattern.matches(patternIP, DATA)) {

			return "IP";

		}

		return "URL";
	}

	// 메일발송건
	public int getStatus() {

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
		int mailcount = 0;
		String query2 = "SELECT mailcount FROM site_status";

		try {

			pstm = conn.prepareStatement(query2);
			resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				mailcount = resultSet.getInt(1);

			}

			return mailcount;

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

	// 처리건수
	public int getStatus2() {

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
		int count = 0;
		String query2 = "SELECT count FROM site_status";

		try {

			pstm = conn.prepareStatement(query2);
			resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				count = resultSet.getInt(1);

			}

			return count;

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

	public int getStatus3() {

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
		int mailcount = 0;
		String query2 = "SELECT COUNT(status) FROM cve WHERE status = '0'";

		try {

			pstm = conn.prepareStatement(query2);
			resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				mailcount = resultSet.getInt(1);

			}

			return mailcount;

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

	public int getStatus4() {

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
		int mailcount = 0;
		String query2 = "SELECT COUNT(status) FROM work_place WHERE status = '0'";

		try {

			pstm = conn.prepareStatement(query2);
			resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				mailcount = resultSet.getInt(1);

			}

			return mailcount;

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

}