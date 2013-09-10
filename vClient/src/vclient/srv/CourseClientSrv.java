/**
 * 
 */
package vclient.srv;

import java.util.ArrayList;

import common.util.MessageStatusCode;
import common.util.MessageType;
import common.util.ObjectTransformer;
import common.vo.Message;
import common.vo.User;
import common.vo.course.Course;
import common.vo.course.CourseMark;

/**
 * 提供选课系统服务的客户端服务类
 * @author goclis
 */
public class CourseClientSrv extends ClientService {
	public CourseClientSrv() {
		super();
	}
	
	/**
	 * 用户添加课程（学生选课）
	 * @param course -- 要添加的课程
	 * @param user -- 添加课程的用户
	 * @return 添加成功返回courseID，否则返回null
	 */
	public Course userAddCourse(Course course, User user) {
		if (socket != null) {
			Message msg = new Message(MessageType.COURSE_USER_ADD, course, user);
			Message msgBack = sendMessage(msg, "用户添加课程");
			return ObjectTransformer.getCourse(msgBack.getData());
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
	
	/**
	 * 查询某课程的所有学生
	 * @param courseId -- 课程ID
	 * @param user -- 授课教师
	 * @return 学生列表（失败为空列表）
	 */
	public ArrayList<CourseMark> queryStudentSelectTheCourse(Integer courseId,
			User user) {
		if (socket != null) {
			Message msg = new Message(MessageType.COURSE_QUERY_STUDENT, courseId, user);
			Message msgBack = sendMessage(msg, "查询选了某课的学生");
			return ObjectTransformer.getMarkList(msgBack.getData());
		}
		return new ArrayList<CourseMark>();
	}
	
	/**
	 * 更新学生成绩
	 * @param marks -- 成绩列表
	 * @return 成功true否则false
	 */
	public boolean updateStudentMark(ArrayList<CourseMark> marks) {
		if (socket != null) {
			Message msg = new Message(MessageType.COURSE_UPDATE_MARK, marks);
			Message msgBack = sendMessage(msg, "更新成绩");
			if (msgBack.getStatusCode().equals(MessageStatusCode.SUCCESS)) {
				return true;
			} else {
				return false;
			}
		}
		
		return false;
	}
}
