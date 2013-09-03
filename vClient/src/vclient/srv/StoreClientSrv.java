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
 * 功能：添加新商品（管理员），按关键字查询，按类型查询，购物车结算
 * @author goclis
 *
 */
public class StoreClientSrv extends ClientService {
	public StoreClientSrv() {
		super();
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
			return ObjectTransformer.getGoodList(msg.getData());
		}
		
		return null;
	}

	public double buyGoods(ArrayList<ShoppingItem> list, User user) {
		if (socket != null) {
			Message msg = new Message(MessageType.STORE_BUY, list, user);
		}
		
		return -1.0;
	}
}
