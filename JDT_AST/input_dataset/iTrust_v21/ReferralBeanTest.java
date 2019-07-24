package edu.ncsu.csc.itrust.unit.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ReferralBean;

public class ReferralBeanTest extends TestCase {



	public void testBean() {
		ReferralBean r = new ReferralBean();
		r.setId(1L);
		r.setSenderID(2L);
		r.setReceiverID(3L);
		r.setPatientID(4L);
		r.setReferralDetails("Five");
		r.setOvid(6L);
		r.setViewedByHCP(true);
		r.setViewedByPatient(true);
		
		assertEquals(1, r.getId());
		assertEquals(2, r.getSenderID());
		assertEquals(3, r.getReceiverID());
		assertEquals(4, r.getPatientID());
		assertEquals("Five", r.getReferralDetails());
		assertEquals(6, r.getOvid());
		assertTrue(r.isViewedByHCP());
		assertTrue(r.isViewedByPatient());
	}
	
	
}
