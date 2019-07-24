package edu.ncsu.csc.itrust.unit.dao.LOINC;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.LOINCbean;
import edu.ncsu.csc.itrust.dao.mysql.LOINCDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;

public class LOINCDAOExceptionTest extends TestCase {
	private LOINCDAO lDAO = EvilDAOFactory.getEvilInstance().getLOINCDAO();

	public void testAddLOINC() throws Exception {
		LOINCbean lb = new LOINCbean();

		try {
			lDAO.addLOINC(lb);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}

	}

	public void testUpdate() throws Exception {
		LOINCbean lb = new LOINCbean();

		try {
			lDAO.update(lb);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}

	}

	public void testGetProcedures() throws Exception {
		try {
			lDAO.getProcedures("2");
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}

	}

	public void testGetAllLOINC() throws Exception {
		try {
			lDAO.getAllLOINC();
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

}
