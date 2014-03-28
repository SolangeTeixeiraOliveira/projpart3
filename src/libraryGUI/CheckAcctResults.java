package libraryGUI;

import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;

import sqlFunctions.SQLFunctionsBorrower;

public class CheckAcctResults extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField bID = CheckAccount.borID;
	private JFrame frame;
	private JTable table1;
	private JScrollPane scrollpane1;
	private JTable table2;
	private JScrollPane scrollpane2;
	private JTable table3;
	private JScrollPane scrollpane3;

	public CheckAcctResults() {
		this.setPreferredSize(new Dimension(750, 600));
		checkAccount();
	}

	public void checkAccount() {

		int bid;

		try {
			bid = Integer.parseInt(bID.getText());
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(frame, "Borrower ID should be an Integer." );
			return;
		}

		System.out.println("Going into SQL Query");

		// Check the items borrowed by the borrower id		
		ResultSet rs1 = SQLFunctionsBorrower.checkborrowing(bid);
		Vector<Vector<String>> data1 = new Vector<Vector<String>>();
		try {
			while (rs1.next()) { 

				//TODO: remove the times in the dates
				Vector<String> rowData1 = new Vector<String>();
				System.out.println(rs1.getString(1));
				rowData1.add(rs1.getString(1));
				rowData1.add(rs1.getString(2));
				rowData1.add(rs1.getString(3));
				rowData1.add(rs1.getString(4));
				rowData1.add(rs1.getString(5));
				rowData1.add(rs1.getString(6));
				data1.add(rowData1);				
			} 
		} catch (SQLException e) {
			System.out.println("Error checking Account");
			e.printStackTrace();
		}

		Vector<String> column1 = new Vector<String>();
		column1.add("Borrower ID");
		column1.add("Borrowing ID");
		column1.add("Call Number");
		column1.add("Copy Number");
		column1.add("Out Date");
		column1.add("In Date");
		table1 = new JTable(data1, column1);
		table1.setPreferredSize(getPreferredSize());
		scrollpane1 = new JScrollPane(table1);
		scrollpane1.setPreferredSize(new Dimension(700, 190));
		this.add(scrollpane1);
		table1.getColumnModel().getColumn(0).setPreferredWidth(300);
		table1.getColumnModel().getColumn(1).setPreferredWidth(300);
		table1.getColumnModel().getColumn(2).setPreferredWidth(300);
		table1.getColumnModel().getColumn(3).setPreferredWidth(300);
		table1.getColumnModel().getColumn(4).setPreferredWidth(300);
		table1.getColumnModel().getColumn(5).setPreferredWidth(350);

		// Check the fines by the borrower id
		ResultSet rs2 = SQLFunctionsBorrower.checkfine(bid);
		Vector<Vector<String>> data2 = new Vector<Vector<String>>();
		try {
			while (rs2.next()) { 
				
				//TODO: remove the times in the dates
				Vector<String> rowData2 = new Vector<String>();
				System.out.println(rs2.getString(1));
				rowData2.add(rs2.getString(1));
				rowData2.add(rs2.getString(2));
				rowData2.add(rs2.getString(3));
				rowData2.add(rs2.getString(4));
				rowData2.add(rs2.getString(5));
				data2.add(rowData2);				
			} 
		} catch (SQLException e) {
			System.out.println("Error checking Account");
			e.printStackTrace();
		}

		Vector<String> column2 = new Vector<String>();
		column2.add("Borrower ID");
		column2.add("Fine ID");
		column2.add("Fine Amount");
		column2.add("Issued Date");
		column2.add("Paid Date");
		table2 = new JTable(data2, column2);
		table2.setPreferredSize(getPreferredSize());
		scrollpane2 = new JScrollPane(table2);
		scrollpane2.setPreferredSize(new Dimension(700, 190));
		this.add(scrollpane2);
		table2.getColumnModel().getColumn(0).setPreferredWidth(300);
		table2.getColumnModel().getColumn(1).setPreferredWidth(300);
		table2.getColumnModel().getColumn(2).setPreferredWidth(300);
		table2.getColumnModel().getColumn(3).setPreferredWidth(300);
		table2.getColumnModel().getColumn(4).setPreferredWidth(300);

		// Check the hold requests by the borrower id
		ResultSet rs3 = SQLFunctionsBorrower.checkholdrequest(bid);
		Vector<Vector<String>> data3 = new Vector<Vector<String>>();
		try {
			while (rs3.next()) { 
				
				//TODO: remove the times in the dates
				Vector<String> rowData3 = new Vector<String>();
				System.out.println(rs3.getString(1));
				rowData3.add(rs3.getString(1));
				rowData3.add(rs3.getString(2));
				rowData3.add(rs3.getString(3));
				rowData3.add(rs3.getString(4));
				data3.add(rowData3);				
			} 
		} catch (SQLException e) {
			System.out.println("Error checking Account");
			e.printStackTrace();
		}

		Vector<String> column3 = new Vector<String>();
		column3.add("Borrower ID");
		column3.add("Hold Request ID");
		column3.add("Call Number");
		column3.add("Issued Date");
		table3 = new JTable(data3, column3);
		table3.setPreferredSize(getPreferredSize());
		scrollpane3 = new JScrollPane(table3);
		scrollpane3.setPreferredSize(new Dimension(700, 190));
		this.add(scrollpane3);
		table3.getColumnModel().getColumn(0).setPreferredWidth(300);
		table3.getColumnModel().getColumn(1).setPreferredWidth(300);
		table3.getColumnModel().getColumn(2).setPreferredWidth(300);
		table3.getColumnModel().getColumn(3).setPreferredWidth(300);


	};

}
