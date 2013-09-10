package vclient.view.schoolroll;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//教务员管理界面，负责学生信息的添加，查询，删除等
public class SchoolRollManagerFrame extends JFrame {
	public static final String sManageStuInfoInc = null;
	// 学生信息管理选项
	JMenu jmbStuManage = new JMenu("学生信息管理");// 学生信息管理菜单
	private JButton jbtStuIncreat = new JButton("新增");
	private JButton jbtStuQuery = new JButton("查询");
	private JButton jbtStuDelete = new JButton("删除");

	private JPanel jpThreeManager;

	JFrame jfStu;

	public SchoolRollManagerFrame() {
		JMenuBar jmbManager = new JMenuBar();// 教务员管理菜单
		setJMenuBar(jmbManager);
		// 学生信息管理菜单
		jmbManager.add(jmbStuManage);
		jmbStuManage.add(jbtStuIncreat);
		jmbStuManage.add(jbtStuQuery);
		jmbStuManage.add(jbtStuDelete);
		// 创建Panel装以上子界面
		jpThreeManager = new JPanel(new BorderLayout());
		add(jpThreeManager, BorderLayout.CENTER);
		// 创建一个监听对象b
		ButtonListener listener = new ButtonListener();
		jbtStuIncreat.addActionListener(listener);
		jbtStuQuery.addActionListener(listener);
		jbtStuDelete.addActionListener(listener);
		setTitle("教务员登录界面");
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		jfStu = this;
	}

	// 内部监听类
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			jpThreeManager.removeAll();
			if (e.getSource() == jbtStuIncreat) { // 新增
				jmbStuManage.setPopupMenuVisible(false);
				jpThreeManager.add((new ManageStuInfoInc()).jpSInformation1);

			}
			if (e.getSource() == jbtStuQuery) { // 查询
				jmbStuManage.setPopupMenuVisible(false);
				jpThreeManager.add((new ManageStuInfoQuery(jfStu)).jpStuQuery);
			}

			else if (e.getSource() == jbtStuDelete) { // 删除
				jpThreeManager.add(new ManageStuInfoDelete().jpStuDel);

			}
			repaint();
			revalidate();

		}
	}
}
