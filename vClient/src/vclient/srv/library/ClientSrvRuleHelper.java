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
import common.vo.library.Rule;

/**
 * 帮助rule管理在客户端方面的通信 有rule的增。删，改。查
 * @author zhongfang
 */
public class ClientSrvRuleHelper {

	private Socket socket;
	private String host = "localhost";
	private int port = 8000;
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;

	/**
	 * 
	 */
	public ClientSrvRuleHelper() {
		// TODO Auto-generated constructor stub
		try {
			socket = new Socket(host, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Rule addRule(Rule rule) {
		Message msgaddRule = new Message(MessageType.RULE_ADD);
		msgaddRule.setData(rule);
//		System.out.println("在clienthelperRuleid" + rule.getRuleId());
//		System.out.println("设置好了Message");

		try {
			// 发送请求至服务器
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgaddRule);
			toServer.flush();
//			System.out.println("发送给了客户端");
			// TODO 避免发生Connection reset

			// 从服务器取回反馈
			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer
					.readObject());
//			System.out.println("从客户端取得返回");
			return ObjectTransformer.getRule(msgBack.getData());
		} catch (IOException e) {
			System.out.println("增加书籍出错");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("增加书籍出错");
			e.printStackTrace();
		}
		return null;
	}

	public Vector findRule(Rule rule, Integer type) {
		Message msgfindRule;
		msgfindRule = new Message(MessageType.RULE_FIND);
		msgfindRule.setFindType(type);
		msgfindRule.setData(rule);
//		System.out.println("在clienthelperRuleid" + rule.getRuleId());
//		System.out.println("设置好了Message");

		try {
			// 发送请求至服务器
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgfindRule);
			toServer.flush();
//			System.out.println("发送给了客户端");
			// TODO 避免发生Connection reset

			// 从服务器取回反馈
			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer
					.readObject());
//			System.out.println("从客户端取得返回");
			return ObjectTransformer.getVector(msgBack.getData());
		} catch (IOException e) {
			System.out.println("增加书籍出错");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("增加书籍出错");
			e.printStackTrace();
		}
		return null;
	}

	public Rule deleteRule(Rule rule) {
		Message msgdeleteRule = new Message(MessageType.RULE_DELETE);
		msgdeleteRule.setData(rule);
//		System.out.println("在clienthelperRuleid" + rule.getRuleId());
//		System.out.println("设置好了Message");

		try {
			// 发送请求至服务器
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgdeleteRule);
			toServer.flush();
//			System.out.println("发送给了客户端");
			// TODO 避免发生Connection reset

			// 从服务器取回反馈
			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer
					.readObject());
//			System.out.println("从客户端取得返回");
			return ObjectTransformer.getRule(msgBack.getData());
		} catch (IOException e) {
			System.out.println("增加书籍出错");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("增加书籍出错");
			e.printStackTrace();
		}
		return null;
	}

	public Rule modifyRule(Rule rule) {
		Message msgmodifyRule = new Message(MessageType.RULE_MODIFY);
		msgmodifyRule.setData(rule);

		try {
			toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(msgmodifyRule);
			toServer.flush();

			fromServer = new ObjectInputStream(socket.getInputStream());
			Message msgBack = ObjectTransformer.getMessage(fromServer
					.readObject());

			return ObjectTransformer.getRule(msgBack.getData());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
