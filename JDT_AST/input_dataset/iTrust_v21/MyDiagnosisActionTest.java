package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.MyDiagnosisAction;
import edu.ncsu.csc.itrust.action.MyDiagnosisAction.HCPDiagnosisBeanComparator;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.beans.HCPDiagnosisBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class MyDiagnosisActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private MyDiagnosisAction action;
	private HCPDiagnosisBean a;
	private HCPDiagnosisBean b;
	private HCPDiagnosisBeanComparator hcpdbc;

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.patient_hcp_vists();
		gen.hcp_diagnosis_data();
		
	}

	public void testGetDiagnoses() throws Exception {
		action = new MyDiagnosisAction(factory, 2L);
		List<DiagnosisBean> diagnoses = action.getDiagnoses();
		assertEquals(11, diagnoses.size());
		// further testing should be done in the patientDAO
	}

	public void testGetHCPByDiagnosis() throws Exception {
		action = new MyDiagnosisAction(factory, 1L);
		List<HCPDiagnosisBean> diags = action.getHCPByDiagnosis("79.1");
		assertEquals(9000000004L, diags.get(0).getHCP());
		assertTrue(diags.get(0).getHCPName().equals("Jason Frankenstein"));
		assertEquals(2, diags.get(0).getNumPatients());
		assertEquals(1, diags.get(0).getMedList().size());
		assertEquals(0, diags.get(0).getLabList().size());
		assertEquals("3.0", diags.get(0).getVisitSatisfaction());
		assertEquals("4.0", diags.get(0).getTreatmentSatisfaction());
	}

	public void testCompare1() {
		a = new HCPDiagnosisBean();
		b = new HCPDiagnosisBean();
		hcpdbc = new HCPDiagnosisBeanComparator();
		a.incNumPatients();
		assertEquals(-1, hcpdbc.compare(a, b)); // a > b
	}
	
	public void testCompare2() {
		a = new HCPDiagnosisBean();
		b = new HCPDiagnosisBean();
		hcpdbc = new HCPDiagnosisBeanComparator();
		b.incNumPatients();
		assertEquals(1, hcpdbc.compare(a, b)); // a < b
	}
	
	public void testCompare3() {
		a = new HCPDiagnosisBean();
		b = new HCPDiagnosisBean();
		hcpdbc = new HCPDiagnosisBeanComparator();
		a.incNumPatients();
		b.incNumPatients();
		assertEquals(0, hcpdbc.compare(a, b)); // a == b
	}
}
