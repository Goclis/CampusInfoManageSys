package vclient.view;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.omg.CORBA.ShortSeqHelper;

import common.beans.User;

import vclient.srv.ClientSrvHelper;

public class RegisterFrame extends JFrame 
		implements ActionListener {
	// 信息Fields标题
	private JLabel jlbId;
	private JLabel jlbPwd;
	private JLabel jlbIdNum;
	private JLabel jlbName;
	private JLabel jlbSex;
	private JLabel jlbDepart;
	private JLabel jlbMajor;
	private JLabel jlbIdentity;
	
	// 院系信息
	private String[] departItems = {
			"01 建筑学院",
			"02 机械学院",
			"03 能源与环境学院" };
	
	// 专业信息
	private String[][] majorItems = {
			{"11", "12", "13"},
			{"21", "22", "23"},
			{"31", "32", "33"}
	};
	
	// 身份信息
	private String[] identityItems = {
			"学生", "老师", "管理员" };
	
	// 信息Fields
	private JTextField jtfId;
	private JPasswordField jpfPwd;
	private JTextField jtfIdNum;
	private JTextField jtfName;
	private JCheckBox jchkMale;
	private JCheckBox jchkFemale;
	private JComboBox<String> jcboDepart;
	private JComboBox<String> jcboMajor; // 需要根据选择的院系变化
	private JComboBox<String> jcboIdentity;

	private JButton jbtOk = new JButton("确定");
	private JButton jbtCancel = new JButton("取消");
	
	// 保存ClientSrvHelper进行服务
	ClientSrvHelper clientSrv;
	
	public RegisterFrame() {
		this(null);
	}

	public RegisterFrame(ClientSrvHelper clientSrv) {
		this.clientSrv = clientSrv;
		initComponents();
		setProperties();
		setComponentAction();
	}

	/**
	 * 根据选择的院系返回该院系的所有专业
	 * @return
	 */
	private String[] getMajors() {
		String[] majors = null;
		String depart = (String) jcboDepart.getSelectedItem();
		for (int i = 0; i < departItems.length; i++) {
			if (depart == departItems[i]) {
				majors = majorItems[i];
				break;
			}
		}
		return majors;
	}
	
	public static void main(String[] args) {
		new RegisterFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbtOk) { // 确认注册
			// TODO: 做客户端的验证
			// ...
			
			// 从表单中取得并封装要注册的用户数据
			User user = getUserToRegister();
			user = clientSrv.register(user);
			
			// 根据返回的结果给出反馈
			// TODO: 优化反馈的界面
			if (user == null) {
				JOptionPane.showMessageDialog(this, "注册失败, 用户已注册");
			} else {
				JOptionPane.showMessageDialog(this, "注册成功");
				this.dispose();
			}
		} else if (e.getSource() == jbtCancel) { // 取消注册
			this.dispose();
		} else if (e.getSource() == jcboDepart) { // 院系变化，变化可选专业
			jcboMajor.removeAllItems();
			String[] majors = getMajors();
			for (int i = 0; i < majors.length; i++) {
				jcboMajor.addItem(majors[i]);
			}
		}
	}
	
	/**
	 * 通过注册表单中的信息生成User并返回
	 * @return
	 */
	private User getUserToRegister() {
		User user = new User();
		user.setId(jtfId.getText());
		user.setPassword(jpfPwd.getText());
		user.setIdNum(jtfIdNum.getText());
		user.setName(jtfName.getText());
		user.setSex(jchkMale.isSelected() ? "男" : "女");
		user.setDepartment((String) jcboDepart.getSelectedItem());
		user.setMajor((String) jcboMajor.getSelectedItem());
		user.setIdentity((String) jcboIdentity.getSelectedItem());
		return user;
	}
	
	/**
	 * 初始化控件及布局
	 */
	private void initComponents() {
		this.setLayout(new GridLayout(9, 2));
		this.add(jlbId = new JLabel("ID"));
		this.add(jtfId = new JTextField());
		this.add(jlbPwd = new JLabel("密码"));
		this.add(jpfPwd = new JPasswordField());
		this.add(jlbIdNum = new JLabel("学号/工号"));
		this.add(jtfIdNum = new JTextField());
		this.add(jlbName = new JLabel("姓名"));
		this.add(jtfName = new JTextField());
		this.add(jlbSex = new JLabel("性别"));
		JPanel jpSexes = new JPanel();
		jpSexes.add(jchkMale = new JCheckBox("男"));
		jpSexes.add(jchkFemale = new JCheckBox("女")); 
		ButtonGroup bgSexes = new ButtonGroup();
		jchkMale.setSelected(true);
		bgSexes.add(jchkMale);
		bgSexes.add(jchkFemale);
		this.add(jpSexes);
		this.add(jlbDepart = new JLabel("院系"));
		this.add(jcboDepart = new JComboBox<String>(departItems));
		this.add(jlbMajor = new JLabel("专业"));
		this.add(jcboMajor = new JComboBox<String>(getMajors()));
		this.add(jlbIdentity = new JLabel("身份"));
		this.add(jcboIdentity = new JComboBox<String>(identityItems));
		this.add(jbtOk);
		this.add(jbtCancel);
	}

	/**
	 * 设置框架属性
	 */
	private void setProperties() {
		this.setTitle("注册");
		this.setVisible(true);
		this.pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * 设置控件响应
	 */
	private void setComponentAction() {
		jbtOk.addActionListener(this);
		jbtCancel.addActionListener(this);
		jcboDepart.addActionListener(this);
	}
}
