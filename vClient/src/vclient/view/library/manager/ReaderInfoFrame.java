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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import vclient.srv.ClientSrvHelper;
import vclient.srv.library.ClientSrvReaderHelper;
import vclient.srv.library.ClientSrvRuleHelper;

import common.util.FindType;
import common.util.ObjectTransformer;
import common.util.Variable;
import common.vo.library.Reader;
import common.vo.library.Rule;

/**
 * 管理读者证的框架，有增加和修改的功能
 * 
 * @author zhongfang
 * 
 */
public class ReaderInfoFrame extends JFrame implements Variable, ActionListener {

	private JLabel idL, certifiTimeL, effectiveL, expireL, statusL, ruleL,
			moneyL, userIdL;
	private JTextField idF, certifiTimeF, effectiveF, expireF, moneyF, userIdF;
	private String userId, certifiTime, effectiveTime, expireTime, status,
			ruleName;
	private int id, ruleId;
	private double money;
	private JComboBox ruleF, statusF;
	private Container c;
	private int manageType;
	private JButton okay, cancel;
	private JPanel upPanel, lowPanel;
	private ManagePanel parent;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
	private Vector rules = new Vector();
	private Vector ruleInfo = new Vector();
	private Rule rule = new Rule();
	private Reader reader = new Reader();

	//private ClientSrvReaderHelper clientSrv = new ClientSrvReaderHelper();
	//private ClientSrvRuleHelper clientRuleSrv = new ClientSrvRuleHelper();

	/**
	 * 
	 * @param parent
	 * @param manageType
	 */
	public ReaderInfoFrame(ManagePanel parent, int manageType) {
		this.parent = parent;
		this.manageType = manageType;

		c = this.getContentPane();
		c.setLayout(new BorderLayout());
		upPanel = new JPanel(new GridLayout(8, 1));
		lowPanel = new JPanel(new FlowLayout());

		idL = new JLabel("编号");
		userIdL = new JLabel("学号");
		certifiTimeL = new JLabel("注册日期");
		effectiveL = new JLabel("生效日期");
		expireL = new JLabel("失效日期");
		moneyL = new JLabel("余额");
		statusL = new JLabel("状态");
		ruleL = new JLabel("规则");
		idF = new JTextField();
		userIdF = new JTextField();
		certifiTimeF = new JTextField();
		effectiveF = new JTextField();
		expireF = new JTextField();
		statusF = new JComboBox(READER_STATUS);
		moneyF = new JTextField();

		// 获取所有的Rule
		Rule rule = new Rule();
		rule.setRuleName("");
		rules = ClientSrvHelper.findRule(rule, FindType.BY_NAME);
		Vector rulesName = new Vector();
		String str = null;
		String str1;
		String str2;
		for (int i = 0; i < rules.size(); i++) {
			ruleInfo.removeAllElements();
			ruleInfo = (Vector) rules.get(i);

			int s1 = ObjectTransformer.getInteger(ruleInfo.get(0));
			str1 = Integer.toString(s1);
			str2 = (String) ruleInfo.get(1);
			str = str1 + "." + str2;
			rulesName.addElement(str);
		}
		ruleF = new JComboBox(rulesName);
		okay = new JButton("确定");
		cancel = new JButton("取消");

		determineComponents();
		upPanel.add(idL);
		upPanel.add(idF);
		upPanel.add(userIdL);
		upPanel.add(userIdF);
		upPanel.add(certifiTimeL);
		upPanel.add(certifiTimeF);
		upPanel.add(effectiveL);
		upPanel.add(effectiveF);
		upPanel.add(expireL);
		upPanel.add(expireF);
		upPanel.add(moneyL);
		upPanel.add(moneyF);
		upPanel.add(statusL);
		upPanel.add(statusF);
		upPanel.add(ruleL);
		upPanel.add(ruleF);
		lowPanel.add(okay);
		lowPanel.add(cancel);
		c.add(lowPanel, BorderLayout.SOUTH);
		c.add(upPanel);

		this.setSize(400, 350);
		Dimension screen = getToolkit().getScreenSize();
		this.setLocation((screen.width - this.getSize().width) / 2,
				(screen.height - this.getSize().height) / 2);
		this.setVisible(true);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okay.addActionListener(this);
		cancel.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okay) {
			if (manageType == ADD) {
				if (idF.getText().equals("") || userIdF.getText().equals("")
						|| moneyF.getText().equals("")) {
					JOptionPane.showMessageDialog(this, "请输入完整的信息");
					return;
				} else { // add
					getReaderText();

					Reader reader = new Reader(id, userId, money, rule);
					reader.setStatus("未激活");
					reader = ClientSrvHelper.addReader(reader);

					if (reader == null)
						JOptionPane.showMessageDialog(this,
								"添加失败，原因可能是：\n该读者证已存在 \n 该用户不存在 \n 该用户已经拥有读者证");
					else {
						JOptionPane.showMessageDialog(this, "添加成功");
					}
					shutDown();
				}
			} else { // modify
				String s1 = effectiveF.getText().trim();
				String s2 = expireF.getText().trim();
				try {
					Date d1 = sdf.parse(s1);
					Date d2 = sdf.parse(s2);
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(this, "请输入正确的日期格式！");
					return;
				}
				getReaderText();
				// 封装reader
				reader.setReaderId(id);
				reader.setUserId(userId);
				reader.setCertificateTime(certifiTime);
				reader.setEffectiveTime(effectiveTime);
				reader.setExpireTime(expireTime);
				reader.setMoney(money);
				reader.setStatus(status);
				reader.setRule(rule);

				reader = ClientSrvHelper.modifyReader(reader);
				if (reader == null) {
					JOptionPane.showMessageDialog(this, "修改信息失败");
				} else {
					JOptionPane.showMessageDialog(this, "修改信息成功");
				}
				shutDown();
			}
		} else { // 点击了cancel按钮
			shutDown();
		}
	}

	/**
	 * 关闭当前窗口
	 */
	private void shutDown() {
		this.dispose();
		this.parent.getParentF().setVisible(true);
	}

	/**
	 * 获取面板上的信息，获取并封装rule
	 */
	private void getReaderText() {
		id = Integer.parseInt(idF.getText().trim());
		userId = userIdF.getText().trim();
		certifiTime = certifiTimeF.getText().trim();
		effectiveTime = effectiveF.getText().trim();
		expireTime = expireF.getText().trim();
		money = Double.parseDouble(moneyF.getText().trim());
		status = READER_STATUS[statusF.getSelectedIndex()].trim();

		String s = (String) ruleF.getSelectedItem();
		int i = s.indexOf(".");
		String s1 = s.substring(0, i);
		ruleId = Integer.parseInt(s1);

		Vector rules = new Vector();
		Vector arule = new Vector();
		rule.setRuleId(ruleId);
		rules = ClientSrvHelper.findRule(rule, FindType.BY_ID);
		arule = (Vector) rules.get(0);
		rule.setRuleId(ObjectTransformer.getInteger(arule.get(0)));
		rule.setRuleName((String) arule.get(1));
		rule.setMaxBorrowDays(ObjectTransformer.getInteger(arule.get(2)));
		rule.setMaxRenewDays(ObjectTransformer.getInteger(arule.get(3)));
		rule.setMaxRenewTimes(ObjectTransformer.getInteger(arule.get(4)));
		rule.setKeepOrderDays(ObjectTransformer.getInteger(arule.get(5)));
		rule.setMaxBorrowBooks(ObjectTransformer.getInteger(arule.get(6)));
	}

	/**
	 * 通过管理类型来决定面板上的控件显示
	 */
	private void determineComponents() {
		if (manageType == ADD) {
			certifiTimeF.setText("今日");
			certifiTimeF.setEditable(false);
			effectiveF.setText("两天后");
			effectiveF.setEditable(false);
			expireF.setText("四年后");
			expireF.setEditable(false);
			statusF.setSelectedIndex(0);
			statusF.setEnabled(false);
		} else {
			idF.setEditable(false);
			userIdF.setEditable(false);
			certifiTimeF.setEditable(false);

			Reader reader = new Reader();
			int readerId = this.parent.getSelectedId();
			reader.setReaderId(readerId);
			Vector readers = new Vector();
			readers = ClientSrvHelper.findReader(reader, FindType.BY_ID);
			Vector readerInfo = new Vector();
			readerInfo = (Vector) readers.get(0);

			idF.setText(Integer.toString(ObjectTransformer.getInteger(readerInfo.get(0))));
			userIdF.setText((String) readerInfo.get(1));
			certifiTimeF.setText((String) readerInfo.get(2));
			effectiveF.setText((String) readerInfo.get(3));
			expireF.setText((String) readerInfo.get(4));
			moneyF.setText(Double.toString(ObjectTransformer.getDouble(readerInfo.get(5))));
			statusF.setSelectedItem(readerInfo.get(6));
			ruleF.setSelectedItem(readerInfo.get(7));
		}
	}
}
