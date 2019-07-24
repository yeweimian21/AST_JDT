package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.EmergencyReportAction;
import edu.ncsu.csc.itrust.beans.AllergyBean;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.beans.PrescriptionBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class EmergencyReportActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EmergencyReportAction action;

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.patient2();
		gen.hcp0();
		gen.ndCodes();
		gen.icd9cmCodes();
		action = new EmergencyReportAction(factory, 9000000000L, "2");
	}

	public void testBaseCase() throws ITrustException {
		assertEquals("Andy Programmer", action.getPatientName());
		assertEquals("O-", action.getBloodType());
		List<AllergyBean> lab = action.getAllergies();
		assertEquals(2, lab.size());
		List<PrescriptionBean> lpb = action.getCurrentPrescriptions();
		assertEquals(1, lpb.size());
		assertEquals("Prioglitazone", lpb.get(0).getMedication().getDescription());
		List<DiagnosisBean> ldb = action.getWarningDiagnoses();
		assertEquals(2, ldb.size());
	}
}
