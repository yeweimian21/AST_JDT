package edu.ncsu.csc.itrust.unit.action;

import java.util.Date;

import edu.ncsu.csc.itrust.action.EditPatientInstructionsAction;
import edu.ncsu.csc.itrust.beans.PatientInstructionsBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class EditPatientInstructionsActionTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditPatientInstructionsAction action;
	private PatientInstructionsBean bean = new PatientInstructionsBean();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.hcp4();
	}

	public void testGetPatientInstructions() throws Exception {
		// Without an Office visit ID, this should return an empty list.
		action = new EditPatientInstructionsAction(factory, 9000000000L, "2");
		assertEquals(0, action.getPatientInstructions().size());
	}

	/**
	 * testAddPatientInstructions
	 * @throws Exception
	 */
	public void testAddPatientInstructions() throws Exception {
		// Without an Office visit ID, this should throw.
		action = new EditPatientInstructionsAction(factory, 9000000000L, "2");
		try {
			action.addPatientInstructions(bean);
			fail("Expected an exception.");
		} catch (ITrustException e) {
			assertEquals("Cannot perform action.  OfficeVisit is not saved.", e.getMessage());
		}
		
		// Try adding patient specific instructions.
		action = new EditPatientInstructionsAction(factory, 9000000000L, "2", "952");
		assertEquals(0, action.getPatientInstructions().size());
		bean.setName("This is an instruction name.");
		bean.setComment("This is an instruction comment.");
		bean.setUrl("http://example.com/");
		bean.setVisitID(952);
		bean.setModified(new Date());
		action.addPatientInstructions(bean);
		assertEquals(1, action.getPatientInstructions().size());
		assertEquals("This is an instruction name.", action.getPatientInstructions().get(0).getName());
	}

	/**
	 * testEditPatientInstructions
	 * @throws Exception
	 */
	public void testEditPatientInstructions() throws Exception {
		// Without an Office visit ID, this should throw.		
		action = new EditPatientInstructionsAction(factory, 9000000000L, "2");
		try {
			action.editPatientInstructions(bean);
			fail("Expected an exception.");
		} catch (ITrustException e) {
			assertEquals("Cannot perform action.  OfficeVisit is not saved.", e.getMessage());
		}

		// Try editing patient specific instructions.
		gen.uc44_acceptance_scenario_2();
		action = new EditPatientInstructionsAction(factory, 9000000004L, "1", "44100");
		assertEquals(1, action.getPatientInstructions().size());
		bean = action.getPatientInstructions().get(0);
		bean.setName("This instruction has been modified!");
		action.editPatientInstructions(bean);
		assertEquals(1, action.getPatientInstructions().size());
		assertEquals("This instruction has been modified!", action.getPatientInstructions().get(0).getName());
	}

	/**
	 * testDeletePatientInstructions
	 * @throws Exception
	 */
	public void testDeletePatientInstructions() throws Exception {
		// Without an Office visit ID, this should throw.		
		action = new EditPatientInstructionsAction(factory, 9000000000L, "2");
		try {
			action.deletePatientInstructions(bean);
			fail("Expected an exception.");
		} catch (ITrustException e) {
			assertEquals("Cannot perform action.  OfficeVisit is not saved.", e.getMessage());
		}

		// Try deleting patient specific instructions.
		gen.uc44_acceptance_scenario_2();
		action = new EditPatientInstructionsAction(factory, 9000000004L, "1", "44100");
		assertEquals(1, action.getPatientInstructions().size());
		bean.setId(44100);
		action.deletePatientInstructions(bean);
		assertEquals(0, action.getPatientInstructions().size());
	}

	/**
	 * testValidate
	 * @throws Exception
	 */
	public void testValidate() throws Exception {
		// Without an Office visit ID, this should throw.		
		action = new EditPatientInstructionsAction(factory, 9000000000L, "2");
		try {
			action.validate(bean);
			fail("Expected an exception.");
		} catch (ITrustException e) {
			assertEquals("Cannot perform action.  OfficeVisit is not saved.", e.getMessage());
		}
		
		// Try validating patient specific instructions.
		gen.uc44_acceptance_scenario_2();
		action = new EditPatientInstructionsAction(factory, 9000000004L, "1", "44100");
		bean.setName("This is an instruction name.");
		bean.setComment("This is an instruction comment.");
		bean.setUrl("http://example.com/");
		bean.setVisitID(952);
		bean.setModified(new Date());
		try {
			action.validate(bean);
		} catch (FormValidationException e) {
			fail("Expected validation to succeed.");
		}
	}

}
