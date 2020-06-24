package nl.calco.biblio;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class Database {
		
	public static Connection getConnection() throws SQLException, NamingException {
		Context context = new InitialContext();
		DataSource dataSource = (DataSource)context.lookup("jdbc/Bibliotheek");
		Connection connection = dataSource.getConnection();
		return connection;
	}
	
	

}
