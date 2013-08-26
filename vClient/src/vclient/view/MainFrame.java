/**
 * 
 */
package vclient.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;

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

	public MainFrame() {
		this(null);
	}

	private void setComponentAction() {
		jbtSchollRoll.addActionListener(this);
		jbtCourses.addActionListener(this);
		jbtLibrary.addActionListener(this);
		jbtStore.addActionListener(this);
	}

	private void setProperties() {
		this.setSize(500, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void initComponents() {
		// create a Box (BoxLayout) to hold selection buttons
		Box boxModules = Box.createHorizontalBox(); 
		Font ft = new Font(jbtSchollRoll.getFont().getFamily(), Font.BOLD, 20);
		boxModules.add(Box.createHorizontalGlue());
		boxModules.add(jbtSchollRoll);
		boxModules.add(Box.createHorizontalGlue());
		boxModules.add(jbtCourses);
		boxModules.add(Box.createHorizontalGlue());
		boxModules.add(jbtLibrary);
		boxModules.add(Box.createHorizontalGlue());
		boxModules.add(jbtStore);
		boxModules.add(Box.createHorizontalGlue());
		jbtSchollRoll.setFont(ft);
		jbtCourses.setFont(ft);
		jbtLibrary.setFont(ft);
		jbtStore.setFont(ft);
		
		this.setLayout(new BorderLayout());
		this.add(boxModules, BorderLayout.NORTH);
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
		}
	}
}
