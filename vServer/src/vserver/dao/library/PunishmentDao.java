/**
 * 
 */
package vserver.dao.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import common.vo.library.Book;
import common.vo.library.Punishment;
import common.vo.library.Reader;

/**
 * 惩罚表的dao 实现寻找读者的所有惩罚信息，产生惩罚，结算惩罚欠款的功能
 * 
 * @author zhongfang
 * 
 */
public class PunishmentDao {

	private static Connection conn = null;
	private static Statement state = null;
	private static ResultSet rs = null;
	private static String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static String CONN_URL = "jdbc:mysql://localhost/campusInfo";
	private static String USER_NAME = "ci_manager";
	private static String PASSWORD = "qqqqqq";
	private static int id;
	private static int readerId;
	private static int bookId;
	private static Reader reader;
	private static Book book;
	private static String reason, generateTime, punishedTime;
	private static double amount;
	private static boolean status;

	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd,HH:mm:ss");

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

	public static boolean generatePunishment(Punishment punishment)
			throws SQLException {
		readerId = punishment.getReader().getReaderId();
		bookId = punishment.getBook().getBookId();
		reason = punishment.getReason();
		amount = punishment.getAmount();
		generateTime = punishment.getGenerateTime();
		punishedTime = punishment.getPunishedTime();
		status = punishment.isStatus();
//		System.out.println("产生惩罚表");
		String sql = "insert into ci_punishment(ReaderId, BookId, Reason, Amount, GenerateTime,"
				+ " PunishedTime,Status) values("
				+ readerId
				+ ","
				+ bookId
				+ ",'"
				+ reason
				+ "',"
				+ amount
				+ ","
				+ "'"
				+ generateTime
				+ "','" + punishedTime + "'," + status + ")";
		initialize();
//		System.out.println(sql);
		state.executeUpdate(sql);
		rs = null;
		terminate();
		return true;
	}

	public static boolean getPunished(Reader reader) throws SQLException {
		String s11 = "select * from ci_punishment where ReaderId=" + readerId
				+ " and Status=false";
//		System.out.println(s11);
		readerId = reader.getReaderId();
		double money = reader.getMoney();
		String sql = null;
		initialize();
		rs = state.executeQuery(s11);
		while (rs.next()) {
			id = rs.getInt(1);
			amount = rs.getDouble("Amount");
			money -= amount;
			if (money <= 0) { // 不够扣，设置读者欠费，等待充值后再进行扣款
				sql = "update ci_reader set Status='欠费' where Id=" + readerId
						+ "";
//				System.out.println(sql);
				state.executeUpdate(sql);
				rs = null;
				terminate();
				return false;
			} else { // 扣款，设置扣款成功
				Calendar ca = Calendar.getInstance();
				Date t = ca.getTime();
				punishedTime = sdf.format(t);
				sql = "update ci_punishment set PunishedTime='" + punishedTime
						+ "', Status=true where Id=" + id + "";
//				System.out.println(sql);
				state.executeUpdate(sql);
				sql = "update ci_reader set ReaderMoney=" + money
						+ " where Id=" + readerId + "";
//				System.out.println(sql);
				state.executeUpdate(sql);
				rs = null;
				terminate();
				return true;
			}
		}
		return false;
	}

	public static Vector findPunishment(Reader reader) throws SQLException {
		Vector rows = new Vector();
		readerId = reader.getReaderId();
		String sql = "select BookId,Reason,Amount,GenerateTime,PunishedTime,Status from ci_punishment where ReaderId="
				+ readerId + "";
//		System.out.println(sql);
		initialize();
		rs = null;
		rs = state.executeQuery(sql);
		if (rs != null) {
			while (rs.next()) {
				Vector v = new Vector();
				v.removeAllElements();
				bookId = rs.getInt(1);
				reason = rs.getString(2);
				amount = rs.getDouble(3);
				generateTime = rs.getString(4);
				punishedTime = rs.getString(5);
				status = rs.getBoolean(6);
				v.addElement(reason);
				v.addElement(bookId);
				sql = "select Name,Author,CallCode,StorePlace from ci_book where Id="
						+ bookId + "";
//				System.out.println(sql);
				Statement st = conn.createStatement();
				ResultSet r = st.executeQuery(sql);
				if (r.next()) {
					v.addElement(r.getString(1));
					v.addElement(r.getString(2));
					v.addElement(r.getString(3));
					v.addElement(r.getString(4));
				}
				v.addElement(generateTime);
				v.addElement(punishedTime);
				v.addElement(amount);
				if (status)
					v.addElement("结算完毕");
				else
					v.addElement("未处理");

				rows.addElement(v);
			}
			terminate();
			return rows;
		} else {
			terminate();
			return new Vector(0);
		}
	}
}
