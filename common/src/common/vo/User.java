package common.vo;

import java.io.Serializable;

public class User implements Serializable {
	private String id; // 一卡通号
	private String password; // 密码
	private String idNum; // 学号/工号
	private String name; // 姓名
	private String sex; // 性别
	private String department; // 院系
	private String major; // 专业
	private String identity; // 身份
	
	public User() {
		
	}
	
	public User(String id, String pwd, String identity) {
		this.id = id;
		this.password = pwd;
		this.identity = identity;
	}
	
	
	// Getters and Setters
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	
}
