/**
 * 
 */
package common.vo.course;

import java.io.Serializable;

/**
 * 封装教师打分
 * @author goclis
 */
public class CourseMark implements Serializable {
	private Integer courseId; // 课程ID
	private String userId; // 学生ID
	private Integer mark; // 分数
	private String username; // 学生姓名
	
	public CourseMark() {
		// TODO Auto-generated constructor stub
	}

	public CourseMark(String userId, Integer courseId, Integer mark) {
		this.courseId = courseId;
		this.userId = userId;
		this.mark = mark;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public String getUserName() {
		return username;
	}
	
	public void setUserName(String name) {
		this.username = name;
	}
}
