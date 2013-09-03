package vclient.srv;

import java.util.ArrayList;

import common.beans.Good;
import common.beans.ShoppingItem;
import common.beans.User;

/**
 * 为客户端提供服务，将各个模块串联起来
 * 各个界面直接通过静态方法调用
 * @author goclis
 *
 */
public class ClientSrvHelper {
	private ClientSrvHelper() {} // 防止实例化
	
	// ---------- 用户管理模块 BEGIN -----------
	
	/**
	 * 使用UserManageClientSrv执行注册服务
	 * @param user -- 要登录的用户
	 * @return 成功则原样返回，失败则返回null
	 */
	public static User register(User user) {
		UserManageClientSrv uMClientSrv = new UserManageClientSrv();
		return uMClientSrv.register(user);
	}
	
	/**
	 * 使用UserManageClientSrv执行登录服务
	 * @param user -- 要登录的用户
	 * @return 如果登录成功，返回数据域被填充完成的User，否则，返回null表示登录失败
	 */
	public static User login(User user) {
		UserManageClientSrv uMClientSrv = new UserManageClientSrv();
		return uMClientSrv.login(user);
	}
	
	/**
	 * 使用UserManageClientSrv执行登出服务
	 * @param user -- 要登录的用户
	 * @return 登出成功返回user，失败返回null
	 */
	public static User logout(User user) {
		UserManageClientSrv uMClientSrv = new UserManageClientSrv();
		return uMClientSrv.logout(user);
	}

	// ------------ 用户管理模块 END ---------------
	
	// ------------ 商店模块 BEGIN -----------------
	
	/**
	 * 使用StoreClientSrv执行添加商品服务
	 * @param good -- 要添加的商品
	 * @return 添加成功返回原商品，失败返回null
	 */
	public static Good addNewGood(Good good) {
		StoreClientSrv storeClientSrv = new StoreClientSrv();
		return storeClientSrv.addNewGood(good);
	}
	
	/**
	 * 按关键字查询商品
	 * @param key -- 关键字
	 * @return 查询成功则返回商品列表，失败返回null
	 */
	public static ArrayList<Good> queryByKey(String key) {
		StoreClientSrv storeClientSrv = new StoreClientSrv();
		return storeClientSrv.queryByKey(key);
	}
	
	/**
	 * 按类别查询商品
	 * @param type -- 类别
	 * @return 查询成功则返回商品列表，失败返回null
	 */
	public static ArrayList<Good> queryByType(String type) {
		StoreClientSrv storeClientSrv = new StoreClientSrv();
		return storeClientSrv.queryByType(type);
	}
	
	public static double buyGoods(ArrayList<ShoppingItem> list, User user) {
		StoreClientSrv storeClientSrv = new StoreClientSrv();
		return storeClientSrv.buyGoods(list, user);
	}
	
	// ------------ 商店模块 ENG -------------------
}
