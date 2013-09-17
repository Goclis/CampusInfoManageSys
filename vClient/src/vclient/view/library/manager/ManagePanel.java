package vclient.view.library.manager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import vclient.srv.ClientSrvHelper;
import vclient.srv.library.ClientSrvBookHelper;
import vclient.srv.library.ClientSrvReaderHelper;
import vclient.srv.library.ClientSrvRuleHelper;

import common.util.FindType;
import common.util.Variable;
import common.vo.library.Book;
import common.vo.library.Reader;
import common.vo.library.Rule;

/**
 * 管理员管理面板。是管理的主面板 用于查找信息，有删除功能，并弹出增加和修改的框架，
 * 
 * @author zhongfang
 * 
 */
public class ManagePanel extends JPanel implements ActionListener, Variable {

	// attributes
	private JButton addbt, deletebt, modifybt;
	private JPanel p1;
	private String bt1, bt2, bt3;
	private int manageThing;
	private JFrame parentF;
	private JButton findbt;
	private JTextField jtf;
	private static int selectedId;
	private JRadioButton idBt;
	private JRadioButton nameBt;
	private JTable table;
	private Vector colNames = new Vector();
	private String[] heads = null;

	private Book book = new Book();
	private Reader reader = new Reader();
	private Rule rule = new Rule();

	//private ClientSrvBookHelper bookClientSrv = new ClientSrvBookHelper();
	//private ClientSrvReaderHelper readerClientSrv = new ClientSrvReaderHelper();
	//private ClientSrvRuleHelper ruleClientSrv = new ClientSrvRuleHelper();

	public ManagePanel(JFrame parentF, Integer manageThing) {
		this.setLayout(new BorderLayout());
		this.parentF = parentF;
		this.manageThing = manageThing;

		manageType();
		if (manageThing == BOOK_MANAGE) {
			heads = BOOK_VARIABLES_LIST;
		} else if (manageThing == READER_MANAGE) {
			heads = READER_VARIABLES_LIST;
		} else
			heads = RULE_VARIABLES_LIST;

		colNames.removeAllElements();
		for (int i = 0; i < heads.length; i++) {// NullPointerException
			colNames.addElement(heads[i]);
		}

		addbt = new JButton(bt1);
		deletebt = new JButton(bt2);
		modifybt = new JButton(bt3);
		p1 = new JPanel();
		p1.add(addbt);
		p1.add(modifybt);
		p1.add(deletebt);
		this.add(p1, BorderLayout.SOUTH);

		addbt.addActionListener(this);
		deletebt.addActionListener(this);
		modifybt.addActionListener(this);

		table = new JTable(new DefaultTableModel());
		this.add(new JScrollPane(table));

		p1 = new JPanel();
		idBt = new JRadioButton("编号");
		nameBt = new JRadioButton("名称");
		if (manageThing == READER_MANAGE) {
			nameBt.setText("一卡通号");
		}
		idBt.setSelected(true);

		ButtonGroup group = new ButtonGroup();
		group.add(nameBt);
		group.add(idBt);
		p1.add(idBt);
		p1.add(nameBt);
		jtf = new JTextField(30);
		p1.add(jtf);
		findbt = new JButton("查找");
		p1.add(findbt);
		this.add(p1, BorderLayout.NORTH);

		findbt.addActionListener(this);

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

	private void manageType() {
		// TODO Auto-generated method stub
		switch (manageThing) {
		case 1:
			bt1 = "添加书籍";
			bt3 = "修改书籍";
			bt2 = "删除书籍";
			heads = BOOK_VARIABLES_LIST;
			break;
		case 2:
			bt1 = "添加读者证";
			bt3 = "修改读者证";
			bt2 = "删除读者证";
			break;
		case 3:
			bt1 = "添加规则";
			bt3 = "修改规则";
			bt2 = "删除规则";
			break;
		default:
			break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addbt) {
			if (manageThing == BOOK_MANAGE) {
				new BookInfoFrame(this, ADD);
			} else if (manageThing == READER_MANAGE) {
				new ReaderInfoFrame(this, ADD);
			} else if (manageThing == RULE_MANAGE) {
				new RuleInfoFrame(this, ADD);
			}
			parentF.setVisible(false);
		} else if (e.getSource() == deletebt) {
			if (selectedId == 0) {
				JOptionPane.showMessageDialog(this, "请选中对象！");
				return;
			} else {
				int res = JOptionPane.showConfirmDialog(this, "确认删除该对象？", "确认",
						JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					delete();
				}
				this.cleanTable();
			}
		} else if (e.getSource() == modifybt) {
			if (selectedId == 0) {
				JOptionPane.showMessageDialog(this, "请选中对象！");
				return;
			} else {
				this.parentF.setVisible(false);
				if (manageThing == BOOK_MANAGE)
					new BookInfoFrame(this, MODIFY);
				else if (manageThing == READER_MANAGE) {
					new ReaderInfoFrame(this, MODIFY);
				} else if (manageThing == RULE_MANAGE) {
					new RuleInfoFrame(this, MODIFY);
				}
				this.cleanTable();
			}
		} else if (e.getSource() == findbt)
			find();
	}

	private void delete() {
		// TODO Auto-generated method stub
		if (manageThing == BOOK_MANAGE) {
			book.setBookId(this.getSelectedId());
			book = ClientSrvHelper.deleteBook(book);
			if (book == null)
				JOptionPane.showMessageDialog(this, "删除书籍失败！");
			else
				JOptionPane.showMessageDialog(this, "删除书籍成功！");
		} else if (manageThing == READER_MANAGE) {
			reader.setReaderId(this.getSelectedId());
			reader = ClientSrvHelper.deleteReader(reader);
			if (reader == null)
				JOptionPane.showMessageDialog(this, "删除读者证失败");
			else
				JOptionPane.showMessageDialog(this, "删除读者证成功");
		} else if (manageThing == RULE_MANAGE) {
			rule.setRuleId(this.getSelectedId());
			rule = ClientSrvHelper.deleteRule(rule);
			if (rule == null)
				JOptionPane.showMessageDialog(this, "有依赖于该规则的读者证，删除规则失败");
			else
				JOptionPane.showMessageDialog(this, "删除规则成功");
		}
		this.cleanTable();
		this.parentF.validate();
	}

	public void FitTableColumns(JTable myTable) {
		JTableHeader header = myTable.getTableHeader();
		int rowCount = myTable.getRowCount();
		Enumeration columns = myTable.getColumnModel().getColumns();
		while (columns.hasMoreElements()) {
			TableColumn column = (TableColumn) columns.nextElement();
			int col = header.getColumnModel().getColumnIndex(
					column.getIdentifier());
			int width = (int) myTable
					.getTableHeader()
					.getDefaultRenderer()
					.getTableCellRendererComponent(myTable,
							column.getIdentifier(), false, false, -1, col)
					.getPreferredSize().getWidth();
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) myTable
						.getCellRenderer(row, col)
						.getTableCellRendererComponent(myTable,
								myTable.getValueAt(row, col), false, false,
								row, col).getPreferredSize().getWidth();
				width = Math.max(width, preferedWidth);
			}
			header.setResizingColumn(column);
			column.setWidth(width + myTable.getIntercellSpacing().width);
		}
	}

	public void find() {
		String text = jtf.getText();
		if (text.equals("")) {
			JOptionPane.showMessageDialog(this, "请输入查询条件！");
			return;
		} else {
			Vector datas = new Vector();
			if (manageThing == BOOK_MANAGE) {
				Book book = new Book();
				if (idBt.isSelected()) {
					book.setBookId(Integer.parseInt(text));
					datas = ClientSrvHelper.findBook(book, FindType.BY_ID);
				} else {
					book.setTitle(text);
					datas = ClientSrvHelper.findBook(book, FindType.BY_NAME);
				}
			} else if (manageThing == READER_MANAGE) {
				Reader reader = new Reader();
				if (idBt.isSelected()) {
					reader.setReaderId(Integer.parseInt(text));
					datas = ClientSrvHelper.findReader(reader, FindType.BY_ID);
				} else {
					reader.setUserId(text);
					datas = ClientSrvHelper.findReader(reader,
							FindType.BY_USER_ID);
				}
			} else if (manageThing == RULE_MANAGE) {
				Rule rule = new Rule();
				if (idBt.isSelected()) {
					rule.setRuleId(Integer.parseInt(text));
					datas = ClientSrvHelper.findRule(rule, FindType.BY_ID);
				} else {
					rule.setRuleName(text);
					datas = ClientSrvHelper.findRule(rule, FindType.BY_NAME);
				}
			}
			if (datas == null)
				JOptionPane.showMessageDialog(this, "不存在！");
			else {
				((DefaultTableModel) table.getModel()).setDataVector(datas, colNames);
			}
			jtf.setText("");
			selectedId = 0;
		}
	}

	/**
	 * @return the selectedId
	 */
	public int getSelectedId() {
		return selectedId;
	}

	/**
	 * @return the parentF
	 */
	public JFrame getParentF() {
		return parentF;
	}

	/**
	 * 清空表
	 */
	public void cleanTable() {
		// DefaultTableModel MyDefaultTableModel=(DefaultTableModel)
		// table.getModel();
		// int num = MyDefaultTableModel.getRowCount(); //得到此数据表中的行数
		// for (int i = 0; i < num; i++) //利用循环依次清空所有行
		// MyDefaultTableModel.removeRow(0);
		// ((DefaultTableModel) table.getModel()).getDataVector().clear();
		// //清除表格数据
		// ((DefaultTableModel)
		// table.getModel()).fireTableDataChanged();//通知模型更新
		// table.updateUI();//刷新表格
		// SwingUtilities.updateComponentTreeUI(table);
		// table.repaint();
		table.removeAll();
		// table=new JTable();

	}
}