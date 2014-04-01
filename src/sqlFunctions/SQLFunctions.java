package sqlFunctions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLFunctions {

	public static boolean isValidAccount(int bid) {
		try {
			PreparedStatement ps = Connector.getConnection().prepareStatement(
					"SELECT * FROM borrower WHERE bid=?");
			ps.setInt(1, bid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean isValidBorrower(int bid, String pwd) {
		try {
			PreparedStatement ps = Connector.getConnection().prepareStatement(
					"SELECT * FROM borrower WHERE bid=? and password=?");
			ps.setInt(1, bid);
			ps.setString(2, pwd);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}


