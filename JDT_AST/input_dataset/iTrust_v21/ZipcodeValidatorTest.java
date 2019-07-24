package edu.ncsu.csc.itrust.unit.validate.regex;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.unit.testutils.ValidatorProxy;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class ZipcodeValidatorTest extends TestCase {
	private ValidatorProxy validatorProxy = new ValidatorProxy();
	private static final ValidationFormat VALIDATION_FORMAT = ValidationFormat.ZIPCODE;
	private static final String PASSED = "";
	private static final String FAILED = "Name: " + VALIDATION_FORMAT.getDescription();

	public void testGoodAnswer() throws Exception {
		String value = "27607";
		String errorMessage = PASSED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}
	
	public void testGoodAnswerTwoParts() throws Exception {
		String value = "27607-0901";
		String errorMessage = PASSED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}

	public void testBadLength() throws Exception {
		String value = "27607-";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}

	public void testBadLetters() throws Exception {
		String value = "xxxxx-xxxx";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}
}
