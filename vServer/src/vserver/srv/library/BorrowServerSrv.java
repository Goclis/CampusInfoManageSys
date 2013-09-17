/**
 * 
 */
package vserver.srv.library;

import java.sql.SQLException;
import java.util.Vector;

import vserver.dao.library.BorrowDao;
import vserver.dao.library.PunishmentDao;

import common.util.MessageStatusCode;
import common.util.MessageType;
import common.vo.Message;
import common.vo.library.Book;
import common.vo.library.Borrow;
import common.vo.library.Reader;

/**
 * borrow在服务器端的通信 实现借书，查找借阅书籍，查找惩罚表，挂失书籍，续借，还书
 * 
 * @author zhongfang
 * 
 */
public class BorrowServerSrv {

	private vserver.srv.ServerSrvHelper serverSrvHelper;

	public BorrowServerSrv(vserver.srv.ServerSrvHelper serverSrvHelper2) {
		this.serverSrvHelper = serverSrvHelper2;
	}

	public Message borrowBook(Reader reader, Book book) {
		if (reader == null || book == null) {
			return Message.createFailureMessage();
		} else {
			Borrow borrow = null;
			try {
				borrow = BorrowDao.borrowBook(reader, book);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msgRt = new Message(MessageType.BORROW_BOOK);
			if (borrow != null) {
				msgRt.setStatusCode(MessageStatusCode.SUCCESS);
				msgRt.setData(borrow);
				msgRt.setSender(reader);
			} else {
				msgRt.setStatusCode(MessageStatusCode.FAILED);
			}
			return msgRt;
		}
	}

	public Message findBorrow(Reader reader, Integer findType) {
		if (reader == null) {
			return Message.createFailureMessage();
		} else {
			Vector vector = new Vector(0);
			try {
				vector = BorrowDao.findBorrow(reader, findType);
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

	public Message returnBook(Reader reader, Book book) {
		if (reader == null || book == null) {
			return Message.createFailureMessage();
		} else {
			boolean borrowRt = false;
			try {
				borrowRt = BorrowDao.returnBook(reader, book);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Message msgRt = new Message(MessageType.RETURN_BOOK);
			if (borrowRt) {
				msgRt.setStatusCode(MessageStatusCode.SUCCESS);
				msgRt.setData(book);
				msgRt.setSender(reader);
			} else {
				msgRt.setStatusCode(MessageStatusCode.FAILED);
				msgRt.setSender(reader);
			}
			return msgRt;
		}
	}

	public Message renewBook(Reader reader, Book book) {
		if (reader == null || book == null) {
			return Message.createFailureMessage();
		} else {
			boolean renewRt = false;
			try {
				renewRt = BorrowDao.renewBook(reader, book);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			Message msgRt = new Message(MessageType.RETURN_BOOK);
			if (renewRt) {
				msgRt.setStatusCode(MessageStatusCode.SUCCESS);
				msgRt.setData(book);
				msgRt.setSender(reader);
			} else {
				msgRt.setStatusCode(MessageStatusCode.FAILED);
				msgRt.setSender(reader);
			}
			return msgRt;
		}
	}

	public Message findPunishment(Reader reader) {
		if (reader == null) {
			return Message.createFailureMessage();
		} else {
			Vector findpRt = new Vector(0);
			try {
				findpRt = PunishmentDao.findPunishment(reader);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			Message msgRt = new Message(MessageType.RETURN_BOOK);
			if (findpRt.size() != 0) {
				msgRt.setStatusCode(MessageStatusCode.SUCCESS);
				msgRt.setData(findpRt);
				msgRt.setSender(reader);
			} else {
				msgRt.setStatusCode(MessageStatusCode.FAILED);
				msgRt.setSender(reader);
			}
			return msgRt;
		}
	}

	public Message lossBook(Reader reader, Book book) {
		if (reader == null || book == null) {
			return Message.createFailureMessage();
		} else {
			boolean lossRt = false;
			try {
				lossRt = BorrowDao.lossBook(reader, book);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			Message msgRt = new Message(MessageType.LOSS_BOOK);
			if (lossRt) {
				msgRt.setStatusCode(MessageStatusCode.SUCCESS);
				msgRt.setData(book);
				msgRt.setSender(reader);
			} else {
				msgRt.setStatusCode(MessageStatusCode.FAILED);
				msgRt.setSender(reader);
			}
			return msgRt;
		}
	}
}
