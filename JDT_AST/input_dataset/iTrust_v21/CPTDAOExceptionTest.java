package edu.ncsu.csc.itrust.unit.dao.standards;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ProcedureBean;
import edu.ncsu.csc.itrust.dao.mysql.CPTCodesDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;

public class CPTDAOExceptionTest extends TestCase {
	private CPTCodesDAO evilDAO = EvilDAOFactory.getEvilInstance().getCPTCodesDAO();

	@Override
	protected void setUp() throws Exception {
	}

	public void testAddCodeException() throws Exception {
		try {
			evilDAO.addCPTCode(new ProcedureBean());
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testUpdateCodeException() throws Exception {
		try {
			evilDAO.updateCode(new ProcedureBean());
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
}
