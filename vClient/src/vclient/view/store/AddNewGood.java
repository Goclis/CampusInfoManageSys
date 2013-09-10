/**
 * 
 */
package vclient.view.store;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import common.vo.store.Good;

import vclient.srv.ClientSrvHelper;

/**
 * 添加新商品
 * @author goclis
 */
public class AddNewGood extends JFrame implements ActionListener {
	private String[] GOOD_TYPE_LIST = { "食物", "饮品", "水果", "学习用品", "生活用品", "其他" };
	private JLabel jlbName = new JLabel("商品名");
	private JLabel jlbPrice = new JLabel("商品价格");
	private JLabel jlbNumber = new JLabel("商品数量");
	private JLabel jlbType = new JLabel("商品数量");
	
	private JTextField jtfName = new JTextField(10);
	private JTextField jtfPrice = new JTextField(10);
	private JTextField jtfNumber = new JTextField(10);
	private JComboBox<String> jcboTypes = new JComboBox<String>(GOOD_TYPE_LIST);
	
	private JButton jbtOk = new JButton("确定");
	
	public AddNewGood() {
		JPanel jpTop = new JPanel(new GridLayout(4, 2));
		jpTop.add(jlbName);
		jpTop.add(jtfName);
		jpTop.add(jlbPrice);
		jpTop.add(jtfPrice);
		jpTop.add(jlbNumber);
		jpTop.add(jtfNumber);
		jpTop.add(jlbType);
		jpTop.add(jcboTypes);
		
		this.setLayout(new BorderLayout());
		this.add(jpTop);
		this.add(jbtOk, BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		
		jbtOk.addActionListener(this);
	}
	
	public static void main(String[] args) {
		new AddNewGood();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String gName = jtfName.getText().trim();
		double gPrice = Double.valueOf(jtfPrice.getText().trim());
		int gNumber = Integer.valueOf(jtfNumber.getText().trim());
		String gType = (String) jcboTypes.getSelectedItem();
		if (gName.isEmpty() || gPrice < 0 || gNumber < 0) {
			JOptionPane.showMessageDialog(getOwner(), "输入有误，请重新输入");
		} else {
			Good good = new Good(gName, gPrice, gNumber, gType);
			if (ClientSrvHelper.addNewGood(good) != null) {
				JOptionPane.showMessageDialog(this, "添加成功");
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "添加失败");
			}
		}
	}
}
