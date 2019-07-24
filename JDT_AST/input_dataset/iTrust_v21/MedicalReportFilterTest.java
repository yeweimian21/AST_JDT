package edu.ncsu.csc.itrust.unit.report;

import java.util.List;

import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.report.MedicalReportFilter;
import edu.ncsu.csc.itrust.report.MedicalReportFilter.MedicalReportFilterType;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class MedicalReportFilterTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private PatientDAO pDAO = factory.getPatientDAO();
	private List<PatientBean> allPatients;
	private MedicalReportFilter filter;
	private TestDataGenerator gen = new TestDataGenerator();

	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		allPatients = pDAO.getAllPatients();
	}

	public void testFilterByProcedure() throws Exception {
		filter = new MedicalReportFilter(MedicalReportFilterType.PROCEDURE, "1270F", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(1, res.size());
		assertTrue(res.get(0).getMID() == 2L); // Andy Programmer
	}

	public void testFilterByProcedureNoResult() {
		filter = new MedicalReportFilter(MedicalReportFilterType.PROCEDURE, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}
	
	public void testFilterByAllergy() throws Exception {
		filter = new MedicalReportFilter(MedicalReportFilterType.ALLERGY, "00882219", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(1, res.size());
		assertTrue(res.get(0).getMID() == 100L);
	}

	public void testFilterByAllergyNoResult() {
		filter = new MedicalReportFilter(MedicalReportFilterType.ALLERGY, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}
	
	public void testFilterByOfficeVisit() throws Exception {
		filter = new MedicalReportFilter(MedicalReportFilterType.UPPER_OFFICE_VISIT_DATE, "12/10/1985", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(2, res.size());
		assertTrue(res.get(0).getMID() == 2L);
		assertTrue(res.get(1).getMID() == 99L);
	}

	public void testFilterByOfficeVisitNoResult() {
		filter = new MedicalReportFilter(MedicalReportFilterType.UPPER_OFFICE_VISIT_DATE, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}
	
	public void testFilterByLowerOfficeVisit() throws Exception {
		filter = new MedicalReportFilter(MedicalReportFilterType.LOWER_OFFICE_VISIT_DATE, "01/01/2012", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(14, res.size());
		assertTrue(res.get(0).getMID() == 100L);
	}

	public void testFilterByLowerOfficeVisitNoResult() {
		filter = new MedicalReportFilter(MedicalReportFilterType.LOWER_OFFICE_VISIT_DATE, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}
	
	public void testFilterByCurrentPrescriptions() throws Exception {
		filter = new MedicalReportFilter(MedicalReportFilterType.CURRENT_PRESCRIPTIONS, "647641512", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(7, res.size());
		assertTrue(res.get(0).getMID() == 2L);
		assertTrue(res.get(1).getMID() == 99L);
		assertTrue(res.get(2).getMID() == 10L);
	}

	public void testFilterByCurrentPrescriptionsNoResult() {
		filter = new MedicalReportFilter(MedicalReportFilterType.CURRENT_PRESCRIPTIONS, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}
	
	public void testFilterByPastCurrentPrescriptions() throws Exception {
		filter = new MedicalReportFilter(MedicalReportFilterType.PASTCURRENT_PRESCRIPTIONS, "081096", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(1, res.size());
		assertTrue(res.get(0).getMID() == 99L);
	}

	public void testFilterByPastCurrentPrescriptionsNoResult() {
		filter = new MedicalReportFilter(MedicalReportFilterType.PASTCURRENT_PRESCRIPTIONS, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}
	
	public void testFilerByCurrentDiagnoses() throws Exception {
		filter = new MedicalReportFilter(MedicalReportFilterType.DIAGNOSIS_ICD_CODE, "84.50", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(1, res.size());
	}
	
	public void testFilterByCurrentDiagnosesNoResult() {
		filter = new MedicalReportFilter(MedicalReportFilterType.DIAGNOSIS_ICD_CODE, "34.00", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(0, res.size());
	}
	
	public void testFilterByMissingDiagnosesICDCode(){
		filter = new MedicalReportFilter(MedicalReportFilterType.MISSING_DIAGNOSIS_ICD_CODE, "FUCK", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.size()>0);
		
		filter = new MedicalReportFilter(MedicalReportFilterType.MISSING_DIAGNOSIS_ICD_CODE, "34.00", factory);
		List<PatientBean> res2 = filter.filter(allPatients);
		assertTrue(res2.size()>0);
	}
	
	public void testToString() {
		String expected = "";
		filter = new MedicalReportFilter(MedicalReportFilterType.ALLERGY, "val", factory);
		expected = "Filter by ALLERGY with value val";
		assertEquals(expected, filter.toString());
	}

	public void testFilterTypeFromString() {
		MedicalReportFilterType expected = MedicalReportFilterType.CURRENT_PRESCRIPTIONS;
		MedicalReportFilterType actual = MedicalReportFilter
				.filterTypeFromString("CurrENt_PREscriptions");
		assertEquals(expected, actual);
	}

	public void testGetFilterType() {
		filter = new MedicalReportFilter(MedicalReportFilterType.PROCEDURE, "city!", factory);
		MedicalReportFilterType expected = MedicalReportFilterType.PROCEDURE;
		assertEquals(expected, filter.getFilterType());
	}

	public void testGetFilterValue() {
		filter = new MedicalReportFilter(MedicalReportFilterType.PROCEDURE, "city!", factory);
		String expected = "city!";
		assertEquals(expected, filter.getFilterValue());
	}
	
	public void testGetFilterTypeString() {
		filter = new MedicalReportFilter(MedicalReportFilterType.PROCEDURE, "city!", factory);
		assertEquals("PROCEDURE", filter.getFilterTypeString());
		
		filter = new MedicalReportFilter(MedicalReportFilterType.CURRENT_PRESCRIPTIONS, "city!", factory);
		assertEquals("CURRENT PRESCRIPTIONS", filter.getFilterTypeString());
	}
}
