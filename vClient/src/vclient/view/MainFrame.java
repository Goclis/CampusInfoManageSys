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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import common.vo.User;

import vclient.srv.ClientSrvHelper;
import vclient.view.course.StudentCourseFrame;
import vclient.view.course.TeacherCourseFrame;
import vclient.view.library.manager.ManagerLibraryFrame;
import vclient.view.library.reader.ReaderLibraryFrame;
import vclient.view.schoolroll.SchoolRollManagerFrame;
import vclient.view.schoolroll.SchoolRollStudentFrame;
import vclient.view.store.ManagerStoreFrame;
import vclient.view.store.UserStoreFrame;

/**
 * 主界面
 * @author goclis
 */
public class MainFrame extends JFrame implements ActionListener {
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
	
	public MainFrame(User user) {
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
	
	/**
	 * 添加事件响应
	 */
	private void setComponentAction() {
		jbtSchollRoll.addActionListener(this);
		jbtCourses.addActionListener(this);
		jbtLibrary.addActionListener(this);
		jbtStore.addActionListener(this);
		jbtLogout.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				ClientSrvHelper.logout(user);
			}
		});
	}
	
	/**
	 * 设置框架属性
	 */
	private void setProperties() {
		this.setSize(200, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, 100);
		
		this.setVisible(true);
	}
	
	/**
	 * 初始化控件及布局
	 */
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
			if (user.getIdentity().equals("学生")) {
				JFrame frame = new SchoolRollStudentFrame(user);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			} else {
				JFrame frame = new SchoolRollManagerFrame();
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		} else if (e.getSource() == jbtCourses) {
			System.out.println("选课系统");
			if (user.getIdentity().equals("学生")) {
				JFrame frame = new StudentCourseFrame(this.user);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			} else {
				JFrame frame = new TeacherCourseFrame(this.user);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		} else if (e.getSource() == jbtLibrary) {
			System.out.println("图书馆");
			if (user.getIdentity().equals("管理员")) {
				JFrame frame = new ManagerLibraryFrame();
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			} else {
				JFrame frame = new ReaderLibraryFrame(user);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		} else if (e.getSource() == jbtStore) {
			System.out.println("商店");
			if (!user.getIdentity().equals("管理员")) {
				JFrame frame = new UserStoreFrame(user);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			} else { // 管理员界面
				JFrame frame = new ManagerStoreFrame();
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		} else if (e.getSource() == jbtLogout) {
			User userBack = ClientSrvHelper.logout(this.user);
			if (userBack == null) {
				System.out.println("登出失败");
			} else {
				System.out.println("登出成功");
				this.dispose();
			}
		}
	}
}
