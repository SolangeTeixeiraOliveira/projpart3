package sqlFunctions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Vector;

public class SQLFunctionsClerk {

	// Add a borrower, automatically generating a new id for them
		public static int addBorrower(String name, String password, String address,
				int phone, String email, int sinOrStNo, String type) {

			System.out.println("Adding borrower " + name);
			try {
				PreparedStatement ps = Connector.getConnection()
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
						Connector.getConnection().commit();
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
		// Return an item
		public static Vector<String> returnItem(String callNumber, int copyNumber) {
			String holderEmailAddress = null;
			String borrowerFine = null;

			try {
				// Check if there is a hold request for the book
				PreparedStatement ps = Connector.getConnection().prepareStatement(
						"SELECT emailAddress FROM holdrequest, borrower "
								+ "WHERE holdrequest.bid = borrower.bid "
								+ "AND callnumber=? ORDER BY issueddate");
				ps.setString(1, callNumber);
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					System.out.println("Setting book to on-hold");
					// Set the status of the book copy to 'on-hold'
					PreparedStatement ps2 = Connector.getConnection().prepareStatement(
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
					PreparedStatement ps3 = Connector.getConnection().prepareStatement(
							"UPDATE bookcopy SET status='in' "
									+ "WHERE callNumber=? AND copyNo=?");
					ps3.setString(1, callNumber);
					ps3.setInt(2, copyNumber);
					ps3.executeUpdate();
					ps3.close();
				}

				// If it is past the book's due date, assess a fine for the borrower
				PreparedStatement ps4 = Connector.getConnection()
						.prepareStatement(
							"SELECT borid, " +
							"ROUND(0.1*(CURRENT_DATE-(borrowertype.booktimelimit+borrowing.outdate)), 2) "
									+ "FROM borrowing, borrower, borrowertype "
									+ "WHERE borrowing.callnumber=? "
									+ "AND borrowing.copyno=? "
									+ "AND borrowing.bid=borrower.bid "
									+ "AND borrower.type=borrowertype.type "
									+ "AND borrowing.indate IS NULL");
				ps4.setString(1, callNumber);
				ps4.setInt(2, copyNumber);
				ResultSet rs2 = ps4.executeQuery();
				int borid;
				float fine;
				if (rs2.next()) {
					borid = rs2.getInt(1);
					fine = rs2.getFloat(2);
					System.out.println("Borid: " + rs2.getInt(1));
				} else {
					System.out.println("Did not find borrowing record");
					throw new SQLException();
				}

				if (fine > 0) {
					PreparedStatement ps6 = Connector.getConnection().prepareStatement(
							"INSERT INTO fine (amount, issueddate, borid) "
									+ "VALUES (?,CURRENT_DATE,?)");
					ps6.setFloat(1, fine);
					ps6.setInt(2, borid);
					ps6.executeUpdate();
					// Format fine as a string
					borrowerFine = NumberFormat.getCurrencyInstance().format(fine);
				}

				// Update the indate of the borrowing record
				PreparedStatement ps5 = Connector.getConnection().prepareStatement(
						"UPDATE borrowing SET indate=CURRENT_DATE "
								+ "WHERE callNumber=? AND copyNo=? AND indate IS NULL");
				ps5.setString(1, callNumber);
				ps5.setInt(2, copyNumber);
				ps5.executeUpdate();
				System.out.println("Updated indate");
				ps5.close();

				Connector.getConnection().commit();
			} catch (SQLException e) {
				System.out.println("Failed to return item");
				e.printStackTrace();
				return null;
			}
			Vector<String> returnvals = new Vector<String>();
			returnvals.add(holderEmailAddress);
			returnvals.add(borrowerFine);
			return returnvals;
		}

		public static ResultSet getOverdueItems() {

			System.out.println("Checking overdue items");
			ResultSet rs = null;

			try {
				PreparedStatement ps = Connector.getConnection()
						.prepareStatement(
								"SELECT borrowing.callnumber, borrowing.copyno, title, emailaddress "
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
		
		// Check out an item
		public static boolean checkOutItem(String callNum, int copyNum, int bid) {
			System.out.println("Checking out item " + callNum + " C" + copyNum);
			try {
				// Check hold requests for the item
				PreparedStatement ps3 = Connector.getConnection().prepareStatement(
						"SELECT bid FROM holdrequest " + "WHERE callnumber=? "
								+ "ORDER BY issueddate");
				ps3.setString(1, callNum);
				ResultSet rs = ps3.executeQuery();
				if (rs.next()) {
					// If this borrower doesn't have the earliest hold request on
					// the item, they can't check it out
					if (rs.getInt(1) != bid) {
						System.out
								.println("Cannot checkout book - someone else has a hold request");
						return false;
					}
				}

				// Check to make sure the item is not already out
				PreparedStatement ps8 = Connector.getConnection().prepareStatement(
						"SELECT status FROM bookcopy WHERE"
								+ " callNumber=? AND copyNo=?");
				ps8.setString(1, callNum);
				ps8.setInt(2, copyNum);
				ResultSet rs4 = ps8.executeQuery();
				if (rs4.next()) {
					if (rs4.getString(1) == "out") {
						System.out
								.println("Cannot check out a book that's already out");
						return false;
					}
				}

				// Set the bookcopy status to 'out'
				PreparedStatement ps1 = Connector.getConnection().prepareStatement(
						"UPDATE bookcopy SET status='out' "
								+ "WHERE callnumber=? AND copyNo=?");
				ps1.setString(1, callNum);
				ps1.setInt(2, copyNum);
				ps1.executeUpdate();

				// If this borrower had a hold request for the item, delete it
				PreparedStatement ps2 = Connector.getConnection()
						.prepareStatement(
								"DELETE FROM holdrequest "
										+ "WHERE callnumber=? AND bid=?");
				ps2.setString(1, callNum);
				ps2.setInt(2, bid);
				ps2.executeUpdate();

				// Insert a borrowing record
				PreparedStatement ps = Connector.getConnection().prepareStatement(
						"INSERT INTO borrowing"
								+ "(bid, callnumber, copyno, outdate, indate) "
								+ "VALUES (?,?,?,CURRENT_DATE,null)");

				// Set all the input values
				ps.setInt(1, bid);
				ps.setString(2, callNum);
				ps.setInt(3, copyNum);
				ps.executeUpdate();
				Connector.getConnection().commit();
				return true;

			} catch (SQLException e) {
				System.out.println("Failed to check out item");
				e.printStackTrace();
			}
			return false;
		}

		// Gets the due date for any items the borrower checks out on the current day
		public static java.util.Date getDueDate(int bid) {
			try {
				PreparedStatement ps = Connector.getConnection().prepareStatement(
						"SELECT (CURRENT_DATE+(7*booktimelimit)) " +
						"FROM borrower, borrowertype " +
						"WHERE bid=? AND borrower.type=borrowertype.type");
				ps.setInt(1, bid);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					java.util.Date duedate = new java.util.Date();
					duedate.setTime(rs.getDate(1).getTime());
					return duedate;
				}

			} catch (SQLException e) {
				System.out.println("Checking borrower account failed");
			}
			return null;
		}

}
