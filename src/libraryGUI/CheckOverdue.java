package libraryGUI;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;

import sqlFunctions.SQLFunctions;


public class CheckOverdue extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JScrollPane scrollpane;
	private JTable table;
	private JFrame frame;

	public CheckOverdue() {
		this.setPreferredSize(new Dimension(600, 400));
		displayOverdueItems();
	}

	private void displayOverdueItems() {
		ResultSet rs = SQLFunctions.getOverdueItems();
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		try {
			while (rs.next()) {
				Vector<String> rowData = new Vector<String>();
				rowData.add(rs.getString(1));
				rowData.add(rs.getString(2));
				rowData.add(rs.getString(3));
				data.add(rowData);
			}
		} catch (SQLException e) {
			System.out.println("Error getting overdue items");
			e.printStackTrace();
		}
		
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Call number");
		columnNames.add("Title");
		columnNames.add("Email address");
		table = new JTable(data, columnNames);
		scrollpane = new JScrollPane(table);
		this.add(scrollpane);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
	}

}
