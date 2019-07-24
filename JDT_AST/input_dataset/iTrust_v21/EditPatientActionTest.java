package edu.ncsu.csc.itrust.unit.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.EditPatientAction;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.exception.FormValidationException;

public class EditPatientActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditPatientAction action;

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.patient2();
		action = new EditPatientAction(factory, 9000000000L, "2");
	}

	public void testConstructNormal() throws Exception {
		assertEquals(2L, action.getPid());
	}

	public void testNonExistent() throws Exception {
		try {
			action = new EditPatientAction(factory, 0L, "200");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("Patient does not exist", e.getMessage());
		}
	}

	public void testEditRepresentatives() throws Exception {
		action = new EditPatientAction(factory, 2l, "2");
		PatientDAO po = new PatientDAO(factory);
		PatientBean pb = po.getPatient(2);
		assertEquals("Andy", pb.getFirstName());
		assertEquals("Programmer", pb.getLastName());
		assertEquals("0", pb.getFatherMID());
		pb.setFatherMID("1");
		assertEquals("1", pb.getFatherMID());
		action.updateInformation(pb);
		PatientBean pb2 = po.getPatient(2);
		assertEquals("Andy", pb2.getFirstName());
		assertEquals("Programmer", pb2.getLastName());
		assertEquals("1", pb2.getFatherMID());

	}

	public void testEditCOD() throws Exception {
		gen.patient1();
		action = new EditPatientAction(factory, 1L, "1");
		PatientDAO po = TestDAOFactory.getTestInstance().getPatientDAO();
		PatientBean pb = po.getPatient(1l);
		assertEquals("Random", pb.getFirstName());
		assertEquals("", pb.getCauseOfDeath());
		assertEquals("", pb.getDateOfDeathStr());
		pb.setCauseOfDeath("79.1");
		pb.setDateOfDeathStr("01/03/2006");
		action.updateInformation(pb);
		PatientBean pb2 = po.getPatient(1l);
		assertEquals("Random", pb2.getFirstName());
		assertEquals("79.1", pb2.getCauseOfDeath());
		assertEquals("01/03/2006", pb2.getDateOfDeathStr());

	}
	
	public void testInvalidDates() throws Exception {
		gen.patient3();
		action = new EditPatientAction(factory, 3L, "3");
		PatientDAO po = TestDAOFactory.getTestInstance().getPatientDAO();
		PatientBean pb = po.getPatient(3l);
		try {
			pb.setCauseOfDeath("79.1");
			pb.setDateOfDeathStr("01/03/2050");
			action.updateInformation(pb);
			fail("exception should have been thrown on invalid date of death");
		}catch (FormValidationException e){
			//test passes, exception should have been thrown
		}
		
		try {
			pb.setDateOfBirthStr("01/03/2050");
			action.updateInformation(pb);
			fail("exception should have been thrown on invalid date of birth");
		}catch (FormValidationException e){
			//test passes, exception should have been thrown
		}
		
		
	}

	public void testWrongFormat() throws Exception {
		try {
			action = new EditPatientAction(factory, 0L, "hello!");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("Patient ID is not a number: hello!", e.getMessage());
		}
	}

	public void testNull() throws Exception {
		try {
			action = new EditPatientAction(factory, 0L, null);
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("Patient ID is not a number: null", e.getMessage());
		}
	}
	
	public void testGetPatientLogged() throws Exception {
		PatientBean patient = action.getPatient();
		assertEquals(2L, patient.getMID());
	}
	
	public void testDeactivateActivate() throws Exception {
		gen.patient1();
		action = new EditPatientAction(factory, 1L, "1");
		PatientDAO po = TestDAOFactory.getTestInstance().getPatientDAO();
		action.deactivate();
		PatientBean pb1 = po.getPatient(1l);
		assertFalse(pb1.getDateOfDeactivationStr().equals(""));
		action.activate();
		PatientBean pb2 = po.getPatient(1l);
		assertTrue(pb2.getDateOfDeactivationStr().equals(""));
	}
	
	public void testIsDependent() throws Exception {
		//Check that patient 2 is not a dependent
		assertFalse(action.isDependent());
		
		//Change patient 2's dependency status
		AuthDAO authDAO = factory.getAuthDAO();
		authDAO.setDependent(2L, true);
		
		//Check that patient 2 is a dependent
		assertTrue(action.isDependent());
	}
	
	public void testSetDependent() throws Exception {
		//2 represents 1, but not 4
		gen.patient1();
		gen.patient4();
		//Add patient 4 to be represented by patient 2
		PatientDAO patientDAO = new PatientDAO(factory);
		patientDAO.addRepresentative(2L, 4L);

		//Ensure the representatives were added correctly
		assertEquals(2, patientDAO.getRepresented(2L).size());
		
		//Make patient 2 a dependent
		assertTrue(action.setDependent(true));
		
		//Assert that no more patients are represented by patient 2
		assertTrue(patientDAO.getRepresented(2L).isEmpty());
		
		//Check patient 2's dependency status
		AuthDAO authDAO = factory.getAuthDAO();
		assertTrue(authDAO.isDependent(2L));
		
		//Make patient 2 not a dependent
		assertTrue(action.setDependent(false));
		
		//Check that patient 2 is not a dependent
		assertFalse(authDAO.isDependent(2L));
	}
}
