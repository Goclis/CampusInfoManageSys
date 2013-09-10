/**
 * 
 */
package vclient.srv.library;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import common.util.MessageType;
import common.util.ObjectTransformer;
import common.vo.Message;
import common.vo.library.Book;
import common.vo.library.Order;
import common.vo.library.Reader;

/**
 * 帮助order类在客户端方面的通信 有预约书籍，寻找预约，取消预约
 * 
 * @author zhongfang
 * 
 */
public class ClientSrvOrderHelper {

	private Socket socket;
	private String host = "localhost";
	private int port = 8000;
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;

	/**
	 * 
	 */
	public ClientSrvOrderHelper() {
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

	public Order orderBook(Reader reader, Book book) {
		Message msgorderBook = new Message(MessageType.ORDER_BOOK);
		msgorderBook.setData(book);
		msgorderBook.setSender(reader);

		try {
			// 发送请求至服务器
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgorderBook);
			toServer.flush();
//			System.out.println("发送给了客户端");

			// 从服务器取回反馈
			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer
					.readObject());
//			System.out.println("从客户端取得返回");
			return ObjectTransformer.getOrder(msgBack.getData());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Order cancelOrder(Order order, Reader reader) {
		Message msgCancelOrder = new Message(MessageType.CANCEL_ORDER);
		msgCancelOrder.setData(order);
		msgCancelOrder.setSender(reader);
		try {
			// 发送请求至服务器
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgCancelOrder);
			toServer.flush();
//			System.out.println("发送给了客户端");

			// 从服务器取回反馈
			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer
					.readObject());
//			System.out.println("从客户端取得返回");
			return ObjectTransformer.getOrder(msgBack.getData());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Vector findOrders(Reader reader) {
		Message msgfindOrder = new Message(MessageType.FIND_ORDER);
		msgfindOrder.setSender(reader);
		msgfindOrder.setData(null);

		try {
			// 发送请求至服务器
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgfindOrder);
			toServer.flush();
//			System.out.println("发送给了客户端");

			// 从服务器取回反馈
			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer
					.readObject());
//			System.out.println("从客户端取得返回");
			return ObjectTransformer.getVector(msgBack.getData());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
