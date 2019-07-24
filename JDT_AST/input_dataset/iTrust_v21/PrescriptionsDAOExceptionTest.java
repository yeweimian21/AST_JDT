package edu.ncsu.csc.itrust.unit.dao.officevisit;

import edu.ncsu.csc.itrust.beans.PrescriptionBean;
import edu.ncsu.csc.itrust.dao.mysql.PrescriptionsDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import junit.framework.TestCase;

public class PrescriptionsDAOExceptionTest extends TestCase {
	private PrescriptionsDAO evilDAO = EvilDAOFactory.getEvilInstance().getPrescriptionsDAO();

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
			evilDAO.add(new PrescriptionBean());
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testEdit() {
		try {
			evilDAO.edit(new PrescriptionBean());
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
