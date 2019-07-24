package edu.ncsu.csc.itrust.unit.validate.regex;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.unit.testutils.ValidatorProxy;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class PasswordValidatorTest extends TestCase {
	private ValidatorProxy validatorProxy = new ValidatorProxy();
	private static final ValidationFormat VALIDATION_FORMAT = ValidationFormat.PASSWORD;
	private static final String PASSED = "";
	private static final String FAILED = "Name: " + VALIDATION_FORMAT.getDescription();

	public void testGood() throws Exception {
		String value = "12345678901234567890";
		String errorMessage = PASSED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}

	public void testTooLong() throws Exception {
		String value = "123456789012345678901";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}

	public void testWithSpace() throws Exception {
		String value = "12345678 ";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}
}
