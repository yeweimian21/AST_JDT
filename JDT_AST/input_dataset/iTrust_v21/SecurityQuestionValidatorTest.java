package edu.ncsu.csc.itrust.unit.validate.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.SecurityQA;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.SecurityQAValidator;

public class SecurityQuestionValidatorTest extends TestCase {
	private SecurityQA qa = new SecurityQA();
	private SecurityQAValidator qav = new SecurityQAValidator();

	public void testCorrectFormat() throws Exception {
		qa.setAnswer("12345678");
		qa.setConfirmAnswer("12345678");
		qa.setQuestion("12345678");
		qav.validate(qa);
	}

	public void testNoMatch() throws Exception {
		qa.setAnswer("12345678");
		qa.setConfirmAnswer("123456789");
		qa.setQuestion("12345678");
		try {
			qav.validate(qa);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals(1, e.getErrorList().size());
			assertEquals("Security answers do not match", e.getErrorList().get(0));
		}
	}

	public void testMatchWrongFormat() throws Exception {
		qa.setAnswer(">");
		qa.setConfirmAnswer(">");
		qa.setQuestion("12345678");
		try {
			qav.validate(qa);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals(1, e.getErrorList().size());
			assertEquals("Security Answer: Up to 30 alphanumeric characters", e.getErrorList().get(0));
		}

	}

	public void testNullConfirm() throws Exception {
		qa.setAnswer("12345678");
		qa.setConfirmAnswer(null);
		qa.setQuestion("12345678");
		try {
			qav.validate(qa);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals(1, e.getErrorList().size());
			assertEquals("Confirm answer cannot be empty", e.getErrorList().get(0));
		}
	}

	public void testNullForm() throws Exception {
		try {
			qav.validate(null);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals(1, e.getErrorList().size());
			assertEquals("Null form", e.getErrorList().get(0));
		}
	}
}
