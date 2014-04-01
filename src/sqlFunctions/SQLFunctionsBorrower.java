package sqlFunctions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLFunctionsBorrower {

	// Search a book in the Book Table
	public static ResultSet searchbook(String title, String author, String subject){

		title = title.toLowerCase();
		author = author.toLowerCase();
		subject = subject.toLowerCase();
		
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
			
			e.printStackTrace();
		}
		return rs;

	}

	// Add a new Hold Request
	public static int holdRequest(int userID, String callNum) {
		
		// Insert the new hold request into the hold request table
		try {
			// Check if there is a book copy that is in
			PreparedStatement ps1 = Connector.getConnection().prepareStatement(
					"SELECT * FROM bookcopy "
							+ "WHERE callnumber=? and status = 'in'");
			ps1.setString(1, callNum);
			ResultSet rs = ps1.executeQuery();

			if (rs.next()) {
				return 0;
			}else{			
				//Insert a hold request when there is no book copy available
				PreparedStatement ps2 = Connector.getConnection().prepareStatement(
						"INSERT INTO holdrequest(bid, callnumber, issuedDate) "
								+ "VALUES ( ?, ?, CURRENT_DATE) ", new String[] { "hid" });

				// Set all the input values
				ps2.setInt(1, userID);
				ps2.setString(2, callNum);
				
				// Execute the insert statement and return the new hold request id
				if (ps2.executeUpdate() > 0) {
					ResultSet generatedHid = ps2.getGeneratedKeys();
					if (null != generatedHid && generatedHid.next()) {
						Connector.getConnection().commit();
						return generatedHid.getInt(1);
					}
				} else {
					throw new SQLException("Creating hold request failed.");
				}	
			}
			
		} catch (SQLException e) {
		
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
			
		} catch (SQLException e) { 
			
			e.printStackTrace();
		}
		return rs;
	}

	// Pay the Fine. Update the Fine tuple
	public static int payFine(int fid, int bid) {

		try {
			PreparedStatement ps2 = Connector.getConnection().prepareStatement(
					"UPDATE fine SET paidDate = CURRENT_DATE "
							+ "WHERE fine.fid=?");

			// Set all the input values
			ps2.setInt(1, fid);

			// Execute the update statement
			ps2.executeUpdate();

			Connector.getConnection().commit();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return 0;
	}
	
	public static ResultSet payFineHelper(int bid) {

		ResultSet rs = null;
		try {
			PreparedStatement ps1 = Connector.getConnection().prepareStatement(
					"select fine.borid from borrowing, fine where borrowing.bid=? and "
					+ "fine.borid = borrowing.borid");
			ps1.setInt(1, bid);
			rs = ps1.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}


	// Check the items that are currently borrowed by the borrower
	public static ResultSet checkborrowing(Integer bid) {
		ResultSet rs = null;

		try {
			// Create the prepared statement for the query

			PreparedStatement ps = Connector.getConnection().prepareStatement(
					"SELECT callnumber, copyno, outdate, outdate+(7*booktimelimit) " +
					"FROM borrowing, borrower, borrowertype " +
					"WHERE borrowing.bid = ? " +
					"AND borrowing.bid=borrower.bid " +
					"AND borrower.type=borrowertype.type " +
					"AND borrowing.indate IS NULL"); 

			// Set all the input values
			ps.setInt(1, bid);

			// Execute the query statement and return the books searched
			rs = ps.executeQuery();
			

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return rs;
	}

	// Check the fines that the borrower has
	public static ResultSet checkfine(Integer bid) {

		ResultSet rs = null;

		try {
			// Create the prepared statement for the query
			PreparedStatement ps = Connector.getConnection().prepareStatement(
					"SELECT fine.fid, fine.amount, fine.issueddate FROM fine, borrowing " + 
					"WHERE fine.borid = borrowing.borid AND borrowing.bid = ? " +
					"AND fine.paiddate IS NULL"); 

			// Set all the input values
			ps.setInt(1, bid);

			// Execute the query statement and return the books searched
			rs = ps.executeQuery();

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return rs;
	}

	// Check hold requests the borrower has
	public static ResultSet checkholdrequest(Integer bid) {

		ResultSet rs = null;

		try {
			// Create the prepared statement for the query
			PreparedStatement ps = Connector.getConnection().prepareStatement(
					"SELECT hid, callnumber, issueddate FROM holdrequest WHERE bid = ?"); 

			// Set all the input values
			ps.setInt(1, bid);

			// Execute the query statement and return the books searched
			rs = ps.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

}
