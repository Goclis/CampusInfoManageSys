/**
 * 
 */
package vclient.view.library.manager;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import vclient.srv.ClientSrvHelper;

import common.util.FindType;
import common.util.ObjectTransformer;
import common.util.Variable;
import common.vo.library.Rule;

/**
 * 管理规则的框架，有增加和修改的功能
 * 
 * @author zhongfang
 * 
 */
public class RuleInfoFrame extends JFrame implements ActionListener, Variable {

	private JLabel idL, nameL, maxBorrowDaysL, maxRenewDaysL, maxRenewTimesL,
			keepOrderDaysL, maxBorrowBooksL;
	private JTextField idF, nameF, maxBorrowDaysF, maxRenewDaysF,
			maxRenewTimesF, keepOrderDaysF, maxBorrowBooksF;
	private int ruleId, maxBorrowDays, maxRenewDays, maxRenewTimes,
			keepOrderDays, maxBorrowBooks;
	private String ruleName;
	private Container c;
	private JButton okay, cancel;
	private JPanel upPanel, lowPanel;
	private ManagePanel parentP;
	private int manageType;
	private Rule rule = new Rule();

	//private ClientSrvRuleHelper clientSrv = new ClientSrvRuleHelper();

	/**
	 * 
	 */
	public RuleInfoFrame(ManagePanel parentP, int i) {
		// TODO Auto-generated constructor stub
		this.parentP = parentP;
		this.manageType = i;

		c = this.getContentPane();
		c.setLayout(new BorderLayout());

		upPanel = new JPanel(new GridLayout(7, 2));
		idL = new JLabel("规则编号");
		nameL = new JLabel("规则名称");
		maxBorrowDaysL = new JLabel("最大借阅天数");
		maxRenewDaysL = new JLabel("最大续借天数");
		maxRenewTimesL = new JLabel("最大续借次数");
		keepOrderDaysL = new JLabel("保存预约天数");
		maxBorrowBooksL = new JLabel("最多可借书本数");
		idF = new JTextField();
		nameF = new JTextField();
		maxBorrowDaysF = new JTextField();
		maxRenewDaysF = new JTextField();
		maxRenewTimesF = new JTextField();
		keepOrderDaysF = new JTextField();
		maxBorrowBooksF = new JTextField();

		determineComponents();

		upPanel.add(idL);
		upPanel.add(idF);
		upPanel.add(nameL);
		upPanel.add(nameF);
		upPanel.add(maxBorrowDaysL);
		upPanel.add(maxBorrowDaysF);
		upPanel.add(maxRenewDaysL);
		upPanel.add(maxRenewDaysF);
		upPanel.add(maxRenewTimesL);
		upPanel.add(maxRenewTimesF);
		upPanel.add(keepOrderDaysL);
		upPanel.add(keepOrderDaysF);
		upPanel.add(maxBorrowBooksL);
		upPanel.add(maxBorrowBooksF);

		lowPanel = new JPanel(new FlowLayout());
		okay = new JButton("确定");
		cancel = new JButton("取消");
		lowPanel.add(okay);
		lowPanel.add(cancel);

		c.add(lowPanel, BorderLayout.SOUTH);
		c.add(upPanel);

		okay.addActionListener(this);
		cancel.addActionListener(this);

		this.setSize(550, 350);
		Dimension screen = getToolkit().getScreenSize();
		this.setLocation((screen.width - this.getSize().width) / 2,
				(screen.height - this.getSize().height) / 2);
		this.setVisible(true);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == okay) {
			if (manageType == ADD) {
				// TODO 获取信息，添加，
				if (idF.getText().equals("") || nameF.getText().equals("")
						|| maxBorrowDaysF.getText().equals("")
						|| maxRenewDaysF.getText().equals("")
						|| maxRenewTimesF.getText().equals("")
						|| keepOrderDaysF.getText().equals("")
						|| maxBorrowBooksF.getText().equals("")) {
					JOptionPane.showMessageDialog(this, "请输入所有的数据");
				} else {
					getRuleText();
					Rule rule = new Rule(ruleId, ruleName, maxBorrowDays,
							maxRenewDays, maxRenewTimes, keepOrderDays,
							maxBorrowBooks);
					System.out.println("界面rule.getRuleId()" + rule.getRuleId());
					System.out.println("界面rule.getBooks"
							+ rule.getMaxBorrowBooks());
					rule = ClientSrvHelper.addRule(rule);
					if (rule == null)
						JOptionPane.showMessageDialog(this, "该规则已存在，添加失败");
					else
						JOptionPane.showMessageDialog(this, "添加成功");

					shutDown();
				}
			} else { // modify
				getRuleText();
				rule.setRuleId(ruleId);
				rule.setRuleName(ruleName);
				rule.setMaxBorrowDays(maxBorrowDays);
				rule.setMaxRenewDays(maxRenewDays);
				rule.setMaxRenewTimes(maxRenewTimes);
				rule.setKeepOrderDays(keepOrderDays);
				rule.setMaxBorrowBooks(maxBorrowBooks);

				rule = ClientSrvHelper.modifyRule(rule);

				if (rule == null)
					JOptionPane.showMessageDialog(this, "修改信息失败");
				else
					JOptionPane.showMessageDialog(this, "修改信息成功");

				this.shutDown();
			}
		} else if (arg0.getSource() == cancel) {
			shutDown();
		}
	}

	private void shutDown() {
		// TODO Auto-generated method stub
		this.dispose();
		this.parentP.getParentF().setVisible(true);
	}

	private void determineComponents() {
		if (manageType == MODIFY) {
			idF.setEditable(false);

			// 利用rule信息来设置面板
			ruleId = this.parentP.getSelectedId();
			rule.setRuleId(ruleId);
			Vector rules = new Vector();
			rules = ClientSrvHelper.findRule(rule, FindType.BY_ID);
			Vector ruleInfo = new Vector();
			ruleInfo = (Vector) rules.get(0);
			ruleId = ObjectTransformer.getInteger(ruleInfo.get(0));
			ruleName = (String) ruleInfo.get(1);
			maxBorrowDays = ObjectTransformer.getInteger(ruleInfo.get(2));
			maxRenewDays = ObjectTransformer.getInteger(ruleInfo.get(3));
			maxRenewTimes = ObjectTransformer.getInteger(ruleInfo.get(4));
			keepOrderDays = ObjectTransformer.getInteger(ruleInfo.get(5));
			maxBorrowBooks = ObjectTransformer.getInteger(ruleInfo.get(6));
			idF.setText(Integer.toString(ruleId));
			nameF.setText(ruleName);
			maxBorrowDaysF.setText(Integer.toString(maxBorrowDays));
			maxRenewDaysF.setText(Integer.toString(maxRenewDays));
			maxRenewTimesF.setText(Integer.toString(maxRenewTimes));
			keepOrderDaysF.setText(Integer.toString(keepOrderDays));
			maxBorrowBooksF.setText(Integer.toString(maxBorrowBooks));
		}
	}

	/**
	 * 获取面板上的信息，并封装
	 */
	private void getRuleText() {
		ruleId = Integer.parseInt(idF.getText());
		System.out.print("在界面里ID" + ruleId);
		ruleName = nameF.getText();
		maxBorrowDays = Integer.parseInt(maxBorrowDaysF.getText());
		maxRenewDays = Integer.parseInt(maxRenewDaysF.getText());
		maxRenewTimes = Integer.parseInt(maxRenewTimesF.getText());
		keepOrderDays = Integer.parseInt(keepOrderDaysF.getText());
		maxBorrowBooks = Integer.parseInt(maxBorrowBooksF.getText());
		rule.setRuleId(ruleId);
		rule.setRuleName(ruleName);
		rule.setMaxBorrowDays(maxBorrowDays);
		rule.setMaxRenewDays(maxRenewDays);
		rule.setMaxRenewTimes(maxRenewTimes);
		rule.setKeepOrderDays(keepOrderDays);
		rule.setMaxBorrowBooks(maxBorrowBooks);
	}
}
