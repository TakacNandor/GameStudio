package GameStudio.score;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	protected static Connection getDBConnection() {

		Connection dbConnection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection("jdbc:mysql://localhost/gamestudio?user=root&password=");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}

}
