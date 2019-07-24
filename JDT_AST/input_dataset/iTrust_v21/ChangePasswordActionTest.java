package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.ChangePasswordAction;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

@SuppressWarnings("unused")
public class ChangePasswordActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private ChangePasswordAction action;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		action = new ChangePasswordAction(factory);
	}

	public void testChangePassword() throws Exception {
		String response1 = action.changePassword(1L, "pw", "pass1", "pass1");
		String response2 = action.changePassword(1L, "pass1", "pw1", "pw1");
		String response3 = action.changePassword(1L, "pass1", "password", "password");
		String response4 = action.changePassword(1L, "shallnotpass", "pass1", "pass1");
		
		assertTrue(response1.contains("Password Changed"));
		assertTrue(response2.contains("Invalid password"));
		assertTrue(response3.contains("Invalid password"));
		assertTrue(response4.contains("Invalid password"));
	}

}
