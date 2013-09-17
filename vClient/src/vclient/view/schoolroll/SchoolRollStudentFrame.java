package vclient.view.schoolroll;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.*;

import vclient.srv.ClientSrvHelper;

import common.vo.User;
import common.vo.schoolroll.MessageToStuQue;
import common.vo.schoolroll.StudentInfo;

//学生界面框架类
public class SchoolRollStudentFrame extends JFrame {
	// 学生登录控件
	private JButton jbtSInformation = new JButton("学生信息查询");
	private JButton jbtSScore = new JButton("成绩查询");
	private JButton jbtSExercises = new JButton("出操查询");

	private JPanel Up = new JPanel();
	private JPanel sPanel = new JPanel();
	private JScrollPane js = new JScrollPane();
	private JPanel jpStuQuery = new JPanel(new BorderLayout());
	private JPanel jpStudent;

	private User user;

	public SchoolRollStudentFrame() {
		this(null);
	}

	// 初始化界面
	public SchoolRollStudentFrame(User user) {
		this.user = user;
		sPanel.setLayout(new GridLayout(1, 4));
		sPanel.add(jbtSInformation);
		// 创建Panel装以上子界面
		Up.setBackground(new Color(233, 42, 31));
		Up.add(sPanel, BorderLayout.SOUTH);
		add(Up, BorderLayout.NORTH);

		// 创建一个监听对象b
		ButtonListener listener = new ButtonListener();
		jbtSInformation.addActionListener(listener);

		setTitle("学生登录界面");
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	// 内部监听类
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == jbtSInformation) {
				MessageToStuQue obj = new MessageToStuQue(user.getIdNum(), 1);
				StudentInfo acceptObj = ClientSrvHelper.queryStudentInfo(obj);
				doFinishJob(acceptObj);
			}
			repaint();
			revalidate();
		}
	}

	private void doFinishJob(StudentInfo acceptObj) {
		if (acceptObj == null)
			JOptionPane.showMessageDialog(null, "数据库没有符合要求的，请重新输入!");
		else {
			String[] columnNames = { "学号", "一卡通号", "院系", "专业", "入学日期", "姓名",
					"性别", "政治面貌", "联系方式", "身份证号码", "出生日期", "民族", "籍贯", "住址" };
			Object[][] data = { { acceptObj.getStuNum(), acceptObj.getStuId(),
					acceptObj.getCollege(), acceptObj.getMajor(),
					acceptObj.getEntryDate(), acceptObj.getStuName(),
					acceptObj.getStuSex(),
					acceptObj.getStuPoliticalLandscape(),
					acceptObj.getStuPhoneNum(), acceptObj.getStuID(),
					acceptObj.getStuBirthday(), acceptObj.getStuNation(),
					acceptObj.getStuHomeTown(), acceptObj.getStuAddress() }, };
			JTable jtCourseView = new JTable(data, columnNames);

			js.setViewportView(jtCourseView);
			this.add(js, BorderLayout.CENTER);
		}
	}
}
