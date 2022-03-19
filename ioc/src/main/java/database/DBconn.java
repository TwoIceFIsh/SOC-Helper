package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBconn {

	private static Connection connection = null;
	private static PreparedStatement pstm = null;
	private static ResultSet resultSet = null;

	public DBconn() {
	}

	public  ResultSet getresultSet() {
		return resultSet;
	}

	public  PreparedStatement getPreparedStatement() {
		return pstm;
	}

	public  Connection getConnection() {

		String jdbcUrl = "jdbc:mysql://localhost:3306/ioc?useUnicode=true&characterEncoding=utf8";
		String dbId = "root";
		String dbPass = "Dlqudgh1!";

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcUrl, dbId, dbPass);

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return connection;

	}

	public  Connection getConnection(String className, String url, String user, String password) {
		if (connection == null) {
			try {
				Class.forName(className);
				connection = DriverManager.getConnection(url, user, password);
			} catch (SQLException | ClassNotFoundException e) {

				e.printStackTrace();
			}
		}
		return connection;
	}

	public  void close() // 항상 DBconn으로 열었으면 DBConn으로 close하자.
	{
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connection = null;
		}

		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultSet = null;
		}

		if (pstm != null) {
			try {
				pstm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pstm = null;
		}
	}

}
