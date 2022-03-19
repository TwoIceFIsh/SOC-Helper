package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; 

import database.DBconn;

public class userDAO {

	Connection conn = null;
	PreparedStatement pstm = null;
	ResultSet rs = null;

	userDAO() {
		this.pstm = DBconn.getPreparedStatement();
		this.rs = DBconn.getresultSet();
	}

	public boolean login(userDTO user) {

		conn = DBconn.getConnection();

		String sql = "SELECT exists (select * FROM user WHERE a= '" + user.getId() + "' AND b = '" + user.getPw()
				+ "') As Q";

		System.out.println(sql);
 
		boolean no = false;
		try {

			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			if (rs != null) {
				rs.next();
				System.out.println("OK");
				no = rs.getBoolean(1);
				System.out.println(no);
				return no;
			} else {
				System.out.println("FAIL");
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {
			DBconn.close();
		}
		return no;
	}

	public boolean find(String id) {

		conn = DBconn.getConnection();
		int n = 0;
		boolean no1 = false;
		try {

			// 계정이존재하는지
			String sql = "SELECT exists (select * FROM user WHERE a= '" + id + "') As Q";
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			rs.next();
			System.out.println("OK");
			no1 = rs.getBoolean(1);

			if (no1 == false) {
				return no1;
			}

			// 대기열 번호 획득
			String sql2 = "SELECT no FROM find ORDER BY no DESC LIMIT 1";
			pstm = conn.prepareStatement(sql2);
			rs = pstm.executeQuery();

			while (rs.next()) {
				n = rs.getInt(1);
			}

			// 대기열 삽입
			String sqls = "INSERT INTO find VALUES(?,?,?)";
			pstm = conn.prepareStatement(sqls);
			pstm.setInt(1, n + 1);
			pstm.setString(2, id);
			pstm.setString(3, "0");
			pstm.executeUpdate();

			return no1;
		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {
			DBconn.close();
		}
		return no1;
	}

	public boolean getEmail(String email) {

		String sql = "SELECT exists (select * FROM user WHERE a= '" + email + "') As Q";
		conn = DBconn.getConnection();

 
		boolean no = false;
		try {

			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			if (rs != null) {
				rs.next();
				System.out.println("OK");
				no = rs.getBoolean(1);
				System.out.println(no);
				return no;
			} else {
				System.out.println("FAIL");
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {
			DBconn.close();
		}
		return no;
	}

	// 이메일계정이 없어서 코드를 삽입
	public int setCode(String email) {
 

		int code = (int) ((Math.random() * 10000));

		conn = DBconn.getConnection();

		try {
			int no = 0;
			int s = 0;

			String querya = "select no, a,b,c from code WHERE a = ? AND c = '0' limit 1";
			pstm = conn.prepareStatement(querya);
			pstm.setString(1, email);
			rs = pstm.executeQuery();

			while (rs.next()) {
				s = rs.getInt(4);
			}
			if (s == 1) {
				System.out.println("인증코드가 이미 발송되었습니다.");
				return 4;

			}

			String query = "select no from code ORDER BY no desc limit 1";
			pstm = conn.prepareStatement(query);
			rs = pstm.executeQuery();

			rs.next();
			no = rs.getInt(1);
			System.out.println(no);

			// no , email, code, satus
			String sql = "INSERT INTO code VALUES(?,?,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, no + 1);
			pstm.setString(2, email);
			pstm.setString(3, Integer.toString(code));
			pstm.setString(4, "1");
			pstm.executeUpdate();

			return 1;
		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {
			DBconn.close();
		}

		return 0;
	}

	public int checkCode(String email, String code) {
 

		conn = DBconn.getConnection();

		try {
 
			boolean s = false;

			String sql = "SELECT exists (select no, a,b,c from code WHERE a = ? AND b = ? AND c = 2) As Q";

			pstm = conn.prepareStatement(sql);
			pstm.setString(1, email);
			pstm.setString(2, code);
			rs = pstm.executeQuery();

			while (rs.next()) {
				s = rs.getBoolean(1);
			}
			if (!s) {

				return 10;

			}

			// no , email, code, status
			String sqlqq = "UPDATE code SET c = 3 WHERE a = ? AND b = ? AND c = 2";
			pstm = conn.prepareStatement(sqlqq);

			pstm.setString(1, email);
			pstm.setString(2, code);

			pstm.executeUpdate();

			return 11;

		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {
			DBconn.close();
		}
		return 0;
	}

	public int userjoin(userDTO user) {
 

		conn = DBconn.getConnection();

		try {
			int no = 0;
			boolean s = false;
			boolean ss = false;

			// 계정에 따른 CODE 존재여부 확인
			String test = "SELECT exists (SELECT no FROM code WHERE a = ? AND b = ? AND c = ?) As Q";
			pstm = conn.prepareStatement(test);
			pstm.setString(1, user.getId());
			pstm.setString(2, user.getCode());
			pstm.setString(3, "3");
			rs = pstm.executeQuery();

			while (rs.next()) {
				s = rs.getBoolean(1);
			}

			// 계정이 있으면 TRUE
			String tests = "SELECT exists (SELECT a FROM user WHERE a = ?) As Q";
			pstm = conn.prepareStatement(tests);
			pstm.setString(1, user.getId());
			rs = pstm.executeQuery();

			while (rs.next()) {
				ss = rs.getBoolean(1);
			}

			if (!s) {
				return 222;
			}
			if (ss) {
				return 333;
			}

			String sql = "SELECT no FROM user ORDER BY no DESC LIMIT 1";

			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			while (rs.next()) {
				no = rs.getInt(1);
			}

			// no , email, code, satus
			String sqlqq = "INSERT INTO user VALUES(?,?,?,?,?,?,?,?)";
			pstm = conn.prepareStatement(sqlqq);

			pstm.setInt(1, no + 1);
			pstm.setString(2, user.getId());
			pstm.setString(3, user.getPw());
			pstm.setString(4, user.getName());
			pstm.setString(5, user.getCode());
			pstm.setString(6, "");
			pstm.setString(7, "");
			pstm.setString(8, "");

			pstm.executeUpdate();

			return 11;

		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {
			DBconn.close();
		}
		return 0;

	}

	public userDTO getUser(String id) {

		userDTO user = new userDTO();
 		conn = DBconn.getConnection(); 
		try {

			// 반환
			String sql = "select no,a,b,c FROM user WHERE a= '" + id + "'";
			System.out.println(sql);
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			while (rs.next()) {
				user.setId(rs.getString(2));
				user.setPw(rs.getString(3));
				user.setName(rs.getString(4));
			}

			return user;
		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {
			DBconn.close();
		}
		return user;
	}
////////////////////////////////////////////////////////////

}
