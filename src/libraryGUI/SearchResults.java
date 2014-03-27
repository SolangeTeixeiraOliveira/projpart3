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
		this.setPreferredSize(new Dimension(750, 400));
		searchLib();
	}
	
	public void searchLib() {

		System.out.println("Going into SQL Query");
		System.out.println("in searchLib, Searching for: " + bkTitle.getText() + " and " + bkAuthor.getText() + " and " + bkSubject.getText());
		ResultSet rs = SQLFunctionsBorrower.searchbook(bkTitle.getText(), bkAuthor.getText(), bkSubject.getText());
		
		Vector<Vector<String>> data1 = new Vector<Vector<String>>();
		try {
			while (rs.next()) { //TODO: make a list of authors instead of having 2 rows of authors
					Vector<String> rowData1 = new Vector<String>();
					System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));
					rowData1.add(rs.getString(1));
					rowData1.add(rs.getString(2));
					rowData1.add(rs.getString(3));
					rowData1.add(rs.getString(4));
					rowData1.add(rs.getString(5));
					rowData1.add(rs.getString(6));
					data1.add(rowData1);				
			} 
		} catch (SQLException e) {
			System.out.println("Error searching Library");
			e.printStackTrace();
		}

		Vector<String> columnNames = new Vector<String>();
		columnNames.add("CallNumber");
		columnNames.add("Title");
		columnNames.add("Author");
		columnNames.add("Subject");
		columnNames.add("Available Copies");
		columnNames.add("Unavailable Copies");
		table = new JTable(data1, columnNames);
		table.setPreferredSize(getPreferredSize());
		scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(700, 350));
		this.add(scrollpane);
		table.getColumnModel().getColumn(0).setPreferredWidth(300);
		table.getColumnModel().getColumn(1).setPreferredWidth(300);
		table.getColumnModel().getColumn(2).setPreferredWidth(300);
		table.getColumnModel().getColumn(3).setPreferredWidth(300);
		table.getColumnModel().getColumn(4).setPreferredWidth(300);
		table.getColumnModel().getColumn(5).setPreferredWidth(350);
	};
	
}
