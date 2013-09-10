package vclient.view.schoolroll;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import vclient.srv.ClientSrvHelper;
import common.vo.schoolroll.StudentInfo;

//学生信息添加
public class ManageStuInfoInc {
	public JPanel jpSInformation1 = new JPanel(new BorderLayout());
	private JButton jbtStuConfirm = new JButton("确定");
	JTextField jtfStuNum = new JTextField(10);
	JTextField jtfStuId = new JTextField(10);
	JTextField jtfCollege = new JTextField(10);
	JTextField jtfMajor = new JTextField(10);
	JTextField jtfEntryDate = new JTextField(10);
	JTextField jtfStuName = new JTextField(10);
	JTextField jtfStuPoliticalLandscape = new JTextField(10);
	JTextField jtfStuPhoneNum = new JTextField(10);
	String jrbstuSex;
	JRadioButton jrbWoman;
	JRadioButton jrbMan;
	JTextField jtfStuID = new JTextField(14);
	JTextField jtfStuBirthday = new JTextField(6);
	JTextField jtfStuNation = new JTextField(6);
	JTextField jtfStuHomeTown = new JTextField(15);
	JTextField jtfStuAddress = new JTextField(18);

	// 构造函数初始化界面
	public ManageStuInfoInc() {
		JPanel jpSex = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jpSex.setLayout(new GridLayout(1, 3));
		jpSex.add(new JLabel("性别:"));
		jrbMan = new JRadioButton("男");
		jpSex.add(jrbMan);
		jpSex.add(jrbWoman = new JRadioButton("女"));
		ButtonGroup btgSex = new ButtonGroup();
		btgSex.add(jrbMan);
		btgSex.add(jrbWoman);
		JRadioButtonListener listener = new JRadioButtonListener();
		jrbMan.addActionListener(listener);
		jrbWoman.addActionListener(listener);
		jbtStuConfirm.addActionListener(listener);

		JPanel jpSInformation = new JPanel();
		JPanel jpChoose = new JPanel(new FlowLayout());
		// sInformation.setLayout();

		jpSInformation.add(new JLabel("学号"));
		jpSInformation.add(jtfStuNum);
		jpSInformation.add(new JLabel("一卡通号"));
		jpSInformation.add(jtfStuId);
		jpSInformation.add(new JLabel("院系"));
		jpSInformation.add(jtfCollege);
		jpSInformation.add(new JLabel("专业"));
		jpSInformation.add(jtfMajor);
		jpSInformation.add(new JLabel("入学日期"));
		jpSInformation.add(jtfEntryDate);
		jpSInformation.add(new JLabel("姓名"));
		jpSInformation.add(jtfStuName);
		jpSInformation.add(jpSex);
		jpSInformation.add(new JLabel("政治面貌"));
		jpSInformation.add(jtfStuPoliticalLandscape);
		jpSInformation.add(new JLabel("联系方式"));
		jpSInformation.add(jtfStuPhoneNum);
		jpSInformation.add(new JLabel("身份证号码"));
		jpSInformation.add(jtfStuID);
		jpSInformation.add(new JLabel("出生日期"));
		jpSInformation.add(jtfStuBirthday);
		jpSInformation.add(new JLabel("民族"));
		jpSInformation.add(jtfStuNation);
		jpSInformation.add(new JLabel("籍贯"));
		jpSInformation.add(jtfStuHomeTown);
		jpSInformation.add(new JLabel("家庭住址"));
		jpSInformation.add(jtfStuAddress);

		jpChoose.add(jbtStuConfirm);

		jpSInformation1.add(new JLabel("-------------------------"
				+ "-----------------------" + "学生基本信息----------------------"
				+ "-------------------------------"), BorderLayout.NORTH);
		jpSInformation1.add(jpSInformation, BorderLayout.CENTER);
		jpSInformation1.add(jpChoose, BorderLayout.SOUTH);
	}

	// 按钮的消息响应
	class JRadioButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jrbMan) {
				jrbstuSex = "男";
			} else if (e.getSource() == jrbWoman) {
				jrbstuSex = "女";
			} else if (e.getSource() == jbtStuConfirm) { // 添加学生信息
				String stuNum = jtfStuNum.getText().trim();
				String stuId = jtfStuId.getText().trim();
				String college = jtfCollege.getText().trim();
				String major = jtfMajor.getText().trim();
				String entryDate = jtfEntryDate.getText().trim();
				String stuName = jtfStuName.getText().trim();
				String stuPoliticalLandscape = jtfStuPoliticalLandscape.getText()
						.trim();
				long stuPhoneNum = Long.parseLong(jtfStuPhoneNum.getText());
				String stuSex = jrbstuSex;
				String stuID = jtfStuID.getText().trim();
				String stuBirthday = jtfStuBirthday.getText().trim();
				String stuNation = jtfStuNation.getText().trim();
				String stuHomeTown = jtfStuHomeTown.getText().trim();
				String stuAddress = jtfStuAddress.getText().trim();
				StudentInfo stuInfor = new StudentInfo(stuNum, stuId, college,
						major, entryDate, stuName, stuPoliticalLandscape,
						stuPhoneNum, stuSex, stuID, stuBirthday, stuNation,
						stuHomeTown, stuAddress);
				int status = ClientSrvHelper.increatStuInfo(stuInfor);
				
				if (status == 1)
					JOptionPane.showMessageDialog(null, "添加成功!");
				else
					JOptionPane.showMessageDialog(null, "学号或身份证号已存在，请重新输入");
			}
		}
	}

}
