package libraryGUI;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;

import sqlFunctions.SQLFunctionsClerk;


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
		ResultSet rs = SQLFunctionsClerk.getOverdueItems();
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		try {
			while (rs.next()) {
				Vector<String> rowData = new Vector<String>();
				Integer copyNo = rs.getInt(2);
				rowData.add(rs.getString(1) + " C" + copyNo.toString());
				rowData.add(rs.getString(3));
				rowData.add(rs.getString(4));
				data.add(rowData);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "Error getting overdue items", "Error", JOptionPane.ERROR_MESSAGE);
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
		scrollpane.setPreferredSize(new Dimension(600, 400));
		this.add(scrollpane);
		
		System.out.println("Displayed overdue items");
	}

}
