package edu.ncsu.csc.itrust.unit.dao.standards;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.MedicationBean;
import edu.ncsu.csc.itrust.dao.mysql.NDCodesDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;

public class NDDAOExceptionTest extends TestCase {
	private NDCodesDAO evilDAO = EvilDAOFactory.getEvilInstance().getNDCodesDAO();

	@Override
	protected void setUp() throws Exception {
	}

	public void testAddCodeException() throws Exception {
		try {
			evilDAO.addNDCode(new MedicationBean());
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetAllCodesException() throws Exception {
		try {
			evilDAO.getAllNDCodes();
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetException() throws Exception {
		try {
			evilDAO.getNDCode("");
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testUpdateCodeException() throws Exception {
		try {
			evilDAO.updateCode(new MedicationBean());
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
}
