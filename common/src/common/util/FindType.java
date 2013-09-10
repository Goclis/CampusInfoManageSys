/**
 * 定义查询类型的Integer变量
 */
package common.util;

/**
 * @author zhongfang
 *
 */
public class FindType {

	public static final Integer EMPTY=null;
	public static final Integer BY_ID=1;
	public static final Integer BY_NAME=2;
	public static final Integer BY_AUTHOR=3;
	public static final Integer BY_CALLCODE=4;
	public static final Integer BY_STOREPLACE=5;
	public static final Integer BY_PUBLISHER=6;
	public static final Integer BY_TYPE=7;
	
	public static final Integer BY_USER_ID=8;
	
	//查正在借阅的书籍和借阅过的书籍
	public static final Integer PAST=9;
	public static final Integer NOW=10;
	/**
	 * 
	 */
	private FindType() {
		// TODO Auto-generated constructor stub
	}

}
