package vclient.view.store;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import vclient.srv.ClientSrvHelper;

import common.vo.UserAccount;

/**
 * 管理员管理用户账户
 * @author goclis
 */
public class UserAccountManagePanel extends JPanel implements ActionListener {
	private String[] columnNames = { "用户ID", "用户余额" };
	private DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
	private JTable jtAccounts = new JTable(tableModel);
	private JButton jbtUpload = new JButton("更新用户账户");
	private JButton jbtShow = new JButton("显示用户账户");
	
	public UserAccountManagePanel() {
		JPanel jpBtns = new JPanel(new FlowLayout());
		jpBtns.add(jbtUpload);
		jpBtns.add(jbtShow);
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(jtAccounts));
		this.add(jpBtns, BorderLayout.SOUTH);
		
		jbtUpload.addActionListener(this);
		jbtShow.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbtUpload) { // 上传更新用户账户
			// 停止编辑状态
			if (jtAccounts.isEditing()) {
				jtAccounts.getCellEditor().stopCellEditing();
			}
			ArrayList<UserAccount> accounts = new ArrayList<UserAccount>();
			for (int i = 0; i < jtAccounts.getRowCount(); i++) {
				int userId = Integer.valueOf((String)jtAccounts.getValueAt(i, 0));
				double money = Double.valueOf((String) jtAccounts.getValueAt(i, 1));
				UserAccount account = new UserAccount(userId, money);
				accounts.add(account);
			}
			if (ClientSrvHelper.updateUserAccount(accounts)) {
				JOptionPane.showMessageDialog(this, "更新成功");
			}
		} else if (e.getSource() == jbtShow) { // 显示账户情况
			while (jtAccounts.getRowCount() > 0) {
				tableModel.removeRow(0);
			}
			ArrayList<UserAccount> accounts = ClientSrvHelper.queryAllAccount();
			for (int i = 0; i < accounts.size(); i++) {
				UserAccount account = accounts.get(i);
				String userId = String.valueOf(account.getUserId());
				String money = String.valueOf(account.getMoney());
				tableModel.addRow(new Object[] { userId, money });
			}
		}
	}
}
