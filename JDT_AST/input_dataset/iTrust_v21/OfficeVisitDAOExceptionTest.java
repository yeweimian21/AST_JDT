package edu.ncsu.csc.itrust.unit.dao.officevisit;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;

/**
 * Test office visit with exception DAO
 * @throw exception
 */
public class OfficeVisitDAOExceptionTest extends TestCase {
	private OfficeVisitDAO evilDAO = EvilDAOFactory.getEvilInstance().getOfficeVisitDAO();
	//private DiagnosesDAO evilDAO = EvilDAOFactory.getEvilInstance().getDiagnosesDAO();

	@Override
	protected void setUp() throws Exception {
	}

	public void testAddException() throws Exception {
		try {
			evilDAO.add(new OfficeVisitBean());
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testCheckOVExistsException() throws Exception {
		try {
			evilDAO.checkOfficeVisitExists(0L, 0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetAllOVsException() throws Exception {
		try {
			evilDAO.getAllOfficeVisits(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetOVException() throws Exception {
		try {
			evilDAO.getOfficeVisit(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}


	public void testUpdateOVException() throws Exception {
		try {
			evilDAO.update(new OfficeVisitBean());
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
}
