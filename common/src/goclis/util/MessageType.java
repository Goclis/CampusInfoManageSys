package goclis.util;

/**
 * 定义发送的Message的类型，即Message的type域值
 * @author goclis
 *
 */
public final class MessageType {
	public static final Integer EMPTY = null; // 无标识
	public static final Integer USER_LOGIN = 1; // 标识为登录请求之间的Message
	public static final Integer USER_REGISTER = 2; // 标识为注册请求之间的Message
	public static final Integer USER_LOGOUT = 3; // 标识为的登出
	
	/**
	 * 拒绝实例化
	 */
	private MessageType() {
	}
}
