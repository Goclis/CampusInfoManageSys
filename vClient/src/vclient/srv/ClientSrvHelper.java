package vclient.srv;

import java.util.ArrayList;
import java.util.Vector;

import vclient.srv.library.ClientSrvBookHelper;
import vclient.srv.library.ClientSrvBorrowHelper;
import vclient.srv.library.ClientSrvOrderHelper;
import vclient.srv.library.ClientSrvReaderHelper;
import vclient.srv.library.ClientSrvRuleHelper;

import common.vo.User;
import common.vo.UserAccount;
import common.vo.course.Course;
import common.vo.course.CourseMark;
import common.vo.library.Book;
import common.vo.library.Borrow;
import common.vo.library.Order;
import common.vo.library.Reader;
import common.vo.library.Rule;
import common.vo.schoolroll.MessageToStuDel;
import common.vo.schoolroll.MessageToStuQue;
import common.vo.schoolroll.StudentInfo;
import common.vo.store.Good;
import common.vo.store.ShoppingItem;

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
	
	/**
	 * 查询用户余额
	 * @param user -- 用户
	 * @return 成功返回余额，否则为null
	 */
	public static Double queryUserAccount(User user) {
		UserManageClientSrv uMClientSrv = new UserManageClientSrv();
		return uMClientSrv.queryUserAccount(user);
	}
	
	/**
	 * 更新用户账户
	 * @param accounts
	 * @return
	 */
	public static boolean updateUserAccount(ArrayList<UserAccount> accounts) {
		UserManageClientSrv uMClientSrv = new UserManageClientSrv();
		return uMClientSrv.updateUserAccount(accounts);
	}
	
	/**
	 * 查询所有的用户账户
	 * @return
	 */
	public static ArrayList<UserAccount> queryAllAccount() {
		UserManageClientSrv uMClientSrv = new UserManageClientSrv();
		return uMClientSrv.queryAllAccount();
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
	 * 使用StoreClientSrv执行按关键字查询商品服务
	 * @param key -- 关键字
	 * @return 查询成功则返回商品列表，失败返回null
	 */
	public static ArrayList<Good> queryByKey(String key) {
		StoreClientSrv storeClientSrv = new StoreClientSrv();
		return storeClientSrv.queryByKey(key);
	}
	
	/**
	 * 使用StoreClientSrv执行按类别查询商品服务
	 * @param type -- 类别
	 * @return 查询成功则返回商品列表，失败返回null
	 */
	public static ArrayList<Good> queryByType(String type) {
		StoreClientSrv storeClientSrv = new StoreClientSrv();
		return storeClientSrv.queryByType(type);
	}
	
	/**
	 * 使用StoreClientSrv执行结算购物车服务
	 * @param list -- 要购买的商品（商品编号+数量）的列表
	 * @param user -- 购买商品的用户
	 * @return 购买成功返回null，否则返回出问题（<b>缺货</b>）的商品的编号的列表，
	 * 如果列表为空（不为null说明通信失败，即socket==null；或者是余额不足），不为空为缺货
	 */
	public static ArrayList<Integer> buyGoods(ArrayList<ShoppingItem> list, User user) {
		StoreClientSrv storeClientSrv = new StoreClientSrv();
		return storeClientSrv.buyGoods(list, user);
	}
	
	// ------------ 商店模块 ENG -------------------
	
	// ------------ 选课模块 BEGIN -----------------
	
	/**
	 * 使用CourseCLientSrv进行用户添加课程（学生选课）
	 * @param course -- 要添加的课程
	 * @param user -- 添加课程的用户
	 * @return 添加成功返回courseId，否则返回null
	 */
	public static Course userAddCourse(Course course, User user) {
		CourseClientSrv courseClientSrv = new CourseClientSrv();
		return courseClientSrv.userAddCourse(course, user);
	}
	
	/**
	 * 使用CourseCLientSrv进行查询用户课程
	 * @param user -- 要查询的用户
	 * @return 查询成功返回course的列表，否则为空列表
	 */
	public static ArrayList<Course> queryUserCourse(String userId) {
		CourseClientSrv courseClientSrv = new CourseClientSrv();
		return courseClientSrv.queryUserCourse(userId);
	}
	
	/**
	 * 使用CourseClientSrv查询所有课程
	 * @return
	 */
	public static ArrayList<Course> queryAllCourse() {
		CourseClientSrv courseClientSrv = new CourseClientSrv();
		return courseClientSrv.queryAllCourse();
	}
	
	/**
	 * 查询选了某们课的所有学生
	 * @param courseId -- 课程ID
	 * @param user -- 授课老师
	 * @return 学生列表（失败为空列表）
	 */
	public static ArrayList<CourseMark> queryStudentSelectTheCourse(Integer courseId,
			User user) {
		CourseClientSrv courseClientSrv = new CourseClientSrv();
		return courseClientSrv.queryStudentSelectTheCourse(courseId, user);
	}
	
	/**
	 * 更新学生成绩
	 * @param marks -- 学生成绩列表
	 * @return 成功返回true否则为false
	 */
	public static boolean updateStudentMark(ArrayList<CourseMark> marks) {
		CourseClientSrv courseClientSrv = new CourseClientSrv();
		return courseClientSrv.updateStudentMark(marks);
	}

	// ------------ 选课模块 END -------------------
	
	// ------------ 学籍管理模块 BEGIN -------------
	
	/**
	 * 添加学生信息
	 * @param stuInfor
	 * @return 成功则返回1
	 */
	public static int increatStuInfo(StudentInfo stuInfor) {
		SchoolRollClientSrv sRClientSrv = new SchoolRollClientSrv();
		return sRClientSrv.increatStuInfo(stuInfor);
	}
	
	/**
	 * 查询学生信息
	 * @param obj
	 * @return
	 */
	public static StudentInfo queryStudentInfo(MessageToStuQue obj) {
		SchoolRollClientSrv sRClientSrv = new SchoolRollClientSrv();
		return sRClientSrv.queryStudentInfo(obj);
	}
	
	/**
	 * 删除学生信息
	 * @param messageToStuDel
	 * @return 成功则返回删除的记录数，返回0为失败
	 */
	public static int deleteStuInfo(MessageToStuDel messageToStuDel) {
		SchoolRollClientSrv sRClientSrv = new SchoolRollClientSrv();
		return sRClientSrv.deleteStuInfo(messageToStuDel);
	}

	// ------------ 学籍管理模块 END ---------------
	
	// ------------ 图书馆模块 BEGIN ---------------
	
	public static Vector findReader(Reader reader, Integer byUserId) {
		ClientSrvReaderHelper readerClient = new ClientSrvReaderHelper();
		return readerClient.findReader(reader, byUserId);
	}

	public static Vector findRule(Rule rule, Integer byId) {
		ClientSrvRuleHelper clientRuleSrv = new ClientSrvRuleHelper();
		return clientRuleSrv.findRule(rule, byId);
	}

	public static Vector findBorrow(Reader reader, Integer past) {
		ClientSrvBorrowHelper borrowClient = new ClientSrvBorrowHelper();
		return borrowClient.findBorrow(reader, past);
	}

	public static Vector findPunishment(Reader reader) {
		ClientSrvBorrowHelper borrowClient = new ClientSrvBorrowHelper();
		return borrowClient.findPunishment(reader);
	}

	public static Reader modifyReader(Reader reader) {
		ClientSrvReaderHelper readerClient = new ClientSrvReaderHelper();
		return readerClient.modifyReader(reader);
	}

	public static Reader deleteReader(Reader reader) {
		ClientSrvReaderHelper readerClient = new ClientSrvReaderHelper();
		return readerClient.deleteReader(reader);
	}

	public static Vector findOrders(Reader reader) {
		ClientSrvOrderHelper orderClient = new ClientSrvOrderHelper();
		return orderClient.findOrders(reader);
	}

	public static Order cancelOrder(Order order, Reader reader) {
		ClientSrvOrderHelper orderClient = new ClientSrvOrderHelper();
		return orderClient.cancelOrder(order, reader);
	}

	public static Vector findBook(Book book, Integer findType) {
		ClientSrvBookHelper bookClient = new ClientSrvBookHelper();
		return bookClient.findBook(book, findType);
	}

	public static Borrow borrowBook(Reader reader, Book book) {
		ClientSrvBorrowHelper borrowClient = new ClientSrvBorrowHelper();
		return borrowClient.borrowBook(reader, book);
	}

	public static Order orderBook(Reader reader, Book book) {
		ClientSrvOrderHelper orderClient = new ClientSrvOrderHelper();
		return orderClient.orderBook(reader, book);
	}

	public static Reader reportLossReader(Reader reader) {
		ClientSrvReaderHelper readerClient = new ClientSrvReaderHelper();
		return readerClient.reportLossReader(reader);
	}

	public static Book returnBook(Reader reader, Book book) {
		ClientSrvBorrowHelper borrowClient = new ClientSrvBorrowHelper();
		return borrowClient.returnBook(reader, book);
	}

	public static Book renewBook(Reader reader, Book book) {
		ClientSrvBorrowHelper borrowClient = new ClientSrvBorrowHelper();
		return borrowClient.renewBook(reader, book);
	}

	public static Book lossBook(Reader reader, Book book) {
		ClientSrvBorrowHelper borrowClient = new ClientSrvBorrowHelper();
		return borrowClient.lossBook(reader, book);
	}

	public static Rule addRule(Rule rule) {
		ClientSrvRuleHelper clientSrv = new ClientSrvRuleHelper();
		return clientSrv.addRule(rule);
	}

	public static Rule modifyRule(Rule rule) {
		ClientSrvRuleHelper clientSrv = new ClientSrvRuleHelper();
		return clientSrv.modifyRule(rule);
	}

	public static Reader addReader(Reader reader) {
		ClientSrvReaderHelper clientSrv = new ClientSrvReaderHelper();
		return clientSrv.addReader(reader);
	}

	public static Book deleteBook(Book book) {
		ClientSrvBookHelper bookClientSrv = new ClientSrvBookHelper();
		return bookClientSrv.deleteBook(book);
	}

	public static Rule deleteRule(Rule rule) {
		ClientSrvRuleHelper ruleClientSrv = new ClientSrvRuleHelper();
		return ruleClientSrv.deleteRule(rule);
	}

	public static Book modifyBook(Book book) {
		ClientSrvBookHelper clientSrv = new ClientSrvBookHelper();
		return clientSrv.modifyBook(book);
	}

	public static Book addBook(Book book) {
		ClientSrvBookHelper clientSrv = new ClientSrvBookHelper();
		return clientSrv.addBook(book);
	}
	
	// ------------ 图书馆模块 ENG -----------------
}
