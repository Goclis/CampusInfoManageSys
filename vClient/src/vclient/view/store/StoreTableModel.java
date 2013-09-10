package vclient.view.store;

import javax.swing.table.DefaultTableModel;

/**
 * 商店表格的Model，用于设置某些列不能修改
 * @author goclis
 *
 */
public class StoreTableModel extends DefaultTableModel {
	public StoreTableModel(String[] columnNames, int i) {
		super(columnNames, i);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return (column == 4) ? true : false;
	}
}
