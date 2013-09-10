package vclient.view.schoolroll;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import common.vo.schoolroll.MessageToStuDel;
import vclient.srv.ClientSrvHelper;

//学生信息删除
public class ManageStuInfoDelete {

	JComboBoxListener listener = new JComboBoxListener();
	JTextField jtfDel = new JTextField(10);
	String stuDel;// 姓名或学号
	int delFlag;
	public JPanel jpBox = new JPanel();
	JPanel jpStuDel = new JPanel(new BorderLayout());
	JScrollPane jscrollPane1 = new JScrollPane();
	private JButton jbtDelConfirm = new JButton("确定");
	String[] petStrings = { "学号", "姓名" };
	JComboBox box = new JComboBox(petStrings);

	// 构造函数初始化界面
	public ManageStuInfoDelete() {
		jbtDelConfirm.addActionListener(listener);
		box.addActionListener(listener);

		box.setSelectedIndex(0);
		jpBox.add(new JLabel("删除类别："));
		jpBox.add(box);
		jpBox.add(jtfDel);
		jpBox.add(jbtDelConfirm);
		jpStuDel.add(jpBox, BorderLayout.NORTH);
	}

	// 按钮的消息响应
	class JComboBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == box) {
				int index = box.getSelectedIndex();
				switch (index) {
				case 0:
					delFlag = 1;// 1：按学号查找
					break;
				case 1:
					delFlag = 2;// 按姓名查找
					break;
				}
			} else if (e.getSource() == jbtDelConfirm) {
				stuDel = jtfDel.getText().trim();
				MessageToStuDel messageToStuDel = new MessageToStuDel(stuDel,
						delFlag);// 封装消息
				int status = ClientSrvHelper.deleteStuInfo(messageToStuDel);
				if (status == 0)
					JOptionPane.showMessageDialog(null, "删除失败，请重新输入");
				else
					JOptionPane.showMessageDialog(null, "已成功删除" + status
							+ "条记录");
			}
		}
	}
}
