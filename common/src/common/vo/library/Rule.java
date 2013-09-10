/**
 * 
 */
package common.vo.library;

import java.io.Serializable;

/**
 * 规则类，对于不同的读者应有不同的规则
 * 属性有id，规则名称，最大借阅天数，最大续借天数，最大续借次数，保持预约天数，最大借阅书籍数量
 * @author zhongfang
 *
 */
public class Rule implements Serializable{

	private int ruleId;
	private String ruleName;
	private int maxBorrowDays,MaxRenewDays,MaxRenewTimes,keepOrderDays,maxBorrowBooks;
	
	public Rule(int ruleId,String ruleName,int maxBo,int maxR,int MaxRT,int kOD,int mBB){
		this.setRuleId(ruleId);
		this.setRuleName(ruleName);
		this.setMaxBorrowDays(maxBo);
		this.setMaxRenewDays(maxR);
		this.setMaxRenewTimes(MaxRT);
		this.setKeepOrderDays(kOD);
		this.setMaxBorrowBooks(mBB);
	}

	public Rule() {
		// TODO Auto-generated constructor stub
		this.setKeepOrderDays(0);
		this.setMaxBorrowBooks(0);
		this.setMaxBorrowDays(0);
		this.setMaxRenewDays(0);
		this.setMaxRenewTimes(0);
		this.setRuleId(0);
		this.setRuleName(null);
	}

	/**
	 * @return the ruleId
	 */
	public int getRuleId() {
		return ruleId;
	}

	/**
	 * @param ruleId the ruleId to set
	 */
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	/**
	 * @return the ruleName
	 */
	public String getRuleName() {
		return ruleName;
	}

	/**
	 * @param ruleName the ruleName to set
	 */
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	/**
	 * @return the maxBorrowDays
	 */
	public int getMaxBorrowDays() {
		return maxBorrowDays;
	}

	/**
	 * @param maxBorrowDays the maxBorrowDays to set
	 */
	public void setMaxBorrowDays(int maxBorrowDays) {
		this.maxBorrowDays = maxBorrowDays;
	}

	/**
	 * @return the maxRenewDays
	 */
	public int getMaxRenewDays() {
		return MaxRenewDays;
	}

	/**
	 * @param maxRenewDays the maxRenewDays to set
	 */
	public void setMaxRenewDays(int maxRenewDays) {
		MaxRenewDays = maxRenewDays;
	}

	/**
	 * @return the maxRenewTimes
	 */
	public int getMaxRenewTimes() {
		return MaxRenewTimes;
	}

	/**
	 * @param maxRenewTimes the maxRenewTimes to set
	 */
	public void setMaxRenewTimes(int maxRenewTimes) {
		MaxRenewTimes = maxRenewTimes;
	}

	/**
	 * @return the keepOrderDays
	 */
	public int getKeepOrderDays() {
		return keepOrderDays;
	}

	/**
	 * @param keepOrderDays the keepOrderDays to set
	 */
	public void setKeepOrderDays(int keepOrderDays) {
		this.keepOrderDays = keepOrderDays;
	}

	/**
	 * @return the maxBorrowBooks
	 */
	public int getMaxBorrowBooks() {
		return maxBorrowBooks;
	}

	/**
	 * @param maxBorrowBooks the maxBorrowBooks to set
	 */
	public void setMaxBorrowBooks(int maxBorrowBooks) {
		this.maxBorrowBooks = maxBorrowBooks;
	}
	
}
