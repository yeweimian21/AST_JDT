package edu.ncsu.csc.itrust.unit.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.ncsu.csc.itrust.action.ViewRecordsReleaseAction;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.RecordsReleaseBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.RecordsReleaseDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class ViewRecordsReleaseActionTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DAOFactory evilFactory = EvilDAOFactory.getEvilInstance();
	private TestDataGenerator gen;
	private ViewRecordsReleaseAction action;
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		action = new ViewRecordsReleaseAction(factory, 9000000000L);
	}
	
	public void testGetHospitalReleaseRequests() throws ITrustException {
		//Get the current amount of release requests for the hospital
		int amountOfRecords = action.getHospitalReleaseRequests().size();
		
		//Add a RecordsReleaseBean
		RecordsReleaseBean release = new RecordsReleaseBean();
		release.setPid(102L);
		release.setDateRequested(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		release.setDocEmail("test@gmail.com");
		release.setDocFirstName("Doctor");
		release.setDocLastName("Super");
		release.setDocPhone("111-111-1111");
		release.setRecHospitalAddress("500 Address Lane");
		release.setRecHospitalName("Worst Hospital");
		release.setReleaseHospitalID("1");
		release.setStatus(0);
		release.setJustification("Who needs a justification?");
		RecordsReleaseDAO rrDAO = new RecordsReleaseDAO(factory);
		try {
			rrDAO.addRecordsRelease(release);
		} catch (DBException e) {
			//There should be no DBException
			fail();
		}
		//Assert that one more records release bean is returned
		assertEquals(++amountOfRecords, action.getHospitalReleaseRequests().size());
		
		//Add a record release request from a different hospital
		release.setReleaseHospitalID("9999");
		try {
			rrDAO.addRecordsRelease(release);
		} catch (DBException e) {
			//There should be no DBException
			fail();
		}
		//Assert that the same amount of records release beans are returned
		assertEquals(amountOfRecords, action.getHospitalReleaseRequests().size());
		
		//Attempt to get the release requests as an uap
		action = new ViewRecordsReleaseAction(factory, 8000000009L);
		assertFalse(action.getHospitalReleaseRequests().isEmpty());
		
		//Attempt to get the release requests as a non hcp or uap user
		action = new ViewRecordsReleaseAction(factory, 102L);
		assertTrue(action.getHospitalReleaseRequests().isEmpty());
	}
	
	public void testGetNumPendingRequests() {
		List<RecordsReleaseBean> testList = new ArrayList<RecordsReleaseBean>();
		assertEquals(0, action.getNumPendingRequests(testList));
		
		RecordsReleaseBean pendingBean = new RecordsReleaseBean();
		pendingBean.setStatus(0);
		testList.add(pendingBean);
		assertEquals(1, action.getNumPendingRequests(testList));
		
		RecordsReleaseBean approvedBean = new RecordsReleaseBean();
		approvedBean.setStatus(2);
		testList.add(approvedBean);
		assertEquals(1, action.getNumPendingRequests(testList));
		
		RecordsReleaseBean deniedBean = new RecordsReleaseBean();
		deniedBean.setStatus(2);
		testList.add(deniedBean);
		assertEquals(1, action.getNumPendingRequests(testList));
	}
	
	public void testApproveRequest() {
		//Add a RecordsReleaseBean
		RecordsReleaseBean release = new RecordsReleaseBean();
		release.setPid(102L);
		release.setDateRequested(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		release.setDocEmail("test@gmail.com");
		release.setDocFirstName("Doctor");
		release.setDocLastName("Super");
		release.setDocPhone("111-111-1111");
		release.setRecHospitalAddress("500 Address Lane");
		release.setRecHospitalName("Worst Hospital");
		release.setReleaseHospitalID("1");
		release.setStatus(0);
		release.setJustification("Who needs a justification?");
		RecordsReleaseDAO rrDAO = new RecordsReleaseDAO(factory);
		try {
			rrDAO.addRecordsRelease(release);
			//Get a records release bean for patient 102
			release = rrDAO.getAllRecordsReleasesByPid(102L).get(0);
		} catch (DBException e) {
			//There should be no DBException
			fail();
		}
		//Get the request id
		long requestID = release.getReleaseID();
		
		//Approve the records release
		assertTrue(action.approveRequest(release));
		//Make sure that the records release status is properly updated
		try {
			release = rrDAO.getRecordsReleaseByID(requestID);
		} catch (DBException e) {
			//There should be no DBException
			fail();
		}
		assertEquals(1, release.getStatus());
		
		//Attempt to approve a request that is not in pending status
		assertFalse(action.approveRequest(release));
	}
	
	public void testDenyRequest() {
		//Add a RecordsReleaseBean
		RecordsReleaseBean release = new RecordsReleaseBean();
		release.setPid(102L);
		release.setDateRequested(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		release.setDocEmail("test@gmail.com");
		release.setDocFirstName("Doctor");
		release.setDocLastName("Super");
		release.setDocPhone("111-111-1111");
		release.setRecHospitalAddress("500 Address Lane");
		release.setRecHospitalName("Worst Hospital");
		release.setReleaseHospitalID("1");
		release.setStatus(0);
		release.setJustification("Who needs a justification?");
		RecordsReleaseDAO rrDAO = new RecordsReleaseDAO(factory);
		try {
			rrDAO.addRecordsRelease(release);
			//Get a records release bean for patient 102
			release = rrDAO.getAllRecordsReleasesByPid(102L).get(0);
		} catch (DBException e) {
			//There should be no DBException
			fail();
		}
		//Get the request id
		long requestID = release.getReleaseID();
		
		//Deny the records release
		assertTrue(action.denyRequest(release));
		//Make sure that the records release status is properly updated
		try {
			release = rrDAO.getRecordsReleaseByID(requestID);
		} catch (DBException e) {
			//There should be no DBException
			fail();
		}
		assertEquals(2, release.getStatus());
		
		//Attempt to deny a request that is not in pending status
		assertFalse(action.denyRequest(release));
	}
	
	public void testEvilFactory() throws ITrustException {
		action = new ViewRecordsReleaseAction(evilFactory, 9000000000L);
		
		//Create and add a new RecordsReleaseBean to test with
		RecordsReleaseBean release = new RecordsReleaseBean();
		release.setPid(102L);
		release.setDateRequested(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		release.setDocEmail("test@gmail.com");
		release.setDocFirstName("Doctor");
		release.setDocLastName("Super");
		release.setDocPhone("111-111-1111");
		release.setRecHospitalAddress("500 Address Lane");
		release.setRecHospitalName("Worst Hospital");
		release.setReleaseHospitalID("1");
		release.setStatus(0);
		release.setJustification("Who needs a justification?");
		RecordsReleaseDAO rrDAO = new RecordsReleaseDAO(factory);
		try {
			rrDAO.addRecordsRelease(release);
			//Get a records release bean for patient 102
			release = rrDAO.getAllRecordsReleasesByPid(102L).get(0);
		} catch (DBException e) {
			//There should be no DBException
			fail();
		}
		
		//Attempt to approve and deny a release request
		assertFalse(action.approveRequest(release));
		assertFalse(action.denyRequest(release));
		
		//Attempt to get all hospitals and ensure an empty list is returned
		assertTrue(action.getHospitalReleaseRequests().isEmpty());
		
		//Attempt to get a patient name with a dirty factory
		assertEquals("", action.getPatientName(102L));
		
		//Attempt to get a hospital name with a dirty factory
		assertEquals("", action.getHospitalName("1"));
		
		//Attempt to get a list of requested health records with a dirty factory
		assertTrue(action.getRequestedHealthRecords(release).isEmpty());
		
		//Attempt to get a doctor's name with a dirty factory
		assertEquals("", action.getDoctorName(9000000000L));
	}
	
	public void testGetPatientName() {
		assertEquals("Caldwell Hudson", action.getPatientName(102L));
		
		//Try running the test with a non existent patient
		assertEquals("", action.getPatientName(66L));
	}
	
	public void testGetHospitalName() {
		HospitalsDAO hosDAO = new HospitalsDAO(factory);
		String hospitalName = "";
		try {
			//Get hospital name for hospital id 1
			hospitalName = hosDAO.getHospital("1").getHospitalName();
		} catch (DBException e) {
			//Fail if there is a DBException
			fail();
		}
		//Test that the name the action method gets is the same
		assertEquals(hospitalName, action.getHospitalName("1"));
		
		//Clear the db tables
		try {
			gen.clearAllTables();
		} catch (Exception e) {
			//Fail if there is an exception
			fail();
		}
		
		//Test that a blank string is returned if there is no hospital with the given id
		assertEquals("", action.getHospitalName("1"));
	}
	
	public void testFilterPendingRequests() {
		RecordsReleaseBean release = new RecordsReleaseBean();
		release.setStatus(0);
		
		//Create a list of requests
		List<RecordsReleaseBean> releases = new ArrayList<RecordsReleaseBean>();
		releases.add(release);
		
		assertEquals(1, action.filterPendingRequests(releases).size());
		
		//Add a non pending release
		RecordsReleaseBean nonPending = new RecordsReleaseBean();
		nonPending.setStatus(1);
		releases.add(nonPending);
		
		assertEquals(1, action.filterPendingRequests(releases).size());
	}
	
	public void testGetRequestedHealthRecords() {
		//Add a RecordsReleaseBean
		RecordsReleaseBean release = new RecordsReleaseBean();
		release.setPid(102L);
		release.setDateRequested(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		release.setDocEmail("test@gmail.com");
		release.setDocFirstName("Doctor");
		release.setDocLastName("Super");
		release.setDocPhone("111-111-1111");
		release.setRecHospitalAddress("500 Address Lane");
		release.setRecHospitalName("Worst Hospital");
		release.setReleaseHospitalID("1");
		release.setStatus(0);
		release.setJustification("Who needs a justification?");
		
		//Assert that the method returns a list with at least one hospital
		List<HealthRecord> records = action.getRequestedHealthRecords(release);
		assertFalse(records.isEmpty());
		
		//Check that the all the health records are from hospital 1
		OfficeVisitDAO ovDAO = factory.getOfficeVisitDAO();
		long ovID;
		try {
			for (int i = 0; i < records.size(); i++) {
				ovID = records.get(i).getOfficeVisitID();
				assertEquals("1", ovDAO.getOfficeVisit(ovID).getHospitalID());
			}
		} catch (DBException e) {
			//If a DBException occurs fail
			fail("A DBException was thrown when getting an office visit");
		}
	}
	
	public void testGetDoctorName() throws ITrustException {
		//Get Kelly Doctor's name
		assertEquals("Kelly Doctor", action.getDoctorName(9000000000L));
		//Get Shelly Vang's name
		assertEquals("Shelly Vang", action.getDoctorName(8000000011L));
	}
		
}
