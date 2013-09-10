/**
 * 
 */
package vserver.srv.library;

import java.sql.SQLException;
import java.util.Vector;

import vserver.dao.library.RuleDao;

import common.util.MessageStatusCode;
import common.util.MessageType;
import common.vo.Message;
import common.vo.library.Rule;

/**
 * rule在服务器端的通信 有rule的增删改查
 * 
 * @author zhongfang
 * 
 */
public class RuleServerSrv {

	private vserver.srv.ServerSrvHelper serverSrvHelper;

	/**
	 * 
	 */
	public RuleServerSrv(vserver.srv.ServerSrvHelper serverSrvHelper2) {
		// TODO Auto-generated constructor stub
		this.serverSrvHelper = serverSrvHelper2;
	}

	public Message addRule(Rule rule) {
		if (rule == null) {
			return Message.createFailureMessage();
		} else {
			boolean addRs = false;
			try {
				addRs = RuleDao.add(rule);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			Message msgRt = new Message(MessageType.RULE_ADD);
			if (addRs) {
				msgRt.setStatusCode(MessageStatusCode.SUCCESS);
				msgRt.setData(rule);
			} else {
				msgRt.setSender(MessageStatusCode.FAILED);
				// msgRt.setData(null);
			}
			return msgRt;
		}
	}

	public Message findRule(Rule rule, Integer findType) {
		if (rule == null) {
			return Message.createFailureMessage();
		} else {
			Vector vector = new Vector(0);
			try {
				vector = RuleDao.findRule(rule, findType);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Message msgRt;
			msgRt = new Message(MessageType.RULE_FIND);
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

	public Message deleteRule(Rule rule) {
		if (rule == null) {
			return Message.createFailureMessage();
		} else {
			boolean deleteRs = false;
			try {
				deleteRs = RuleDao.delete(rule);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			Message msgRt = new Message(MessageType.RULE_DELETE);
			if (deleteRs) {
				msgRt.setStatusCode(MessageStatusCode.SUCCESS);
				msgRt.setData(rule);
			} else {
				msgRt.setSender(MessageStatusCode.FAILED);
			}
			return msgRt;
		}
	}

	public Message modifyRule(Rule rule) {
		if (rule == null) {
			return Message.createFailureMessage();
		} else {
			boolean modifyRs = false;
			try {
				modifyRs = RuleDao.update(rule);
				modifyRs = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}

			Message msgRt = new Message(MessageType.RULE_MODIFY);
			if (modifyRs) {
				msgRt.setStatusCode(MessageStatusCode.SUCCESS);
				;
				msgRt.setData(rule);
				serverSrvHelper.close();
			} else {
				msgRt.setStatusCode(MessageStatusCode.FAILED);
			}
			return msgRt;
		}
	}
}
