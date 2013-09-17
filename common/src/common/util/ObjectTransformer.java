package common.util;

import java.util.ArrayList;
import java.util.Vector;

import common.vo.Message;
import common.vo.User;
import common.vo.UserAccount;
import common.vo.course.Course;
import common.vo.course.CourseMark;
import common.vo.library.Book;
import common.vo.library.Borrow;
import common.vo.library.Order;
import common.vo.library.Reader;
import common.vo.library.Rule;
import common.vo.schoolroll.MessageToStuDel;
import common.vo.schoolroll.MessageToStuQue;
import common.vo.schoolroll.StudentInfo;
import common.vo.store.Good;
import common.vo.store.ShoppingItem;

/**
 * 用来将Object转化为适当的类型
 * 
 * @author goclis
 * 
 */
public final class ObjectTransformer {
	/**
	 * 私有化构造函数，防止实例化行为
	 */
	private ObjectTransformer() {
	}

	/**
	 * 将Object转化为User 成功则返回User，失败返回null
	 * 
	 * @param Object
	 * @return User
	 */
	public static User getUser(Object obj) {
		if (obj != null) {
			return (User) obj;
		} else {
			return null;
		}
	}

	/**
	 * 将Object转化为Message 成功则返回Message，失败返回null
	 * 
	 * @param Object
	 * @param Message
	 */
	public static Message getMessage(Object obj) {
		if (obj != null) {
			return (Message) obj;
		} else {
			return null;
		}
	}

	public static Good getGood(Object data) {
		if (data != null) {
			return (Good) data;
		} else {
			return null;
		}
	}

	public static String getString(Object data) {
		if (data != null) {
			return (String) data;
		} else {
			return null;
		}
	}

	public static ArrayList<ShoppingItem> getShoppingList(Object data) {
		if (data != null) {
			return (ArrayList<ShoppingItem>) data;
		} else {
			return null;
		}
	}

	public static ArrayList<Good> getGoodList(Object data) {
		if (data != null) {
			return (ArrayList<Good>) data;
		} else {
			return null;
		}
	}

	public static ArrayList<Integer> getGoodIds(Object data) {
		if (data != null) {
			return (ArrayList<Integer>) data;
		} else {
			return null;
		}
	}

	// TODO: 考虑删除
	public static Course getCourse(Object data) {
		if (data != null) {
			return (Course) data;
		} else {
			return null;
		}
	}

	public static ArrayList<Course> getCourseList(Object data) {
		if (data != null) {
			return (ArrayList<Course>) data;
		} else {
			return null;
		}
	}

	public static Integer getInteger(Object data) {
		if (data != null) {
			return (Integer) data;
		} else {
			return null;
		}
	}

	public static ArrayList<User> getUserList(Object data) {
		if (data != null) {
			return (ArrayList<User>) data;
		} else {
			return null;
		}
	}

	public static ArrayList<CourseMark> getMarkList(Object data) {
		if (data != null) {
			return (ArrayList<CourseMark>) data;
		} else {
			return null;
		}
	}

	public static Double getDouble(Object data) {
		return (data == null) ? null : (Double) data;
	}

	public static ArrayList<UserAccount> getAccountList(Object data) {
		return (data == null) ? null : (ArrayList<UserAccount>) data;
	}

	public static StudentInfo getObjStu(Object data) {
		return (data == null) ? null : (StudentInfo) data;
	}

	public static MessageToStuQue getMessageToStuQue(Object data) {
		return (data == null) ? null : (MessageToStuQue) data;
	}

	public static MessageToStuDel getMessageToStuDel(Object data) {
		return (data == null) ? null : (MessageToStuDel) data;
	}

	public static Book getBook(Object obj) {
		if (obj != null) {
			return (Book) obj;
		} else {
			return null;
		}
	}

	public static Reader getReader(Object obj) {
		if (obj != null) {
			return (Reader) obj;
		} else {
			return null;
		}
	}

	public static Rule getRule(Object obj) {
		if (obj != null) {
			return (Rule) obj;
		} else {
			return null;
		}
	}

	public static Order getOrder(Object obj) {
		if (obj != null) {
			return (Order) obj;
		} else
			return null;
	}

	public static Vector getVector(Object obj) {
		if (obj != null) {
			return (Vector) obj;
		} else {
			return null;
		}
	}

	public static Borrow getBorrow(Object obj) {
		// TODO Auto-generated method stub
		if (obj != null) {
			return (Borrow) obj;
		}
		return null;
	}
}
