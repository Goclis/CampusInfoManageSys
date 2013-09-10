/**
 * 
 */
package vclient.view.library.reader;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import vclient.srv.ClientSrvHelper;
import vclient.srv.library.ClientSrvBorrowHelper;
import vclient.srv.library.ClientSrvReaderHelper;
import vclient.srv.library.ClientSrvRuleHelper;

import common.util.FindType;
import common.util.ObjectTransformer;
import common.util.Variable;
import common.vo.User;
import common.vo.library.Borrow;
import common.vo.library.Reader;
import common.vo.library.Rule;

/**
 * 读者图书馆的框架
 * @author zhongfang
 */
public class ReaderLibraryFrame extends JFrame implements Variable {

	// attributes
	private Container c;
	private JPanel jp1, jp2;
	private JTabbedPane tabbedp;
	private User user;
	private Reader reader;
	private JPanel imagePanel;
	private JTable historyTable, punishTable;

	//private ClientSrvReaderHelper readerClient = new ClientSrvReaderHelper();
	//private ClientSrvRuleHelper clientRuleSrv = new ClientSrvRuleHelper();
	//private ClientSrvBorrowHelper borrowClient = new ClientSrvBorrowHelper();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public ReaderLibraryFrame(User user) {
		c = this.getContentPane();
		this.user = user;
		this.reader = getReader();

		checkReader();

		Border border = BorderFactory.createEtchedBorder(Color.BLACK,
				Color.blue);
		jp1 = new JPanel();
		jp1.setBorder(border);
		historyTable = getHistoryTable();
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		JScrollPane scroll = new JScrollPane(historyTable, v, h);
		punishTable = getPunishTable();
		JScrollPane scroll2 = new JScrollPane(punishTable, v, h);
		tabbedp = new JTabbedPane(JTabbedPane.LEFT);
		tabbedp.addTab("查询书籍", new FindBookPanel(this, reader));
		tabbedp.addTab("正在借阅", new BorrowingBookPanel(this, reader));
		tabbedp.addTab("借阅历史", scroll);
		tabbedp.addTab("我的预约", new OrderBookPanel(this, reader));
		tabbedp.addTab("罚款信息", scroll2);
		tabbedp.addTab("个人信息", new CardInfoPanel(this));
		c.add(tabbedp);

		this.setSize(850, 600);
		Dimension screen = getToolkit().getScreenSize();
		this.setLocation((screen.width - this.getSize().width) / 2,
				(screen.height - this.getSize().height) / 2);
		this.setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		User user = new User("213113694", "123", "学生");
		new ReaderLibraryFrame(user);
	}

	// 从传递过来的user获取读者信息，并封装了reader和rule
	public Reader getReader() {
		String userId = user.getId();
		Reader reader = new Reader();
		reader.setUserId(userId);
		Vector readers = new Vector();
		Vector readerInfo = new Vector();
		
		//ClientSrvReaderHelper readerClient = new ClientSrvReaderHelper();
		//readers = readerClient.findReader(reader, FindType.BY_USER_ID);
		readers = ClientSrvHelper.findReader(reader, FindType.BY_USER_ID);
		
		readerInfo = (Vector) readers.get(0);
		reader.setReaderId(ObjectTransformer.getInteger(readerInfo.get(0)));
		reader.setUserId((String) readerInfo.get(1));
		reader.setCertificateTime((String) readerInfo.get(2));
		reader.setEffectiveTime((String) readerInfo.get(3));
		reader.setExpireTime((String) readerInfo.get(4));
		reader.setMoney(ObjectTransformer.getDouble(readerInfo.get(5)));
		reader.setStatus((String) readerInfo.get(6));
		Rule rule = new Rule();
		String readerrule = (String) readerInfo.get(7);
		String ruleI = readerrule.substring(0, readerrule.indexOf("."));
		int ruleId = Integer.parseInt(ruleI);
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
		reader.setRule(rule);
		return reader;
	}

	// 显示借阅历史
	public JTable getHistoryTable() {
		Borrow borrow = new Borrow();
		borrow.setReader(reader);
		Vector datas = new Vector();
		datas = ClientSrvHelper.findBorrow(reader, FindType.PAST);
		MyTable mt = new MyTable(datas, BORROW_PAST_LIST);
		return mt;
	}

	// 罚款信息
	public JTable getPunishTable() {
		Vector datas = new Vector();
		datas = ClientSrvHelper.findPunishment(reader);
		MyTable mt = new MyTable(datas, PUNISH_VARIABLE_LIST);
		return mt;
	}

	public void checkReader() {
		Calendar ca = Calendar.getInstance();
		Date now = ca.getTime();
		Date certificate = null, effective = null, expire = null;
		try {
			certificate = sdf.parse(reader.getCertificateTime());
			effective = sdf.parse(reader.getEffectiveTime());
			expire = sdf.parse(reader.getExpireTime());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		if (now.before(certificate)) {
			reader.setStatus("未注册");
			ClientSrvHelper.modifyReader(reader);
//			System.out.print("设置为未注册");
		} else if (now.after(certificate) && now.before(effective)) {
			reader.setStatus("未激活");
			ClientSrvHelper.modifyReader(reader);
//			System.out.println("设置为未激活");
		} else if (now.after(effective) && (reader.getStatus().equals("未激活"))) {
			reader.setStatus("正常");
			ClientSrvHelper.modifyReader(reader);
//			System.out.println("时间到，激活读者证");
		} else if (now.after(expire)) {
			JOptionPane.showMessageDialog(this, "读者证已过期");
			ClientSrvHelper.deleteReader(reader);
			return;
		}

		if (!(reader.getStatus().equals("正常") || reader.getStatus()
				.equals("欠费")))
			JOptionPane.showMessageDialog(this, "你的读者证状态为" + reader.getStatus()
					+ "\n 请联系管理员");
	}

}
