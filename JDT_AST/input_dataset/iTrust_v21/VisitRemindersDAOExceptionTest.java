package edu.ncsu.csc.itrust.unit.dao.visitreminders;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.mysql.VisitRemindersDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;

public class VisitRemindersDAOExceptionTest extends TestCase {
	private VisitRemindersDAO evilDAO = EvilDAOFactory.getEvilInstance().getVisitRemindersDAO();
	
	@Override
	protected void setUp() throws Exception {
	}
	
	public void testGetDiagnosedVisitNeedersException() throws Exception {
		try {
			evilDAO.getDiagnosedVisitNeeders(0);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testGetFluShotDelinquentsException() throws Exception {
		try {
			evilDAO.getFluShotDelinquents(0);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
}
