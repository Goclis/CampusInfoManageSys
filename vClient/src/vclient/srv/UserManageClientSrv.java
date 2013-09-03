/**
 * 
 */
package vclient.srv;

import goclis.util.MessageType;
import goclis.util.ObjectTransformer;

import java.io.IOException;
import common.beans.Message;
import common.beans.User;

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
	
}
