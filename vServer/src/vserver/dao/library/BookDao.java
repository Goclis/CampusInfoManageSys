package vserver.dao.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;

import common.util.FindType;
import common.vo.library.Book;

/**
 * book管理的dao类 有书籍的增，删，改，查
 * 
 * @author zhongfang
 * 
 */
public class BookDao {
	private static Connection conn = null;
	private static Statement state = null;
	private static ResultSet rs = null;
	private static String title, author, type, callCode, publisher,
			publishTime, enterTime, status, descrip, storePlace;
	private static int bookId;
	private static double price;
	private static String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static String CONN_URL = "jdbc:mysql://localhost/campusInfo";
	private static String USER_NAME = "ci_manager";
	private static String PASSWORD = "qqqqqq";

	public static void initialize() {
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(CONN_URL,USER_NAME,PASSWORD);
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
				// e.printStackTrace();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		System.out.println("成功关闭资源");
	}

	public static boolean add(Book book) throws SQLException {
		String sql;
		bookId = book.getBookId();

		initialize();
		sql = "select * from ci_book where Id=" + bookId + "";
//		System.out.println(sql);
		rs = state.executeQuery(sql);

		if (rs.next()) {
			terminate();
			System.out.println("该书籍已存在");
			return false;
		} else {
//			System.out.println("开始添加");
			title = book.getTitle();
			author = book.getAuthor();
			type = book.getType();
			callCode = book.getCallCode();
			storePlace = book.getStorePlace();
			publisher = book.getPublisher();
			publishTime = book.getPublishTime();
			enterTime = book.getEnterTime();
			price = book.getPrice();
			status = book.getStatus();
			descrip = book.getDescription();

			sql = "insert into ci_book values(" + bookId + ",'" + title + "',"
					+ "'" + author + "','" + type + "','" + callCode + "','"
					+ storePlace + "','" + publisher + "'," + "'" + publishTime
					+ "','" + enterTime + "'," + price + ",'" + status + "',"
					+ "'" + descrip + "')";
//			System.out.println("开始插入书籍");
			state.executeUpdate(sql);
			rs = null;
			terminate();
			return true;
		}
	}

	public static boolean update(Book book) throws SQLException {
		bookId = book.getBookId();

		initialize();
		String sql = "select * from ci_book where Id=" + bookId + "";
		rs = null;
		rs = state.executeQuery(sql);
		if (rs == null) {
			terminate();
			return false;
		} else {
			title = book.getTitle();
			author = book.getAuthor();
			type = book.getType();
			callCode = book.getCallCode();
			storePlace = book.getStorePlace();
			publisher = book.getPublisher();
			publishTime = book.getPublishTime();
			// enterTime =book.getEnterTime();//不可更改
			price = book.getPrice();
			status = book.getStatus();
			descrip = book.getDescription();
			sql = "update ci_book set Name='" + title + "', Author='" + author
					+ "', Type='" + type + "', " + "CallCode='" + callCode
					+ "', StorePlace='" + storePlace + "', Publisher='"
					+ publisher + "'," + "PublishTime='" + publishTime
					+ "', Price=" + price + ", " + "Status='" + status
					+ "', Description='" + descrip + "' where Id=" + bookId
					+ "";
			state.executeUpdate(sql);
//			System.out.println(sql);
			rs = null;
			terminate();
			return true;
		}
	}

	public static boolean delete(Book book) throws SQLException {
		bookId = book.getBookId();
		initialize();
		String sql = "select * from ci_book where Id=" + bookId + "";
		rs = null;

		rs = state.executeQuery(sql);
		if (rs != null) {
			sql = "delete from ci_book where Id=" + bookId + "";
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

	public static Vector findBook(Book book, Integer findtype)
			throws SQLException {
		initialize();
		Vector rows = new Vector();
		String sql = null;
		if (findtype.equals(FindType.BY_NAME)) {
			String bookName = book.getTitle();
			sql = "select * from ci_book where Name like '%" + bookName + "%'";
		} else if (findtype.equals(FindType.BY_ID)) {
			int bookId = book.getBookId();
			sql = "select * from ci_book where Id=" + bookId + "";
		} else if (findtype.equals(FindType.BY_AUTHOR)) {
			author = book.getAuthor();
			sql = "select * from ci_book where Author like'%" + author + "%'";
		} else if (findtype.equals(FindType.BY_TYPE)) {
			type = book.getType();
			sql = "select * from ci_book where Type='" + type + "'";
		} else if (findtype.equals(FindType.BY_CALLCODE)) {
			callCode = book.getCallCode();
			sql = "select * from ci_book where CallCode like '%" + callCode
					+ "%'";
		} else if (findtype.equals(FindType.BY_PUBLISHER)) {
			publisher = book.getPublisher();
			sql = "select * from ci_book where Publisher like '%" + publisher
					+ "%'";
		} else if (findtype.equals(FindType.BY_STOREPLACE)) {
			storePlace = book.getStorePlace();
			sql = "select * from ci_book where StorePlace like '%" + storePlace
					+ "%'";
		}

//		System.out.println(sql);

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
				v.addElement(rs.getString(6));
				v.addElement(rs.getString(7));
				v.addElement(rs.getString(8));
				v.addElement(rs.getString(9));
				v.addElement(rs.getDouble(10));
				v.addElement(rs.getString(11));
				v.addElement(rs.getString(12));
				rows.addElement(v);
//				System.out.println("找到符合条件的书籍");
			}
			terminate();
			return rows;
		} else {
			terminate();
			return (new Vector(0));
		}
	}
}
