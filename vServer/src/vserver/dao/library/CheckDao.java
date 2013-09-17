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

import common.vo.library.Order;

public class CheckDao {
	private static Connection conn = null;
	private static Statement state = null;
	private static ResultSet rs = null;
	private static String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static String CONN_URL = "jdbc:mysql://localhost/campusInfo";
	private static String USER_NAME = "ci_manager";
	private static String PASSWORD = "qqqqqq";
	private static int readerId, ruleId;
	private static int orderId;
	private static String certificateTime, effectiveTime, expireTime, userId,
			status;
	private static String startDate, endDate;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public static void initialize() {
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
			state = conn.createStatement();
			System.out.println("连接到了数据库");
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
		System.out.println("成功关闭资源");
	}

	public static void checkReaders() throws SQLException {
		Calendar ca = Calendar.getInstance();
		Date now = ca.getTime();
		Date certificate = null, effective = null, expire = null;
		initialize();
		String sql = "select Id,CertificateTime,EffectiveTime,ExpireTime,Status from ci_reader";
		rs = null;
		rs = state.executeQuery(sql);
		while (rs.next()) {
			readerId = rs.getInt(1);
			certificateTime = rs.getString(2);
			effectiveTime = rs.getString(3);
			expireTime = rs.getString(4);
			status = rs.getString(5);
			try {
				certificate = sdf.parse(certificateTime);
				effective = sdf.parse(effectiveTime);
				expire = sdf.parse(expireTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String up = "";
			if (now.after(effective) && (status.equals("未激活"))) { // 时间到，激活读者证
				status = "正常";
				up = "update ci_reader set Status='" + status + "'where Id="
						+ readerId + "";
				System.out.println("时间到，激活读者证");
			} else if (now.after(expire)) {
				up = "delete from ci_reader where Id=" + readerId + "";
				System.out.println("时间到，读者证过期，删除读者证");
			}
			state.executeUpdate(up);
			rs = null;
			terminate();
		}
	}

	public static void checkOrder() throws SQLException {
		initialize();
		Calendar ca = Calendar.getInstance();
		Date now = ca.getTime();
		Date end = null;
		String sql = "select Id,EndDate,Status from ci_order ";
		rs = state.executeQuery(sql);
		while (rs.next()) {
			orderId = rs.getInt("Id");
			Order order = new Order();
			order.setOrderId(orderId);
			endDate = rs.getString("EndDate");
			boolean status = rs.getBoolean("Status");
			try {
				end = sdf.parse(endDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (now.after(end))
				OrderDao.cancelOrder(order);
		}
	}
}
