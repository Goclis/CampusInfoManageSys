package vserver.srv;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import vserver.dao.StoreDbOperator;

import common.util.MessageStatusCode;
import common.util.MessageType;
import common.util.ObjectTransformer;
import common.vo.Message;
import common.vo.User;
import common.vo.store.Good;
import common.vo.store.ShoppingItem;


public class StoreServerSrv {
	/**
	 * 添加新商品
	 * @param good -- 要添加的商品
	 * @return 反馈Message
	 */
	public Message addNewGood(Good good) {
		if (good == null) {
			return Message.createFailureMessage();
		}
		
		// 数据库处理
		boolean addStatus = false;
		StoreDbOperator dbOperator = new StoreDbOperator();
		addStatus = dbOperator.addNewGood(good);
		
		// 反馈Message
		Message msgRt = new Message(MessageType.STORE_ADD_NEW_GOOD);
		if (addStatus) {
			msgRt.setStatusCode(MessageStatusCode.SUCCESS);
			msgRt.setData(good);
		} else {
			msgRt.setStatusCode(MessageStatusCode.FAILED);
			msgRt.setData(null);
		}
		
		return msgRt;
	}
	
	/**
	 * 按关键字查询商品
	 * @param key -- 要查询的关键字
	 * @return 反馈Message
	 */
	public Message queryByKey(String key) {
		if (key == null) {
			return Message.createFailureMessage();
		}
		
		// 数据库处理
		StoreDbOperator dbOperator = new StoreDbOperator();
		ArrayList<Good> results = dbOperator.queryByKey(key); 
		
		Message msgRt = new Message(MessageType.STORE_QUERY_BY_KEY);
		if (results == null) {
			msgRt.setStatusCode(MessageStatusCode.FAILED);
			msgRt.setData(null);
		} else {
			msgRt.setStatusCode(MessageStatusCode.SUCCESS);
			msgRt.setData(results);
		}
		
		return msgRt;
	}
	
	/**
	 * 按类型查询商品
	 * @param type -- 要查询的类别
	 * @return 反馈Message
	 */
	public Message queryByType(String type) {
		if (type == null) {
			return Message.createFailureMessage();
		}
		
		// 数据库处理
		StoreDbOperator dbOperator = new StoreDbOperator();
		ArrayList<Good> results = dbOperator.queryByType(type);
		
		Message msgRt = new Message(MessageType.STORE_QUERY_BY_KEY);
		if (results == null) {
			msgRt.setStatusCode(MessageStatusCode.FAILED);
			msgRt.setData(null);
		} else {
			msgRt.setStatusCode(MessageStatusCode.SUCCESS);
			msgRt.setData(results);
		}
		
		return msgRt;
	}
	
	/**
	 * 结算购物车
	 * @param goods -- 要购买的商品（商品编号+数量）的列表
	 * @param user -- 购买商品的用户
	 * @return 购买成功返回null，否则返回出问题（<b>缺货</b>）的商品的编号的列表，
	 * 如果列表为空（不为null, 说明通信失败，即socket==null），不为空为缺货
	 */
	public Message buyGoods(ArrayList<ShoppingItem> goods, User user) {
		if (goods == null || user == null) {
			// 返回一个空ArrayList表示通信失败
			return new Message(MessageType.STORE_BUY, new ArrayList<Integer>());
		}
		
		StoreDbOperator dbOperator = new StoreDbOperator();
		ArrayList<Integer> goodIds = dbOperator.buyGoods(goods, user);
		
		Message msgRt = new Message(MessageType.STORE_BUY, goodIds);
		
		return msgRt;
	}
	
}
