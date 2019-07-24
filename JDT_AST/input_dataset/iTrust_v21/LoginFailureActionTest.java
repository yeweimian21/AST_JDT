package edu.ncsu.csc.itrust.unit.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.LoginFailureAction;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class LoginFailureActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DAOFactory evil = EvilDAOFactory.getEvilInstance();
	private TestDataGenerator gen;
	private LoginFailureAction action;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		action = new LoginFailureAction(factory, "192.168.1.1");
	}

	public void testNormalLoginFailureSequence() throws Exception {
		assertTrue(action.isValidForLogin());
		assertEquals("Login failed, attempt 1", action.recordLoginFailure());
		assertTrue(action.isValidForLogin());
		assertEquals("Login failed, attempt 2", action.recordLoginFailure());
		assertTrue(action.isValidForLogin());
		assertEquals("Login failed, attempt 3", action.recordLoginFailure());
		assertFalse(action.isValidForLogin());
	}

	public void testRecordLoginFailureEvil() throws Exception {
		action = new LoginFailureAction(evil, "192.168.1.1");
		assertEquals("A database exception has occurred. Please see the log in the "
				+ "console for stacktrace", action.recordLoginFailure());
	}

	public void testIsValidForLoginEvil() throws Exception {
		action = new LoginFailureAction(evil, "192.168.1.1");
		assertEquals(false, action.isValidForLogin());
	}
	
	public void testNeedsCaptcha() throws Exception {
		assertEquals(0, action.getFailureCount());
		assertFalse(action.needsCaptcha());
		action.recordLoginFailure();
		assertFalse(action.needsCaptcha());
		action.recordLoginFailure();
		assertFalse(action.needsCaptcha());
		assertEquals(2, action.getFailureCount());
		
		action.recordLoginFailure();
		assertTrue(action.needsCaptcha());
		assertEquals(3, action.getFailureCount());
		
		action.resetFailures();
		assertFalse(action.needsCaptcha());
		assertEquals(0, action.getFailureCount());
	}
	
	public void testSetCaptcha() throws Exception {
		action.setCaptcha(true);
		assertEquals(true, action.isValidForLogin());
	}
	
	public void testEvilFactory() throws Exception {
		action = new LoginFailureAction(evil, "192.168.1.1");
		
		assertEquals(false, action.needsCaptcha());
		
		assertEquals(0, action.getFailureCount());
	}

}
