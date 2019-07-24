package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.EditOfficeVisitAction;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.forms.EditOfficeVisitForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Test all office visit by doctors
 */
public class EditOfficeVisitActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditOfficeVisitAction action;
	private EditOfficeVisitAction actionUC60; //UC60

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.hospitals();
		gen.hcp0();
		gen.patient1();
		gen.admin1();
		gen.officeVisit1();
		gen.ndCodes();
		gen.uc60();
		
		action = new EditOfficeVisitAction(factory, 9000000001L, "1", "1");
		actionUC60 = new EditOfficeVisitAction(factory, 9000000011L, "311", "3"); //UC60
	}

	
	/**
	 * testOVID
	 */
	public void testOVID() {
		try {
			action = new EditOfficeVisitAction(factory, 0L, "1", "NaN");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("Office Visit ID is not a number: For input string: \"NaN\"", e.getMessage());
		}
	}

	/**
	 * testEvilDatabase
	 */
	public void testEvilDatabase() {
		try {
			action = new EditOfficeVisitAction(EvilDAOFactory.getEvilInstance(), 0L, "1", "1");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals(
					"A database exception has occurred. Please see the log in the console for stacktrace", e
							.getMessage());
			DBException dbe = (DBException) e;
			assertEquals(EvilDAOFactory.MESSAGE, dbe.getSQLException().getMessage());
		}
	}

	/**
	 * testOVDoesntExist
	 */
	public void testOVDoesntExist() {
		try {
			action = new EditOfficeVisitAction(TestDAOFactory.getTestInstance(), 0L, "1", "158");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("Office Visit 158 with Patient MID 1 does not exist", e.getMessage());
		}
	}
/** Test Hospital location
 * 
 * @throws Exception
 */
	public void testGetHospitals() throws Exception {
		// NOTE: may have to fix this... use different pid and ovid.
		action = new EditOfficeVisitAction(factory, 9000000000L, "1", "1");
		List<HospitalBean> hospitals = action.getHospitals();
		assertEquals(9, hospitals.size());
		// First comes hospitals associated with the HCP in alphabetical order.
		// Then comes all other hospitals in alphabetical order.
		assertEquals("Test Hospital 8181818181", hospitals.get(0).getHospitalName());
		assertEquals("Test Hospital 9191919191", hospitals.get(1).getHospitalName());
		assertEquals("Facebook Rehab Center", hospitals.get(2).getHospitalName());
		assertEquals("Health Institute Dr. E", hospitals.get(3).getHospitalName());
		assertEquals("Health Institute Mr. Barry", hospitals.get(4).getHospitalName());
		assertEquals("Health Institute Mr. Donghoon", hospitals.get(5).getHospitalName());
		assertEquals("Le Awesome Hospital", hospitals.get(6).getHospitalName());
	}

	/**
	 * Test patient office visit
	 * @throws ITrustException
	 */
	public void testGetOfficeVisit() throws ITrustException {
		OfficeVisitBean ovb = action.getOfficeVisit();
		assertEquals(1L, action.getOvID());
		assertEquals("Generated for Death for Patient: 1", ovb.getNotes());
		assertEquals(9000000000L, ovb.getHcpID());
		assertEquals(1L, ovb.getID());
		assertEquals(1, ovb.getVisitID());
		assertEquals("1", ovb.getHospitalID());
		
		assertEquals(0, action.prescriptions().getPrescriptions().size());
		
		//UC60
		OfficeVisitBean ovb60 = actionUC60.getOfficeVisit();
		assertEquals("Sean needs to lower his sodium intake.", ovb60.getNotes());
	}

	/**
	 * testUpdateInformationEmptyForm
	 */
	public void testUpdateInformationEmptyForm() {
		try {
			EditOfficeVisitForm frm = new EditOfficeVisitForm();
			action.updateInformation(frm, false);
			fail("should have thrown exception");
		} catch (FormValidationException fve) {
			//TODO
		}
	}

	/**
	 * Test if patient information is updated
	 * @throws FormValidationException
	 */
	public void testUpdateInformation() throws FormValidationException {
		EditOfficeVisitForm frm = new EditOfficeVisitForm();
		frm.setHcpID("9000000000");
		frm.setPatientID("1");
		frm.setVisitDate("05/02/2001");
		action.updateInformation(frm, false);
		
		//UC60
		EditOfficeVisitForm frm60 = new EditOfficeVisitForm();
		frm60.setHcpID("9000000011");
		frm60.setPatientID("311");
		frm60.setVisitDate("12/02/2013");
		frm60.setIsBilled("false");
		action.updateInformation(frm60, false);
	}
	
	/**
	 * testUpdateInformationSemicolon
	 * @throws FormValidationException
	 */
	public void testUpdateInformationSemicolon() throws FormValidationException {
		EditOfficeVisitForm frm = new EditOfficeVisitForm();
		frm.setHcpID("9000000000");
		frm.setPatientID("1");
		frm.setVisitDate("05/02/2001");
		frm.setNotes("semicolon test ;");
		action.updateInformation(frm, false);
	}
	
	/**
	 * testUpdateInformationOctothorpe
	 * @throws Exception
	 */
	public void testUpdateInformationOctothorpe() throws Exception {
		EditOfficeVisitForm frm = new EditOfficeVisitForm();
		frm.setHcpID("9000000000");
		frm.setPatientID("1");
		frm.setVisitDate("05/02/2001");
		frm.setNotes("semicolon test #");
		try {
			action.updateInformation(frm, false);
		} catch (FormValidationException e) {
			fail(e.getMessage());
		}

	}
	
	/**
	 * testUpdateInformationNewOfficeVisit
	 * @throws Exception
	 */
	public void testUpdateInformationNewOfficeVisit() throws Exception {
		action = new EditOfficeVisitAction(factory, 9000000001L, "1");
		assertEquals(true, action.isUnsaved());
		assertEquals(-1, action.getOvID());
		EditOfficeVisitForm frm = new EditOfficeVisitForm();
		frm.setHcpID("9000000001");
		frm.setPatientID("1");
		frm.setVisitDate("05/02/2001");
		frm.setIsBilled("false"); //UC60
		frm.setApptType("Test type");
		frm.setNotes("That was a doctor's visit");
		try {
			action.updateInformation(frm, false);
		} catch (FormValidationException e) {
			fail(e.getMessage());
		}
		assertEquals(false, action.isUnsaved());
		assertFalse(-1 == action.getOvID());
	}
	
	/**
	 * testMakeEmailApp
	 * @throws Exception
	 */
	public void testMakeEmailApp() throws Exception {
		gen.patient2();
		gen.hcp0();
		Email testEmail = action.makeEmailApp(9000000000L, "2", "You are allergic.");
		assertEquals("no-reply@itrust.com", testEmail.getFrom());
		assertEquals("andy.programmer@gmail.com", testEmail.getToListStr());	
		assertEquals("HCP has prescribed you a potentially dangerous medication", testEmail.getSubject());
		assertEquals("Kelly Doctor has prescribed a medication that you are allergic to or that has a known interaction with a drug you are currently taking. You are allergic.", testEmail.getBody());
	}
}
