/**
 * 
 */
package vclient.view.library.manager;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;

import common.util.Variable;

/**
 * 管理员管理的框架
 * @author zhongfang
 *
 */
public class ManagerLibraryFrame extends JFrame implements Variable{

	//attributes
	private JTabbedPane tabbedp;
	private Container c;

	
	public ManagerLibraryFrame(){
		c = this.getContentPane();
		
		tabbedp = new JTabbedPane(JTabbedPane.LEFT);
		
		tabbedp.addTab("书籍管理", new ManagePanel(this,BOOK_MANAGE));
		tabbedp.addTab("读者管理", new ManagePanel(this,READER_MANAGE));
		tabbedp.addTab("规则管理", new ManagePanel(this,RULE_MANAGE));
		c.add(tabbedp);
		
		Border border=BorderFactory.createEtchedBorder(Color.BLACK,Color.blue);
		
		this.setSize(850, 600);
		Dimension screen = getToolkit().getScreenSize();
		this.setLocation((screen.width-this.getSize().width)/2,
				(screen.height-this.getSize().height)/2);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		
	}

	public static void main(String[] args){
		new ManagerLibraryFrame();
		}
}
