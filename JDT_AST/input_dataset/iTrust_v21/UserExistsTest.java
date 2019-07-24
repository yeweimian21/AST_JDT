package edu.ncsu.csc.itrust.unit.dao.auth;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class UserExistsTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	AuthDAO authDAO = factory.getAuthDAO();
	private TestDataGenerator gen = new TestDataGenerator();

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.patient1();
	}

	public void testUserExists() throws Exception {
		assertTrue(authDAO.checkUserExists(1L));
		assertFalse(authDAO.checkUserExists(200L));
	}
}
