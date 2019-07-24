package edu.ncsu.csc.itrust.unit.validate.regex;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.unit.testutils.ValidatorProxy;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class EmailValidatorTest extends TestCase {
	private ValidatorProxy validatorProxy = new ValidatorProxy();
	private static final ValidationFormat VALIDATION_FORMAT = ValidationFormat.EMAIL;
	private static final String PASSED = "";
	private static final String FAILED = "Name: " + ValidationFormat.EMAIL.getDescription();

	public void testGoodEmail() throws Exception {
		String value = "bob.person1@nc.rr.A.com";
		String errorMessage = PASSED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}
	
	public void testGoodEmailWithPlus() throws Exception {
		String value = "disposable.style.email.with+symbol@example.com";
		String errorMessage = PASSED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}

	public void testBadLength() throws Exception {
		String value = "1234567890123456789012345678901";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}
	
	public void testBadLetters() throws Exception {
		String value = "bob%";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}
	
	//Now legal according to http://tools.ietf.org/html/rfc2822
	public void testGoodFormat() throws Exception {
		String value = "---@---.com";
		String errorMessage = PASSED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, true));
	}
	
	//This is now legal should validate see above link
	public void testGoodFormatMultipleAtSymbol() throws Exception {
		String value = "A@b@c@example.com";
		String errorMessage = PASSED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, true));
	}
	
}
