package edu.ncsu.csc.itrust.unit.dao.patient;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class GetDiagnosesTest extends TestCase {
	private PatientDAO patientDAO = TestDAOFactory.getTestInstance().getPatientDAO();
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.icd9cmCodes();
		gen.patient2();
	}

	public void testGetPatient2() throws Exception {
		List<DiagnosisBean> diagnoses = patientDAO.getDiagnoses(2L);
		assertEquals(6, diagnoses.size());
		assertEquals("250.10", diagnoses.get(0).getICDCode());
		assertEquals("Diabetes with ketoacidosis", diagnoses.get(0).getDescription());
	}

	public void testNotPatient200() throws Exception {
		List<DiagnosisBean> diagnoses = patientDAO.getDiagnoses(200L);
		assertEquals(0, diagnoses.size());
	}
}
