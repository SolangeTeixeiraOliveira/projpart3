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
				DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
				con = DriverManager.getConnection(
						"jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug",
						"ora_x4q7", "a45775103");
			} catch (SQLException e) {
				System.out.println("Problem registering driver or connecting to oracle");
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
			PreparedStatement ps = getConnection().prepareStatement("INSERT INTO borrower" +
					"(name, password, address, phone, emailAddress, sinOrStNo, expiryDate, type) " +
					"VALUES (?,?,?,?,?,?,?,?)", new String[] { "bid" });
			
			// Set all the input values
			ps.setString(1, name);
			ps.setString(2, password);
			ps.setString(3, address);
			ps.setInt(4, phone);
			ps.setString(5, email);
			ps.setInt(6, sinOrStNo);
			ps.setDate(7, getCurrentDate());
			ps.setString(8, type);
			
			// execute the insert statement and return the new borrower id
			if (ps.executeUpdate() > 0) {
				ResultSet generatedKeys = ps.getGeneratedKeys();
				if (null != generatedKeys && generatedKeys.next()) {
					return generatedKeys.getInt(1);
				}
			} else {
	            throw new SQLException("Creating user failed, no generated key obtained.");
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
	public static boolean returnItem(String callNumber, int copyNumber) {
		try {
			// TODO: Set to "on-hold" if there is a hold request for it
			// Do this in Java or SQL?
			
			// Update the status of the book copy
			PreparedStatement ps = getConnection().prepareStatement("UPDATE bookcopy" +
					"SET status='in' " +
					"WHERE callNumber=?, copyNumber=?");
			ps.setString(1, callNumber);
			ps.setInt(2, copyNumber);
			ps.executeUpdate();
			
			// If it is past the book's due date, assess a fine for the borrower
			ps = getConnection().prepareStatement("SELECT borid,inDate FROM borrowing" +
					"WHERE callNumber=?, copyNumber=?");
			ps.setString(1, callNumber);
			ps.setInt(2,  copyNumber);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				java.sql.Date duedate = rs.getDate("inDate");
				String borid = rs.getString("borid");
				java.sql.Date currentDate = getCurrentDate();
				if (duedate.before(currentDate)) {
					// Book is late - assess a fine
					ps = getConnection().prepareStatement("INSERT INTO fine"
							+ "(amount, issueddate, borid)" +
							"VALUES (?,?,?)");
					float dayslate = (float)((currentDate.getTime() - duedate.getTime())/(1000 * 60 * 60 * 24));
					ps.setFloat(1, dayslate/10); // Charge 10 cents per day
					ps.setDate(2, currentDate);
					ps.setString(3, borid);
					ps.executeUpdate();
				}
			}
			
			// Delete the borrowing record
			ps = getConnection().prepareStatement("DELETE FROM borrowing" +
					"WHERE callNumber=?, copyNumber=?");
			ps.setString(1, callNumber);
			ps.setInt(2, copyNumber);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Failed to return item");
			e.printStackTrace();
		}
		
		return false;
	}
	
	// Get the current date in SQL format
	private static java.sql.Date getCurrentDate() {
		 
		java.util.Date today = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(today.getTime());
		return sqlDate;
	}
	
	// Search a book in the Book Table
	public static String searchbook(String title, String author, String subject){
		
		System.out.println("Searching for book with " + title + ", " + author + ", " + subject);
		String book = null;
		try {
			// Create the prepared statement for the query
			PreparedStatement ps = getConnection().prepareStatement("SELECT book.title, hasauthor.name as AUTHOR, hassubject.subject"
					+ "FROM Book, HasAuthor, HasSubject" + 
					"WHERE book.callnumber = hasauthor.callnumber and book.callnumber = hassubject.callnumber and" +
					"book.title like '%" + title + "%' and hasauthor.name like '%" + author + 
					"%' and hassubject.subject like '%" + subject + "%'");
		
			// Set all the input values
			ps.setString(1, title);
			ps.setString(2, author);
			ps.setString(3, subject);
			
			// Execute the query
			ps.executeQuery();
			
			System.out.println("Query executed");
				
		} catch (SQLException e) {
			System.out.println("Failed to search for book");
			e.printStackTrace();
		}
		
		return book;
	}

}