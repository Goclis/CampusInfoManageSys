package vclient.view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import common.beans.Message;
import common.beans.User;

import vclient.srv.ClientSrvHelper;

/**
 * 启动程序的界面设计
 * @author goclis
 *
 */
public class StartupFrame extends JFrame 
		implements ActionListener {
	// 用户名与密码
	private JLabel jlbUsername = new JLabel("用户名");
	private JLabel jlbPassword = new JLabel("密码");
	private JTextField jtfId = new JTextField();
	private JPasswordField jpfPwd = new JPasswordField();
	
	// 登录的身份
	private JComboBox<String> jcboIdentity = new JComboBox<String>(
			new String[] {"学生", "老师", "管理员"});
	private JCheckBox jchkStudent = new JCheckBox("学生");
	private JCheckBox jchkTeacher = new JCheckBox("老师");
	private JCheckBox jchkManager = new JCheckBox("管理员");
	
	// 执行的操作
	private JButton jbtLogin = new JButton("登录");
	private JButton jbtRegister = new JButton("注册");
	
	// 保存ClientSrvHelper进行服务
	private ClientSrvHelper clientSrv;
	
	/**
	 * 初始化
	 */
	public StartupFrame() {		
		clientSrv = new ClientSrvHelper();
		initComponents();
		setProperties();
		setComponentAction();
	}

	/**
	 * 为控件添加响应
	 */
	private void setComponentAction() {
		jbtLogin.addActionListener(this);
		jbtRegister.addActionListener(this);
	}
	
	/**
	 * 设置框架属性
	 */
	private void setProperties() {
		this.setSize(350, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 居中显示
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		this.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.width) / 2);
		this.setVisible(true);
	}
	
	/**
	 * 初始化控件并布局
	 */
	private void initComponents() {
		// TODO: 调整美化布局
		JLabel jlbWelcome = new JLabel("Welcome");
		jlbWelcome.setFont(new Font(jlbWelcome.getFont().getFamily(), Font.BOLD, 55));
		jlbWelcome.setHorizontalAlignment(JLabel.CENTER);
		
		// JPanel to hold username
		JPanel jpUsername = new JPanel(null); 
		jpUsername.add(jlbUsername);
		jpUsername.add(jtfId);
		jlbUsername.setBounds(50, 0, 250, 15);
		jlbUsername.setFont(new Font(jlbUsername.getFont().getFamily(), Font.BOLD, 10));
		jtfId.setBounds(50, 15, 250, 30);
		
		// JPanel to hold password
		JPanel jpPassword = new JPanel(null);
		jpPassword.add(jlbPassword);
		jpPassword.add(jpfPwd);
		jlbPassword.setFont(jlbUsername.getFont());
		jlbPassword.setBounds(50, 0, 250, 15);
		jpfPwd.setBounds(50, 15, 250, 30);
		
		// JPanel to hold username and password
		JPanel jpMain = new JPanel(null);
		jpMain.add(jpUsername);
		jpMain.add(jpPassword);
		jpUsername.setBounds(0, 0, 350, 55);
		jpPassword.setBounds(0, 55, 350, 55);
		
		// JPanel to hold login and register button
		JPanel jpBtns = new JPanel(null);
		jpBtns.add(jbtLogin);
		jbtLogin.setBounds(50, 5, 100, 35);
		jpBtns.add(jbtRegister);
		jbtRegister.setBounds(199, 5, 100, 35);
		
		// JPanel to hold all components
		JPanel jpBody = new JPanel(null);
		jpBody.add(jcboIdentity);
		jpBody.add(jpMain);
		jpBody.add(jpBtns);
		jcboIdentity.setBounds(50, 0, 250, 40);
		jpMain.setBounds(0, 55, 350, 110);
		jpBtns.setBounds(0, 170, 350, 80);
		
		this.setLayout(null);
		this.add(jlbWelcome);
		jlbWelcome.setBounds(0, 50, 350, 50);
		this.add(jpBody);
		jpBody.setBounds(0, 150, 350, 350);
	}

	public static void main(String[] args) {
		new StartupFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbtLogin) { // 点击登录
			doLogin();
		} else if (e.getSource() == jbtRegister) { // 点击注册
			doRegister();
		} else {
			System.out.println(e.getSource());
		}
	}
	
	/**
	 * 处理注册
	 */
	private void doRegister() {
		JFrame frame = new RegisterFrame(clientSrv); // 传递ServiceHelper
		frame.setTitle("注册");
		frame.setVisible(true);
		
		// TODO: 调整美化布局
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * 处理登录
	 */
	private void doLogin() {
		// 从输入框获得登录的信息
		String userId = jtfId.getText().trim(); // 用户名
		String pwd = jpfPwd.getText().trim(); // 密码
		String identity = null; // 身份 
		if (jchkStudent.isSelected()) {
			identity = "学生";
		} else if (jchkTeacher.isSelected()) {
			identity = "老师";
		} else if (jchkManager.isSelected()) {
			identity = "管理员";
		}
		
		// TODO: 在客户端验证输入
		// ...
		
		// 创建User并发送登录请求
		User user = new User(userId, pwd, identity);
		user = clientSrv.login(user);
		
		// 处理登录结果
		// TODO: 优化反馈信息
		if (user == null) {
			JOptionPane.showMessageDialog(this, "已登录或用户名密码错误");
		} else {
			System.out.println("登录成功");
			// TODO: 启动主界面
		}
	}
}
