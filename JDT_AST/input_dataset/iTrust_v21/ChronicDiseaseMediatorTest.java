package edu.ncsu.csc.itrust.unit.risk;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.NoHealthRecordsException;
import edu.ncsu.csc.itrust.risk.ChronicDiseaseMediator;
import edu.ncsu.csc.itrust.risk.RiskChecker;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ChronicDiseaseMediatorTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
	}

	public void testPatient2() throws Exception {
		gen.patient2();
		List<RiskChecker> list = new ChronicDiseaseMediator(factory, 2L).getDiseaseAtRisk();
		assertEquals(2, list.size());
		assertEquals("Heart Disease", list.get(0).getName());
		assertEquals("Type 2 Diabetes", list.get(1).getName());
	}

	public void testNoRecords() throws Exception {
		try {
			new ChronicDiseaseMediator(factory, 2L);
			fail("exception should have been thrown");
		} catch (NoHealthRecordsException e) {
			assertEquals("message matches too", NoHealthRecordsException.MESSAGE, e.getMessage());
		}
	}
}
