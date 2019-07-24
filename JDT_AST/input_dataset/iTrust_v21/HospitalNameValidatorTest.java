package edu.ncsu.csc.itrust.unit.validate.regex;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.unit.testutils.ValidatorProxy;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

/**
 * HospitalNameValidator
 */
public class HospitalNameValidatorTest extends TestCase {
	private ValidatorProxy validatorProxy = new ValidatorProxy();
	private static final ValidationFormat VALIDATION_FORMAT = ValidationFormat.HOSPITAL_NAME;
	private static final String PASSED = "";
	private static final String FAILED = "Hospital Name: " + VALIDATION_FORMAT.getDescription();

	/**
	 * testHospitalNameGood
	 * @throws Exception
	 */
	public void testHospitalNameGood() throws Exception {
		String value = "90A very long Hospital Name's.";
		String errorMessage = PASSED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Hospital Name", value, VALIDATION_FORMAT, false));
	}

	/**
	 * testHospitalNameTooLong
	 * @throws Exception
	 */
	public void testHospitalNameTooLong() throws Exception {
		String chunkOfTen = "1234567890";
		String value = "a";
		for (int i = 0; i < 3; i++) {
			value += chunkOfTen; // shamelessly borrowed from Notes Test
		}
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Hospital Name", value, VALIDATION_FORMAT, false));
	}
	
	/**
	 * testHospitalNameBadChars
	 */
	public void testHospitalNameBadChars() {
		String hName = "9 A very long Hospital Name's!";
		assertEquals(FAILED, validatorProxy.checkFormat("Hospital Name", hName, VALIDATION_FORMAT, false));
	}
	
	/**
	 * testHospitalNameTooShort
	 */
	public void testHospitalNameTooShort() {
		String hName = "";
		assertEquals(FAILED, validatorProxy.checkFormat("Hospital Name", hName, VALIDATION_FORMAT, false));
	}
}
