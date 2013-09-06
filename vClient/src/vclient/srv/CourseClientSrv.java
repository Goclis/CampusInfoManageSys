/**
 * 
 */
package vclient.srv;

import java.util.ArrayList;

import goclis.util.MessageType;
import goclis.util.ObjectTransformer;
import common.beans.Course;
import common.beans.Message;
import common.beans.User;

/**
 * 提供选课系统服务的客户端服务类
 * @author goclis
 */
public class CourseClientSrv extends ClientService {
	public CourseClientSrv() {
		super();
	}
	
	/**
	 * 用户添加课程（学生选课，老师添加课）
	 * @param courseId -- 要添加的课程
	 * @param user -- 添加课程的用户
	 * @return 添加成功返回courseID，否则返回null
	 */
	public Integer userAddCourse(Integer courseId, User user) {
		if (socket != null) {
			Message msg = new Message(MessageType.COURSE_USER_ADD, courseId, user);
			Message msgBack = sendMessage(msg, "用户添加课程");
			return ObjectTransformer.getInteger(msgBack.getData());
		}
		
		return null;
	}
	
	/**
	 * 查询用户课程
	 * @param userId -- 用户
	 * @return 返回课程的列表（失败返回空列表）
	 */
	public ArrayList<Course> queryUserCourse(String userId) {
		if (socket != null) {
			Message msg = new Message(MessageType.COURSE_QUERY_USER_ALL, userId);
			Message msgBack = sendMessage(msg, "用户查询课程");
			return ObjectTransformer.getCourseList(msgBack.getData());
		}
		
		return new ArrayList<Course>();
	}
	
	/**
	 * 查询所有课程
	 * @return 返回课程的列表（失败返回空列表）
	 */
	public ArrayList<Course> queryAllCourse() {
		if (socket != null) {
			Message msg = new Message(MessageType.COURSE_QUERY_ALL);
			Message msgBack = sendMessage(msg, "查询所有课程");
			return ObjectTransformer.getCourseList(msgBack.getData());
		}
		
		return new ArrayList<Course>();
	}
}
