/**
 * 
 */
package vserver.dao.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import common.util.FindType;
import common.vo.library.Book;
import common.vo.library.Borrow;
import common.vo.library.Punishment;
import common.vo.library.Reader;

/**
 * 借阅的dao类 有借书，查询借阅的书籍，挂失书籍，还书的功能，还能判断在预约表和借阅表里是否有特定的索书号的书
 * 
 * @author zhongfang
 * 
 */
public class BorrowDao {

	private static Connection conn = null;
	private static Statement state1 = null, state2 = null;
	private static ResultSet rs = null;
	private static String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static String CONN_URL = "jdbc:mysql://localhost/campusInfo";
	private static String USER_NAME = "ci_manager";
	private static String PASSWORD = "qqqqqq";
	private static Reader reader;
	private static Book book;
	private static int id, readerId, bookId, renewTimes;
	private static String startTime, endTime, deadLine;
	private static boolean status;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public static void initialize() {
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
			state1 = conn.createStatement();
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
		if (state1 != null)
			try {
				state1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static Borrow borrowBook(Reader reader, Book book)
			throws SQLException {
		initialize();
		Borrow borrow = new Borrow(reader, book);
		readerId = reader.getReaderId();
		id = borrow.getBorrowId();
		bookId = book.getBookId();
		startTime = borrow.getStartTime();
		deadLine = borrow.getDeadLine();
		endTime = borrow.getEndTime();
		renewTimes = borrow.getRenewTimes();
		status = borrow.isStatus();
		double money = reader.getMoney();
		// 已经借阅或预约了相同索书号的书
		if (getBooksSameCallCode(reader, book)) {
			rs = null;
			terminate();
			return null;
		} else {
			// 查询有未还的书
			String test = "select * from ci_borrow where ReaderId=" + readerId
					+ " and Status=false";
			Statement ssss = conn.createStatement();
			ResultSet rs2 = ssss.executeQuery(test);
			int borrowingBooks = 0;
			while (rs2.next()) {
				borrowingBooks++;
			}
			if (borrowingBooks >= reader.getRule().getMaxBorrowBooks()) { // 超过祖大可借书的数量
				rs2.close();
				rs = null;
				terminate();
				return null;
			} else {
				// 如果有未付款的惩罚，不能借书//付款
				String s = "select * from ci_punishment where ReaderId="
						+ readerId + " and Status=false";
				rs = state1.executeQuery(s);
				boolean koukuan;
				if (rs.next()) { // 有未付款欠款
					koukuan = PunishmentDao.getPunished(reader);
				} else
					koukuan = true;
				if (koukuan) { // 扣款全部成功，没有欠款
					String sql = "insert into ci_borrow (ReaderId, BookId, StartTime, DeadLine, EndTime,"
							+ " RenewTimes, Status) values("
							+ readerId
							+ ","
							+ bookId
							+ ","
							+ "'"
							+ startTime
							+ "','"
							+ deadLine
							+ "','"
							+ endTime
							+ "',"
							+ renewTimes
							+ ","
							+ status + ")";
					state1.executeUpdate(sql);
					book.setStatus("可预约");
					sql = "update ci_book set Status='" + book.getStatus()
							+ "' where Id=" + bookId + "";
					state1.executeUpdate(sql);
					rs = null;
					terminate();
					return borrow;
				} else { // 扣款失败
					return null;
				}
			}
		}

	}

	public static boolean returnBook(Reader reader, Book book)
			throws SQLException {
		initialize();
		bookId = book.getBookId();
		readerId = reader.getReaderId();
		rs = null;
		String sql = "select * from ci_borrow where BookId=" + bookId
				+ " and ReaderId=" + readerId + " and Status=false";
		rs = state1.executeQuery(sql);
		if (rs.next()) {
			id = rs.getInt(1);
			Calendar ca = Calendar.getInstance();
			Date d = ca.getTime();
			endTime = sdf.format(d);
			deadLine = rs.getString("DeadLine");
			Date d2 = null;
			try {
				d2 = sdf.parse(deadLine);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			// 当前时间超过应还书日期，生成惩罚
			if (d.after(d2)) {
				Punishment punishment = new Punishment(reader, book);
				punishment.setReason("还书超期");
				long millionSecondsForDate1 = d.getTime();
				long mollionSecondsForDate2 = d2.getTime();
				long time = millionSecondsForDate1 - mollionSecondsForDate2;
				int days = (int) (time / (1000 * 3600 * 24));
				double amount = (double) (0.1 * (days));// 每超期一天罚款0.1元
				punishment.setAmount(amount);
				PunishmentDao.generatePunishment(punishment);
			}
			sql = "update ci_borrow set Status=true ,EndTime='" + endTime
					+ "' where Id=" + id + "";
			state1.executeUpdate(sql);
			// 检查是否预约，如果预约则改书籍状态为等待预约取书 否则设为可借
			sql = "select Status from ci_book where Id=" + bookId + "";
			state2 = conn.createStatement();
			ResultSet r = state2.executeQuery(sql);
			if (r.next()) {
				String status = r.getString(1);
				if (status.equals("已预约")) // 如果已经有预约
					status = "等待预约取书";
				else
					status = "可借";
				sql = "update ci_book set Status='" + status + "' where Id="
						+ bookId + "";
				state1.executeUpdate(sql);
				sql = "update ci_order set Status=true where BookId=" + bookId
						+ "";
				state1.executeUpdate(sql);
			}
			rs = null;
			terminate();
			return true;
		} else {
			terminate();
			return false;
		}
	}

	public static boolean renewBook(Reader reader, Book book)
			throws SQLException {
		initialize();
		bookId = book.getBookId();
		readerId = reader.getReaderId();
		rs = null;
		String sql = "select * from ci_borrow where ReaderId=" + readerId
				+ " and BookId=" + bookId + " and Status=false";
		rs = state1.executeQuery(sql);
		if (rs.next()) { // 超期不得续借
			id = rs.getInt(1);
			deadLine = rs.getString(5);
			renewTimes = rs.getInt(7);
			int longerDays = reader.getRule().getMaxRenewDays();
			Calendar ca = Calendar.getInstance();
			Date d1 = ca.getTime();
			Date d2 = null;
			try {
				d2 = sdf.parse(deadLine);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (d1.after(d2)) {
				terminate();
				return false;
			} else {
				ca.add(Calendar.DATE, longerDays);
				d2 = ca.getTime();
				deadLine = sdf.format(d2);
				renewTimes += 1;
				sql = "update ci_borrow set DeadLine='" + deadLine
						+ "', RenewTimes=" + renewTimes + " where Id=" + id
						+ "";
				state1.executeUpdate(sql);
				rs = null;
				terminate();
				return true;
			}
		} else {
			terminate();
			return false;
		}
	}

	// 检查读者是否有预约或这在借阅相同索书号的书籍，如果有，则不能借书或预约
	public static boolean getBooksSameCallCode(Reader reader, Book book)
			throws SQLException {
		initialize();
		readerId = reader.getReaderId();
		String callCode = book.getCallCode();
		String sql = null;
		sql = "select BookId from ci_borrow where ReaderId=" + readerId
				+ " and Status=false";
		rs = state1.executeQuery(sql);
		while (rs.next()) {
			bookId = rs.getInt(1);
			state2 = conn.createStatement();
			sql = "select CallCode from ci_book where Id=" + bookId + "";
			ResultSet r = state2.executeQuery(sql);
			if (r.next())
				if (callCode.equals(r.getString(1))) { // 读者借阅的书籍存在与该书籍相同的索书号
					r.close();
					state2.close();
					return true;
				}
		}
		sql = "select BookId from ci_order where ReaderID=" + readerId + "";
		Statement state3 = conn.createStatement();
		ResultSet rrr = state3.executeQuery(sql);
		while (rrr.next()) {
			bookId = rrr.getInt(1);
			Statement state4 = conn.createStatement();
			sql = "select CallCode from ci_book where Id=" + bookId + "";
			ResultSet r = state4.executeQuery(sql);
			if (r.next())
				if (callCode.equals(r.getString(1))) { // 读者预约的书籍存在与该书籍相同的索书号
					r.close();
					state4.close();
					return true;
				}
		}
		return false;
	}

	public static Vector findBorrow(Reader reader, Integer findType)
			throws SQLException {
		Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
		readerId = reader.getReaderId();
		initialize();
		rs = null;
		String sql = null;
		if (findType.equals(FindType.NOW))
			sql = "select * from ci_borrow where ReaderId=" + readerId
					+ " and Status=0";
		else
			sql = "select * from ci_borrow where ReaderId=" + readerId
					+ " and Status=1";
		rs = state1.executeQuery(sql);
		if (rs == null) {
			terminate();
			return new Vector(0);
		} else {
			while (rs.next()) {
				Vector<Object> v = new Vector<Object>();
				v.removeAllElements();
				bookId = rs.getInt(3);

				String sql2 = "select * from ci_book where Id=" + bookId + "";
				state2 = conn.createStatement();
				ResultSet rs2 = state2.executeQuery(sql2);
				if (rs2.next()) {
					v.addElement(bookId);// bookId
					v.addElement(rs2.getString(2));// bookName
					v.addElement(rs2.getString(3));// Author
					v.addElement(rs.getString(4));// startTIme
					if (findType.equals(FindType.NOW)) {
						v.addElement(rs.getString(5));// deadLine
						v.addElement(rs.getInt(7));// renewTimes
					} else
						v.addElement(rs.getString(6));// endTime

					v.addElement(rs2.getString(6));// storePlace
				}
				rs2.close();
				rows.addElement(v);
			}
			terminate();
			for (Vector<Object> row : rows) {
				for (Object ele : row) {
					System.out.print(ele + " ");
				}
				System.out.println();
			}
			return rows;
		}
	}

	public static boolean lossBook(Reader reader, Book book)
			throws SQLException {
		bookId = book.getBookId();
		readerId = reader.getReaderId();

		String sql = "select * from ci_borrow where ReaderId=" + readerId
				+ " and BookId=" + bookId + " and Status= false";
		initialize();
		rs = state1.executeQuery(sql);
		if (rs.next()) { // 生成惩罚表
			sql = "select Price from ci_book where Id=" + bookId + "";
			state2 = conn.createStatement();
			ResultSet r = state2.executeQuery(sql);
			if (r.next()) {
				double amount = r.getDouble("Price");
				Punishment punishment = new Punishment(reader, book);
				punishment.setReason("遗失书籍");
				punishment.setAmount(amount);
				PunishmentDao.generatePunishment(punishment);
			}
			// 书籍状态设置
			sql = "update ci_book set Status='已挂失' where Id=" + bookId + "";
			Statement s = conn.createStatement();
			s.executeUpdate(sql);
			rs = null;
			terminate();
			return true;
		} else {
			terminate();
			return false;
		}
	}
}
