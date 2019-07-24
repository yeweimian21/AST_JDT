package edu.ncsu.csc.itrust.unit.validate.regex;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.unit.testutils.ValidatorProxy;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class InsuranceIDValidatorTest extends TestCase {
	private static final String PASSED = "";
	private ValidatorProxy validatorProxy = new ValidatorProxy();
	private static final String FAILED = "Name: " + ValidationFormat.INSURANCE_ID.getDescription();

	public void testNameCheckTooLong() throws Exception {
		String value = "This-Name-Is-Too-Long ";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, ValidationFormat.INSURANCE_ID, false));
	}

	public void testNameGood() throws Exception {
		String value = "NameIsGood-'";
		String errorMessage = PASSED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, ValidationFormat.INSURANCE_ID, false));
	}

	
	public void testIDWithNumber() throws Exception {
		String value = "NameIs1337";
		String errorMessage = PASSED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, ValidationFormat.INSURANCE_ID, false));
	}
	public void testNameLotsOfBadStuff() throws Exception {
		String value = "Bad!@#$%^&*()?.:;";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, ValidationFormat.INSURANCE_ID, false));
	}
}
