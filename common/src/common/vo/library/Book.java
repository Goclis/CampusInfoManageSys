/**
 * 
 */
package common.vo.library;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 书籍信息，包括书籍Id，书名，作者，类型，索书号，馆藏地，出版社，出版时间，入馆时间，书籍状态，附注
 * @author zhongfang
 *
 */
public class Book implements Serializable{


	private int bookId;

	private String title,author,type,publisher,publishTime,callCode,description;
	String status;
	private String enterTime;
	private double price;
	private String storePlace;
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * @param id,name,author,storeId,type,publisher,
	 * publishTime,enterTime,status,callCode,description
	 */
	public Book(int bookId,String title,String author,String type,String callCode,String storePlace,
			String publisher,String publishTime,double price,String status){
		this.setBookId(bookId);
		this.setTitle(title);
		this.setAuthor(author);
		this.setType(type);
		this.setPublisher(publisher);
		this.setStorePlace(storePlace);
		this.setPublishTime(publishTime);
		if(enterTime==null){
			Calendar ca= Calendar.getInstance();
			Date enterDate = ca.getTime();
			enterTime=sdf.format(enterDate);
			this.setEnterTime(enterTime);
		}
		this.setStatus(status);
		this.setCallCode(callCode);
		this.setPrice(price);
		this.setDescription(null);
	}
	public Book() {
		// TODO Auto-generated constructor stub
		this.setBookId(0);
		this.setTitle(null);
		this.setAuthor(null);
		this.setType(null);
		this.setCallCode(null);
		this.setPublisher(null);
		this.setPublishTime(null);
		this.setEnterTime(null);
		if(enterTime==null){
			Calendar ca= Calendar.getInstance();
			Date enterDate = ca.getTime();
			enterTime=sdf.format(enterDate);
			this.setEnterTime(enterTime);
		}
		this.setPrice(0);
		this.setDescription(null);
		this.setStatus(null);
	}
	/**
	 * @return the storePlace
	 */
	public String getStorePlace() {
		return storePlace;
	}
	/**
	 * @param storePlace the storePlace to set
	 */
	public void setStorePlace(String storePlace) {
		this.storePlace = storePlace;
	}
	/**
	 * @return the bookId
	 */
	public int getBookId() {
		return bookId;
	}
	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}
	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	/**
	 * @return the publishTime
	 */
	public String getPublishTime() {
		return publishTime;
	}
	/**
	 * @param publishTime the publishTime to set
	 */
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	/**
	 * @return the enterTime
	 */
	public String getEnterTime() {
		return enterTime;
	}
	/**
	 * @param enterTime the enterTime to set
	 */
	public void setEnterTime(String enterTime) {
		this.enterTime = enterTime;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param bookStatus the status to set
	 */
	public void setStatus(String bookStatus) {
		this.status = bookStatus;
	}
	/**
	 * @return the callCode
	 */
	public String getCallCode() {
		return callCode;
	}
	/**
	 * @param callCode the callCode to set
	 */
	public void setCallCode(String callCode) {
		this.callCode = callCode;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
}
