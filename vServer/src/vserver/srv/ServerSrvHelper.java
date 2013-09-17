package vserver.srv;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import vserver.srv.library.BookServerSrv;
import vserver.srv.library.BorrowServerSrv;
import vserver.srv.library.OrderServerSrv;
import vserver.srv.library.ReaderServerSrv;
import vserver.srv.library.RuleServerSrv;

import common.util.MessageType;
import common.util.ObjectTransformer;
import common.vo.Message;
import common.vo.User;
import common.vo.UserAccount;
import common.vo.course.Course;
import common.vo.course.CourseMark;
import common.vo.library.Book;
import common.vo.library.Order;
import common.vo.library.Reader;
import common.vo.library.Rule;
import common.vo.schoolroll.MessageToStuDel;
import common.vo.schoolroll.MessageToStuQue;
import common.vo.schoolroll.StudentInfo;
import common.vo.store.Good;
import common.vo.store.ShoppingItem;

public class ServerSrvHelper implements Runnable {
	public static void main(String[] args) {
		new ServerSrvHelper();
	}

	private Socket socket;
	private ObjectInputStream fromClient;
	private ObjectOutputStream toClient;

	private boolean closed;

	public ServerSrvHelper() {
		this(null);
	}

	public ServerSrvHelper(Socket socket) {
		this.socket = socket;
		this.closed = false;
	}

	/**
	 * 关闭线程
	 */
	public void close() {
		this.closed = true;
	}

	/**
	 * 处理接受到的Message并返回相应的反馈Message
	 * 
	 * @param msg
	 * @return
	 */
	private Message dealMessage(Message msg) {
		Integer type = msg.getType();

		// TODO: 考虑设置返回的Message的状态域，以标识更多的失败原因，比如服务器端问题（数据库错误）

		if (type.equals(MessageType.USER_LOGIN) // 用户管理模块
				|| type.equals(MessageType.USER_REGISTER)
				|| type.equals(MessageType.USER_LOGOUT)
				|| type.equals(MessageType.USER_QUERY_ACCOUNT)
				|| type.equals(MessageType.USER_UPDATE_ACCOUNT)
				|| type.equals(MessageType.USER_QUERY_ALL_ACCOUNT)) {
			UserManageServerSrv userManageSrv = new UserManageServerSrv(this);

			if (type.equals(MessageType.USER_LOGIN)) { // 登录
				User user = ObjectTransformer.getUser(msg.getData());
				return userManageSrv.login(user);
			} else if (type.equals(MessageType.USER_REGISTER)) { // 注册
				User user = ObjectTransformer.getUser(msg.getData());
				return userManageSrv.register(user);
			} else if (type.equals(MessageType.USER_LOGOUT)) { // 登出
				User user = ObjectTransformer.getUser(msg.getData());
				return userManageSrv.logout(user);
			} else if (type.equals(MessageType.USER_QUERY_ACCOUNT)) { // 查询用户余额
				User user = ObjectTransformer.getUser(msg.getData());
				return userManageSrv.queryAccount(user);
			} else if (type.equals(MessageType.USER_UPDATE_ACCOUNT)) { // 更新用户账户
				ArrayList<UserAccount> accounts = ObjectTransformer
						.getAccountList(msg.getData());
				return userManageSrv.updateAccounts(accounts);
			} else if (type.equals(MessageType.USER_QUERY_ALL_ACCOUNT)) { // 查询所有用户账户
				return userManageSrv.queryAllAccount();
			}
		} else if (type.equals(MessageType.STORE_ADD_NEW_GOOD) // 商店模块
				|| type.equals(MessageType.STORE_QUERY_BY_KEY)
				|| type.equals(MessageType.STORE_QUERY_BY_TYPE)
				|| type.equals(MessageType.STORE_BUY)) {
			StoreServerSrv storeSrv = new StoreServerSrv();

			if (type.equals(MessageType.STORE_ADD_NEW_GOOD)) { // 添加新商品
				Good good = ObjectTransformer.getGood(msg.getData());
				return storeSrv.addNewGood(good);
			} else if (type.equals(MessageType.STORE_QUERY_BY_KEY)) { // 按关键字搜索
				String key = ObjectTransformer.getString(msg.getData());
				return storeSrv.queryByKey(key);
			} else if (type.equals(MessageType.STORE_QUERY_BY_TYPE)) { // 按类型搜索
				String queryType = ObjectTransformer.getString(msg.getData());
				return storeSrv.queryByType(queryType);
			} else if (type.equals(MessageType.STORE_BUY)) { // 购物车结算
				ArrayList<ShoppingItem> goods = ObjectTransformer
						.getShoppingList(msg.getData());
				User user = ObjectTransformer.getUser(msg.getSender());
				return storeSrv.buyGoods(goods, user);
			}
		} else if (type.equals(MessageType.COURSE_USER_ADD)
				|| type.equals(MessageType.COURSE_QUERY_USER_ALL)
				|| type.equals(MessageType.COURSE_QUERY_ALL)
				|| type.equals(MessageType.COURSE_QUERY_STUDENT)
				|| type.equals(MessageType.COURSE_UPDATE_MARK)) { // 选课模块
			CourseServerSrv courseSrv = new CourseServerSrv();

			if (type.equals(MessageType.COURSE_USER_ADD)) { // 用户添加课程
				Course course = ObjectTransformer.getCourse(msg.getData());
				User user = ObjectTransformer.getUser(msg.getSender());
				return courseSrv.userAddCourse(course, user);
			} else if (type.equals(MessageType.COURSE_QUERY_USER_ALL)) { // 查询用户已选课程
				String userId = ObjectTransformer.getString(msg.getData());
				return courseSrv.queryUserCourse(userId);
			} else if (type.equals(MessageType.COURSE_QUERY_ALL)) { // 查询所有课程
				return courseSrv.queryAllCourse();
			} else if (type.equals(MessageType.COURSE_QUERY_STUDENT)) { // 查询选择了某门课的学生
				Integer courseId = ObjectTransformer.getInteger(msg.getData());
				User user = ObjectTransformer.getUser(msg.getSender());
				return courseSrv.queryStudentSelectTheCourse(courseId, user);
			} else if (type.equals(MessageType.COURSE_UPDATE_MARK)) { // 更新学生成绩
				ArrayList<CourseMark> marks = ObjectTransformer.getMarkList(msg
						.getData());
				return courseSrv.updateStudentMark(marks);
			}
		} else if (type.equals(MessageType.SCHOOLROLL_ADD_STU)
				|| type.equals(MessageType.SCHOOLROLL_QUERY_STU)
				|| type.equals(MessageType.SCHOOLROLL_DELETE_STU)) { // 学籍管理模块
			SchoolRollServerSrv sRServerSrv = new SchoolRollServerSrv();
			if (type.equals(MessageType.SCHOOLROLL_ADD_STU)) { // 新增学生信息
				StudentInfo stuInfo = ObjectTransformer
						.getObjStu(msg.getData());
				return sRServerSrv.addNewStuInfo(stuInfo);
			} else if (type.equals(MessageType.SCHOOLROLL_QUERY_STU)) { // 查询学生信息
				MessageToStuQue stuQuery = ObjectTransformer
						.getMessageToStuQue(msg.getData());
				return sRServerSrv.queryStuInfo(stuQuery);
			} else if (type.equals(MessageType.SCHOOLROLL_DELETE_STU)) { // 删除学生信息
				MessageToStuDel stuDel = ObjectTransformer
						.getMessageToStuDel(msg.getData());
				return sRServerSrv.deleteStuInfo(stuDel);
			}
		} else { // 图书馆模块
			Integer findType = msg.getFindType();
			if (type.equals(MessageType.BOOK_ADD)
					|| type.equals(MessageType.BOOK_DELETE)
					|| type.equals(MessageType.BOOK_FIND)
					|| type.equals(MessageType.BOOK_MODIFY)) {
				// System.out.println("ServerSrvHelper:服务器成功获取处理类型");
				BookServerSrv bookSrv = new BookServerSrv(this);
				Book book = ObjectTransformer.getBook(msg.getData());
				if (type.equals(MessageType.BOOK_ADD)) {
					return bookSrv.addBook(book);
				} else if (type.equals(MessageType.BOOK_DELETE)) {
					return bookSrv.deleteBook(book);
				} else if (type.equals(MessageType.BOOK_FIND)) {
					return bookSrv.findBook(book, findType);
				} else if (type.equals(MessageType.BOOK_MODIFY)) {
					return bookSrv.modifyBook(book);
				}
			} else if (type.equals(MessageType.READER_ADD)
					|| type.equals(MessageType.READER_DELETE)
					|| type.equals(MessageType.READER_FIND)
					|| type.equals(MessageType.READER_MODIFY)
					|| type.equals(MessageType.REPORT_LOSS_OF_READER)) {
				ReaderServerSrv readerSrv = new ReaderServerSrv(this);
				Reader reader = ObjectTransformer.getReader(msg.getData());
				if (type.equals(MessageType.READER_ADD))
					return readerSrv.addReader(reader);
				else if (type.equals(MessageType.READER_DELETE))
					return readerSrv.deleteReader(reader);
				else if (type.equals(MessageType.READER_FIND)) {
					findType = msg.getFindType();
					return readerSrv.findReader(reader, findType);
				} else if (type.equals(MessageType.READER_MODIFY))
					return readerSrv.modifyReader(reader);
				else if (type.equals(MessageType.REPORT_LOSS_OF_READER))
					return readerSrv.reportLossReader(reader);
			} else if (type.equals(MessageType.RULE_ADD)
					|| type.equals(MessageType.RULE_DELETE)
					|| type.equals(MessageType.RULE_FIND)
					|| type.equals(MessageType.RULE_MODIFY)) {
				RuleServerSrv ruleSrv = new RuleServerSrv(this);
				Rule rule = ObjectTransformer.getRule(msg.getData());
				if (type.equals(MessageType.RULE_ADD))
					return ruleSrv.addRule(rule);
				else if (type.equals(MessageType.RULE_FIND)) {
					return ruleSrv.findRule(rule, findType);
				} else if (type.equals(MessageType.RULE_DELETE))
					return ruleSrv.deleteRule(rule);
				else if (type.equals(MessageType.RULE_MODIFY))
					return ruleSrv.modifyRule(rule);
			} else if (type.equals(MessageType.BORROW_BOOK)
					|| type.equals(MessageType.RETURN_BOOK)
					|| type.equals(MessageType.FIND_BORROW)
					|| type.equals(MessageType.RENEW_BOOK)
					|| type.equals(MessageType.PUNISHMENT_FIND)
					|| type.equals(MessageType.LOSS_BOOK)) {
				BorrowServerSrv borrowSrv = new BorrowServerSrv(this);
				Book book = ObjectTransformer.getBook(msg.getData());
				Reader reader = ObjectTransformer.getReader(msg.getSender());
				if (type.equals(MessageType.BORROW_BOOK))
					return borrowSrv.borrowBook(reader, book);
				else if (type.equals(MessageType.RETURN_BOOK))
					return borrowSrv.returnBook(reader, book);
				else if (type.equals(MessageType.FIND_BORROW))
					return borrowSrv.findBorrow(reader, findType);
				else if (type.equals(MessageType.RENEW_BOOK))
					return borrowSrv.renewBook(reader, book);
				else if (type.equals(MessageType.PUNISHMENT_FIND))
					return borrowSrv.findPunishment(reader);
				else if (type.equals(MessageType.LOSS_BOOK))
					return borrowSrv.lossBook(reader, book);
			} else if (type.equals(MessageType.ORDER_BOOK)
					|| type.equals(MessageType.CANCEL_ORDER)
					|| type.equals(MessageType.FIND_ORDER)) {
				OrderServerSrv orderSrv = new OrderServerSrv(this);
				Reader reader = ObjectTransformer.getReader(msg.getSender());
				if (type.equals(MessageType.ORDER_BOOK)) {
					Book book = ObjectTransformer.getBook(msg.getData());
					return orderSrv.orderBook(reader, book);
				} else if (type.equals(MessageType.CANCEL_ORDER)) {
					Order order = ObjectTransformer.getOrder(msg.getData());
					return orderSrv.cancelOrder(order);
				} else if (type.equals(MessageType.FIND_ORDER))
					return orderSrv.findOrder(reader);
			}
		}

		return Message.createFailureMessage();
	}

	@Override
	public void run() {
		while (!closed) {
			try {
				// 从客户端获得Message
				fromClient = new ObjectInputStream(socket.getInputStream());
				Message msg = ObjectTransformer.getMessage(fromClient
						.readObject());

				// 将反馈发回客户端
				Message msgRet = dealMessage(msg);
				toClient = new ObjectOutputStream(socket.getOutputStream());
				toClient.writeObject(msgRet);
				toClient.flush();
			} catch (IOException e) {
				System.out.println("本线程已关闭");
				this.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				this.close();
			} finally {
				this.close(); // 服务执行完毕关闭线程
			}
		}
	}
}
