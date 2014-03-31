package sqlFunctions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

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
						
				"jdbc:oracle:thin:@localhost:1522:ug", "ora_x4q7",
						"a45775103");

				con.setAutoCommit(false);
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
									+ "VALUES (?,?,?,?,?,?,CURRENT_DATE+365,?)",
							new String[] { "bid" });

			// Set all the input values
			ps.setString(1, name);
			ps.setString(2, password);
			ps.setString(3, address);
			ps.setInt(4, phone);
			ps.setString(5, email);
			ps.setInt(6, sinOrStNo);
			ps.setString(7, type);

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
			getConnection().commit();

		} catch (SQLException e) {
			System.out.println("Failed to add borrower");
			e.printStackTrace();
		}
		return 0;
	}

	// Librarian): Adds a new book or new copy of an existing book to the
	// library
	public static int addBook(String callNumber, int isbn, String title,
			String mainAuthor, String publisher, Integer publicationYear,
			Vector<String> authorList, Vector<String> subjectList) {

		System.out.println("Adding Book " + callNumber);
		try {
			// See how many copies of the book exist already (if any)
			PreparedStatement ps1 = getConnection()
					.prepareStatement("SELECT MAX(copyno) FROM bookcopy " +
							"WHERE callNumber=?");
			ps1.setString(1, callNumber);
			ResultSet rs = ps1.executeQuery();
			
			int numcopies = 0;
			if (rs.next()) {
				numcopies = rs.getInt(1);
			}
			
			// Insert a new book entry if needed
			if (numcopies == 0) {
				PreparedStatement ps = getConnection()
						.prepareStatement(
								"INSERT INTO book "
										+ "(callNumber, isbn, title, mainAuthor, Publisher, year) "
										+ "VALUES (?,?,?,?,?,?)");

				// Set all the input values
				ps.setString(1, callNumber);
				ps.setInt(2, isbn);
				ps.setString(3, title);
				ps.setString(4, mainAuthor);
				ps.setString(5, publisher);
				if (publicationYear == null) {
					ps.setNull(6, java.sql.Types.INTEGER);
				} else {
					ps.setInt(6, publicationYear);
				}
				ps.executeUpdate();
			}
			
			// Add a new book copy
			PreparedStatement ps2 = getConnection().prepareStatement(
					"INSERT INTO bookcopy (callNumber, copyNo, status) "
							+ "VALUES (?,?,'in')");
			ps2.setString(1, callNumber);
			ps2.setInt(2, numcopies+1);
			ps2.executeUpdate();
			
			// Add each subject
			PreparedStatement ps3 = getConnection().prepareStatement(
					"INSERT INTO hassubject (callNumber, subject) " +
					"VALUES (?,?)");
			ps3.setString(1, callNumber);
			for (String subject : subjectList) {
				ps3.setString(2, subject);
				ps3.executeUpdate();
			}
			
			// Add each author
			PreparedStatement ps4 = getConnection().prepareStatement(
					"INSERT INTO hasauthor (callNumber, name) " +
					"VALUES (?,?)");
			ps4.setString(1, callNumber);
			for (String author : authorList) {
				ps4.setString(2, author);
				ps4.executeUpdate();
			}
			
			getConnection().commit();
			return numcopies+1;

		} catch (SQLException e) {
			System.out.println("Failed to add book");
			e.printStackTrace();
		}
		return 0;
	}

	// Return an item
	public static String returnItem(String callNumber, int copyNumber) {
		String holderEmailAddress = null;

		try {
			// Check if there is a hold request for the book
			PreparedStatement ps = getConnection().prepareStatement(
					"SELECT emailAddress FROM holdrequest, borrower "
							+ "WHERE holdrequest.bid = borrower.bid "
							+ "AND callnumber=? ORDER BY issueddate");
			ps.setString(1, callNumber);
			ResultSet rs = ps.executeQuery();

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
			PreparedStatement ps4 = getConnection()
					.prepareStatement(
							"SELECT borid, 0.1*(CURRENT_DATE-(borrowertype.booktimelimit+borrowing.outdate)) "
									+ "FROM borrowing, borrower, borrowertype "
									+ "WHERE borrowing.callnumber=? "
									+ "AND borrowing.copyno=? "
									+ "AND borrowing.bid=borrower.bid "
									+ "AND borrower.type=borrowertype.type");
			ps4.setString(1, callNumber);
			ps4.setInt(2, copyNumber);
			ResultSet rs2 = ps4.executeQuery();
			int borid;
			float fine;
			if (rs2.next()) {
				borid = rs2.getInt(1);
				fine = rs2.getFloat(2);
			} else {
				System.out.println("Did not find borrowing record");
				throw new SQLException();
			}

			if (fine > 0) {
				PreparedStatement ps6 = getConnection().prepareStatement(
						"INSERT INTO fine (amount, issueddate, borid) "
								+ "VALUES (?,CURRENT_DATE,?)");
				ps6.setFloat(1, fine);
				ps6.setInt(2, borid);
				ps6.executeUpdate();
			}

			// Update the indate of the borrowing record
			PreparedStatement ps5 = getConnection().prepareStatement(
					"UPDATE borrowing SET indate=CURRENT_DATE "
							+ "WHERE callNumber=? AND copyNo=?");
			ps5.setString(1, callNumber);
			ps5.setInt(2, copyNumber);
			ps5.executeUpdate();
			System.out.println("Updated indate");
			ps5.close();

			getConnection().commit();
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
							"SELECT book.callnumber, title, emailaddress "
									+ "FROM borrowing, borrower, borrowertype, book "
									+ "WHERE borrowing.bid=borrower.bid "
									+ "AND borrowing.callnumber = book.callnumber "
									+ "AND borrower.type=borrowertype.type "
									+ "AND borrowing.outdate+(7*(borrowertype.booktimelimit)) < CURRENT_DATE "
									+ "AND borrowing.indate IS NULL");
			rs = ps.executeQuery();

		} catch (SQLException e) {
			System.out.println("Failed to check overdue items");
			e.printStackTrace();
		}
		return rs;
	}

	public static boolean isValidAccount(int bid) {
		try {
			PreparedStatement ps = getConnection().prepareStatement(
					"SELECT * FROM borrower WHERE bid=?");
			ps.setInt(1, bid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			System.out.println("Checking borrower account failed");
		}
		return false;
	}

	// Check out an item
	public static boolean checkOutItem(String callNum, int copyNum, int bid) {
		System.out.println("Checking out item " + callNum + " C" + copyNum);
		try {
			// Check hold requests for the item
			PreparedStatement ps3 = getConnection().prepareStatement(
					"SELECT bid FROM holdrequest " + "WHERE callnumber=? "
							+ "ORDER BY issueddate");
			ps3.setString(1, callNum);
			ResultSet rs = ps3.executeQuery();
			if (rs.next()) {
				// If this borrower doesn't have the earliest hold request on
				// the
				// item, they can't check it out
				if (rs.getInt(1) != bid) {
					System.out
							.println("Cannot checkout book - someone else has a hold request");
					return false;
				}
			}

			// Check to make sure the item is not already out
			PreparedStatement ps8 = getConnection().prepareStatement(
					"SELECT status FROM bookcopy WHERE"
							+ " callNumber=? AND copyNo=?");
			ps8.setString(1, callNum);
			ps8.setInt(2, copyNum);
			ResultSet rs4 = ps8.executeQuery();
			if (rs4.next()) {
				if (rs.getString(1) == "out") {
					System.out
							.println("Cannot check out a book that's already out");
					return false;
				}
			}

			// Set the bookcopy status to 'out'
			PreparedStatement ps1 = getConnection().prepareStatement(
					"UPDATE bookcopy SET status='out' "
							+ "WHERE callnumber=? AND copyNo=?");
			ps1.setString(1, callNum);
			ps1.setInt(2, copyNum);
			ps1.executeUpdate();

			// If this borrower had a hold request for the item, delete it
			PreparedStatement ps2 = getConnection()
					.prepareStatement(
							"DELETE FROM holdrequest "
									+ "WHERE callnumber=? AND bid=?");
			ps2.setString(1, callNum);
			ps2.setInt(2, bid);
			ps2.executeUpdate();

			// Insert a borrowing record
			PreparedStatement ps = getConnection().prepareStatement(
					"INSERT INTO borrowing"
							+ "(bid, callnumber, copyno, outdate, indate) "
							+ "VALUES (?,?,?,CURRENT_DATE,"
							+ "(SELECT CURRENT_DATE+(7*booktimelimit)"
							+ " FROM borrowertype, borrower "
							+ "WHERE borrowertype.type=borrower.type "
							+ "AND borrower.bid=?))");

			// Set all the input values
			ps.setInt(1, bid);
			ps.setString(2, callNum);
			ps.setInt(3, copyNum);
			ps.setInt(4, bid);
			ps.executeUpdate();
			getConnection().commit();
			return true;

		} catch (SQLException e) {
			System.out.println("Failed to check out item");
			e.printStackTrace();
		}
		return false;
	}

	// Get the current date in SQL format
	/*
	 * private static java.sql.Date getCurrentDate() {
	 * 
	 * java.util.Date today = new java.util.Date(); java.sql.Date sqlDate = new
	 * java.sql.Date(today.getTime()); return sqlDate; }
	 */

	// (Librarian):Generating a report of all the books that have been checked
	// out.

	public static ResultSet getdisplayCheckOutAllBook(String subject) {
		ResultSet co = null;
		try {
			if (subject.length() > 0) {
				System.out.println("Report with subject = " + subject);
				PreparedStatement ps = getConnection()
						.prepareStatement(
								"SELECT borrowing.callnumber, borrowing.copyno, book.title, "
										+ "borrowing.outdate, (borrowing.outdate+(7*booktimelimit)) "
										+ "FROM borrowing, book, borrower, borrowertype, hassubject "
										+ "WHERE borrowing.callNumber=book.callNumber "
										+ "AND borrowing.callNumber=hassubject.callNumber "
										+ "AND LOWER(hassubject.subject)=?"
										+ "AND borrowing.bid=borrower.bid "
										+ "AND borrower.type=borrowertype.type "
										+ "AND borrowing.indate IS NULL "
										+ "ORDER BY callnumber");
				ps.setString(1, subject.toLowerCase());
				co = ps.executeQuery();
			} else {
				System.out.println("Report without subject");
				PreparedStatement ps = getConnection()
						.prepareStatement(
								"SELECT borrowing.callnumber, borrowing.copyno, book.title, "
										+ "borrowing.outdate, (borrowing.outdate+(7*booktimelimit)) "
										+ "FROM borrowing, book, borrower, borrowertype "
										+ "WHERE borrowing.callNumber=book.callNumber "
										+ "AND borrowing.bid=borrower.bid "
										+ "AND borrower.type=borrowertype.type "
										+ "AND borrowing.indate IS NULL "
										+ "ORDER BY callnumber");
				co = ps.executeQuery();
			}
		} catch (SQLException e) {
			System.out.println("Failed to check all book checked out");
			e.printStackTrace();
		}
		return co;
	}

	// Generate a report with the most popular items in a given year
	public static int popuparItem(int callNumber, String outDate,
			String dueDate, String bookTitle) {
		return 0;

	}

}


