package vserver.dao;


import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.vo.User;
import common.vo.UserAccount;

/**
 * 用于处理用户管理模块的数据库操作
 * @author goclis
 *
 */
public class UserManageDbOperator {
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
					&& uIdentity.equals(rs.getString(3))) {
				// 更新用户状态（登录，离线）
				// TODO: 在退出时改变用户的状态
				String sqlUpdate = "UPDATE ci_user SET status = 'online' WHERE id = '"
						+ uId + "'"; 
				Statement connStat = conn.createStatement();
				connStat.executeUpdate(sqlUpdate);
				
				// TODO：填充user的数据
				String sqlGetUserInfo = "SELECT stuNum, stuName FROM ci_stuInfo WHERE stuId = '" + uId + "'";
				Statement statInfo = conn.createStatement();
				ResultSet rs2 = statInfo.executeQuery(sqlGetUserInfo);
				if (rs2.first()) {
					user.setIdNum(rs2.getString(1));
					user.setName(rs2.getString(2));
				}
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
		String uIdNum = user.getIdNum();
		String uIdentity = user.getIdentity();
		String uPwd = user.getPassword();
		String uStatus = "offline";
		
		// 连接数据库并检查用户是否已存在
		Class.forName(DRIVER_NAME);
		Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
		PreparedStatement preparedStat = conn.prepareStatement(sqlGetUsers);
		preparedStat.setString(1, uId);
		ResultSet rs = preparedStat.executeQuery();
		if (uIdentity.equals("学生")) {
			// 检查学籍表
			Statement stat = conn.createStatement();
			String sqlGetStuInfo = "SELECT * FROM ci_stuInfo WHERE stuId = '" + uId + "' AND stuNum = '" + uIdNum + "'";
			ResultSet rs2 = stat.executeQuery(sqlGetStuInfo);
			if (!rs2.first() || rs.first()) { // 学籍未注册或用户已存在
				return false;
			} 
			
			// 在ci_user表中添加新用户
			String sqlCreateUser = String.format("INSERT INTO ci_user (id, password, " 
					+ " identity, status) VALUES ('%s', '%s', '%s', '%s')",
					uId, uPwd, uIdentity, uStatus);
			Statement connStat = conn.createStatement();
			connStat.executeUpdate(sqlCreateUser);
		} else if (uIdentity.equals("老师")) {
			// TODO： 检查老师表
			// 在ci_user表中添加新用户
			String sqlCreateUser = String.format("INSERT INTO ci_user (id, password, " 
					+ " identity, status) VALUES ('%s', '%s', '%s', '%s', '%s')",
					uId, uPwd, uIdentity, uStatus);
			Statement connStat = conn.createStatement();
			connStat.executeUpdate(sqlCreateUser);
		}
		
		// 在ci_account表中关联用户（非管理员）
		if (!user.getIdentity().equals("管理员")) {
			String sqlAccount = "INSERT INTO ci_account VALUES ('" + uId + "', 0)";
			Statement accountStat = conn.createStatement();
			accountStat.executeUpdate(sqlAccount);
		}
		
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
	
	/**
	 * 查询用户账户余额
	 * @param user -- 用户
	 * @return 查询成功（有记录）返回余额否则返回null
	 */
	public Double queryAccount(User user) {
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
			Statement stat = conn.createStatement();
			String sql = "SELECT money FROM ci_account WHERE user_id = '" + user.getId() + "'";
			ResultSet rs = stat.executeQuery(sql);
			if (rs.first()) {
				return Double.valueOf(rs.getString(1));
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Error when query user account");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error when query user account");
			e.printStackTrace();
		}
		
		return null;
	}

	public boolean updateAccounts(ArrayList<UserAccount> accounts) {
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
			Statement stat = conn.createStatement();
			for (UserAccount account : accounts) {
				String sql = "UPDATE ci_account SET money = " + account.getMoney() 
						+ "WHERE user_id = '" + account.getUserId() + "'";
				if (stat.executeUpdate(sql) <= 0) {
					return false;
				}
			}
			return true;
		} catch (ClassNotFoundException e) {
			System.out.println("Error when update acounts");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error when update acounts");
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 查询所有用户账户
	 * @return
	 */
	public ArrayList<UserAccount> queryAllAccount() {
		ArrayList<UserAccount> accounts = new ArrayList<UserAccount>();
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
			Statement stat = conn.createStatement();
			String sql = "SELECT * FROM ci_account";
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				int userId = Integer.valueOf(rs.getString(1));
				double money = Double.valueOf(rs.getString(2));
				accounts.add(new UserAccount(userId, money));
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Error when query all account");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error when query all account");
			e.printStackTrace();
		}
		return accounts;
	}
}
