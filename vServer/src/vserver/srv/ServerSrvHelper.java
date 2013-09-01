package vserver.srv;

import goclis.util.MessageStatusCode;
import goclis.util.MessageType;
import goclis.util.ObjectTransformer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import vserver.dao.DatabaseOperator;

import common.beans.Message;
import common.beans.User;

public class ServerSrvHelper implements Runnable {
	private int port = 8000;
	private Socket socket;
	private ObjectInputStream fromClient;
	private ObjectOutputStream toClient;
	private boolean closed;
	
	public static void main(String[] args) {
		new ServerSrvHelper();
	}
	
	public ServerSrvHelper() {
		this(null);
	}

	public ServerSrvHelper(Socket socket) {
		this.socket = socket;
		this.closed = false;
	}

	@Override
	public void run() {
		while (!closed) {
			try {
				// 从客户端获得Message
				fromClient = new ObjectInputStream(socket.getInputStream());
				Message msg = ObjectTransformer.getMessage(fromClient.readObject());
				
				// 将反馈发回客户端
				Message msgRet = dealMessage(msg);
				toClient = new ObjectOutputStream(socket.getOutputStream());
				toClient.writeObject(msgRet);
				toClient.flush();
			} catch (IOException e) {
				// TODO 处理客户端断开后，如何结束线程
				e.printStackTrace();
				this.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				this.close();
			}
		}
	}
	
	/**
	 * 处理接受到的Message并返回相应的反馈Message
	 * @param msg
	 * @return
	 */
	private Message dealMessage(Message msg) {
		Integer type = msg.getType(); 
		
		// TODO: 可以考虑封装if...else...块中的某些行为
		// TODO: 考虑设置返回的Message的状态域，以标识更多的失败原因，比如服务器端问题（数据库错误）
		if (type.equals(MessageType.USER_LOGIN)) { // 登录
			User user = ObjectTransformer.getUser(msg.getData());
			if (user == null) {
				Message msgRt = new Message(MessageType.USER_LOGIN,
						MessageStatusCode.FAILED);
				msgRt.setData(null);
				return msgRt;
			}
			
			// 校验数据库中的User
			boolean loginRs = false; // 校验结果
			try {
				DatabaseOperator dbOperator = new DatabaseOperator();
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
		} else if (type.equals(MessageType.USER_REGISTER)) { // 注册
			User user = ObjectTransformer.getUser(msg.getData());
			if (user == null) {
				Message msgRt = new Message(MessageType.USER_REGISTER,
						MessageStatusCode.FAILED);
				msgRt.setData(null);
				return msgRt;
			}
			
			// TODO: 校验数据库中是否已存在此用户，否则添加
			boolean registerRs = false;
			try {
				DatabaseOperator dbOperator = new DatabaseOperator();
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
		} else if (type.equals(MessageType.USER_LOGOUT)) {
			User user = ObjectTransformer.getUser(msg.getData());
			
			// 通信传输失败 OR ...
			if (user == null) { 
				Message msgRt = new Message(MessageType.USER_LOGOUT,
						MessageStatusCode.FAILED);
				msgRt.setData(null);
				return msgRt;
			}
			
			// 校验数据库并修改用户状态域
			boolean logoutRs = false;
			try {
				DatabaseOperator dbOperator = new DatabaseOperator();
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
				msgRt.setSender(MessageStatusCode.SUCCESS);
				msgRt.setData(user);
				this.close();// 关闭线程监听
			} else { // 登出失败。。。基本不会发生...
				msgRt.setStatusCode(MessageStatusCode.FAILED);
				msgRt.setData(null);
			}
			
			return msgRt;
		}
		
		return null;
	}
	
	/**
	 * 关闭线程
	 */
	private void close() {
		this.closed = true;
	}
}
