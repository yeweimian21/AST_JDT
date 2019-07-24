package edu.ncsu.csc.itrust.unit.validate.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.AddPatientValidator;
import edu.ncsu.csc.itrust.validate.MailValidator;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class AddPatientValidatorTest extends TestCase {
	
	
	public void testPatientAllCorrect() throws Exception {
		PatientBean p = new PatientBean();
		p.setFirstName("Person'a");
		p.setLastName("LastName");
		p.setEmail("andy.programmer@gmail.com");
		new AddPatientValidator().validate(p);
	}

	public void testPatientAllErrors() throws Exception {
		MailValidator val = new MailValidator();
		PatientBean p = new PatientBean();
		p.setFirstName("Person5");
		p.setLastName("LastName5");
		p.setEmail("andy.programmer?gmail.com");
		try {
			new AddPatientValidator().validate(p);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("First name: " + ValidationFormat.NAME.getDescription(), e.getErrorList().get(0));
			assertEquals("Last name: " + ValidationFormat.NAME.getDescription(), e.getErrorList().get(1));
			assertEquals(false, val.validateEmail("andy.programmer?gmail.com"));
			assertEquals("number of errors", 3, e.getErrorList().size());
		}
	}
}
