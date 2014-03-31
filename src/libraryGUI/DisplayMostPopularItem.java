
package libraryGUI;

import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.*;

import sqlFunctions.SQLFunctionsLibrarian;

public class DisplayMostPopularItem extends JPanel{
	
private static final long serialVersionUID = 1L;
	
	private JScrollPane scrollpane;
	private JTable table;
	private JFrame frame;

	public DisplayMostPopularItem() {
		this.setPreferredSize(new Dimension(600, 400));
		displayMostPopuparItem();
	}

	private void displayMostPopuparItem() {
		ResultSet res = SQLFunctionsLibrarian.getMostPopularItems();
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		try {
			while (res.next()) {
				System.out.println("Found a book");
				Vector<String> rowData = new Vector<String>();
				rowData.add(res.getString(1) + " C" + res.getInt(2)); 
				rowData.add(res.getString(3)); // Title
				
				// Get out date of item as a string
				SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
				java.sql.Date sqlDateOut = res.getDate(4);
				java.util.Date utilDateOut = new java.util.Date();
				utilDateOut.setTime(sqlDateOut.getTime());
				String outdate = fm.format(utilDateOut);
				
				// Get due date of item as a string
				java.sql.Date sqlDateDue = res.getDate(5);
				java.util.Date utilDateDue = new java.util.Date();
				utilDateDue.setTime(sqlDateDue.getTime());
				String duedate = fm.format(utilDateDue);
				
				rowData.add(outdate);
				rowData.add(duedate);
				data.add(rowData);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "Error getting  all Checked Out Book", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		// Create the table
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Call number");
		columnNames.add("Number of Times");
		//columnNames.add("Out date");
		table = new JTable(data, columnNames);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		//table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.setEnabled(false);
		scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(600, 400));
		this.add(scrollpane);
		
		System.out.println("Displayed All Checked Out Book");
	}
	
	

}
