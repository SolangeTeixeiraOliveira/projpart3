
package libraryGUI;

import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;

import sqlFunctions.SQLFunctionsLibrarian;

public class DisplayMostPopularItem extends JPanel{
	
private static final long serialVersionUID = 1L;
	
	private JScrollPane scrollpane;
	private JTable table;
	private JFrame frame;

	public DisplayMostPopularItem(int year, int n) {
		this.setPreferredSize(new Dimension(600, 400));
		displayMostPopuparItem(year, n);
	}

	private void displayMostPopuparItem(int year, int n) {
		ResultSet res = SQLFunctionsLibrarian.getMostPopularItems(year, n);
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		try {
			while (res.next()) {
				Vector<String> rowData = new Vector<String>();
				rowData.add(res.getString(1));
				Integer numTimes = res.getInt(2);
				rowData.add(numTimes.toString());
				data.add(rowData);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "Error getting popular books", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return;
		}
		
		// Create the table
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Call number");
		columnNames.add("Number of Times Borrowed");
		table = new JTable(data, columnNames);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.setEnabled(false);
		scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(600, 400));
		this.add(scrollpane);
	}
	
	

}
