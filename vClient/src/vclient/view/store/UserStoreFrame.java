package vclient.view.store;


import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import common.vo.User;


public class UserStoreFrame extends JFrame {
	// attributes
	private Container c;
	private SearchPanel search;
	public ShoppingCardPanel shopping;
	private JTabbedPane tabbedp = new JTabbedPane();
	private User user;

	public UserStoreFrame() {
		this(null);
	}

	public UserStoreFrame(User user) {
		this.user = user;
		c = this.getContentPane();
		shopping = new ShoppingCardPanel(user);
		search = new SearchPanel(shopping);
		tabbedp = new JTabbedPane(JTabbedPane.LEFT);
		tabbedp.addTab("搜索商品", search);
		tabbedp.addTab("我的购物车", shopping);
		c.add(tabbedp);

		this.setSize(850, 600);
		Dimension screen = getToolkit().getScreenSize();
		this.setLocation((screen.width - this.getSize().width) / 2,
				(screen.height - this.getSize().height) / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UserStoreFrame frame = new UserStoreFrame();
	}
}
