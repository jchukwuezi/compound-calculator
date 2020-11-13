import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InitializeDatabase {
	public static Connection startConnection() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/calcaccounts?serverTimezone=UTC", "root", "root");
		return connection;
	}
	
}
