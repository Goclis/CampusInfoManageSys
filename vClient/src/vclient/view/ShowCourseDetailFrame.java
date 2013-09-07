package vclient.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import vclient.srv.ClientSrvHelper;

import common.beans.CourseMark;
import common.beans.User;

/**
 * 显示选了某门课程的学生信息
 * @author goclis
 */
public class ShowCourseDetailFrame extends JFrame implements ActionListener {
	String[] columnNames = new String[] {"学生ID", "学生姓名", "课程分数"};
	private DefaultTableModel dtmStudents = new DefaultTableModel(columnNames, 0);
	private JTable jtStudents = new JTable(dtmStudents);
	private JButton jbtSave = new JButton("上传成绩");
	private ArrayList<CourseMark> marks;
	private Integer courseId;
	private String courseName;
	
	
	
	public ShowCourseDetailFrame() {
		this(null, null, "Test");
	}
	
	public ShowCourseDetailFrame(ArrayList<CourseMark> courseMarks, Integer courseId, String courseName) {
		this.marks = courseMarks;
		this.courseId = courseId;
		this.courseName = courseName;
		
		initTable();
		this.setTitle(courseName);
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(jtStudents));
		this.add(jbtSave, BorderLayout.SOUTH);
		jbtSave.addActionListener(this);
		this.pack();
		this.setVisible(true);
	}
	
	private void initTable() {
		for (CourseMark mark : marks) {
			dtmStudents.addRow(new Object[] {mark.getUserId(), mark.getUserName(), mark.getMark().toString()});
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbtSave) { // 保存老师打的分
			ArrayList<CourseMark> marks = new ArrayList<CourseMark>();
			int rows = jtStudents.getRowCount();
			jbtSave.setFocusable(true);
			for (int i = 0; i < rows; i++) {
				String userId = (String) dtmStudents.getValueAt(i, 0);
				Integer mark = Integer.valueOf((String) dtmStudents.getValueAt(i, 2));
				CourseMark courseMark = new CourseMark(userId, courseId, mark);
				marks.add(courseMark);
			}
			
			if (ClientSrvHelper.updateStudentMark(marks)) {
				JOptionPane.showMessageDialog(this, "成绩上传成功");
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "成绩上传失败");
			}
		}
	}
	
	public static void main(String[] args) {
		new ShowCourseDetailFrame();
	}

}
