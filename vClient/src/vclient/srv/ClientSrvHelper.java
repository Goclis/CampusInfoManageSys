package vclient.srv;

import goclis.beans.Message;
import goclis.beans.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSrvHelper {
	private Socket socket;
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;
	
	public ClientSrvHelper() {
	}
	
	public ClientSrvHelper(Socket socket) {
		this.socket = socket;
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
			//toServer.writeObject(regMsg);
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(regMsg);
			toServer.flush();
			// System.out.println("Write Message");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
