package sqlFunctions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLFunctionsBorrower {

	// Only access con through the getConnection function
	private static Connection con;

	private static Connection getConnection() {
		if (con == null) {
			try {
				System.out.println("Forming new connection");
				DriverManager
				.registerDriver(new oracle.jdbc.driver.OracleDriver());
				System.out.println("Connected");
				con = DriverManager.getConnection(
						"jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug",
						"ora_t3s7", "a41513102");
				System.out.println("Connect?");
			} catch (SQLException e) {
				System.out
				.println("Problem registering driver or connecting to oracle");
				e.printStackTrace();
			}
		}
		return con;
	}

	// Search a book in the Book Table
	public static ResultSet searchbook(String title, String author, String subject){

		title = title.toLowerCase();
		author = author.toLowerCase();
		subject = subject.toLowerCase();
		System.out.println("In search book, Searching for book with " + title + " or " 
							+ author + " or " + subject);
		ResultSet rs = null;

		try {
			// Create the prepared statement for the query
			PreparedStatement ps = getConnection().prepareStatement(
					"SELECT DISTINCT book.callnumber, LOWER(book.title) as TITLE, " + 
					"LOWER(hasauthor.name) as AUTHOR, LOWER(hassubject.subject) as subject, " + 
					"copies.in_copies, copies.out_copies "+ 
					"FROM book, hasauthor, hassubject, (select callnumber, " + 
					"count(case status when 'in' then 1 else null end) as in_copies, " +
					"count(case status when 'out' then 1 else null end) as out_copies FROM bookcopy " + 
					"GROUP BY callnumber) copies " + 
					"WHERE book.callnumber = hasauthor.callnumber and " + 
					"book.callnumber = hassubject.callnumber and book.callnumber = copies.callnumber " + 
					"and LOWER(book.title) like ? and LOWER(hasauthor.name) like ? and " + 
					"LOWER(hassubject.subject) like ?"); 

			// Set all the input values
			ps.setString(1, "%" + title + "%");
			ps.setString(2, "%" + author + "%");
			ps.setString(3, "%" + subject + "%");

			// Execute the query statement and return the books searched
			rs = ps.executeQuery();

		} catch (SQLException e) {
			System.out.println("Failed to search for book");
			e.printStackTrace();
		}
		return rs;

	}

	// Add a new Hold Request
	public static int holdRequest(int userID, String callNum) {

		System.out.println("Adding hold request for " + userID);

		try {
			PreparedStatement ps = getConnection().prepareStatement(
					"INSERT INTO holdrequest(bid, callnumber, issuedDate) "
							+ "VALUES ( ?, ?, CURRENT_DATE) ", new String[] { "hid" });

			// Set all the input values
			ps.setInt(1, userID);
			ps.setString(2, callNum);

			// Execute the insert statement and return the new hold request id
			if (ps.executeUpdate() > 0) {
				ResultSet generatedHid = ps.getGeneratedKeys();
				if (null != generatedHid && generatedHid.next()) {
					return generatedHid.getInt(1);
				}
			} else {
				throw new SQLException("Creating hold request failed.");
			}

		} catch (SQLException e) {
			System.out.println("Failed to add hold request");
			e.printStackTrace();
		}
		return 0;
	}

	// Pay the Fine. Update the Fine tuple
	public static int payFine(int bid) {

		System.out.println("Paying fine.");

		try {
			PreparedStatement ps = getConnection().prepareStatement(
					"UPDATE fine SET paidDate = CURRENT_DATE "
							+ "WHERE fine.borid = (select borid from borrowing where bid = ?)");

			// Set all the input values
			ps.setInt(1, bid);

			// Execute the update statement
			ps.executeUpdate();
			System.out.println("Fine Paid");

		} catch (SQLException e) {
			System.out.println("Failed to pay fine");
			e.printStackTrace();
		}

		return 0;
	}


	// Check the items that are currently borrowed by the borrower
	public static ResultSet checkborrowing(Integer bid) {

		System.out.println("In check borrowing items for: " + bid);
		ResultSet rs = null;

		try {
			// Create the prepared statement for the query
			PreparedStatement ps = getConnection().prepareStatement(
					"SELECT bid, borid, callnumber, copyno, TO_DATE(outdate, YY-MM-DD), " + 
					"TO_DATE(indate, YY-MM-DD) FROM borrowing WHERE bid = ?"); 

			// Set all the input values
			ps.setInt(1, bid);

			// Execute the query statement and return the books searched
			rs = ps.executeQuery();
			System.out.println("items borrowed query done!");

		} catch (SQLException e) {
			System.out.println("Failed to check the items borrowed.");
			e.printStackTrace();
		}
		return rs;
	}

	// Check the fines that the borrower has
	public static ResultSet checkfine(Integer bid) {

		System.out.println("In check fines for: " + bid);
		ResultSet rs = null;

		try {
			// Create the prepared statement for the query
			PreparedStatement ps = getConnection().prepareStatement(
					"SELECT borrowing.bid, fine.fid, fine.amount, TO_DATE(fine.issueddate, YY-MM-DD), " +
					"TO_DATE(fine.paiddate, YY-MM-DD) FROM fine, borrowing " + 
					"WHERE fine.borid = borrowing.borid and borrowing.bid = ?"); 

			// Set all the input values
			ps.setInt(1, bid);

			// Execute the query statement and return the books searched
			rs = ps.executeQuery();
			System.out.println("fine query done!");

		} catch (SQLException e) {
			System.out.println("Failed to check fines.");
			e.printStackTrace();
		}
		return rs;
	}

	// Check hold requests the borrower has
	public static ResultSet checkholdrequest(Integer bid) {

		System.out.println("In check hold requests for: " + bid);
		ResultSet rs = null;

		try {
			// Create the prepared statement for the query
			PreparedStatement ps = getConnection().prepareStatement(
					"SELECT bid, hid, callnumber, TO_DATE(issueddate, YY-MM-DD) FROM holdrequest WHERE bid = ?"); 

			// Set all the input values
			ps.setInt(1, bid);

			// Execute the query statement and return the books searched
			rs = ps.executeQuery();
			System.out.println("hold request query done!");

		} catch (SQLException e) {
			System.out.println("Failed to check hold requests.");
			e.printStackTrace();
		}
		return rs;
	}

}
