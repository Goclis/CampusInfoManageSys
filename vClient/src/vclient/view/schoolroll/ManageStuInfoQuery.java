package vclient.view.schoolroll;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import vclient.srv.ClientSrvHelper;

import common.vo.schoolroll.MessageToStuQue;
import common.vo.schoolroll.StudentInfo;

//学生信息查询类
public class ManageStuInfoQuery {

	JComboBoxListener listener = new JComboBoxListener();
	JTextField jtfQue = new JTextField(10);
	String stuQue;// 姓名或学号
	int queFlag;
	public JPanel jpBox = new JPanel();
	JPanel jpStuQuery = new JPanel(new BorderLayout());
	JScrollPane jscrollPane1 = new JScrollPane();
	private JButton jbtQueConfirm = new JButton("确定");
	String[] petStrings = { "学号", "姓名" };
	JComboBox box = new JComboBox(petStrings);

	JFrame jfStu;

	// 构造函数初始化界面，并传入JFrame对象用于面板的刷新
	public ManageStuInfoQuery(JFrame jf) {

		jbtQueConfirm.addActionListener(listener);
		box.addActionListener(listener);

		box.setSelectedIndex(0);
		jpBox.add(new JLabel("查询类别："));
		jpBox.add(box);
		jpBox.add(jtfQue);
		jpBox.add(jbtQueConfirm);
		jpStuQuery.add(jpBox, BorderLayout.NORTH);
		jfStu = jf;
	}

	// 消息的响应
	class JComboBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == box) {
				int index = box.getSelectedIndex();
				switch (index) {
				case 0:
					queFlag = 1;// 1：按学号查找
					break;
				case 1:
					queFlag = 2;// 按姓名查找
					break;
				}
			} else if (e.getSource() == jbtQueConfirm) {
				stuQue = jtfQue.getText().trim();// 获得界面输入的字符串
				MessageToStuQue messageToStuQue = new MessageToStuQue(stuQue,
						queFlag); // 封装消息
				StudentInfo acceptObj = ClientSrvHelper
						.queryStudentInfo(messageToStuQue);
				if (acceptObj == null)
					JOptionPane.showMessageDialog(null, "数据库没有符合要求的，请重新输入!");
				else {
					String[] columnNames = { "学号", "一卡通号", "院系", "专业", "入学日期",
							"姓名", "性别", "政治面貌", "联系方式", "身份证号码", "出生日期", "民族",
							"籍贯", "住址" };
					Object[][] data = { { acceptObj.getStuNum(),
							acceptObj.getStuId(), acceptObj.getCollege(),
							acceptObj.getMajor(), acceptObj.getEntryDate(),
							acceptObj.getStuName(), acceptObj.getStuSex(),
							acceptObj.getStuPoliticalLandscape(),
							acceptObj.getStuPhoneNum(), acceptObj.getStuID(),
							acceptObj.getStuBirthday(), acceptObj.getStuNation(),
							acceptObj.getStuHomeTown(), acceptObj.getStuAddress() }, };
					JTable jtCourseView = new JTable(data, columnNames);
					jscrollPane1.setViewportView(jtCourseView);
					jpStuQuery.add(jscrollPane1, BorderLayout.CENTER);
				}
				jfStu.setVisible(true);// 刷新框架

			}
		}
	}
}
