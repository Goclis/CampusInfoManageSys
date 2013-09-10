/**
 * 
 */
package common.vo.library;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 惩罚管理类，包括id，读者，书籍，原因，产生时间，结算时间，金额，状态（未付款为false，已付款为true）
 * @author zhongfang
 *
 */
public class Punishment  implements Serializable{

	private int id;
	private Reader reader;
	private Book book;
	private String reason,generateTime,punishedTime;
	private double amount;
	private boolean status;
	
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");

	/**
	 * 
	 */
	public Punishment(Reader reader,Book book) {
		this.setReader(reader);
		this.setBook(book);
		this.setAmount(0);
		Calendar ca=Calendar.getInstance();
		Date d=ca.getTime();
		this.setGenerateTime(sdf.format(d));
		this.setStatus(false);
	}

	public Punishment() {
	}

	/**
	 * @return the generateTime
	 */
	public String getGenerateTime() {
		return generateTime;
	}

	/**
	 * @param generateTime the generateTime to set
	 */
	public void setGenerateTime(String generateTime) {
		this.generateTime = generateTime;
	}

	/**
	 * @return the punishedTime
	 */
	public String getPunishedTime() {
		return punishedTime;
	}

	/**
	 * @param punishedTime the punishedTime to set
	 */
	public void setPunishedTime(String punishedTime) {
		this.punishedTime = punishedTime;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the reader
	 */
	public Reader getReader() {
		return reader;
	}

	/**
	 * @param reader the reader to set
	 */
	public void setReader(Reader reader) {
		this.reader = reader;
	}

	/**
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}

	/**
	 * @param book the book to set
	 */
	public void setBook(Book book) {
		this.book = book;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

}
