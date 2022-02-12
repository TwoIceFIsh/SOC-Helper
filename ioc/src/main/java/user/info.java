/*
 * package user;
 * 
 * import java.sql.Connection; import java.sql.DriverManager; import
 * java.sql.PreparedStatement; import java.sql.ResultSet;
 * 
 * public class info {
 * 
 * Connection conn = null; PreparedStatement pstm = null; ResultSet resultSet =
 * null; Connection conn2 = null; PreparedStatement pstm2 = null; ResultSet
 * resultSet2 = null;
 * 
 * public int updateUserIP(String ip) {
 * 
 * try { String jdbcUrl =
 * "jdbc:mysql://localhost:3306/ioc?useUnicode=true&characterEncoding=utf8";
 * String dbId = "root"; String dbPass = "!Hg1373002934";
 * 
 * Class.forName("com.mysql.cj.jdbc.Driver"); conn =
 * DriverManager.getConnection(jdbcUrl, dbId, dbPass);
 * System.out.println("제대로 연결되었습니다."); } catch (Exception e) {
 * e.printStackTrace(); }
 * 
 * int n = 0; int no = 0; String query =
 * "INSERT INTO user_info values(?,?,?,?,?,?,?)"; String query2 =
 * "SELECT MAX(no) FROM cve";
 * 
 * try {
 * 
 * pstm = conn.prepareStatement(query2); resultSet = pstm.executeQuery();
 * 
 * while (resultSet.next()) { no = resultSet.getInt(1);
 * 
 * }
 * 
 * pstm = conn.prepareStatement(query); pstm.setInt(1, no + 1);
 * pstm.setString(2, ip); pstm.setString(3, ip); pstm.setString(4, ip);
 * pstm.setString(5, ip); pstm.setString(6, ip); pstm.setString(7, ip);
 * 
 * n = pstm.executeUpdate();
 * 
 * if (n == 1) { System.out.println(no + " " + cve + " insert Success"); return
 * 1; } else { System.out.println("insert fail"); } } catch (Exception e) {
 * e.printStackTrace(); } finally { try { if (resultSet != null)
 * resultSet.close();
 * 
 * if (pstm != null) pstm.close();
 * 
 * if (conn != null) conn.close();
 * 
 * } catch (Exception e) { } } return 0;
 * 
 * } }
 */