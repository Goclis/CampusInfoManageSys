package vclient.view.course;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import vclient.srv.ClientSrvHelper;

import common.vo.User;
import common.vo.course.Course;

/**
 * 选课管理的界面
 * @author goclis
 *
 */
public class StudentCourseFrame extends JFrame 
		implements ActionListener {
	JTextField jtfSearch = new JTextField(11);
	String[] columnNames = new String[] { "课程ID", "课程名", "上课时间", "分数"};
	DefaultTableModel dtmSearchResult = new DefaultTableModel(columnNames, 0);
	JTable jtSearchResult = new JTable(dtmSearchResult);
	JComboBox jcbSearch = new JComboBox();
	JTextField jtfSelectedCourse = new JTextField(10);
	JButton jbtSelectedCourse;
	JButton jbtSearchAll; // 查询所有课程
	JButton jbtSearchMine; // 查询自己已选课程
	
	User user;
	

	// 在构造方法中添加两个参数。以便在"提交"时，将学生的身份连同所选的课程，一同记录在学生选课表中。
	public StudentCourseFrame(User user) {
		createSearchCourse();
		this.user = user;
	}

	// 根据用户的时间，做出相应的反映
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbtSelectedCourse) { // 选择课程
			Integer courseId = Integer.valueOf(jtfSelectedCourse.getText().trim());
			Course course = ClientSrvHelper.userAddCourse(new Course(courseId), user);
			if (course == null) {
				JOptionPane.showMessageDialog(this, "添加失败，可能已添加此课程");
			} else {
				JOptionPane.showMessageDialog(this, "添加成功");
			}
		} else if (e.getSource() == jbtSearchAll) { // 查询所有课程
			// 清空表格
			int i = dtmSearchResult.getRowCount();
			while (i > 0) {
				dtmSearchResult.removeRow(0);
				i--;
			}
			
			ArrayList<Course> courses = ClientSrvHelper.queryAllCourse();
			for (Course course : courses) {
				dtmSearchResult.addRow(new Object[] {course.getCourseId()
						, course.getCourseName(), course.getSchooltime(), ""});
			}
		} else if (e.getSource() == jbtSearchMine) { // 查询已选课程
			// 清空表格
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
		}
	}

	// 产生图形用户界面，以便用户操作
	public void createSearchCourse() {
		this.setLayout(new BorderLayout());
		JPanel jp1 = new JPanel();
		jp1.setLayout(new GridLayout(3, 1, 5, 5));
		JPanel jp2 = new JPanel();
		JPanel jp3 = new JPanel();
		JPanel jp10 = new JPanel();
		JPanel jp11 = new JPanel();
		JPanel jp12 = new JPanel();
		JPanel jp13 = new JPanel();
		JLabel jlSearch = new JLabel(" 学 生 选 课 系 统 ");
		jp11.add(jlSearch);
		jbtSearchMine = new JButton("查询已选课程");
		jp12.add(jbtSearchMine);
		jbtSearchMine.addActionListener(this);
		jbtSearchAll = new JButton("查询所有课程");
		jbtSearchAll.addActionListener(this);
		jp13.add(jbtSearchAll);
		jp1.add(jp11);
		jp1.add(jp12);
		jp1.add(jp13);
		jp2.add(new JScrollPane(jtSearchResult));
		JLabel jlSelectedCourse = new JLabel("请输入课程ID：");
		jbtSelectedCourse = new JButton("提交");
		jbtSelectedCourse.addActionListener(this);
		jp3.add(jlSelectedCourse);
		jp3.add(jtfSelectedCourse);
		jp3.add(jbtSelectedCourse);
		this.add(jp1, BorderLayout.NORTH);
		this.add(jp2, BorderLayout.CENTER);
		this.add(jp3, BorderLayout.SOUTH);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}

	// 当某学生有效登录后，启动程序（将学生的登录信息也传过来，以便保存选课操作时使用）
	public static void main(String[] args) {
		new StudentCourseFrame(new User("213110000", null, null)).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
