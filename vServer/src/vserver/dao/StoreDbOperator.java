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
	
	/*
	 * 通过保存一个静态变量来实现同步，静态变量为一个装有<id, number>对的ArrayList,
	 * 一切商品的数量的获取都通过该ArrayList来得到，再通过一个静态方法来修改和添加
	 * 商品的数量，静态方法接受参数为（商品id，改变数量），通过查询匹配商品ID得到
	 * 相应的<id, number>对（即一个对象），再而对该对象进行同步！
	 */
	//private static ArrayList<ShoppingItem> dbList =  
	//		new ArrayList<ShoppingItem>();	// 保存商品的数量
	
	/**
	 * 添加新商品
	 * @return
	 */
	public boolean addNewGood(Good good) {
		String gName = good.getName();
		Integer gPrice = good.getPrice();
		Integer gNumber = good.getNumber();
		String gType = good.getType();
		
		// Connect to database
		try {
			Class.forName(CONN_URL);
			Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
			Statement stat = conn.createStatement();
			
			// 得到商品类型的ID
			String sqlGetType = "SELECT id FROM ci_goodType WHERE name = '" + gType + "'";
			ResultSet rs = stat.executeQuery(sqlGetType);
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
			return false;
		} catch (SQLException e) {
			System.out.println("Error when add new good");
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 按关键字查询
	 * @param key
	 * @return
	 */
	public ArrayList<Good> queryByKey(String key) {
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
			Statement stat = conn.createStatement();
			String sql = "SELECT ci_good.id, ci_good.name, ci_good.price, ci_good.number, ci_goodType.name " 
					+ "FROM ci_good INNER JOIN ci_goodType ON ci_good.gType_id = ci_goodType.id " +
					"WHERE ci_good.name LIKE '%" + key + "%'";
			ResultSet rs = stat.executeQuery(sql);
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
	 * @param type
	 * @return
	 */
	public ArrayList<Good> queryByType(String type) {
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
			Statement stat = conn.createStatement();
			String sql = "SELECT ci_good.id, ci_good.name, ci_good.price, ci_good.number, ci_goodType.name " 
					+ "FROM ci_good INNER JOIN ci_goodType ON ci_good.gType_id = ci_goodType.id " +
					"WHERE ci_goodType.name = '" + type + "'";
			ResultSet rs = stat.executeQuery(sql);
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
	 * 购物结算
	 * @param list
	 */
	public Integer buyGoods(ArrayList<ShoppingItem> list) {
		for (ShoppingItem item : list) {
			
		}
		return null;
	}
}
