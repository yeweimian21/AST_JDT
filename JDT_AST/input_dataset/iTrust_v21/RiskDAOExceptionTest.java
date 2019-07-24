package edu.ncsu.csc.itrust.unit.dao.risk;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.mysql.RiskDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;

public class RiskDAOExceptionTest extends TestCase {
	private RiskDAO evilDAO = EvilDAOFactory.getEvilInstance().getRiskDAO();

	@Override
	protected void setUp() throws Exception {
	}

	public void testChildhoodInfectionException() throws Exception {
		try {
			evilDAO.hadChildhoodInfection(0L, 0.0);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testPriorDiagnosesException() throws Exception {
		try {
			evilDAO.hadPriorDiagnoses(0L, 0.0, 0.0);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testHasSmokedException() throws Exception {
		try {
			evilDAO.hasSmoked(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
}
