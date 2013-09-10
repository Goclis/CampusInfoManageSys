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

import common.util.FindType;
import common.util.MessageType;
import common.util.ObjectTransformer;
import common.vo.Message;
import common.vo.library.Book;

/**
 * 帮助book管理在客户端方面的通信
 * 
 * @author zhongfang
 * 
 */
public class ClientSrvBookHelper {

	private Socket socket;
	private String host = "localhost";
	private int port = 8000;
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;

	public ClientSrvBookHelper() {
		try {
			socket = new Socket(host, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("不确定的主机");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("客户端连接服务器失败");
			e.printStackTrace();
		}
	}

	public Book addBook(Book book) {
		Message msgaddBook = new Message(MessageType.BOOK_ADD);
		msgaddBook.setData(book);
//		System.out.println("在clienthelperbookid" + book.getBookId());
//		System.out.println("设置好了Message");

		try {
			// 发送请求至服务器
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgaddBook);
			toServer.flush();
//			System.out.println("发送给了客户端");
			// TODO 避免发生Connection reset

			// 从服务器取回反馈
			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer
					.readObject());
//			System.out.println("从客户端取得返回");
			return ObjectTransformer.getBook(msgBack.getData());
		} catch (IOException e) {
			System.out.println("增加书籍出错");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("增加书籍出错");
			e.printStackTrace();
		}
		return null;
	}

	public Book deleteBook(Book book) {
		Message msgdeleteBook = new Message(MessageType.BOOK_DELETE);
		msgdeleteBook.setData(book);
		try {
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgdeleteBook);
			toServer.flush();

			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer
					.readObject());

			return ObjectTransformer.getBook(msgBack.getData());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Book modifyBook(Book book) {
		Message msgmodifyBook = new Message(MessageType.BOOK_MODIFY);
		msgmodifyBook.setData(book);

		try {
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgmodifyBook);
			toServer.flush();

			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer
					.readObject());

			return ObjectTransformer.getBook(msgBack.getData());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Vector findBook(Book book, Integer findType) {
		Message msgfindBook;
		if (findType.equals(FindType.BY_ID))
			msgfindBook = new Message(MessageType.BOOK_FIND);
		else
			msgfindBook = new Message(MessageType.BOOK_FIND);

		msgfindBook.setFindType(findType);
		msgfindBook.setData(book);

		try {
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgfindBook);
//			System.out.println("发送给了客户端");

			toServer.flush();

			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer
					.readObject());

			return ObjectTransformer.getVector(msgBack.getData());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
