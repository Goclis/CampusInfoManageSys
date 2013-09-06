/**
 * 
 */
package vserver.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import common.beans.Course;
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
	 * 用户添加课程
	 * @param courseId -- 课程
	 * @param user -- 用户
	 * @return 添加成功返回true, 否则false
	 */
	public boolean userAddCourse(Integer courseId, User user) {
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
			stat = conn.createStatement();
			// TODO：添加对是否有此门课的检查
			String sql = String.format("INSERT INTO ci_course_users (course_id, user_id) VALUES (%s, '%s')", 
					courseId, user.getId());
			if (stat.executeUpdate(sql) > 0) {
				return true;
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
		ArrayList<Course> list = dbOperator.queryUserCourse("211111");
		if (list != null) {
			for (Course course : list) {
				System.out.println(course.getCourseName() + " " + course.getCourseId() + " " + course.getSchooltime());
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
}
