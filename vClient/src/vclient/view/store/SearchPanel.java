package vclient.view.store;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;

import vclient.srv.ClientSrvHelper;

import common.vo.store.Good;

public class SearchPanel extends JPanel implements ActionListener {
	private JRadioButton jrbSearchByKey, jrbSeachByType;
	private JButton jbtSearchByKey;
	private JPanel jpTop, jpSearchByKey, jpAllTypes, jpSearchByType,
			jpCenterTop, jpBottom, jpCenter;
	private JButton jbtInsertToList;
	private JTextField jtfKey;
	private String[] columnNames = { "商品编号", "名称", "价格", "类型", "剩余数量" };
	private StoreTableModel tableModel = new StoreTableModel(columnNames, 0);
	private JTable jtSearchResult = new JTable(tableModel); // 搜索结果列表
	private String[] GOOD_TYPE_LIST = { "食物", "饮品", "水果", "学习用品", "生活用品", "其他" };
	private ShoppingCardPanel shoppingList;
	private JButton jbtAddGood;

	/**
	 * 建立查询商品的面板（管理员面板）
	 */
	public SearchPanel() {
		// 顶部的搜索选择
		jpTop = new JPanel(new FlowLayout());
		jrbSearchByKey = new JRadioButton("按名称搜索");
		jrbSeachByType = new JRadioButton("按类别搜索");
		ButtonGroup bgForSearch = new ButtonGroup();
		bgForSearch.add(jrbSearchByKey);
		bgForSearch.add(jrbSeachByType);
		jpTop.add(jrbSearchByKey);
		jpTop.add(jrbSeachByType);
		jrbSearchByKey.setSelected(true);
		
		// TODO: 在底部增加添加商品以及删除商品的按钮
		jpBottom = new JPanel(new FlowLayout());
		jbtAddGood = new JButton("添加商品");
		jpBottom.add(jbtAddGood);
		
		// 中间面板，显示搜索内容输入以及搜索结果
		jpCenter = new JPanel(new BorderLayout());

		jpCenterTop = new JPanel(new CardLayout()); // 用于显示搜索输入框以及类型选择的面板

		// 按关键字搜索的面板Card
		jpSearchByKey = new JPanel(new FlowLayout());
		jtfKey = new JTextField(50);
		jbtSearchByKey = new JButton("搜索");
		jpSearchByKey.add(jtfKey);
		jpSearchByKey.add(jbtSearchByKey);

		// 按类型搜索的面板Card
		jpSearchByType = new JPanel(new FlowLayout());
		jpAllTypes = new JPanel(new GridLayout(2, 3)); // 装各个类型的面板
		ButtonGroup bgForSearchType = new ButtonGroup();
		SearchByTypeListener typeListener = new SearchByTypeListener();
		for (int i = 0; i < GOOD_TYPE_LIST.length; i++) {
			JRadioButton jrbType = new JRadioButton(GOOD_TYPE_LIST[i]);
			jpAllTypes.add(jrbType);
			bgForSearchType.add(jrbType);
			jrbType.addActionListener(typeListener);
		}
		jpSearchByType.add(jpAllTypes);

		jpCenterTop.add(jpSearchByKey, "SearchByKey");
		jpCenterTop.add(jpSearchByType, "SearchByType");
		jpCenter.add(jpCenterTop, BorderLayout.NORTH);
		jpCenter.add(new JScrollPane(jtSearchResult), BorderLayout.CENTER);
		jtSearchResult
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		this.setLayout(new BorderLayout());
		this.add(jpTop, BorderLayout.NORTH);
		this.add(jpCenter, BorderLayout.CENTER);
		this.add(jpBottom, BorderLayout.SOUTH);

		jrbSearchByKey.addActionListener(this);
		jrbSeachByType.addActionListener(this);
		jbtSearchByKey.addActionListener(this);
		jbtAddGood.addActionListener(this);
	}
	
	/**
	 * 普通用户面板
	 * @param shopping
	 */
	public SearchPanel(ShoppingCardPanel shopping) {
		this.shoppingList = shopping; // 保存购物车面板
		
		// 顶部的搜索选择
		jpTop = new JPanel(new FlowLayout());
		jrbSearchByKey = new JRadioButton("按名称搜索");
		jrbSeachByType = new JRadioButton("按类别搜索");
		ButtonGroup bgForSearch = new ButtonGroup();
		bgForSearch.add(jrbSearchByKey);
		bgForSearch.add(jrbSeachByType);
		jpTop.add(jrbSearchByKey);
		jpTop.add(jrbSeachByType);
		jrbSearchByKey.setSelected(true);

		// 底部添加商品到购物车
		jpBottom = new JPanel();
		jbtInsertToList = new JButton("放入购物车");
		jpBottom.add(jbtInsertToList);

		// 中间面板，显示搜索内容输入以及搜索结果
		jpCenter = new JPanel(new BorderLayout());

		jpCenterTop = new JPanel(new CardLayout()); // 用于显示搜索输入框以及类型选择的面板

		// 按关键字搜索的面板Card
		jpSearchByKey = new JPanel(new FlowLayout());
		jtfKey = new JTextField(50);
		jbtSearchByKey = new JButton("搜索");
		jpSearchByKey.add(jtfKey);
		jpSearchByKey.add(jbtSearchByKey);

		// 按类型搜索的面板Card
		jpSearchByType = new JPanel(new FlowLayout());
		jpAllTypes = new JPanel(new GridLayout(2, 3)); // 装各个类型的面板
		ButtonGroup bgForSearchType = new ButtonGroup();
		SearchByTypeListener typeListener = new SearchByTypeListener();
		for (int i = 0; i < GOOD_TYPE_LIST.length; i++) {
			JRadioButton jrbType = new JRadioButton(GOOD_TYPE_LIST[i]);
			jpAllTypes.add(jrbType);
			bgForSearchType.add(jrbType);
			jrbType.addActionListener(typeListener);
		}
		jpSearchByType.add(jpAllTypes);

		jpCenterTop.add(jpSearchByKey, "SearchByKey");
		jpCenterTop.add(jpSearchByType, "SearchByType");
		jpCenter.add(jpCenterTop, BorderLayout.NORTH);
		jpCenter.add(new JScrollPane(jtSearchResult), BorderLayout.CENTER);
		jtSearchResult.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		this.setLayout(new BorderLayout());
		this.add(jpTop, BorderLayout.NORTH);
		this.add(jpBottom, BorderLayout.SOUTH);
		this.add(jpCenter, BorderLayout.CENTER);

		jrbSearchByKey.addActionListener(this);
		jrbSeachByType.addActionListener(this);
		jbtSearchByKey.addActionListener(this);
		jbtInsertToList.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jrbSearchByKey) { // 切换为按关键字搜索的面板
			((CardLayout) jpCenterTop.getLayout()).show(jpCenterTop,
					"SearchByKey");
		} else if (e.getSource() == jrbSeachByType) { // 切换为按类型搜索的面板
			((CardLayout) jpCenterTop.getLayout()).show(jpCenterTop,
					"SearchByType");
		} else if (e.getSource() == jbtSearchByKey) {
			// 清空表格
			while (tableModel.getRowCount() > 0) {
				tableModel.removeRow(0);
			}
			String key = jtfKey.getText().trim();
			ArrayList<Good> goods = ClientSrvHelper.queryByKey(key);
			for (int i = 0; i < goods.size(); i++) {
				String gId = goods.get(i).getId().toString();
				String gName = goods.get(i).getName();
				String gPrice = goods.get(i).getPrice().toString();
				String gType = goods.get(i).getType();
				String gNumber = goods.get(i).getNumber().toString();
				tableModel.addRow(new Object[] { gId, gName, gPrice, gType, gNumber});
			}
		} else if (e.getSource() == jbtInsertToList) { // 将所选商品加入购物车
			int[] rows = jtSearchResult.getSelectedRows();
			ArrayList<Good> goods = new ArrayList<Good>();
			for (int i = 0; i < rows.length; i++) { // 如果有货
				if (Integer.valueOf((String) tableModel.getValueAt(i, 4)) > 0) {
					Good good = new Good();
					good.setId(Integer.valueOf((String) tableModel.getValueAt(i, 0)));
					good.setName((String) tableModel.getValueAt(i, 1));
					good.setPrice(Double.valueOf((String) tableModel.getValueAt(i, 2)));
					good.setType((String) tableModel.getValueAt(i, 3));
					good.setNumber(1);
					goods.add(good);
				}
			}
			shoppingList.addList(goods);
			JOptionPane.showMessageDialog(this, "添加成功");
		} else if (e.getSource() == jbtAddGood) { // 添加商品
			new AddNewGood().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	}
	
	/**
	 * 响应按类别查询的JRadioButton
	 * @author goclis
	 *
	 */
	private class SearchByTypeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String type = ((JRadioButton)e.getSource()).getText();
			ArrayList<Good> goods = ClientSrvHelper.queryByType(type);
			// 清空表格
			while (tableModel.getRowCount() > 0) {
				tableModel.removeRow(0);
			}
			for (int i = 0; i < goods.size(); i++) {
				String gId = goods.get(i).getId().toString();
				String gName = goods.get(i).getName();
				String gPrice = goods.get(i).getPrice().toString();
				String gType = goods.get(i).getType();
				String gNumber = goods.get(i).getNumber().toString();
				tableModel.addRow(new Object[] { gId, gName, gPrice, gType, gNumber});
			}
		}
	}
}
