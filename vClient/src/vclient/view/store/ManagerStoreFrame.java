/**
 * 
 */
package vclient.view.store;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import common.vo.User;

/**
 * 管理员的商店界面
 * @author goclis
 */
public class ManagerStoreFrame extends JFrame {
	private Container c;
	private SearchPanel search;
	public ShoppingCardPanel shopping;
	private JTabbedPane tabbedp;
	
	public ManagerStoreFrame() {
		c = this.getContentPane();
		search = new SearchPanel();
		UserAccountManagePanel userAccount = new UserAccountManagePanel();
		tabbedp = new JTabbedPane(JTabbedPane.LEFT);
		tabbedp.addTab("搜索商品", search);
		tabbedp.addTab("用户账户管理", userAccount);
		c.add(tabbedp);

		this.setSize(850, 600);
		Dimension screen = getToolkit().getScreenSize();
		this.setLocation((screen.width - this.getSize().width) / 2,
				(screen.height - this.getSize().height) / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new ManagerStoreFrame();
	}
}
