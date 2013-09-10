package common.util;

/**
 * 定义发送的Message的类型，即Message的type域值
 * 
 * @author goclis
 * 
 */
public final class MessageType {
	public static final Integer EMPTY = null; // 无标识

	// 用户管理模块
	public static final Integer USER_LOGIN = 1; // 标识为登录请求之间的Message
	public static final Integer USER_REGISTER = 2; // 标识为注册请求之间的Message
	public static final Integer USER_LOGOUT = 3; // 标识为的登出
	public static final Integer USER_QUERY_ACCOUNT = 4; // 查询用户账户余额
	public static final Integer USER_UPDATE_ACCOUNT = 5; // 更新用户余额
	public static final Integer USER_QUERY_ALL_ACCOUNT = 15; // 查询所有账户

	// 商店模块
	public static final Integer STORE_ADD_NEW_GOOD = 6; // 添加新商品
	public static final Integer STORE_QUERY_BY_KEY = 7; // 根据关键字查询商品
	public static final Integer STORE_QUERY_BY_TYPE = 8; // 根据类别查询商品
	public static final Integer STORE_BUY = 9; // 购物车结算

	// 选课模块
	public static final Integer COURSE_USER_ADD = 10; // 用户添加课程
	public static final Integer COURSE_QUERY_USER_ALL = 11; // 查询用户所有课程
	public static final Integer COURSE_QUERY_ALL = 12; // 查询所有课程
	public static final Integer COURSE_QUERY_STUDENT = 13; // 查询选了某们课的学生
	public static final Integer COURSE_UPDATE_MARK = 14; // 更新学生成绩

	// 学籍管理模块
	public static final Integer SCHOOLROLL_ADD_STU = 16; // 新增学生
	public static final Integer SCHOOLROLL_QUERY_STU = 17; // 查询学生信息
	public static final Integer SCHOOLROLL_DELETE_STU = 18; // 删除学生信息

	// 图书馆模块
	public static final Integer BOOK_ADD = 31;// 书籍的增
	public static final Integer BOOK_MODIFY = 32; // 改
	public static final Integer BOOK_DELETE = 33;// 删
	public static final Integer BOOK_FIND = 34;// 查

	public static final Integer READER_ADD = 35;// 读者证
	public static final Integer READER_MODIFY = 36;
	public static final Integer READER_DELETE = 37;
	public static final Integer READER_FIND = 38;

	public static final Integer RULE_ADD = 39;// 规则
	public static final Integer RULE_MODIFY = 40;
	public static final Integer RULE_DELETE = 41;
	public static final Integer RULE_FIND = 42;

	public static final Integer BORROW_BOOK = 43;// 借书
	public static final Integer RETURN_BOOK = 44;// 还书
	public static final Integer FIND_BORROW = 45;// 查询借阅
	public static final Integer RENEW_BOOK = 46;// 续借

	public static final Integer REPORT_LOSS_OF_READER = 47;// 挂失读者证
	public static final Integer PUNISHMENT_FIND = 48;// 查询惩罚表

	public static final Integer ORDER_BOOK = 49;// 预约书籍
	public static final Integer CANCEL_ORDER = 50;// 取消预约
	public static final Integer FIND_ORDER = 51;// 查询预约
	public static final Integer LOSS_BOOK = 52;// 挂失书籍

	/**
	 * 拒绝实例化
	 */
	private MessageType() {
	}
}
