package vclient.srv;

import goclis.beans.Message;
import goclis.beans.MessageStatusCode;
import goclis.beans.MessageType;
import goclis.beans.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSrvHelper {
	private Socket socket;
	private String host = "localhost";
	private int port = 8000;
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;
	
	public ClientSrvHelper() {
		try {
			socket = new Socket(host, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 验证传入的User是否可注册（服务器端）
	 * 成功则原样返回，失败则返回null
	 * @param user
	 * @return
	 */
	public User register(User user) {
		Message regMsg = new Message();
		regMsg.setName("test");
		regMsg.setData(user);
		regMsg.setType(1); // 1--注册
		try {			
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(regMsg);
			toServer.flush();
			
			fromServer = new ObjectInputStream(socket.getInputStream());
			Object obj = fromServer.readObject();
			if (obj != null) {
				Message msg = (Message) obj;
				System.out.println(msg.getStatusCode());
			}
			// System.out.println("Write Message");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 处理登录
	 * 如果登录成功，返回数据域被填充完成的User，否则，返回null表示登录失败
	 * @return User
	 */
	public User login(User user) {
		// 创建登录请求的Message
		Message msgLogin = Message.loginMessage(user);
		
		try {
			// 将请求发送到服务器
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgLogin);
			
			// 从服务器取回结果
			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = (Message) fromServer.readObject();
			
			return (User) msgBack.getData();
			//System.out.println(msgBack.getType() + " " + msgBack.getStatusCode());
			/*if (msgBack.getType() == MessageType.USER_LOGIN
					&& msgBack.getStatusCode() == MessageStatusCode.SUCCESS) {
				System.out.println("登录成功");
			}*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null; // 以防异常
	}
}