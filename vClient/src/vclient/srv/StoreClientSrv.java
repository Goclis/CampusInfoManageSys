/**
 * 
 */
package vclient.srv;

import goclis.util.MessageType;
import goclis.util.ObjectTransformer;

import java.util.ArrayList;

import common.beans.Good;
import common.beans.Message;
import common.beans.ShoppingItem;
import common.beans.User;

/**
 * 为客户端题提供商店模块的服务
 * <p>功能：<p>添加新商品（管理员），<p>按关键字查询，<p>按类型查询，<p>购物车结算
 * @author goclis
 */
public class StoreClientSrv extends ClientService {
	public StoreClientSrv() {
		super(); // 直接使用父类构造函数形成socket
	}
	
	/**
	 * 添加新商品
	 * @param good -- 要添加的商品
	 * @return 添加成功返回原商品，失败返回null
	 */
	public Good addNewGood(Good good) {
		if (socket != null) {
			Message msg = new Message(MessageType.STORE_ADD_NEW_GOOD, good); // 封装Message
			Message msgBack = sendMessage(msg, "添加新商品");
			return ObjectTransformer.getGood(msgBack.getData());
		}
		
		return null;
	}
	
	/**
	 * 按关键字查询商品
	 * @param key -- 关键字
	 * @return 查询成功则返回商品列表，失败返回null
	 */
	public ArrayList<Good> queryByKey(String key) {
		if (socket != null) {
			Message msg = new Message(MessageType.STORE_QUERY_BY_KEY, key); // 封装Message
			Message msgBack = sendMessage(msg, "按关键字查询");
			return ObjectTransformer.getGoodList(msgBack.getData());
		}
		
		return null;
	}
	
	/**
	 * 按类别查询商品
	 * @param type -- 类别
	 * @return 查询成功则返回商品列表，失败返回null
	 */
	public ArrayList<Good> queryByType(String type) {
		if (socket != null) {
			Message msg = new Message(MessageType.STORE_QUERY_BY_TYPE, type); // 封装Message
			Message msgBack = sendMessage(msg, "按类型查询");
			return ObjectTransformer.getGoodList(msgBack.getData());
		}
		
		return null;
	}
	
	/**
	 * 结算购物车
	 * @param list -- 要购买的商品（商品编号+数量）的列表
	 * @param user -- 购买商品的用户
	 * @return 购买成功返回null，否则返回出问题（<b>缺货</b>）的商品的编号的列表，
	 * 如果列表为空（不为null说明通信失败，即socket==null），不为空为缺货
	 */
	public ArrayList<Integer> buyGoods(ArrayList<ShoppingItem> list, User user) {
		if (socket != null) {
			Message msg = new Message(MessageType.STORE_BUY, list, user);
			Message msgBack = sendMessage(msg, "结算购物车");
			return ObjectTransformer.getGoodIds(msgBack.getData());
		}
		
		return new ArrayList<Integer>();
	}
}
