package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import edu.ncsu.csc.itrust.action.RequestRecordsReleaseAction;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.RecordsReleaseBean;
import edu.ncsu.csc.itrust.beans.forms.RecordsReleaseForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class RequestRecordsReleaseActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DAOFactory evilFactory = EvilDAOFactory.getEvilInstance();
	private TestDataGenerator gen;
	private RequestRecordsReleaseAction action;
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		action = new RequestRecordsReleaseAction(factory, 102L);
	}
	
	public void testGetPatientName() {
		assertEquals("Caldwell Hudson", action.getPatientName());
		
		//Try running the test with a non existent patient
		action = new RequestRecordsReleaseAction(factory, 66L);
		assertEquals("", action.getPatientName());
	}
	
	public void testAddRecordsRelease() {
		//Insert a good request
		RecordsReleaseForm form = new RecordsReleaseForm();
		form.setReleaseHospitalID("1");
		form.setRecipientHospitalName("New Hospital");
		form.setRecipientHospitalAddress("123 Fake Street");
		form.setRecipientFirstName("BillyBob");
		form.setRecipientLastName("Joe");
		form.setRecipientPhone("919-123-4567");
		form.setRecipientEmail("bbjoe@yahoo.com");
		form.setRequestJustification("Just for kicks n' giggles");
		form.setDigitalSignature(true);
		assertEquals("Request successfully sent", action.addRecordsRelease(form));
		
		//Insert a bad request form without a digital signature
		RecordsReleaseForm noSigForm = new RecordsReleaseForm();
		noSigForm.setReleaseHospitalID("1");
		noSigForm.setRecipientHospitalName("New Hospital");
		noSigForm.setRecipientHospitalAddress("123 Fake Street");
		noSigForm.setRecipientFirstName("BillyBob");
		noSigForm.setRecipientLastName("Joe");
		noSigForm.setRecipientPhone("919-123-4567");
		noSigForm.setRecipientEmail("bbjoe@yahoo.com");
		noSigForm.setRequestJustification("Just for kicks n' giggles");
		noSigForm.setDigitalSignature(false);
		assertEquals("Error: Digital signature does not match name on record", action.addRecordsRelease(noSigForm));
		
		//Insert a bad request form that has not been filled
		RecordsReleaseForm unfilledForm = new RecordsReleaseForm();
		String unfilledFormMsg = "This form has not been validated correctly. The following field are not properly "
				+ "filled in: [Release Hospital ID: Between 1 and 10 digits, Recipient hospital name: "
				+ "Between 1 and 30 alphanumerics, space, ', and ., Recipient hospital address: Up to 100 "
				+ "alphanumeric characters, comma, and ., Doctor's first name: Up to 20 Letters, space, ' and -, "
				+ "Doctor's last name: Up to 20 Letters, space, ' and -, Doctor's phone number: xxx-xxx-xxxx, "
				+ "Doctor's email address: Up to 30 alphanumeric characters and symbols . and _ @], "
				+ "Digital signature does not match name on record";
		assertEquals(unfilledFormMsg, action.addRecordsRelease(unfilledForm));
		
		//Insert a bad request form completely unfilled except for the digital signature
		unfilledForm.setDigitalSignature(true);
		String unfilledFormMsg2 = "This form has not been validated correctly. The following field are not properly "
				+ "filled in: [Release Hospital ID: Between 1 and 10 digits, Recipient hospital name: "
				+ "Between 1 and 30 alphanumerics, space, ', and ., Recipient hospital address: Up to 100 "
				+ "alphanumeric characters, comma, and ., Doctor's first name: Up to 20 Letters, space, ' and -, "
				+ "Doctor's last name: Up to 20 Letters, space, ' and -, Doctor's phone number: xxx-xxx-xxxx, "
				+ "Doctor's email address: Up to 30 alphanumeric characters and symbols . and _ @]";
		assertEquals(unfilledFormMsg2, action.addRecordsRelease(unfilledForm));
		
	}
	
	public void testGetAllPatientReleaseRequests() {
		//Get the initial amount of release requests for the patient
		List<RecordsReleaseBean> releases = action.getAllPatientReleaseRequests();
		int size = releases.size();
		
		RecordsReleaseForm form = new RecordsReleaseForm();
		form.setReleaseHospitalID("1");
		form.setRecipientHospitalName("New Hospital");
		form.setRecipientHospitalAddress("123 Fake Street");
		form.setRecipientFirstName("BillyBob");
		form.setRecipientLastName("Joe");
		form.setRecipientPhone("919-123-4567");
		form.setRecipientEmail("bbjoe@yahoo.com");
		form.setRequestJustification("Just for kicks n' giggles");
		form.setDigitalSignature(true);
		
		//Insert 1 record first
		assertEquals("Request successfully sent", action.addRecordsRelease(form));
		//Get new list of release requests and check that the size has incremented by 1
		releases = action.getAllPatientReleaseRequests();
		assertEquals(++size, releases.size());
		
		//Insert another record
		assertEquals("Request successfully sent", action.addRecordsRelease(form));
		//Get new list of release requests and check that the size has incremented by 1
		releases = action.getAllPatientReleaseRequests();
		assertEquals(++size, releases.size());
	}
	
	public void testEvilFactory() {
		action = new RequestRecordsReleaseAction(evilFactory, 102L);
		
		//Attempt to insert a good request form
		RecordsReleaseForm form = new RecordsReleaseForm();
		form.setReleaseHospitalID("1");
		form.setRecipientHospitalName("New Hospital");
		form.setRecipientHospitalAddress("123 Fake Street");
		form.setRecipientFirstName("BillyBob");
		form.setRecipientLastName("Joe");
		form.setRecipientPhone("919-123-4567");
		form.setRecipientEmail("bbjoe@yahoo.com");
		form.setRequestJustification("Just for kicks n' giggles");
		form.setDigitalSignature(true);
		assertEquals("Error: There was an error in the database", action.addRecordsRelease(form));
		
		//Attempt to get the patient's records release requests
		assertTrue(action.getAllPatientReleaseRequests().isEmpty());
		
		//Attempt to get the patient's name
		assertEquals("", action.getPatientName());
		
		//Attempt to get a hospital's name
		assertEquals("", action.getHospitalName(""));
		
		//Attempt to get all patient hospitals
		assertTrue(action.getAllPatientHospitals().isEmpty());
		
		//Attempt to get the patient's list of represented patients
		assertTrue(action.getRepresented().isEmpty());
	}
	
	public void testGetHospitalName() {
		HospitalsDAO hosDAO = new HospitalsDAO(factory);
		String hospitalName = "";
		try {
			//Get hospital name for hospital id 1
			hospitalName = hosDAO.getHospital("1").getHospitalName();
		} catch (DBException e) {
			//Fail if there is a DBException
			fail();
		}
		//Test that the name the action method gets is the same
		assertEquals(hospitalName, action.getHospitalName("1"));
		
		//Clear the db tables
		try {
			gen.clearAllTables();
		} catch (Exception e) {
			//Fail if there is an exception
			fail();
		}
		
		//Test that a blank string is returned if there is no hospital with the given id
		assertEquals("", action.getHospitalName("1"));
	}
	
	public void testGetAllPatientHospitals() {
		assertTrue(!action.getAllPatientHospitals().isEmpty());
	}
	
	public void testGetRepresented() {
		action = new RequestRecordsReleaseAction(factory, 2L);
		List<PatientBean> representedList = action.getRepresented();
		
		assertFalse(representedList.isEmpty());
	}
}
