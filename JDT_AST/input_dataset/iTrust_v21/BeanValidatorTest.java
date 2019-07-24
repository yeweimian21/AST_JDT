package edu.ncsu.csc.itrust.unit.validate;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.enums.Gender;
import edu.ncsu.csc.itrust.unit.testutils.ValidatorProxy;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class BeanValidatorTest extends TestCase {
	private ValidatorProxy validatorProxy = new ValidatorProxy();

	public void testCheckIsNullable() throws Exception {
		String value = null;
		String errorMessage = "";
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, ValidationFormat.NAME, true));
	}

	public void testCheckIsNullableEmpty() throws Exception {
		String value = "";
		String errorMessage = "";
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, ValidationFormat.NAME, true));
	}

	public void testCheckLongValues() throws Exception {
		Long value = 80L;
		String errorMessage = "";
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, ValidationFormat.MID, true));
	}

	public void testProxyIsOnlyProxy() throws Exception {
		try {
			validatorProxy.validate(null);
			fail("exception should have been thrown");
		} catch (IllegalStateException e) {
			assertEquals("Mock object acts as a proxy to protected BeanValidator classes. Do not call this method", e
					.getMessage());
		}
	}
	
	public void testCheckGender() {
		assertEquals("", validatorProxy.checkGender("Gender", Gender.Male, ValidationFormat.GENDERCOD, true));
	}
	
	public void testCheckGenderNull() {
		assertEquals("", validatorProxy.checkGender("Gender", null, ValidationFormat.GENDERCOD, true));
	}
	
	/**
	 * Test method to see if colon is accepted in notes.
	 */
	public void testCheckFormatStringStringValidationFormatBoolean() {
		assertEquals("", validatorProxy.checkFormat("Notes", "Updated Notes:", ValidationFormat.NOTES, true));
	}
	
	public void testDoubleValues() throws Exception {
		assertEquals("Test must be a decimal in [1.0,2.0)", validatorProxy.checkDouble("Test", "0", 1L, 2L));
	}
	
	public void testCheckInt() {
		assertEquals("", validatorProxy.checkInt("null", null, 0, 1, true));
	}
	
	public void testCheckDouble() {
		assertEquals("", validatorProxy.checkDouble("double", "1.5", 1, 2));
		assertEquals("double must be a decimal in [1.0,2.0)", validatorProxy.checkDouble("double", "bad", 1, 2));
	}
}
