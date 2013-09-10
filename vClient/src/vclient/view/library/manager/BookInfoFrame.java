/**
 * 
 */
package vclient.view.library.manager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import vclient.srv.ClientSrvHelper;
import vclient.srv.library.ClientSrvBookHelper;

import common.util.FindType;
import common.util.ObjectTransformer;
import common.util.Variable;
import common.vo.library.Book;

/**
 * 书籍信息的框架，用于书籍的增加和修改
 * 
 * @author zhongfang
 * 
 */
public class BookInfoFrame extends JFrame implements Variable, ActionListener {

	private JLabel bookIdL, titleL, authorL, typeL, publisherL, publishTimeL,
			descripL, priceL, callCodeL, enterTimeL, statusL, storePlaceL;
	private JTextField bookIdF, titleF, authorF, publisherF, publishTimeF,
			descripF, priceF, callCodeF, enterTimeF;
	JComboBox statusF;
	private JComboBox typeF, storePlaceF;
	private JButton ok, cancel;
	private ManagePanel parentPanel;
	private int type;
	private int bookId;
	private String title, author, typ, callCode, publisher, publishTime,
			enterTime, status, descrip;
	private double price;
	private String storePlace;
	private Book book = new Book();
	//private ClientSrvBookHelper clientSrv = new ClientSrvBookHelper();

	/**
	 * 
	 */
	public BookInfoFrame(ManagePanel parentPanel, int type) {
		// TODO Auto-generated constructor stub
		this.parentPanel = parentPanel;
		this.type = type;

		this.setLayout(new BorderLayout());

		bookIdL = new JLabel("编号");
		titleL = new JLabel("书名");
		authorL = new JLabel("作者");
		typeL = new JLabel("类型");
		callCodeL = new JLabel("索书号");
		publisherL = new JLabel("出版社");
		publishTimeL = new JLabel("出版时间");
		enterTimeL = new JLabel("入馆时间");
		descripL = new JLabel("附注");
		priceL = new JLabel("价格");
		statusL = new JLabel("状态");
		storePlaceL = new JLabel("馆藏地");
		bookIdF = new JTextField(20);
		titleF = new JTextField(20);
		authorF = new JTextField(40);
		typeF = new JComboBox(BOOK_TYPE_LIST);
		publisherF = new JTextField(20);
		publishTimeF = new JTextField(20);
		enterTimeF = new JTextField(20);
		descripF = new JTextField(100);
		statusF = new JComboBox(BOOK_STATUS);
		priceF = new JTextField(10);
		callCodeF = new JTextField(20);
		storePlaceF = new JComboBox(STORE_PLACE_LIST);
		JPanel jpanel1 = new JPanel(new GridLayout(12, 1));

		determinComponent();

		jpanel1.add(bookIdL);
		jpanel1.add(bookIdF);
		jpanel1.add(titleL);
		jpanel1.add(titleF);
		jpanel1.add(authorL);
		jpanel1.add(authorF);
		jpanel1.add(typeL);
		jpanel1.add(typeF);
		jpanel1.add(callCodeL);
		jpanel1.add(callCodeF);
		jpanel1.add(storePlaceL);
		jpanel1.add(storePlaceF);
		jpanel1.add(publisherL);
		jpanel1.add(publisherF);
		jpanel1.add(publishTimeL);
		jpanel1.add(publishTimeF);
		jpanel1.add(enterTimeL);
		jpanel1.add(enterTimeF);
		jpanel1.add(priceL);
		jpanel1.add(priceF);
		jpanel1.add(statusL);
		jpanel1.add(statusF);
		jpanel1.add(descripL);
		jpanel1.add(descripF);

		JPanel jpanel2 = new JPanel();
		ok = new JButton("okay");
		cancel = new JButton("cancel");
		jpanel2.add(ok);
		jpanel2.add(cancel);

		this.add(jpanel2, BorderLayout.SOUTH);
		this.add(jpanel1);

		ok.addActionListener(this);
		cancel.addActionListener(this);

		this.setSize(550, 350);
		Dimension screen = getToolkit().getScreenSize();
		this.setLocation((screen.width - this.getSize().width) / 2,
				(screen.height - this.getSize().height) / 2);
		this.setVisible(true);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == ok) {
			if (type == ADD) {
				addBook();
			} else if (type == MODIFY) {
				getInfoBook();
				book = ClientSrvHelper.modifyBook(book);

				if (book == null)
					JOptionPane.showMessageDialog(this, "修改书籍失败");
				else
					JOptionPane.showMessageDialog(this, "修改书籍成功");

				shutDown();
				this.parentPanel.cleanTable();
			}
		} else if (e.getSource() == cancel)
			shutDown();
	}

	public void shutDown() {
		this.dispose();
		this.parentPanel.getParentF().setVisible(true);
	}

	/**
	 * 从界面获取信息并封装进book
	 * 
	 * @return
	 */
	private Book getInfoBook() {
		bookId = Integer.parseInt(bookIdF.getText().trim());
		title = titleF.getText().trim();
		author = authorF.getText().trim();
		typ = BOOK_TYPE_LIST[typeF.getSelectedIndex()].trim();
		storePlace = STORE_PLACE_LIST[storePlaceF.getSelectedIndex()].trim();
		callCode = callCodeF.getText().trim();
		publisher = publisherF.getText().trim();
		publishTime = publishTimeF.getText().trim();
		price = Double.parseDouble(priceF.getText().trim());
		descrip = descripF.getText().trim();
		status = BOOK_STATUS[statusF.getSelectedIndex()].trim();
		book = new Book(bookId, title, author, typ, callCode, storePlace,
				publisher, publishTime, price, status);
		book.setDescription(descrip);
		return book;
	}

	private void addBook() {
		if (bookIdF.getText().equals("") || titleF.getText().equals("")
				|| authorF.getText().equals("") || typeF.equals("")
				|| callCodeF.getText().equals("")
				|| publisherF.getText().equals("")
				|| publishTimeF.getText().equals("")
				|| priceF.getText().equals(""))
			JOptionPane.showMessageDialog(this,
					"编号/书名/作者/索书号/出版社/出版时间/价格 不能为空！");
		else {
			Book book = getInfoBook();
			book = ClientSrvHelper.addBook(book);

			if (book == null) {
				JOptionPane.showMessageDialog(this, "添加失败，该书籍已经存在");
			} else {
				JOptionPane.showMessageDialog(this, "添加书籍成功");
			}
			shutDown();
			this.parentPanel.cleanTable();
		}
	}

	/**
	 * 通过book对象的信息设置面板信息
	 * 
	 * @param book
	 */
	private void setInfoBook(Book book) {
		bookIdF.setText(Integer.toString(book.getBookId()));
		titleF.setText(book.getTitle());
		authorF.setText(book.getAuthor());
		typeF.setSelectedItem(book.getType());
		storePlaceF.setSelectedItem(book.getStorePlace());
		statusF.setSelectedItem(book.getStatus());
		callCodeF.setText(book.getCallCode());
		publisherF.setText(book.getPublisher());
		publishTimeF.setText(book.getPublishTime());
		enterTimeF.setText(book.getEnterTime());
		priceF.setText(Double.toString(book.getPrice()));
		descripF.setText(book.getDescription());
	}

	/**
	 * 由操作类型（add,modify）决定面板显示
	 */
	public void determinComponent() {
		if (type == MODIFY) {
			if (this.parentPanel.getSelectedId() == 0) {
				JOptionPane.showMessageDialog(this, "没有选中对象");
				this.shutDown();
				System.out.println("关闭子窗口");
			} else {
				bookIdF.setEditable(false);
				enterTimeF.setEditable(false);
				Vector infos = new Vector();
				Vector info = new Vector();
				book.setBookId(this.parentPanel.getSelectedId());

				// 获取book信息
				infos = ClientSrvHelper.findBook(book, FindType.BY_ID);
				info = (Vector) infos.get(0);
				book.setTitle((String) info.get(1));
				book.setAuthor((String) info.get(2));
				book.setType((String) info.get(3));
				book.setCallCode((String) info.get(4));
				book.setStorePlace((String) info.get(5));
				book.setPublisher((String) info.get(6));
				book.setPublishTime((String) info.get(7));
				book.setEnterTime((String) info.get(8));
				book.setPrice(ObjectTransformer.getDouble(info.get(9)));
				book.setStatus((String) info.get(10));
				book.setDescription((String) info.get(11));

				setInfoBook(book);
			}
		} else {
			enterTimeF.setText("今日");
			enterTimeF.setEditable(false);
		}
	}

}
