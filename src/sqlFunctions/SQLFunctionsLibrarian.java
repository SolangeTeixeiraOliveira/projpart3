package sqlFunctions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class SQLFunctionsLibrarian {
	// Librarian): Adds a new book or new copy of an existing book to the
	// library
	public static int addBook(String callNumber, int isbn, String title,
			String mainAuthor, String publisher, Integer publicationYear,
			Vector<String> authorList, Vector<String> subjectList) {

		
		try {
			// See how many copies of the book exist already (if any)
			PreparedStatement ps1 = Connector.getConnection().prepareStatement(
					"SELECT MAX(copyno) FROM bookcopy " + "WHERE callNumber=?");
			ps1.setString(1, callNumber);
			ResultSet rs = ps1.executeQuery();

			int numcopies = 0;
			if (rs.next()) {
				numcopies = rs.getInt(1);
			}

			// Insert a new book entry if needed
			if (numcopies == 0) {
				PreparedStatement ps = Connector
						.getConnection()
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

				// Add each subject
				PreparedStatement ps3 = Connector.getConnection()
						.prepareStatement(
								"INSERT INTO hassubject (callNumber, subject) "
										+ "VALUES (?,?)");
				ps3.setString(1, callNumber);
				for (String subject : subjectList) {
					ps3.setString(2, subject);
					ps3.executeUpdate();
				}

				// Add each author
				PreparedStatement ps4 = Connector.getConnection()
						.prepareStatement(
								"INSERT INTO hasauthor (callNumber, name) "
										+ "VALUES (?,?)");
				ps4.setString(1, callNumber);
				authorList.add(mainAuthor);
				for (String author : authorList) {
					ps4.setString(2, author);
					ps4.executeUpdate();
				}
			}

			// Add a new book copy
			PreparedStatement ps2 = Connector.getConnection().prepareStatement(
					"INSERT INTO bookcopy (callNumber, copyNo, status) "
							+ "VALUES (?,?,'in')");
			ps2.setString(1, callNumber);
			ps2.setInt(2, numcopies + 1);
			ps2.executeUpdate();

			Connector.getConnection().commit();
			return numcopies + 1;

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return 0;
	}

	// (Librarian):Generating a report of all the books that have been checked
	// out.

	public static ResultSet getdisplayCheckOutAllBook(String subject) {
		ResultSet co = null;
		try {
			if (subject.length() > 0) {
				PreparedStatement ps = Connector
						.getConnection()
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
				PreparedStatement ps = Connector
						.getConnection()
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
			e.printStackTrace();
		}
		return co;
	}

	// Generate a report with the most popular items in a given year
	public static ResultSet getMostPopularItems(int year, int n) {
		ResultSet res = null;

		try {

			PreparedStatement ps = Connector.getConnection().prepareStatement(
					"SELECT topbook.callnumber, topbook.checkouts "
					+ "FROM (SELECT callNumber, count(callNumber) AS checkouts "
							+ "FROM borrowing WHERE (EXTRACT(year from indate) = ? "
							+ "OR EXTRACT(year from outdate) = ?) GROUP BY callNumber "
							+ "ORDER BY checkouts DESC) topbook "
					+ "WHERE ROWNUM <= ? ");
			ps.setInt(1, year);
			ps.setInt(2, year);
			ps.setInt(3, n);

			res = ps.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
}
