package edu.ncsu.csc.itrust.unit.dao.officevisit;

import edu.ncsu.csc.itrust.beans.OverrideReasonBean;
import edu.ncsu.csc.itrust.dao.mysql.PrescriptionOverrideDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import junit.framework.TestCase;

public class PrescriptionOverrideDAOExceptionTest extends TestCase {
	private PrescriptionOverrideDAO evilDAO = new PrescriptionOverrideDAO(EvilDAOFactory.getEvilInstance());

	public void testGetList() {
		try {
			evilDAO.getList(1L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testAdd() {
		try {
			evilDAO.add(new OverrideReasonBean("12345"));
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}


	public void testRemove() {
		try {
			evilDAO.remove(1L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

}
