package vserver.srv;

import goclis.beans.Message;
import goclis.beans.User;
import goclis.util.MessageStatusCode;
import goclis.util.MessageType;
import goclis.util.ObjectTransformer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
				fromClient = new ObjectInputStream(socket.getInputStream());
				Message msg = ObjectTransformer.getMessage(fromClient.readObject());
				// System.out.println(msg.getName());
				
				Message msgRet = dealMessage(msg);
				toClient = new ObjectOutputStream(socket.getOutputStream());
				//msgRet.setName("反馈");
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

	private Message dealMessage(Message msg) {
		Integer type = msg.getType();
		
		if (type.equals(MessageType.USER_LOGIN)) { // 登录
			//Object user = msg.getData();
			User user = ObjectTransformer.getUser(msg.getData());
			if (user == null) {
				// TODO: 返回登录失败的Message
				// return ...
			}
			
			// TODO: 校验数据库中的User
			// ...
			
			// TODO: 此处随意返回一个Message用于测试
			System.out.println("登录");
			Message msgRt = new Message();
			msgRt.setType(MessageType.USER_LOGIN);
			msgRt.setStatusCode(MessageStatusCode.SUCCESS); // 登录成功
			msgRt.setData(user); // TODO: User需要数据库响应数据的填充
			return msgRt;
		}
		Message msgRt = new Message();
		msgRt.setType(MessageType.USER_LOGIN);
		msgRt.setStatusCode(MessageStatusCode.FAILED); // 登录成功
		return msgRt;
	}
}
