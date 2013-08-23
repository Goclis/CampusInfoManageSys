package goclis.util;

import goclis.beans.Message;
import goclis.beans.User;

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
}
