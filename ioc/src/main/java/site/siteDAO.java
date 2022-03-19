package site;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import database.DBconn;

public class siteDAO {

	Connection conn = null;
	PreparedStatement pstm = null;
	ResultSet rs = null;
	DBconn DBconn = new DBconn();

	public siteDAO() {
	}

	public int[] moduleStatusCheck() {

		int[] module_Status = { 0, 0, 0, 0, 0 };
		conn = DBconn.getConnection();

		try {
			int no = 0;
			// 계정에 따른 CODE 존재여부 확인
			String test = "SELECT no, c FROM programs limit 5";
			pstm = conn.prepareStatement(test);
			rs = pstm.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					module_Status[no] = rs.getInt(2);
					no += 1;
				}

				return module_Status;
			}
		} catch (

		Exception e) { 
		} finally {
			DBconn.close();

		}

		return module_Status;

	}

	public ArrayList<siteDTO> getMail1() {

		ArrayList<siteDTO> out = new ArrayList<siteDTO>();
		conn = DBconn.getConnection();

		try {

			// 계정에 따른 CODE 존재여부 확인
			String test = "SELECT no, a,b,c FROM user ";
			System.out.println("Email Data 조회");
			pstm = conn.prepareStatement(test);
			rs = pstm.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					siteDTO site = new siteDTO();
					site.setNo(rs.getInt(1));
					site.setEmail(rs.getString(2));
					site.setPw(rs.getString(3));
					site.setName(rs.getString(4));
					out.add(site);

				}

				return out;
			}

		} catch (

		Exception e) {
		} finally {
			DBconn.close();
		}

		return out;
	}

	public String sendMessage(String email, String message) {

		conn = DBconn.getConnection();

		try {
			String name = "";
			int no1 = 0;

			// 이름가져옴
			String test = "SELECT * FROM user WHERE a = ?";

			pstm = conn.prepareStatement(test);
			pstm.setString(1, email);
			rs = pstm.executeQuery();

			while (rs.next()) {
				name = rs.getString(4);
			}

			// 메시지번호가져옴
			String test1 = "SELECT * FROM messages";
			pstm = conn.prepareStatement(test1);
			rs = pstm.executeQuery();

			while (rs.next()) {
				no1 = rs.getInt(1);
			}

			// 메시지 입력
			// no , date, message, who
			String sqlqq = "INSERT INTO messages VALUES(?,?,?,?)";
			pstm = conn.prepareStatement(sqlqq);

			// 현재 날짜/시간
			LocalDateTime now = LocalDateTime.now();
			String formatedNow = now.format(DateTimeFormatter.ofPattern("MM/dd HH:mm:ss"));

			pstm.setInt(1, no1 + 1);
			pstm.setString(2, formatedNow);

			if (message.equals("")) {
				pstm.setString(3, name);
			} else {
				pstm.setString(3, message);
			}

			pstm.setString(4, name);
			pstm.executeUpdate();

			return "[" + formatedNow + "] " + name + " : " + message;

		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {
			DBconn.close();
		}
		return "";

	}

	public String getMessage() {

		DBconn newConn = new DBconn();
		conn = newConn.getConnection();

		String formatedNow = "";
		String name = "";
		String message = "";
		String all = "";
		try {

			// message 가져옴
			String test1 = "select * from (SELECT * FROM messages ORDER BY no DESC) as Q ORDER BY no asc";
			pstm = conn.prepareStatement(test1);

			rs = pstm.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					formatedNow = rs.getString(2);
					name = rs.getString(4);
					message = rs.getString(3);
					all = all + "[" + formatedNow + "] " + name + " : " + message + "<br>";
				}

				return all;
			}
		} catch (

		Exception e) {
			
		} finally {
			newConn.close();
		}
		return "";
	}

	public int insert_log(String dateToStr, String ipAddress, int count1, String string, String mailAddress) {

		DBconn newConn = new DBconn();
		conn = newConn.getConnection();

		int n = 0;
		int no = 0;
		String query = "INSERT INTO log values(?,?,?,?,?,?)";
		String query2 = "SELECT MAX(no) FROM log";

		try {

			pstm = conn.prepareStatement(query2);
			rs = pstm.executeQuery();

			while (rs.next()) {
				no = rs.getInt(1);

			}

			String query3 = "SELECT no FROM jobq WHERE ipip = ? AND time = ?";
			pstm = conn.prepareStatement(query3);
			pstm.setString(1, ipAddress);
			pstm.setString(2, dateToStr);

			rs = pstm.executeQuery();

			int k = 0;
			while (rs.next()) {
				k = rs.getInt("no");
			}

			pstm = conn.prepareStatement(query);
			pstm.setInt(1, no + 1);

			if (string.equals("CVE")) {
				pstm.setString(2, dateToStr + " : 작업[" + Integer.toString(k) + "] : " + ipAddress + "님께서 CVE 작업 요청 ["
						+ count1 + "건] ");

			}
			if (string.equals("IOC")) {
				pstm.setString(2, dateToStr + " : 작업[" + Integer.toString(k) + "] : " + ipAddress + "님께서 IOC 작업 요청 ["
						+ count1 + "건] ");
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
			newConn.close();
		}
		return 0;
	}

	public String[] loglog() {

		String[] log = null;

		DBconn newConn = new DBconn();
		conn = newConn.getConnection();

		String query2 = "SELECT no, text FROM (SELECT no, text FROM log  ORDER BY no desc limit 10) as logT ORDER BY NO ASC";
		String query3 = "SELECT no FROM log limit 10";

		try {

			// 최고 no+1 값을 조회 하여 저장

			int count = 0;
			int nono = 0;
			pstm = conn.prepareStatement(query3);
			rs = pstm.executeQuery();

			while (rs.next()) {

				nono = rs.getInt(1);
			}

			log = new String[nono + 1];

			pstm = conn.prepareStatement(query2);
			rs = pstm.executeQuery();

			try {
				while (rs.next()) {
					// no[count] = Integer.toString(rs2.getInt(1));
					log[count] = rs.getString(2);
					// System.out.println("log[count] " + log[count]);
					count += 1;
				}

				log[count] = Integer.toString(nono);
			}

			catch (Exception e) {
			}

			return log;

		} catch (

		Exception e) {
			
		} finally {
			newConn.close();
		}
		return log;
	}

	// 메일발송건
	public int getStatus() {

		DBconn newDBconn = new DBconn();
		conn = newDBconn.getConnection();

		int mailcount = 0;
		String query2 = "SELECT mailcount FROM site_status";

		try {

			pstm = conn.prepareStatement(query2);
			rs = pstm.executeQuery();

			while (rs.next()) {
				mailcount = rs.getInt(1);

			}

			return mailcount;

		} catch (Exception e) {
		} finally {
			newDBconn.close();
		}
		return 0;
	}

	// 처리건수
	public int getStatus2() {

		DBconn newDBconn = new DBconn();
		conn = newDBconn.getConnection();

		int count = 0;
		String query2 = "SELECT count FROM site_status";

		try {

			pstm = conn.prepareStatement(query2);
			rs = pstm.executeQuery();

			while (rs.next()) {
				count = rs.getInt(1);

			}

			return count;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			newDBconn.close();
		}
		return 0;
	}

	public int getStatus3() {

		DBconn newDBconn = new DBconn();
		conn = newDBconn.getConnection();

		int mailcount = 0;
		String query2 = "SELECT COUNT(status), no FROM cve WHERE status = '0'";

		try {

			pstm = conn.prepareStatement(query2);
			rs = pstm.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					mailcount = rs.getInt(1);

				}

				return mailcount;

			}
		} catch (Exception e) {
		} finally {
			newDBconn.close();
		}
		return 0;
	}

	public int getStatus4() {
		DBconn newDBconn = new DBconn();
		conn = newDBconn.getConnection();
		int mailcount = 0;
		String query2 = "SELECT COUNT(status) FROM work_place WHERE status = '0'";

		try {

			pstm = conn.prepareStatement(query2);
			rs = pstm.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					mailcount = rs.getInt(1);

				}

				return mailcount;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			newDBconn.close();
		}
		return 0;
	}

	public String mailAddress2(String address) {

		conn = DBconn.getConnection();
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
			DBconn.close();
		}
		return "no";
	}

	public String getMail() {
		String address = "";
		conn = DBconn.getConnection();

		String query = "SELECT address FROM site_status WHERE no = 1";

		try {

			pstm = conn.prepareStatement(query);
			rs = pstm.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					address = rs.getString(1);

				}

				return address;
			}
		} catch (Exception e) { 
		} finally {
			DBconn.close();
		}
		return "";

	}

	public int nokori() {

		int result = 0;
		int result2 = 0;
		conn = DBconn.getConnection();

		String query = "SELECT count(no) FROM work_place WHERE status = 0";
		String query2 = "SELECT count(no) FROM cve WHERE status = 0";
		try {

			pstm = conn.prepareStatement(query);
			rs = pstm.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					result = rs.getInt(1);

				}

				pstm = conn.prepareStatement(query2);
				rs = pstm.executeQuery();

				while (rs.next()) {
					result2 = rs.getInt(1);

				}

				return result + result2;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBconn.close();
		}
		return 0;

	}

	public int getProcess() {

		conn = DBconn.getConnection();

		int mailcount = 0;
		String query2 = "SELECT no, from, date, status FROM work_place";

		try {

			pstm = conn.prepareStatement(query2);
			rs = pstm.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					mailcount = rs.getInt(1);

				}

				return mailcount;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBconn.close();
		}
		return 0;
	}

	public String setDate(String time) {

		conn = DBconn.getConnection();
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
			DBconn.close();
		}
		return "no";

	}

	public int setJobq(String dateToStr2, String ipAddress, String type, String mailaddress, String fileName) {

		int result = 0;
		conn = DBconn.getConnection();

		String sql = "";

		String query = "SELECT count(no) FROM jobq";
		try {

			pstm = conn.prepareStatement(query);
			rs = pstm.executeQuery();

			while (rs.next()) {
				result = rs.getInt(1);

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
			DBconn.close();
		}
		return 0;

	}

	public String nameTomail(String name) {

		conn = DBconn.getConnection();

		String address = "";
		String query2 = "SELECT * FROM USER WHERE c = ?";

		try {

			pstm = conn.prepareStatement(query2);
			pstm.setString(1, name);
			rs = pstm.executeQuery();

			while (rs.next()) {
				address = rs.getString(2);

			}

			return address;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBconn.close();
		}
		return "";
	}

}
