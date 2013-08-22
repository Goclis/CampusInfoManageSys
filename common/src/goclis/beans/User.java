package goclis.beans;

import java.io.Serializable;

public class User implements Serializable {
	private String id; 
	private String password;
	private Integer idNum;
	private String name;
	private String sex;
	private String department;
	private String major;
	private String identity;
	
	public User() {
		
	}
	
	public User(String id, String pwd) {
		this.id = id;
		this.password = pwd;
	}

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
	public Integer getIdNum() {
		return idNum;
	}
	public void setIdNum(Integer idNum) {
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
