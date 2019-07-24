package edu.ncsu.csc.itrust.unit.validate.bean;

import edu.ncsu.csc.itrust.beans.PatientInstructionsBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.PatientInstructionsBeanValidator;
import junit.framework.TestCase;

public class PatientInstructionsBeanValidatorTest extends TestCase {
	PatientInstructionsBeanValidator validator = new PatientInstructionsBeanValidator();
	PatientInstructionsBean bean;
	
	@Override
	protected void setUp() {
		// Initialize a bean to valid values.
		bean = new PatientInstructionsBean();
		bean.setName("name");
		bean.setComment("comment");
		bean.setUrl("http://www.example.com/");
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
	
	public void testValidateGoodName() {
		bean.setName("x"); // shortest possible entry
		try {
			validator.validate(bean);
		} catch (FormValidationException e) {
			fail("Validation should have passed.");
		}
		
		bean.setName("#;?-'.:,!/ \n"); // special legal characters
		try {
			validator.validate(bean);
		} catch (FormValidationException e) {
			fail("Validation should have passed.");
		}
		
		bean.setName(longString(100)); // longest possible entry
		try {
			validator.validate(bean);
		} catch (FormValidationException e) {
			fail("Validation should have passed.");
		}
	}
	
	public void testValidateBadName() {
		bean.setName("");  // empty
		try {
			validator.validate(bean);
			fail("Expected a validation failure.");
		} catch (FormValidationException e) {
			// passed.
		}
		
		bean.setName("@");  // bad symbol
		try {
			validator.validate(bean);
			fail("Expected a validation failure.");
		} catch (FormValidationException e) {
			// passed.
		}
		
		bean.setName(">");  // bad symbol
		try {
			validator.validate(bean);
			fail("Expected a validation failure.");
		} catch (FormValidationException e) {
			// passed.
		}
		
		bean.setName(longString(101));  // string too long
		try {
			validator.validate(bean);
			fail("Expected a validation failure.");
		} catch (FormValidationException e) {
			// passed.
		}
	}

	
	public void testValidateGoodComment() {
		bean.setComment("x"); // shortest possible entry
		try {
			validator.validate(bean);
		} catch (FormValidationException e) {
			fail("Validation should have passed.");
		}
		
		bean.setComment("#;?-'.:,!/ \n"); // special legal characters
		try {
			validator.validate(bean);
		} catch (FormValidationException e) {
			fail("Validation should have passed.");
		}
		
		bean.setComment(longString(500)); // longest possible entry
		try {
			validator.validate(bean);
		} catch (FormValidationException e) {
			fail("Validation should have passed.");
		}
	}
	
	public void testValidateBadComment() {
		bean.setComment("");  // empty
		try {
			validator.validate(bean);
			fail("Expected a validation failure.");
		} catch (FormValidationException e) {
			// passed.
		}

		bean.setComment("@");  // bad symbol
		try {
			validator.validate(bean);
			fail("Expected a validation failure.");
		} catch (FormValidationException e) {
			// passed.
		}
		
		bean.setComment(">");  // bad symbol
		try {
			validator.validate(bean);
			fail("Expected a validation failure.");
		} catch (FormValidationException e) {
			// passed.
		}
		
		bean.setComment(longString(501));  // string too long
		try {
			validator.validate(bean);
			fail("Expected a validation failure.");
		} catch (FormValidationException e) {
			// passed.
		}
	}
	
	
	public void testValidateGoodURL() {
		bean.setUrl("http://www.example.com");
		try {
			validator.validate(bean);
		} catch (FormValidationException e) {
			fail("Validation should have passed.");
		}
		
		bean.setUrl("http://example.com");
		try {
			validator.validate(bean);
		} catch (FormValidationException e) {
			fail("Validation should have passed.");
		}
		
		bean.setUrl("http://www.example-example.com/page.html?arg=15");
		try {
			validator.validate(bean);
		} catch (FormValidationException e) {
			fail("Validation should have passed.");
		}
	}
	
	public void testValidateBadURL() {
		bean.setUrl("");  // empty
		try {
			validator.validate(bean);
			fail("Expected a validation failure.");
		} catch (FormValidationException e) {
			// passed.
		}

		bean.setUrl("123");  // not a url
		try {
			validator.validate(bean);
			fail("Expected a validation failure.");
		} catch (FormValidationException e) {
			// passed.
		}

		bean.setUrl("http://www example com");  // no spaces in url
		try {
			validator.validate(bean);
			fail("Expected a validation failure.");
		} catch (FormValidationException e) {
			// passed.
		}

		bean.setUrl("http://");  // not a url
		try {
			validator.validate(bean);
			fail("Expected a validation failure.");
		} catch (FormValidationException e) {
			// passed.
		}
	}
}
