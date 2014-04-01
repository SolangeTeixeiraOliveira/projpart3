package sqlFunctions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

	// Only access con through the getConnection function
	private static Connection con;

	public static Connection getConnection() {
		if (con == null) {
			try {
				
				DriverManager
						.registerDriver(new oracle.jdbc.driver.OracleDriver());
				con = DriverManager.getConnection(

				"jdbc:oracle:thin:@localhost:1522:ug", "ora_t3s7", "a41513102");

				con.setAutoCommit(false);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		return con;
	}
	
}
