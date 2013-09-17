package vserver.srv.library;

import java.sql.SQLException;
import java.util.Vector;

import vserver.dao.library.ReaderDao;

import common.util.MessageStatusCode;
import common.util.MessageType;
import common.vo.Message;
import common.vo.library.Reader;

/**
 * reader在服务器的通信 实现reader的增删改查和挂失
 * 
 * @author zhongfang
 * 
 */
public class ReaderServerSrv {
	private vserver.srv.ServerSrvHelper serverSrvHelper;

	public ReaderServerSrv(vserver.srv.ServerSrvHelper serverSrvHelper2) {
		this.serverSrvHelper = serverSrvHelper2;
	}

	public Message addReader(Reader reader) {
		if (reader == null) {
			return Message.createFailureMessage();
		} else {
			boolean addRs = false;
			try {
				addRs = ReaderDao.add(reader);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			Message msgRt = new Message(MessageType.READER_ADD);
			if (addRs) {
				msgRt.setStatusCode(MessageStatusCode.SUCCESS);
				msgRt.setData(reader);
			} else {
				msgRt.setSender(MessageStatusCode.FAILED);
			}
			return msgRt;
		}
	}

	public Message deleteReader(Reader reader) {
		if (reader == null) {
			return Message.createFailureMessage();
		} else {
			boolean deleteRs = false;
			try {
				deleteRs = ReaderDao.delete(reader);
				deleteRs = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}

			Message msgRt = new Message(MessageType.READER_DELETE);
			if (deleteRs) {
				msgRt.setStatusCode(MessageStatusCode.SUCCESS);
				;
				msgRt.setData(reader);
				serverSrvHelper.close();
			} else {
				msgRt.setStatusCode(MessageStatusCode.FAILED);
			}
			return msgRt;
		}
	}

	public Message findReader(Reader reader, Integer findType) {
		if (reader == null) {
			return Message.createFailureMessage();
		} else {
			Vector vector = new Vector(0);
			try {
				vector = ReaderDao.findReader(reader, findType);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Message msgRt;
			msgRt = new Message(MessageType.READER_FIND);
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

	public Message modifyReader(Reader reader) {
		if (reader == null) {
			return Message.createFailureMessage();
		} else {
			boolean modifyRs = false;
			try {
				modifyRs = ReaderDao.update(reader);
				modifyRs = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}

			Message msgRt = new Message(MessageType.READER_MODIFY);
			if (modifyRs) {
				msgRt.setStatusCode(MessageStatusCode.SUCCESS);
				;
				msgRt.setData(reader);
				serverSrvHelper.close();
			} else {
				msgRt.setStatusCode(MessageStatusCode.FAILED);
			}
			return msgRt;
		}
	}

	public Message reportLossReader(Reader reader) {
		if (reader == null) {
			return Message.createFailureMessage();
		} else {
			boolean lossRs = false;
			try {
				lossRs = ReaderDao.reportLoss(reader);
				lossRs = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}

			Message msgRt = new Message(MessageType.READER_MODIFY);
			if (lossRs) {
				msgRt.setStatusCode(MessageStatusCode.SUCCESS);
				;
				msgRt.setData(reader);
				serverSrvHelper.close();
			} else {
				msgRt.setStatusCode(MessageStatusCode.FAILED);
			}
			return msgRt;
		}
	}
}
