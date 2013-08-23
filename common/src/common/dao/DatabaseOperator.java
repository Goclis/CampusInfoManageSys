package common.dao;


import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.beans.User;

public class DatabaseOperator {
	private static String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static String CONN_URL = "jdbc:mysql://localhost/campusInfo";
	private static String USER_NAME = "goclis";
	private static String PASSWORD = "qqqqqq";
	
	/*private Statement connStat;
	private Connection conn;
	private ResultSet rs;
	*/
	
	/**
	 * 处理登录的数据库操作
	 * @param user
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static boolean login(User user) 
			throws SQLException, ClassNotFoundException {
		String sqlLogin = "SELECT * FROM ci_user WHERE id = ?";
		
		Class.forName(DRIVER_NAME);
		Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
		PreparedStatement preparedStat = conn.prepareStatement(sqlLogin);
		
		preparedStat.setString(1, user.getId());
		ResultSet rs = preparedStat.executeQuery();
		
		if (rs.first()) {
			if (user.getPassword().equals(rs.getString(2))
					&& user.getIdentity().equals(rs.getString(5))) {
				// TODO: 更新用户状态（登录，离线）
				String sqlUpdate = "UPDATE ci_user SET status = 'online' WHERE id = '"
						+ user.getId() + "'"; 
				Statement connStat = conn.createStatement();
				connStat.executeUpdate(sqlUpdate);
				return true;
			} else {
				return false;
			}
		}
		
		return false;
	}
}
