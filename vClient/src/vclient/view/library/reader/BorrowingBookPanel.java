/**
 * 
 */
package vclient.view.library.reader;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
import javax.swing.table.DefaultTableModel;

import vclient.srv.ClientSrvHelper;
import vclient.srv.library.ClientSrvBookHelper;
import vclient.srv.library.ClientSrvBorrowHelper;

import common.util.FindType;
import common.util.Variable;
import common.vo.library.Book;
import common.vo.library.Reader;

/**
 * 显示正在借阅的书籍的面板，有续借，还书，挂失的功能
 * @author zhongfang
 */
public class BorrowingBookPanel extends JPanel implements ActionListener,
		Variable {

	JTable table;
	Vector<String> columnNames;
	private JButton returnbt, lostbt, renewbt;
	private JPanel p1, p2;
	private JScrollPane scroll;
	private ReaderLibraryFrame parentF;
	private Reader reader;
	private int selectedId, bookId;

	//private ClientSrvBorrowHelper borrowClient = new ClientSrvBorrowHelper();
	//private ClientSrvBookHelper bookClient = new ClientSrvBookHelper();

	public BorrowingBookPanel(ReaderLibraryFrame parentF, Reader reader) {
		this.parentF = parentF;
		this.reader = reader;

		this.setLayout(new BorderLayout());
		Vector datas = new Vector();
		datas = ClientSrvHelper.findBorrow(reader, FindType.NOW);
		System.out.println(datas == null);
		columnNames = new Vector<String>();
		for (String str : BORROW_NOW_LIST) {
			columnNames.add(str);
		}
		DefaultTableModel model = new DefaultTableModel(datas, columnNames);
		table = new MyTable(model);
		scroll = new JScrollPane(table);
		this.add(scroll);
		this.parentF.validate();

		returnbt = new JButton("还书");
		renewbt = new JButton("续借");
		lostbt = new JButton("挂失");
		p1 = new JPanel(new FlowLayout());
		p1.add(returnbt);
		p1.add(lostbt);
		p1.add(renewbt);
		this.add(p1, BorderLayout.SOUTH);

		returnbt.addActionListener(this);
		renewbt.addActionListener(this);
		lostbt.addActionListener(this);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (table.getValueAt(table.getSelectedRow(), 0) != null)
					selectedId = Integer.parseInt(table.getValueAt(
							table.getSelectedRow(), 0).toString());
				System.out.println("选中" + selectedId);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (selectedId == 0) {
			JOptionPane.showMessageDialog(this, "请选中书籍");
		} else {
			if (!reader.getStatus().equals("正常")) {
				JOptionPane.showMessageDialog(this,
						"你的读者证状态是" + reader.getStatus() + "不能进行此操作");
			} else {
				if (e.getSource() == returnbt) {
					Book book = new Book();
					book.setBookId(selectedId);
					book = ClientSrvHelper.returnBook(reader, book);
					if (book != null)
						JOptionPane.showMessageDialog(this, "还书成功");
					else
						JOptionPane.showMessageDialog(this, "还书失败");
				} else if (e.getSource() == renewbt) {
					int renewTimes = Integer.parseInt(table.getValueAt(
							table.getSelectedRow(), 5).toString());
					if (renewTimes >= reader.getRule().getMaxRenewTimes()) {
						JOptionPane.showMessageDialog(this, "超过最大续借次数，不得续借");
					} else {
						Book book = new Book();
						book.setBookId(selectedId);
						book = ClientSrvHelper.renewBook(reader, book);
						if (book == null)
							JOptionPane.showMessageDialog(this, "超过续借时间,续借失败");
						else
							JOptionPane.showMessageDialog(this, "续借成功");
					}
					// TODO 刷新，重新获取数据
					// this.parentF.validate();
				} else if (e.getSource() == lostbt) {
					Book book = new Book();
					book.setBookId(selectedId);
					Vector books = new Vector();
					books = ClientSrvHelper.findBook(book, FindType.BY_ID);
					Vector abook = (Vector) books.get(0);

					book = ClientSrvHelper.lossBook(reader, book);
					if (book == null)
						JOptionPane.showMessageDialog(this, "挂失书籍失败");
					else
						JOptionPane.showMessageDialog(this, "挂失书籍成功");
				}
			}
		}
	}
}
