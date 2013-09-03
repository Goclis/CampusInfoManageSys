package common.beans;

import goclis.util.MessageStatusCode;
import goclis.util.MessageType;

import java.io.Serializable;
import java.util.Enumeration;


public class Message implements Serializable {
	private Long uid; // 标识客户端身份
	private Integer type;
	private Integer statusCode;
	private Object data;
	private User sender;
	
	public Message() {
		uid = 111111111111111111L;
		type = MessageType.EMPTY;
		statusCode = MessageStatusCode.EMPTY;
		data = null;
		sender = null;
	}
	
	/**
	 * 根据所给Message类型构造
	 * @param type
	 */
	public Message(Integer type, Object data) {
		this.type = type;
		this.data = data;
	}
	
	/**
	 * [Sender] send a message of [type] with a [data]
	 * @param type -- Message 类型
	 * @param data -- Message 包含的数据
	 * @param sender -- 发送者
	 */
	public Message(Integer type, Object data, User sender) {
		this.type = type;
		this.data = data;
		this.sender = sender;
	}

	public Message(Integer type) {
		this.type = type;
	}
	
	/**
	 * 失败Message的Factory
	 * @return
	 */
	public static Message createFailureMessage() {
		Message msgRt = new Message();
		msgRt.setData(null);
		msgRt.setStatusCode(MessageStatusCode.FAILED);
		return msgRt;
	}

	// Setters and Getters
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
}
