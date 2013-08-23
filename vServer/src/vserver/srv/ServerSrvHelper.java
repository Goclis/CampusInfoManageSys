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

import common.beans.Message;
import common.beans.User;
import common.dao.DatabaseOperator;

public class ServerSrvHelper implements Runnable {
	private int port = 8000;
	private Socket socket;
	private ObjectInputStream fromClient;
	private ObjectOutputStream toClient;
	
	public static void main(String[] args) {
		new ServerSrvHelper();
	}
	
	public ServerSrvHelper() {
		
	}

	public ServerSrvHelper(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		while (true) {
			try {
				// 从客户端获得Message
				fromClient = new ObjectInputStream(socket.getInputStream());
				Message msg = ObjectTransformer.getMessage(fromClient.readObject());
				// System.out.println(msg.getName());
				
				// 将反馈发回客户端
				Message msgRet = dealMessage(msg);
				toClient = new ObjectOutputStream(socket.getOutputStream());
				//msgRet.setName("反馈");
				System.out.println(msgRet.getData() == null);
				toClient.writeObject(msgRet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		if (type.equals(MessageType.USER_LOGIN)) { // 登录
			//Object user = msg.getData();
			User user = ObjectTransformer.getUser(msg.getData());
			if (user == null) {
				// TODO: 返回登录失败的Message
				// return ...
			}
			
			// TODO: 校验数据库中的User
			// 成功则返回登录成功Message，并更新状态
			// 失败则返回登录失败Message
			boolean loginRs = false;
			try {
				loginRs = DatabaseOperator.login(user);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("Driver Error");
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Database Access Error");
				e.printStackTrace();
			}
			
			System.out.println("登录" + loginRs);
			Message msgRt = new Message();
			msgRt.setType(MessageType.USER_LOGIN);
			if (loginRs) {
				msgRt.setStatusCode(MessageStatusCode.SUCCESS); // 登录成功
				msgRt.setData(user); // TODO: User需要数据库响应数据的填充
			} else {
				msgRt.setStatusCode(MessageStatusCode.FAILED); // 登录失败
				msgRt.setData(null); 
			}
			
			return msgRt;
		} else if (type.equals(MessageType.USER_REGISTER)) { // 注册
			User user = ObjectTransformer.getUser(msg.getData());
			if (user == null) {
				// TODO: 返回注册失败的Message
				// return ...
			}
			
			// TODO: 校验数据库中是否已存在此用户，否则添加
			// 添加成功返回注册成功Message
			// 添加失败返回注册失败Message
			// ...
			
			// TODO: 此处随意返回提供测试
			System.out.println("注册");
			Message msgRt = new Message();
			msgRt.setType(MessageType.USER_REGISTER);
			msgRt.setStatusCode(MessageStatusCode.SUCCESS);
			msgRt.setData(user);
			return msgRt;
		}
		
		return null;
	}
}
