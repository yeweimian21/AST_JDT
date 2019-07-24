package edu.ncsu.csc.itrust.unit.dao.reportrequest;

import java.text.SimpleDateFormat;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ReportRequestBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ReportRequestDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * 
 *
 */
public class ReportRequestDAOTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private ReportRequestDAO dao = factory.getReportRequestDAO();
	
	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.reportRequests();
		
	}

	public void testGetReportsUnknownRequeter() throws Exception {
		try {
			dao.getAllReportRequestsForRequester(0);
			fail("should have thrown an exception");
		} catch (DBException ex) {
			assertEquals("RequesterMID cannot be null", ex.getSQLException().getMessage());
		}
	}
	
	public void testGetReportsCheckListRequester() throws Exception {
		List<ReportRequestBean> list = dao.getAllReportRequestsForRequester(9000000000L);
		assertEquals(6, list.size());
		assertEquals(ReportRequestBean.Requested, list.get(0).getStatus());
	}
	
	public void testGetReportsCheckListPatient() throws Exception {
		List<ReportRequestBean> list = dao.getAllReportRequestsForPatient(2L);
		assertEquals(4, list.size());
		assertEquals(ReportRequestBean.Requested, list.get(0).getStatus());
	}
	
	public void testGetReportsNullReportID() throws Exception {
		try {
			dao.getReportRequest(0);
			fail("Should have thrown an exception");
		} catch (DBException ex) {
			assertEquals("ID cannot be null", ex.getSQLException().getMessage());
		}
	}
	
	public void testGetSpecificReport3CheckDetails() throws Exception {
		ReportRequestBean b = dao.getReportRequest(3);
		assertEquals(3, b.getID());
		assertEquals(9000000000L, b.getRequesterMID());
		assertEquals(2, b.getPatientMID());
		assertEquals("01/03/2008 12:00", b.getRequestedDateString());
	}

	public void testGetSpecificReport4CheckDetails() throws Exception {
		ReportRequestBean b = dao.getReportRequest(4);
		assertEquals(4, b.getID());
		assertEquals(9000000000L, b.getRequesterMID());
		assertEquals(2, b.getPatientMID());
		assertEquals("01/04/2008 12:00", b.getRequestedDateString());
		assertEquals("03/04/2008 12:00", b.getViewedDateString());
		assertEquals(ReportRequestBean.Viewed, b.getStatus());
	}
	
	public void testInsertReportFailureNullMIDs() throws Exception {
		try {
			dao.addReportRequest(0, 0, null);
			fail("Should have throw exception");
		} catch (DBException ex) {
			assertEquals("RequesterMID cannot be null", ex.getSQLException().getMessage());
		}
	}
	
	public void testAddThenRetrieveReport() throws Exception {
		long id = dao.addReportRequest(9000000000L, 2, new SimpleDateFormat(ReportRequestBean.dateFormat).parse("06/06/2008 13:00"));
		ReportRequestBean b2 = dao.getReportRequest(id);
		assertEquals(9000000000L, b2.getRequesterMID());
		assertEquals(2, b2.getPatientMID());
		assertEquals("06/06/2008 13:00", b2.getRequestedDateString());
		assertEquals(ReportRequestBean.Requested, b2.getStatus());
	}
	
	public void testSetViewedFailure() throws Exception {
		try {
			dao.setViewed(0, null);
			fail("Should have throw exception");
		} catch (DBException ex) {
			assertEquals("ID cannot be null", ex.getSQLException().getMessage());
		}
	}
	
	public void testAddThenSetViewed() throws Exception {
		long id = dao.addReportRequest(9000000000L, 2, new SimpleDateFormat(ReportRequestBean.dateFormat).parse("06/06/2008 13:00"));
		dao.setViewed(id, new SimpleDateFormat(ReportRequestBean.dateFormat).parse("08/08/2008 15:00"));
		ReportRequestBean b2 = dao.getReportRequest(id);
		assertEquals("08/08/2008 15:00", b2.getViewedDateString());
		assertEquals(ReportRequestBean.Viewed, b2.getStatus());
	}
}
