/**
 * 
 */
package vserver.srv;

import java.util.ArrayList;

import vserver.dao.CourseDbOperator;
import common.util.MessageStatusCode;
import common.util.MessageType;
import common.vo.Message;
import common.vo.User;
import common.vo.course.Course;
import common.vo.course.CourseMark;

/**
 * 提供选课系统服务的服务器端服务类
 * @author goclis
 */
public class CourseServerSrv {
	/**
	 * 处理用户添加课程并给予反馈信息
	 * @param course -- 课程
	 * @param user -- 用户
	 * @return 反馈信息，如果添加成功则data域不为null，否则...
	 */
	public Message userAddCourse(Course course, User user) {
		if (course == null || user == null) {
			return Message.createFailureMessage();
		}
		
		CourseDbOperator dbOperator = new CourseDbOperator();
		boolean bAddCourse = dbOperator.userAddCourse(course, user);
		
		Message msgRt = new Message(MessageType.COURSE_USER_ADD);
		if (bAddCourse) {
			msgRt.setData(course);
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
			return new Message(MessageType.COURSE_QUERY_USER_ALL, new ArrayList<Course>());
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
	
	/**
	 * 查询某课程的所有学生
	 * @param courseId -- 课程ID
	 * @param user -- 授课老师
	 * @return Message中data域带用户列表，失败则列表为空
	 */
	public Message queryStudentSelectTheCourse(Integer courseId, User user) {
		if (courseId == null || user == null) {
			return new Message(MessageType.COURSE_QUERY_STUDENT, new ArrayList<CourseMark>());
		} 
		
		CourseDbOperator dbOperator = new CourseDbOperator();
		ArrayList<CourseMark> marks = dbOperator.queryStudentSelectTheCourse(courseId, user);
		
		Message msgRt = new Message(MessageType.COURSE_QUERY_STUDENT);
		if (marks != null) {
			msgRt.setData(marks);
			msgRt.setStatusCode(MessageStatusCode.SUCCESS);
		} else {
			msgRt.setData(new ArrayList<CourseMark>()); // 返回空列表
			msgRt.setStatusCode(MessageStatusCode.FAILED);
		}
		return msgRt;
	}
	
	/**
	 * 更新学生成绩
	 * @param marks -- 成绩列表
	 * @return statusCode域中包含成功或失败信息
	 */
	public Message updateStudentMark(ArrayList<CourseMark> marks) {
		if (marks == null) {
			return Message.createFailureMessage();
		}
		
		CourseDbOperator dbOperator = new CourseDbOperator();
		boolean bUpdate = dbOperator.updateStudentMark(marks);
		
		Message msgRt = new Message(MessageType.COURSE_UPDATE_MARK);
		if (bUpdate) {
			msgRt.setStatusCode(MessageStatusCode.SUCCESS);
		} else {
			msgRt.setStatusCode(MessageStatusCode.FAILED);
		}
		return msgRt;
	}

}
