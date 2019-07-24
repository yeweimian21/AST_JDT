package edu.ncsu.csc.itrust.unit.validate.bean;

import edu.ncsu.csc.itrust.beans.AllergyBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.AllergyBeanValidator;
import edu.ncsu.csc.itrust.validate.ValidationFormat;
import junit.framework.TestCase;

public class AllergyBeanValidatorTest extends TestCase {

	public void testCorrectFormat() throws Exception {
		AllergyBean ab = new AllergyBean();
		ab.setDescription("Correct format");
		new AllergyBeanValidator().validate(ab);
	}

	public void testWrongFormat() throws Exception {
		AllergyBean ab = new AllergyBean();
		ab.setDescription(">");
		try {
			new AllergyBeanValidator().validate(ab);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals(1, e.getErrorList().size());
			assertEquals("Allergy Description: " + ValidationFormat.ALLERGY_DESCRIPTION.getDescription(), e
					.getErrorList().get(0));
		}
	}
}
