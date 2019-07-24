package edu.ncsu.csc.itrust.unit.validate.regex;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.unit.testutils.ValidatorProxy;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class PhoneValidatorTest extends TestCase {
	private ValidatorProxy validatorProxy = new ValidatorProxy();
	private static final String FAILED = "Name: " + ValidationFormat.PHONE_NUMBER.getDescription();
	private static final String PASSED = "";


	public void testPhoneGood() throws Exception {
		String value = "012-345-6789";
		String errorMessage = PASSED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, ValidationFormat.PHONE_NUMBER, false));
	}

	public void testPhoneWithLetter() throws Exception {
		String value = "O12-345-6789";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, ValidationFormat.PHONE_NUMBER, false));
	}

	public void testPhoneBadLength() throws Exception {
		String value = "012-345-67890";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, ValidationFormat.PHONE_NUMBER, false));
	}

}
