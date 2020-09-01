package nl.rubend.ovchipkaart;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class SQLConnector {
	private static Connection conn;
	static {
		Properties prop = new Properties();
		try {
			prop.load(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties")));
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("pass"));
		} catch (SQLException | IOException throwables) {
			throwables.printStackTrace();
		}
	}
	public static Connection getConn() {return conn;}
}
