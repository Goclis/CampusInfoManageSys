/**
 * 
 */
package vclient.srv;

import goclis.util.ObjectTransformer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import common.beans.Message;

/**
 * 客户端服务类的抽象父类
 * @author goclis
 *
 */
public class ClientService {
	protected Socket socket;
	protected String host = "localhost";
	protected int port = 8000;
	
	protected ClientService() {
		try {
			socket = new Socket(host, port);
		} catch (UnknownHostException e) {
			System.out.println("不确定的主机");
			e.printStackTrace();
			socket = null;
		} catch (IOException e) {
			System.out.println("客户端连接服务器失败");
			e.printStackTrace();
			socket = null;
		}
	}
	
	/**
	 * 向服务器发送消息
	 * @param msg -- 要发送的消息
	 * @param operator -- 所执行的服务
	 * @return 反馈Message
	 */
	protected Message sendMessage(Message msg, String operator) {
		try {
			ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msg);
			toServer.flush();
			
			ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());
			return ObjectTransformer.getMessage(fromServer.readObject());
		} catch (IOException e) {
			System.out.println("Error when " + operator);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Error when " + operator);
			e.printStackTrace();
		}
		
		return Message.createFailureMessage();
	}
}
