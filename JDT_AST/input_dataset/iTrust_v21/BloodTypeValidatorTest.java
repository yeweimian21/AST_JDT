package edu.ncsu.csc.itrust.unit.validate.regex;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.unit.testutils.ValidatorProxy;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class BloodTypeValidatorTest extends TestCase {
	private static final ValidationFormat VALIDATION_FORMAT = ValidationFormat.BLOODTYPE;
	private ValidatorProxy validatorProxy = new ValidatorProxy();
	private static final String FAILED = "Name: " + VALIDATION_FORMAT.getDescription();
	private static final String PASSED = "";

	public void testO() throws Exception {
		String value = "O-";
		String errorMessage = PASSED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}
	public void testAB() throws Exception {
		String value = "AB-";
		String errorMessage = PASSED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}
	
	public void testA() throws Exception {
		String value = "A+";
		String errorMessage = PASSED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}
	
	public void testB() throws Exception {
		String value = "B+";
		String errorMessage = PASSED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}

	public void testBadLetter() throws Exception {
		String value = "a";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}
	
	public void testNoNegative() throws Exception {
		String value = "O";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}


}
