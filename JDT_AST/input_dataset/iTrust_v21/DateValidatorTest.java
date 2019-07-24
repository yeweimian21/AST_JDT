package edu.ncsu.csc.itrust.unit.validate.regex;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.unit.testutils.ValidatorProxy;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class DateValidatorTest extends TestCase {
	private ValidatorProxy validatorProxy = new ValidatorProxy();
	private static final String PASSED = "";
	private static final String FAILED = "Name: " + ValidationFormat.DATE.getDescription();

	public void testDateGood() throws Exception {
		String value = "05/19/1984";
		String errorMessage = PASSED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, ValidationFormat.DATE, false));
	}

	public void testDateWithSpace() throws Exception {
		String value = "05/19/1984 ";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, ValidationFormat.DATE, false));
	}
}
