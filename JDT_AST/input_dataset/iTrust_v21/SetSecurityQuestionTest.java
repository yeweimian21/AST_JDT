package edu.ncsu.csc.itrust.unit.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.SetSecurityQuestionAction;
import edu.ncsu.csc.itrust.beans.SecurityQA;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class SetSecurityQuestionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DAOFactory evil = EvilDAOFactory.getEvilInstance();
	private TestDataGenerator gen;
	private SetSecurityQuestionAction action;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
	}

	public void testNotUserID() throws Exception {
		try {
			action = new SetSecurityQuestionAction(factory, 500L);
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("MID 500 is not a user!", e.getMessage());
		}
	}

	public void testBadConnection() throws Exception {
		gen.patient2();
		try {
			action = new SetSecurityQuestionAction(evil, 2L);
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getExtendedMessage());
		}
	}

	public void testRetriveInformation() throws Exception {
		gen.patient2();
		action = new SetSecurityQuestionAction(factory, 2L);
		SecurityQA qa = action.retrieveInformation();
		assertEquals("how you doin?", qa.getQuestion());
		assertEquals("good", qa.getAnswer());
	}

	public void testUpdateInformationCorrectly() throws Exception {
		gen.patient2();
		action = new SetSecurityQuestionAction(factory, 2L);
		SecurityQA qa = action.retrieveInformation();
		qa.setAnswer("12345678");
		qa.setConfirmAnswer("12345678");
		qa.setQuestion("12345678");
		action.updateInformation(qa);
		qa = action.retrieveInformation();
		assertEquals("12345678", qa.getAnswer());
		assertEquals("12345678", qa.getQuestion());
	}
}
