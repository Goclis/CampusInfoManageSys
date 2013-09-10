package vclient.view.course;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import vclient.srv.ClientSrvHelper;

import common.vo.User;
import common.vo.course.Course;

public class AddNewCourseFrame extends JFrame implements ActionListener {
	JLabel jlbCourseName = new JLabel("课程名称");
	JLabel jlbSchooltime = new JLabel("上课时间");
	JTextField jtfCourseName = new JTextField(20);
	JTextField jtfSchooltime = new JTextField(20);
	
	JButton jbtOk = new JButton("确定");
	private User user;
	
	public AddNewCourseFrame() {
		this(null);
	}
	
	public AddNewCourseFrame(User user) {
		this.user = user;
		
		JPanel jp1 = new JPanel();
		jp1.setLayout(new GridLayout(2, 2, 5, 5));
		jp1.add(jlbCourseName);
		jp1.add(jtfCourseName);
		jp1.add(jlbSchooltime);
		jp1.add(jtfSchooltime);
		this.setLayout(new BorderLayout(5, 5));
		this.add(jp1);
		this.add(jbtOk, BorderLayout.SOUTH);
		
		jbtOk.addActionListener(this);
		
		this.setTitle("添加课程");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbtOk) {
			String courseName = jtfCourseName.getText().trim();
			String schooltime = jtfSchooltime.getText().trim();
			Course course = new Course("1", courseName, schooltime);
			if (ClientSrvHelper.userAddCourse(course, user) != null) {
				JOptionPane.showMessageDialog(this, "添加成功");
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "添加失败");
			}
		}
	}
	
	public static void main(String[] args) {
		new AddNewCourseFrame();
	}
}
