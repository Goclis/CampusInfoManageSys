package vclient.view.course;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import vclient.srv.ClientSrvHelper;

import common.vo.User;
import common.vo.course.Course;
import common.vo.course.CourseMark;

/**
 * 选课管理的界面
 * @author goclis
 *
 */
public class TeacherCourseFrame extends JFrame 
		implements ActionListener {
	JTextField jtfSearch = new JTextField(11);
	String[] columnNames = new String[] { "课程ID", "课程名", "上课时间"};
	DefaultTableModel dtmSearchResult = new DefaultTableModel(columnNames, 0);
	JTable jtSearchResult = new JTable(dtmSearchResult);
	JComboBox jcbSearch = new JComboBox();
	JTextField jtfSelectedCourse = new JTextField(10);
	JButton jbtSearchMine; // 查询自己已选课程
	JButton jbtAddCourse; // 添加课程
	JButton jbtQueryStudent; // 查询选择了某课程的学生
	
	User user;
	

	public TeacherCourseFrame(User user) {
		createSearchCourse();
		this.user = user;
	}

	// 根据用户的时间，做出相应的反映
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbtSearchMine) { // 显示自己所授的所有课
			int i = dtmSearchResult.getRowCount();
			while (i > 0) {
				dtmSearchResult.removeRow(0);
				i--;
			}
				
			ArrayList<Course> courses = ClientSrvHelper.queryUserCourse(user.getId());
			
			for (Course course : courses) {
				dtmSearchResult.addRow(new Object[] {course.getCourseId()
						, course.getCourseName(), course.getSchooltime(), course.getGrade()});
			}
		} else if (e.getSource() == jbtAddCourse) { // 添加课程
			JFrame frame = new AddNewCourseFrame(user);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		} else if (e.getSource() == jbtQueryStudent) { // 查询选择了某课程的学生
			if (jtSearchResult.getSelectedRow() >= 0) {
				Integer courseId = (Integer) jtSearchResult.getValueAt(jtSearchResult.getSelectedRow(), 0);
				String courseName = (String) jtSearchResult.getValueAt(jtSearchResult.getSelectedRow(), 1);
				ArrayList<CourseMark> courseMarks = ClientSrvHelper.queryStudentSelectTheCourse(courseId, user);
				new ShowCourseDetailFrame(courseMarks, courseId, courseName).setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		}
	}

	// 产生图形用户界面，以便用户操作
	public void createSearchCourse() {
		this.setLayout(new BorderLayout());
		JPanel jp1 = new JPanel();
		jp1.setLayout(new BorderLayout());
		JPanel jp2 = new JPanel();
		JPanel jp11 = new JPanel();
		JPanel jp12 = new JPanel(new FlowLayout());
		JLabel jlSearch = new JLabel(" 教 师 课 程 管 理 系 统 ");
		jp11.add(jlSearch);
		jbtSearchMine = new JButton("查询所授课程");
		jp12.add(jbtSearchMine);
		jp12.add(jbtAddCourse = new JButton("添加课程"));
		jp12.add(jbtQueryStudent = new JButton("查询该课程学生"));
		jp1.add(jp11, BorderLayout.NORTH);
		jp1.add(jp12);
		jp2.add(new JScrollPane(jtSearchResult));
		jtSearchResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.add(jp1, BorderLayout.NORTH);
		this.add(jp2);
		
		jbtAddCourse.addActionListener(this);
		jbtQueryStudent.addActionListener(this);
		jbtSearchMine.addActionListener(this);
		
		this.setVisible(true);
		this.pack();
	}

	public static void main(String[] args) {
		new TeacherCourseFrame(new User("213110000", null, null)).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
