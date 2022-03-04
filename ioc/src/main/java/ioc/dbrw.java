package ioc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Pattern;

import user.status_log;

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
	// CVE Á¤º¸¼öÁý readFile > writeLine
	//////////////////////////////////////////////////////////////////////////
	public int readFile(String location, String fileName, String ipAddress, String dateToStr, String mailAddress) {
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
			int count1 = 0;
			try {
				while ((sLine = inFile.readLine()) != null) {
					sLine = sLine.strip();
					if (sLine.matches(".*[¤¡-¤¾¤¿-¤Ó°¡-ÆR]+.*") || !sLine.matches(".*[CVE]+.*")) {
						sLine = "X"; 
						return 3;
					}

					// ÆÄÀÏÀ» ÇÑÁÙ¾¿ ÀÐ¾î¼­ DB¿¡ writeLine ÇÑ´Ù
					no = writeLine(sLine, ipAddress, dateToStr);
					count1 += 1;
				}

				status_log status_log = new status_log();
				status_log.insert_log(dateToStr, ipAddress, count1, "CVE", mailAddress);

				return no;
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return 0;
	}

	// ÆÄÀÏÀ» ÇÑÁÙ¾¿ ÀÐ¾î¼­ DB¿¡ writeLine ÇÑ´Ù
	public int writeLine(String cve, String ipAddress, String time) {

		try {
			String jdbcUrl = "jdbc:mysql://localhost:3306/ioc?useUnicode=true&characterEncoding=utf8";
			String dbId = "root";
			String dbPass = "!Hg1373002934";

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("pathWrite " + cve);
		cve = cve.trim();
		int n = 0;
		int no = 0;
		String query = "INSERT INTO cve values(?,?,?,?,?)";
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
			pstm.setString(4, ipAddress);
			pstm.setString(5, time);

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
	// IOC Á¤º¸¼öÁý readFile2 > writeLine2
	//////////////////////////////////////////////////////////////////////////
	public int readFile2(String location, String fileName, String ipAddress, String dateToStr, String mailAddress) {
		File file = new File(location + "\\" + fileName);

		if (file.exists()) {
			BufferedReader inFile = null;
			try {
				inFile = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
			String sLine = null;
			int no = 0;
			int count2 = 0;

			// ÆÄÀÏÀ» ÇÑÁÙ¾¿ ÀÐ¾î¼­ DB¿¡ writeLine ÇÑ´Ù
			try {
				String filePath = location + "\\" + fileName;
				while ((sLine = inFile.readLine()) != null) {
					sLine = sLine.strip();
					sLine = sLine.replace("\r", "");
					sLine = sLine.replace("\n", "");
					if (sLine.matches(".*[¤¡-¤¾¤¿-¤Ó°¡-ÆR]+.*") || sLine.equals("")) {
						continue;
					}

					if (sLine.contains("[.]") || sLine.contains("hxxp") || sLine.contains("HASH:")) {
						sLine = sLine.replace("[.]", ".");
						sLine = sLine.replace("hxxp", "http");
						sLine = sLine.replace("HASH:", "");
					}
	 
					no = writeLine2(sLine, filePath, returnType(sLine), ipAddress, dateToStr);
					count2 += 1;
				}

				status_log status_log = new status_log();
				status_log.insert_log(dateToStr, ipAddress, count2, "IOC", mailAddress);

				return no;
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return 0;
	}

	// ÆÄÀÏÀ» ÇÑÁÙ¾¿ ÀÐ¾î¼­ DB¿¡ writeLine ÇÑ´Ù
	public int writeLine2(String DATA, String filePath, String TYPE, String From, String time) {

		try {
			String jdbcUrl = "jdbc:mysql://localhost:3306/ioc?useUnicode=true&characterEncoding=utf8";
			String dbId = "root";
			String dbPass = "!Hg1373002934";

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn2 = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
			// 
		} catch (Exception e) {
			e.printStackTrace();
		}

		DATA = DATA.trim();
		int n = 0;
		int no = 0;
		String query = "INSERT INTO work_place values(?,?,?,?,?,?,?,?,?,?)";
		String query2 = "SELECT MAX(no) FROM work_place";

		try {

			// ÃÖ°í no+1 °ªÀ» Á¶È¸ ÇÏ¿© ÀúÀå

			pstm2 = conn2.prepareStatement(query2);
			resultSet2 = pstm2.executeQuery();

			while (resultSet2.next()) {
				no = resultSet2.getInt(1);

			}

			pstm2 = conn2.prepareStatement(query);
			// System.out.println(TYPE + " : " + DATA);

			pstm2.setInt(1, no + 1);
			pstm2.setString(2, "X");
			pstm2.setString(3, "X");
			pstm2.setString(4, "X");
			pstm2.setString(5, "X");
			pstm2.setString(6, "X");

			if (TYPE.equals("MD5")) {
				pstm2.setString(2, DATA);
			}
			if (TYPE.equals("SHA256")) {
				pstm2.setString(3, DATA);
			}
			if (TYPE.equals("SHA1")) {
				pstm2.setString(4, DATA);
			}
			if (TYPE.equals("IP")) {
				pstm2.setString(5, DATA);
			}
			if (TYPE.equals("URL")) {
				pstm2.setString(6, DATA);
			}

			pstm2.setInt(7, 0);
			pstm2.setString(8, filePath);
			pstm2.setString(9, From);
			pstm2.setString(10, time);

			n = pstm2.executeUpdate();

			if (n == 1) {
				// System.out.println(TYPE + " : " + DATA);
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
	// °øÅë¸ðµâ checkData
	//////////////////////////////////////////////////////////////////////////

	public static String getCurrentDateTime() {
		Date today = new Date(0);
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "yyyyMMddHHmmss"; // hhmmss·Î ½Ã°£,ºÐ,ÃÊ¸¸ »Ì±âµµ °¡´É
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
		return formatter.format(today);
	}

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

	// ¸ÞÀÏ¹ß¼Û°Ç
	public int getStatus() {

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

	// Ã³¸®°Ç¼ö
	public int getStatus2() {

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
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		int n = 0;
		int mailcount = 0;
		String query2 = "SELECT COUNT(status), no FROM cve WHERE status = '0'";

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

	// mailº¯°æ
	public String mailAddress(String address) {

		if (address.equals("a"))
			address = "DLZ1160@s-oil.com";
		if (address.equals("b"))
			address = "sungwoo.kwon@s-oil.com";
		if (address.equals("c"))
			address = "jsh0119@s-oil.com";
		if (address.equals("d"))
			address = "kmh0816@s-oil.com";
		if (address.equals("e"))
			address = "bh.lee@s-oil.com";
		if (address.equals("f"))
			address = "ksm0117@s-oil.com";
		if (address.equals("g"))
			address = "lyj0409@s-oil.com";
		if (address.equals("h"))
			address = "khw1205@s-oil.com";
		if (address.equals("j"))
			address = "osh1010@s-oil.com";

		if (address.equals("") || address.isBlank())
			address = "dlz1160@s-oil.com";

		System.out.println("mailAddress  to " + address);

		try {
			String jdbcUrl = "jdbc:mysql://localhost:3306/ioc?useUnicode=true&characterEncoding=utf8";
			String dbId = "root";
			String dbPass = "!Hg1373002934";

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		address = address.trim();
		int n = 0;
		String query = "UPDATE site_status SET address = ?";

		try {

			pstm = conn.prepareStatement(query);
			pstm.setString(1, address);
			// pstm.setString(4, "");

			n = pstm.executeUpdate();

			if (n == 1) {
				System.out.println("mailAddress Changed to " + address);
				return address;
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
		return "no";
	}

	public String getMail() {
		String address = "";
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
		String query = "SELECT address FROM site_status WHERE no = 1";

		try {

			pstm = conn.prepareStatement(query);
			resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				address = resultSet.getString(1);

			}

			return address;

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
		return "";

	}

	public int nokori() {

		int result = 0;
		int result2 = 0;
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
		String query = "SELECT count(no) FROM work_place WHERE status = 0";
		String query2 = "SELECT count(no) FROM cve WHERE status = 0";
		try {

			pstm = conn.prepareStatement(query);
			resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				result = resultSet.getInt(1);

			}

			pstm = conn.prepareStatement(query2);
			resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				result2 = resultSet.getInt(1);

			}

			 return result + result2;

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

	public int getProcess() {

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
		int mailcount = 0;
		String query2 = "SELECT no, from, date, status FROM work_place";

		try {

			pstm = conn.prepareStatement(query2);
			resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				mailcount = resultSet.getInt(1);

			}

			int total;
			int nokori;
			String from;
			String date;
			int Status;

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

	public String setDate(String time) {

		try {
			String jdbcUrl = "jdbc:mysql://localhost:3306/ioc?useUnicode=true&characterEncoding=utf8";
			String dbId = "root";
			String dbPass = "!Hg1373002934";

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		String query = "UPDATE site_status SET time = ?";

		try {

			pstm = conn.prepareStatement(query);
			pstm.setString(1, time);
			// pstm.setString(4, "");

			int n = pstm.executeUpdate();

			if (n == 1) {
				System.out.println("time Changed to " + time);
				return time;
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
		return "no";

	}

	public int setJobq(String dateToStr2, String ipAddress, String type, String mailaddress, String fileName) {

		int result = 0;
		int result2 = 0;
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
		String sql = "";

		String query = "SELECT count(no) FROM jobq";
		try {

			pstm = conn.prepareStatement(query);
			resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				result = resultSet.getInt(1);

			}
			sql = "INSERT INTO jobq VALUES (?,?,?,?,?,?,?)";
			if (result == 0) {
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, 1);
				pstm.setString(2, ipAddress);
				pstm.setString(3, dateToStr2);
				pstm.setInt(4, 0);
				pstm.setString(5, type);
				pstm.setString(6, mailaddress);
				pstm.setString(7, fileName);
				pstm.executeUpdate();
			} else if (result > 0) {
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, result + 1);
				pstm.setString(2, ipAddress);
				pstm.setString(3, dateToStr2);
				pstm.setInt(4, 0);
				pstm.setString(5, type);
				pstm.setString(6, mailaddress);
				pstm.setString(7, fileName);
				pstm.executeUpdate();
			}

			return 1;

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