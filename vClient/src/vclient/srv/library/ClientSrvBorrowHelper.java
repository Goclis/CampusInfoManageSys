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
import common.vo.library.Borrow;
import common.vo.library.Reader;


/**
 * 帮助borrow管理在客户端方面的通信 有借书，寻找惩罚表，寻找借阅表，挂失书籍，续借，还书
 * 
 * @author zhongfang
 * 
 */
public class ClientSrvBorrowHelper {

	private Socket socket;
	private String host = "localhost";
	private int port = 8000;
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;

	public ClientSrvBorrowHelper() {
		try {
			socket = new Socket(host, port);
//			System.out.println("socket 创建成功");
		} catch (UnknownHostException e) {
			System.out.println("不确定的主机");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("客户端连接服务器失败");
			e.printStackTrace();
		}
	}

	public Borrow borrowBook(Reader reader, Book book) {
		Message msgborrowBook = new Message(MessageType.BORROW_BOOK);
		msgborrowBook.setData(book);
		msgborrowBook.setSender(reader);

		try {
			// 发送请求至服务器
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgborrowBook);
			toServer.flush();
//			System.out.println("发送给了客户端");

			// 从服务器取回反馈
			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer
					.readObject());
//			System.out.println("从客户端取得返回");
			return ObjectTransformer.getBorrow(msgBack.getData());
		} catch (IOException e) {
			System.out.println("增加书籍出错");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("增加书籍出错");
			e.printStackTrace();
		}
		return null;
	}

	public Vector findBorrow(Reader reader, Integer findType) {
		Message msgfindBorrow;
		msgfindBorrow = new Message(MessageType.FIND_BORROW);
		msgfindBorrow.setFindType(findType);
		msgfindBorrow.setSender(reader);

		try {
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgfindBorrow);
//			System.out.println("发送给了客户端");

			toServer.flush();

			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer
					.readObject());

			return ObjectTransformer.getVector(msgBack.getData());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Book returnBook(Reader reader, Book book) {
		Message msgreturn;
		msgreturn = new Message(MessageType.RETURN_BOOK);
		msgreturn.setData(book);
		msgreturn.setSender(reader);

		try {
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgreturn);
//			System.out.println("发送给了客户端");

			toServer.flush();

			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer
					.readObject());

			return ObjectTransformer.getBook(msgBack.getData());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Book renewBook(Reader reader, Book book) {
		Message msgrenew;
		msgrenew = new Message(MessageType.RENEW_BOOK);
		msgrenew.setData(book);
		msgrenew.setSender(reader);

		try {
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgrenew);
//			System.out.println("发送给了客户端");

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

	public Vector findPunishment(Reader reader) {
		Message msgfindPunish;
		msgfindPunish = new Message(MessageType.PUNISHMENT_FIND);
		msgfindPunish.setSender(reader);

		try {
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgfindPunish);
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

	public Book lossBook(Reader reader, Book book) {
		Message msglossBook;
		msglossBook = new Message(MessageType.LOSS_BOOK);
		msglossBook.setSender(reader);
		msglossBook.setData(book);

		try {
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msglossBook);
//			System.out.println("发送给了客户端");

			toServer.flush();

			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer
					.readObject());// NullPointerException

			return ObjectTransformer.getBook(msgBack.getData());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
