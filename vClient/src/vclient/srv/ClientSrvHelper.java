package vclient.srv;

import goclis.util.MessageType;
import goclis.util.ObjectTransformer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import common.beans.Message;
import common.beans.User;

public class ClientSrvHelper {
	private Socket socket;
	private String host = "localhost";
	private int port = 8000;
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;
	
	public ClientSrvHelper() {
		// 创建socket保持联系
		try {
			socket = new Socket(host, port);
		} catch (UnknownHostException e) {
			System.out.println("不确定的主机");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("客户端连接服务器失败");
			e.printStackTrace();
		}
	}
	
	// ---------- 用户管理模块 BEGIN -----------
	
	/**
	 * 验证传入的User是否可注册（服务器端）
	 * 成功则原样返回，失败则返回null
	 * @param user
	 * @return
	 */
	public User register(User user) {
		// 封装登录请求Message
		Message regMsg = new Message(MessageType.USER_REGISTER);
		regMsg.setData(user);
		
		try {
			// 发送请求至服务器
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(regMsg);
			toServer.flush();
			
			// 从服务器取回反馈
			fromServer = new ObjectInputStream(socket.getInputStream());
			Object obj = fromServer.readObject();
			Message msg = ObjectTransformer.getMessage(obj);
			
			User userRt = ObjectTransformer.getUser(msg.getData());
			
			return userRt;
		} catch (ClassNotFoundException e) {
			System.out.println("注册Error");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("注册Error");
			e.printStackTrace();
		} 
		
		return null; // 以防异常
	}
	
	/**
	 * 处理登录
	 * 如果登录成功，返回数据域被填充完成的User，否则，返回null表示登录失败
	 * @return User
	 */
	public User login(User user) {
		// 创建登录请求的Message
		Message msgLogin = new Message(MessageType.USER_LOGIN);
		msgLogin.setData(user);
		
		try {
			// 将请求发送到服务器
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgLogin);
			toServer.flush();
			
			// 从服务器取回结果
			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer.readObject());
			
			return ObjectTransformer.getUser(msgBack.getData());
		} catch (IOException e) {
			System.out.println("登录Error");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("登录Error");
			e.printStackTrace();
		} 
		
		return null; // 以防异常
	}
	
	/**
	 * 处理登出
	 * 登出成功返回user，失败返回null
	 */
	public User logout(User user) {
		// 封装登出Message
		Message msgLogout = new Message(MessageType.USER_LOGOUT);
		msgLogout.setData(user);
		
		try {
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgLogout);
			toServer.flush();
			
			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer.readObject());
			
			return ObjectTransformer.getUser(msgBack.getData());
		} catch (IOException e) {
			System.out.println("登出Error");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("登出Error");
			e.printStackTrace();
		} 
		
		return null;
	}

	// ------------ 用户管理模块 END ---------------
}
