package edu.ncsu.csc.itrust.unit.validate.bean;

import edu.ncsu.csc.itrust.beans.ReferralBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.ReferralBeanValidator;
import junit.framework.TestCase;

public class ReferralBeanValidatorTest extends TestCase {
	ReferralBeanValidator validator = new ReferralBeanValidator();
	ReferralBean bean;
	
	@Override
	protected void setUp() {
		// Initialize a bean to valid values.
		bean = new ReferralBean();
		bean.setOvid(1L);
		bean.setPatientID(1L);
		bean.setPriority(1);
		bean.setReceiverID(1L);
		bean.setSenderID(1L);
		bean.setReferralDetails("details");
		bean.setTimeStamp("");
	}
	
	private String longString(int n) {
		StringBuilder builder = new StringBuilder();
		for (int i=0; i<n; i++) {
			builder.append('x');
		}
		return builder.toString();
	}
	
	public void testValidateOk() {
		// Ensure the bean used throughout this test case really is valid.
		try {
			validator.validate(bean);
		} catch (FormValidationException e) {
			fail("Validation should have passed.");
		}
	}
	
	public void testValidateGoodNotes() {
		bean.setReferralDetails("x"); // shortest possible entry
		try {
			validator.validate(bean);
		} catch (FormValidationException e) {
			fail("Validation should have passed.");
		}
		
		//  a-zA-Z0-9\\s'\"?!:;\\-.,_\n\t()\\\\/
		bean.setReferralDetails(";?-'.:,!/ \n"); // special legal characters
		try {
			validator.validate(bean);
		} catch (FormValidationException e) {
			fail("Validation should have passed.");
		}
		
		bean.setReferralDetails(longString(500)); // longest possible entry
		try {
			validator.validate(bean);
		} catch (FormValidationException e) {
			fail("Validation should have passed.");
		}
	}
	
	public void testValidateBadNotes() {
		bean.setReferralDetails("");  // empty
		try {
			validator.validate(bean);
			fail("Expected a validation failure.");
		} catch (FormValidationException e) {
			// passed.
		}
		
		bean.setReferralDetails("@");  // bad symbol
		try {
			validator.validate(bean);
			fail("Expected a validation failure.");
		} catch (FormValidationException e) {
			// passed.
		}
		
		bean.setReferralDetails(">");  // bad symbol
		try {
			validator.validate(bean);
			fail("Expected a validation failure.");
		} catch (FormValidationException e) {
			// passed.
		}
		
		bean.setReferralDetails(longString(501));  // string too long
		try {
			validator.validate(bean);
			fail("Expected a validation failure.");
		} catch (FormValidationException e) {
			// passed.
		}
	}

}
