/**
 * 
 */
package common.vo.library;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 借阅类，书籍和读者的关联类，包括编号，读者，书籍，起始时间，应还书日期，实际还书日期，续借次数，状态（未还为false，已还书为true）
 * @author zhongfang
 *
 */
public class Borrow  implements Serializable{

	private int borrowId;
	private Reader reader;
	private Book book;
	private String startTime,deadLine,endTime;
	private int renewTimes;
	private boolean status;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

	
	/**
	 * 有参数的borrow构造函数
	 * @param reader
	 * @param book
	 */
	public Borrow(Reader reader,Book book) {
		this.setReader(reader);
		this.setBook(book);
		Calendar ca= Calendar.getInstance();
		Date d = ca.getTime();
		startTime=sdf.format(d);
		this.setStartTime(startTime);
		int maxDays=reader.getRule().getMaxBorrowDays();
		ca.add(Calendar.DATE, maxDays);
		d=ca.getTime();
		this.setDeadLine(sdf.format(d));
		this.setEndTime(null);
		this.setRenewTimes(0);
		this.setStatus(false);
	}
	/**
	 * 无参构造函数
	 */
	public Borrow(){
		this.setReader(null);
		this.setBook(null);
		this.setStartTime(null);
		this.setDeadLine(null);
		this.setEndTime(null);
		this.setRenewTimes(0);
		this.setStartTime(null);
	}
	/**
	 * @return the borrowId
	 */
	public int getBorrowId() {
		return borrowId;
	}


	/**
	 * @param borrowId the borrowId to set
	 */
	public void setBorrowId(int borrowId) {
		this.borrowId = borrowId;
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
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the deadLine
	 */
	public String getDeadLine() {
		return deadLine;
	}

	/**
	 * @param deadLine the deadLine to set
	 */
	public void setDeadLine(String deadLine) {
		this.deadLine = deadLine;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the renewTimes
	 */
	public int getRenewTimes() {
		return renewTimes;
	}

	/**
	 * @param renewTimes the renewTimes to set
	 */
	public void setRenewTimes(int renewTimes) {
		this.renewTimes = renewTimes;
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
