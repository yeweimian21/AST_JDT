package edu.ncsu.csc.itrust.unit.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ApptBean;

public class ApptBeanTest extends TestCase {
	
	public void testApptEquals() {
		ApptBean b = new ApptBean();
		b.setApptID(3);
		
		ApptBean a = new ApptBean();
		a.setApptID(3);
		
		assertTrue(a.equals(b));
	}
}