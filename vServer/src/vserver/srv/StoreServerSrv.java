package vserver.srv;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import vserver.dao.StoreDbOperator;

import common.beans.Good;
import common.beans.Message;
import common.beans.ShoppingItem;

import goclis.util.MessageStatusCode;
import goclis.util.MessageType;
import goclis.util.ObjectTransformer;

public class StoreServerSrv implements Callable<Message> {
	private Integer messageType;
	private Object data;
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	@Override
	public Message call() throws Exception {
		if (messageType.equals(MessageType.STORE_ADD_NEW_GOOD)) { // 添加新商品
			Good good = ObjectTransformer.getGood(data);
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
		} else if (messageType.equals(MessageType.STORE_QUERY_BY_KEY)) { // 根据关键字查询
			String key = ObjectTransformer.getString(data);
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
		} else if (messageType.equals(MessageType.STORE_QUERY_BY_TYPE)) { // 根据类别查询
			String type = ObjectTransformer.getString(data);
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
		} else if (messageType.equals(MessageType.STORE_BUY)) { // 购物车结算
			ArrayList<ShoppingItem> list = ObjectTransformer.getList(data);
			StoreDbOperator dbOperator = new StoreDbOperator();
			dbOperator.buyGoods(list);
		}
		
		return Message.createFailureMessage();
	}
	
}
