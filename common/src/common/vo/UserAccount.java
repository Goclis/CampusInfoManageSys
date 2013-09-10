package common.vo;

import java.io.Serializable;

/**
 * 更新用户账户余额
 * @author goclis
 */
public class UserAccount implements Serializable {
	Integer userId;
	Double money;
	
	public UserAccount() {
		// TODO Auto-generated constructor stub
	}

	public UserAccount(int userId, double money) {
		this.userId = userId;
		this.money = money;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
}
