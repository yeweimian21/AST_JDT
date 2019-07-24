package edu.ncsu.csc.itrust.unit.validate.regex;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.unit.testutils.ValidatorProxy;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class MIDValidatorTest extends TestCase {
	private ValidatorProxy validatorProxy = new ValidatorProxy();
	private static final String FAILED = "Name: " + ValidationFormat.MID.getDescription();
	private static final String PASSED = "";

	public void testMIDGood() throws Exception {
		String value = "9000000000";
		String errorMessage = PASSED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, ValidationFormat.MID, false));
	}

	public void testMIDLetter() throws Exception {
		String value = "a";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, ValidationFormat.MID, false));
	}

	public void testMIDLength() throws Exception {
		String value = "12345678901";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, ValidationFormat.MID, false));
	}
	
	public void testMIDNegative() throws Exception {
		Long value = -1L;
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, ValidationFormat.MID, false));
	}
}
