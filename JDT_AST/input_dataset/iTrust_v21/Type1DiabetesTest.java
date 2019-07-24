package edu.ncsu.csc.itrust.unit.risk;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.NoHealthRecordsException;
import edu.ncsu.csc.itrust.risk.RiskChecker;
import edu.ncsu.csc.itrust.risk.Type1DiabetesRisks;
import edu.ncsu.csc.itrust.risk.factors.PatientRiskFactor;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class Type1DiabetesTest extends TestCase {
	private TestDataGenerator gen;
	private RiskChecker checker;
	private DAOFactory factory = TestDAOFactory.getTestInstance();

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient1();
		gen.patient2();
	}

	// Note: patient 2 does NOT qualify for the disease, see the next test
	public void testPatient2() throws Exception {
		checker = new Type1DiabetesRisks(factory, 2L);
		List<PatientRiskFactor> factors = checker.getPatientRiskFactors();
		assertEquals(3, factors.size());
		assertEquals("Patient's ethnicity is Caucasian", factors.get(0).getDescription());
		assertEquals("Patient has a family history of this disease", factors.get(1).getDescription());
		assertEquals("This patient had a viral infection during their childhood that would "
				+ "increase the risk for this diesease", factors.get(2).getDescription());
	}

	public void testQualifies() throws Exception {
		checker = new Type1DiabetesRisks(factory, 2L);
		assertFalse(checker.qualifiesForDisease());
		PatientBean p = factory.getPatientDAO().getPatient(2L);
		p.setDateOfBirthStr(DateUtil.yearsAgo(10));
		factory.getPatientDAO().editPatient(p, 9000000003L);
		checker = new Type1DiabetesRisks(factory, 2L);
		assertTrue(checker.qualifiesForDisease());
		assertTrue(checker.isAtRisk());
	}

	public void testNoPHR() throws Exception {
		try {
			checker = new Type1DiabetesRisks(factory, 200L);
			fail("exception should have been thrown");
		} catch (NoHealthRecordsException e) {
			assertEquals(NoHealthRecordsException.MESSAGE, e.getMessage());
		}
	}
}
