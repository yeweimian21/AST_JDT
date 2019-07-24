package edu.ncsu.csc.itrust.unit.dao.patientinstructions;

import edu.ncsu.csc.itrust.beans.PatientInstructionsBean;
import edu.ncsu.csc.itrust.dao.mysql.PatientInstructionsDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import junit.framework.TestCase;

public class PatientInstructionsDAOExceptionTest extends TestCase {
	private PatientInstructionsDAO evilDAO = EvilDAOFactory.getEvilInstance().getPatientInstructionsDAO();
	
	public void testCreateException() throws Exception {
		try {
			evilDAO.add(new PatientInstructionsBean());
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testEditException() throws Exception {
		try {
			evilDAO.edit(new PatientInstructionsBean());
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetListException() throws Exception {
		try {
			evilDAO.getList(1);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testRemoveException() throws Exception {
		try {
			evilDAO.remove(1);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetOfficeVisitsWithInstructions() throws Exception {
		try {
			evilDAO.getOfficeVisitsWithInstructions(1);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
}
