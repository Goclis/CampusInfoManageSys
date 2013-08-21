package goclis.beans;

import java.io.Serializable;
import java.util.Enumeration;


public class Message implements Serializable {
	private Long uid; // 标识客户端身份
	private String name; 
	private Integer type;
	private String statusCode;
	private Object data;
	private Object sender;
	
	public Message() {
		uid = 111111111111111111L;
		name = "t";
		type = 0;
		statusCode = "test";
		data = null;
		sender = null;
	}
	
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
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
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
