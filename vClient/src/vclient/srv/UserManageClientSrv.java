/**
 * 
 */
package vclient.srv;


import java.io.IOException;
import java.util.ArrayList;

import common.util.MessageStatusCode;
import common.util.MessageType;
import common.util.ObjectTransformer;
import common.vo.Message;
import common.vo.User;
import common.vo.UserAccount;

/**
 * 提供客户端的用户管理服务
 * 功能：登录，注册，登出
 * @author goclis
 */
public class UserManageClientSrv extends ClientService {
	public UserManageClientSrv() {
		super();
	}
	
	/**
	 * 处理注册
	 * @param user -- 要注册的用户
	 * @return 成功则原样返回，失败则返回null
	 */
	public User register(User user) {
		if (socket != null) {
			Message regMsg = new Message(MessageType.USER_REGISTER, user); // 封装登录请求Message
			Message msgBack = sendMessage(regMsg, "注册");
			return ObjectTransformer.getUser(msgBack.getData());
		}
		
		return null; // 以防异常
	}
	
	/**
	 * 处理登录
	 * @param user -- 要登录的用户
	 * @return 如果登录成功，返回数据域被填充完成的User，否则，返回null表示登录失败
	 */
	public User login(User user) {
		if (socket != null) {
			Message msgLogin = new Message(MessageType.USER_LOGIN, user); // 创建登录请求的Message
			Message msgBack = sendMessage(msgLogin, "登录");
			return ObjectTransformer.getUser(msgBack.getData());
		}
		
		return null; // 以防异常
	}
	
	/**
	 * 处理登出
	 * @param user -- 要登出的用户
	 * @return 登出成功返回user，失败返回null
	 */
	public User logout(User user) {
		if (socket != null) {
			Message msgLogout = new Message(MessageType.USER_LOGOUT, user); // 封装登出Message
			Message msgBack = sendMessage(msgLogout, "登出");
			return ObjectTransformer.getUser(msgBack.getData());
		}
		
		return null;
	}
	
	/**
	 * 查询用户余额
	 * @param user -- 用户
	 * @return 成功返回余额，否则为空
	 */
	public Double queryUserAccount(User user) {
		if (socket != null) {
			Message msgQueryAccount = new Message(MessageType.USER_QUERY_ACCOUNT, user);
			Message msgBack = sendMessage(msgQueryAccount, "查询用户余额");
			return ObjectTransformer.getDouble(msgBack.getData());
		}
		return null;
	}
	
	/**
	 * 更新用户余额
	 * @param accounts
	 * @return
	 */
	public boolean updateUserAccount(ArrayList<UserAccount> accounts) {
		if (socket != null) {
			Message msg = new Message(MessageType.USER_UPDATE_ACCOUNT, accounts);
			Message msgBack = sendMessage(msg, "更新用户账户");
			return (msgBack.getStatusCode().equals(MessageStatusCode.SUCCESS)) ? true : false;
		}
		return false;
	}
	
	/**
	 * 查询所有用户账户情况
	 * @return
	 */
	public ArrayList<UserAccount> queryAllAccount() {
		if (socket != null) {
			Message msg = new Message(MessageType.USER_QUERY_ALL_ACCOUNT);
			Message msgBack = sendMessage(msg, "查询所有用户账户");
			return ObjectTransformer.getAccountList(msgBack.getData());
		}
		return new ArrayList<UserAccount>();
	}
}
