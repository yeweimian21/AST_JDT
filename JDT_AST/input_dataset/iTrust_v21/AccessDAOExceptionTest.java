package edu.ncsu.csc.itrust.unit.dao.access;

import java.sql.SQLException;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.mysql.AccessDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;

public class AccessDAOExceptionTest extends TestCase {
	private AccessDAO evilDAO = EvilDAOFactory.getEvilInstance().getAccessDAO();

	
	@Override
	protected void setUp() throws Exception {
	}

	public void testGetSessionTimeoutException() throws Exception {
		try {
			evilDAO.getSessionTimeoutMins();
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testSetSessionTimeoutException() throws Exception {
		try {
			evilDAO.setSessionTimeoutMins(0);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertSame(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testDBException() throws Exception {
		DBException e = new DBException(null);
		assertEquals("No extended information.", e.getExtendedMessage());
		
		e = new DBException(new SQLException("Fake SQL Exception"));
		assertEquals("Fake SQL Exception", e.getExtendedMessage());
	}
	
	public void testiTrustException() throws Exception {
		ITrustException e = new ITrustException(null);
		assertEquals("An error has occurred. Please see log for details.", e.getMessage());
		assertEquals("No extended information.", e.getExtendedMessage());
	}
}
