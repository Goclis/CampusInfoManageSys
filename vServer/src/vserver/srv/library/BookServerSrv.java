package vserver.srv.library;

import java.sql.SQLException;
import java.util.Vector;

import vserver.dao.library.BookDao;

import common.util.MessageStatusCode;
import common.util.MessageType;
import common.vo.Message;
import common.vo.library.Book;

/**
 * book管理在服务器端的通信 帮助实现书籍的增删改查
 * @author zhongfang
 */
public class BookServerSrv {
	private vserver.srv.ServerSrvHelper serverSrvHelper;

	public BookServerSrv(vserver.srv.ServerSrvHelper serverSrvHelper2) {
		this.serverSrvHelper = serverSrvHelper2;
	}

	public Message addBook(Book book) {
		if (book == null) {
			return Message.createFailureMessage();
		} else {
			boolean addRs = false;
			try {
				addRs = BookDao.add(book);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			Message msgRt = new Message(MessageType.BOOK_ADD);
			if (addRs) {
				msgRt.setStatusCode(MessageStatusCode.SUCCESS);
				msgRt.setData(book);
			} else {
				msgRt.setSender(MessageStatusCode.FAILED);
			}
			return msgRt;
		}
	}

	public Message deleteBook(Book book) {
		if (book == null) {
			return Message.createFailureMessage();
		} else {
			boolean deleteRs = false;
			try {
				deleteRs = BookDao.delete(book);
				deleteRs = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}

			Message msgRt = new Message(MessageType.BOOK_DELETE);
			if (deleteRs) {
				msgRt.setStatusCode(MessageStatusCode.SUCCESS);
				;
				msgRt.setData(book);
				serverSrvHelper.close();
			} else {
				msgRt.setStatusCode(MessageStatusCode.FAILED);
			}
			return msgRt;
		}
	}

	public Message findBook(Book book, Integer findType) {
		if (book == null) {
			return Message.createFailureMessage();
		} else {
			Vector vector = new Vector(0);
			try {
				vector = BookDao.findBook(book, findType);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Message msgRt;

			msgRt = new Message(MessageType.BOOK_FIND);
			msgRt.setFindType(findType);

			if (vector.size() != 0) {
				msgRt.setStatusCode(MessageStatusCode.SUCCESS);
				msgRt.setData(vector);
			} else {
				msgRt.setStatusCode(MessageStatusCode.FAILED);
			}
			return msgRt;
		}
	}

	public Message modifyBook(Book book) {
		if (book == null) {
			return Message.createFailureMessage();
		} else {
			boolean modifyRs = false;
			try {
				modifyRs = BookDao.update(book);
				modifyRs = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}

			Message msgRt = new Message(MessageType.BOOK_MODIFY);
			if (modifyRs) {
				msgRt.setStatusCode(MessageStatusCode.SUCCESS);
				;
				msgRt.setData(book);
				serverSrvHelper.close();
			} else {
				msgRt.setStatusCode(MessageStatusCode.FAILED);
			}
			return msgRt;
		}
	}
}