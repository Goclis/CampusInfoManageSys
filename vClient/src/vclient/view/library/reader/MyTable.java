/**
 * 
 */
package vclient.view.library.reader;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 * JTable类，用于展示信息 在此类中，使JTable不可编辑，并且调用函数FitTableColumns来达到最佳显示效果
 * @author zhongfang
 */
public class MyTable extends JTable {
	/**
	 * 
	 */
	public MyTable(Vector datas, String[] list) {
		Vector a = new Vector();
		a.removeAllElements();
		for (int i = 0; i < list.length; i++) {
			a.addElement(list[i]);
		}
		DefaultTableModel tm = new DefaultTableModel(datas, a) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		this.setModel(tm);
		FitTableColumns(this);
	}

	public MyTable(DefaultTableModel model) {
		super(model);
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

}
