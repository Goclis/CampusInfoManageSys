package goclis.beans;

/**
 * 定义发送的Message的类型，即Message的type域值
 * @author goclis
 *
 */
public final class MessageType {
	public static final Integer EMPTY = null;
	public static final Integer USER_LOGIN = 1; // 标识Message为登录请求
	public static final Integer USER_REGISTER = 2; // 标识Message为注册请求
	
	/**
	 * 拒绝实例化
	 */
	private MessageType() {
	}
}
