import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLFunctions {

	private static Connection con;

	public SQLFunctions() {
		/*try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug",
					"ora_x4q7", "a45775103");
		} catch (SQLException e) {
			System.out.println("Problem registering driver or connecting to oracle");
			e.printStackTrace();
		}*/
	}

	// Add a borrower, automatically generating a new id for them
	public static int addBorrower(String name, String password, String address,
			int phone, String email, int sinOrStNo, String type) {

		System.out.println("Adding borrower " + name);
		try {
			// Create the statement
			PreparedStatement ps = con.prepareStatement("INSERT INTO borrower" +
					"(name, password, address, phone, email, sinOrStNo, expiryDate, type) " +
					"VALUES (?,?,?,?,?,?,?,?)");
			
			// Set all the input values
			ps.setString(1, name);
			ps.setString(2, password);
			ps.setString(3, address);
			ps.setInt(4, phone);
			ps.setString(5, email);
			ps.setInt(6, sinOrStNo);
			ps.setDate(7, getCurrentDate());
			ps.setString(8, type);
			
			// Check for the automatically generated borrower id and return it
			ResultSet generatedKeys = null;
			generatedKeys = ps.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            return generatedKeys.getInt(1);
	        } else {
	            throw new SQLException("Creating user failed, no generated key obtained.");
	        }
			
		} catch (SQLException e) {
			System.out.println("Failed to add borrower");
			e.printStackTrace();
		}
		return 0;
	}
	
	// Get the current date in SQL format
	private static java.sql.Date getCurrentDate() {
		 
		java.util.Date today = new java.util.Date();
		return (java.sql.Date) today;
	}
	
	// Search a book in the Book Table
	public static String searchbook(String title, String author, String subject){
		
		System.out.println("Searching for book with " + title + ", " + author + ", " + subject);
		String book = null;
		try {
			// Create the statement
			PreparedStatement ps = con.prepareStatement("SELECT CallNumber, Title, MainAuthor"
					+ "FROM Book, BookCopy, HasAuthor, HasSubject" + 
					"WHERE ");
		
			// Set all the input values
			ps.setString(1, title);
			ps.setString(2, author);
			ps.setString(3, subject);
			
			} else {
			      throw new SQLException("Searching for Book failed, no book obtained.");
			}
						
		} catch (SQLException e) {
			System.out.println("Failed to search for book");
			e.printStackTrace();
		}
		
		return book;
	}

}
