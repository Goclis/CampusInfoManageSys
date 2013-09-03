/**
 * 
 */
package vclient.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import common.beans.User;

import vclient.srv.ClientSrvHelper;

/**
 * 主界面
 * @author goclis
 */
public class MainFrame extends JFrame implements ActionListener {
	private ClientSrvHelper clientSrv; // 保存ClientSrvHelper
	
	private JButton jbtSchollRoll = new JButton("学籍管理");
	private JButton jbtCourses = new JButton("选课系统");
	private JButton jbtLibrary = new JButton("图书馆");
	private JButton jbtStore = new JButton("商店");
	private JLabel jlbWelcome;
	private JButton jbtLogout = new JButton("登出");

	private User user; // 用户
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MainFrame();
	}
	
	public MainFrame(ClientSrvHelper clientSrv) {
		this.clientSrv = clientSrv;
		initComponents();
		setProperties();
		setComponentAction();
	}

	public MainFrame(ClientSrvHelper clientSrv, User user) {
		this.clientSrv = clientSrv;
		this.user = user;
		initComponents();
		setProperties();
		setComponentAction();
	}
	
	/**
	 * for test
	 */
	public MainFrame() {
		User user = new User();
		user.setName("test");
		this.user = user;
		initComponents();
		setProperties();
		setComponentAction();
	}

	private void setComponentAction() {
		jbtSchollRoll.addActionListener(this);
		jbtCourses.addActionListener(this);
		jbtLibrary.addActionListener(this);
		jbtStore.addActionListener(this);
		jbtLogout.addActionListener(this);
	}

	private void setProperties() {
		this.setSize(200, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, 100);
		
		this.setVisible(true);
	}

	private void initComponents() {
		Font ft = new Font(jbtSchollRoll.getFont().getFamily(), Font.BOLD, 20);
		jlbWelcome = new JLabel("欢迎登录，" + user.getName() + "   ");
		jlbWelcome.setHorizontalAlignment(JLabel.CENTER);
		jbtSchollRoll.setFont(ft);
		jbtCourses.setFont(ft);
		jbtLibrary.setFont(ft);
		jbtStore.setFont(ft);
		jbtLogout.setFont(ft);
		
		this.setLayout(new GridLayout(7, 1, 5, 5));
		this.add(jlbWelcome);
		this.add(jbtSchollRoll);
		this.add(jbtCourses);
		this.add(jbtStore);
		this.add(jbtLibrary);
		this.add(jbtLogout);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO: 调用相应模块
		if (e.getSource() == jbtSchollRoll) {
			System.out.println("学籍管理");
		} else if (e.getSource() == jbtCourses) {
			System.out.println("选课系统");
		} else if (e.getSource() == jbtLibrary) {
			System.out.println("图书馆");
		} else if (e.getSource() == jbtStore) {
			System.out.println("商店");
		} else if (e.getSource() == jbtLogout) {
			User userBack = clientSrv.logout(this.user);
			if (userBack == null) {
				System.out.println("登出失败");
			} else {
				System.out.println("登出成功");
				this.dispose();
			}
		}
	}
}
