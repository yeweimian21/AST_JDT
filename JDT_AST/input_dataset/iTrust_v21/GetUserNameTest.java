package edu.ncsu.csc.itrust.unit.dao.auth;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class GetUserNameTest extends TestCase {
	AuthDAO authDAO = TestDAOFactory.getTestInstance().getAuthDAO();
	private TestDataGenerator gen = new TestDataGenerator();

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
	}
	
	public void testHCP0() throws Exception {
		gen.hcp0();
		assertEquals("HCP 0", "Kelly Doctor", authDAO.getUserName(9000000000L));
	}

	public void testPatient1() throws Exception {
		gen.patient1();
		assertEquals("Patient 1", "Random Person", authDAO.getUserName(1L));
	}

	public void testAdmin1() throws Exception {
		gen.admin1();
		assertEquals("Admin 1", "Shape Shifter", authDAO.getUserName(9000000001L));
	}
}
