package edu.ncsu.csc.itrust.unit.testutils;

import java.sql.Connection;
import java.sql.SQLException;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.IConnectionDriver;

/**
 * This class is an "evil" (or Diabolical) test connection driver that will not give you a connection, but
 * instead will throw a special SQLException every time. Unit tests need to test this catch block and assert
 * the SQLException message. <br />
 * <br />
 * It's implemented as a singleton to make unit tests easier (just a pointer comparison)
 * 
 *  
 * 
 */
public class EvilDAOFactory extends DAOFactory implements IConnectionDriver {
	public static final String MESSAGE = "Exception thrown from Evil Test Connection Driver";
	private static DAOFactory evilTestInstance;

	public static DAOFactory getEvilInstance() {
		if (evilTestInstance == null)
			evilTestInstance = new EvilDAOFactory();
		return evilTestInstance;
	}

	private DAOFactory driver = null;
	private int numCorrect = 0;

	public EvilDAOFactory() {
	}

	// Here's how this behavior works: you can set EvilDAOFactory to count down to 0, giving correct
	// connections the whole way. Then, when you want it to, it starts throwing exceptions
	public EvilDAOFactory(int numCorrect) {
		this.driver = TestDAOFactory.getTestInstance();
		this.numCorrect = numCorrect;
	}

	@Override
	public Connection getConnection() throws SQLException {
		if (numCorrect-- > 0) //check THEN decrement
			return driver.getConnection();
		else
			throw new SQLException(MESSAGE);
	}
}
