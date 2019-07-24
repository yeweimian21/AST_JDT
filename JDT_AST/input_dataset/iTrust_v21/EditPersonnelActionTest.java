package edu.ncsu.csc.itrust.unit.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.EditPersonnelAction;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class EditPersonnelActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditPersonnelAction personnelEditor;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
	}

	public void testNotAuthorized() throws Exception {
		gen.standardData();
		try {
			personnelEditor = new EditPersonnelAction(factory, 9000000000L, "9000000003");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("You can only edit your own demographics!", e.getMessage());
		}
	}
	
	public void testNotAuthorized2() throws Exception {
		gen.standardData();
		try {
			personnelEditor = new EditPersonnelAction(factory, 9000000000L, "9000000001");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("You are not authorized to edit this record!", e.getMessage());
		}
	}
	
	public void testNonExistent() throws Exception {
		try {
			personnelEditor = new EditPersonnelAction(factory, 0L, "8999999999");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("Personnel does not exist", e.getMessage());
		}
	}

	public void testWrongFormat() throws Exception {
		try {
			gen.hcp0();
			personnelEditor = new EditPersonnelAction(factory, 0L, "hello!");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("Personnel ID is not a number: For input string: \"hello!\"", e.getMessage());
		}
	}

	public void testNull() throws Exception {
		try {
			gen.hcp0();
			personnelEditor = new EditPersonnelAction(factory, 0L, null);
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("Personnel ID is not a number: null", e.getMessage());
		}
	}
	
	public void testUpdateInformation() throws Exception {
		gen.uap1();
		personnelEditor = new EditPersonnelAction(factory, 8000000009L, "8000000009");
		PersonnelBean j = factory.getPersonnelDAO().getPersonnel(8000000009l);
		j.setStreetAddress2("second line");
		personnelEditor.updateInformation(j);
		j = factory.getPersonnelDAO().getPersonnel(8000000009l);
		assertEquals("second line", j.getStreetAddress2());
	}
	
	
}
