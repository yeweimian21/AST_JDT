package edu.ncsu.csc.itrust.unit.validate.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.forms.EditOfficeVisitForm;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.EditOfficeVisitValidator;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

/**
 * Test office visit validator
 *
 */
public class EditOfficeVisitValidatorTest extends TestCase {
	public void testEditOfficeVisitAllCorrect() throws Exception {
		EditOfficeVisitForm form = new EditOfficeVisitForm();
		form.setHcpID("99");
		form.setHospitalID("9840");
		form.setNotes("");
		form.setPatientID("309");
		form.setVisitDate("09/09/1982");
		new EditOfficeVisitValidator().validate(form);
	}

	/**
	 * test errors made on patients
	 * @throws Exception
	 */
	public void testPatientAllErrors() throws Exception {
		EditOfficeVisitForm form = new EditOfficeVisitForm();
		form.setHcpID("99L");
		form.setHospitalID("-9840");
		form.setNotes("Some fun notes**");
		form.setPatientID("a309");
		form.setVisitDate("09.09.1982");
		try {
			new EditOfficeVisitValidator().validate(form);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("HCP ID: " + ValidationFormat.MID.getDescription(), e.getErrorList().get(0));
			assertEquals("Hospital ID: " + ValidationFormat.HOSPITAL_ID.getDescription(), e.getErrorList().get(1));
			assertEquals("Notes: " + ValidationFormat.NOTES.getDescription(), e.getErrorList().get(2));
			assertEquals("Patient ID: " + ValidationFormat.MID.getDescription(), e.getErrorList().get(3));
			assertEquals("Visit Date: " + ValidationFormat.DATE.getDescription(), e.getErrorList().get(4));
			assertEquals("number of errors", 5, e.getErrorList().size());
		}
	}
}
