package common.beans;

import java.io.Serializable;

public class Course implements Serializable {
	private Integer courseId; // 课程ID
	private String courseName; // 课程名
	private String teacherName; // 老师名
	private String schooltime; // 上课时间
	
	public Course() {
		
	}
	
	public Course(String courseId, String courseName, String schooltime) {
		this.courseId = Integer.valueOf(courseId);
		this.courseName = courseName;
		this.schooltime = schooltime;
	}

	public Course(Integer courseId) {
		this.courseId = courseId;
	}

	// Setters and Getters
	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getSchooltime() {
		return schooltime;
	}

	public void setSchooltime(String schooltime) {
		this.schooltime = schooltime;
	}
	
	
}
