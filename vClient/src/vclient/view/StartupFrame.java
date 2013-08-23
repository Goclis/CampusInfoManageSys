package vclient.view;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;

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
	private JTextField jtfId = new JTextField(10);
	private JPasswordField jpfPwd = new JPasswordField(10);
	
	// 登录的身份
	private JCheckBox jchkStudent = new JCheckBox("学生");
	private JCheckBox jchkTeacher = new JCheckBox("老师");
	private JCheckBox jchkManager = new JCheckBox("管理员");
	
	// TODO: 缺少选择身份登录
	// ...
	
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
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
	}
	
	/**
	 * 初始化控件并布局
	 */
	private void initComponents() {
		// TODO: 调整美化布局
		
		this.setLayout(new FlowLayout());
		this.add(jlbUsername);
		this.add(jtfId);
		this.add(jlbPassword);
		this.add(jpfPwd);
		ButtonGroup group = new ButtonGroup();
		group.add(jchkStudent);
		group.add(jchkTeacher);
		group.add(jchkManager);
		this.add(jchkStudent);
		this.add(jchkTeacher);
		this.add(jchkManager);
		this.add(jbtLogin);
		this.add(jbtRegister);
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
		String identity = "学生"; // 身份 TODO: 从表单中得到
		
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
