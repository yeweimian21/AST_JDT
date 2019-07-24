package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.ChronicDiseaseRiskAction;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.risk.RiskChecker;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ChronicDiseaseRiskActionTest extends TestCase {
	private TestDataGenerator gen = new TestDataGenerator();
	private DAOFactory factory = TestDAOFactory.getTestInstance();

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.patient2();
	}

	public void testGetPatient2() throws Exception {
		ChronicDiseaseRiskAction action = new ChronicDiseaseRiskAction(factory, 2L, "2");
		assertEquals(2L, action.getPatientID());
		assertEquals("Andy Programmer", action.getUserName());
		List<RiskChecker> atRisk = action.getDiseasesAtRisk();
		assertEquals(2, atRisk.size());
		
		
		//The test for the Chronic Disease mediator will assert the rest of these
	}

}
