package goclis.beans;

/**
 * 定义Message状态码
 * @author goclis
 *
 */
public final class MessageStatusCode {
	public final static Integer EMPTY = null; // 表示无状态
	public final static Integer SUCCESS = 1;
	public final static Integer FAILED = 2;
	
	/**
	 * 拒绝生成实例
	 */
	private MessageStatusCode() {
	}
}
