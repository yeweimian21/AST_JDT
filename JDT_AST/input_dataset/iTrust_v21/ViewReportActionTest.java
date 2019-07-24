package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.ViewReportAction;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.beans.PrescriptionBean;
import edu.ncsu.csc.itrust.beans.ProcedureBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ViewReportActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private ViewReportAction action = new ViewReportAction(factory, 2L);
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hcp0();
		gen.patient2();
	}

	public void testGetDiagnoses() throws Exception {
		gen.icd9cmCodes();
		List<DiagnosisBean> list = action.getDiagnoses(2L);
		assertEquals(6, list.size());
		DiagnosisBean bean = list.get(0);
		assertEquals("250.10", bean.getICDCode());
		assertEquals("Diabetes with ketoacidosis", bean.getDescription());
	}

	public void testGetProcedures() throws Exception {
		gen.cptCodes();
		List<ProcedureBean> list = action.getProcedures(2L);
		assertEquals(1, list.size());
		ProcedureBean bean = list.get(0);
		assertEquals("1270F", bean.getCPTCode());
		assertEquals("Injection procedure", bean.getDescription());
	}

	public void testGetPrescriptions() throws Exception {
		gen.ndCodes();
		List<PrescriptionBean> list = action.getPrescriptions(2L);
		assertEquals(1, list.size());
		PrescriptionBean bean = list.get(0);
		assertEquals("647641512", bean.getMedication().getNDCode());
		assertEquals("10/10/2006", bean.getStartDateStr());
		assertEquals("10/11/2020", bean.getEndDateStr());
		assertEquals(5, bean.getDosage());
		assertEquals("Take twice daily", bean.getInstructions());
	}

	public void testGetDeclaredHCPs() throws Exception {
		gen.hcp3();
		List<PersonnelBean> list = action.getDeclaredHCPs(2L);
		assertEquals(1, list.size());
	}
	
	public void testGetPersonnel() throws ITrustException {
		PersonnelBean bean = action.getPersonnel(9000000000L);
		assertEquals("Kelly Doctor", bean.getFullName());
		assertEquals("surgeon", bean.getSpecialty());
	}

	public void testGetPatientl() throws ITrustException {
		PatientBean bean = action.getPatient(2L);
		assertEquals("Andy Programmer", bean.getFullName());
	}
	
}
