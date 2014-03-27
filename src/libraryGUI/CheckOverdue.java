package libraryGUI;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
				System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));
				rowData.add(rs.getString(1));
				rowData.add(rs.getString(2));
				rowData.add(rs.getString(3));
				data.add(rowData);
			}
		} catch (SQLException e) {
			System.out.println("Error getting overdue items");
			e.printStackTrace();
		}
		
		// Create the table
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Call number");
		columnNames.add("Title");
		columnNames.add("Email address");
		table = new JTable(data, columnNames);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.setEnabled(false);
		scrollpane = new JScrollPane(table);
		this.add(scrollpane);
		
		System.out.println("Displayed overdue items");
	}

}
