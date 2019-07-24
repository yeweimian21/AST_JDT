package edu.ncsu.csc.itrust.unit.validate.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.forms.RecordsReleaseForm;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.RecordsReleaseFormValidator;

public class RecordsReleaseBeanValidatorTest extends TestCase {
	private RecordsReleaseFormValidator validator = new RecordsReleaseFormValidator();
	
	public void testAllCorrect() throws Exception {
		RecordsReleaseForm rr = new RecordsReleaseForm();
		rr.setReleaseHospitalID("1");
		rr.setRecipientHospitalName("New Hospital");
		rr.setRecipientHospitalAddress("123 Fake Street");
		rr.setRecipientFirstName("BillyBob");
		rr.setRecipientLastName("Joe");
		rr.setRecipientPhone("919-123-4567");
		rr.setRecipientEmail("bbjoe@yahoo.com");
		rr.setRequestJustification("Just for kicks n' giggles");
		rr.setDigitalSignature(true);
		validator.validate(rr);
	}
	
	public void testNoReleaseHospitalID(){
		RecordsReleaseForm rr = new RecordsReleaseForm();
		rr.setReleaseHospitalID("");
		rr.setRecipientHospitalName("New Hospital");
		rr.setRecipientHospitalAddress("123 Fake Street");
		rr.setRecipientFirstName("BillyBob");
		rr.setRecipientLastName("Joe");
		rr.setRecipientPhone("919-123-4567");
		rr.setRecipientEmail("bbjoe@yahoo.com");
		rr.setRequestJustification("Just for kicks n' giggles");
		rr.setDigitalSignature(true);
		try {
			validator.validate(rr);
			fail("Should have thrown exception");
		} catch (FormValidationException e) {
			//good
		}
	}
	
	public void testNoRecipientHospitalName(){
		RecordsReleaseForm rr = new RecordsReleaseForm();
		rr.setReleaseHospitalID("1");
		rr.setRecipientHospitalName("");
		rr.setRecipientHospitalAddress("123 Fake Street");
		rr.setRecipientFirstName("BillyBob");
		rr.setRecipientLastName("Joe");
		rr.setRecipientPhone("919-123-4567");
		rr.setRecipientEmail("bbjoe@yahoo.com");
		rr.setRequestJustification("Just for kicks n' giggles");
		rr.setDigitalSignature(true);
		try {
			validator.validate(rr);
			fail("Should have thrown exception");
		} catch (FormValidationException e) {
			//good
		}
	}
	
	public void testNoRecipientHospitalAddress(){
		RecordsReleaseForm rr = new RecordsReleaseForm();
		rr.setReleaseHospitalID("1");
		rr.setRecipientHospitalName("New Hospital");
		rr.setRecipientHospitalAddress("");
		rr.setRecipientFirstName("BillyBob");
		rr.setRecipientLastName("Joe");
		rr.setRecipientPhone("919-123-4567");
		rr.setRecipientEmail("bbjoe@yahoo.com");
		rr.setRequestJustification("Just for kicks n' giggles");
		rr.setDigitalSignature(true);
		try {
			validator.validate(rr);
			fail("Should have thrown exception");
		} catch (FormValidationException e) {
			//good
		}
	}
	
	public void testNoRecipientFirstName(){
		RecordsReleaseForm rr = new RecordsReleaseForm();
		rr.setReleaseHospitalID("1");
		rr.setRecipientHospitalName("New Hospital");
		rr.setRecipientHospitalAddress("123 Fake Street");
		rr.setRecipientFirstName("");
		rr.setRecipientLastName("Joe");
		rr.setRecipientPhone("919-123-4567");
		rr.setRecipientEmail("bbjoe@yahoo.com");
		rr.setRequestJustification("Just for kicks n' giggles");
		rr.setDigitalSignature(true);
		try {
			validator.validate(rr);
			fail("Should have thrown exception");
		} catch (FormValidationException e) {
			//good
		}
	}
	
	public void testNoRecipientLastName(){
		RecordsReleaseForm rr = new RecordsReleaseForm();
		rr.setReleaseHospitalID("1");
		rr.setRecipientHospitalName("New Hospital");
		rr.setRecipientHospitalAddress("123 Fake Street");
		rr.setRecipientFirstName("BillyBob");
		rr.setRecipientLastName("");
		rr.setRecipientPhone("919-123-4567");
		rr.setRecipientEmail("bbjoe@yahoo.com");
		rr.setRequestJustification("Just for kicks n' giggles");
		rr.setDigitalSignature(true);
		try {
			validator.validate(rr);
			fail("Should have thrown exception");
		} catch (FormValidationException e) {
			//good
		}
	}
	
	public void testNoRecipientPhone(){
		RecordsReleaseForm rr = new RecordsReleaseForm();
		rr.setReleaseHospitalID("1");
		rr.setRecipientHospitalName("New Hospital");
		rr.setRecipientHospitalAddress("123 Fake Street");
		rr.setRecipientFirstName("BillyBob");
		rr.setRecipientLastName("Joe");
		rr.setRecipientPhone("");
		rr.setRecipientEmail("bbjoe@yahoo.com");
		rr.setRequestJustification("Just for kicks n' giggles");
		rr.setDigitalSignature(true);
		try {
			validator.validate(rr);
			fail("Should have thrown exception");
		} catch (FormValidationException e) {
			//good
		}
	}
	
	public void testNoRecipientEmail(){
		RecordsReleaseForm rr = new RecordsReleaseForm();
		rr.setReleaseHospitalID("1");
		rr.setRecipientHospitalName("New Hospital");
		rr.setRecipientHospitalAddress("123 Fake Street");
		rr.setRecipientFirstName("BillyBob");
		rr.setRecipientLastName("Joe");
		rr.setRecipientPhone("919-123-4567");
		rr.setRecipientEmail("");
		rr.setRequestJustification("Just for kicks n' giggles");
		rr.setDigitalSignature(true);
		try {
			validator.validate(rr);
			fail("Should have thrown exception");
		} catch (FormValidationException e) {
			//good
		}
	}
		
	public void testNoRequestJustification() throws Exception {
		RecordsReleaseForm rr = new RecordsReleaseForm();
		rr.setReleaseHospitalID("1");
		rr.setRecipientHospitalName("New Hospital");
		rr.setRecipientHospitalAddress("123 Fake Street");
		rr.setRecipientFirstName("BillyBob");
		rr.setRecipientLastName("Joe");
		rr.setRecipientPhone("919-123-4567");
		rr.setRecipientEmail("bbjoe@yahoo.com");
		rr.setRequestJustification("");
		rr.setDigitalSignature(true);
		validator.validate(rr);
	}
	
	public void testFalseDigitalSignature() throws Exception {
		RecordsReleaseForm rr = new RecordsReleaseForm();
		rr.setReleaseHospitalID("1");
		rr.setRecipientHospitalName("New Hospital");
		rr.setRecipientHospitalAddress("123 Fake Street");
		rr.setRecipientFirstName("BillyBob");
		rr.setRecipientLastName("Joe");
		rr.setRecipientPhone("919-123-4567");
		rr.setRecipientEmail("bbjoe@yahoo.com");
		rr.setRequestJustification("");
		rr.setDigitalSignature(false);
		validator.validate(rr);
	}
	
	public void testNotNumbers() throws Exception {
		try {
			RecordsReleaseForm rr = new RecordsReleaseForm();
			rr.setReleaseHospitalID("a");
			rr.setRecipientHospitalName(null);
			rr.setRecipientHospitalAddress("!");
			rr.setRecipientFirstName("~b");
			rr.setRecipientLastName("'s");
			rr.setRecipientPhone("2342v");
			rr.setRecipientEmail("rf543");
			rr.setRequestJustification(null);
			rr.setDigitalSignature(false);
			validator.validate(rr);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("Release Hospital ID: Between 1 and 10 digits", e.getErrorList().get(0));
			assertEquals("Recipient hospital name: Between 1 and 30 alphanumerics, space, ', and .", e.getErrorList().get(1));
			assertEquals("Recipient hospital address: Up to 100 alphanumeric characters, comma, and .", e.getErrorList().get(2));
			assertEquals("Doctor's first name: Up to 20 Letters, space, ' and -", e.getErrorList().get(3));
			assertEquals("Doctor's phone number: xxx-xxx-xxxx", e.getErrorList().get(4));
			assertEquals("Doctor's email address: Up to 30 alphanumeric characters and symbols . and _ @", e.getErrorList().get(5));
			assertEquals(6, e.getErrorList().size());
		}
	}
	
}