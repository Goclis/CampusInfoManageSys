/**
 * 
 */
package vclient.view.library.reader;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import vclient.srv.ClientSrvHelper;
import vclient.srv.library.ClientSrvBookHelper;
import vclient.srv.library.ClientSrvBorrowHelper;
import vclient.srv.library.ClientSrvOrderHelper;

import common.util.FindType;
import common.util.Variable;
import common.vo.library.Book;
import common.vo.library.Borrow;
import common.vo.library.Order;
import common.vo.library.Reader;

/**
 * 查询书籍的面板，有按不同类型和关键字查询书籍，借书，预约的功能
 * 
 * @author zhongfang
 * 
 */
public class FindBookPanel extends JPanel implements ActionListener, Variable {

	// attributes
	private JRadioButton searchbt, classbt;
	private JComboBox type;
	private JTextField txt;
	private JButton findbt1, findbt2;
	private DefaultTableModel model = new DefaultTableModel(BOOK_VARIABLES_LIST, 0);
	private JTable table;
	private JPanel jp1, jp2, jpin1, jpin2, jp3, jpcenter, byKeyPanel,
			byClassPanel, lowJp;
	private ReaderLibraryFrame parentF;
	private JButton orderBt, borrowBt;
	private JComboBox searchbyCom;
	private Vector heads = new Vector();
	private Reader reader;
	private int selectedId;

	//private ClientSrvBookHelper bookClient = new ClientSrvBookHelper();
	//private ClientSrvBorrowHelper borrowClient = new ClientSrvBorrowHelper();
	//private ClientSrvOrderHelper orderClient = new ClientSrvOrderHelper();

	public FindBookPanel(ReaderLibraryFrame parentF, Reader reader) {
		
		this.setLayout(new BorderLayout());
		this.parentF = parentF;
		this.reader = reader;
		jp1 = new JPanel(); // 最上面有单选按钮
		searchbt = new JRadioButton("按关键字查询");
		classbt = new JRadioButton("按类别查询");
		ButtonGroup goup = new ButtonGroup();
		goup.add(searchbt);
		goup.add(classbt);
		searchbt.setSelected(true);
		jp1.add(searchbt);
		jp1.add(classbt);
		this.add(jp1, BorderLayout.NORTH);

		// 最下面有两个按钮执行借书和预约功能
		lowJp = new JPanel();
		borrowBt = new JButton("借书");
		orderBt = new JButton("预约");
		lowJp.add(borrowBt);
		lowJp.add(orderBt);
		this.add(lowJp, BorderLayout.SOUTH);

		jpcenter = new JPanel(new BorderLayout());
		Border border = BorderFactory
				.createEtchedBorder(Color.BLACK, Color.red);
		jpcenter.setBorder(border);
		byKeyPanel = new JPanel(new FlowLayout());
		byClassPanel = new JPanel(new FlowLayout());
		jpin1 = new JPanel(new CardLayout());
		searchbyCom = new JComboBox(BOOK_FIND_TYPE);
		txt = new JTextField(50);
		findbt1 = new JButton("查询");
		byKeyPanel.add(searchbyCom);
		byKeyPanel.add(txt);
		byKeyPanel.add(findbt1);
		type = new JComboBox(BOOK_TYPE_LIST);
		findbt2 = new JButton("查询");
		byClassPanel.add(type);
		byClassPanel.add(findbt2);

		jpin1.add(byKeyPanel, "one");
		jpin1.add(byClassPanel, "two");
		jpcenter.add(jpin1, BorderLayout.NORTH);
		this.add(jpcenter);

		heads.removeAllElements();
		for (int i = 0; i < BOOK_VARIABLES_LIST.length; i++) {
			heads.addElement(BOOK_VARIABLES_LIST[i]);
		}
		table = new JTable(model); // modify
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jpcenter.add(new JScrollPane(table));
		searchbt.addActionListener(this);
		classbt.addActionListener(this);
		findbt1.addActionListener(this);
		findbt2.addActionListener(this);
		borrowBt.addActionListener(this);
		orderBt.addActionListener(this);
		selectedId = 0;

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("选中啦");
				if (table.getValueAt(table.getSelectedRow(), 0) != null)
					selectedId = Integer.parseInt(table.getValueAt(
							table.getSelectedRow(), 0).toString());
				System.out.println("选中的行" + selectedId);
			}
		});
	}

	public FindBookPanel() {
		this(null, null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == searchbt)
			((CardLayout) jpin1.getLayout()).show(jpin1, "one");
		else if (arg0.getSource() == classbt)
			((CardLayout) jpin1.getLayout()).show(jpin1, "two");
		else if (arg0.getSource() == borrowBt)
			borrowBook();
		else if (arg0.getSource() == orderBt)
			orderBook();
		else if (arg0.getSource() == findbt1)
			findBook();
		else if (arg0.getSource() == findbt2)
			findBook();
	}

	private void findBook() {
		Integer findType = null;
		Vector datas = new Vector();
		if (searchbt.isSelected()) { // 按关键字查询
			int i = searchbyCom.getSelectedIndex();
			Book book = new Book();
			switch (i) {
			case 0:
				findType = FindType.BY_ID;
				book.setBookId(Integer.parseInt(txt.getText().trim()));
				break;
			case 1:
				findType = FindType.BY_NAME;
				book.setTitle(txt.getText().trim());
				break;
			case 2:
				findType = FindType.BY_AUTHOR;
				book.setAuthor(txt.getText().trim());
				break;
			case 3:
				findType = FindType.BY_CALLCODE;
				book.setCallCode(txt.getText().trim());
				break;
			case 5:
				findType = FindType.BY_PUBLISHER;
				book.setPublisher(txt.getText().trim());
				break;
			case 4:
				findType = FindType.BY_STOREPLACE;
				book.setStorePlace(txt.getText().trim());
				break;
			default:
				break;
			}
			datas = ClientSrvHelper.findBook(book, findType);
		} else { // 按类别查询
			int i = type.getSelectedIndex();
			String txt = BOOK_TYPE_LIST[i].trim();
			Book book = new Book();
			book.setType(txt);
			datas = ClientSrvHelper.findBook(book, FindType.BY_TYPE);
		}
		if (datas == null) {
			JOptionPane.showMessageDialog(this, "查询失败");
			return;
		} else {
			model.setDataVector(datas, heads);
		}
	}

	private void orderBook() {
		if (!reader.getStatus().equals("正常") || reader.getStatus().equals("欠费")) {
			JOptionPane.showMessageDialog(this, "读者证状态为" + reader.getStatus()
					+ "不能预约书籍");
		} else {
			if (selectedId == 0) {
				JOptionPane.showMessageDialog(this, "请选中对象");
				return;
			} else {
				String bookStatus = table
						.getValueAt(table.getSelectedRow(), 10).toString()
						.trim();
				if (!bookStatus.equals("可预约")) {
					JOptionPane.showMessageDialog(this, "该书籍的状态是" + bookStatus
							+ "\n 不可预约");
				} else {
					Book book = new Book();
					book.setBookId(selectedId);
					String callCode = table.getValueAt(table.getSelectedRow(),
							4).toString();
					book.setCallCode(callCode);
					Order order = new Order();
					order = ClientSrvHelper.orderBook(reader, book);
					if (order == null)
						JOptionPane
								.showMessageDialog(this,
										"预约失败 ！ \n 可能的原因有\n 有相同的书籍可供借阅\n 你已预约或正在借阅相同的书籍");
					else
						JOptionPane.showMessageDialog(this, "预约成功");
				}
			}
		}
	}

	private void borrowBook() {
		if (selectedId == 0) {
			JOptionPane.showMessageDialog(this, "请选中对象");
			return;
		} else {
			String bookStatus = table.getValueAt(table.getSelectedRow(), 10)
					.toString().trim();
			Book book = new Book();
			book.setBookId(selectedId);
			book.setCallCode(table.getValueAt(table.getSelectedRow(), 4)
					.toString());
			if (!bookStatus.equals("可借".trim())) {
				JOptionPane.showMessageDialog(this, "该书籍的状态是" + bookStatus
						+ "\n不可外借");
				return;
			} else {
				Borrow borrow = null;
				System.out.println(book.getBookId());
				borrow = ClientSrvHelper.borrowBook(reader, book);
				if (borrow == null) {
					JOptionPane
							.showMessageDialog(this,
									"借书失败！\n 可能原因有\n 有欠款而余额不足\n 已达到最大可借书籍数 \n 已预约或正在借阅相同的书籍");
					return;
				} else {
					JOptionPane.showMessageDialog(this, "借书成功");
					return;
				}
			}
		}
	}
}
