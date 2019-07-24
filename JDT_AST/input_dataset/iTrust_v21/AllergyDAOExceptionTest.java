package edu.ncsu.csc.itrust.unit.dao.allergies;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.AllergyBean;
import edu.ncsu.csc.itrust.dao.mysql.AllergyDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;

public class AllergyDAOExceptionTest extends TestCase {
	private AllergyDAO evilDAO = EvilDAOFactory.getEvilInstance().getAllergyDAO();

	@Override
	protected void setUp() throws Exception {
	}
	
	/*
	 * updated to reflect the new way addAllergy updates allergyDAO.
	 */
	public void testAddAllergyException() throws Exception {
		try {
			AllergyBean bean = new AllergyBean();
			bean.setPatientID(0);
			bean.setNDCode("");
			bean.setDescription("");
			evilDAO.addAllergy(bean);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetAllergyException() throws Exception {
		try {
			evilDAO.getAllergies(0);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
}
