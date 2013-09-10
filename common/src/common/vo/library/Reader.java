/**
 * 
 */
package common.vo.library;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;


import common.util.Variable;

/**
 * 读者证类，属性有id，一卡通号，注册日期，生效日期，失效日期，余额，状态，适用的规则
 * 默认情况下读者证的生效日期在注册日期的两日后，失效日期在生效日期的四年后
 * @author zhongfang
 *
 */
public class Reader implements Variable,Serializable{

	private int readerId;
	private String certificateTime,effectiveTime,expireTime,userId,status;
	private double money;
	private Rule rule;
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

	public Reader(int readerId,String userId,double money,Rule rule){
		this.setReaderId(readerId);
		this.setUserId(userId);
		
		Calendar ca =Calendar.getInstance();
		Date d = ca.getTime();
		certificateTime=sdf.format(d);
		ca.add(Calendar.DATE, 2);
		effectiveTime=sdf.format(ca.getTime());
		ca.add(Calendar.YEAR, 4);
		expireTime=sdf.format(ca.getTime());
		
		this.setRule(rule);
		this.setMoney(money);
	}
	
	/**
	 * @return the rule
	 */
	public Rule getRule() {
		return rule;
	}

	/**
	 * @param rule the rule to set
	 */
	public void setRule(Rule rule) {
		this.rule = rule;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Reader(){
		this.setReaderId(0);
		this.setUserId(null);
		this.setCertificateTime(null);
		this.setEffectiveTime(null);
		this.setExpireTime(null);
		this.setRule(null);
		this.setStatus(null);
		this.setMoney(0);
	}
	
	/**
	 * @return the certificateTime
	 */
	public String getCertificateTime() {
		return certificateTime;
	}
	/**
	 * @param certificateTime the certificateTime to set
	 */
	public void setCertificateTime(String certificateTime) {
		this.certificateTime = certificateTime;
	}
	/**
	 * @return the effectiveTime
	 */
	public String getEffectiveTime() {
		return effectiveTime;
	}
	/**
	 * @param effectiveTime the effectiveTime to set
	 */
	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	/**
	 * @return the expireTime
	 */
	public String getExpireTime() {
		return expireTime;
	}
	/**
	 * @param expireTime the expireTime to set
	 */
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	/**
	 * @return the readerId
	 */
	public int getReaderId() {
		return readerId;
	}
	/**
	 * @param readerId the readerId to set
	 */
	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param readerStatus the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the money
	 */
	public double getMoney() {
		return money;
	}
	/**
	 * @param money the money to set
	 */
	public void setMoney(double money) {
		this.money = money;
	}

}
