package edu.ncsu.csc.itrust.unit.dao.officevisit;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class OfficeVisitExistsTest extends TestCase {
	private OfficeVisitDAO ovDAO = TestDAOFactory.getTestInstance().getOfficeVisitDAO();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient2();
	}
	
	public void testExists() throws Exception {
		new TestDataGenerator().officeVisit1();
		assertTrue(ovDAO.checkOfficeVisitExists(1, 1));
		// wrong patient
		assertFalse(ovDAO.checkOfficeVisitExists(1, 2));
	}

	public void testDoesNotExist() throws Exception {
		assertFalse(ovDAO.checkOfficeVisitExists(500, 1));
	}
}
