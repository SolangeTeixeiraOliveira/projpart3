package sqlFunctions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SQLFunctionsBorrower {

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
			PreparedStatement ps = Connector.getConnection().prepareStatement(
					"SELECT DISTINCT book.callnumber, book.title, " + 
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
		
		// Insert the new hold request into the hold request table
		try {
			// Check if there is a book copy that is in
			PreparedStatement ps1 = Connector.getConnection().prepareStatement(
					"SELECT * FROM bookcopy "
							+ "WHERE callnumber=? and status = 'in'");
			ps1.setString(1, callNum);
			ResultSet rs = ps1.executeQuery();

			if (rs.next()) {
				//JOptionPane.showMessageDialog(frame, "There is a copy of the book.");
			}else{			
				//Insert a hold request when there is no book copy available
				PreparedStatement ps2 = Connector.getConnection().prepareStatement(
						"INSERT INTO holdrequest(bid, callnumber, issuedDate) "
								+ "VALUES ( ?, ?, CURRENT_DATE) ", new String[] { "hid" });

				// Set all the input values
				ps2.setInt(1, userID);
				ps2.setString(2, callNum);

				//JOptionPane.showMessageDialog(frame, "New Hold Request Made.");
				
				// Execute the insert statement and return the new hold request id
				if (ps2.executeUpdate() > 0) {
					ResultSet generatedHid = ps2.getGeneratedKeys();
					if (null != generatedHid && generatedHid.next()) {
						return generatedHid.getInt(1);
					}
				} else {
					throw new SQLException("Creating hold request failed.");
				}	
			}
		} catch (SQLException e) {
			System.out.println("Failed to add hold request");
			e.printStackTrace();
		}
		
		return 0;
	}
	
	// Check if the book exists
	public static ResultSet bookExists(String callnum) {
		ResultSet rs = null;
		try {
			PreparedStatement ps = Connector.getConnection().prepareStatement("SELECT * FROM book " + 
					"WHERE callnumber = ?");
			ps.setString(1, callnum);
			rs = ps.executeQuery();
			System.out.println("Checking call number: " + callnum);
		} catch (SQLException e) { 
			System.out.println("Book does not Exists");
			e.printStackTrace();
		}
		return rs;
	}

	// Pay the Fine. Update the Fine tuple
	public static int payFine(int bid) {

		System.out.println("Paying fine.");

		try {
			PreparedStatement ps = Connector.getConnection().prepareStatement(
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
			PreparedStatement ps = Connector.getConnection().prepareStatement(
					"SELECT bid, borid, callnumber, copyno, outdate, " + 
					"indate FROM borrowing WHERE bid = ?"); 

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
			PreparedStatement ps = Connector.getConnection().prepareStatement(
					"SELECT borrowing.bid, fine.fid, fine.amount, fine.issueddate, " +
					"fine.paiddate FROM fine, borrowing " + 
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
			PreparedStatement ps = Connector.getConnection().prepareStatement(
					"SELECT bid, hid, callnumber, issueddate FROM holdrequest WHERE bid = ?"); 

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
