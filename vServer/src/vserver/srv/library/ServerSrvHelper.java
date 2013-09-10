package vserver.srv.library;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import common.util.MessageType;
import common.util.ObjectTransformer;
import common.vo.Message;
import common.vo.library.Book;
import common.vo.library.Order;
import common.vo.library.Reader;
import common.vo.library.Rule;

public class ServerSrvHelper implements Runnable {
	private int port = 8222;
	private Socket socket;
	private ObjectInputStream fromClient;
	private ObjectOutputStream toClient;
	private boolean closed;

	private ExecutorService threadPools = Executors.newCachedThreadPool(); // 线程池

	public static void main(String[] args) {
		new ServerSrvHelper();
	}

	public ServerSrvHelper() {
	}

	public ServerSrvHelper(Socket socket) {
		this.socket = socket;
		this.closed = false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!closed) {
			try {
				// TODO 注意Connection reset
				fromClient = new ObjectInputStream(socket.getInputStream());
				Message msg = ObjectTransformer.getMessage(fromClient
						.readObject());

				Message msgRet = dealMessage(msg);
				toClient = new ObjectOutputStream(socket.getOutputStream());
				toClient.writeObject(msgRet);
			} catch (IOException e) {
				e.printStackTrace();
				this.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				this.close();
			}
		}
	}

	private Message dealMessage(Message msg) {
		Integer type = msg.getType();
		Integer findType = msg.getFindType();
		if (type.equals(MessageType.BOOK_ADD)
				|| type.equals(MessageType.BOOK_DELETE)
				|| type.equals(MessageType.BOOK_FIND)
				|| type.equals(MessageType.BOOK_MODIFY)) {
			System.out.println("ServerSrvHelper:服务器成功获取处理类型");
			BookServerSrv bookSrv = new BookServerSrv(this);
			Book book = ObjectTransformer.getBook(msg.getData());
			if (type.equals(MessageType.BOOK_ADD)) {
				return bookSrv.addBook(book);
			} else if (type.equals(MessageType.BOOK_DELETE)) {
				return bookSrv.deleteBook(book);
			} else if (type.equals(MessageType.BOOK_FIND)) {
				findType = msg.getFindType();
				return bookSrv.findBook(book, findType);
			} else if (type.equals(MessageType.BOOK_MODIFY)) {
				return bookSrv.modifyBook(book);
			}

		} else if (type.equals(MessageType.READER_ADD)
				|| type.equals(MessageType.READER_DELETE)
				|| type.equals(MessageType.READER_FIND)
				|| type.equals(MessageType.READER_MODIFY)
				|| type.equals(MessageType.REPORT_LOSS_OF_READER)) {
			ReaderServerSrv readerSrv = new ReaderServerSrv(this);
			Reader reader = ObjectTransformer.getReader(msg.getData());
			if (type.equals(MessageType.READER_ADD))
				return readerSrv.addReader(reader);
			else if (type.equals(MessageType.READER_DELETE))
				return readerSrv.deleteReader(reader);
			else if (type.equals(MessageType.READER_FIND)) {
				findType = msg.getFindType();
				return readerSrv.findReader(reader, findType);
			} else if (type.equals(MessageType.READER_MODIFY))
				return readerSrv.modifyReader(reader);
			else if (type.equals(MessageType.REPORT_LOSS_OF_READER))
				return readerSrv.reportLossReader(reader);

		} else if (type.equals(MessageType.RULE_ADD)
				|| type.equals(MessageType.RULE_DELETE)
				|| type.equals(MessageType.RULE_FIND)
				|| type.equals(MessageType.RULE_MODIFY)) {
			RuleServerSrv ruleSrv = new RuleServerSrv(this);
			Rule rule = ObjectTransformer.getRule(msg.getData());
			if (type.equals(MessageType.RULE_ADD))
				return ruleSrv.addRule(rule);
			else if (type.equals(MessageType.RULE_FIND)) {
				findType = msg.getFindType();
				return ruleSrv.findRule(rule, findType);
			} else if (type.equals(MessageType.RULE_DELETE))
				return ruleSrv.deleteRule(rule);
			else if (type.equals(MessageType.RULE_MODIFY))
				return ruleSrv.modifyRule(rule);
		}

		else if (type.equals(MessageType.BORROW_BOOK)
				|| type.equals(MessageType.RETURN_BOOK)
				|| type.equals(MessageType.FIND_BORROW)
				|| type.equals(MessageType.RENEW_BOOK)
				|| type.equals(MessageType.PUNISHMENT_FIND)
				|| type.equals(MessageType.LOSS_BOOK)) {
			BorrowServerSrv borrowSrv = new BorrowServerSrv(this);
			Book book = ObjectTransformer.getBook(msg.getData());
			Reader reader = ObjectTransformer.getReader(msg.getSender());
			if (type.equals(MessageType.BORROW_BOOK))
				return borrowSrv.borrowBook(reader, book);
			else if (type.equals(MessageType.RETURN_BOOK))
				return borrowSrv.returnBook(reader, book);
			else if (type.equals(MessageType.FIND_BORROW))
				return borrowSrv.findBorrow(reader, findType);
			else if (type.equals(MessageType.RENEW_BOOK))
				return borrowSrv.renewBook(reader, book);
			else if (type.equals(MessageType.PUNISHMENT_FIND))
				return borrowSrv.findPunishment(reader);
			else if (type.equals(MessageType.LOSS_BOOK))
				return borrowSrv.lossBook(reader, book);
		}

		else if (type.equals(MessageType.ORDER_BOOK)
				|| type.equals(MessageType.CANCEL_ORDER)
				|| type.equals(MessageType.FIND_ORDER)) {
			OrderServerSrv orderSrv = new OrderServerSrv(this);
			Reader reader = ObjectTransformer.getReader(msg.getSender());
			if (type.equals(MessageType.ORDER_BOOK)) {
				Book book = ObjectTransformer.getBook(msg.getData());
				return orderSrv.orderBook(reader, book);
			} else if (type.equals(MessageType.CANCEL_ORDER)) {
				Order order = ObjectTransformer.getOrder(msg.getData());
				return orderSrv.cancelOrder(order);
			} else if (type.equals(MessageType.FIND_ORDER))
				return orderSrv.findOrder(reader);

		}
		return null;
	}

	public void close() {
		this.closed = true;
	}
}
