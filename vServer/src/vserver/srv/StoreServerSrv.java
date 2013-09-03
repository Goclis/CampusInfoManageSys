package vserver.srv;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import vserver.dao.StoreDbOperator;

import common.beans.Good;
import common.beans.Message;
import common.beans.ShoppingItem;
import common.beans.User;

import goclis.util.MessageStatusCode;
import goclis.util.MessageType;
import goclis.util.ObjectTransformer;

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
	
	public Message buyGoods(ArrayList<ShoppingItem> goods, User user) {
		StoreDbOperator dbOperator = new StoreDbOperator();
		ArrayList<Integer> goodIds = dbOperator.buyGoods(goods, user);
		
		Message msgRt = new Message(MessageType.STORE_BUY, goodIds);
		
		return msgRt;
	}
	
}
