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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;

/**
 * 启动程序的界面设计
 * @author goclis
 *
 */
public class StartupFrame extends JFrame implements ActionListener{
	// 用户名与密码
	private JLabel jlbUsername = new JLabel("用户名");
	private JLabel jlbPassword = new JLabel("密码");
	private JTextField jtfName = new JTextField(10);
	private JTextField jtfPwd = new JTextField(10);
	
	// 登录的身份
	private JCheckBox jchkStudent = new JCheckBox("学生");
	private JCheckBox jchkTeacher = new JCheckBox("老师");
	private JCheckBox jchkManager = new JCheckBox("管理员");
	
	// 执行的操作
	private JButton jbtLogin = new JButton("登录");
	private JButton jbtRegister = new JButton("注册");
	
	// 与服务器的连接
	private Socket socket;
	private String host = "localhost";
	private int port = 8000;
	
	/**
	 * 初始化界面
	 */
	public StartupFrame() {		
		this.setLayout(new FlowLayout());
		this.add(jlbUsername);
		this.add(jtfName);
		this.add(jlbPassword);
		this.add(jtfPwd);
		ButtonGroup group = new ButtonGroup();
		group.add(jchkStudent);
		group.add(jchkTeacher);
		group.add(jchkManager);
		this.add(jchkStudent);
		this.add(jchkTeacher);
		this.add(jchkManager);
		this.add(jbtLogin);
		this.add(jbtRegister);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		
		jbtLogin.addActionListener(this);
		jbtRegister.addActionListener(this);
		
		connectToServer();
	}
	
	/**
	 * 通过Socket连接服务器
	 */
	private void connectToServer() {
		try {
			socket = new Socket(host, port);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new StartupFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbtLogin) { // 点击登录
			System.out.println("Login");
		} else if (e.getSource() == jbtRegister) { // 点击注册
			JFrame frame = new RegisterFrame(socket); // 传递socket以便进行注册验证
			frame.setTitle("注册");
			frame.setVisible(true);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		} else {
			System.out.println(e.getSource());
		}
	}
}
