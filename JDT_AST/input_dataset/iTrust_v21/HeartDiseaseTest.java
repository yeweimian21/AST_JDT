package edu.ncsu.csc.itrust.unit.risk;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.risk.HeartDiseaseRisks;
import edu.ncsu.csc.itrust.risk.RiskChecker;
import edu.ncsu.csc.itrust.risk.factors.PatientRiskFactor;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class HeartDiseaseTest extends TestCase {
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

	public void testPatient1() throws Exception {
		checker = new HeartDiseaseRisks(factory, 1L);
		assertTrue(checker.isAtRisk());
		List<PatientRiskFactor> factors = checker.getPatientRiskFactors();
		assertEquals(3, factors.size());
		assertEquals("Patient is over 45", factors.get(0).getDescription());
		assertEquals("Patient's ethnicity is African American", factors.get(1).getDescription());
		assertEquals("Patient has had related diagnoses", factors.get(2).getDescription());
	}

	public void testPatient2() throws Exception {
		checker = new HeartDiseaseRisks(factory, 2L);
		assertTrue(checker.isAtRisk());
		List<PatientRiskFactor> factors = checker.getPatientRiskFactors();
		assertEquals(7, factors.size());
		assertEquals("Patient is male", factors.get(0).getDescription());
		assertEquals("Patient's body mass index is over 30", factors.get(1).getDescription());
		assertEquals("Patient has hypertension", factors.get(2).getDescription());
		assertEquals("Patient has bad cholesterol", factors.get(3).getDescription());
		assertEquals("Patient is or was a smoker", factors.get(4).getDescription());
		assertEquals("Patient has had related diagnoses", factors.get(5).getDescription());
		assertEquals("Patient has a family history of this disease", factors.get(6).getDescription());
	}
}
