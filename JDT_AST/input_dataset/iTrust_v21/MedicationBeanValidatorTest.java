package edu.ncsu.csc.itrust.unit.validate.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.MedicationBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.BeanValidator;
import edu.ncsu.csc.itrust.validate.MedicationBeanValidator;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class MedicationBeanValidatorTest extends TestCase {
	private BeanValidator<MedicationBean> validator = new MedicationBeanValidator();

	public void testAllCorrect() throws Exception {
		MedicationBean d = new MedicationBean();
		d.setDescription("A description");
		d.setNDCode("52563");
		validator.validate(d);
	}

	public void testPatientAllErrors() throws Exception {
		MedicationBean d = new MedicationBean();
		d.setDescription("An description!");
		d.setNDCode("-1");
		try {
			validator.validate(d);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("ND Code: " + ValidationFormat.ND.getDescription(), e.getErrorList().get(0));
			assertEquals("Description: " + ValidationFormat.ND_CODE_DESCRIPTION.getDescription(), e.getErrorList().get(1));
			assertEquals("number of errors", 2, e.getErrorList().size());
		}
	}
}
