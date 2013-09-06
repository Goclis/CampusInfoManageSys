/**
 * 
 */
package vserver.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import common.beans.Good;
import common.beans.ShoppingItem;
import common.beans.User;

/**
 * @author goclis
 *
 */
public class StoreDbOperator {
	// 数据库参数
	private static String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static String CONN_URL = "jdbc:mysql://localhost/campusInfo";
	private static String USER_NAME = "ci_manager";
	private static String PASSWORD = "qqqqqq";
	
	private Connection conn;
	private Statement stat;
	private ResultSet rs;
	
	/**
	 * 添加新商品
	 * @param good -- 要添加的商品
	 * @return 成功返回true，否则false
	 */
	public boolean addNewGood(Good good) {
		String gName = good.getName();
		Integer gPrice = good.getPrice();
		Integer gNumber = good.getNumber();
		String gType = good.getType();
		
		// Connect to database
		try {
			Class.forName(CONN_URL);
			conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
			stat = conn.createStatement();
			
			// 得到商品类型的ID
			String sqlGetType = "SELECT id FROM ci_goodType WHERE name = '" + gType + "'";
			rs = stat.executeQuery(sqlGetType);
			String gTypeId = "";
			if (rs.first()) {
				gTypeId = rs.getString(1);
			}
			
			// TODO: 考虑添加对同名商品插入的检查
			String sql = String.format("INSERT INTO ci_good (name, price, number, gType_id) values (%s, '%s', %s, %s, %s)",
					gName, gPrice, gNumber, gTypeId);
			stat.executeUpdate(sql);
			return true;
		} catch (ClassNotFoundException e) {
			System.out.println("Error when add new good");
			e.printStackTrace();
			
		} catch (SQLException e) {
			System.out.println("Error when add new good");
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 按关键字查询
	 * @param key -- 要查询的关键字
	 * @return 查询成功返回商品列表，否则返回null
	 */
	public ArrayList<Good> queryByKey(String key) {
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
			stat = conn.createStatement();
			String sql = "SELECT ci_good.id, ci_good.name, ci_good.price, ci_good.number, ci_goodType.name " 
					+ "FROM ci_good INNER JOIN ci_goodType ON ci_good.gType_id = ci_goodType.id " +
					"WHERE ci_good.name LIKE '%" + key + "%'";
			rs = stat.executeQuery(sql);
			ArrayList<Good> goods = new ArrayList<Good>();
			while (rs.next()) {
				Good good = new Good(rs.getString(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5));
				goods.add(good);
			}
			
			return goods;
		} catch (ClassNotFoundException e) {
			System.out.println("Error when query by key");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error when query by key");
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 按类型查询商品 
	 * @param type -- 要查询的类型
	 * @return 查询成功返回商品列表，否则返回null
	 */
	public ArrayList<Good> queryByType(String type) {
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
			stat = conn.createStatement();
			String sql = "SELECT ci_good.id, ci_good.name, ci_good.price, ci_good.number, ci_goodType.name " 
					+ "FROM ci_good INNER JOIN ci_goodType ON ci_good.gType_id = ci_goodType.id " +
					"WHERE ci_goodType.name = '" + type + "'";
			rs = stat.executeQuery(sql);
			ArrayList<Good> goods = new ArrayList<Good>();
			while (rs.next()) {
				Good good = new Good(rs.getString(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5));
				goods.add(good);
			}
			
			return goods;
		} catch (ClassNotFoundException e) {
			System.out.println("Error when query by type");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error when query by type");
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 购物结算，改变商品库存，改变用户账户余额
	 * @param list -- 要结算的list
	 * @param user -- 购物者
	 * @return 购物成功（不缺货）情况下返回null，缺货返回缺货商品的编号的列表
	 */
	public ArrayList<Integer> buyGoods(ArrayList<ShoppingItem> list, User user) {
		ArrayList<Integer> goodIds = null;
		double cost = 0.0; // 总消费
		double itemCost; // 单个商品消费
		for (ShoppingItem item : list) {
			if ((itemCost = dealWithShoppingItem(item)) < 0) { // 库存不足
				if (goodIds == null) {
					goodIds = new ArrayList<Integer>();
				}
				goodIds.add(item.getGoodId());
			} else if (goodIds == null) { // 如果已经出现库存不足则不再计数
				cost += itemCost;
			}
		}
		
		if ((goodIds == null) && updateAccount(user, -cost)) { // 无库存不足且扣费成功，即购物成功
			return null;
		} else {
			return goodIds;
		}
	}
	
	/**
	 * 更新用户账户，新余额 = 原余额 + money
	 * @param user -- 要更新的用户
	 * @param money -- 更新的数目
	 * @return 成功（账户余额不变为负数）返回true，否则返回false
	 */
	private boolean updateAccount(User user, double money) {
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
			stat = conn.createStatement();
			String sql = "SELECT money FROM ci_account WHERE user_id = " + user.getId();
			rs = stat.executeQuery(sql);
			
			if (rs.first()) {
				double userAccount = Double.valueOf(rs.getString(1));
				if (userAccount < money) { // 余额不足
					return false;
				} else {
					userAccount -= money;
					sql = "UPDATE TABLE ci_account SET money = " + userAccount 
							+ " WHERE user_id = " + user.getId();
					stat.executeUpdate(sql);
					return true;
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Error when update account");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error when update account");
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * 检查某一项购物需求是否能满足，即库存是否满足要购买数量
	 * @param item -- 要检查的购物列表中的一项
	 * @return 能满足则扣除该商品库存并返回消费价格，否则返回-1表示失败
	 */
	private double dealWithShoppingItem(ShoppingItem item) {
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
			stat = conn.createStatement();
			String sql = "SELECT number, price FROM ci_good WHERE id = " + item.getGoodId();
			rs = stat.executeQuery(sql);
			
			if (rs.first()) {
				Integer reserve = Integer.valueOf(rs.getString(1)); // 商品库存
				if (reserve < item.getNumber()) { // 库存不足
					return -1;
				} else { // 满足，改变库存
					Integer newNum = reserve - item.getNumber();
					sql = "UPDATE TABLE SET number = " + newNum + " WHERE id = " + item.getGoodId();
					stat.executeUpdate(sql);
					double consume = item.getNumber() * Integer.valueOf(rs.getString(2)); // 计算价格
					return consume;
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Error when check item");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error when check item");
			e.printStackTrace();
		}
		
		return -1;
	}
}
