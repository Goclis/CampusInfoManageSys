package vserver.srv;

import goclis.util.MessageStatusCode;
import goclis.util.MessageType;
import goclis.util.ObjectTransformer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import vserver.dao.UserManageDbOperator;

import common.beans.Good;
import common.beans.Message;
import common.beans.ShoppingItem;
import common.beans.User;

public class ServerSrvHelper implements Runnable {
	private int port = 8000;
	private Socket socket;
	private ObjectInputStream fromClient;
	private ObjectOutputStream toClient;
	private boolean closed;
	
	private ExecutorService threadPools = 
			Executors.newCachedThreadPool(); // 线程池
	
	public static void main(String[] args) {
		new ServerSrvHelper();
	}
	
	public ServerSrvHelper() {
		this(null);
	}

	public ServerSrvHelper(Socket socket) {
		this.socket = socket;
		this.closed = false;
	}

	@Override
	public void run() {
		while (!closed) {
			try {
				// 从客户端获得Message
				fromClient = new ObjectInputStream(socket.getInputStream());
				Message msg = ObjectTransformer.getMessage(fromClient.readObject());
				
				// 将反馈发回客户端
				Message msgRet = dealMessage(msg);
				toClient = new ObjectOutputStream(socket.getOutputStream());
				toClient.writeObject(msgRet);
				toClient.flush();
			} catch (IOException e) {
				System.out.println("客户端已关闭");
				this.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				this.close();
			}
		}
	}
	
	/**
	 * 处理接受到的Message并返回相应的反馈Message
	 * @param msg
	 * @return
	 */
	private Message dealMessage(Message msg) {
		Integer type = msg.getType(); 
		
		// TODO: 考虑设置返回的Message的状态域，以标识更多的失败原因，比如服务器端问题（数据库错误）
		
		if (type.equals(MessageType.USER_LOGIN)			// 用户管理模块
				|| type.equals(MessageType.USER_REGISTER)
				|| type.equals(MessageType.USER_LOGOUT)) {
			UserManageServerSrv userManageSrv = new UserManageServerSrv(this);
			User user = ObjectTransformer.getUser(msg.getData());
			
			if (type.equals(MessageType.USER_LOGIN)) { // 登录
				return userManageSrv.login(user);
			} else if (type.equals(MessageType.USER_REGISTER)) { // 注册
				return userManageSrv.register(user);
			} else if (type.equals(MessageType.USER_LOGOUT)) { // 登出
				return userManageSrv.logout(user);
			}
		} else if (type.equals(MessageType.STORE_ADD_NEW_GOOD)		// 商店模块
				|| type.equals(MessageType.STORE_QUERY_BY_KEY) 
				|| type.equals(MessageType.STORE_QUERY_BY_TYPE)
				|| type.equals(MessageType.STORE_BUY)) { 
			StoreServerSrv storeSrv = new StoreServerSrv();
			
			if (type.equals(MessageType.STORE_ADD_NEW_GOOD)) { // 添加新商品
				Good good = ObjectTransformer.getGood(msg.getData());
				return storeSrv.addNewGood(good);
			} else if (type.equals(MessageType.STORE_QUERY_BY_KEY)) { // 按关键字搜索
				String key = ObjectTransformer.getString(msg.getData());
				return storeSrv.queryByKey(key);
			} else if (type.equals(MessageType.STORE_QUERY_BY_TYPE)) { // 按类型搜索
				String queryType = ObjectTransformer.getString(msg.getData());
				return storeSrv.queryByType(queryType);
			} else if (type.equals(MessageType.STORE_BUY)) { // 购物车结算
				ArrayList<ShoppingItem> goods = ObjectTransformer.getShoppingList(msg.getData());
				User user = ObjectTransformer.getUser(msg.getSender());
				return storeSrv.buyGoods(goods, user);
			}
		}
		
		return Message.createFailureMessage();
	}
	
	/**
	 * 关闭线程
	 */
	public void close() {
		this.closed = true;
	}
}
