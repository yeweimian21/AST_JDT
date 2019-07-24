package edu.ncsu.csc.itrust.unit.validate;

import edu.ncsu.csc.itrust.validate.MailValidator;
import junit.framework.TestCase;

public class TestMailValidator extends TestCase {
	
	private MailValidator val = new MailValidator();

	@Override
	public void setUp() throws Exception {
	}
	
	public void testValidateEmail() throws Exception {
		String value = "google@google.com";
		assertEquals(true, val.validateEmail(value));
		String value2 = "google   google";
		assertEquals(false, val.validateEmail(value2));
		String value3 = "google?google.com";
		assertEquals(false, val.validateEmail(value3));
		String value4 = "googlegooglegooglegoogle@google.com";
		assertEquals(true, val.validateEmail(value4));
	}

	@Override
	public void tearDown() throws Exception {
	}

}
