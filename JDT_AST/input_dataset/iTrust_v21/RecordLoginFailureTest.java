package edu.ncsu.csc.itrust.unit.dao.auth;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class RecordLoginFailureTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	AuthDAO authDAO = factory.getAuthDAO();
	private TestDataGenerator gen = new TestDataGenerator();
	private String ipAddr = "192.168.1.1";

	@Override
	protected void setUp() throws Exception {
		gen.clearLoginFailures();
	}

	public void testGetLoginFailuresNoEntry() throws Exception {
		authDAO.recordLoginFailure(ipAddr);
		assertEquals(1, authDAO.getLoginFailures(ipAddr));
	}
	
	public void testGetLoginFailuresWithEntry() throws Exception {
		assertEquals(0, authDAO.getLoginFailures(ipAddr));
		authDAO.recordLoginFailure(ipAddr);
		assertEquals(1, authDAO.getLoginFailures(ipAddr));
		authDAO.recordLoginFailure(ipAddr);
		assertEquals(2, authDAO.getLoginFailures(ipAddr));
		authDAO.recordLoginFailure(ipAddr);
		assertEquals(3, authDAO.getLoginFailures(ipAddr));
	}
}
