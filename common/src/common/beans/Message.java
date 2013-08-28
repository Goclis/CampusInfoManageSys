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
	private Object sender;
	
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
	public Message(Integer type) {
		this.type = type;
		this.statusCode = MessageStatusCode.EMPTY;
		this.data = null;
		this.sender = null;
	}
	
	public Message(Integer type, Integer status) {
		this.type = type;
		this.statusCode = status;
		this.data = null;
		this.sender = null;
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
	public Object getSender() {
		return sender;
	}
	public void setSender(Object sender) {
		this.sender = sender;
	}
}
