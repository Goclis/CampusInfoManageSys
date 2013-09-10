/**
 * 
 */
package vclient.view.library.reader;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import vclient.srv.ClientSrvHelper;
import vclient.srv.library.ClientSrvOrderHelper;

import common.util.Variable;
import common.vo.library.Order;
import common.vo.library.Reader;

/**
 * 显示读者的预约信息的面板，有取消预约的功能
 * 
 * @author C5115
 * 
 */
public class OrderBookPanel extends JPanel implements Variable, ActionListener {

	private JButton cancelbt;
	private JPanel lowpanel;
	private ReaderLibraryFrame parentF;
	private Reader reader;
	private JTable table;
	private int selectedId;

	//private ClientSrvOrderHelper orderClient = new ClientSrvOrderHelper();

	/**
	 * @param reader
	 * @param readerLibraryFrame
	 * 
	 */
	public OrderBookPanel(ReaderLibraryFrame readerLibraryFrame, Reader reader) {
		this.parentF = readerLibraryFrame;
		this.reader = reader;
		this.setLayout(new BorderLayout());

		lowpanel = new JPanel();
		cancelbt = new JButton("取消");
		lowpanel.add(cancelbt);
		this.add(lowpanel, BorderLayout.SOUTH);
		Vector datas = new Vector();
		datas = ClientSrvHelper.findOrders(reader);
		table = new MyTable(datas, ORDER_VARIABLE_LIST);
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		JScrollPane sc = new JScrollPane(table, v, h);
		this.add(sc);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (table.getValueAt(table.getSelectedRow(), 0) != null)
					selectedId = Integer.parseInt(table.getValueAt(
							table.getSelectedRow(), 0).toString());
				System.out.println("选中" + selectedId);
			}
		});
		cancelbt.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == cancelbt) {
			System.out.println("点到了cancelbt");
			Order order = new Order();
			order.setOrderId(selectedId);
			order = ClientSrvHelper.cancelOrder(order, reader);

			if (order == null)
				JOptionPane.showMessageDialog(this, "取消预约失败");
			else
				JOptionPane.showMessageDialog(this, "取消预约成功");
		}
	}
}
