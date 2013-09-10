/**
 * 
 */
package vserver.srv;


import java.sql.SQLException;
import java.util.ArrayList;

import vserver.dao.UserManageDbOperator;
import common.util.MessageStatusCode;
import common.util.MessageType;
import common.vo.Message;
import common.vo.User;
import common.vo.UserAccount;

/**
 * 提供服务器端用户管理模块的服务
 * @author goclis
 */
public class UserManageServerSrv {
	private ServerSrvHelper serverSrvHelper;

	public UserManageServerSrv(ServerSrvHelper serverSrvHelper) {
		this.serverSrvHelper = serverSrvHelper;
	}

	public Message login(User user) {
		// 通信传输失败 OR ...
		if (user == null) {
			return Message.createFailureMessage();
		}
		
		// 校验数据库中的User
		boolean loginRs = false; // 校验结果
		try {
			UserManageDbOperator dbOperator = new UserManageDbOperator();
			loginRs = dbOperator.login(user);
		} catch (ClassNotFoundException e) {
			System.out.println("Driver Error during logining in");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Database Access Error during logining in");
			e.printStackTrace();
		}
		
		// 反馈Message
		Message msgRt = new Message(MessageType.USER_LOGIN);
		if (loginRs) {
			msgRt.setStatusCode(MessageStatusCode.SUCCESS); // 登录成功
			msgRt.setData(user); // 将已填充数据的user封装进Message
		} else {
			msgRt.setStatusCode(MessageStatusCode.FAILED); // 登录失败
			msgRt.setData(null); 
		}
		
		return msgRt;
	}

	public Message register(User user) {
		// 通信传输失败 OR ...
		if (user == null) {
			return Message.createFailureMessage();
		}
		
		// TODO: 校验数据库中是否已存在此用户，否则添加
		boolean registerRs = false;
		try {
			UserManageDbOperator dbOperator = new UserManageDbOperator();
			registerRs = dbOperator.register(user);
		} catch (ClassNotFoundException e) {
			System.out.println("Driver Error during registering");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Database Access Error during registering");
			e.printStackTrace();
		}
		
		Message msgRt = new Message(MessageType.USER_REGISTER);
		if (registerRs) {
			msgRt.setStatusCode(MessageStatusCode.SUCCESS);
			msgRt.setData(user);
		} else {
			msgRt.setStatusCode(MessageStatusCode.FAILED);
			msgRt.setData(null);
		}
		
		return msgRt;
	}

	public Message logout(User user) {
		// 通信传输失败 OR ...
		if (user == null) { 
			return Message.createFailureMessage();
		}
		
		// 校验数据库并修改用户状态域
		boolean logoutRs = false;
		try {
			UserManageDbOperator dbOperator = new UserManageDbOperator();
			logoutRs = dbOperator.logout(user);
		} catch (ClassNotFoundException e) {
			System.out.println("Driver Error during registering");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Database Access Error during registering");
			e.printStackTrace();
		}
		
		// 反馈Message
		Message msgRt = new Message(MessageType.USER_LOGOUT);
		if (logoutRs) {
			msgRt.setStatusCode(MessageStatusCode.SUCCESS);
			msgRt.setData(user);
			serverSrvHelper.close();// 关闭线程监听
		} else { // 登出失败。。。基本不会发生...
			msgRt.setStatusCode(MessageStatusCode.FAILED);
			msgRt.setData(null);
		}
		
		return msgRt;
	}
	
	/**
	 * 查询用户余额
	 * @param user -- 用户
	 * @return 成功data域为余额，否则为null
	 */
	public Message queryAccount(User user) {
		if (user == null) {
			return Message.createFailureMessage();
		}
		
		UserManageDbOperator dbOperator = new UserManageDbOperator();
		Double restMoney = dbOperator.queryAccount(user);
		
		Message msgRt = new Message(MessageType.USER_QUERY_ACCOUNT, restMoney);
		return msgRt;
	}
	
	/**
	 * 更新用户余额
	 * @param accounts
	 * @return
	 */
	public Message updateAccounts(ArrayList<UserAccount> accounts) {
		if (accounts == null) {
			return Message.createFailureMessage();
		}
		
		UserManageDbOperator dbOperator = new UserManageDbOperator();
		boolean bResult = dbOperator.updateAccounts(accounts);
		
		Message msgRt = new Message(MessageType.USER_UPDATE_ACCOUNT);
		msgRt.setStatusCode(bResult ? MessageStatusCode.SUCCESS : MessageStatusCode.FAILED);
		return msgRt;
	}
	
	/**
	 * 查询所有用户账户
	 * @return
	 */
	public Message queryAllAccount() {
		UserManageDbOperator dbOperator = new UserManageDbOperator();
		ArrayList<UserAccount> accounts = dbOperator.queryAllAccount();
		
		Message msgRt = new Message(MessageType.USER_QUERY_ALL_ACCOUNT, accounts);
		return msgRt;
	}
}
