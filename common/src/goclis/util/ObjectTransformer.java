package goclis.util;

import java.util.ArrayList;

import common.beans.Good;
import common.beans.Message;
import common.beans.ShoppingItem;
import common.beans.User;

/**
 * 用来将Object转化为适当的类型
 * @author goclis
 *
 */
public final class ObjectTransformer {
	/**
	 * 私有化构造函数，防止实例化行为
	 */
	private ObjectTransformer() {
	}
	
	/**
	 * 将Object转化为User
	 * 成功则返回User，失败返回null
	 * @param Object
	 * @return User
	 */
	public static User getUser(Object obj) {
		if (obj != null) {
			return (User) obj;
		} else {
			return null;
		}
	}
	
	/**
	 * 将Object转化为Message
	 * 成功则返回Message，失败返回null
	 * @param Object
	 * @param Message
	 */
	public static Message getMessage(Object obj) {
		if (obj != null) {
			return (Message) obj;
		} else {
			return null;
		}
	}

	public static Good getGood(Object data) {
		if (data != null) {
			return (Good) data;
		} else {
			return null;
		}
	}

	public static String getString(Object data) {
		if (data != null) {
			return (String) data;
		} else {
			return null;
		}
	}

	public static ArrayList<ShoppingItem> getShoppingList(Object data) {
		if (data != null) {
			return (ArrayList<ShoppingItem>) data;
		} else {
			return null;
		}
	}

	public static ArrayList<Good> getGoodList(Object data) {
		if (data != null) {
			return (ArrayList<Good>) data;
		} else {
			return null;
		}
	}
}
