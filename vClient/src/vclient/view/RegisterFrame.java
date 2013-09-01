package vclient.view;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
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
			"建筑学院",
			"机械工程学院",
			"能源与环境学院",
			"信息科学与工程学院",
			"土木工程学院",
			"电子科学与工程学院",
			"数学系",
			"自动化学院",
			"计算机科学与工程学院" };
	
	// 专业信息
	private String[][] majorItems = {
			{"建筑学", "城市规划", "景观学"},
			{"机械工程及自动化", "工业工程", "机械类"},
			{"热能与动力工程", "建筑环境与设备工程", "环境工程"},
			{"信息工程"},
			{"土木工程", "给水排水工程"},
			{"电子科学与技术"},
			{"数学类", "统计学", "数学与应用数学", "信息与计算数学"},
			{"自动化"},
			{"计算机科学与技术"}
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
	
	public static void main(String[] args) {
		new RegisterFrame();
	}
	
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
	 * 初始化控件及布局
	 */
	private void initComponents() {
		// TODO: 美化布局
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
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, 100);
		
		this.setVisible(true);
	}

	/**
	 * 设置控件响应
	 */
	private void setComponentAction() {
		jbtOk.addActionListener(this);
		jbtCancel.addActionListener(this);
		jcboDepart.addActionListener(this);
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
	
	/**
	 * 通过注册表单中的信息生成User并返回
	 * @return
	 */
	private User getUserToRegister() {
		User user = new User();
		user.setId(jtfId.getText().trim());
		user.setPassword(String.valueOf(jpfPwd.getPassword()).trim());
		user.setIdNum(jtfIdNum.getText());
		user.setName(jtfName.getText());
		user.setSex(jchkMale.isSelected() ? "男" : "女");
		user.setDepartment((String) jcboDepart.getSelectedItem());
		user.setMajor((String) jcboMajor.getSelectedItem());
		user.setIdentity((String) jcboIdentity.getSelectedItem());
		return user;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbtOk) { // 确认注册
			// 从表单中取得并封装要注册的用户数据
			User user = getUserToRegister();
			
			// 做客户端的验证，只验证了用户名和密码
			if (user.getId().isEmpty() 
					&& user.getPassword().isEmpty()) {
				JOptionPane.showMessageDialog(this, "用户名和密码不能为空");
			} else {
				user = clientSrv.register(user);
				
				// 根据返回的结果给出反馈
				// TODO: 优化反馈的界面
				if (user == null) {
					JOptionPane.showMessageDialog(this, "注册失败, 用户已注册");
				} else {
					JOptionPane.showMessageDialog(this, "注册成功");
					this.dispose();
				}
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
}
