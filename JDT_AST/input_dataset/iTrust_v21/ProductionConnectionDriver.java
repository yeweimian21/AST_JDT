package edu.ncsu.csc.itrust.dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Produces the JDBC connection from Tomcat's JDBC connection pool (defined in context.xml). Produces and
 * exception when running the unit tests because they're not being run through Tomcat.
 * 
 *  
 * 
 */
public class ProductionConnectionDriver implements IConnectionDriver {
	private InitialContext initialContext;

	// In production situations
	public ProductionConnectionDriver() {
	}

	// For our special unit test - do not use unless you know what you are doing
	public ProductionConnectionDriver(InitialContext context) {
		initialContext = context;
	}

	public Connection getConnection() throws SQLException {
		try {
			if (initialContext == null)
				initialContext = new InitialContext();
			return ((DataSource) (((Context) initialContext.lookup("java:comp/env"))).lookup("jdbc/itrust"))
					.getConnection();
		} catch (NamingException e) {
			throw new SQLException(("Context Lookup Naming Exception: " + e.getMessage()));
		}
	}
}
