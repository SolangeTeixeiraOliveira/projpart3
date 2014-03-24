import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLFunctions {

	private Connection con;

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
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Connection con2 = DriverManager.getConnection(
					"jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug",
					"ora_x4q7", "a45775103");
			PreparedStatement ps = con2.prepareStatement("INSERT INTO borrower" +
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
	
	// Get the current date in SQL format
	private static java.sql.Date getCurrentDate() {
		 
		java.util.Date today = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(today.getTime());
		return sqlDate;
	}
}