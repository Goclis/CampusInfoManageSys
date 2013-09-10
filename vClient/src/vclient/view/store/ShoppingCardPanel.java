package vclient.view.store;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;

import common.vo.User;
import common.vo.store.Good;
import common.vo.store.ShoppingItem;

import vclient.srv.ClientSrvHelper;

import java.awt.Dimension;
import java.util.ArrayList;

public class ShoppingCardPanel extends JPanel implements ActionListener {
	static String[] columnNames = { "商品编号", "商品名称", "单价", "类型", "数量" };
	
	JPanel jp1, jp2;
	JButton jbtCal, jbtPay, jbtCheckRestMoney, jbtRemoveGood;
	JLabel jlbTotalPrice = new JLabel("总价：0"); // 总价
	StoreTableModel model = new StoreTableModel(columnNames, 0);
	JTable tb = new ShoppingTable(model);
	
	private User user; // 用户

	public ShoppingCardPanel() {
		this(null);
		
		// test data
		user = new User();
		user.setId("213131111");
	}
	
	public ShoppingCardPanel(User user) {
		this.user = user;
		
		jbtCal = new JButton("结算");
		jbtCheckRestMoney = new JButton("查询我的账户余额");
		jbtPay = new JButton("付款");
		jbtRemoveGood = new JButton("删除商品");
		jp1 = new JPanel();
		jp1.add(jbtCal);
		jp1.add(jbtCheckRestMoney);
		jp1.add(jbtPay);
		jp1.add(jbtRemoveGood);

		jp2 = new JPanel(new BorderLayout());
		Border border = BorderFactory.createEtchedBorder(Color.BLACK, Color.red);
		//jp2.setBorder(border);
		jp2.add(new JScrollPane(tb));
		tb.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // 多选
		jp2.add(jlbTotalPrice, BorderLayout.SOUTH);
		jlbTotalPrice.setFont(new Font(jlbTotalPrice.getFont().getFamily(), Font.BOLD, 20));
		jlbTotalPrice.setHorizontalAlignment(JLabel.CENTER);
		
		jbtCal.addActionListener(this);
		jbtCheckRestMoney.addActionListener(this);
		jbtPay.addActionListener(this);
		jbtRemoveGood.addActionListener(this);
		
		this.setLayout(new BorderLayout());
		this.add(jp1, BorderLayout.SOUTH);
		this.add(jp2, BorderLayout.CENTER);
	}

	/**
	 * @return the tb
	 */
	// public static JTable getTb() {
	// return tb;
	// }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbtCal) { // 结算
			updateListTotalPrice();
		} else if (e.getSource() == jbtCheckRestMoney) { // 查询余额
			Double price = ClientSrvHelper.queryUserAccount(user);
			if (price == null) {
				JOptionPane.showMessageDialog(this, "通信失败");
			} else {
				JOptionPane.showMessageDialog(this, "您的余额为：" + price);
			}
		} else if (e.getSource() == jbtPay) { // 支付
			// 停止编辑保存内容
			if (tb.isEditing()) {
				tb.getCellEditor().stopCellEditing();
			}
			
			// 封装购物车信息
			ArrayList<ShoppingItem> shopItems = new ArrayList<ShoppingItem>();
			for (int i = 0; i < tb.getRowCount(); i++) {
				int goodId = Integer.valueOf((String) tb.getValueAt(i, 0));
				int goodNumber = Integer.valueOf((String) tb.getValueAt(i, 4));
				ShoppingItem item = new ShoppingItem(goodId, goodNumber);
				shopItems.add(item);
			}
			
			// 处理购物
			ArrayList<Integer> rs = ClientSrvHelper.buyGoods(shopItems, user);
			if (rs == null) {
				JOptionPane.showMessageDialog(this, "购买成功");
			} else if (rs.isEmpty()) {
				JOptionPane.showMessageDialog(this, "余额不足或网络问题");
			} else {
				String notice = "以下编号的商品缺货：\n";
				for (int i = 0; i < rs.size(); i++) {
					notice += i + "\n";
				}
				JOptionPane.showMessageDialog(this, notice);
			}
			
		} else if (e.getSource() == jbtRemoveGood) { // 移除商品
			while (tb.getSelectedRows().length != 0) {
				model.removeRow(tb.getSelectedRow());
			}
			updateListTotalPrice();
		}
	}
	
	/**
	 * 更新购物车当前总价
	 * @return
	 */
	private void updateListTotalPrice() {
		// 停止编辑保存内容
		if (tb.isEditing()) {
			tb.getCellEditor().stopCellEditing();
		}
		double sum = 0.0;
		for (int i = 0; i < tb.getRowCount(); i++) {
			sum += Double.valueOf((String) tb.getValueAt(i, 2))
					* Integer.valueOf((String) tb.getValueAt(i, 4));
		}
		jlbTotalPrice.setText("总价：" + String.valueOf(sum));
	}

	public void addList(ArrayList<Good> goods) {
		for (Good good : goods) {
			String gId = String.valueOf(good.getId());
			String gName = good.getName();
			String gPrice = String.valueOf(good.getPrice());
			String gType = good.getType();
			String gNumber = String.valueOf(good.getNumber());
			model.addRow(new Object[] { gId, gName, gPrice, gType, gNumber });
		}
		updateListTotalPrice();
	}
	
	/**
	 * 内部类继承JTable重写editingStopped以实现总价更新
	 * @author goclis
	 */
	private class ShoppingTable extends JTable {
		public ShoppingTable(StoreTableModel model) {
			super(model);
		}

		@Override
		public void editingStopped(ChangeEvent e) {
			super.editingStopped(e);
			updateListTotalPrice();
		}
	}
}

