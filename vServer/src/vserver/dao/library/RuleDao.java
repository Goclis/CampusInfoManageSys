/**
 * 
 */
package vserver.dao.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import common.util.FindType;
import common.vo.library.Rule;

/**
 * rule的dao类 实现rule的增删改查。
 * 
 * @author zhongfang
 * 
 */
public class RuleDao {
	private static Connection conn = null;
	private static Statement state = null;
	private static ResultSet rs = null;

	private static String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static String CONN_URL = "jdbc:mysql://localhost/campusInfo";
	private static String USER_NAME = "ci_manager";
	private static String PASSWORD = "qqqqqq";
	private static Vector vct = new Vector();
	private static String sql = null;
	private static String name;
	private static int id, maxBorrowDays, maxRenewDays, maxRenewTimes,
			keepOrderDays, maxBorrowBooks;
	private static Rule rule;

	public static void initialize() {
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(CONN_URL,USER_NAME,PASSWORD);
			state = conn.createStatement();
//			System.out.println("连接到了数据库");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void terminate() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (state != null) {
			try {
				state.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//		System.out.println("成功关闭资源");
	}

	public static boolean add(Rule rule) throws SQLException {
		// TODO Auto-generated method stub
		initialize();
		id = rule.getRuleId();
		String sql = "select * from ci_rule where Id=" + id + "";
//		System.out.println(sql);
		rs = state.executeQuery(sql);
		if (rs.next()) {
			terminate();
			return false;
		} else {
			name = rule.getRuleName();
			maxBorrowDays = rule.getMaxBorrowDays();
			maxRenewDays = rule.getMaxRenewDays();
			maxRenewTimes = rule.getMaxRenewTimes();
			keepOrderDays = rule.getKeepOrderDays();
			maxBorrowBooks = rule.getMaxBorrowBooks();
			sql = "insert into ci_rule values(" + id + ",'" + name + "',"
					+ maxBorrowDays + "," + maxRenewDays + "," + maxRenewTimes
					+ "," + keepOrderDays + "," + maxBorrowBooks + ")";
//			System.out.println(sql);
			state.executeUpdate(sql);
			rs = null;
			terminate();
			return true;
		}
	}

	public static boolean delete(Rule rule) throws SQLException {
		initialize();
		id = rule.getRuleId();
		String sql = "select * from ci_reader where RuleId=" + id + "";
//		System.out.println(sql);
		rs = null;
		rs = state.executeQuery(sql);
		if (rs.next()) { // 存在依赖于该rule的读者证，不能删除
			terminate();
			return false;
		} else {
			sql = "delete from ci_rule where Id=" + id + "";
//			System.out.println(sql);
			state.executeUpdate(sql);
//			System.out.println("成功删除规则");
			rs = null;
			terminate();
			return true;
		}
	}

	public static boolean update(Rule rule) throws SQLException {
		id = rule.getRuleId();
		String sql = "select * from ci_rule where Id=" + id + "";
//		System.out.println(sql);
		initialize();

		rs = null;
		rs = state.executeQuery(sql);

		if (rs == null) {
			terminate();
			return false;
		} else {
			name = rule.getRuleName();
			maxBorrowDays = rule.getMaxBorrowDays();
			maxRenewDays = rule.getMaxRenewDays();
			maxRenewTimes = rule.getMaxRenewTimes();
			keepOrderDays = rule.getKeepOrderDays();
			maxBorrowBooks = rule.getKeepOrderDays();

			sql = "update ci_rule set Name='" + name + "', MaxBorrowDays="
					+ maxBorrowDays + "," + " MaxRenewDays=" + maxRenewDays
					+ ", MaxRenewTimes=" + maxRenewTimes + ","
					+ " KeepOrderDays=" + keepOrderDays + ", MaxBorrowBooks="
					+ maxBorrowBooks + " " + "where Id=" + id + "";
//			System.out.println(sql);
			state.executeUpdate(sql);
			rs = null;
			terminate();
			return true;
		}
	}

	public static Vector findRule(Rule rule, Integer findtype)
			throws SQLException {
		initialize();
		Vector rows = new Vector();
		String sql;
		if (findtype.equals(FindType.BY_NAME)) {
			String ruleName = rule.getRuleName();
			sql = "select * from ci_rule where Name like '%" + ruleName + "%'";
//			System.out.println(sql);
		} else {
			int ruleId = rule.getRuleId();
			sql = "select * from ci_rule where Id=" + ruleId + "";
//			System.out.println(sql);
		}
		rs = null;
		rs = state.executeQuery(sql);
		if (rs != null) {
			while (rs.next()) {
				Vector v = new Vector();
				v.removeAllElements();
				v.addElement(rs.getInt(1));
				v.addElement(rs.getString(2));
				v.addElement(rs.getInt(3));
				v.addElement(rs.getInt(4));
				v.addElement(rs.getInt(5));
				v.addElement(rs.getInt(6));
				v.addElement(rs.getInt(7));
				rows.addElement(v);
//				System.out.println("找到符合条件的规则");
			}
			terminate();
			return rows;
		} else {
			terminate();
			return (new Vector(0));
		}
	}
}
