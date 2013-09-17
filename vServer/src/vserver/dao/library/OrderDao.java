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

import common.vo.library.Book;
import common.vo.library.Order;
import common.vo.library.Reader;

/**
 * 预约的dao类 有预约书籍，获取读者的所有预约，取消预约的功能
 * 
 * @author zhongfang
 * 
 */
public class OrderDao {
	private static Connection conn = null;
	private static Statement state = null, state2 = null;
	private static ResultSet rs = null;
	private static String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static String CONN_URL = "jdbc:mysql://localhost/campusInfo";
	private static String USER_NAME = "ci_manager";
	private static String PASSWORD = "qqqqqq";
	private static int orderId;
	private static int readerId;
	private static int bookId;
	private static Reader reader;
	private static Book book;
	private static String place;
	private static String startDate, endDate;

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

	public static Order orderBook(Reader reader, Book book) throws SQLException {
		Order order = new Order(reader, book);
		readerId = order.getReader().getReaderId();
		bookId = order.getBook().getBookId();
		startDate = order.getStartDate();
		endDate = order.getEndDate();
		String sql = "select * from ci_book where CallCode='"
				+ book.getCallCode() + "' and Status='可借'";
//		System.out.println(sql);
		initialize();
		rs = state.executeQuery(sql);
		if (rs.next()) { // 有相同索书号的书可借，不可预约
			terminate();
			System.out.println("same ISO");
			return null;
		} else {
			// 已经借阅或借阅了相同索书号的书，不能预约
			if (BorrowDao.getBooksSameCallCode(reader, book)) {
				terminate();
				System.out.println("same book");
				return null;
			} else {
				sql = "insert into ci_order ( ReaderId, BookId, StartDate, EndDate, Status) values("
						+ readerId
						+ ","
						+ bookId
						+ ",'"
						+ startDate
						+ "','"
						+ endDate + "', false)";
//				System.out.println(sql);
				initialize();
				state.executeUpdate(sql);
				String updateBook = "update ci_book set Status='已预约' where Id="
						+ bookId + "";
//				System.out.println(updateBook);
				state.executeUpdate(updateBook);
				rs = null;
				terminate();
				return order;
			}
		}
	}

	public static Order cancelOrder(Order order) throws SQLException {
		orderId = order.getOrderId();
		initialize();
		String sql = "select * from ci_order where Id=" + orderId + "";
//		System.out.println(sql);
		
		rs = state.executeQuery(sql);
		if (rs.next()) {
			String bookId = rs.getString(3);
			sql = "delete from ci_order where Id=" + orderId + "";
//			System.out.println(sql);
			Statement stat2 = conn.createStatement();
			String sqlUpdate = "UPDATE ci_book SET Status = '可预约' WHERE Id = " + bookId;
			stat2.executeUpdate(sqlUpdate);
			state.executeUpdate(sql);
			rs = null;
			terminate();
			return order;
		} else {
			terminate();
			return null;
		}
	}

	public static Vector getOrders(Reader reader) throws SQLException {
		Vector row = new Vector();
		readerId = reader.getReaderId();
		String sql = "select * from ci_order where ReaderId=" + readerId + "";
		initialize();
		rs = state.executeQuery(sql);
		while (rs.next()) {
			Vector v = new Vector();
			v.removeAllElements();
			v.addElement(rs.getInt(1));
			bookId = rs.getInt(3);
			Statement s = conn.createStatement();
			sql = "select Name,Author,CallCode,StorePlace from ci_book where Id="
					+ bookId + "";
			ResultSet r = s.executeQuery(sql);
			if (r.next()) {
				v.addElement(r.getString(1));
				v.addElement(r.getString(2));
				v.addElement(r.getString(3));
				v.addElement(r.getString(4));
			}
			v.addElement(rs.getString(4));
			v.addElement(rs.getString(5));
			if (rs.getBoolean(6))
				v.addElement("书已到，请取");
			else
				v.addElement("申请中");
			row.addElement(v);
		}
		terminate();
		return row;
	}
}
