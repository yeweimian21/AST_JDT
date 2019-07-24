package edu.ncsu.csc.itrust.unit.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.ViewOfficeVisitAction;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.HealthRecordsDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Test All doctor office visit
 */
public class ViewOfficeVisitActionTest extends TestCase {
	

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private ViewOfficeVisitAction action;
	private TestDataGenerator gen;
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
	}

	/**
	 * Test view office visit
	 * @throws Exception
	 */
	public void testViewOfficeVisit() throws Exception {
		String hcp = null;
		action = new ViewOfficeVisitAction(factory, 2L, "955");
		assertEquals(955L, action.getOvID());
		assertEquals(2L, action.getPid());
		assertEquals(955L, action.getOfficeVisit().getID());
		hcp = action.getHCPName(9000000000L);
		assertNotNull(hcp);
		hcp = action.getHCPName(9000000099L);
		assertEquals("User does not exist", hcp);
		
		try {
			action = new ViewOfficeVisitAction(factory, 2L, "0");
			fail("should have been iTrustException");
		}
		catch (DBException dbe) {
			//TODO
		}
		catch (ITrustException e) {
			assertEquals("Office Visit "+Long.valueOf("0")+" with Patient MID 2 does not exist", e.getMessage());
		}
	}
	
	public void testOfficeVisitSubActions() throws Exception {
		action = new ViewOfficeVisitAction(factory, 2L, "955");
		assertEquals(1, action.getAllProcedures().size());
		assertEquals(1, action.getDiagnoses().size());
		assertEquals(0, action.getImmunizations().size());
		assertEquals(2, action.getLabProcedures().size());
		assertEquals(3, action.getPrescriptions().size());
		assertEquals(1, action.getProcedures().size());
	}

	public void testCanRepresent() throws Exception {
		try {
			action = new ViewOfficeVisitAction(factory, 2L, "1", "11");
		} catch (Exception e) {
			fail("No exception should be thrown; exception: " + e.toString());
		}
	}
	
	public void testCannotRepresent() throws Exception {
		try {
			action = new ViewOfficeVisitAction(factory, 1L, "2", "955");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("You do not represent this patient, contact your HCP to represent this patient", 
					e.getMessage());
		}
	}
	
	public void testGetPatientAgeInMonths() throws Exception {
		OfficeVisitDAO ovDAO = factory.getOfficeVisitDAO();
		OfficeVisitBean ovBean = new OfficeVisitBean();
		ovBean.setPatientID(101L);
		ovBean.setERIncident(false);
		ovBean.setHcpID(9000000000L);
		ovBean.setHospitalID("1");
		ovBean.setNotes("");
		ovBean.setVisitDateStr("09/30/2013");
		long ovID = ovDAO.add(ovBean);
		
		action = new ViewOfficeVisitAction(factory, 101L, Long.toString(ovID));
		
		assertEquals(4, action.getPatientAgeInMonths());
	}
	
	public void testGetHealthRecord() throws Exception {
		OfficeVisitDAO ovDAO = factory.getOfficeVisitDAO();		
		OfficeVisitBean ovBean = new OfficeVisitBean();
		ovBean.setPatientID(101L);
		ovBean.setERIncident(false);
		ovBean.setHcpID(9000000000L);
		ovBean.setHospitalID("1");
		ovBean.setNotes("");
		ovBean.setVisitDateStr("10/01/2013");
		long ovID = ovDAO.add(ovBean);
		
		HealthRecordsDAO hrDAO = factory.getHealthRecordsDAO();
		HealthRecord hr = new HealthRecord();
		hr.setPatientID(101L);
		hr.setHeight(20.1);
		hr.setWeight(22.3);
		hr.setHeadCircumference(36.2);
		hr.setHouseholdSmokingStatus(1);
		hr.setOfficeVisitID(ovID);
		hr.setOfficeVisitDateStr("10/01/2013");
		hrDAO.add(hr);
		
		action = new ViewOfficeVisitAction(factory, 101L, Long.toString(ovID));
		HealthRecord testHR = action.getHealthRecord();
		assertEquals(hr.getPatientID(), testHR.getPatientID());
		assertEquals(hr.getHeight(), testHR.getHeight());
		assertEquals(hr.getWeight(), testHR.getWeight());
		assertEquals(hr.getHeadCircumference(), testHR.getHeadCircumference());
		assertEquals(hr.getOfficeVisitID(), testHR.getOfficeVisitID());
		assertEquals(hr.getVisitDateStr(), testHR.getVisitDateStr());
	}
}
