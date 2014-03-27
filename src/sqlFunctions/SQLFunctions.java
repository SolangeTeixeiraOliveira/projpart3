package sqlFunctions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLFunctions {

	// Only access con through the getConnection function
	private static Connection con;

	private static Connection getConnection() {
		if (con == null) {
			try {
				System.out.println("Forming new connection");
				DriverManager
						.registerDriver(new oracle.jdbc.driver.OracleDriver());
				con = DriverManager.getConnection(
						"jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug",
						"ora_t3s7", "a41513102");
			} catch (SQLException e) {
				System.out
						.println("Problem registering driver or connecting to oracle");
				e.printStackTrace();
			}
		}
		return con;
	}

	// Add a borrower, automatically generating a new id for them
	public static int addBorrower(String name, String password, String address,
			int phone, String email, int sinOrStNo, String type) {

		System.out.println("Adding borrower " + name);
		try {
			PreparedStatement ps = getConnection()
					.prepareStatement(
							"INSERT INTO borrower"
									+ "(name, password, address, phone, emailAddress, sinOrStNo, expiryDate, type) "
									+ "VALUES (?,?,?,?,?,?,?,?)",
							new String[] { "bid" });

			// Set all the input values
			ps.setString(1, name);
			ps.setString(2, password);
			ps.setString(3, address);
			ps.setInt(4, phone);
			ps.setString(5, email);
			ps.setInt(6, sinOrStNo);
			// TODO: use current date plus one year
			ps.setDate(7, getCurrentDate());
			ps.setString(8, type);

			// execute the insert statement and return the new borrower id
			if (ps.executeUpdate() > 0) {
				ResultSet generatedKeys = ps.getGeneratedKeys();
				if (null != generatedKeys && generatedKeys.next()) {
					return generatedKeys.getInt(1);
				}
			} else {
				throw new SQLException(
						"Creating user failed, no generated key obtained.");
			}

		} catch (SQLException e) {
			System.out.println("Failed to add borrower");
			e.printStackTrace();
		}
		return 0;
	}

	public static int addBook(int bookNumber, int isbn, String title,
			String mainAuthor, String Publisher, int publicationYear) {

		return 0;
	}

	// Return an item
	public static String returnItem(String callNumber, int copyNumber) {
		String holderEmailAddress = null;
		
		try {
			// Check if there is a hold request for the book
			/*PreparedStatement ps = getConnection().prepareStatement(
					"SELECT emailAddress FROM holdrequest, borrower "
							+ "WHERE holdrequest.bid = borrower.bid "
							+ "AND holdrequest.issuedDate="
							+ "(SELECT MIN(issuedDate) FROM holdrequest)");
			ResultSet rs = ps.executeQuery();*/
			System.out.println("Callnumber: " + callNumber + " copyNumber: " + copyNumber);
			
			PreparedStatement ps8 = getConnection().prepareStatement(
					"SELECT status FROM bookcopy WHERE" +
					" callNumber=? AND copyNo=?");
			ps8.setString(1, callNumber);
			ps8.setInt(2, copyNumber);
			ResultSet rs4 = ps8.executeQuery();
			if (rs4.next()) {
				System.out.println("Status: " + rs4.getString(1));
			} else {
				System.out.println("Did not find book copy status");
			}
			/*
			if (rs.next()) {
				System.out.println("Setting book to on-hold");
				// Set the status of the book copy to 'on-hold'
				PreparedStatement ps2 = getConnection().prepareStatement(
						"UPDATE bookcopy SET status='on-hold' "
								+ "WHERE callNumber=? AND copyNo=?");
				ps2.setString(1, callNumber);
				ps2.setInt(2, copyNumber);
				int result = ps2.executeUpdate();
				System.out.println("Result: " + result);
				ps2.close();
				
				// Get the email address of the borrower with the hold request
				holderEmailAddress = rs.getString(1);
				
			} else {
				System.out.println("Setting book to in");
				// Set the status of the book copy to 'in'
				PreparedStatement ps3 = getConnection().prepareStatement(
						"UPDATE bookcopy SET status='in' "
								+ "WHERE callNumber=? AND copyNo=?");
				ps3.setString(1, callNumber);
				ps3.setInt(2, copyNumber);
				ps3.executeUpdate();
				ps3.close();
			}

			// If it is past the book's due date, assess a fine for the borrower
			PreparedStatement ps4 = getConnection().prepareStatement(
					"SELECT borid,inDate FROM borrowing"
							+ " WHERE callNumber=? AND copyNo=?");
			ps4.setString(1, callNumber);
			ps4.setInt(2, copyNumber);
			ResultSet rs2 = ps4.executeQuery();
			while (rs2.next()) {
				java.sql.Date duedate = rs2.getDate("inDate");
				String borid = rs2.getString("borid");
				java.sql.Date currentDate = getCurrentDate();
				System.out.println("Duedate: " + duedate.toString() + " Current date: " + currentDate.toString());
				
				if (duedate.before(currentDate)) {
					// Book is late - assess a fine
					PreparedStatement ps6 = getConnection().prepareStatement(
							"INSERT INTO fine (amount, issueddate, borid) "
									+ "VALUES (?,?,?)");
					float dayslate = (float) ((currentDate.getTime() - duedate
							.getTime()) / (1000 * 60 * 60 * 24));
					System.out.println("Charging fine to borrower for " + dayslate + " days");
					ps6.setFloat(1, dayslate / 10); // Charge 10 cents per day
					ps6.setDate(2, currentDate);
					ps6.setString(3, borid);
					ps6.executeUpdate();
				}
			}

			// Delete the borrowing record
			PreparedStatement ps5 = getConnection().prepareStatement(
					"DELETE FROM borrowing "
							+ "WHERE callNumber=? AND copyNo=?");
			ps5.setString(1, callNumber);
			ps5.setInt(2, copyNumber);
			ps5.executeUpdate();
			System.out.println("Deleted borrowing record");
			ps5.close();
*/
		} catch (SQLException e) {
			System.out.println("Failed to return item");
			e.printStackTrace();
		}
		return holderEmailAddress;
	}
	
	public static ResultSet getOverdueItems() {

		System.out.println("Checking overdue items");
		ResultSet rs = null;
		
		try {
			PreparedStatement ps = getConnection()
					.prepareStatement(
							"SELECT book.callnumber, title, emailaddress " +
							"FROM borrowing, borrower, book " +
							"WHERE borrowing.bid=borrower.bid " +
							"AND borrowing.callnumber = book.callnumber " +
							"AND borrowing.indate < CURRENT_DATE");
			rs = ps.executeQuery();

		} catch (SQLException e) {
			System.out.println("Failed to add borrower");
			e.printStackTrace();
		}
		return rs;
	}

	// Get the current date in SQL format
	private static java.sql.Date getCurrentDate() {

		java.util.Date today = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(today.getTime());
		return sqlDate;
	}


	//Generating a report of all the books that have been checked out.

	public static int  BookcheckeOut(int callNumber, String checkedOutDate,
			String dueDate, String bookTitle) {
		return 0;

	}

	
}
