package common.beans;

import goclis.util.MessageStatusCode;
import goclis.util.MessageType;

import java.io.Serializable;
import java.util.Enumeration;


public class Message implements Serializable {
	private Long uid; // 标识客户端身份
	private String name; // TODO: 考虑删除
	private Integer type;
	private Integer statusCode;
	private Object data;
	private Object sender;
	
	public Message() {
		uid = 111111111111111111L;
		name = null;
		type = MessageType.EMPTY;
		statusCode = MessageStatusCode.EMPTY;
		data = null;
		sender = null;
	}
	
	// Setters and Getters
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Object getSender() {
		return sender;
	}
	public void setSender(Object sender) {
		this.sender = sender;
	}
	
	/**
	 * 创建用于注册某个user的Message
	 * @return
	 */
	public static Message registerMessage(User user) {
		Message msg = new Message();
		msg.setType(MessageType.USER_REGISTER);
		msg.setData(user);
		return msg;
	}
	
	/**
	 * 创建用于登录某个user的Message
	 * @param user
	 * @return
	 */
	public static Message loginMessage(User user) {
		Message msg = new Message();
		msg.setType(MessageType.USER_LOGIN);
		msg.setData(user);
		return msg;
	}
}
