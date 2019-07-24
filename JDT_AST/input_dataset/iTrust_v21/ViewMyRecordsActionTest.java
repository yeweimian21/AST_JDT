package edu.ncsu.csc.itrust.unit.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FamilyDAO;
import edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO;

import java.util.Calendar;
import java.util.List;

import edu.ncsu.csc.itrust.action.ViewMyRecordsAction;
import edu.ncsu.csc.itrust.beans.AllergyBean;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.FamilyMemberBean;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.beans.ReportRequestBean;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ViewMyRecordsActionTest extends TestCase {
	private ViewMyRecordsAction action;
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DAOFactory evil = EvilDAOFactory.getEvilInstance();
	private FamilyDAO famDAO = new FamilyDAO(factory);
	private List<FamilyMemberBean> fmbList = null;
	private FamilyMemberBean fmBean = null;
	
	private long pid = 2L;
	private long hcpId = 9000000000L;

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();

		action = new ViewMyRecordsAction(factory, pid);
		fmbList = famDAO.getParents(5);
		fmBean = fmbList.get(0);
	}

	public void testViewMyRecords() throws Exception {
		assertEquals(pid, action.getPatient().getMID());
		assertEquals(2, action.getAllergies().size());
		assertEquals(9, action.getFamily().size());
		assertEquals(2, action.getAllHealthRecords().size());
		assertEquals(12, action.getAllOfficeVisits().size());
		assertEquals(6, action.getRepresented().size());
		assertEquals(0, action.getRepresenting().size());
		assertTrue(action.isSurveyCompleted(952));
		assertEquals(1, action.getLabs().size());
		action.representPatient("1");
		assertEquals(1L, action.getPatient().getMID());
		assertTrue(action.doesFamilyMemberHaveHighBP(fmBean));
		assertTrue(action.doesFamilyMemberHaveDiabetes(fmBean));
		assertTrue(action.isFamilyMemberSmoker(fmBean));
		assertFalse(action.doesFamilyMemberHaveCancer(fmBean));
		assertFalse(action.doesFamilyMemberHaveHighCholesterol(fmBean));
		assertFalse(action.doesFamilyMemberHaveHeartDisease(fmBean));
		assertTrue(action.getFamilyMemberCOD(fmBean).contains("Diabetes"));
		assertEquals(5, new ViewMyRecordsAction(factory, 5L).getFamilyHistory().size());
	}
	
	public void testViewNonExistantRecords()  {
		action = new ViewMyRecordsAction(evil, 0l);
		try
		{
			action.getFamilyHistory();
		}
		catch (ITrustException e)
		{
			assertEquals("A database exception has occurred. Please see the log in the console for stacktrace", e.getMessage());
		}
		
		try
		{
			action.getFamily();
		}
		catch (ITrustException e)
		{
			assertEquals("A database exception has occurred. Please see the log in the console for stacktrace", e.getMessage());
		}
	}
	

	public void testRepresentPatient() throws ITrustException {
		String StrRep = "3";
		long longRep = (new Long(StrRep)).longValue();
		long mid = action.representPatient("3");
		assertEquals(longRep, mid);
		
		try {
			mid = action.representPatient("Three");
			fail();
		} catch(ITrustException e) {
			assertEquals(e.getMessage(), "MID is not a number");
		}
	}

	public void testGetPatient() throws ITrustException {
		PatientBean pBean = action.getPatient();
		assertEquals(2L, pBean.getMID());

		pBean = action.getPatient(2L);
		assertEquals(2L, pBean.getMID());
	}

	public void testGetPersonnel() throws ITrustException {
		PersonnelBean pBean = action.getPersonnel(hcpId);
		
		assertEquals(hcpId, pBean.getMID());
	}
	
	public void testGetEmailHistory() throws ITrustException {
		List<Email> eList = action.getEmailHistory();
		
		assertEquals(2, eList.size());
	}
	
	public void testGetAllergies() throws ITrustException {
		List<AllergyBean> abList = action.getAllergies();
		
		assertEquals(2, abList.size());
	}
	
	public void testGetFamily() throws ITrustException {
		List<FamilyMemberBean> fmbList = action.getFamily();
		
		assertEquals(9, fmbList.size());
	}
	
	public void testGetFamilyHistory() throws ITrustException {
		List<FamilyMemberBean> fmbList = action.getFamilyHistory();
		
		assertEquals(5, fmbList.size());
	}
	
	public void testGetAllHealthRecords() throws ITrustException {
		List<HealthRecord> hrList = action.getAllHealthRecords();
		
		assertEquals(2, hrList.size());
	}

	public void testGetFamilyHealthRecords() throws ITrustException {
		List<HealthRecord> hrList = action.getFamilyHealthRecords(this.pid);
		
		assertEquals(2, hrList.size());
	}
	
	public void testGetAllOfficeVisits() throws ITrustException {
		List<OfficeVisitBean> ovbList = action.getAllOfficeVisits();
		
		assertEquals(12, ovbList.size());
	}
	
	public void testGetRepresented() throws ITrustException {
		List<PatientBean> pbList = action.getRepresented();
		
		assertEquals(6, pbList.size());
	}

	public void testGetRepresenting() throws ITrustException {
		List<PatientBean> pbList = action.getRepresenting();
		
		assertEquals(0, pbList.size());
	}
	
	public void testIsSurveyCompleted() throws ITrustException {
		List<OfficeVisitBean> ovbList = action.getAllOfficeVisits();

		OfficeVisitBean ovBean = ovbList.get(0);
		
		assertFalse(action.isSurveyCompleted(ovBean.getID()));
	}
	
	public void testGetLabs() throws ITrustException {
		List<LabProcedureBean> lpbList = action.getLabs();
		
		assertEquals(1, lpbList.size());
	}
	
	public void testGetReportRequests() throws ITrustException {
		List<ReportRequestBean> rrbList = action.getReportRequests();

		assertEquals(4, rrbList.size());
	}
	
	public void testDoesFamilyMemberHaveHighBP() throws ITrustException {
		assertTrue(action.doesFamilyMemberHaveHighBP(fmBean));
	}
	
	public void testDoesFamilyMemberHaveHighCholesterol() throws ITrustException {
		assertFalse(action.doesFamilyMemberHaveHighCholesterol(fmBean));
	}
	
	public void testDoesFamilyMemberHaveDiabetes() throws ITrustException {
		assertTrue(action.doesFamilyMemberHaveDiabetes(fmBean));
	}

	public void testDoesFamilyMemberHaveCancer() throws ITrustException {
		assertFalse(action.doesFamilyMemberHaveCancer(fmBean));
	}

	public void testDoesFamilyMemberHaveHeartDisease() throws ITrustException {
		assertFalse(action.doesFamilyMemberHaveHeartDisease(fmBean));
	}

	public void testIsFamilyMemberSmoker() throws ITrustException {
		assertTrue(action.isFamilyMemberSmoker(fmBean));
	}

	public void testGetFamilyMemberCOD() throws ITrustException {
		assertEquals("Diabetes with ketoacidosis", action.getFamilyMemberCOD(fmBean));
	}
	
	public void testGetSpecificLabs() throws ITrustException {
		List<LabProcedureBean> lpbList = action.getSpecificLabs(pid, "10640-1");
		
		assertEquals(1, lpbList.size());
	}
	
	public void testGetOfficeVisit() throws ITrustException {
		OfficeVisitBean testBean = action.getCompleteOfficeVisit(955L);
		
		assertEquals(955L, testBean.getID());
		assertEquals(9000000000L, testBean.getHcpID());
		assertEquals("1", testBean.getHospitalID());
		assertEquals("06/10/2007", testBean.getVisitDateStr());
		
	}
	
	public void testGetProcedures() throws DBException {
		assertEquals(1, action.getProcedures(955L).size());
		assertEquals(0, action.getProcedures(0).size());
	}
	
	public void testSetViewed() throws ITrustException {
		LabProcedureDAO labDAO = new LabProcedureDAO(factory);
		List<LabProcedureBean> testProcs = labDAO.getAllLabProcedures();
		
		for (LabProcedureBean b : testProcs) {
			if (b.getStatus().equals("Completed"))
				assertFalse(b.isViewedByPatient());
		}
		action.setViewed(testProcs);
		for (LabProcedureBean b : testProcs) {
			if (b.getStatus().equals("Completed"))
				assertTrue(b.isViewedByPatient());
		}
	}
	
	public void testGetPatientAgeInMonths() throws DBException {
		action = new ViewMyRecordsAction(factory, 101L);
		Calendar testDate = Calendar.getInstance();
		//Test 9/30/2013. Patient's birthday is 5/01/13
		testDate.set(2013, 8, 30);
		assertEquals(4, action.getPatientAgeInMonths(testDate));
		//Change test date to a year after the patient's birthdate
		testDate.set(2014, 4, 1);
		assertEquals(12, action.getPatientAgeInMonths(testDate));
	}
}
