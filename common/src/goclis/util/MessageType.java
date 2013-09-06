package goclis.util;

/**
 * 定义发送的Message的类型，即Message的type域值
 * @author goclis
 *
 */
public final class MessageType {
	public static final Integer EMPTY = null; // 无标识
	
	// 用户管理模块
	public static final Integer USER_LOGIN = 1; // 标识为登录请求之间的Message
	public static final Integer USER_REGISTER = 2; // 标识为注册请求之间的Message
	public static final Integer USER_LOGOUT = 3; // 标识为的登出
	
	// 商店模块
	public static final Integer STORE_ADD_NEW_GOOD = 4; // 添加新商品 
	public static final Integer STORE_QUERY_BY_KEY = 5; // 根据关键字查询商品
	public static final Integer STORE_QUERY_BY_TYPE = 6; // 根据类别查询商品
	public static final Integer STORE_BUY = 7; // 购物车结算
	
	// 选课模块
	public static final Integer COURSE_USER_ADD = 8; // 用户添加课程
	public static final Integer COURSE_QUERY_USER_ALL = 9; // 查询用户所有课程
	public static final Integer COURSE_QUERY_ALL = 10; // 查询所有课程
	
	/**
	 * 拒绝实例化
	 */
	private MessageType() {
	}
}
