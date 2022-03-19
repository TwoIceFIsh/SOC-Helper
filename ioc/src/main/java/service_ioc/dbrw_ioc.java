package service_ioc;

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

public class dbrw_ioc {
	Connection conn = null;
	PreparedStatement pstm = null;
	ResultSet resultSet = null;
	DBconn DBconn = new DBconn();
	public dbrw_ioc() {
		this.resultSet = DBconn.getresultSet();
		this.pstm = DBconn.getPreparedStatement();
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

				siteDAO siteDAO = new siteDAO();
				siteDAO.insert_log(dateToStr, ipAddress, count2, "IOC", mailAddress);

				return no;
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return 0;
	}

	// ÆÄÀÏÀ» ÇÑÁÙ¾¿ ÀÐ¾î¼­ DB¿¡ writeLine ÇÑ´Ù
	public int writeLine2(String DATA, String filePath, String TYPE, String From, String time) {
		conn = DBconn.getConnection();
		DATA = DATA.trim();
		int n = 0;
		int no = 0;
		String query = "INSERT INTO work_place values(?,?,?,?,?,?,?,?,?,?)";
		String query2 = "SELECT MAX(no) FROM work_place";

		try {

			// ÃÖ°í no+1 °ªÀ» Á¶È¸ ÇÏ¿© ÀúÀå

			pstm = conn.prepareStatement(query2);
			resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				no = resultSet.getInt(1);

			}

			pstm = conn.prepareStatement(query);
			// System.out.println(TYPE + " : " + DATA);

			pstm.setInt(1, no + 1);
			pstm.setString(2, "X");
			pstm.setString(3, "X");
			pstm.setString(4, "X");
			pstm.setString(5, "X");
			pstm.setString(6, "X");

			if (TYPE.equals("MD5")) {
				pstm.setString(2, DATA);
			}
			if (TYPE.equals("SHA256")) {
				pstm.setString(3, DATA);
			}
			if (TYPE.equals("SHA1")) {
				pstm.setString(4, DATA);
			}
			if (TYPE.equals("IP")) {
				pstm.setString(5, DATA);
			}
			if (TYPE.equals("URL")) {
				pstm.setString(6, DATA);
			}

			pstm.setInt(7, 0);
			pstm.setString(8, filePath);
			pstm.setString(9, From);
			pstm.setString(10, time);

			n = pstm.executeUpdate();

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
				DBconn.close();

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

	

}
