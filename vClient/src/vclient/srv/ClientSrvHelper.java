package vclient.srv;

import goclis.beans.Message;
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
	 */
	public void login() {
		Message msg = new Message();
		msg.setName("登录");
		
		try {
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msg);
			
			fromServer = new ObjectInputStream(socket.getInputStream());
			Message rt = (Message) fromServer.readObject();
			System.out.println(rt.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
