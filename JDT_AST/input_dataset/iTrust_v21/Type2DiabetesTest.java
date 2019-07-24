package edu.ncsu.csc.itrust.unit.risk;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.risk.RiskChecker;
import edu.ncsu.csc.itrust.risk.Type2DiabetesRisks;
import edu.ncsu.csc.itrust.risk.factors.PatientRiskFactor;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class Type2DiabetesTest extends TestCase {
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
	public void testPatient2() throws Exception {
		checker = new Type2DiabetesRisks(factory, 2L);
		List<PatientRiskFactor> factors = checker.getPatientRiskFactors();
		assertEquals(5, factors.size());
		assertEquals("Patient's body mass index is over 25", factors.get(0).getDescription());
		assertEquals("Patient has hypertension", factors.get(1).getDescription());
		assertEquals("Patient has bad cholesterol", factors.get(2).getDescription());
		assertEquals("Patient has had related diagnoses", factors.get(3).getDescription());
		assertEquals("Patient has a family history of this disease", factors.get(4).getDescription());
		
	}
	
	public void testQualifies() throws Exception {
		checker = new Type2DiabetesRisks(factory, 2L);
		assertTrue(checker.qualifiesForDisease());
		PatientBean p = factory.getPatientDAO().getPatient(2L);
		p.setDateOfBirthStr(DateUtil.yearsAgo(10));
		factory.getPatientDAO().editPatient(p, 9000000003L);
		checker = new Type2DiabetesRisks(factory, 2L);
		assertFalse(checker.qualifiesForDisease());
	}
}
