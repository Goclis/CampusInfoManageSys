/**
 * 
 */
package common.vo.library;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 预约类，读者和书籍的关联类，包括id，读者，书籍，起始时间，终止时间
 * @author zhongfang
 *
 */
public class Order implements Serializable{

	private  int orderId;
	private Reader reader;
	private Book book;
	private String startDate,endDate;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

	public Order(Reader reader,Book book){
		this.setBook(book);
		this.setReader(reader);
		Calendar ca= Calendar.getInstance();
		Date d=ca.getTime();
		this.setStartDate(sdf.format(d));
		int days=reader.getRule().getKeepOrderDays();
		ca.add(Calendar.DATE, days);
		this.setEndDate(sdf.format(ca.getTime()));
	}
	public Order(){
		
	}
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
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
}
