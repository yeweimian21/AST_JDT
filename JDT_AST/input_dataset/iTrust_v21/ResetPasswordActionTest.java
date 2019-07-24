package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.ResetPasswordAction;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FakeEmailDAO;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class ResetPasswordActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DAOFactory evil = EvilDAOFactory.getEvilInstance();
	private FakeEmailDAO feDAO = factory.getFakeEmailDAO();
	private ResetPasswordAction action;
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		action = new ResetPasswordAction(factory);
	}

	public void testCheckMID() throws Exception {
		gen.patient1();
		gen.hcp0();
		assertEquals("empty", 0, action.checkMID(""));
		assertEquals("null", 0, action.checkMID(null));
		assertEquals("not a number", 0, action.checkMID("a"));
		assertEquals("non-existant", 0, action.checkMID("200"));
		assertEquals("existant", 1, action.checkMID("1"));
		assertEquals("existant", 9000000000L, action.checkMID("9000000000"));
	}

	public void testCheckMIDEvil() throws Exception {
		action = new ResetPasswordAction(evil);
		assertEquals(0l, action.checkMID(""));
	}

	public void testCheckMIDEvil2() throws Exception {
		action = new ResetPasswordAction(evil);
		assertEquals(0l, action.checkMID("a"));
	}

	public void testCheckRole() throws Exception {
		gen.patient2();
		gen.hcp0();
		gen.uap1();
		assertEquals("patient", action.checkRole(2L, "patient"));
		assertEquals("hcp", action.checkRole(9000000000L, "hcp"));
		assertEquals("uap", action.checkRole(8000000009L, "uap"));
		assertEquals(null, action.checkRole(0L, "admin"));
		assertEquals(null, action.checkRole(0L, "HCP"));
	}

	public void testCheckWrongRole() {
		try {
			action.checkRole(9000000000L, "patient");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("User does not exist with the designated role", e.getMessage());
		}
	}

	public void testCheckAnswerNull() throws Exception {
		assertEquals("empty", null, action.checkAnswerNull(""));
		assertEquals("null", null, action.checkAnswerNull(null));
		assertEquals("answer", action.checkAnswerNull("answer"));
	}

	public void testGetSecurityQuestion() throws Exception {
		gen.patient1();
		gen.hcp0();
		assertEquals("what is your favorite color?", action.getSecurityQuestion(1l));
		assertEquals("first letter?", action.getSecurityQuestion(9000000000L));
		assertEquals("first letter?", action.getSecurityQuestion(9000000000L));
	}

	public void testGetSecurityQuestionEvil() throws Exception {
		action = new ResetPasswordAction(EvilDAOFactory.getEvilInstance());
		assertEquals("", action.getSecurityQuestion(1l));

	}

	public void testResetPassword() throws Exception {
		gen.patient1();
		gen.hcp0();
		assertEquals("Answer did not match", action.resetPassword(1L, "patient", "wrong", "12345678",
				"12345678", "127.0.0.1"));
		assertEquals("Answer did not match", action.resetPassword(9000000000L, "hcp", "wrong", "12345678",
				"12345678", "127.0.0.1"));
		assertEquals("Invalid role", action.resetPassword(9000000000L, "a", "a", "12345678", "12345678",
				"127.0.0.1"));

		assertEquals("Password changed", action.resetPassword(1L, "patient", "blue", "12345678", "12345678",
				"127.0.0.1"));
		List<Email> list = feDAO.getAllEmails();
		assertTrue(list.get(0).getBody().contains("Dear Random Person,"));
		assertTrue(list.get(0).getBody().contains("You have chosen to change your iTrust password for user 1"));

		assertEquals("Role mismatch", action.resetPassword(9000000000L, "uap", "a", "12345678", "12345678",
				"127.0.0.1"));
	}

	public void testResetForHCP() throws Exception {
		gen.hcp0();
		assertEquals("Password changed", action.resetPassword(9000000000L, "hcp", "a", "12345678",
				"12345678", "127.0.0.1"));
		List<Email> list = feDAO.getAllEmails();
		assertTrue(list.get(0).getBody().contains("Dear Kelly Doctor,"));
		assertTrue(list.get(0).getBody().contains("You have chosen to change your iTrust password for user 9000000000"));
	}

	public void testValidatePasswordNull() throws Exception {
		gen.patient1();
		try {
			action.resetPassword(1L, "patient", "blue", null, "12345678", "127.0.0.1");
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("Password cannot be empty", e.getErrorList().get(0));
			assertEquals(1, e.getErrorList().size());
		}
	}

	public void testValidatePasswordEmpty() throws Exception {
		gen.patient1();
		try {
			action.resetPassword(1L, "patient", "blue", "", "12345678", "127.0.0.1");
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("Password cannot be empty", e.getErrorList().get(0));
			assertEquals(1, e.getErrorList().size());
		}
	}

	public void testValidatePasswordWrong() throws Exception {
		gen.patient1();
		try {
			action.resetPassword(1L, "patient", "blue", "1234567", "12345678", "127.0.0.1");
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("Passwords don't match", e.getErrorList().get(0));
			assertEquals("Password must be in the following format: "
					+ ValidationFormat.PASSWORD.getDescription(), e.getErrorList().get(1));
			assertEquals(2, e.getErrorList().size());
		}
	}
}
