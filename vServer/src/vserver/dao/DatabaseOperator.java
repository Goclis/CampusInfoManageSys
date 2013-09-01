package vserver.dao;


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
	private static String USER_NAME = "ci_manager";
	private static String PASSWORD = "qqqqqq";
	
	/*private Statement connStat;
	private Connection conn;
	private ResultSet rs;
	*/
	
	// sql strings
	private static String sqlGetUsers = "SELECT * FROM ci_user WHERE id = ?";
	
	/**
	 * 处理登录的数据库操作
	 * @param user
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public boolean login(User user) 
			throws SQLException, ClassNotFoundException {
		// TODO： 考虑优化失败信息，添加不同的失败类型，比如密码错误，已登录...
		String uId = user.getId();
		String uPwd = user.getPassword();
		String uIdentity = user.getIdentity();
		
		// 连接数据库并获得ResultSet
		Class.forName(DRIVER_NAME);
		Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
		PreparedStatement preparedStat = conn.prepareStatement(sqlGetUsers);
		preparedStat.setString(1, uId);
		ResultSet rs = preparedStat.executeQuery();
		
		if (rs.first()) {
			if (uPwd.equals(rs.getString(2))
					&& uIdentity.equals(rs.getString(5))) {
				// 更新用户状态（登录，离线）
				// TODO: 在退出时改变用户的状态
				String sqlUpdate = "UPDATE ci_user SET status = 'online' WHERE id = '"
						+ uId + "'"; 
				Statement connStat = conn.createStatement();
				connStat.executeUpdate(sqlUpdate);
				
				// 填充user的数据
				user.setName(rs.getString(3));
				user.setIdNum(rs.getString(4));
				user.setSex(rs.getString(6));
				user.setDepartment(rs.getString(7));
				user.setMajor(rs.getString(8));
				return true;
			} else {
				return false;
			}
		}
		
		return false;
	}
	
	/**
	 * 处理注册的数据库操作
	 * @param user
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean register(User user) 
			throws ClassNotFoundException, SQLException {
		String uId = user.getId();
		
		// 连接数据库并检查用户是否已存在
		Class.forName(DRIVER_NAME);
		Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
		PreparedStatement preparedStat = conn.prepareStatement(sqlGetUsers);
		preparedStat.setString(1, uId);
		ResultSet rs = preparedStat.executeQuery();
		if (rs.first()) { // 用户已存在
			return false;
		} 
		
		// 创建新用户
		String uPwd = user.getPassword();
		String uName = user.getName();
		String uIdNum = user.getIdNum();
		String uIdentity = user.getIdentity();
		String uSex = user.getSex();
		String uDepart = user.getDepartment();
		String uMajor = user.getMajor();
		String uStatus = "offline";
		
		String sqlCreateUser = String.format("INSERT INTO ci_user (id, password, name, " 
				+ " id_num, identity, sex, department, major, status) VALUES "
				+ "('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
				uId, uPwd, uName, uIdNum, uIdentity, uSex, uDepart, uMajor, uStatus);
		Statement connStat = conn.createStatement();
		connStat.executeUpdate(sqlCreateUser);
		
		return true;
	}
	
	/*
	 * 处理登出时用户状态的改变
	 */
	public boolean logout(User user) 
			throws ClassNotFoundException, SQLException {
		String uId = user.getId();
		
		// 连接数据库
		Class.forName(DRIVER_NAME);
		Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
		PreparedStatement preparedStat = conn.prepareStatement(sqlGetUsers);
		preparedStat.setString(1, uId);
		ResultSet rs = preparedStat.executeQuery();
		
		if (rs.first()) {
			String sqlLogout = String.format(
					"update ci_user set status = 'offline' where id = '%s'", uId);
			Statement stat = conn.createStatement();
			stat.executeUpdate(sqlLogout);
			return true;
		}
		
		return false;
	}
}
