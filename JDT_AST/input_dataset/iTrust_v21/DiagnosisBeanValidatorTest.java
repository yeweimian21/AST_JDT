package edu.ncsu.csc.itrust.unit.validate.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.BeanValidator;
import edu.ncsu.csc.itrust.validate.DiagnosisBeanValidator;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class DiagnosisBeanValidatorTest extends TestCase {
	private BeanValidator<DiagnosisBean> validator = new DiagnosisBeanValidator();

	public void testAllCorrect() throws Exception {
		DiagnosisBean d = new DiagnosisBean();
		d.setDescription("A description");
		d.setICDCode("8.84");
		d.setOvDiagnosisID(0L);
		validator.validate(d);
	}

	public void testPatientAllErrors() throws Exception {
		DiagnosisBean d = new DiagnosisBean();
		d.setDescription("An description!");
		d.setICDCode("8000.84");
		try {
			validator.validate(d);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("ICD9CM Code: " + ValidationFormat.ICD9CM.getDescription(), e.getErrorList().get(0));
			assertEquals("Description: " + ValidationFormat.ICD_CODE_DESCRIPTION.getDescription(), e.getErrorList().get(1));
			assertEquals("number of errors", 2, e.getErrorList().size());
		}
	}
	public void testDiagnosisNoICDCode() throws Exception {
		DiagnosisBean d = new DiagnosisBean();
		d.setDescription("Good description");
		try {
			validator.validate(d);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("ICD9CM Code: " + ValidationFormat.ICD9CM.getDescription(), e.getErrorList().get(0));
			assertEquals("number of errors", 1, e.getErrorList().size());
		}
	}
}
