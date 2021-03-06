package libraryGUI;

import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;

import sqlFunctions.SQLFunctionsBorrower;

public class SearchResults extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JScrollPane scrollpane;
	private JTable table;
	private JTextField bkTitle = SearchLibrary.bookTitle;
	private JTextField bkAuthor = SearchLibrary.bookAuthor;
	private JTextField bkSubject = SearchLibrary.bookSubject;

	public SearchResults() {
		this.setPreferredSize(new Dimension(600, 400));
		searchLib();
	}
	
	public void searchLib() {

		ResultSet rs = SQLFunctionsBorrower.searchbook(bkTitle.getText(), bkAuthor.getText(), bkSubject.getText());
		
		Vector<Vector<String>> data1 = new Vector<Vector<String>>();
		try {
				while (rs.next()) { 
					Vector<String> rowData1 = new Vector<String>();
						rowData1.add(rs.getString(1));
						rowData1.add(rs.getString(2));
						rowData1.add(rs.getString(3));
						rowData1.add(rs.getString(4));
						data1.add(rowData1);				
					} 
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Vector<String> columnNames = new Vector<String>();
		columnNames.add("CallNumber");
		columnNames.add("Title");
		columnNames.add("Available Copies");
		columnNames.add("Unavailable Copies");
		table = new JTable(data1, columnNames);
		table.setPreferredSize(getPreferredSize());
		scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(550, 350));
		this.add(scrollpane);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.setEnabled(false);
	};
	
}
