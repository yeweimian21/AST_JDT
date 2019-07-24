package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.action.ViewPatientInstructionsAction;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class ViewPatientInstructionsActionTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private ViewPatientInstructionsAction action;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	public void testGetOfficeVisitsWithInstructions() throws Exception {
		action = new ViewPatientInstructionsAction(factory, "5");
		assertEquals(0, action.getOfficeVisitsWithInstructions().size());
		
		gen.uc44_acceptance_scenario_2();
		gen.hcp4();
		action = new ViewPatientInstructionsAction(factory, "1");
		assertEquals(1, action.getOfficeVisitsWithInstructions().size());
	}

	public void testGetInstructionsForOfficeVisit() throws Exception {
		action = new ViewPatientInstructionsAction(factory, "2");
		assertEquals(0, action.getInstructionsForOfficeVisit(952).size());
		
		gen.uc44_acceptance_scenario_2();
		gen.hcp4();
		action = new ViewPatientInstructionsAction(factory, "1");
		assertEquals(1, action.getInstructionsForOfficeVisit(44100).size());
		assertEquals("Flu Diet", action.getInstructionsForOfficeVisit(44100).get(0).getName());
	}

	public void testGetHCPNameLookup() throws Exception {
		action = new ViewPatientInstructionsAction(factory, "20");
		assertEquals(0, action.getHCPNameLookup().size());
		
		gen.uc44_acceptance_scenario_2();
		gen.hcp4();
		action = new ViewPatientInstructionsAction(factory, "1");
		assertEquals(3, action.getHCPNameLookup().size());
		assertEquals("Kelly Doctor", action.getHCPNameLookup().get(9000000000L));
		assertEquals("Antonio Medico", action.getHCPNameLookup().get(9000000004L));
	}

}
