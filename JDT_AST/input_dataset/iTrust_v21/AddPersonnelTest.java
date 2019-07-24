package edu.ncsu.csc.itrust.unit.dao.personnel;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.enums.Role;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class AddPersonnelTest extends TestCase {
	PersonnelDAO personnelDAO = TestDAOFactory.getTestInstance().getPersonnelDAO();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
	}

	public void testAddEmptyPersonnel() throws Exception {
		long mid = personnelDAO.addEmptyPersonnel(Role.HCP);
		assertEquals(" ", personnelDAO.getName(mid));
		assertEquals(Role.HCP, personnelDAO.getPersonnel(mid).getRole());
		assertTrue("hcp MID is greater or equal to 9 billion, actual:" + mid, mid >= 9000000000L);
	}

	public void testAddEmptyER() throws Exception {
		long mid = personnelDAO.addEmptyPersonnel(Role.ER);
		assertEquals(" ", personnelDAO.getName(mid));
		assertEquals("er", personnelDAO.getPersonnel(mid).getRole().getUserRolesString());
	}
	
	public void testGetNextID() throws Exception {
		assertEquals(         1L, personnelDAO.getNextID(Role.ADMIN));
		assertEquals(9000000000L, personnelDAO.getNextID(Role.ER));
		assertEquals(9000000000L, personnelDAO.getNextID(Role.HCP));
		assertEquals(5000000000L, personnelDAO.getNextID(Role.LT));
		assertEquals(         1L, personnelDAO.getNextID(Role.PATIENT));
		assertEquals(7000000000L, personnelDAO.getNextID(Role.PHA));
		assertEquals(         1L, personnelDAO.getNextID(Role.TESTER));
		assertEquals(8000000000L, personnelDAO.getNextID(Role.UAP));
	}

	public void testDoesNotExist() throws Exception {
		try {
			personnelDAO.getName(0L);
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("User does not exist", e.getMessage());
		}
	}
}
