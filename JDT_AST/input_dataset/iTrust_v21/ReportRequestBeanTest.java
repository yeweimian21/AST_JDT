package edu.ncsu.csc.itrust.unit.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ReportRequestBean;

public class ReportRequestBeanTest extends TestCase {
	
	public void testReportRequestBean() throws Exception {
		ReportRequestBean b = new ReportRequestBean();
		assertEquals(0L, b.getID());
		// test setters
		b.setID(1);
		b.setRequesterMID(2);
		b.setPatientMID(3);
		b.setRequestedDateString("01/01/2008 12:00");
		b.setViewedDateString("03/03/2008 12:00");
		b.setStatus(ReportRequestBean.Requested);
		
		// confirm with getters
		assertEquals(1, b.getID());
		assertEquals(2, b.getRequesterMID());
		assertEquals(3, b.getPatientMID());
		assertEquals("01/01/2008 12:00", b.getRequestedDateString());
		assertEquals("03/03/2008 12:00", b.getViewedDateString());
		assertEquals(ReportRequestBean.Requested, b.getStatus());
	}

	
	public void testBadRequestedDate() throws Exception {
		ReportRequestBean b = new ReportRequestBean();
		b.setRequestedDate(null);
		assertNull(b.getRequestedDate());
		b.setRequestedDateString("bad format");
		assertNull(b.getRequestedDate());
	}

}
