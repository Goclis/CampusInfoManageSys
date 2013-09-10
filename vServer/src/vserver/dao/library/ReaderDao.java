package vserver.dao.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import common.util.FindType;
import common.vo.library.Reader;
import common.vo.library.Rule;

/**
 * reader的dao 实现reader的增删改查，和读者证的挂失
 * 
 * @author zhongfang
 * 
 */
public class ReaderDao {
	private static Connection conn = null;
	private static Statement state = null;
	private static ResultSet rs = null;
	private static String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static String CONN_URL = "jdbc:mysql://localhost/campusInfo";
	private static String USER_NAME = "ci_manager";
	private static String PASSWORD = "qqqqqq";
	private static int readerId, ruleId;
	private static String certificateTime, effectiveTime, expireTime, userId,
			status;
	private static double money;

	public static void initialize() {
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
			state = conn.createStatement();
//			System.out.println("连接到了数据库");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void terminate() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (state != null)
			try {
				state.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
//		System.out.println("成功关闭资源");
	}

	public static boolean add(Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		int id = reader.getReaderId();
		String sql = "select * from ci_reader where Id=" + id + "";
		initialize();
		rs = state.executeQuery(sql);

		if (rs.next()) { // 读者证已存在，添加失败
			terminate();
			return false;
		} else {
			userId = reader.getUserId();
			sql = "select * from ci_user where id='" + userId + "'";
			rs = state.executeQuery(sql);
			if (!rs.next()) { // 用户不存在，添加失败
				terminate();
				return false;
			} else {
				sql = "select * from ci_reader where UserId='" + userId + "'";
				Statement st = conn.createStatement();
				ResultSet r1 = st.executeQuery(sql);
				if (r1.next()) { // 用户已存在读者证，添加失败
					r1.close();
					terminate();
					return false;
				} else {
					readerId = reader.getReaderId();
					certificateTime = reader.getCertificateTime();
					effectiveTime = reader.getEffectiveTime();
					expireTime = reader.getExpireTime();
					money = reader.getMoney();
					status = reader.getStatus();
					ruleId = reader.getRule().getRuleId();

					sql = "insert into ci_reader values(" + readerId + ",'"
							+ userId + "','" + certificateTime + "'," + "'"
							+ effectiveTime + "','" + expireTime + "'," + money
							+ ",'" + status + "'," + ruleId + ")";
//					System.out.println(sql);
					state.executeUpdate(sql);
					rs = null;
					terminate();
					return true;
				}
			}
		}
	}

	public static Vector findReader(Reader reader, Integer findtype)
			throws SQLException {
		initialize();
		Vector rows = new Vector();
		String sql = null;
		if (findtype.equals(FindType.BY_USER_ID)) {
			String userId = reader.getUserId();
			sql = "select * from ci_reader where UserId ='" + userId + "'";
//			System.out.println(sql);
		} else if (findtype.equals(FindType.BY_ID)) {
			int readerId = reader.getReaderId();
			sql = "select * from ci_reader where Id=" + readerId + "";
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
				v.addElement(rs.getString(3));
				v.addElement(rs.getString(4));
				v.addElement(rs.getString(5));
				v.addElement(rs.getDouble(6));
				v.addElement(rs.getString(7));

				ruleId = rs.getInt(8);
				Vector rules = new Vector();
				Rule rule = new Rule();
				rule.setRuleId(ruleId);
				rules = RuleDao.findRule(rule, FindType.BY_ID);
				Vector ruleInfo = new Vector();
				ruleInfo = (Vector) rules.get(0);
				rule.setRuleId((Integer) ruleInfo.get(0));
				rule.setRuleName((String) ruleInfo.get(1));
				String s = rule.getRuleId() + "." + rule.getRuleName();

				v.addElement(s);
				rows.addElement(v);
//				System.out.println("找到符合条件的读者");
			}
			terminate();
			return rows;
		} else {
			terminate();
			return (new Vector(0));
		}
	}

	public static boolean delete(Reader reader) throws SQLException {
		readerId = reader.getReaderId();
		initialize();
		String sql = "select * from ci_reader where Id=" + readerId + "";
		rs = null;
		rs = state.executeQuery(sql);
		if (rs != null) { // 抹杀该读者证在所有表里的记录
			sql = "delete from ci_borrow where ReaderId=" + readerId + "";
//			System.out.println(sql);
			state.executeUpdate(sql);
			sql = "delete from ci_order where ReaderId=" + readerId + "";
//			System.out.println(sql);
			state.executeUpdate(sql);
			sql = "delete from ci_punishment where ReaderId=" + readerId + "";
//			System.out.println(sql);
			state.executeUpdate(sql);
			sql = "delete from ci_reader where Id=" + readerId + "";
//			System.out.println(sql);
			state.executeUpdate(sql);
			rs = null;
			terminate();
			return true;
		} else {
			terminate();
			return false;
		}
	}

	public static boolean update(Reader reader) throws SQLException {
		readerId = reader.getReaderId();
		initialize();
		String sql = "select * from ci_book where Id=" + readerId + "";
		rs = null;
		rs = state.executeQuery(sql);
		if (rs == null) {
			terminate();
			return false;
		} else {
			// readerId=reader.getReaderId();//不可改
			// userId=reader.getUserId();//不可改
			// certificateTime=reader.getCertificateTime();//注册时间不可改
			effectiveTime = reader.getEffectiveTime();
			expireTime = reader.getExpireTime();
			money = reader.getMoney();
			status = reader.getStatus();
			ruleId = reader.getRule().getRuleId();
			sql = "update ci_reader set EffectiveTime='" + effectiveTime + "',"
					+ "ExpireTime='" + expireTime + "',Status='" + status
					+ "',RuleId=" + ruleId + " where Id=" + readerId + "";
//			System.out.println(sql);
			state.executeUpdate(sql);
			rs = null;
			terminate();
			return true;
		}
	}

	public static boolean reportLoss(Reader reader) throws SQLException {
		readerId = reader.getReaderId();
		initialize();
		String sql = "select * from ci_reader where Id=" + readerId + "";
		rs = null;
		rs = state.executeQuery(sql);
		if (rs != null) {
			sql = "update ci_reader set Status='已挂失' where Id=" + readerId + "";
//			System.out.println(sql);
			state.executeUpdate(sql);
			rs = null;
			terminate();
			return true;
		} else {
			terminate();
			return false;
		}
	}
}
