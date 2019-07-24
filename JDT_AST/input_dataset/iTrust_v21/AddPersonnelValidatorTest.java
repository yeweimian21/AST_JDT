package edu.ncsu.csc.itrust.unit.validate.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.AddPersonnelValidator;
import edu.ncsu.csc.itrust.validate.MailValidator;
import edu.ncsu.csc.itrust.validate.ValidationFormat;


public class AddPersonnelValidatorTest extends TestCase {
	public void testPatientAllCorrect() throws Exception {
		PersonnelBean p = new PersonnelBean();
		p.setFirstName("Person'a");
		p.setLastName("LastName");
		p.setEmail("andy.programmer@gmail.com");
		new AddPersonnelValidator().validate(p);
	}

	public void testPatientAllErrors() throws Exception {
		MailValidator val = new MailValidator();
		PersonnelBean p = new PersonnelBean();
		p.setFirstName("Person5");
		p.setLastName("LastName5");
		p.setEmail("andy.programmer?gmail.com");
		try {
			new AddPersonnelValidator().validate(p);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("First name: " + ValidationFormat.NAME.getDescription(), e.getErrorList().get(0));
			assertEquals("Last name: " + ValidationFormat.NAME.getDescription(), e.getErrorList().get(1));
			assertEquals(false, val.validateEmail("andy.programmer?gmail.com"));
			assertEquals("number of errors", 3, e.getErrorList().size());
		}
	}
}
