package edu.ncsu.csc.itrust.unit.validate.regex;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.unit.testutils.ValidatorProxy;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class HospitalIDValidatorTest extends TestCase {
	private ValidatorProxy validatorProxy = new ValidatorProxy();
	private static final String FAILED = "Hospital ID: " + ValidationFormat.HOSPITAL_ID.getDescription();
	private static final String PASSED = "";

	public void testHospitalIDGood() throws Exception {
		String value = "0000000000";
		String errorMessage = PASSED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Hospital ID", value, ValidationFormat.HOSPITAL_ID, false));
	}

	public void testHospitalIDLetter() throws Exception {
		String value = "a";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Hospital ID", value, ValidationFormat.HOSPITAL_ID, false));
	}
	
	public void testHospitalIDPunct() throws Exception {
		String value = ".";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Hospital ID", value, ValidationFormat.HOSPITAL_ID, false));
	}
	
	public void testHospitalIDLength() throws Exception {
		String value = "12345678901";
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Hospital ID", value, ValidationFormat.HOSPITAL_ID, false));
	}
	
	public void testHospitalIDNegative() throws Exception {
		Long value = -1L;
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Hospital ID", value, ValidationFormat.HOSPITAL_ID, false));
	}
}
