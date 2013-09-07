/**
 * 
 */
package vserver.dao;

import goclis.util.ObjectTransformer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import common.beans.Course;
import common.beans.CourseMark;
import common.beans.User;

/**
 * @author goclis
 *
 */
public class CourseDbOperator {
	// 数据库参数
	private static String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static String CONN_URL = "jdbc:mysql://localhost/campusInfo";
	private static String USER_NAME = "ci_manager";
	private static String PASSWORD = "qqqqqq";
	
	private Connection conn;
	private Statement stat;
	private ResultSet rs;
	
	/**
	 * 学生添加课程
	 * @param course.getCourseId -- 课程
	 * @param user -- 用户
	 * @return 添加成功返回true, 否则false
	 */
	public boolean studentAddCourse(Course course, User user) {
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
			stat = conn.createStatement();
			// TODO：添加对是否有此门课的检查
			if (user.getIdentity().equals("学生")) { // 学生添加课程
				String sql = String.format("INSERT INTO ci_course_users (course_id, user_id) VALUES (%s, '%s')", 
						course.getCourseId(), user.getId());
				if (stat.executeUpdate(sql) > 0) {
					return true;
				} 
			} else { // 老师新建课程
				String sql = "SELECT id FROM ci_course ORDER BY id"; // 设置新的课程编号
				rs = stat.executeQuery(sql);
				if (rs.last()) {
					int newId = Integer.valueOf(rs.getString(1)) + 1;
					String sqlNewCourse = String.format("INSERT INTO ci_course (id, name, schooltime) VALUES (%s, '%s', '%s')",
							newId, course.getCourseName(), course.getSchooltime());
					Statement stat2 = conn.createStatement();
					if (stat2.executeUpdate(sqlNewCourse) > 0) { // 插入成功
						String sqlConnCourse = String.format("INSERT INTO ci_course_users (course_id, user_id) " +
								"VALUES (%s, '%s')", newId, user.getId()); // 在ci_course_users表中建立关联
						Statement stat3 = conn.createStatement();
						if (stat3.executeUpdate(sqlConnCourse) > 0) {
							return true;
						}
					}
				}
				return false;
			}
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("Error when user add course");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error when user add course");
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 查询用户的所有课程
	 * @param userId -- 用户
	 * @return 成功返回课程列表，否则返回null
	 */
	public ArrayList<Course> queryUserCourse(String userId) {
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
			stat = conn.createStatement();
			String sql = "SELECT course_id FROM ci_course_users WHERE user_id = '" + userId + "'";
			rs = stat.executeQuery(sql);
			ArrayList<Course> courses = new ArrayList<Course>(); 
			while (rs.next()) {
				String sqlCourse = "SELECT id, name, schooltime FROM ci_course WHERE id = " + rs.getString(1);
				Statement stat2 = conn.createStatement();
				ResultSet rsCourse = stat2.executeQuery(sqlCourse);
				if (rsCourse.first()) {
					Course course = new Course(rsCourse.getString(1), 
							rsCourse.getString(2), rsCourse.getString(3));
					String sqlGrade = "SELECT grade FROM ci_grade WHERE student_id = '" + userId + "' AND course_id =" + course.getCourseId();
					Statement stat3 = conn.createStatement();
					ResultSet rsGrade = stat3.executeQuery(sqlGrade);
					if (rsGrade.first()) {
						course.setGrade(Integer.valueOf(rsGrade.getString(1))); 
					}
					courses.add(course);
				}
			}
			
			return courses;
		} catch (ClassNotFoundException e) {
			System.out.println("Error when query user course");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error when query user course");
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		CourseDbOperator dbOperator = new CourseDbOperator();
		//Integer courseId = 2;
		User user = new User("2121111", "1111", "test");
		//System.out.println(dbOperator.userAddCourse(courseId, user));
		ArrayList<Course> list = dbOperator.queryAllCourse();
		if (list != null) {
			for (Course course : list) {
				System.out.println(course.getCourseName() + " " + course.getCourseId() + " " + course.getSchooltime() + " " + course.getGrade());
			}
		}
	}
	
	/**
	 * 查询所有的课程
	 * @return 成功返回课程列表，否则返回null
	 */
	public ArrayList<Course> queryAllCourse() {
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
			stat = conn.createStatement();
			String sql = "SELECT id, name, schooltime FROM ci_course";
			rs = stat.executeQuery(sql);
			ArrayList<Course> courses = new ArrayList<Course>();
			while (rs.next()) {
				Course course = new Course(rs.getString(1), rs.getString(2), rs.getString(3));
				courses.add(course);
			}

			return courses;
		} catch (ClassNotFoundException e) {
			System.out.println("Error when query all course");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error when query all course");
			e.printStackTrace();
		}
		
		return null;
	}

	public ArrayList<CourseMark> queryStudentSelectTheCourse(Integer courseId, User user) {
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
			stat = conn.createStatement();
			String sql = "SELECT user_id, mark FROM ci_course_users WHERE course_id = " + courseId;
			rs = stat.executeQuery(sql);
			ArrayList<CourseMark> marks = new ArrayList<CourseMark>();
			while (rs.next()) {
				String id = rs.getString(1);
				if (!id.equals(user.getId())) {
					String sqlUser = "SELECT id, name, id_num FROM ci_user WHERE id = '" + id + "'";
					Statement stat2 = conn.createStatement();
					ResultSet rs2 = stat2.executeQuery(sqlUser);
					if (rs2.first()) {
						CourseMark mark = new CourseMark();
						mark.setUserId(id);
						mark.setUserName(rs2.getString(2));
						mark.setMark(Integer.valueOf(rs.getString(2)));
						marks.add(mark);
					}
				}
			}
			
			return marks;
		} catch (ClassNotFoundException e) {
			System.out.println("Error when query student ");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error when query student");
			e.printStackTrace();
		}
		
		return null;
	}

	public boolean updateStudentMark(ArrayList<CourseMark> marks) {
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
			stat = conn.createStatement();
			for (CourseMark mark : marks) {
				String sql = "UPDATE ci_course_users SET mark = " + mark.getMark() + " WHERE course_id = " + mark.getCourseId() 
						+ " AND user_id = '" + mark.getUserId() + "'";
				if (stat.executeUpdate(sql) <= 0) {
					return false;
				}
			}
			return true;
		} catch (ClassNotFoundException e) {
			System.out.println("Error when update student mark");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error when update student mark");
			e.printStackTrace();
		}
		
		return false;
	}
}
