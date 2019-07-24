package edu.ncsu.csc.itrust.unit.validate.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.MailValidator;
import edu.ncsu.csc.itrust.validate.PatientValidator;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

/**
 * PatientValidatorTest
 */
public class PatientValidatorTest extends TestCase {
	
	MailValidator val = new MailValidator();
	
	/**
	 * testPatientAllCorrect
	 * @throws Exception
	 */
	public void testPatientAllCorrect() throws Exception {
		PatientBean p = new PatientBean();
		p.setFirstName("Person'a");
		p.setLastName("LastName");
		p.setDateOfBirthStr("10/10/2005");
		p.setDateOfDeathStr("");
		p.setCauseOfDeath("");
		p.setEmail("andy.programmer@gmail.com");
		p.setSecurityQuestion("'What is your quest?'-");
		p.setSecurityAnswer("I s33k the holy grail");
		p.setStreetAddress1("344 East Random Ave.");
		p.setStreetAddress2("");
		p.setCity("Intercourse");
		p.setState("PA");
		p.setZip("17534");
		p.setPhone("555-542-9023");
		p.setEmergencyName("Tow Mater");
		p.setEmergencyPhone("809-940-1943");
		p.setIcName("Dewie Cheatum n Howe");
		p.setIcAddress1("458 Ripoff Blvd.");
		p.setIcAddress2("Greedy Suite");
		p.setIcCity("Hell");
		p.setIcState("MI");
		p.setIcZip("48169-0000");
		p.setIcPhone("666-059-4023");
		p.setIcID("Money");
		p.setMotherMID("58");
		p.setFatherMID("0");
		p.setBloodTypeStr("O-");
		p.setEthnicityStr("Caucasian");
		p.setGenderStr("Male");
		p.setTopicalNotes("Here are some random topical notes. Isn't there more? Yes.\n There is.");
		p.setPassword("testpass1");
		p.setConfirmPassword("testpass1");
		new PatientValidator().validate(p);
	}
	
	/**
	 * testPatientAllErrors
	 * @throws Exception
	 */
	public void testPatientAllErrors() throws Exception {
		PatientBean p = new PatientBean();
		p.setFirstName("Person5");
		p.setLastName("LastName5");
		p.setDateOfBirthStr("10/ 10/2005");
		p.setDateOfDeathStr("05-19-1984");
		p.setCauseOfDeath("Q150");
		p.setEmail("andy.programmer?gmail.com");
		p.setStreetAddress1("344 East < Ave.");
		p.setStreetAddress2("?");
		p.setCity("Wr0ng");
		p.setState("Pa");
		p.setZip("17534-");
		p.setPhone("555");
		p.setEmergencyName("Tow #ater");
		p.setEmergencyPhone("(809)");
		p.setIcName("Dewie Che@tum and Howe the 2nd");
		p.setIcAddress1("458 Ripoff Blvd?");
		p.setIcAddress2("Greedy Suite                        ");
		p.setIcCity("%");
		p.setIcState("mI");
		p.setIcZip("48169-0000 ");
		p.setIcPhone(" 666-059-4023 ");
		p.setIcID("$$");
		p.setMotherMID("-1");
		p.setFatherMID("-2");
		p.setBloodTypeStr("AB");
		p.setEthnicityStr("Caucasion");
		p.setGenderStr("female");
		p.setTopicalNotes("<script>alert('hello');</script>");
		p.setPassword("toooooooooooooooooooooooooo long password");
		p.setPassword("toooooooooooooooooooooooooo long password");
		try {
			new PatientValidator().validate(p);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("First name: " + ValidationFormat.NAME.getDescription(), e.getErrorList().get(0));
			assertEquals("Last name: " + ValidationFormat.NAME.getDescription(), e.getErrorList().get(1));
			assertEquals("Date of Birth: MM/DD/YYYY", e.getErrorList().get(3));
			assertEquals("Date of Death: MM/DD/YYYY", e.getErrorList().get(4));
			assertEquals("Cause of Death cannot be specified without Date of Death!", e.getErrorList().get(5));
			assertEquals("Cause of Death: xxx.xx", e.getErrorList().get(6));
			assertEquals(false, val.validateEmail("andy.programmer?gmail.com"));
			assertEquals("Street Address 1: " + ValidationFormat.ADDRESS.getDescription(), e.getErrorList().get(7));
			assertEquals("Street Address 2: " + ValidationFormat.ADDRESS.getDescription(), e.getErrorList().get(8));
			assertEquals("City: " + ValidationFormat.CITY.getDescription(), e.getErrorList().get(9));
			assertEquals("State: " + ValidationFormat.STATE.getDescription(), e.getErrorList().get(10));
			assertEquals("Zip Code: " + ValidationFormat.ZIPCODE.getDescription(), e.getErrorList().get(11));
			assertEquals("Phone Number: " + ValidationFormat.PHONE_NUMBER.getDescription(), e.getErrorList().get(12));
			assertEquals("Emergency Contact Name: " + ValidationFormat.NAME.getDescription(), e.getErrorList().get(13));
			assertEquals("Emergency Contact Phone: " + ValidationFormat.PHONE_NUMBER.getDescription(), e.getErrorList().get(14));
			assertEquals("Insurance Company Name: " + ValidationFormat.NAME.getDescription(), e.getErrorList().get(15));
			assertEquals("Insurance Company Address 1: " + ValidationFormat.ADDRESS.getDescription(), e.getErrorList().get(16));
			assertEquals("Insurance Company Address 2: " + ValidationFormat.ADDRESS.getDescription(), e.getErrorList().get(17));
			assertEquals("Insurance Company City: " + ValidationFormat.CITY.getDescription(), e.getErrorList().get(18));
			assertEquals("Insurance Company State: " + ValidationFormat.STATE.getDescription(), e.getErrorList().get(19));
			assertEquals("Insurance Company Zip: " + ValidationFormat.ZIPCODE.getDescription(), e.getErrorList().get(20));
			assertEquals("Insurance Company Phone: " + ValidationFormat.PHONE_NUMBER.getDescription(), e.getErrorList().get(21));
			assertEquals("Insurance Company ID: " + ValidationFormat.INSURANCE_ID.getDescription(), e.getErrorList().get(22));
			assertEquals("Mother MID: " + ValidationFormat.NPMID.getDescription(), e.getErrorList().get(23));
			assertEquals("Father MID: " + ValidationFormat.NPMID.getDescription(), e.getErrorList().get(24));
			assertEquals("Topical Notes: " + ValidationFormat.NOTES.getDescription(), e.getErrorList().get(25));
			assertEquals("number of errors", 26, e.getErrorList().size());
		}
	}
	
	/**
	 * testFutureBirthError
	 */
	public void testFutureBirthError(){
		PatientBean p = new PatientBean();
		p.setFirstName("Person5");
		p.setLastName("LastName5");
		p.setDateOfBirthStr("10/10/3000");
		p.setDateOfDeathStr("");
		p.setCauseOfDeath("");
		p.setEmail("andy.programmer?gmail.com");
		p.setStreetAddress1("344 East < Ave.");
		p.setStreetAddress2("?");
		p.setCity("Wr0ng");
		p.setState("Pa");
		p.setZip("17534-");
		p.setPhone("555");
		p.setEmergencyName("Tow #ater");
		p.setEmergencyPhone("(809)");
		p.setIcName("Dewie Che@tum and Howe the 2nd");
		p.setIcAddress1("458 Ripoff Blvd?");
		p.setIcAddress2("Greedy Suite                        ");
		p.setIcCity("%");
		p.setIcState("mI");
		p.setIcZip("48169-0000 ");
		p.setIcPhone(" 666-059-4023 ");
		p.setIcID("$$");
		p.setMotherMID("-1");
		p.setFatherMID("-2");
		p.setBloodTypeStr("AB");
		p.setEthnicityStr("Caucasion");
		p.setGenderStr("female");
		p.setTopicalNotes("<script>alert('hello');</script>");
		p.setPassword("toooooooooooooooooooooooooo long password");
		p.setPassword("toooooooooooooooooooooooooo long password");
		try {
			new PatientValidator().validate(p);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("Birth date cannot be in the future!", e.getErrorList().get(3));
		}
	}
	
	/**
	 * testCauseOfDeathValidation
	 */
	public void testCauseOfDeathValidation(){
		PatientBean p = new PatientBean();
		p.setFirstName("Person5");
		p.setLastName("LastName5");
		p.setDateOfBirthStr("10/10/2000");
		p.setDateOfDeathStr("");
		p.setCauseOfDeath("Q150");
		p.setEmail("andy.programmer?gmail.com");
		p.setStreetAddress1("344 East < Ave.");
		p.setStreetAddress2("?");
		p.setCity("Wr0ng");
		p.setState("Pa");
		p.setZip("17534-");
		p.setPhone("555");
		p.setEmergencyName("Tow #ater");
		p.setEmergencyPhone("(809)");
		p.setIcName("Dewie Che@tum and Howe the 2nd");
		p.setIcAddress1("458 Ripoff Blvd?");
		p.setIcAddress2("Greedy Suite                        ");
		p.setIcCity("%");
		p.setIcState("mI");
		p.setIcZip("48169-0000 ");
		p.setIcPhone(" 666-059-4023 ");
		p.setIcID("$$");
		p.setMotherMID("-1");
		p.setFatherMID("-2");
		p.setBloodTypeStr("AB");
		p.setEthnicityStr("Caucasion");
		p.setGenderStr("female");
		p.setTopicalNotes("<script>alert('hello');</script>");
		p.setPassword("toooooooooooooooooooooooooo long password");
		p.setPassword("toooooooooooooooooooooooooo long password");
		try {
			new PatientValidator().validate(p);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("Cause of Death cannot be specified without Date of Death!", e.getErrorList().get(3));
		}
	}
	
	/*
	 * JUnit test for bug #2 on the bug list
	 */
	/**
	 * testPatientTopicalNoteWithQUotationMark
	 * @throws Exception
	 */
	public void testPatientTopicalNoteWithQuotationMark() throws Exception {
		PatientBean p = new PatientBean();
		p.setFirstName("Person'a");
		p.setLastName("LastName");
		p.setDateOfBirthStr("10/10/2005");
		p.setDateOfDeathStr("");
		p.setCauseOfDeath("");
		p.setEmail("andy.programmer@gmail.com");
		p.setSecurityQuestion("'What is your quest?'-");
		p.setSecurityAnswer("I s33k the holy grail");
		p.setStreetAddress1("344 East Random Ave.");
		p.setStreetAddress2("");
		p.setCity("Intercourse");
		p.setState("PA");
		p.setZip("17534");
		p.setPhone("555-542-9023");
		p.setEmergencyName("Tow Mater");
		p.setEmergencyPhone("809-940-1943");
		p.setIcName("Dewie Cheatum n Howe");
		p.setIcAddress1("458 Ripoff Blvd.");
		p.setIcAddress2("Greedy Suite");
		p.setIcCity("Hell");
		p.setIcState("MI");
		p.setIcZip("48169-0000");
		p.setIcPhone("666-059-4023");
		p.setIcID("Money");
		p.setMotherMID("58");
		p.setFatherMID("0");
		p.setBloodTypeStr("O-");
		p.setEthnicityStr("Caucasian");
		p.setGenderStr("Male");
		p.setTopicalNotes("Here are some random topical notes. \" Isn't there more? Yes.\n There is.");
		p.setPassword("testpass1");
		p.setConfirmPassword("testpass1");
		new PatientValidator().validate(p);
	}
	
	/*
	 * Test for threat model - Last name too long.
	 */
	/**
	 * testPatientWithLongLastName
	 * @throws Exception
	 */
	public void testPatientWithLongLastName() throws Exception {
		PatientBean p = new PatientBean();
		p.setFirstName("Person'a");
		p.setLastName("MyLastNameIsReallySuperDuperLoooooooong");
		p.setDateOfBirthStr("10/10/2005");
		p.setDateOfDeathStr("");
		p.setCauseOfDeath("");
		p.setEmail("andy.programmer@gmail.com");
		p.setSecurityQuestion("'What is your quest?'-");
		p.setSecurityAnswer("I s33k the holy grail");
		p.setStreetAddress1("344 East Random Ave.");
		p.setStreetAddress2("");
		p.setCity("Intercourse");
		p.setState("PA");
		p.setZip("17534");
		p.setPhone("555-542-9023");
		p.setEmergencyName("Tow Mater");
		p.setEmergencyPhone("809-940-1943");
		p.setIcName("Dewie Cheatum n Howe");
		p.setIcAddress1("458 Ripoff Blvd.");
		p.setIcAddress2("Greedy Suite");
		p.setIcCity("Hell");
		p.setIcState("MI");
		p.setIcZip("48169-0000");
		p.setIcPhone("666-059-4023");
		p.setIcID("Money");
		p.setMotherMID("58");
		p.setFatherMID("0");
		p.setBloodTypeStr("O-");
		p.setEthnicityStr("Caucasian");
		p.setGenderStr("Male");
		p.setTopicalNotes("Here are some random topical notes. \" Isn't there more? Yes.\n There is.");
		p.setPassword("testpass1");
		p.setConfirmPassword("testpass1");
		try {
			new PatientValidator().validate(p);
			fail();
		}
		catch(FormValidationException e){
			assertEquals("This form has not been validated correctly. The following field are not " +
					"properly filled in: [Last name: Up to 20 Letters, space, ' and -]", e.getMessage());
		}
	}

	/**
	 * testPatientWithValidCardNumbers
	 * @throws Exception
	 */
	public void testPatientWithValidCardNumbers() throws Exception {
		PatientBean p = new PatientBean();
		p.setFirstName("Person'a");
		p.setLastName("MyLastNameIsOK");
		p.setDateOfBirthStr("10/10/2005");
		p.setDateOfDeathStr("");
		p.setCauseOfDeath("");
		p.setEmail("andy.programmer@gmail.com");
		p.setSecurityQuestion("'What is your quest?'-");
		p.setSecurityAnswer("I s33k the holy grail");
		p.setStreetAddress1("344 East Random Ave.");
		p.setStreetAddress2("");
		p.setCity("Intercourse");
		p.setState("PA");
		p.setZip("17534");
		p.setPhone("555-542-9023");
		p.setEmergencyName("Tow Mater");
		p.setEmergencyPhone("809-940-1943");
		p.setIcName("Dewie Cheatum n Howe");
		p.setIcAddress1("458 Ripoff Blvd.");
		p.setIcAddress2("Greedy Suite");
		p.setIcCity("Hell");
		p.setIcState("MI");
		p.setIcZip("48169-0000");
		p.setIcPhone("666-059-4023");
		p.setIcID("Money");
		p.setMotherMID("58");
		p.setFatherMID("0");
		p.setBloodTypeStr("O-");
		p.setEthnicityStr("Caucasian");
		p.setGenderStr("Male");
		p.setTopicalNotes("Here are some random topical notes. \" Isn't there more? Yes.\n There is.");
		p.setPassword("testpass1");
		p.setConfirmPassword("testpass1");
		
		
		
		PatientValidator pv = new PatientValidator();
		
		p.setCreditCardType("MASTERCARD");
		p.setCreditCardNumber("5593090746812380");
		pv.validate(p);
		
		p.setCreditCardType("VISA");
		p.setCreditCardNumber("4539592576502361");
		pv.validate(p);
		
		p.setCreditCardType("AMEX");
		p.setCreditCardNumber("344558915054011");
		pv.validate(p);
		
		p.setCreditCardType("DISCOVER");
		p.setCreditCardNumber("6011953266156193");
		pv.validate(p);
		
		
	}
	
	/**
	 * testPatientWithBadCardNumbers
	 * @throws Exception
	 */
	public void testPatientWithBadCardNumbers() throws Exception {
		PatientBean p = new PatientBean();
		p.setFirstName("Person'a");
		p.setLastName("MyLastNameIsOK");
		p.setDateOfBirthStr("10/10/2005");
		p.setDateOfDeathStr("");
		p.setCauseOfDeath("");
		p.setEmail("andy.programmer@gmail.com");
		p.setSecurityQuestion("'What is your quest?'-");
		p.setSecurityAnswer("I s33k the holy grail");
		p.setStreetAddress1("344 East Random Ave.");
		p.setStreetAddress2("");
		p.setCity("Intercourse");
		p.setState("PA");
		p.setZip("17534");
		p.setPhone("555-542-9023");
		p.setEmergencyName("Tow Mater");
		p.setEmergencyPhone("809-940-1943");
		p.setIcName("Dewie Cheatum n Howe");
		p.setIcAddress1("458 Ripoff Blvd.");
		p.setIcAddress2("Greedy Suite");
		p.setIcCity("Hell");
		p.setIcState("MI");
		p.setIcZip("48169-0000");
		p.setIcPhone("666-059-4023");
		p.setIcID("Money");
		p.setMotherMID("58");
		p.setFatherMID("0");
		p.setBloodTypeStr("O-");
		p.setEthnicityStr("Caucasian");
		p.setGenderStr("Male");
		p.setTopicalNotes("Here are some random topical notes. \" Isn't there more? Yes.\n There is.");
		p.setPassword("testpass1");
		p.setConfirmPassword("testpass1");
		
		
		
		PatientValidator pv = new PatientValidator();
		
		try {
			p.setCreditCardType("VISA");
			p.setCreditCardNumber("5593090746812380");
			pv.validate(p);
			fail("Invalid card number should have thrown exception");
		} catch (Exception e) {
			//TODO
		}
		
		try {
			p.setCreditCardType("MASTERCARD");
			p.setCreditCardNumber("4539592576502361");
			pv.validate(p);
			fail("Invalid card number should have thrown exception");
		} catch (Exception e) {
			//TODO
		}
		
		try {
			p.setCreditCardType("DISCOVER");
			p.setCreditCardNumber("344558915054011");
			pv.validate(p);
			fail("Invalid card number should have thrown exception");
		} catch (Exception e) {
			//TODO
		}
		
		try {
			p.setCreditCardType("AMEX");
			p.setCreditCardNumber("6011953266156193");
			pv.validate(p);
			fail("Invalid card number should have thrown exception");
		} catch (Exception e) {
			//TODO
		}
		
		
	}
	
	/**
	 * testMFWithPersonnelMID
	 * @throws Exception
	 */
	public void testMFWithPersonnelMID() throws Exception {
		PatientBean p = new PatientBean();
		p.setFirstName("Person'a");
		p.setLastName("LastName");
		p.setDateOfBirthStr("10/10/2005");
		p.setDateOfDeathStr("");
		p.setCauseOfDeath("");
		p.setEmail("andy.programmer@gmail.com");
		p.setSecurityQuestion("'What is your quest?'-");
		p.setSecurityAnswer("I s33k the holy grail");
		p.setStreetAddress1("344 East Random Ave.");
		p.setStreetAddress2("");
		p.setCity("Intercourse");
		p.setState("PA");
		p.setZip("17534");
		p.setPhone("555-542-9023");
		p.setEmergencyName("Tow Mater");
		p.setEmergencyPhone("809-940-1943");
		p.setIcName("Dewie Cheatum n Howe");
		p.setIcAddress1("458 Ripoff Blvd.");
		p.setIcAddress2("Greedy Suite");
		p.setIcCity("Hell");
		p.setIcState("MI");
		p.setIcZip("48169-0000");
		p.setIcPhone("666-059-4023");
		p.setIcID("Money");
		p.setMotherMID("9");
		p.setFatherMID("98");
		p.setBloodTypeStr("O-");
		p.setEthnicityStr("Caucasian");
		p.setGenderStr("Male");
		p.setTopicalNotes("Here are some random topical notes. Isn't there more? Yes.\n There is.");
		p.setPassword("testpass1");
		p.setConfirmPassword("testpass1");
		try {
			new PatientValidator().validate(p);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("Mother MID: " + ValidationFormat.NPMID.getDescription(), e.getErrorList().get(0));
			assertEquals("Father MID: " + ValidationFormat.NPMID.getDescription(), e.getErrorList().get(1));
		}
	}

}
