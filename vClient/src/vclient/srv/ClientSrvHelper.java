package vclient.srv;

import java.util.ArrayList;

import common.beans.Course;
import common.beans.CourseMark;
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
	 * 如果列表为空（不为null说明通信失败，即socket==null），不为空为缺货
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

	public static boolean updateStudentMark(ArrayList<CourseMark> marks) {
		CourseClientSrv courseClientSrv = new CourseClientSrv();
		return courseClientSrv.updateStudentMark(marks);
	}
	
	// ------------ 选课模块 END -------------------
}
