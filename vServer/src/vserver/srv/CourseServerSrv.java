/**
 * 
 */
package vserver.srv;

import java.util.ArrayList;

import goclis.util.MessageStatusCode;
import goclis.util.MessageType;
import vserver.dao.CourseDbOperator;
import common.beans.Course;
import common.beans.Message;
import common.beans.User;

/**
 * 提供选课系统服务的服务器端服务类
 * @author goclis
 */
public class CourseServerSrv {
	/**
	 * 处理用户添加课程并给予反馈信息
	 * @param courseId -- 课程
	 * @param user -- 用户
	 * @return 反馈信息，如果添加成功则data域不为null，否则...
	 */
	public Message userAddCourse(Integer courseId, User user) {
		if (courseId == null || user == null) {
			return Message.createFailureMessage();
		}
		
		CourseDbOperator dbOperator = new CourseDbOperator();
		boolean bAddCourse = dbOperator.userAddCourse(courseId, user);
		
		Message msgRt = new Message(MessageType.COURSE_USER_ADD);
		if (bAddCourse) {
			msgRt.setData(courseId);
			msgRt.setStatusCode(MessageStatusCode.SUCCESS);
		} else {
			msgRt.setData(null);
			msgRt.setStatusCode(MessageStatusCode.FAILED);
		}
		return msgRt;
	}
	
	/**
	 * 查询用户的已选课程
	 * @param userId -- 用户ID
	 * @return Message中data域带课程列表，失败则列表为空
	 */
	public Message queryUserCourse(String userId) {
		if (userId == null) {
			return Message.createFailureMessage();
		}
		
		CourseDbOperator dbOperator = new CourseDbOperator();
		ArrayList<Course> courses = dbOperator.queryUserCourse(userId);
		
		Message msgRt = new Message(MessageType.COURSE_QUERY_USER_ALL);
		if (courses != null) {
			msgRt.setData(courses);
			msgRt.setStatusCode(MessageStatusCode.SUCCESS);
		} else {
			msgRt.setData(new ArrayList<Course>()); // 返回空列表
			msgRt.setStatusCode(MessageStatusCode.FAILED);
		}
		return msgRt;
	}
	
	/**
	 * 查询所有课程
	 * @return Message中data域带课程列表，失败则列表为空
	 */
	public Message queryAllCourse() {
		CourseDbOperator dbOperator = new CourseDbOperator();
		ArrayList<Course> courses = dbOperator.queryAllCourse();
		
		Message msgRt = new Message(MessageType.COURSE_QUERY_ALL);
		if (courses != null) {
			msgRt.setData(courses);
			msgRt.setStatusCode(MessageStatusCode.SUCCESS);
		} else {
			msgRt.setData(new ArrayList<Course>()); // 返回空列表
			msgRt.setStatusCode(MessageStatusCode.FAILED);
		}
		return msgRt;
	}

}
