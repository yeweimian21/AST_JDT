package edu.ncsu.csc.itrust.unit.dao.standards;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.dao.mysql.ICDCodesDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;

public class ICDDAOExceptionTest extends TestCase {
	private ICDCodesDAO evilDAO = EvilDAOFactory.getEvilInstance().getICDCodesDAO();

	@Override
	protected void setUp() throws Exception {
	}

	public void testAddICDCodeException() throws Exception {
		try {
			evilDAO.addICDCode(new DiagnosisBean());
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetAllICDCodesException() throws Exception {
		try {
			evilDAO.getAllICDCodes();
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetICDException() throws Exception {
		try {
			evilDAO.getICDCode("0.0");
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testUpdateCodeException() throws Exception {
		try {
			evilDAO.updateCode(new DiagnosisBean());
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
}
