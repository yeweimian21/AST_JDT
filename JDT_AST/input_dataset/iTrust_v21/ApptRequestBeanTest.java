package edu.ncsu.csc.itrust.unit.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.beans.ApptRequestBean;

public class ApptRequestBeanTest extends TestCase {

	private ApptRequestBean req;
	private ApptBean apt;

	protected void setUp() throws Exception {
		req = new ApptRequestBean();
		apt = new ApptBean();
	}
	
	public void testGetRequestedAppt() {
		ApptBean b = req.getRequestedAppt();
		assertNull(b);
		req.setRequestedAppt(apt);
		b = req.getRequestedAppt();
		assertEquals(apt, b);
	}
	
	public void testPending() {
		assertTrue(req.isPending());
		req.setPending(false);
		assertFalse(req.isPending());
		req.setPending(true);
		assertTrue(req.isPending());
	}
	
	public void testAccepted() {
		assertFalse(req.isAccepted());
		req.setPending(false);
		assertFalse(req.isAccepted());
		req.setAccepted(true);
		assertTrue(req.isAccepted());
		req.setAccepted(false);
		assertFalse(req.isAccepted());
	}
}
