package service_cve;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat; 
import java.util.Locale;
import java.util.regex.Pattern;
import database.DBconn;
import site.siteDAO;

public class dbrw_cve {
	Connection conn = null;
	PreparedStatement pstm = null;
	ResultSet resultSet = null;
	
	DBconn DBconn = new DBconn();
	
	public dbrw_cve() {
		this.resultSet = DBconn.getresultSet();
		this.pstm = DBconn.getPreparedStatement();
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
 
				siteDAO siteDAO = new siteDAO();
				siteDAO.insert_log(dateToStr, ipAddress, count1, "CVE", mailAddress);

				return no;
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return 0;
	}

	// ÆÄÀÏÀ» ÇÑÁÙ¾¿ ÀÐ¾î¼­ DB¿¡ writeLine ÇÑ´Ù
	public int writeLine(String cve, String ipAddress, String time) {

		
		conn = DBconn.getConnection();
		
		System.out.println("pathWrite " + cve);
		cve = cve.trim();
		int n = 0;
		int no = 0;
		String query = "INSERT INTO cve values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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

			for (int i = 6; i < 16; i++) {
				pstm.setString(i, "X");
			}

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

			DBconn.close();

		}
		return 0;
	}

	public int writeLine1111(String cve, String ipAddress, String time) {
		conn = DBconn.getConnection();
		System.out.println("pathWrite " + cve);
		cve = cve.trim();
		int n = 0;
		int no = 0;
		String query = "INSERT INTO cve values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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

			for (int i = 6; i < 16; i++) {
				pstm.setString(i, "X");
			}

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
			
			DBconn.close();
		}
		return 0;
	}

	//////////////////////////////////////////////////////////////////////////
	// °øÅë¸ðµâ checkData
	//////////////////////////////////////////////////////////////////////////

	public static String getCurrentDateTime() {
		Date today = new Date(0);
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "yyyyMMddHHmmss"; // 
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

 

}
